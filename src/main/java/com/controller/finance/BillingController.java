
package com.controller.finance;

import java.io.Console;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.controller.dto.finance.BillingByBrandAndDataActivityDTO;
import com.controller.dto.finance.DataActivityDTO;
import com.itextpdf.text.Element;
import com.model.Project;
import com.model.finance.InactiveControl;
import com.model.view.BillingByBrandView;
import com.model.view.SummaryBillingView;
import com.repository.finance.BillingRepository;
import com.service.finance.BillingService;
import com.service.finance.DataActivityService;

import net.bytebuddy.asm.Advice.Local;

@RestController
@RequestMapping("/billing")
public class BillingController {

	@Autowired
	BillingService billingService;
	@Autowired
	ModelMapper modelMapper;
	@Autowired
	DataActivityService dataActivityService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity getBilling(@RequestParam(required = false) List<Project> projects,
			@RequestParam(name = "start",required = false) String _start, @RequestParam(name = "end",required = false) String _end) {
		
		LocalDate start = LocalDate.parse(_start, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		LocalDate end = LocalDate.parse(_end, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

		List<BillingByBrandAndDataActivityDTO> dtos = new ArrayList<>();

		List<SummaryBillingView> summaryBillingViews = billingService.getSummaryBilling(start,end)
				.stream()
				.map(element -> new SummaryBillingView(dataActivityService.findById(element.getDataActivity().getId()),
						element.getSumBill(), element.getHasInactive(), element.getHasBonus()))
				.toList();

		var list = createBatch(summaryBillingViews, 4);

		/*
		CompletableFuture<List<BillingByBrandAndDataActivityDTO>> supply = CompletableFuture.supplyAsync(() -> {
			return billWithOutInactive(summaryBillingViewsWithOutInactive);
		});

		CompletableFuture<List<BillingByBrandAndDataActivityDTO>> supply_2 = CompletableFuture.supplyAsync(() -> {
			try {
				return billWithInactive(summaryBillingViewsWithInactive);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		});

		try {
			dtos.addAll(supply.get());
			dtos.addAll(supply_2.get());
			return ResponseEntity.ok(dtos);
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			return null;
		}
		*/
		return null;

	}

	private List<BillingByBrandAndDataActivityDTO> billWithOutInactive(List<SummaryBillingView> list, LocalDate end,
			LocalDate start) {

		List<BillingByBrandAndDataActivityDTO> dtos = new ArrayList<>();
		Supplier<Stream<SummaryBillingView>> supplier = () -> list.stream();

		// Marcas criadas antes do start
		Supplier<Stream<SummaryBillingView>> summaryBillingViewsByBrandBeforeStart = () -> supplier.get()
				.filter(element -> element.getDataActivity().getCreatedAt().isBefore(start));
		// Marcas criadas antes do start
		Supplier<Stream<SummaryBillingView>> summaryBillingViewsByBrandAfterStart = () -> supplier.get()
				.filter(element -> element.getDataActivity().getCreatedAt().isAfter(start)
						|| element.getDataActivity().getCreatedAt().equals(start));

		// Se end é hoje e depois, precisa usar a data de hoje para o calculo
		LocalDate finalDate;
		if (end.isAfter(LocalDate.now()) || end.equals(LocalDate.now())) {
			finalDate = LocalDate.now();
		} else {
			finalDate = end;
		}
		// Se marca comecou antes do start
		if (summaryBillingViewsByBrandBeforeStart.get().findAny().isPresent()) {
			dtos.addAll(summaryBillingViewsByBrandBeforeStart.get()
					.map(element -> new BillingByBrandAndDataActivityDTO(
							modelMapper.map(element.getDataActivity(), DataActivityDTO.class),
							element.getSumBill().multiply(
									new BigDecimal(java.time.temporal.ChronoUnit.DAYS.between(start, finalDate)))))
					.collect(Collectors.toList()));
		}
		// Se marca comecou depois do start
		if (summaryBillingViewsByBrandAfterStart.get().findAny().isPresent()) {
			dtos.addAll(summaryBillingViewsByBrandAfterStart.get()
					.map(element -> new BillingByBrandAndDataActivityDTO(
							modelMapper.map(element.getDataActivity(), DataActivityDTO.class),
							element.getSumBill()
									.multiply(new BigDecimal(java.time.temporal.ChronoUnit.DAYS
											.between(element.getDataActivity().getCreatedAt(), finalDate)))))
					.collect(Collectors.toList()));
		}
		return dtos;
	}

	private List<BillingByBrandAndDataActivityDTO> billWithInactive(List<SummaryBillingView> list, LocalDate end,
			LocalDate start) {

		List<BillingByBrandAndDataActivityDTO> dtos = new ArrayList<>();
		Supplier<Stream<SummaryBillingView>> supplier = () -> list.stream();

		Supplier<Stream<SummaryBillingView>> summaryBillingViewsByBrandBeforeStart = () -> supplier.get()
				.filter(element -> element.getDataActivity().getCreatedAt().isBefore(start));

		Supplier<Stream<SummaryBillingView>> summaryBillingViewsByBrandAfterStart = () -> supplier.get()
				.filter(element -> element.getDataActivity().getCreatedAt().isAfter(start)
						|| element.getDataActivity().getCreatedAt().equals(start));

		// Se end é hoje e depois, precisa usar a data de hoje para o calculo
		LocalDate finalDate;
		if (end.isAfter(LocalDate.now()) || end.equals(LocalDate.now())) {
			finalDate = LocalDate.now();
		} else {
			finalDate = end;
		}

		if (summaryBillingViewsByBrandBeforeStart.get().findAny().isPresent()) {

			var stream = summaryBillingViewsByBrandBeforeStart.get()
					.filter(element -> element.getInactiveControls().stream()
							.allMatch(inactive -> inactive.getStartedAt().isAfter(start)
									&& inactive.getFinishedAt().isBefore(finalDate)));

			dtos.addAll(stream
					.map(element -> new BillingByBrandAndDataActivityDTO(
							modelMapper.map(element.getDataActivity(), DataActivityDTO.class),
							element.getSumBill()
									.multiply(new BigDecimal(
											this.sumDaysToInative(element.getInactiveControls(), start, finalDate)
													- java.time.temporal.ChronoUnit.DAYS.between(start, finalDate)))))
					.collect(Collectors.toList()));

		}

		if (summaryBillingViewsByBrandAfterStart.get().findAny().isPresent()) {
			var stream = summaryBillingViewsByBrandAfterStart.get()
					.filter(element -> element.getInactiveControls().stream()
							.allMatch(inactive -> inactive.getStartedAt().isAfter(start)
									&& inactive.getFinishedAt().isBefore(finalDate)));

			dtos.addAll(stream
					.map(element -> new BillingByBrandAndDataActivityDTO(
							modelMapper.map(element.getDataActivity(), DataActivityDTO.class),
							element.getSumBill()
									.multiply(new BigDecimal(this.sumDaysToInative(element.getInactiveControls(),
											element.getDataActivity().getCreatedAt(), finalDate)
											- java.time.temporal.ChronoUnit.DAYS.between(start, finalDate)))))
					.collect(Collectors.toList()));
		}
		return dtos;
	}

	private long sumDaysToInative(List<InactiveControl> inactiveControls, LocalDate start, LocalDate end) {
		long daysToCount = 0;
		for (InactiveControl inactiveControl : inactiveControls) {
			long cont = 0;
			LocalDate ca = inactiveControl.getStartedAt();
			LocalDate fa = inactiveControl.getFinishedAt();
			if (ca.isAfter(start) && fa.isBefore(end)) {
				if (ca.isBefore(start)) {
					if (fa.isBefore(end)) {
						cont = ChronoUnit.DAYS.between(start, fa);
					} else {
						cont = ChronoUnit.DAYS.between(start, end);
					}
				}
				if (ca.isAfter(start)) {
					if (fa.isBefore(end)) {
						cont = ChronoUnit.DAYS.between(ca, fa);
					} else {
						cont = ChronoUnit.DAYS.between(ca, end);
					}
				}
			}
			daysToCount = daysToCount + cont;
		}
		if (daysToCount >= ChronoUnit.DAYS.between(start, end)) {
			return ChronoUnit.DAYS.between(start, end);
		} else {
			return daysToCount;
		}

	}

	private static List<List<SummaryBillingView>> createBatch(List<SummaryBillingView> summaryBillingViews,
			int chunkSize) {
		List<List<SummaryBillingView>> listOfChunks = new ArrayList<List<SummaryBillingView>>();
		for (int i = 0; i < summaryBillingViews.size() / chunkSize; i++) {
			listOfChunks.add(summaryBillingViews.subList(i * chunkSize, i * chunkSize + chunkSize));
		}
		if (summaryBillingViews.size() % chunkSize != 0) {
			listOfChunks.add(summaryBillingViews.subList(
					summaryBillingViews.size() - summaryBillingViews.size() % chunkSize, summaryBillingViews.size()));
		}
		return listOfChunks;
	}

}

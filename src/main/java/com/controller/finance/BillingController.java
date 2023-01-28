

package com.controller.finance;

import java.io.Console;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
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
import com.model.view.BillingByBrandView;
import com.model.view.SummaryBillingView;
import com.repository.finance.BillingRepository;
import com.service.finance.BillingService;
import com.service.finance.DataActivityService;

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
			@RequestParam(required = false) LocalDate start, @RequestParam(required = false) LocalDate end) {
		
		List<BillingByBrandAndDataActivityDTO> dtos = new ArrayList<>();

		List<SummaryBillingView> summaryBillingViewsWithInactive = billingService
				.getSummaryBillingWithInactive(LocalDate.of(2023, 1, 1)).stream()
				.map(element -> new SummaryBillingView(dataActivityService.findById(element.getDataActivity().getId()),
						element.getSumBill(), element.getStarted_at_inactive())).toList();

		List<SummaryBillingView> summaryBillingViewsWithOutInactive = billingService
				.getSummaryBillingWithOutInactive().stream()
				.map(element -> new SummaryBillingView(dataActivityService.findById(element.getDataActivity().getId()),
						element.getSumBill(), element.getStarted_at_inactive())).toList();

		CompletableFuture<List<BillingByBrandAndDataActivityDTO>> supply = CompletableFuture.supplyAsync(() -> {
			return billWithOutInactive(summaryBillingViewsWithOutInactive);
		});

		CompletableFuture<List<BillingByBrandAndDataActivityDTO>> supply_2 = CompletableFuture.supplyAsync(() -> {
			try {
				return billWithInactive(summaryBillingViewsWithInactive);
			}catch (Exception e) {
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

	}

	private List<BillingByBrandAndDataActivityDTO> billWithOutInactive(List<SummaryBillingView> list) {

		List<BillingByBrandAndDataActivityDTO> dtos = new ArrayList<>();
		Supplier<Stream<SummaryBillingView>> supplier = () -> list.stream();

		Supplier<Stream<SummaryBillingView>> summaryBillingViewsByBrandBeforeStart = () -> supplier.get()
				.filter(element -> element.getDataActivity().getCreatedAt().isBefore(LocalDate.of(2023, 1, 1)));

		Supplier<Stream<SummaryBillingView>> summaryBillingViewsByBrandAfterStart = () -> supplier.get()
				.filter(element -> element.getDataActivity().getCreatedAt().isAfter(LocalDate.of(2023, 1, 1))
						|| element.getDataActivity().getCreatedAt().equals(LocalDate.of(2023, 1, 1)));

		if (summaryBillingViewsByBrandBeforeStart.get().findAny().isPresent()) {

			var summaryBillingViewsByBrandBeforeStartWithOutInactive = summaryBillingViewsByBrandBeforeStart.get()
					.filter(element -> element.getStarted_at_inactive() == null);

			if (LocalDate.of(2023, 2, 1).isAfter(LocalDate.now()) || LocalDate.of(2023, 2, 1).equals(LocalDate.now())) {
				var today = LocalDate.now();
				dtos.addAll(summaryBillingViewsByBrandBeforeStartWithOutInactive
						.map(element -> new BillingByBrandAndDataActivityDTO(
								modelMapper.map(element.getDataActivity(), DataActivityDTO.class),
								element.getSumBill()
										.multiply(new BigDecimal(java.time.temporal.ChronoUnit.DAYS
												.between(LocalDate.of(2023, 1, 1), LocalDate.now()))),null))
						.collect(Collectors.toList()));
			} else {

				dtos.addAll(summaryBillingViewsByBrandBeforeStartWithOutInactive
						.map(element -> new BillingByBrandAndDataActivityDTO(
								modelMapper.map(element.getDataActivity(), DataActivityDTO.class),
								element.getSumBill()
										.multiply(new BigDecimal(java.time.temporal.ChronoUnit.DAYS.between(
												LocalDate.of(2023, 1, 1), element.getDataActivity().getCreatedAt()))),null))
						.collect(Collectors.toList()));

			}
		}

		if (summaryBillingViewsByBrandAfterStart.get().findAny().isPresent()) {
			var summaryBillingViewsByBrandAfterStartWithOutInactive = summaryBillingViewsByBrandAfterStart.get()
					.filter(element -> element.getStarted_at_inactive() == null);

			if (LocalDate.of(2023, 2, 1).isAfter(LocalDate.now())) {
				var today = LocalDate.now();

				dtos.addAll(
						summaryBillingViewsByBrandAfterStartWithOutInactive
								.map(element -> new BillingByBrandAndDataActivityDTO(
										modelMapper.map(element.getDataActivity(), DataActivityDTO.class),
										element.getSumBill()
												.multiply(new BigDecimal(java.time.temporal.ChronoUnit.DAYS.between(
														element.getDataActivity().getCreatedAt(), LocalDate.now()))),null))
								.collect(Collectors.toList()));
			} else {

				dtos.addAll(summaryBillingViewsByBrandAfterStartWithOutInactive
						.map(element -> new BillingByBrandAndDataActivityDTO(
								modelMapper.map(element.getDataActivity(), DataActivityDTO.class),
								element.getSumBill()
										.multiply(new BigDecimal(java.time.temporal.ChronoUnit.DAYS.between(
												element.getDataActivity().getCreatedAt(), LocalDate.of(2023, 2, 1)))),null))
						.collect(Collectors.toList()));
			}
		}
		return dtos;
	}

	private List<BillingByBrandAndDataActivityDTO> billWithInactive(List<SummaryBillingView> list) {

		List<BillingByBrandAndDataActivityDTO> dtos = new ArrayList<>();
		Supplier<Stream<SummaryBillingView>> supplier = () -> list.stream();


		Supplier<Stream<SummaryBillingView>> summaryBillingViewsByBrandBeforeStart = () -> supplier.get()
				.filter(element -> element.getDataActivity().getCreatedAt().isBefore(LocalDate.of(2023, 1, 1)));
		
		System.out.println(summaryBillingViewsByBrandBeforeStart.get().collect(Collectors.toList()));

		Supplier<Stream<SummaryBillingView>> summaryBillingViewsByBrandAfterStart = () -> supplier.get()
				.filter(element -> element.getDataActivity().getCreatedAt().isAfter(LocalDate.of(2023, 1, 1))
						|| element.getDataActivity().getCreatedAt().equals(LocalDate.of(2023, 1, 1)));
		
		System.out.println(summaryBillingViewsByBrandAfterStart.get().collect(Collectors.toList()));


		if (summaryBillingViewsByBrandBeforeStart.get().findAny().isPresent()) {

			dtos.addAll(summaryBillingViewsByBrandBeforeStart.get()
					.filter(element -> element.getStarted_at_inactive().isAfter(/* end */ LocalDate.of(2023, 2, 1)) || element.getStarted_at_inactive().equals(/* end */ LocalDate.of(2023, 2, 1)))
					.map(element -> new BillingByBrandAndDataActivityDTO(
							modelMapper.map(element.getDataActivity(), DataActivityDTO.class),
							element.getSumBill()
									.multiply(new BigDecimal(java.time.temporal.ChronoUnit.DAYS.between(
											/* start */ LocalDate.of(2023, 1, 1),
											/* end */ LocalDate.of(2023, 2, 1)))),element.getStarted_at_inactive()))
					.collect(Collectors.toList()));

			dtos.addAll(summaryBillingViewsByBrandBeforeStart.get()
					.filter(element -> element.getStarted_at_inactive().isBefore(/* end */ LocalDate.of(2023, 2, 1)))
					.map(element -> new BillingByBrandAndDataActivityDTO(
							modelMapper.map(element.getDataActivity(), DataActivityDTO.class),
							element.getSumBill()
									.multiply(new BigDecimal(java.time.temporal.ChronoUnit.DAYS.between(
											/* start */ LocalDate.of(2023, 1, 1), element.getStarted_at_inactive()))),element.getStarted_at_inactive()))
					.collect(Collectors.toList()));

		}

		if (summaryBillingViewsByBrandAfterStart.get().findAny().isPresent()) {
			if (LocalDate.of(2023, 2, 1).isAfter(LocalDate.now())
					|| /* end */ LocalDate.of(2023, 2, 1).equals(LocalDate.now())) {
				System.out.println(summaryBillingViewsByBrandAfterStart.get()
						.filter(element -> element.getStarted_at_inactive().isAfter(/* end */ LocalDate.of(2023, 2, 1)) || element.getStarted_at_inactive().equals(/* end */ LocalDate.of(2023, 2, 1)) ).collect(Collectors.toList()));

				dtos.addAll(summaryBillingViewsByBrandAfterStart.get()
						.filter(element -> element.getStarted_at_inactive().isAfter(/* end */ LocalDate.of(2023, 2, 1)) || element.getStarted_at_inactive().equals(/* end */ LocalDate.of(2023, 2, 1)) )
						.map(element -> new BillingByBrandAndDataActivityDTO(
								modelMapper.map(element.getDataActivity(), DataActivityDTO.class),
								element.getSumBill()
										.multiply(new BigDecimal(java.time.temporal.ChronoUnit.DAYS.between(
												element.getDataActivity().getCreatedAt(),
												/* end */ LocalDate.of(2023, 2, 1)))),element.getStarted_at_inactive()))
						.collect(Collectors.toList()));
				
				System.out.println(summaryBillingViewsByBrandAfterStart.get().filter(element -> element.getStarted_at_inactive().isBefore(/* end */ LocalDate.of(2023, 2, 1)))
						.collect(Collectors.toList()));

				dtos.addAll(summaryBillingViewsByBrandAfterStart.get().filter(
						element -> element.getStarted_at_inactive().isBefore(/* end */ LocalDate.of(2023, 2, 1)))
						.map(element -> new BillingByBrandAndDataActivityDTO(
								modelMapper.map(element.getDataActivity(), DataActivityDTO.class),
								element.getSumBill()
										.multiply(new BigDecimal(java.time.temporal.ChronoUnit.DAYS.between(
												element.getDataActivity().getCreatedAt(),
												element.getStarted_at_inactive()))),element.getStarted_at_inactive()))
						.collect(Collectors.toList()));
			}

		}
		return dtos;
	}

}


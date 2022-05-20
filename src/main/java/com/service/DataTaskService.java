package com.service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.Activity;
import com.model.DataTask;
import com.model.Promoter;
import com.model.Task;
import com.repository.DataTaskRepository;

@Service
public class DataTaskService {
	
	@Autowired
	DataTaskRepository dataTaskRepository;
	
	public List<DataTask> findByShopAndGradeAndSituationEqualsIncompleto(String shop, String grade) throws Exception{
		try {
			return dataTaskRepository.findByShopAndGradeAndSituationEqualsIncompleto(grade,shop);
		}catch (Exception e) {
			throw new Exception("ERRO NO CARREGAMENTO DA LISTA");
		}
	}
	
	public List<Object[]> convertToReport(){
		List<DataTask> datas = dataTaskRepository.findByDate(LocalDate.now().minusDays(1));
	    List<Object[]> objects = new ArrayList<Object[]>();
	    
	    for(DataTask dataTask : datas) {
	        Object[] arr = new Object[5];
	        arr[0]= dataTask.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	        arr[1]= dataTask.getTeam().getName();
	        arr[2]= dataTask.getPromoter().getName();
	        arr[3]= dataTask.getSituation();
	        var starts = filterStart(dataTask);
			var ends = filterEnds(dataTask);
	        arr[4]= subtractDate(getMostNewDate(starts),getMostOldDate(ends));
	        objects.add(arr);
	    }
	    return objects;
	}
	
	public List<Object[]> convertToReportAjudaDeCusto(){
		List<DataTask> datas = dataTaskRepository.findByDate(LocalDate.now().minusDays(1));
	    List<Object[]> objects = new ArrayList<Object[]>();
	    List<Promoter> promoters = datas.stream().map(data -> data.getPromoter()).distinct().collect(Collectors.toList());
	    for(Promoter promoter : promoters) {
	        Object[] arr = new Object[8];
	        
	        List<DataTask> promoter_DataTasks = datas.stream().filter(dataTask -> dataTask.getPromoter().getId()==promoter.getId()).collect(Collectors.toList());
	        int[] relacaoDias = generateRelacaoDias(promoter_DataTasks);
	        arr[0] =  promoter;
	        arr[1] = relacaoDias[0];
	        arr[2] = relacaoDias[1];
	        arr[3] = relacaoDias[2];
	        arr[4] = relacaoDias[3];
	        arr[5] = relacaoDias[4];
	        arr[6] = relacaoDias[5];
	        arr[7] = relacaoDias[6];
	        objects.add(arr);
	    }
	    return objects;
	}
	
	public int[] generateRelacaoDias(List<DataTask> datas){
		
		int[] days = new int[7];
		List<DataTask> monday_DataTasks = datas.stream()
				.filter(data -> data.getDate().get(ChronoField.DAY_OF_WEEK) == 2)
				.collect(Collectors.toList());
		List<DataTask> tuesday_DataTasks = datas.stream()
				.filter(data -> data.getDate().get(ChronoField.DAY_OF_WEEK) == 3)
				.collect(Collectors.toList());
		List<DataTask> wednesday_DataTasks = datas.stream()
				.filter(data -> data.getDate().get(ChronoField.DAY_OF_WEEK) == 4)
				.collect(Collectors.toList());
		List<DataTask> thursday_DataTasks = datas.stream()
				.filter(data -> data.getDate().get(ChronoField.DAY_OF_WEEK) == 5)
				.collect(Collectors.toList());
		List<DataTask> friday_DataTasks = datas.stream()
				.filter(data -> data.getDate().get(ChronoField.DAY_OF_WEEK) == 6)
				.collect(Collectors.toList());
		List<DataTask> saturday_DataTasks = datas.stream()
				.filter(data -> data.getDate().get(ChronoField.DAY_OF_WEEK) == 7)
				.collect(Collectors.toList());
		
		days[0]= monday_DataTasks.stream()
				.filter(data -> data.getSituation().equals("COMPLETO") || data.getSituation().equals("INCOMPLETO"))
				.collect(Collectors.toList()).size();
		days[1] = tuesday_DataTasks.stream()
				.filter(data -> data.getSituation().equals("COMPLETO") || data.getSituation().equals("INCOMPLETO"))
				.collect(Collectors.toList()).size();
		days[2] = wednesday_DataTasks.stream()
				.filter(data -> data.getSituation().equals("COMPLETO") || data.getSituation().equals("INCOMPLETO"))
				.collect(Collectors.toList()).size();
		days[3] = thursday_DataTasks.stream()
				.filter(data -> data.getSituation().equals("COMPLETO") || data.getSituation().equals("INCOMPLETO"))
				.collect(Collectors.toList()).size();
		days[4] = friday_DataTasks.stream()
				.filter(data -> data.getSituation().equals("COMPLETO") || data.getSituation().equals("INCOMPLETO"))
				.collect(Collectors.toList()).size();
		days[5] = saturday_DataTasks.stream()
				.filter(data -> data.getSituation().equals("COMPLETO") || data.getSituation().equals("INCOMPLETO"))
				.collect(Collectors.toList()).size();
		days[6] = datas.stream().filter(data -> !data.getSituation().equals("COMPLETO") || !data.getSituation().equals("INCOMPLETO"))
				.collect(Collectors.toList()).size();
		return days;
	}
	
	public String subtractDate(LocalDateTime first, LocalDateTime second) {
		try {
			LocalDateTime tempDateTime = LocalDateTime.from(first);

			/*long days = tempDateTime.until( second, ChronoUnit.DAYS );
			tempDateTime = tempDateTime.plusDays( days );

			long hours = tempDateTime.until(second, ChronoUnit.HOURS);
			tempDateTime = tempDateTime.plusHours(hours);

			long minutes = tempDateTime.until(second, ChronoUnit.MINUTES);
			tempDateTime = tempDateTime.plusMinutes(minutes);
			long seconds = tempDateTime.until(second, ChronoUnit.SECONDS);
			*/
			Duration duration = Duration.between(first, second);
			long hours = duration.toHours();
			int minutes = (int) ((duration.getSeconds() % (60 * 60)) / 60);
			int seconds = (int) (duration.getSeconds() % 60);
			return hours + ":" + minutes + ":" + seconds;
		} catch (Exception e) {
			return null;
		}
	}
	public LocalDateTime getMostOldDate(List<LocalDateTime> datas) {
		LocalDateTime old = null;
	     for (int i = 0; i<datas.size();i++) {
			if(datas.get(i)!=null) {
				if(old==null) {
					old = datas.get(i);
					continue;
				}
				if (!old.isAfter(datas.get(i))) {
					old = datas.get(i);
				}
			}
			
		}
		return old;
	}
	public LocalDateTime getMostNewDate(List<LocalDateTime> datas) {
		LocalDateTime _new = null;
	     for (int i = 0; i<datas.size();i++) {
			if(datas.get(i)!=null) {
				if(_new==null) {
					_new = datas.get(i);
					continue;
				}
				if (!_new.isBefore(datas.get(i))) {
					_new = datas.get(i);
				}
			}
			
		}
		return _new;
	}
	public List<LocalDateTime> filterStart(DataTask dataTask) {
		List<LocalDateTime> starts = new ArrayList<LocalDateTime>();
		
		for(Task task:dataTask.getTasks()) {
			for(Activity activity: task.getActivities()) {
				starts.add(activity.getStart());
			}
		}
		return starts;
	}
	public List<LocalDateTime> filterEnds(DataTask dataTask) {
		List<LocalDateTime> ends = new ArrayList<LocalDateTime>();
		
		for(Task task:dataTask.getTasks()) {
			for(Activity activity: task.getActivities()) {
				ends.add(activity.getEnd());
			}
		}
		return ends;
	}
}
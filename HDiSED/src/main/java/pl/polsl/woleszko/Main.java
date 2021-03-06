package pl.polsl.woleszko;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import pl.woleszko.polsl.interpret.DataAnalizer;

public class Main {

	public static void main(String[] args) {

	    //Logback configuration - package level grained
        ((Logger) LoggerFactory.getLogger("pl.woleszko")).setLevel(Level.DEBUG);
        ((Logger) LoggerFactory.getLogger("org.apache.camel")).setLevel(Level.INFO);
		
		DataAnalizer extractor = new DataAnalizer();
		extractor.detect();
//		
//		HashMap<Long, HashMap<Long, Times>> list = extractor.getUsagePeriods();
//		HashMap<Long, Long> assignments = extractor.getNozzlesAssign();
//		HashMap<Integer, HashMap<Long, Double>> list = extractor.getHoursTrend();
//		
//		
////		Set<Long> keys = variances.keySet();
////		System.out.println();
////		for(Long key : keys) {System.out.println("Avg leakage per hour = " + extractor.getAvgPerHour(variances.get(key)));}
//		
////		HashMap<Long, Double> list = extractor.getVolumeTotals(Period.FULL_TIME);
////		
//		Integer counter = 0;
//		for(Long key : list.keySet()) {
//			System.out.println("Nozzle #" + key);
//			for(Long time : list.get(key).keySet())
//			System.out.println("Usage period: " + list.get(key).get(time).getFrom().toString() + " --> " + list.get(key).get(time).getTo().toString()+ " on nozzle #" + key);
//		}
//		
//		for(Long nozzle : assignments.keySet()) {
//			System.out.println("Nozzle #" + nozzle + " is attached to tank #" + assignments.get(nozzle));			
//		}
//			HashMap<Long, Double> tanksTotals = list.get(key);
//			// odczyt z tankow
//			for(Long tank : tanksTotals.keySet()) {
//				System.out.println("Tank " + tank + "avg = " +tanksTotals.get(tank));
//				
//			}
//		}
////		
////		System.out.println("counter: " + counter);
		
//		DataAnalizer analise = new DataAnalizer();
//		analise.checkTank();
//				analise.detect();
//		analise.splitDates(Period.DAY);
//		analise.splitDates((long) 86400000);
//		analise.checkTank((long) 3);

	}


}

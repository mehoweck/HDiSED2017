package pl.woleszko.polsl.interpret;

import java.util.HashMap;
import java.util.Set;

import pl.woleszko.polsl.maths.impl.NozzleDataExtractor;
import pl.woleszko.polsl.maths.impl.RefuelDataExtractor;
import pl.woleszko.polsl.maths.impl.TankDataExtractor;
import pl.woleszko.polsl.maths.objects.Period;
import pl.woleszko.polsl.maths.objects.Times;

public class DataAnalizer {

	NozzleDataExtractor nozzles = new NozzleDataExtractor();
	RefuelDataExtractor refuels = new RefuelDataExtractor();
	TankDataExtractor tanks = new TankDataExtractor();

	public HashMap<Long, Double> detect() {
		HashMap<Times, HashMap<Long, Double>> nozzleVolumes = nozzles.getVolumeTotals(Period.FULL_TIME);
		HashMap<Times, HashMap<Long, Double>> refuelVolumes = refuels.getVolumeTotals(Period.FULL_TIME);
		HashMap<Times, HashMap<Long, Double>> tankVolumes = tanks.getVolumeTotals(Period.FULL_TIME);

		Set<Long> tanksList = tanks.getTanksIndexes();
		Set<Times> periodKeys = tankVolumes.keySet();

		HashMap<Long, Double> variances = new HashMap<Long, Double>();
		HashMap<Long, Double> refuelTotals = null;
		HashMap<Long, Double> tankTotals = null;
		HashMap<Long, Double> nozzleTotals = null;

		for (Times key : periodKeys) {
			System.out.println(key.toString());
			// Totals initialization for tanks in periods (if not in period sets zeroes)

			for (Times nozzleVolumesTime : nozzleVolumes.keySet()) {
				if (key.dateInPeriod(nozzleVolumesTime.getFrom()) && key.dateInPeriod(nozzleVolumesTime.getTo()))
					nozzleTotals = nozzleVolumes.get(nozzleVolumesTime);
				break;
			}

			if (nozzleTotals.equals(null)) {
				nozzleTotals = new HashMap<Long, Double>();
				for (Long tank : tanksList) {
					nozzleTotals.put(tank, new Double(0));
				}
			}
			// =======================================
			if (tankVolumes.containsKey(key))
				tankTotals = tankVolumes.get(key);
			else {
				tankTotals = new HashMap<Long, Double>();
				for (Long tank : tanksList) {
					tankTotals.put(tank, new Double(0));
				}
			}

			// --------------------------------------

			for (Times refuelVolumesTime : refuelVolumes.keySet()) {
				if (key.dateInPeriod(refuelVolumesTime.getFrom()) && key.dateInPeriod(refuelVolumesTime.getTo()))
					refuelTotals = refuelVolumes.get(refuelVolumesTime);

			}
			if (refuelTotals.equals(null)) {
				refuelTotals = new HashMap<Long, Double>();
				for (Long tank : tanksList) {
					refuelTotals.put(tank, new Double(0));
				}
			}

			// ==========================================

			Double variance = new Double(0);
			for (Long tank : tanksList) {
				variance = nozzleTotals.get(tank);
				variance -= tankTotals.get(tank);
				variance -= refuelTotals.get(tank);
				System.out.println("Variance for tank " + tank + " = " + variance);
			}

			// variance = nozzleVolumes.get(key);
			// variance -= tankVolumes.get(key);
			// variance -= refuelVolumes.get(key);
			// variances.put(key, variance);
			// System.out.println("Variance for tank nr " + key.toString() + " = " +
			// variance);
		}

		return variances;
	}

	// 10%
	public void checkTank(Long tankID) {
		HashMap<Integer, HashMap<Long, Double>> hoursAvg = tanks.getHoursTrend();
		HashMap<Times, HashMap<Long, Double>> hoursVol = tanks.getVolumeTotals(Period.HOUR);

		Integer counter = 0;
		HashMap<Long, Double> testers = new HashMap<Long, Double>();
		HashMap<Long, Double> actuals = new HashMap<Long, Double>();

		for (Times time : hoursVol.keySet()) {
			for (Integer hour : hoursAvg.keySet()) {
				if (time.getFrom().getHours() == hour) {
					testers = hoursAvg.get(hour);
					actuals = hoursVol.get(time);

					Double standard = testers.get(tankID);
					Double actual = actuals.get(tankID);
					Double value = new Double(0);
					if (!actual.equals(0D))
						value = actual / standard; // uzyskujemy jaki procent odchyłu
					value = Math.abs(value);

					if (value > 2.60) {
						System.out.println("Probability of leakage on tank #" + tankID + " at " + time.toString());
						System.out.println("Delta: " + value + "%");
						counter++;
					}

				}
			}

		}
		System.out.println("COunter: " + counter);

	}

	public double getAvgVariancePerHour(Double variance) {

		double result = 0;
		HashMap<Long, Times> list = tanks.splitDates(Period.HOUR);
		Integer hoursCount = list.size();
		result = variance / hoursCount;

		return result;
	}

}

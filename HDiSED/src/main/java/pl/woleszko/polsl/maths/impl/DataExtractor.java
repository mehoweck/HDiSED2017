package pl.woleszko.polsl.maths.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.*;

import pl.woleszko.polsl.maths.objects.Period;
import pl.woleszko.polsl.maths.objects.Times;
import pl.woleszko.polsl.model.entities.Entity;
import pl.woleszko.polsl.model.entities.RefuelEntity;
import pl.woleszko.polsl.model.entities.TankMeasuresEntity;
import pl.woleszko.polsl.model.impl.FileAccessorCSV;

public abstract class DataExtractor {
	protected ArrayList<Entity> list;

	protected ArrayList<Entity> getEntities(String fileName) {
		// TODO Auto-generated method stub
		FileAccessorCSV access = new FileAccessorCSV(fileName);
		list = (ArrayList<Entity>) access.getValues();
		return list;

	}

	public HashMap<Long, Times> splitDates(Period swPeriod) {

		switch (swPeriod) {
		case FULL_TIME: {
			Date lastDate = list.get(list.size() - 1).getDate();
			Date firstDate = list.get(0).getDate();
			Long key = new Long(0);
			for (Entity entity : list) {
				if (entity.getDate().getTime() > lastDate.getTime())
					lastDate = entity.getDate();
				if (entity.getDate().getTime() < firstDate.getTime())
					firstDate = entity.getDate();
			}
			Times dates = new Times(firstDate, lastDate);
			HashMap<Long, Times> result = new HashMap<Long, Times>();
			result.put(key, dates);
			return result;
		}
		case MONTH: {
			// Date lastDate = list.get(list.size() - 1).getDate();
			// Date firstDate = list.get(0).getDate();
		}
		case DAY: {
			HashMap<Long, Times> result = new HashMap<Long, Times>();
			System.out.println(list.get(0).toString());
			System.out.println("----------------------->>");
			for (Entity entity : list) {
				Integer day = entity.getDate().getDate() * 31;
				Integer month = (entity.getDate().getMonth() + 1) * 12;
				Long keyValue = (long) (day + month);
				if (!result.containsKey(keyValue)) {
					System.out.println("New day! " + entity.getDate().toString());

					Long currDay = keyValue;
					Date lastDate = entity.getDate();
					Date firstDate = entity.getDate();

					for (Entity currEntity : list) {
						Integer currentDay = currEntity.getDate().getDate() * 31;
						Integer currMonth = (currEntity.getDate().getMonth() + 1) * 12;
						Long checkVal = (long) (currentDay + currMonth);
						if (checkVal.equals(currDay)) {

							if (currEntity.getDate().getTime() >= lastDate.getTime()) {
								lastDate = currEntity.getDate();

							}
							if (currEntity.getDate().getTime() < firstDate.getTime()) {
								firstDate = currEntity.getDate();
								System.out.println("Lesser! don know why?!");
								System.out.println("---------------------------->>");
							}

						}
					}
					Times dates = new Times(firstDate, lastDate);
					result.put(keyValue, dates);
					System.out.println("Period: " + firstDate.toString() + " --> " + lastDate.toString());
					System.out.println("Absolute Period: " + firstDate.getTime() + " --> " + lastDate.getTime());
					System.out.println("---------------------->>");

				}
			}
			return result;
		}
		case HOUR: {

			HashMap<Long, Times> result = new HashMap<Long, Times>();
			for (Entity entity : list) {
				Integer day = entity.getDate().getDate() * 31;
				Integer month = (entity.getDate().getMonth() + 1) * 12;
				Integer hour = (entity.getDate().getHours() + 1) * 24;
				Long keyValue = (long) (hour + day + month);
				if (!result.containsKey(keyValue)) {
					System.out.println("New hour! " + entity.getDate().toString());
					Long currHour = keyValue;
					Date lastDate = entity.getDate();
					Date firstDate = entity.getDate();

					for (Entity currEntity : list) {
						Integer currDay = currEntity.getDate().getDate() * 31;
						Integer currMonth = (currEntity.getDate().getMonth() + 1) * 12;
						Integer currentHour = (currEntity.getDate().getHours() + 1) * 24;
						Long checkVal = (long) (currentHour + currDay + currMonth);
						if (checkVal.equals(currHour)) {
							if (currEntity.getDate().getTime() >= lastDate.getTime()) {
								lastDate = currEntity.getDate();
							}
							if (currEntity.getDate().getTime() < firstDate.getTime()) {
								firstDate = currEntity.getDate();
							}
						}
					}
					Times dates = new Times(firstDate, lastDate);
					result.put(currHour, dates);
					System.out.println("Period: " + firstDate.toString() + " --> " + lastDate.toString());
					System.out.println("Absolute Period: " + firstDate.getTime() + " --> " + lastDate.getTime());
					System.out.println("---------------------->>");
				}
			}
			return result;
		}

		}
		return null;
	}

	public HashMap<Long, Times> splitDates(Long customPeriodInMilis) {

		HashMap<Long, Times> result = new HashMap<Long, Times>();
		ArrayList<Date> dateList = new ArrayList<Date>();

		for (Entity entity : list) {
			dateList.add(entity.getDate());
		}

		// Sorting
		Collections.sort(dateList, new Comparator<Date>() {
			@Override
			public int compare(Date o1, Date o2) {
				// TODO Auto-generated method stub
				Long obj1 = o1.getTime();
				Long obj2 = o2.getTime();
				return obj1.compareTo(obj2);
			}
		});

		Date firstDateOfPeriod = dateList.get(0);
		Date lastDateOfPeriod = null;

		Long checkDate = firstDateOfPeriod.getTime() + customPeriodInMilis;

		for (int i = 1; i < dateList.size(); i++) {
			if (dateList.get(i).getTime() >= checkDate) {

				lastDateOfPeriod = dateList.get(i);
				Times time = new Times(firstDateOfPeriod, lastDateOfPeriod);
				result.put((long) i, time);

				firstDateOfPeriod = dateList.get(i);
				lastDateOfPeriod = null;
				checkDate = firstDateOfPeriod.getTime() + customPeriodInMilis;

			}
		}

		if (lastDateOfPeriod == null) {
			lastDateOfPeriod = dateList.get(dateList.size() - 1);
			Times time = new Times(firstDateOfPeriod, lastDateOfPeriod);
			result.put((long) dateList.size(), time);
		}

		return result;
	}

	public Integer getPeriodsCount(Period period) {
		return this.splitDates(period).size();
	}

	public abstract HashMap<Times, HashMap<Long, Double>> getVolumeTotals(Period period);

}

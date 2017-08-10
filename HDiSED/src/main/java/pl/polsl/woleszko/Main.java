package pl.polsl.woleszko;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pl.woleszko.polsl.maths.impl.NozzleDataExtractor;
import pl.woleszko.polsl.model.entities.Entity;
import pl.woleszko.polsl.model.entities.NozzleMeasuresEntity;
import pl.woleszko.polsl.model.impl.FileAccessorCSV;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		NozzleDataExtractor extractor = new NozzleDataExtractor();
		HashMap<Long, Double> list = extractor.getTotals();
		
		for(Long key : list.keySet()) {
			System.out.println(list.get(key));
		}
	}


}
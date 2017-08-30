package pl.meho.fuel.model;

import java.util.Date;

import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

import lombok.Getter;
import lombok.Setter;
import pl.woleszko.polsl.model.entities.Entity;

@Getter
@Setter
@CsvRecord(separator = ";")
public class TankEnt extends Entity{

	@DataField(pos = 1, pattern="yyyy-MM-dd HH:mm:ss")
	private Date date;
	
	@DataField(pos = 2)
	private Integer locId;
	
	@DataField(pos = 3)
	private Integer meterId;

	@DataField(pos = 4)
	private Integer tankId;
	
	@DataField(pos = 5)
	private String fuelLvl;
	
	@DataField(pos = 6)
	private String fuelVol;
	
	@DataField(pos = 7)
	private Integer fuelTemp;
	
}

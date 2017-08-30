package pl.meho.fuel.model;

import java.util.Date;

import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.woleszko.polsl.model.entities.Entity;

@Getter
@Setter
@ToString
@NoArgsConstructor
@CsvRecord(separator = ";")
public class NozzleEnt extends Entity {
	
	@DataField(pos = 1, pattern="yyyy-MM-dd HH:mm:ss")
	private Date date;
	
	@DataField(pos = 2)
	private int locId;
	
	@DataField(pos = 3)
	private Integer nozId;

	@DataField(pos = 4)
	private Integer tankId;
	
	@DataField(pos = 5, decimalSeparator=",")
	private Double literCounter;
	
	@DataField(pos = 6, decimalSeparator=",")
	private Double totalCounter;
	
	@DataField(pos = 7)
	private short status;

}

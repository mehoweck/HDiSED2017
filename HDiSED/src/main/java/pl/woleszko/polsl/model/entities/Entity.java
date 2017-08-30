package pl.woleszko.polsl.model.entities;

import java.util.Date;

import org.apache.camel.dataformat.bindy.annotation.CsvRecord;

import lombok.Getter;

@Getter
@CsvRecord(separator = ";")
public abstract class Entity implements Comparable<Entity> {
    public static Integer globalRowNum;
    
    public Entity() {
        rowNum = ++globalRowNum;
    }
    
    public Integer rowNum; 
    
	public abstract Date getDate();
	public abstract Integer getTankId();

    @Override
    public int compareTo(Entity o) {
        return (o instanceof Entity)
//         time measurement has too low density to by reliable                 
//         ? this.getDate().compareTo(((Entity)o).getDate()) 
         ? this.getRowNum().compareTo(o.getRowNum()) 
         : 0;
    }
}

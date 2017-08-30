package pl.meho.fuel;

import java.util.Collections;
import java.util.List;

import pl.meho.fuel.data.DataExplorer;
import pl.woleszko.polsl.model.entities.Entity;

public class DataResolver<T extends Entity> {
    private List<T> list;
    

    public DataResolver(DataExplorer<T> explorer) {
        list = explorer.getEntities();

        Collections.sort(list);
    }  

	protected List<T> getElements() {
		return list;
	}
	
    @SuppressWarnings("unchecked")
    protected List<Entity> getEntities() {
        return (List<Entity>)list;
    }
	

}

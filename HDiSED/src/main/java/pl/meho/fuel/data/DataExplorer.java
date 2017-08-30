package pl.meho.fuel.data;

import java.util.List;

import pl.woleszko.polsl.model.entities.Entity;

public interface DataExplorer<T extends Entity> {

    public abstract void exploreData(Class<T> type);
    public abstract List<T> getEntities();

}

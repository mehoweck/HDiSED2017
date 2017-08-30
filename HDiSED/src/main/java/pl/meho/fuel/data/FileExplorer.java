package pl.meho.fuel.data;

import java.io.File;
import java.net.URL;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import pl.woleszko.polsl.model.entities.Entity;

@Slf4j
@Getter
public abstract class FileExplorer<T extends Entity> implements DataExplorer<T> {
    
    private String filePath;
    private String fileName;    
    
    public FileExplorer(Class<T> type, String fileName) {
        File file = new File(fileName);

        // try to find in resources if there is no in default path
        file = file.exists() ? file : getFileFromResources(fileName);

        this.fileName = file.getName();
        this.filePath = file.getParentFile().getAbsolutePath();
        exploreData(type);
    }
    
    protected File getFileFromResources(String filename) {
        ClassLoader classLoader = getClass().getClassLoader();
        URL url = classLoader.getResource(filename);
        if (url == null) 
            log.error("File {} does not exists!", filename);
        return new File(url.getFile());
    }
}

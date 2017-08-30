package pl.meho.fuel.data;

import java.util.List;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.main.Main;

import lombok.extern.slf4j.Slf4j;
import pl.meho.fuel.hack.BindyCsvDataFormat;
import pl.woleszko.polsl.model.entities.Entity;
import pl.woleszko.polsl.model.impl.CSVHandler;

@Slf4j
public class ZipExplorer<T extends Entity> extends FileExplorer<T> {
    
    private CSVHandler<T> csvHandler;
    private Main mainContext; 
    
    public ZipExplorer(Class<T> type, String filename) {
        super(type, filename);
    }
    
    public List<T> getEntities() {
        return csvHandler.getList();
    }    

    @Override
    public void exploreData(Class<T> type) {
        csvHandler = new CSVHandler<>();

        mainContext = new Main();
        mainContext.bind("csvHandlerBean", csvHandler);
        mainContext.bind("terminate", this);
        mainContext.addRouteBuilder(getRoute(type));
        
        try {
            mainContext.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected RouteBuilder getRoute(Class<T> type) {
        return new RouteBuilder() {

            @Override
            public void configure() throws Exception {
                BindyCsvDataFormat bindy = new BindyCsvDataFormat(type);
                bindy.setLocale("pl_PL");

                getContext().disableJMX();
                
                from("file:" + getFilePath() + "?fileName=" + getFileName() + "&delay=1000&noop=true")
                    .id("ZipExplorerRoute")
                    .unmarshal().zipFile()
                    .unmarshal(bindy)
		            .to("bean:csvHandlerBean?method=csvHandler")
		            .to("bean:terminate?method=close");
            }
        };
    }

    public void close() {
        log.info("Processing of the file:{} has been completed.", getFileName());
        mainContext.completed();
    }
	
	


}

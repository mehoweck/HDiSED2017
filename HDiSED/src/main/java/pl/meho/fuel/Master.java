package pl.meho.fuel;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import lombok.extern.slf4j.Slf4j;
import pl.meho.fuel.data.CsvExplorer;
import pl.meho.fuel.data.FileExplorer;
import pl.meho.fuel.data.ZipExplorer;
import pl.meho.fuel.model.NozzleEnt;
import pl.meho.fuel.model.RefuelEnt;
import pl.meho.fuel.model.TankEnt;
import pl.woleszko.polsl.model.entities.Entity;

@Slf4j
public class Master {

    public static void main(String[] args) throws ParseException {
        setLogPattern(
                "%gray(%d{HH:mm:ss.SSS}) %boldYellow(%-5level) %-10.-10thread %gray(|) %cyan(%-36.36logger{36} %gray(%4line |) %msg%n");
        
        setLogLevel("pl.meho.fuel", Level.DEBUG);
        setLogLevel("pl.meho.fuel.hack", Level.WARN);
        setLogLevel("org.apache.camel", Level.WARN);
        
        Master app = new Master();
        app.run();
    };
    
    
    private void run() {    
        
//        NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);
//        Number number = format.parse("1,234");
//        double d = number.doubleValue();
        
        //=========================================================================
        
        FileExplorer<RefuelEnt> refuelData = new CsvExplorer<>(RefuelEnt.class, "refuel.csv");
        FileExplorer<NozzleEnt> nozzleData = new ZipExplorer<>(NozzleEnt.class, "nozzleMeasures.zip");
        FileExplorer<TankEnt> tankData = new ZipExplorer<>(TankEnt.class, "tankMeasures.zip");

        DataResolver<TankEnt> tanks = new DataResolver<>(tankData);
        DataResolver<RefuelEnt> refuels = new DataResolver<>(refuelData);
        DataResolver<NozzleEnt> nozzles = new DataResolver<>(nozzleData);
        
        List<NozzleEnt> nozzlist = nozzles.getElements();

        Collections.sort(nozzlist);
        

        Map<Integer, List<NozzleEnt>> nozzidlist;
        
        nozzidlist = nozzlist.stream()
                .collect(Collectors.groupingBy(NozzleEnt::getNozId));
                
        nozzidlist.entrySet()
            .stream()

            .forEach(element-> {
                System.out.println(element);
                element.getValue().stream()
                    .filter(nozz -> filter(nozz))
                    .forEach(nozz -> System.out.println(nozz));
                });
        
//        .forEach(elem -> { 
//                elem.getValue().stream()
//                    .filter(foo -> filter(foo));
//                  });
        
        nozzlist = nozzlist.stream().filter((elem) -> elem.getTankId() == 4 && elem.getNozId() == 24).collect(Collectors.toList());
        
               
        reductio(nozzlist);        
        
        List<Entity> listall = new ArrayList<>(); 
        listall.addAll(tanks.getElements());
        listall.addAll(refuels.getElements());
        listall.addAll(nozzles.getElements());
        
        listall = listall.stream().filter(entity -> entity.getTankId() == 1).sorted().collect(Collectors.toList());
        
        
        
        
//        DataResolver<NozzleMeasuresEntity> dh = new DataResolver<>(nozzleAccessor);

    }


    private boolean filter(NozzleEnt nozz) {
        System.out.println(nozz);
        return true;
    }

    private static void reductio(List<NozzleEnt> nozzlist) {
        List<NozzleEnt> newlist = new ArrayList<>();
        log.info(">=> == rows before reduction: {}", nozzlist.size());
        NozzleEnt prev = null;
        for(NozzleEnt nozz : nozzlist) {
            if (prev != null) {
                //zmiana z 0 na 1 - odłożenie pistoletu
                Boolean isRefuel = (nozz.getStatus() - prev.getStatus() < 0);
                Double delta = nozz.getTotalCounter() - prev.getTotalCounter();
                
                if (delta != 0)  {
                    if (isRefuel)
                        nozz.setLiterCounter(delta);
                    newlist.add(nozz);
                }
            }            
            prev = nozz;
        }
        nozzlist = newlist;
        log.info(">=> == rows after reduction: {}", nozzlist.size());
    }

    private void aa(Entry<Long, List<NozzleEnt>> mapelem) {
        System.out.println(mapelem);
    }

    private void doSomething(List<NozzleEnt> list) {
        //
    }

    private static void setLogPattern(String pattern) {
        //custom pattern
        Logger rootLogger = (Logger)LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        LoggerContext loggerContext = rootLogger.getLoggerContext();
        // we are not interested in auto-configuration
        loggerContext.reset();

        PatternLayoutEncoder encoder = new PatternLayoutEncoder();
        encoder.setContext(loggerContext);
//        encoder.setPattern("%-5level [%thread]: %message%n");
        encoder.setPattern(pattern);
        
        
        encoder.start();
        
        ConsoleAppender<ILoggingEvent> appender = new ConsoleAppender<ILoggingEvent>();
        appender.setContext(loggerContext);
        appender.setEncoder(encoder); 
        appender.start();

        rootLogger.addAppender(appender);        
    }
    
    private static void setLogLevel(String logger, Level level) {
        ((Logger) LoggerFactory.getLogger(logger)).setLevel(level);
    }

}

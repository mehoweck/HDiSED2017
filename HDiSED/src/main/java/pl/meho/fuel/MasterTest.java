package pl.meho.fuel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class MasterTest {
    List<Foo> list = new ArrayList<>();
    
    
    public static void main(String[] args) {
        MasterTest app = new MasterTest();
        app.run();
    }
    
    
    private void run() {
        int row = 0;
        list.add(new Foo(1, 1, 1));
        list.add(new Foo(2, 1, 1));
        list.add(new Foo(3, 2, 1));
        list.add(new Foo(4, 3, 0));
        list.add(new Foo(5, 3, 1));
        list.add(new Foo(6, 4, 1));
        list.add(new Foo(7, 4, 2));
        list.add(new Foo(8, 4, 2));
        list.add(new Foo(9, 4, 2));
        list.add(new Foo(10, 5, 2));
        list.add(new Foo(11, 5, 2));
        list.add(new Foo(12, 5, 2));
        
//        list = list.stream().filter(new Predicate<Entity>() {}))
        
        Map<Integer, List<Foo>> map;
        
//        map = list.stream().collect(Collectors.groupingBy(Foo::getState));
        map = list.stream().collect(Collectors.groupingBy(x -> x.getState()));

        map.entrySet()
           .stream()

           .forEach(element-> {
               System.out.println(element);
               element.getValue().stream().forEach(foo -> System.out.println(foo));
               });
               
//           .forEach(System.out::println);

        
        
//        System.out.println(map);
        
    }

    

}

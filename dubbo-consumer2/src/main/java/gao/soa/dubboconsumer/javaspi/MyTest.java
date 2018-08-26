package gao.soa.dubboconsumer.javaspi;

import java.util.Iterator;
import java.util.ServiceLoader;

public class MyTest {
    
    public static void main(String[] args) {
        ServiceLoader<Log> loginstances = ServiceLoader.load(Log.class);
        Iterator<Log> iterator = loginstances.iterator();
        while (iterator.hasNext()) {
            iterator.next().debug();
        }
    }
    
}

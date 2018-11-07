import java.awt.*;
import java.util.HashMap;

public class AnyProvider implements DataProvider {

    private HashMap map = new HashMap<>();
    // Let's keep our own count of the number of misses, just to make sure it matches
    private int numberOfMisses = 0;


    public Object get (Object key) {
        numberOfMisses++;
        return map.get(key);
    }

    public int getNumberOfMisses() {
        return numberOfMisses;
    }

    public void addObject(Object key, Object obj)
    {
        if(map.containsKey(key)) {
            System.err.println("Key was already used, ignoring new Object: " + obj.toString());
        }
        else
        {
            map.put(key, obj);
        }
    }
}
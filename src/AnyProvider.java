import java.util.HashMap;

public class AnyProvider implements DataProvider {

    private HashMap map = new HashMap<>();
    // Let's keep our own count of the number of misses, just to make sure it matches
    private int numberOfMisses = 0;


    public Object get (Object key) {
        return map.get(key);
    }

    public int getNumberOfMisses() {
        return numberOfMisses;
    }

    public void addObject(Object key, Object obj)
    {
        numberOfMisses++;
        if(map.containsKey(key)) {
            throw new Error("Key already exists in the hashmap");
        }
        else
        {
            map.put(key, obj);
        }
    }
}
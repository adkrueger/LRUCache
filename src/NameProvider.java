import java.util.HashMap;

/**
 * An implementation of a class that utilizes a hashMap to store data
 */
public class NameProvider implements DataProvider<Integer, String> {

    private HashMap<Integer, String> map = new HashMap<>();

    /**
     * Returns the key value of a hashMap
     * @param key the key
     * @return the key value
     */
    public String get (Integer key) {
        return map.get(key);
    }

    /**
     * Adds a key and value to the hashMap
     * @param key the key value of the data
     * @param name the corresponding name data
     */
    public void addName(Integer key, String name)
    {
        if(map.containsKey(key)) {
            throw new Error("Key already exists in the hashMap");
        }
        else
        {
            map.put(key, name);
        }
    }
}
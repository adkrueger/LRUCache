import java.util.HashMap;

public class NameProvider implements DataProvider<Integer, String> {

    private HashMap<Integer, String> map = new HashMap<>();

    public String get (Integer key) {
        return map.get(key);
    }

    public void addName(Integer key, String name)
    {
        if(map.containsKey(key)) {
            throw new Error("Key already exists in the hashmap");
        }
        else
        {
            map.put(key, name);
        }
    }
}
import java.util.HashMap;

public class NameProvider implements DataProvider<String, String> {

    private HashMap<String, String> map = new HashMap<>();

    public String get (String key) {
        return map.get(key);
    }

    public void addName(String key, String name)
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
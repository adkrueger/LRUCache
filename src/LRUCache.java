import java.util.HashMap;

/**
 * An implementation of <tt>Cache</tt> that uses a least-recently-used (LRU)
 * eviction policy.
 */
public class LRUCache<T, U> implements Cache<T, U> {

	private int _numberOfMisses = 0;
	private int _capacity;
	private DataProvider<T, U> _dataProvider;

	private HashMap<T, Node> _map = new HashMap<>();

	private Node _head = null;
	private Node _tail = null;


    /**
     * An implementation of a Node that contains a key and corresponding data
     */
	private class Node {
        Node _next = null;
        U _data;
        T _key;

        private Node (U data, T key) {
            _data = data;
            _key = key;
        }
    }

	/**
	 * @param provider the data provider to consult for a cache miss
	 * @param capacity the exact number of (key,value) pairs to store in the cache
	 */
	LRUCache (DataProvider<T, U> provider, int capacity) {
	    _capacity = capacity;
        _dataProvider = provider;
	}

	/**
	 * Returns the value associated with the specified key.
	 * @param key the key
	 * @return the value associated with the key
	 */
	public U get (T key) {
	    // if it's in the cache
            // if the capacity isn't at maximum -> add a node with that data as the tail
            // if it is at capacity, remove the head and add the new head
        if (_map.containsKey(key)) {
            Node n = _map.get(key);
            return n._data;
        }
        else {
            _numberOfMisses += 1;
            U data = _dataProvider.get(key);
            Node node = new Node(data, key);
            if(_map.size() == 0)
            {
                _head = node;
                _tail = node;
            }
            else if(_map.size() < _capacity)
            {
                _tail._next = node;
                _tail = node;
            }
            else
            {
                _map.remove(_head._key);
                _head = _head._next;
                _tail._next = node;
                _tail = node;
            }
            _map.put(key, node);
            return data;
        }
	}

	/**
	 * Returns the number of cache misses since the object's instantiation.
	 * @return the number of cache misses since the object's instantiation.
	 */
	public int getNumMisses () {
		return _numberOfMisses;
	}
}

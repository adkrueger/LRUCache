import java.util.HashMap;

/**
 * An implementation of <tt>Cache</tt> that uses a least-recently-used (LRU)
 * eviction policy.
 */
public class LRUCache<T, U> implements Cache<T, U> {

	private int _numberOfMisses = 0;
	private int _capacity = 0;
	private int _numberOfNodes = 0;
	private DataProvider<T, U> _dataProvider;

	private HashMap<T, Node> _map = new HashMap<T, Node>();

	Node _head = null;
	Node _tail = null;

	class Node {
        Node _next = null;
        U _data;

        public Node (U data) {
            _data = data;
        }
    }

	/**
	 * @param provider the data provider to consult for a cache miss
	 * @param capacity the exact number of (key,value) pairs to store in the cache
	 */
	public LRUCache (DataProvider<T, U> provider, int capacity) {
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
            U data = _dataProvider.get(key);
            _map.remove(key);
            Node node = new Node(data);
            _map.put(key, node);
            if(_numberOfNodes == 0)
            {
                _head = node;
                _tail = node;
                System.out.println("Number of nodes was 0");
                _numberOfNodes += 1;
            }
            if(_numberOfNodes < _capacity && _numberOfNodes > 0)
            {
                _tail._next = node;
                _tail = node;
                _numberOfNodes += 1;
                System.out.println("Number of nodes was less than capacity");
            }
            else
            {
                _head = _head._next;
                _tail = node;
                _tail._next = null;
                System.out.println("Number of nodes was equal to capacity");
            }
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

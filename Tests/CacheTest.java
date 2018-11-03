import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Code to test an <tt>LRUCache</tt> implementation.
 */
public class CacheTest {
	@Test
	public void testGetNumMisses () {
		NameProvider nameProvider = new NameProvider();
		nameProvider.addName(0, "Bob");
		nameProvider.addName(1, "Bill");
		nameProvider.addName(2, "Joe");

		DataProvider<Integer,String> provider = nameProvider; // Need to instantiate an actual DataProvider
		Cache<Integer,String> cache = new LRUCache<Integer,String>(provider, 5);

		assertEquals(cache.getNumMisses(), 0);
		assertEquals(cache.get(0), "Bob");
		assertEquals(cache.get(1), "Bill");
		assertEquals(cache.get(2), "Joe");
		// Should have 3 misses by now
		assertEquals(cache.getNumMisses(), 3);
        assertEquals(cache.get(0), "Bob");
        assertEquals(cache.get(1), "Bill");
        assertEquals(cache.get(2), "Joe");
        // Now there should still be 3 misses, as everything was in the cache
        assertEquals(cache.getNumMisses(), 3);
	}

	@Test
    public void testLRU () {
        NameProvider nameProvider = new NameProvider();
        nameProvider.addName(4, "Bob");
        nameProvider.addName(0, "Bill");
        nameProvider.addName(3, "Joe");
        nameProvider.addName(1, "Zim");
        nameProvider.addName(2, "Brandon");

        DataProvider<Integer,String> provider = nameProvider;
        Cache<Integer,String> cache = new LRUCache<Integer,String>(provider, 4); // Only four capacity

        assertEquals(cache.getNumMisses(), 0);
        assertEquals(cache.get(0), "Bill");
        assertEquals(cache.get(1), "Zim");
        assertEquals(cache.get(2), "Brandon");
        assertEquals(cache.get(3), "Joe");
        assertEquals(cache.get(4), "Bob");
        assertEquals(cache.get(0), "Bill");
        assertEquals(cache.get(1), "Zim");
        assertEquals(cache.get(4), "Bob"); // Shouldn't increase number of misses, as it's still in the cache
        assertEquals(cache.getNumMisses(), 7); // A total of 7 misses
    }
}

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
		Cache<Integer,String> cache = new LRUCache<>(provider, 5);

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
    public void testGetNumMisses2 () {
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

    @Test
    public void testGetNumMisses3() {
        NameProvider nameProvider = new NameProvider();
        nameProvider.addName(4, "Bob");
        nameProvider.addName(0, "Bill");
        nameProvider.addName(3, "Joe");
        nameProvider.addName(1, "Zim");
        nameProvider.addName(2, "Brandon");

        Cache<Integer, String> cache = new LRUCache<> (nameProvider, 3);

        assertEquals(cache.get(0), "Bill");   // misses += 1
        cache.get(0);
        cache.get(0);
        assertEquals(cache.get(0), "Bill");
        // cache should still only have one person, so misses still = 1
        assertEquals(cache.getNumMisses(), 1);
        cache.get(1);   // new addition, so misses += 1
        assertEquals(cache.getNumMisses(), 2);

    }

    @Test
    public void testGetNumMisses4() {
        NameProvider nameProvider = new NameProvider();
        nameProvider.addName(4, "Bob");
        nameProvider.addName(0, "Bill");
        nameProvider.addName(3, "Joe");
        nameProvider.addName(1, "Zim");
        nameProvider.addName(2, "Brandon");

        Cache<Integer, String> cache = new LRUCache<> (nameProvider, 3);

        assertEquals(cache.get(4), "Bob");
        assertEquals(cache.get(3), "Joe");
        assertEquals(cache.get(0), "Bill");
        // cache is now full, misses = 3
        assertEquals(cache.get(2), "Brandon");   // Adds Brandon to the cache and removes Bob, misses = 4
        assertEquals(cache.get(4), "Bob");       // Should add 1 to misses because Bob wasn't in cache anymore
        assertEquals(cache.getNumMisses(), 5);
    }

    @Test
    public void testLRUCache () {
        NameProvider nameProvider = new NameProvider();
        nameProvider.addName(0, "Liz");
        nameProvider.addName(1, "Sadie");
        nameProvider.addName(2, "Bailey");

        DataProvider<Integer,String> provider = nameProvider;
        Cache<Integer,String> cache = new LRUCache<>(provider, 3);

        assertNull(cache.get(3));     // There is no 4th key, so should return null
    }
}

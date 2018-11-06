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
		nameProvider.addName("bo", "Bob");
		nameProvider.addName("bi", "Bill");
		nameProvider.addName("jo", "Joe");

		DataProvider<String,String> provider = nameProvider; // Need to instantiate an actual DataProvider
		Cache<String,String> cache = new LRUCache<>(provider,5);

		assertEquals(cache.getNumMisses(), 0);
		assertEquals(cache.get("bo"), "Bob");
		assertEquals(cache.get("bi"), "Bill");
		assertEquals(cache.get("jo"), "Joe");
		// Should have 3 misses by now
		assertEquals(cache.getNumMisses(), 3);
        assertEquals(cache.get("bo"), "Bob");
        assertEquals(cache.get("bi"), "Bill");
        assertEquals(cache.get("jo"), "Joe");
        // Now there should still be 3 misses, as everything was in the cache
        assertEquals(cache.getNumMisses(), 3);
	}

	@Test
    public void testGetNumMisses2 () {
        NameProvider nameProvider = new NameProvider();
        nameProvider.addName("bo", "Bob");
        nameProvider.addName("bi", "Bill");
        nameProvider.addName("jo", "Joe");
        nameProvider.addName("zi", "Zim");
        nameProvider.addName("br", "Brandon");

        DataProvider<String,String> provider = nameProvider;
        Cache<String,String> cache = new LRUCache<>(provider, 4); // Only four capacity

        assertEquals(cache.getNumMisses(), 0);
        assertEquals(cache.get("bi"), "Bill");
        assertEquals(cache.get("zi"), "Zim");
        assertEquals(cache.get("br"), "Brandon");
        assertEquals(cache.get("jo"), "Joe");
        assertEquals(cache.get("bo"), "Bob");
        assertEquals(cache.get("bi"), "Bill");
        assertEquals(cache.get("zi"), "Zim");
        assertEquals(cache.get("bo"), "Bob"); // Shouldn't increase number of misses, as it's still in the cache
        assertEquals(cache.getNumMisses(), 7); // A total of 7 misses
    }

    @Test
    public void testGetNumMisses3() {
        NameProvider nameProvider = new NameProvider();
        nameProvider.addName("bo", "Bob");
        nameProvider.addName("bi", "Bill");
        nameProvider.addName("jo", "Joe");
        nameProvider.addName("zi", "Zim");
        nameProvider.addName("br", "Brandon");

        Cache<String, String> cache = new LRUCache<> (nameProvider, 3);

        assertEquals(cache.get("bi"), "Bill");   // misses += 1
        cache.get("bi");
        cache.get("bi");
        assertEquals(cache.get("bi"), "Bill");
        // cache should still only have one person, so misses still = 1
        assertEquals(cache.getNumMisses(), 1);
        cache.get("bo");   // new addition, so misses += 1
        assertEquals(cache.getNumMisses(), 2);

    }

    @Test
    public void testGetNumMisses4() {
        NameProvider nameProvider = new NameProvider();
        nameProvider.addName("bo", "Bob");
        nameProvider.addName("bi", "Bill");
        nameProvider.addName("jo", "Joe");
        nameProvider.addName("zi", "Zim");
        nameProvider.addName("br", "Brandon");

        Cache<String, String> cache = new LRUCache<> (nameProvider, 3);

        assertEquals(cache.get("bo"), "Bob");
        assertEquals(cache.get("jo"), "Joe");
        assertEquals(cache.get("bi"), "Bill");
        // cache is now full, misses = 3
        assertEquals(cache.get("br"), "Brandon");   // Adds Brandon to the cache and removes Bob, misses = 4
        assertEquals(cache.get("bo"), "Bob");       // Should add 1 to misses because Bob wasn't in cache anymore
        assertEquals(cache.getNumMisses(), 5);
    }

    @Test
    public void testLRUCache () {
        NameProvider nameProvider = new NameProvider();
        nameProvider.addName("li", "Liz");
        nameProvider.addName("sa", "Sadie");
        nameProvider.addName("ba", "Bailey");

        DataProvider<String,String> provider = nameProvider;
        Cache<String,String> cache = new LRUCache<>(provider, 3);

        assertNull(cache.get("zz"));     // There is no 4th key, so should return null
    }
}

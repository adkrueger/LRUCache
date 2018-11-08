import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Code to test an <tt>LRUCache</tt> implementation.
 */
public class CacheTest {

    // Test with names and keys as first two characters
	@Test
	public void testGetNumMisses () {
		AnyProvider anyProvider = new AnyProvider();
		anyProvider.addObject("bo", "Bob");
		anyProvider.addObject("jo", "Joe");
		anyProvider.addObject("bi", "Bill");
        anyProvider.addObject("no", "Noel");

		Cache<String,String> cache = new LRUCache<String, String>(anyProvider, 5);

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
        assertEquals(cache.getNumMisses(), anyProvider.getNumberOfMisses());
        assertEquals(cache.get("no"), "Noel");
        assertEquals(cache.getNumMisses(), 4);
        assertEquals(cache.getNumMisses(), anyProvider.getNumberOfMisses());
	}

    // Test with names and keys as first two characters
	@Test
    public void testGetNumMisses2 () {
        AnyProvider anyProvider = new AnyProvider();

        anyProvider.addObject("bo", "Bob");
        anyProvider.addObject("bi", "Bill");
        anyProvider.addObject("jo", "Joe");
        anyProvider.addObject("zi", "Zim");
        anyProvider.addObject("br", "Brandon");

        Cache<String,String> cache = new LRUCache<String, String>(anyProvider, 4); // Only four capacity

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

    // Test with names and keys as first two characters
    @Test
    public void testGetNumMisses3() {
        AnyProvider anyProvider = new AnyProvider();
        anyProvider.addObject("bo", "Bob");
        anyProvider.addObject("bi", "Bill");
        anyProvider.addObject("jo", "Joe");
        anyProvider.addObject("zi", "Zim");
        anyProvider.addObject("br", "Brandon");

        Cache<String, String> cache = new LRUCache<String, String> (anyProvider, 3);

        assertEquals(cache.get("bi"), "Bill");   // misses += 1
        cache.get("bi");
        cache.get("bi");
        assertEquals(cache.get("bi"), "Bill");
        // cache should still only have one person, so misses still = 1
        assertEquals(cache.getNumMisses(), 1);
        cache.get("bo");   // new addition, so misses += 1
        assertEquals(cache.getNumMisses(), 2);

    }

    // Test with names and keys as first two characters
    @Test
    public void testGetNumMisses4() {
        AnyProvider anyProvider = new AnyProvider();
        anyProvider.addObject("bo", "Bob");
        anyProvider.addObject("bi", "Bill");
        anyProvider.addObject("jo", "Joe");
        anyProvider.addObject("zi", "Zim");
        anyProvider.addObject("br", "Brandon");

        Cache<String, String> cache = new LRUCache<String, String> (anyProvider, 3);

        assertEquals(cache.get("bo"), "Bob");
        assertEquals(cache.get("jo"), "Joe");
        assertEquals(cache.get("bi"), "Bill");
        // cache is now full, misses = 3
        assertEquals(cache.get("br"), "Brandon");   // Adds Brandon to the cache and removes Bob, misses = 4
        assertEquals(cache.get("bo"), "Bob");       // Should add 1 to misses because Bob wasn't in cache anymore
        assertEquals(cache.getNumMisses(), 5);
    }

    // Test with names and keys as first two characters
    @Test
    public void testLRUCache () {
        AnyProvider anyProvider = new AnyProvider();
        anyProvider.addObject("li", "Liz");
        anyProvider.addObject("sa", "Sadie");
        anyProvider.addObject("ba", "Bailey");

        Cache<String,String> cache = new LRUCache<String, String>(anyProvider, 3);

        assertNull(cache.get("zz"));     // There is no 4th key, so should return null
    }

    // Some more tests, now with random Object types
    @Test
    public void testBrutal () {
	    AnyProvider anyProvider = new AnyProvider();
	    anyProvider.addObject(null, 1.0f / 0.0f);
        anyProvider.addObject(1.0f / 0.0f, "To Infinity...");

	    Cache<Object, Object> cache = new LRUCache<Object, Object>(anyProvider, 2);

	    assertEquals(cache.get(null), 1.0f / 0.0f);
	    assertEquals(cache.get(1.0f / 0.0f), "To Infinity...");
    }

    @Test
    public void testBrutal2 () {
        AnyProvider anyProvider = new AnyProvider();
        anyProvider.addObject("FB", "FaceBook");
        anyProvider.addObject("Ea", "Electronic Arts");

        Cache cache = new LRUCache<Object, Object>(anyProvider, 2);

        assertEquals(cache.get("Ea"), "Electronic Arts");
        assertEquals(cache.get("FB"), "FaceBook");
        assertEquals(cache.getNumMisses(), 2);
        assertEquals(cache.getNumMisses(), anyProvider.getNumberOfMisses());

        // Same, no extra misses
        assertEquals(cache.get("Ea"), "Electronic Arts");
        assertEquals(cache.get("FB"), "FaceBook");
        assertEquals(cache.getNumMisses(), 2);
        assertEquals(cache.getNumMisses(), anyProvider.getNumberOfMisses());
    }

    // Let's see if the LRUCache's numberOfMisses matches the number of times it access the data provider
    @Test
    public void testProviderNumMisses () {
        AnyProvider anyProvider = new AnyProvider();
        anyProvider.addObject(true, anyProvider); // It's technically an Object
        Object randomObject = new Object(); // Create an object to test with
        anyProvider.addObject(randomObject, null);

        Cache<Object, Object> cache = new LRUCache<Object, Object>(anyProvider, 2);

        assertEquals(cache.get(true), anyProvider);
        assertNull(cache.get(randomObject));

        // Now to check numberOfMisses
        assertEquals(cache.getNumMisses(), anyProvider.getNumberOfMisses());

        // Number of misses should stay at 2
        assertEquals(cache.get(true), anyProvider);
        assertNull(cache.get(randomObject));

        assertEquals(cache.getNumMisses(), 2);
        assertEquals(cache.getNumMisses(), anyProvider.getNumberOfMisses());
    }

    @Test
    public void testCacheInACache () {
        AnyProvider innerProvider = new AnyProvider();
        innerProvider.addObject("inner", "Inner Cache");

        Cache<Object, Object> innerCache = new LRUCache<Object, Object>(innerProvider, 1);

        AnyProvider outerProvider = new AnyProvider();
        outerProvider.addObject("Inner Cache", "Outer Cache");

        Cache<Object, Object> outerCache = new LRUCache<Object, Object>(outerProvider, 1);

        assertEquals(outerCache.get(innerCache.get("inner")), "Outer Cache");
        assertEquals(outerCache.getNumMisses() + innerCache.getNumMisses(), 2);
        assertEquals(innerProvider.getNumberOfMisses() + outerProvider.getNumberOfMisses(), 2);
	}
}

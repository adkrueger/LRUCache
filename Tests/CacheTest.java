import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Code to test an <tt>LRUCache</tt> implementation.
 */
public class CacheTest {
	@Test
	public void leastRecentlyUsedIsCorrect () {
		NameProvider nameProvider = new NameProvider();
		nameProvider.addName(0, "Bob");
		nameProvider.addName(1, "Bill");
		nameProvider.addName(2, "Joe");

		DataProvider<Integer,String> provider = nameProvider; // Need to instantiate an actual DataProvider
		Cache<Integer,String> cache = new LRUCache<Integer,String>(provider, 5);

		System.out.println(cache.get(0));
		System.out.println(cache.get(1));
		System.out.println(cache.get(2));
	}
}

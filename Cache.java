/**
 * @author TylerBrown380 This program is used to simulate a single or double
 *         cache system.
 * @param <T>
 */
public class Cache<T> {

	// instance variables
	private IUDoubleLinkedList<T> cache1, cache2;
	private int size1, size2;
	private int cache1Adds = 0;
	private int cache2Adds = 0;
	private int cache1Misses = 0;
	private int cache2Misses = 0;
	private int cache1Matches = 0;
	private int cache2Matches = 0;

	/**
	 * Constructor for a single size Cache
	 * 
	 * @param size
	 * @param file
	 */
	public Cache(int size) {
		this.size1 = size;
		size2 = 0;
		cache1 = new IUDoubleLinkedList<T>();
	}

	/**
	 * Constructor for a 2 Level Cache
	 * 
	 * @param size1
	 * @param size2
	 * @param file
	 */
	public Cache(int size1, int size2) {
		this.size1 = size1;
		this.size2 = size2;
		cache1 = new IUDoubleLinkedList<T>();
		cache2 = new IUDoubleLinkedList<T>();
	}

	/**
	 * Method used to check if an element is present in the Cache(s)
	 * If object is found, it will move it to the top of cache(s)
	 * 
	 * @param element
	 * @return element if it is present in the cache,
	 * @return null if element is not present
	 */
	public T getObj(T element) {
		if (cache1.contains(element)) {
			cache1.remove(element);
			cache1.addToFront(element);
			cache1Matches++;
			if(size2 != 0) {
				cache2.remove(element);
				cache2.addToFront(element);
			}
			return element;
		} else {
			cache1Misses++;
			if (size2 != 0) {
				if (cache2.contains(element)) {
					cache2Matches++;
					cache2.remove(element);
					cache2.addToFront(element);
					if ((cache1.size() + 1) > size1) {
						cache1.removeLast();
						cache1.addToFront(element);
					} else {
						cache1.addToFront(element);
					}
					return element;
				} else {
					cache2Misses++;
				}
			}
		}
		return null;
	}

	/**
	 * Method used to add elements to the Cache(s)
	 * 
	 * @param element
	 */
	public void addObj(T element) {
		if ((cache1.size() + 1) > size1) {
			cache1.removeLast();
			cache1.addToFront(element);
			cache1Adds++;
		} else {
			cache1.addToFront(element);
			cache1Adds++;
		}
		if (size2 != 0) {
			if (cache2.size() + 1 > size2) {
				cache2.removeLast();
				cache2.addToFront(element);
				cache2Adds++;
			} else {
				cache2.addToFront(element);
				cache2Adds++;
			}
		}
	}

	/**
	 * Method used to remove an object from the cache.
	 * 
	 * @param element
	 */
	public void removeObj(T element) {
		if (cache1.size() > 0) {
			cache1.remove(element);
		}
		if (size2 != 0) {
			if (cache2.size() > 0) {
				cache2.remove(element);
			}
		}
	}

	/**
	 * Method used to clear the caches
	 */
	public void clearCache() {
		while (cache1.size() > 0) {
			cache1.removeLast();
		}
		if (size2 != 0) {
			while (cache2.size() > 0) {
				cache2.removeLast();
			}
		}
	}

	/**
	 * @return number of elements added to cache 1
	 */
	public int getCache1Adds() {
		return cache1Adds + getCache1Matches() + getCache2Matches();
	}

	/**
	 * @return number of elements added to cache 2
	 */
	public int getCache2Adds() {
		return cache2Adds + getCache2Matches();
	}

	/**
	 * @return number of times element was not found in cache 1
	 */
	public int getCache1Misses() {
		return cache1Misses;
	}

	/**
	 * @return number of times element was not found in cache 2
	 */
	public int getCache2Misses() {
		return cache2Misses;
	}

	/**
	 * @return number of times element matched an element in cache 1
	 */
	public int getCache1Matches() {
		return cache1Matches;
	}

	/**
	 * @return number of times element matched an element in cache 2
	 */
	public int getCache2Matches() {
		return cache2Matches;
	}

	/**
	 * @return Global hit ratio
	 */
	public double getGlobalHitRatio() {
		return (1.0 * cache1Matches + cache2Matches) / (getCache1Adds());
	}

	/**
	 * @return First Cache hit ratio
	 */
	public double getFirstLevelHitRatio() {
		return (1.0 * cache1Matches / getCache1Adds() * 1.0);
	}

	/**
	 * @return Second Cache hit ratio
	 */
	public double getSecondLevelHitRatio() {
		return (1.0 * cache2Matches / (1.0 * getCache2Adds()));
	}

}
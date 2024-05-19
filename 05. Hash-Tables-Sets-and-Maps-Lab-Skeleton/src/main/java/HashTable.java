import java.util.LinkedList;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

public class HashTable<K, V> implements Iterable<KeyValue<K, V>> {
	private static final int INITIAL_CAPACITY = 16;
	private static final double LOAD_FACTOR = 0.80d;

	private LinkedList<KeyValue<K, V>>[] slots;
		
	private int count, capacity;
	
    public HashTable() {
    	this(INITIAL_CAPACITY);
    }

    public HashTable(int capacity) {
    	slots = new LinkedList[capacity];
    	
    	this.count = 0;
    	this.capacity = capacity;
    }

    public void add(K key, V value) {
        this.growIfNeeded();
        
        int index = findSlotNumber(key);
        
        LinkedList<KeyValue<K, V>> slot = this.slots[index];
        
        if(slot == null) {
        	slot = new LinkedList<KeyValue<K, V>>();
        }
        
        for(KeyValue<K, V> element : slot) {
        	if(element.getKey().equals(key)) {
        		throw new IllegalArgumentException("Key already exists: " + key);
        	}
        }
        
        KeyValue<K, V> toInsert = new KeyValue<>(key, value);
        
        slot.add(toInsert);
        
        this.slots[index] = slot;
        this.count++;
    }

    private int findSlotNumber(K key) {
        return Math.abs(key.hashCode()) % this.capacity;
    }

    private void growIfNeeded() {
    	if((double) (this.size() + 1) / this.capacity() > LOAD_FACTOR) {
    		grow();
    	}
    }

    private void grow() {
    	capacity *= 2;
    	HashTable<K, V> newTable = new HashTable<>(this.capacity);
    	
    	for(KeyValue<K, V> oldPair : this) {
    		newTable.add(oldPair.getKey(), oldPair.getValue());
    	}
    	
    	this.slots = newTable.slots;
    	
    }

    public int size() {
    	return this.count;
    }

    public int capacity() {
       	return this.capacity;
    }

    public boolean addOrReplace(K key, V value) {
        throw new UnsupportedOperationException();
    }

    public V get(K key) {
    	KeyValue<K, V> toGet = this.find(key);
    	
    	if(toGet == null) {
    		throw new IllegalArgumentException();
    	}
    	
        return find(key).getValue();
    }

    public KeyValue<K, V> find(K key) {
        int currentIndex = findSlotNumber(key);
        
        if(slots[currentIndex] == null) return null;
        
        for(KeyValue<K, V> element : slots[currentIndex]) {
        	if(element.getKey().equals(key)) return element;
        }
        
        return null;
    }

    public boolean containsKey(K key) {
        return find(key) != null;
    }

    public boolean remove(K key) {
        throw new UnsupportedOperationException();
    }

    public void clear() {
        throw new UnsupportedOperationException();
    }

    public Iterable<K> keys() {
        throw new UnsupportedOperationException();
    }

    public Iterable<V> values() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<KeyValue<K, V>> iterator() {
        return new Iterator<KeyValue<K,V>>() {
        	int passedCount = 0;
        	int currentIndex = 0;
        	
        	Deque<KeyValue<K, V>> elements = new ArrayDeque<>();
        	
			@Override
			public boolean hasNext() {
				// TODO Auto-generated method stub
				return passedCount < count && elements.isEmpty();
			}

			@Override
			public KeyValue<K, V> next() {
				if(!elements.isEmpty()) {
					return elements.poll();
				}
				
				while(slots[currentIndex] == null && currentIndex < capacity) {
						currentIndex++;
				}
				
				elements.addAll(slots[currentIndex]);
					
				passedCount += slots[currentIndex].size();
				currentIndex++;
				
				return elements.poll();
			}
		};
    }
}

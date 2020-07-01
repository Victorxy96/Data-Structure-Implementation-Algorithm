/**
 * HashMap class implemented by linked list
 * @param <K> key
 * @param <V> value
 */
public class MyHashMap<K,V> {
    /**
     * Size of the hashMap
     */
    private int size;
    /**
     * Linked list array
     */
    private MyLinkedList[] myLinkedLists;

    /**
     * Constructor method
     * @param size size of the linked list
     */
    public MyHashMap(int size) {
        this.size = size;
        myLinkedLists = new MyLinkedList[size];
        for(int i=0;i<size;i++) {
            myLinkedLists[i] = new MyLinkedList();
        }
    }

    /**
     * Put new k-v pairs to hashMap
     * @param key key
     * @param value value
     */
    public void put(K key, V value) {
        int index = getIndex(key);
        KVPairs newPair = new KVPairs(key, value);
        myLinkedLists[index].addAtTail(newPair);
    }

    /**
     * Get the value by the input key in the hashMap
     * @param key key
     * @return value by the input key in the hashMap
     */
    public V get(K key) {
        int index = getIndex(key);
        if(index >= size || index< 0) {
            return null;
        }
        MyLinkedList curLinkedList = myLinkedLists[index];
        curLinkedList.reset();
        while (curLinkedList.hasNext()) {
            KVPairs e = (KVPairs) curLinkedList.next();
            if(e.getKey().equals(key)) {
                return (V) e.getVal();
            }
        }
        return null;
    }

    /**
     * Check whether the hashMap contains the key
     * @param key key
     * @return whether the hashMap contains the key
     */
    public boolean containsKey(K key) {
        int index = getIndex(key);
        if(index >= size || index< 0) {
            return  false;
        }
        MyLinkedList curLinkedList = myLinkedLists[index];
        curLinkedList.reset();
        while (curLinkedList.hasNext()) {
            KVPairs e = (KVPairs) curLinkedList.next();
            if(e.getKey().equals(key)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get the index of the key of the linked list array
     * @param key key
     * @return the index of the key of the linked list array
     */
    public int getIndex(K key) {
        return (key.hashCode() & 0x7FFFFFFF) % size;
    }

    /**
     * Get the size of linked list array
     * @return size of linked list array
     */
    public int getSize(){
        return size;
    }
}

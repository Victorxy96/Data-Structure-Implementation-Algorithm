/**
 * Key value pairs
 * @param <K> key
 * @param <V> value
 */
public class KVPairs<K,V> {
    /**
     * Key
     */
    private K key;
    /**
     * Value
     */
    private V val;

    /**
     * Constructor method
     * @param key key
     * @param val value
     */
    public KVPairs(K key, V val) {
        this.key = key;
        this.val = val;
    }

    /**
     * Get key
     * @return key
     */
    public K getKey() {
        return key;
    }

    /**
     * Set key
     * @param key key
     */
    public void setKey(K key) {
        this.key = key;
    }

    /**
     * Get value
     * @return value
     */
    public V getVal() {
        return val;
    }

    /**
     * Set value
     * @param val value
     */
    public void setVal(V val) {
        this.val = val;
    }
}

package ds;
import java.util.Iterator;

public interface Dictionary<K, V> extends Iterable<K> {
    V put(K key, V value);
    V get(K key);
    V remove(K key);
    boolean contains(K key);
    int size();
    boolean isEmpty();
    void clear();
    Iterator<K> iterator();
}
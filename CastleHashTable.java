import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

/**
 * @param <K>
 * @param <V>
 */
public class CastleHashTable<K, V> implements CastleHashMap<K, V> {

    /**
     *
     */
    private static final int CAPACITY = 20;

    /**
     *
     */
    private static final int LOAD_THRESHOLD = 17;

    /**
     *
     */
    private LinkedList<ListEntry<K, V>>[] table;

    /**
     *
     */
    private int numKeys;


    /**
     * @param numKeys
     */
    @SuppressWarnings("unchecked")
    public CastleHashTable(int numKeys) {
        table = new LinkedList[numKeys];
    }

    /**
     * @param key
     * @param value
     * @return
     */
    @Override
    public V put(K key, V value) {
        int index = key.hashCode() % table.length;
        if (index < 0)
            index += table.length;
        if (table[index] == null)
            table[index] = new LinkedList<>();
        for (ListEntry<K, V> next : table[index]) {
            if (next.key.equals(key)) {
                V old = next.value;
                next.setValue(value);
                return old;
            }
        }
        table[index].add(new ListEntry<>(key, value));
        numKeys++;
        return null;
    }

    /**
     * @param key
     * @return
     */
    @Override
    public V get(Object key) {
        int index = key.hashCode() % table.length;
        if (index < 0) index += table.length;
        if (table[index] == null) return null;
        LinkedList<ListEntry<K, V>> ref = table[index];
        for (ListEntry<K, V> a : ref) {
            if (a.key.equals(key))
                return a.getValue();
        }
        return null;
    }

    /**
     * @param key
     * @return
     */
    @Override
    public V remove(Object key) {
        int index = key.hashCode() % table.length;
        if (index < 0) index += table.length;
        if (table[index] == null) return null;
        Iterator<ListEntry<K, V>> iter = table[index].iterator();
        while (iter.hasNext()) {
            ListEntry<K, V> next = iter.next();
            if (next.key.equals(key)) {
                V toReturn = next.value;
                if (table[index].size() == 1)
                    table[index] = null;
                else
                    iter.remove();
                return toReturn;
            }
        }
        return null;
    }

    /**
     * @return
     */
    @Override
    public int size() {
        return table.length;
    }

    /**
     * @return
     */
    @Override
    public boolean isEmpty() {
        return table == null;
    }

    /**
     * @param rooms
     * @return
     */
    public String displayAll(Room[] rooms) {
        StringBuilder sb = new StringBuilder();
        for (Room room : rooms) {
            sb.append(playersInRoom(room)).append("\n");
        }
        return sb.toString();
    }

    /**
     * @param r
     * @return
     */
    public String playersInRoom(Room r) {
        int index = r.getLocation();
        String room = String.format("%-30s", r.toString());
        StringBuilder sb = new StringBuilder(room);
        if (table[index] == null)
            sb.append("Empty  ");
        else {
            LinkedList<ListEntry<K, V>> list = table[index];
            for (ListEntry<K, V> entry : list) {
                sb.append(entry.getValue().toString()).append(", ");
            }
        }
        return sb.toString().substring(0, sb.toString().length() - 2);
    }


    /**
     * @param <K>
     * @param <V>
     */
    private static class ListEntry<K, V> implements Map.Entry<K, V> {

        /**
         *
         */
        private K key;

        /**
         *
         */
        private V value;

        /**
         * @param key
         * @param value
         */
        public ListEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        /**
         * @return
         */
        @Override
        public V getValue() {
            return value;
        }

        /**
         * @return
         */
        @Override
        public K getKey() {
            return key;
        }

        /**
         * @param v
         * @return
         */
        @Override
        public V setValue(V v) {
            V old = value;
            value = v;
            return old;
        }
    }
}

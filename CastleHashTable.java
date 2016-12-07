import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

@SuppressWarnings("WeakerAccess")
public class CastleHashTable<K, V> implements CastleHashMap<K, V> {

    private static final int CAPACITY = 10;
    private static final int LOAD_THRESHOLD = 200;
    private LinkedList<ListEntry<K, V>>[] table;
    private int numKeys;

    @SuppressWarnings("unchecked")
    public CastleHashTable(int numKeys) {
        table = new LinkedList[numKeys];
    }

    /**
     * TODO: Should be able to search linked list by key
     * TODO: --> what to do when the index already corresponds to the key?
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

    @Override
    public V remove(Object key) {
        int index = key.hashCode() % table.length;
        if (index < 0) index += table.length;
        if (table[index] == null) return null;
//        TODO: Should be equivalent
//        for (ListEntry<K, V> next : table[index]) {
//            if (next.key.equals(key)) {
//                V toReturn = next.value;
//                if (table[index].size() == 1)
//                    table[index] = null;
//                else
//                    table[index].remove(next);
//                return toReturn;
//            }
//        }
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

    @Override
    public int size() {
        return table.length;
    }

    // TODO
    @Override
    public boolean isEmpty() {
        return table == null;
    }

    public String displayAll() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < table.length; i++) {
            if (table[i] == null)
                sb.append(i).append(" null\n");
            else {
                LinkedList<ListEntry<K, V>> list = table[i];
                for (ListEntry<K, V> entry : list) {
                    sb.append(entry.getValue().toString()).append(" ");
                }
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    public String playersInRoom(Room r) {
        int index = r.getLocation();
        StringBuilder sb = new StringBuilder();
        if (table[index] == null)
            //sb.append(r.toString()).append("is empty");
            sb.append("Empty");
        else {
            LinkedList<ListEntry<K, V>> list = table[index];
            for (ListEntry<K, V> entry : list) {
                sb.append(entry.getValue().toString()).append(" ");
            }
        }
        return sb.toString();
    }


    private static class ListEntry<K, V> implements Map.Entry<K, V> {
        private K key;
        private V value;

        public ListEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V setValue(V v) {
            V old = value;
            value = v;
            return old;
        }
    }
}

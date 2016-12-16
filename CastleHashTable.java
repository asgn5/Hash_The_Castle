import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

/**
 * Chris Montani, Richard Kent, Rodrigo Choque
 *
 *
 * @param <K> The object corresponding to the Key
 * @param <V> The object corresponding to the Value
 */
public class CastleHashTable< K, V > implements CastleHashMap< K, V > {

    /**
     * The capacity of the table
     */
    private static final int CAPACITY = 20;

    /**
     * The table's threshold
     */
    private static final int LOAD_THRESHOLD = 17;

    /**
     * The representation of the table as
     * key value pairs of list entries
     */
    private LinkedList< ListEntry< K, V > >[] table;

    /**
     * The number of keys in the hash-table
     */
    private int numKeys;


    /**
     * Initializes a Hash table with an initail size
     * instantiates a list of key-value entries.
     * @param numKeys The intital size of the table
     */
    @SuppressWarnings( "unchecked" )
    public CastleHashTable( int numKeys ) {
        this.numKeys = numKeys;
        table = new LinkedList[ numKeys ];
    }


    /**
     * @param key Returns the value associated with the key
     * @return the value
     */
    @Override
    public V get( Object key ) {
        int index = key.hashCode() % table.length;
        if ( index < 0 ) index += table.length;
        if ( table[ index ] == null ) return null;
        LinkedList< ListEntry< K, V > > ref = table[ index ];
        for ( ListEntry< K, V > a : ref ) {
            if ( a.key.equals( key ) )
                return a.getValue();
        }
        return null;
    }


    /**
     * @return Whether the table is empty or not
     */
    @Override
    public boolean isEmpty() {
        return table == null;
    }


    /**
     * @param key The key to put
     * @param value the value that corresponds to the key
     * @return The old value that corresponded to that key
     */
    @Override
    public V put( K key, V value ) {
        int index = key.hashCode() % table.length;
        if ( index < 0 )
            index += table.length;
        if ( table[ index ] == null )
            table[ index ] = new LinkedList<>();
        for ( ListEntry< K, V > next : table[ index ] ) {
            if ( next.key.equals( key ) ) {
                V old = next.value;
                next.setValue( value );
                return old;
            }
        }
        table[ index ].add( new ListEntry<>( key, value ) );
        numKeys++;
        return null;
    }


    /**
     * @param key Removes the specified key from the table
     * @return The value associated
     */
    @Override
    public V remove( Object key ) {
        int index = key.hashCode() % table.length;
        if ( index < 0 ) index += table.length;
        if ( table[ index ] == null ) return null;
        Iterator< ListEntry< K, V > > iter = table[ index ].iterator();
        while ( iter.hasNext() ) {
            ListEntry< K, V > next = iter.next();
            if ( next.key.equals( key ) ) {
                V toReturn = next.value;
                if ( table[ index ].size() == 1 )
                    table[ index ] = null;
                else
                    iter.remove();
                return toReturn;
            }
        }
        return null;
    }


    /**
     * @return The size of the table
     */
    @Override
    public int size() {
        return table.length;
    }


    /**
     * Specializes this class with a one off method to display all the players in a particular room
     * @param rooms The rooms to search in
     * @return
     */
    public String displayAll( Room[] rooms ) {
        StringBuilder sb = new StringBuilder();
        for ( Room room : rooms ) {
            sb.append( playersInRoom( room ) ).append( "\n" );
        }
        return sb.toString();
    }


    /**
     * @param r The room to search in
     * @return The players in the specified room.
     */
    public String playersInRoom( Room r ) {
        int index = r.getLocation();
        String room = String.format( "%-30s", r.toString() );
        StringBuilder sb = new StringBuilder( "\n" + room + ":" );
        if ( table[ index ] == null )
            sb.append( "\n\tEmpty" );
        else {
            LinkedList< ListEntry< K, V > > list = table[ index ];
            for ( ListEntry< K, V > entry : list ) {
                sb.append( "\n" ).append( entry.getValue().toString() );
            }
        }
        sb.append( "\n" );
//        return sb.toString().substring(0, sb.toString().length() - 2);
        return sb.toString();
    }


    /**
     * This inner class holds the key value pairs for the hash-table
     * @param <K> The generic object representing the key
     * @param <V> The generic object representing the value
     */
    private static class ListEntry< K, V > implements Map.Entry< K, V > {

        /**
         * The Key reference
         */
        private K key;

        /**
         * The generic value reference
         */
        private V value;


        /**
         * Initializes the Entry with a distinct key-value pair
         * @param key the Key
         * @param value the corresponding value
         */
        public ListEntry( K key, V value ) {
            this.key = key;
            this.value = value;
        }


        /**
         * @return the entries key
         */
        @Override
        public K getKey() {
            return key;
        }


        /**
         * @return The corresponding value
         */
        @Override
        public V getValue() {
            return value;
        }


        /**
         * @param v
         * @return
         */
        @Override
        public V setValue( V v ) {
            V old = value;
            value = v;
            return old;
        }
    }
}

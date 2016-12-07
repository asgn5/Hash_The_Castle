public class TesterCastleHashTable {
    public static void main(String[] args) {
        CastleHashTable<Player, String> t = new CastleHashTable<>(5);

        Room[] rooms = new Room[5];
        Player[] player = new Player[15];
        String[] names = {
            "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight",
            "Nine", "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen"
        };

        for (int i = 0; i < rooms.length; i++) {
            rooms[i] = new Room("Room"+(i+1), i);
        }

        for (int j = 0; j < player.length; j++) {
            player[j] = new Player(names[j], j*10);
            t.put(player[j], player[j].toString());
        }
        System.out.println(t.displayAll());

        // Put players in the next room
        for (int k = 0; k < player.length; k++) {
            if (k%2 != 0) {
                t.remove(player[k]);
                // adds 1 to "where", which is also the key
                player[k].setWhere(player[k].hashCode()+1);
                t.put(player[k], player[k].toString());
                System.out.println(t.displayAll());
            } else if (k%3 == 0) {
                t.remove(player[k]);
                player[k].setWhere(player[k].hashCode()+2);
                t.put(player[k], player[k].toString());
                System.out.println(t.displayAll());
            }
        }

        t.remove(player[4]);
        player[4].setWhere(4);
        t.put(player[4], player[4].toString());

        StringBuilder stringBuilder = new StringBuilder();
        for (int l = 0; l < rooms.length; l++) {
            stringBuilder.append(rooms[l].getRoomName())
                .append(" ")
                .append(t.playersInRoom(rooms[l]))
                .append("\n");
        }

        System.out.println(stringBuilder.toString());

    }

}

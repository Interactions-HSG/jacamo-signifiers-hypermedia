package maze;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Util {

    public static Map<Integer, List<Integer>> getStandardMovements(){
        Map<Integer, List<Integer>> movements = new HashMap<>();
        Integer[] array = {6, 2, null, null};
        movements.put(1, Arrays.asList(array));
        array = new Integer[]{5, 3, null, 1};
        movements.put(2, Arrays.asList(array));
        array = new Integer[]{4, null, null, 2};
        movements.put(3, Arrays.asList(array));
        array = new Integer[]{9, null, 3, 5};
        movements.put(4, Arrays.asList(array));
        array = new Integer[]{8, 4, 2, 6};
        movements.put(5, Arrays.asList(array));
        array = new Integer[]{7, 5, 1, null};
        movements.put(6, Arrays.asList(array));
        array = new Integer[]{null, 8, 6, null};
        movements.put(7, Arrays.asList(array));
        array = new Integer[]{null, 9, 5, 7};
        movements.put(8, Arrays.asList(array));
        array = new Integer[]{null, null, 4, 8};
        movements.put(9, Arrays.asList(array));
        return movements;

    }

    /**
     *
     * @param fromRoomNb
     * @param toRoomNb
     * @return -1 if the two rooms are not adjacent and the direction to go from the first room to the other room if they are adjacent.
     */
    public static int getDirection(int fromRoomNb, int toRoomNb){
        int d = -1;
        Map<Integer, List<Integer>> movements = getStandardMovements();
        List<Integer> list = movements.get(fromRoomNb);
        if (list.contains(toRoomNb)){
            d = list.indexOf(toRoomNb);
        }
        return d;
    }

    public static boolean isMovementPossible(int fromRoomNb, int toRoomNb){
        boolean b = false;
        Map<Integer, List<Integer>> movements = getStandardMovements();
        List<Integer> list = movements.get(fromRoomNb);
        if (list.contains(toRoomNb)){
            b = true;
        }
        return b;

    }

    public static int getGeneralDirection(int fromRoomNb, int toRoomNb){
        int d1 = getDirection(fromRoomNb, toRoomNb);
        int d = d1;
        if (d == -1){
            if (fromRoomNb < toRoomNb){
                int newToRoomNb = plus(fromRoomNb);
                d = getDirection(fromRoomNb, newToRoomNb);
            }
            else if (fromRoomNb > toRoomNb){
                int newToRoomNb = minus(fromRoomNb);
                d = getDirection(fromRoomNb, newToRoomNb);
            }

        }
        return d;
    }

    public static int getGeneralDirection2(int fromRoomNb, int toRoomNb){
        boolean b = isMovementPossible(fromRoomNb, toRoomNb);
        int d = getGeneralDirection(fromRoomNb, toRoomNb);
        if (!b){
            if (fromRoomNb < toRoomNb){
                int newToRoomNb = plus(fromRoomNb);
                d = getDirection(fromRoomNb, newToRoomNb);
            }
            else if (fromRoomNb > toRoomNb){
                int newToRoomNb = minus(fromRoomNb);
                d = getDirection(fromRoomNb, newToRoomNb);
            }

        }
        return d;

    }

    public static int plus(int room){
        int newRoom = 9;
        if (room != 9){
            newRoom = room + 1;
        }
        return newRoom;


    }

    public static int minus(int room){
        int newRoom = 1;
        if (room != 1){
            newRoom = room - 1;
        }
        return newRoom;


    }

}

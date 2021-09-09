package maze;

import org.eclipse.rdf4j.model.*;
import org.eclipse.rdf4j.model.util.Models;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.hyperagents.hypermedia.HypermediaOntology;
import org.hyperagents.plan.Plan;
import org.hyperagents.util.RDFS;
import org.hyperagents.util.State;

import java.util.*;

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

    public static boolean hasPrecondition(State precondition, int room){
        boolean b = false;
        Value v = precondition.getStatementList().get(0).getObject();
        System.out.println("value: "+v);
        List<IRI> rooms = getRooms();
        String vString = v.toString();
        System.out.println("vString: "+vString);
        String roomString = rooms.get(room-1).toString();
        System.out.println("roomString: "+roomString);
        if (vString.equals(roomString)){
            System.out.println("vString equals roomString");
            b = true;
        }
        return b;
    }

    public static List<IRI> getRooms(){
        List<IRI> rooms = new ArrayList<>();
        rooms.add(RDFS.rdf.createIRI(MazeOntology.room1));
        rooms.add(RDFS.rdf.createIRI(MazeOntology.room2));
        rooms.add(RDFS.rdf.createIRI(MazeOntology.room3));
        rooms.add(RDFS.rdf.createIRI(MazeOntology.room4));
        rooms.add(RDFS.rdf.createIRI(MazeOntology.room5));
        rooms.add(RDFS.rdf.createIRI(MazeOntology.room6));
        rooms.add(RDFS.rdf.createIRI(MazeOntology.room7));
        rooms.add(RDFS.rdf.createIRI(MazeOntology.room8));
        rooms.add(RDFS.rdf.createIRI(MazeOntology.room9));
        return rooms;

    }

    public static boolean isHypermediaPlan(Plan p){
        boolean b = false;
        Model m = p.getModel();
        Set<Resource> types = Models.objectResources(m.filter(p.getId(), RDF.TYPE, null));
        for (Resource type : types){
            if (type.equals(HypermediaOntology.HypermediaPlan)){
                System.out.println("is hypermedia plan");
                b = true;
            }
        }
        return b;
    }

    public static String createPayloadFromInteger(int n){
        List<Integer> list = new ArrayList<>();
        list.add(n);
        String s = list.toString();
        return s;
    }

}

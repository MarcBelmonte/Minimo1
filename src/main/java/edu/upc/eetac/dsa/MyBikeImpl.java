package edu.upc.eetac.dsa;
import java.util.*;
import java.util.List;

import org.apache.log4j.Logger;

public class MyBikeImpl implements MyBike {
    //Llamamos al archivo de propiedades de LOG4J
    final static Logger log = Logger.getLogger(MyBikeImpl.class.getName());
    //Aplicamos la fachada usando el patrón Singleton
    private static MyBike instance;

    private List<Station> stations;
    private LinkedList<Bike> bikes;
    private HashMap<String,User> users;
    private LinkedList<Bike> listaBikes;
    private Queue<Bike> colaBikes = new LinkedList<>();

    public MyBikeImpl(){
        stations = new ArrayList<Station>();
        listaBikes = new LinkedList<Bike>();
        bikes = new LinkedList<>();
        users = new HashMap<>();
    }
    public static MyBike getInstance(){
        if(instance == null) instance = new MyBikeImpl();
        return instance;
    }

    public void addUser(String idUser, String name, String surname){
        logger.info("Añadiendo usuario");
        User user = new User(idUser,name,surname);
        users.put(idUser,user);

    }

    public void addStation(String idStation, String description, int max, double lat, double lon){
        Station sta = new Station(idStation,description,max,lat,lon);
        stations.add(sta);
    }
    public void addBike(String idBike, String description, double kms, String idStation) throws StationFullException, StationNotFoundException{
        for(Station s:stations){
            if(s.getIdStation().equals(idStation)){
                s.addBike(new Bike(idBike,description,kms));
            }
        }
    }

    @Override
    public List<Bike> bikesByStationOrderByKms(String idStation) throws StationNotFoundException {
        //Copiamos la lista
        List<Bike> lista = new ArrayList<>(this.bikes);

        //Aplicamos criterio de orden
        Collections.sort(lista, new Comparator<Bike>() {
            @Override
            public int compare(Bike o1, Bike o2) {
                return (int)(o1.getKms()-o2.getKms());
            }
        });
        return lista;
    }

    @Override
    public Bike getBike(String stationId, String userId) throws UserNotFoundException, StationNotFoundException {
        Station s = stations.get(stationId); // NO CONSIGO ENCONTRAR LA ESTACION A PARTIR DE SU ID, NO ENTIENDO
        Bike b = null;
        b = this.colaBikes.poll();
        return b;
    }

    @Override
    public List<Bike> bikesByUser(String userId) throws UserNotFoundException {
        User u = this.users.get(userId);
        LinkedList<Bike> listaBikes = new LinkedList<>();
        for (Bike b: bikes){
            if(bikes.c)
        }
        return null;
    }

    @Override
    public int numUsers() {
        log.info("Tamaño:"+ this.users.size());
        return users.size();
    }

    @Override
    public int numStations() {
        log.info("Tamaño:"+ this.stations.size());
        return stations.size();
    }

    @Override
    public int numBikes(String idStation) throws StationNotFoundException {
        return null;
    }

    @Override
    public void clear() {

    }
}

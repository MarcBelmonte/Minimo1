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
        log.info("Añadiendo usuario");
        User user = new User(idUser,name,surname);
        users.put(idUser,user);

    }

    public void addStation(String idStation, String description, int max, double lat, double lon){
        log.info("Añadiendo Estacion");
        Station sta = new Station(idStation,description,max,lat,lon);
        stations.add(sta);
    }
    public void addBike(String idBike, String description, double kms, String idStation) throws StationFullException, StationNotFoundException{
        log.info("Añadiendo Bici");
        for(Station sta:stations){
            if(sta.getIdStation().equals(idStation)){
                sta.addBike(new Bike(idBike,description,kms,idStation));
            }
        }
        log.info("Bici "+idBike+ "ha sido añadida en la estación" + idStation);
    }

    @Override
    public List<Bike> bikesByStationOrderByKms(String idStation) throws StationNotFoundException {

           //Copiamos la lista
           List<Bike> listaRet = new ArrayList<>(this.bikes);
           for (Station sta : stations) {
               if (sta.getIdStation().equals(idStation)) {
                   List<Bike> bikes = sta.getListaBikes();
                   for (Bike bike : bikes) {
                       listaRet.add(bike);
                   }
                   //Aplicamos criterio de orden
                   Collections.sort(listaRet, new Comparator<Bike>() {
                       @Override
                       public int compare(Bike o1, Bike o2) {
                           return (int) (o1.getKms() - o2.getKms());
                       }
                   });
               }
           }
           return listaRet;


    }

    @Override
    public Bike getBike(String stationId, String userId) throws UserNotFoundException, StationNotFoundException {
        log.info("Obteniendo bici de "+userId+ "de la estacion"+stationId);
        Bike b = null;
        for(Station sta: stations) {
            if (sta.getIdStation().equals(stationId)) {
                b = sta.getMyBikes().poll();
            }

        }
        return b;


    }

    @Override
    public List<Bike> bikesByUser(String userId) throws UserNotFoundException {
        log.info("Obteniendo bikes de:" + userId);

        LinkedList<Bike> listaBikes = new LinkedList<>();
        List<User> listUsers = new ArrayList<>();

        for (User u: listUsers){
            if(u.getIdUser().equals(userId)){
                listaBikes = u.getBikeList();
            }
        }
        return listaBikes;
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
        log.info("Obteniendo el numero de bicis de:"+ idStation);
        int num = 0;
        for(Station sta: stations){
            if(sta.getIdStation().equals(idStation)){
                num = sta.getListaBikes().size();
            }
        }
        log.info("El tamaño de la estación"+idStation+" es:"+num);
        return num;
    }

    @Override
    public void clear() {
        stations.clear();
        users.clear();
    }
}

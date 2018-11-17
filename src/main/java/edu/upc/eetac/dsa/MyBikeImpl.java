package edu.upc.eetac.dsa;
import java.util.*;
import java.util.List;

import org.apache.log4j.Logger;

public class MyBikeImpl implements MyBike {
    //Llamamos al archivo de propiedades de LOG4J
    final static Logger log = Logger.getLogger(MyBikeImpl.class.getName());
    //Aplicamos la fachada usando el patrón Singleton
    private static MyBike instance;

    private Station stations[];
    private int numstations;
    private LinkedList<Bike> bikes;
    private HashMap<String,User> users;
    private LinkedList<Bike> listaBikes;

    public MyBikeImpl(){
        stations = new Station[S];
        numstations=0;
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
       /* User user = new User(idUser,name,surname);
        users.put(idUser,user);*/
        this.users.put(idUser,new User(idUser, name, surname));

    }

    public void addStation(String idStation, String description, int max, double lat, double lon){
        log.info("Añadiendo Estacion");
        Station sta = new Station(idStation,description,max,lat,lon);
        this.stations[this.numstations] = sta;
        this.numstations++;
    }
    public void addBike(String idBike, String description, double kms, String idStation) throws StationFullException, StationNotFoundException{
        log.info("Añadiendo Bici");
        Station sta = null;
        Bike bike;
        for(int i = 0; i<this.numstations; i++) {
            if(idStation.equals(this.stations[i].idStation)){
                sta = this.stations[i];
            }
        }

        log.info("Station: " +sta);

        if(sta != null){
            LinkedList<Bike> bikes = sta.getMyBikes();
            int maxbikes = sta.getMax();
            if(bikes.size() < maxbikes){
                sta.addBike(new Bike(idBike, description, kms, idStation));
            }else{
                log.error("Estacion Llena!!!");
                throw new StationFullException();
            }
        }else{
            log.error("La estacion no existe");
            throw new StationNotFoundException();
        }

    }

    @Override
    public List<Bike> bikesByStationOrderByKms(String idStation) throws StationNotFoundException {

            //Copiamos la lista
            List<Bike> listaRet = new ArrayList<>(this.bikes);
            Station sta = null;
        for(int i=0; i<this.numstations;){
            if(idStation.equals(this.stations[i].idStation)){
                sta = this.stations[i];
            }
        }
        if(sta!=null) {
            Collections.sort(listaRet, new Comparator<Bike>() {
                @Override
                public int compare(Bike o1, Bike o2) {
                    return (int) (o1.getKms() - o2.getKms());
                }
            });
        }
            else{
                log.error("La estacion no existe");
                throw new StationNotFoundException();
            }
            log.info("Lista ordenada:" +listaRet);
            return listaRet;


    }

    @Override
    public Bike getBike(String stationId, String userId) throws UserNotFoundException, StationNotFoundException {

        Bike b = null;
        User u = this.users.get(userId);
        Station sta = null;
        for(int i=0; i<this.numstations;){
            if(stationId.equals(this.stations[i].idStation)){
                sta = this.stations[i];
            }
        }
        if(u!=null){
            if (sta!= null) {
                b = sta.getMyBikes().poll();
                u.addBike(b);
            }else{
                log.error("La estacion no existe");
                throw new StationNotFoundException();
            }

        }else{
            log.error("Usuario no existe");
            throw new UserNotFoundException();
        }
        log.info("Has sacado la bici:" +b);
        return b;

    }

    @Override
    public List<Bike> bikesByUser(String userId) throws UserNotFoundException {
        log.info("Obteniendo bikes de:" + userId);

        LinkedList<Bike> listaBikes = new LinkedList<>();
        List<User> listUsers = new ArrayList<>();
        User u = this.users.get(userId);
        if (u != null) {
            log.info("Nombre:" +u);
            listaBikes = u.getBikeList();
        }else{
            log.error("No existe el usuario");
            throw new UserNotFoundException();
        }
        log.info("Bicis del usuario" + listaBikes);
        return listaBikes;

    }

    @Override
    public int numUsers() {
        log.info("Tamaño:"+ this.users.size());
        return this.users.size();
    }

    @Override
    public int numStations() {
        log.info("Tamaño:"+ this.numstations);
        return this.numstations;
    }

    @Override
    public int numBikes(String idStation) throws StationNotFoundException {
        log.info("Obteniendo el numero de bicis de:"+ idStation);

        Station station = null;
        int numBikes;
        for(int i = 0; i<this.numStations(); i++) {
            if(idStation.equals(this.stations[i].idStation)){
                station = this.stations[i];
            }
        }

        log.info("idStation of this Station: " +station.idStation);

        if (station != null){
            numBikes = station.getMyBikes().size();
            log.info("Number of bikes of this station: " +numBikes);
        }
        else{
            log.error("The station doesn't exist");
            throw new StationNotFoundException();
        }

        return numBikes;
    }

    @Override
    public void clear() {
        stations = null;
        numstations =0;
        users.clear();
        bikes.clear();
    }
}

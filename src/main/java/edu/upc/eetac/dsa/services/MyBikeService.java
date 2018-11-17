package edu.upc.eetac.dsa.services;

import edu.upc.eetac.dsa.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;


    @Api(value="/bikes", description = "Endpoint to the Bike Service")
    @Path("/bikes")
    public class MyBikeService {

        final static Logger log = Logger.getLogger(MyBikeService.class.getName());
        private MyBike mb;

        public MyBikeService(){
            this.mb = MyBikeImpl.getInstance();

        }
        @GET
        @ApiOperation(value = "Obtener las bicis de un Usuario")
        @ApiResponses(value = {
                @ApiResponse(code=201, message ="Exito", response=Bike.class,responseContainer = "Lista de bicis"),
                @ApiResponse(code=404, message = "Usuario no encontrado")
        })
        @Path("/{user}")
        @Produces(MediaType.APPLICATION_JSON)
        public Response bikesByUser(@PathParam("user") String user) {
            List<Bike>  listabicis = new ArrayList<>();
            try {
                listabicis = this.mb.bikesByUser(user);
                GenericEntity<List<Bike>> entity = new GenericEntity<List<Bike>>(listabicis) {
                };
                return Response.status(201).entity(entity).build();
            }

            catch(Exception e){
                e.printStackTrace();
                return Response.status(404).build();
            }
        }
        @POST
        @ApiOperation(value = "Añadir Usuario", notes = "x")
        @ApiResponses(value = {
                @ApiResponse(code = 201, message = "Exito")
        })
        @Path("/adduser/")
        @Consumes(MediaType.APPLICATION_JSON)
        public Response addUser(User u){
            mb.addUser(u.getIdUser(),u.getName(),u.getSurname());
            return Response.status(201).build();
        }
        @POST
        @ApiOperation(value = "Añadir bici", notes = "x")
        @ApiResponses(value = {
                @ApiResponse(code = 201, message = "Exito")
        })
        @Path("/addBike")
        @Consumes(MediaType.APPLICATION_JSON)
        public Response addBike(Bike p,String stationId){
            try{
                mb.addBike(p.getBikeId(),p.getDescription(),p.getKms(),stationId);
                return Response.status(201).build();
            }catch (StationFullException e){
                e.printStackTrace();
                return Response.status(402).build();
            }catch (StationNotFoundException e){
                e.printStackTrace();
                return Response.status(404).build();
            }

        }
        @POST
        @ApiOperation(value = "Añadir estacion", notes = "x")
        @ApiResponses(value = {
                @ApiResponse(code = 201, message = "Exito")
        })
        @Path("/addStation")
        @Consumes(MediaType.APPLICATION_JSON)
        public Response addStation(Station p){
            this.mb.addStation(p.getIdStation(),p.getDescription(),p.getMax(),p.getLat(),p.getLon());
            return Response.status(201).build();
        }


    }


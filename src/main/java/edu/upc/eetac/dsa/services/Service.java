package edu.upc.eetac.dsa.services;
import io.swagger.annotations.Api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Root resource (exposed at "myresource" path)
 */
@Api(value = "/text", description = "Endpoint to Text Service")
@Path("text")
public class Service {



}

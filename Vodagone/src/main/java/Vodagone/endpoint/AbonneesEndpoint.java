package Vodagone.endpoint;

import Vodagone.dataaccesslayer.models.ShareAbonnementRequest;
import Vodagone.service.AbonneesService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
public class AbonneesEndpoint {
    @Inject
    private AbonneesService abonneesService;

    @Path("abonnees")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)

    public Response getAllAbonnees(@QueryParam("token") String token){
        return Response.status(Response.Status.OK).entity(abonneesService.getAllAbonnees(token)).build();
    }

    @Path("abonnees/{id}")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)

    public Response shareAbonnement(@PathParam("id") int id, @QueryParam("token") String token, ShareAbonnementRequest shareAbonnementRequest){
        abonneesService.shareAbonnement(id, shareAbonnementRequest.getId());
        return Response.status(Response.Status.OK).build();
    }
}

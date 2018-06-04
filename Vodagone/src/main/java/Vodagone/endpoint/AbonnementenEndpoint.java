package Vodagone.endpoint;

import Vodagone.dataaccesslayer.models.Abonnement;
import Vodagone.dataaccesslayer.models.UpgradeAbonnementRequest;
import Vodagone.service.AbonnementenService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status.OK;

@Path("/")
public class AbonnementenEndpoint {
    @Inject
    private AbonnementenService abonnementenService;

    @Path("abonnementen")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)

    public Response getAbonnementen(@QueryParam("token") String token){
        return Response.status(OK).entity(abonnementenService.getAbonnementenUser(token)).build();
    }

    @Path("abonnementen")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)

    public Response addAbonnement(@QueryParam("token") String token, Abonnement abonnement){
        abonnementenService.addAbonnementToUser(token, abonnement.getId(), abonnement.getAanbieder(), abonnement.getDienst());
        return getAbonnementen(token);
    }

    @Path("abonnementen/all")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)

    public Response getAllAbonnementen(@QueryParam("token") String token, @QueryParam("filter") String filter){
        return Response.status(Response.Status.OK).entity(abonnementenService.getAllAvailableAbonnementen(token, filter)).build();
    }

    @Path("abonnementen/{id}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)

    public Response getSpecificAbonnement(@PathParam("id") int id, @QueryParam("token") String token){
        return Response.status(Response.Status.OK).entity(abonnementenService.getSpecificAbonnement(id, token)).build();
    }

    @Path("abonnementen/{id}")
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)

    public Response terminateAbonnement(@PathParam("id") int id, @QueryParam("token") String token){
        abonnementenService.verwijderAbonnement(id, token);
        return getAbonnementen(token);
    }

    @Path("abonnementen/{id}")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)

    public Response upgradeAbonnement(@PathParam("id") int id, @QueryParam("token") String token, UpgradeAbonnementRequest upgradeAbonnementRequest){
        abonnementenService.upgradeAbonnement(id, token, upgradeAbonnementRequest.getVerdubbeling());
        return getAbonnementen(token);
    }
}

package Vodagone.endpoint;

import Vodagone.dataaccesslayer.models.LoginRequest;
import Vodagone.service.LoginService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
public class LoginEndpoint {
    @Inject
    private LoginService loginService;

    @Path("login")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)

    public Response Login(LoginRequest loginRequest){
        return Response.status(Response.Status.OK).entity(loginService.loginUser(loginRequest.getUser(), loginRequest.getPassword())).build();
    }
}

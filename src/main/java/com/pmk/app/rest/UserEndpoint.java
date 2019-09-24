package com.pmk.app.rest;

import com.pmk.app.model.User;
import com.pmk.app.service.UserService;

import javax.servlet.ServletContext;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/user")
public class UserEndpoint {
    @Context
    protected ServletContext servletContext;

    private UserService getUserService() {
        return (UserService) servletContext.getAttribute("userService");
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers(@HeaderParam("Authorization") String authorization) {
        //System.out.println("UserREST:getUsers: jwt : >"+authorization+"<");
        List<User> userLst = getUserService().getUsers(authorization);
        //System.out.println("UserREST:getSymbols: returning : >"+userLst.size()+"< users.");
        return Response.ok(userLst).build();
    }

    @GET
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("id") String id, @HeaderParam("Authorization") String authorization) {
        //System.out.println("UserREST:getUsers: jwt : >"+authorization+"<");
        return Response.ok(getUserService().getUser(id, authorization)).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addUser(User user, @HeaderParam("Authorization") String authorization) {
        boolean result = getUserService().addUser(user, authorization);
        //System.out.println("\nUserREST:addUser() Creating new use : "+user+" result : "+result);
        return Response.ok(result).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(User user, @HeaderParam("Authorization") String authorization) {
        boolean result = getUserService().updateUser(user, authorization);
        //System.out.println("UserREST:updateUser() : "+user+" result : " + result);
        return Response.ok(result).build();
    }

    @DELETE
    @Path("/{userId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteUser(@PathParam("userId") String userId, @HeaderParam("Authorization") String authorization) {
        boolean result = getUserService().deleteUser(userId, authorization);
        //System.out.println("\nUserREST:deleteUser() Deleting user id : "+userId+" result : "+result);
        return Response.ok(result).build();
    }

    @GET
    @Path("/search")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchUsers(@QueryParam("name") String name, @HeaderParam("Authorization") String authorization) {
        System.out.println("UsrREST:searchUsers name : "+name);
        return Response.ok(getUserService().searchUsers(name)).build();
    }
}

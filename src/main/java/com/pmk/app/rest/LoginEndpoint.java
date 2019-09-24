package com.pmk.app.rest;

import com.pmk.app.service.LoginService;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("/login")
public class LoginEndpoint {
    @Context
    protected UriInfo uriInfo;

    @Context
    protected ServletContext servletContext;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response authenticate(String credentials) {
        return Response.ok(getLoginService().authenticate(credentials,uriInfo.getAbsolutePath().toString())).build();
    }

    private LoginService getLoginService() {
        return (LoginService) servletContext.getAttribute("loginService");
    }
}

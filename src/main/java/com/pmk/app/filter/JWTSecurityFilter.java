package com.pmk.app.filter;

import com.pmk.app.util.TokenUtil;
import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

public class JWTSecurityFilter implements ContainerRequestFilter {

    @Override
    public ContainerRequest filter(ContainerRequest containerRequest) {
        String authorizationHeader = containerRequest.getHeaderValue("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer")) {
            URI uri = containerRequest.getRequestUri();

            // Allow certain requests
            if (uri.getRawPath().contains("/api"))
                return containerRequest;
            else
                throw new WebApplicationException(
                        Response.status(401).type(MediaType.APPLICATION_JSON).entity("Invalid authorization header. Authorization required!").build()
                );
        }

        else {
            String token = authorizationHeader.substring("Bearer".length()).trim();

            if (!TokenUtil.validate(token)) {
                throw new WebApplicationException(Response.Status.UNAUTHORIZED);
            }
        }

        return containerRequest;
    }
}

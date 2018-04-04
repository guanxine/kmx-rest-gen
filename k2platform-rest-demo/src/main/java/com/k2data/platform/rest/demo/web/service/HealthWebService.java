package com.k2data.platform.rest.demo.web.service;

import com.k2data.platform.rest.demo.entity.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by guanxine on 18-04-04.
 */
@Produces(MediaType.APPLICATION_JSON)
public class HealthWebService {

    private static Logger LOG = LoggerFactory.getLogger(HealthWebService.class);


    @GET
    @Path("/health")
    public Response check() {

        try {
            return Response.status(200).entity(new JsonResult(0, "The rest-demo service is healthy.")).build();
        }
        catch (Exception e) {
            LOG.error("The rest-demo service is unhealthy!!!", e);
            return Response.status(400).entity(new JsonResult(1, "The rest-demo service is unhealthy!!!")).build();
        }
    }
}

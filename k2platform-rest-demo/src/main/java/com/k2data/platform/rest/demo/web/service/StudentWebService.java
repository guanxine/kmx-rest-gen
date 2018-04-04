package com.k2data.platform.rest.demo.web.service;

import com.k2data.platform.rest.demo.common.BaseContext;
import com.k2data.platform.rest.demo.entity.JsonResult;
import com.k2data.platform.rest.demo.entity.Page;
import com.k2data.platform.rest.demo.entity.vo.StudentVo;
import com.k2data.platform.rest.demo.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;

/**
 * Created by guanxine on 18-4-2.
 */
@Path("/students")
@Produces(MediaType.APPLICATION_JSON)
public class StudentWebService {

    private static Logger LOG = LoggerFactory.getLogger(StudentWebService.class);

    @Autowired
    private BaseContext baseContext;

    @Autowired
    private StudentService studentService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(StudentVo studentVo) {
        try {
            // TODO: create
            Integer id = studentService.save(studentVo);

            Map<String, Object> map = new HashMap<>();
            map.put("id", id);
            return Response.status(201).entity(new JsonResult(0, "Create student successfully.", map)).build();
        }
        catch (Exception e) {

            LOG.error("Error, create student: ", e);
            return Response.status(400).entity(new JsonResult(1, String.format("Error, Create student: %s!", e.getMessage()))).build();
        }
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") int id) {
        try {

            // TODO: delete
            studentService.delete(id);

            Map<String, Object> map = new HashMap<>();
            map.put("id", id);
            return Response.status(200).entity(new JsonResult(0, "Delete student successfully.", map)).build();
        }
        catch (Exception e) {
            LOG.error("Error, delete student: ", e);
            return Response.status(400).entity(new JsonResult(1, String.format("Error, Delete student: %s!", e.getMessage()))).build();
        }
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") int id, StudentVo studentVo) {
        try {

            // TODO: update
            studentService.update(id, studentVo);

            Map<String, Object> map = new HashMap<>();
            map.put("id", id);
            return Response.status(200).entity(new JsonResult(0, "Update student successfully.", map)).build();
        }
        catch (Exception e) {
            LOG.error("Error, update student: ", e);
            return Response.status(400).entity(new JsonResult(1, String.format("Error, Update student: %s!", e.getMessage()))).build();
        }
    }


    @GET
    @Path("{id}")
    public Response get(@PathParam("id") int id) {
        LOG.info("Try to get student by id: " + id);

        if(id < 0) {
            return Response.status(400).entity(new JsonResult(1, String.format("Failed, student id should be > 0, but actual value is '%s'.", id))).build();
        }

        try {
            // TODO: get
            StudentVo studentVo = studentService.get(id);

            if(studentVo != null) {
                return Response.status(200).entity(new JsonResult(0, "Get student successfully.", studentVo)).build();
            }
            else {
                LOG.info("Failed, Not exist student {}", id);
                return Response.status(404).entity(new JsonResult(1, "Failed, Not exist student: " + id + ".")).build();
            }

        }
        catch (Exception e) {
            LOG.error("Failed, can not get student detail.", e);
            return Response.status(400).entity(new JsonResult(1, String.format("Failed, can not get student detail: %s!", e.getMessage()))).build();
        }
    }

    @GET
    @Path("")
    public Response list(@QueryParam("pageNum") final Long page, @QueryParam("pageSize") Long pageSize, @Context HttpServletRequest request) {

        try {
            List<StudentVo> results = null;
            long offset = Page.getOffset(page, pageSize);

            //TODO: get total
            long total = studentService.count();
            if (offset < total && total > 0) {
                if (pageSize < 1) {
                    pageSize = Page.DEFAULT_PAGE_SIZE;
                }
                //TODO: get lists
                results = studentService.list(offset, pageSize);
            }
            else {
                results = Collections.emptyList();
            }

            return Response.status(200).entity(new JsonResult(0, "Get student list successfully.", new Page<>(results, page, pageSize, total))).build();
        }
        catch (Exception e) {
            LOG.error("Error. Can not get student lists: {}!", e);
            return Response.status(400).entity(new JsonResult(1, String.format("Error. Can not get student lists: %s!", e.getMessage()))).build();
        }
    }

}

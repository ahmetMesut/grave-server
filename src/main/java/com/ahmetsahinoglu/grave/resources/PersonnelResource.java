package com.ahmetsahinoglu.grave.resources;

import com.ahmetsahinoglu.grave.hibernate.dao.PersonnelDao;
import com.ahmetsahinoglu.grave.hibernate.entity.Personnel;
import io.dropwizard.hibernate.UnitOfWork;
import io.robe.auth.Credentials;
import io.robe.auth.RobeAuth;
import io.robe.common.service.RobeService;
import io.robe.common.service.search.SearchParam;
import io.robe.common.service.search.model.SearchModel;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("personnel")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PersonnelResource {

    @Inject
    private PersonnelDao personnelDao;

    @GET
    @UnitOfWork
    @RobeService(group = "Personnel", description = "Personnel page GET method")
    public List<Personnel> getAllPersonnel(@RobeAuth Credentials credentials, @SearchParam SearchModel searchModel) {
        return personnelDao.findAll(searchModel);
    }

    @POST
    @UnitOfWork
    @RobeService(group = "Personnel", description = "Personnel page POST method")
    public Personnel create(@RobeAuth Credentials credentials, @Valid Personnel personnel) {
        return personnelDao.create(personnel);
    }

    @PUT
    @Path("{id}")
    @UnitOfWork
    @RobeService(group = "Personnel", description = "Personnel page PUT method")
    public Personnel update(@RobeAuth Credentials credentials, @PathParam("id") String id, @Valid Personnel personnel) {
        if (!id.equals(personnel.getOid())) {
            throw new WebApplicationException(Response.status(412)
                    .build());
        }
        return personnelDao.update(personnel);
    }

    @DELETE
    @Path("{id}")
    @UnitOfWork
    @RobeService(group = "Personnel", description = "Personnel page DELETE method")
    public Personnel delete(@RobeAuth Credentials credentials, @PathParam("id") String id, @Valid Personnel model) {
        if (!id.equals(model.getOid())) {
            throw new WebApplicationException(Response.status(412).build());
        }
        Personnel personnel = personnelDao.findById(id);
        if (personnel == null) {
            throw new WebApplicationException(Response.status(404).build());
        }
        return personnelDao.delete(personnel);
    }


}

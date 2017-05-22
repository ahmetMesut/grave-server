package com.ahmetsahinoglu.grave.resources;

import com.ahmetsahinoglu.grave.hibernate.dao.DeadOfCauseDao;
import com.ahmetsahinoglu.grave.hibernate.entity.DeadOfCause;
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

@Path("deadOfCause")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DeadOfCauseResource {

    @Inject
    private DeadOfCauseDao deadOfCauseDao;

    @GET
    @UnitOfWork
    @RobeService(group = "DeadOfCause", description = "DeadOfCause page GET method")
    public List<DeadOfCause> getAllDeadOfCause(@RobeAuth Credentials credentials, @SearchParam SearchModel searchModel) {
        return deadOfCauseDao.findAll(searchModel);
    }

    @POST
    @UnitOfWork
    @RobeService(group = "DeadOfCause", description = "DeadOfCause page POST method")
    public DeadOfCause create(@RobeAuth Credentials credentials, @Valid DeadOfCause deadOfCause) {
        return deadOfCauseDao.create(deadOfCause);
    }

    @PUT
    @Path("{id}")
    @UnitOfWork
    @RobeService(group = "DeadOfCause", description = "DeadOfCause page PUT method")
    public DeadOfCause update(@RobeAuth Credentials credentials, @PathParam("id") String id, @Valid DeadOfCause deadOfCause) {
        if (!id.equals(deadOfCause.getOid())) {
            throw new WebApplicationException(Response.status(412)
                    .build());
        }
        return deadOfCauseDao.update(deadOfCause);
    }

    @DELETE
    @Path("{id}")
    @UnitOfWork
    @RobeService(group = "DeadOfCause", description = "DeadOfCause page DELETE method")
    public DeadOfCause delete(@RobeAuth Credentials credentials, @PathParam("id") String id, @Valid DeadOfCause model) {
        if (!id.equals(model.getOid())) {
            throw new WebApplicationException(Response.status(412).build());
        }
        DeadOfCause deadOfCause = deadOfCauseDao.findById(id);
        if (deadOfCause == null) {
            throw new WebApplicationException(Response.status(404).build());
        }
        return deadOfCauseDao.delete(deadOfCause);
    }


}

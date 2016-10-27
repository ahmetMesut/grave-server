package com.mebitech.grave.resources;

import com.mebitech.grave.hibernate.dao.MosqueDao;
import com.mebitech.grave.hibernate.entity.Mosque;
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


@Path("mosque")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MosqueResource {

    @Inject
    private MosqueDao mosqueDao;

    @GET
    @UnitOfWork
    @RobeService(group = "Mosque", description = "Mosque page GET method")
    public List<Mosque> getAllMosque(@RobeAuth Credentials credentials, @SearchParam SearchModel searchModel) {
        return mosqueDao.findAll(searchModel);
    }

    @POST
    @UnitOfWork
    @RobeService(group = "Mosque", description = "Mosque page POST method")
    public Mosque create(@RobeAuth Credentials credentials, @Valid Mosque mosque) {
        return mosqueDao.create(mosque);
    }

    @PUT
    @Path("{id}")
    @UnitOfWork
    @RobeService(group = "Mosque", description = "Mosque page PUT method")
    public Mosque update(@RobeAuth Credentials credentials, @PathParam("id") String id, @Valid Mosque mosque) {
        if (!id.equals(mosque.getOid())) {
            throw new WebApplicationException(Response.status(412).build());
        }
        return mosqueDao.update(mosque);
    }

    @DELETE
    @Path("{id}")
    @UnitOfWork
    @RobeService(group = "Mosque", description = "Mosque page DELETE method")
    public Mosque delete(@RobeAuth Credentials credentials, @PathParam("id") String id, @Valid Mosque model) {
        if (!id.equals(model.getOid())) {
            throw new WebApplicationException(Response.status(412).build());
        }
        Mosque mosque = mosqueDao.findById(id);
        if (mosque == null) {
            throw new WebApplicationException(Response.status(404).build());
        }
        return mosqueDao.delete(mosque);
    }


}

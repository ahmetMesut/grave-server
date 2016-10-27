package com.mebitech.grave.resources;

import com.mebitech.grave.hibernate.dao.GravePriceDao;
import com.mebitech.grave.hibernate.entity.GravePrice;
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

@Path("gravePrice")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class GravePriceResource {

    @Inject
    private GravePriceDao gravePriceDao;

    @GET
    @UnitOfWork
    @RobeService(group = "GravePrice", description = "GravePrice page GET method")
    public List<GravePrice> getAllGrave(@RobeAuth Credentials credentials, @SearchParam SearchModel searchModel) {
        return gravePriceDao.findAll(searchModel);
    }

    @POST
    @UnitOfWork
    @RobeService(group = "GravePrice", description = "GravePrice page POST method")
    public GravePrice create(@RobeAuth Credentials credentials, @Valid GravePrice gravePrice) {
        return gravePriceDao.create(gravePrice);
    }

    @PUT
    @Path("{id}")
    @UnitOfWork
    @RobeService(group = "GravePrice", description = "GravePrice page PUT method")
    public GravePrice update(@RobeAuth Credentials credentials, @PathParam("id") String id, @Valid GravePrice gravePrice) {
        if (!id.equals(gravePrice.getOid())) {
            throw new WebApplicationException(Response.status(412)
                    .build());
        }
        return gravePriceDao.update(gravePrice);
    }

    @DELETE
    @Path("{id}")
    @UnitOfWork
    @RobeService(group = "GravePrice", description = "GravePrice page DELETE method")
    public GravePrice delete(@RobeAuth Credentials credentials, @PathParam("id") String id, @Valid GravePrice model) {
        if (!id.equals(model.getOid())) {
            throw new WebApplicationException(Response.status(412)
                    .build());
        }
        GravePrice gravePrice = gravePriceDao.findById(id);
        if (gravePrice == null) {
            throw new WebApplicationException(Response.status(404)
                    .build());
        }
        return gravePriceDao.delete(gravePrice);
    }

}

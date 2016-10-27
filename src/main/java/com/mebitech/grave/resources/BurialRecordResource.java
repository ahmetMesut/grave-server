package com.mebitech.grave.resources;

import com.mebitech.grave.hibernate.dao.BurialRecordDao;
import com.mebitech.grave.hibernate.entity.BurialRecord;
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

@Path("burialRecord")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BurialRecordResource {

    @Inject
    private BurialRecordDao burialRecordDao;

    @GET
    @UnitOfWork
    @RobeService(group = "BURIAL_RECORD", description = "BurialRecord page GET method")
    public List<BurialRecord> getAllBurialRecord(@RobeAuth Credentials credentials, @SearchParam SearchModel searchModel) {
        return burialRecordDao.findAll(searchModel);
    }

    @GET
    @Path("{oid}")
    @UnitOfWork
    @RobeService(group = "BURIAL_RECORD", description = "BurialRecord page GET method")
    public BurialRecord getBurialRecordById(@RobeAuth Credentials credentials, @PathParam("oid") String oid) {
        return burialRecordDao.findById(oid);
    }

    @POST
    @UnitOfWork
    @RobeService(group = "BURIAL_RECORD", description = "BurialRecord page POST method")
    public BurialRecord create(@RobeAuth Credentials credentials, @Valid BurialRecord burialRecord) {
        return burialRecordDao.create(burialRecord);
    }

    @PUT
    @Path("{id}")
    @UnitOfWork
    @RobeService(group = "BURIAL_RECORD", description = "BurialRecord page PUT method")
    public BurialRecord update(@RobeAuth Credentials credentials, @PathParam("id") String id, @Valid BurialRecord burialRecord) {
        if (!id.equals(burialRecord.getOid())) {
            throw new WebApplicationException(Response.status(412)
                    .build());
        }
        return burialRecordDao.update(burialRecord);
    }

    @DELETE
    @Path("{id}")
    @UnitOfWork
    @RobeService(group = "BURIAL_RECORD", description = "BurialRecord page DELETE method")
    public BurialRecord delete(@RobeAuth Credentials credentials, @PathParam("id") String id, @Valid BurialRecord model) {
        if (!id.equals(model.getOid())) {
            throw new WebApplicationException(Response.status(412)
                    .build());
        }
        BurialRecord burialRecord = burialRecordDao.findById(id);
        if (burialRecord == null) {
            throw new WebApplicationException(Response.status(404)
                    .build());
        }
        return burialRecordDao.delete(burialRecord);
    }
}
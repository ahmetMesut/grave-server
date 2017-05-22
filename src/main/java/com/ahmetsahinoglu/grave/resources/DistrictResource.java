package com.ahmetsahinoglu.grave.resources;

import com.ahmetsahinoglu.grave.hibernate.dao.DistrictDao;
import com.ahmetsahinoglu.grave.hibernate.entity.District;
import io.dropwizard.hibernate.UnitOfWork;
import io.robe.auth.Credentials;
import io.robe.auth.RobeAuth;
import io.robe.common.service.RobeService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("district")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DistrictResource {

    @Inject
    private DistrictDao districtDao;

    @GET
    @Path("{id}")
    @UnitOfWork
    @RobeService(group = "District", description = "GET method for get district by parentOid")
    public List<District> getDistrictByParentOid(@RobeAuth Credentials credentials, @PathParam("id") String id) {
        return districtDao.findDistrictByParentOid(id);
    }

    @GET
    @UnitOfWork
    @RobeService(group = "District", description = "GET method for get all District")
    public List<District> getAllProvince(@RobeAuth Credentials credentials) {
        return districtDao.findAll();
    }
}

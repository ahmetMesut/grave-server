package com.mebitech.grave.resources;

import com.mebitech.grave.hibernate.dao.ProvinceDao;
import com.mebitech.grave.hibernate.entity.Province;
import io.dropwizard.hibernate.UnitOfWork;
import io.robe.auth.Credentials;
import io.robe.auth.RobeAuth;
import io.robe.common.service.RobeService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("province")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProvinceResource {

    @Inject
    private ProvinceDao provinceDao;

    @GET
    @UnitOfWork
    @RobeService(group = "Province", description = "GET method for get all Province")
    public List<Province> getAllProvince(@RobeAuth Credentials credentials) {
        return provinceDao.findAll();
    }
}

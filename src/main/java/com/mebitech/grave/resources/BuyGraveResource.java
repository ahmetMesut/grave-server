package com.mebitech.grave.resources;

import com.mebitech.grave.hibernate.dao.BurialDao;
import com.mebitech.grave.hibernate.dao.BurialRecordDao;
import com.mebitech.grave.hibernate.dao.BuyGraveDao;
import com.mebitech.grave.hibernate.entity.Burial;
import com.mebitech.grave.hibernate.entity.BuyGrave;
import com.mebitech.grave.hibernate.enumtype.GraveState;
import com.mebitech.grave.hibernate.enumtype.PriceType;
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

@Path("buyGrave")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BuyGraveResource {

    @Inject
    private BuyGraveDao buyGraveDao;

    @Inject
    private BurialDao burialDao;

    @Inject
    private BurialRecordDao burialRecordDao;

    @GET
    @UnitOfWork
    @RobeService(group = "SELL_GRAVE_BURIAL", description = "SELL_GRAVE_BURIAL page GET method")
    public List<BuyGrave> getAll(@RobeAuth Credentials credentials, @SearchParam SearchModel searchModel) {
        return buyGraveDao.findAll(searchModel);
    }

    @GET
    @Path("{oid}")
    @UnitOfWork
    @RobeService(group = "SELL_GRAVE_BURIAL", description = "SELL_GRAVE_BURIAL page GET method")
    public BuyGrave getGraveById(@RobeAuth Credentials credentials, @PathParam("oid") String oid) {
        BuyGrave buyGrave = buyGraveDao.findByUserId(oid);
        if (buyGrave == null) {
            return new BuyGrave();
        }
        return buyGrave;
    }

    @POST
    @UnitOfWork
    @RobeService(group = "SELL_GRAVE_BURIAL", description = "SELL_GRAVE_BURIAL page POST method")
    public BuyGrave create(@RobeAuth Credentials credentials, @Valid BuyGrave buyGrave) {

        int total = buyGrave.getFreeGraveCount() + buyGrave.getNextFreeGraveCount() + buyGrave.getTotalPrice();

        if (buyGrave.getFreeGraveCount() != 0) {
            saveForBurialOperation(buyGrave.getFreeGraveCount(), buyGrave, PriceType.FREE, total);
        }
        if (buyGrave.getPaidGraveCount() != 0) {
            saveForBurialOperation(buyGrave.getPaidGraveCount(), buyGrave, PriceType.NEXT_TO_PAID, total);

        }
        if (buyGrave.getNextFreeGraveCount() != 0) {
            saveForBurialOperation(buyGrave.getNextFreeGraveCount(), buyGrave, PriceType.NEXT_TO_FREE, total);
        }
        return buyGraveDao.create(buyGrave);
    }

    @PUT
    @Path("{id}")
    @UnitOfWork
    @RobeService(group = "SELL_GRAVE_BURIAL", description = "SELL_GRAVE_BURIAL page PUT method")
    public BuyGrave update(@RobeAuth Credentials credentials, @PathParam("id") String id, @Valid BuyGrave model) {
        if (!id.equals(model.getOid())) {
            throw new WebApplicationException(Response.status(412)
                    .build());
        }
        return buyGraveDao.create(model);
    }


    @DELETE
    @Path("{id}")
    @UnitOfWork
    @RobeService(group = "SELL_GRAVE_BURIAL", description = "SELL_GRAVE_BURIAL page DELETE method")
    public BuyGrave delete(@RobeAuth Credentials credentials, @PathParam("id") String id, @Valid BuyGrave model) {
        if (!id.equals(model.getOid())) {
            throw new WebApplicationException(Response.status(412)
                    .build());
        }
        BuyGrave buyGrave = buyGraveDao.findById(id);
        if (buyGrave == null) {
            throw new WebApplicationException(Response.status(404)
                    .build());
        }
        return buyGraveDao.delete(buyGrave);
    }

    private int counter = 0;
    private static int registerCount = 1;

    private void saveForBurialOperation(int count, BuyGrave buyGrave, PriceType type, int total) {


        for (int i = 0; i < count; i++) {
            Burial burial = new Burial();
            burial.setRegisterNo(registerCount);
            burial.setUserOid(buyGrave.getUserOid());
            burial.setGrave(buyGrave.getGrave());
            burial.setInsular(buyGrave.getInsular());
            burial.setParcel(buyGrave.getParcel());
            if (buyGrave.getStartGraveNo() == 0) {
                buyGrave.setStartGraveNo(1);
            }
            burial.setStartGraveNo(buyGrave.getStartGraveNo() + counter);
            burial.setBuyDate(buyGrave.getBuyDate());
            burial.setGraveState(GraveState.NULL);
            burial.setGraveType(type);

            burialDao.create(burial);
            registerCount += 1;
            counter += 1;
        }

    }
}
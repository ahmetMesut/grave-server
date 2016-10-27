package com.mebitech.grave.resources;


import com.mebitech.grave.hibernate.dao.BurialDao;
import com.mebitech.grave.hibernate.dao.BuyGraveDao;
import com.mebitech.grave.hibernate.dao.GravePriceDao;
import com.mebitech.grave.hibernate.entity.Burial;
import com.mebitech.grave.hibernate.entity.BuyGrave;
import com.mebitech.grave.hibernate.entity.GravePrice;
import com.mebitech.grave.hibernate.enumtype.GraveState;
import com.mebitech.grave.hibernate.enumtype.PriceType;
import com.mebitech.grave.hibernate.enumtype.ReceiptState;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Path("burial")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BurialResource {

    @Inject
    private BurialDao burialDao;

    @Inject
    private BuyGraveDao buyGraveDao;

    @Inject
    private GravePriceDao gravePriceDao;

    @GET
    @UnitOfWork
    @RobeService(group = "BURIAL", description = "Burial page GET method")
    public List<Burial> getAllBurial(@RobeAuth Credentials credentials, @SearchParam SearchModel searchModel) {
        return burialDao.findAllWithSearchFrom(searchModel);
    }

    @GET
    @Path("detail")
    @UnitOfWork
    @RobeService(group = "BURIAL", description = "Burial page GET method")
    public List<Burial> getAllBurialForDetail(@RobeAuth Credentials credentials, @SearchParam SearchModel searchModel) {
        searchModel.addFilter("lastBurialOid", "!=", null);
        return burialDao.findAllWithSearchFrom(searchModel);
    }

    @GET
    @Path("detail/{userOid}")
    @UnitOfWork
    @RobeService(group = "BURIAL", description = "Burial page GET method")
    public List<Burial> getAllBurialForDetailById(@RobeAuth Credentials credentials, @PathParam("userOid") String userOid) {
        List<Burial> burials = burialDao.findBurialByUserOid(userOid);
        List<Burial> response = new ArrayList<>();
        for (Burial burial : burials) {
            if (burial.getLastBurialOid() != null) {
                response.add(burial);
            }
        }
        return response;
    }

    @POST
    @UnitOfWork
    @RobeService(group = "BURIAL", description = "Burial page POST method")
    public Burial create(@RobeAuth Credentials credentials, @Valid Burial burial) {
        return burialDao.create(burial);
    }

    @PUT
    @UnitOfWork
    @RobeService(group = "BURIAL", description = "Burial page PUT method")
    public Burial update(@RobeAuth Credentials credentials, @Valid Burial burial) {
        return burialDao.update(burial);
    }

    @DELETE
    @Path("{id}")
    @UnitOfWork
    @RobeService(group = "BURIAL", description = "Burial page DELETE method")
    public Burial delete(@RobeAuth Credentials credentials, @PathParam("id") String id, @Valid Burial model) {
        if (!id.equals(model.getOid())) {
            throw new WebApplicationException(Response.status(412).build());
        }
        Burial burial = burialDao.findById(id);
        if (burial == null) {
            throw new WebApplicationException(Response.status(412).build());
        }
        return burialDao.delete(burial);
    }

    @PUT
    @UnitOfWork
    @Path("{graveOid}/{burialRecordOid}")
    @RobeService(group = "BURIAL", description = "Burial page PUT method")
    public Burial updateBurial(@RobeAuth Credentials credentials, @PathParam("graveOid") String graveOid,
                               @PathParam("burialRecordOid") String burialRecordOid) {
        Burial burial = burialDao.findById(graveOid);
        if (burial.getLastBurialOid() != null) {
            burial.setGraveState(GraveState.OVER_BURIAL);
        } else {
            burial.setGraveState(GraveState.FULL);
        }
        burial.setLastBurialOid(burialRecordOid);
        return burialDao.update(burial);
    }


    @PUT
    @UnitOfWork
    @Path("transfer/{oid}/{newUserOid}")
    @RobeService(group = "BURIAL", description = "Burial page PUT method")
    public Burial updateBurialUserOid(@RobeAuth Credentials credentials, @PathParam("oid") String oid,
                                      @PathParam("newUserOid") String newUserOid) {
        return transferBurial(oid, newUserOid);
    }

    private Burial transferBurial(String oid, String newUserOid) {

        GravePrice gravePriceNextToFree = gravePriceDao.findUnitPrice(PriceType.NEXT_TO_FREE);
        GravePrice gravePricePaid = gravePriceDao.findUnitPrice(PriceType.NEXT_TO_PAID);
        GravePrice gravePriceFree = gravePriceDao.findUnitPrice(PriceType.FREE);

        Burial burial = burialDao.findById(oid);
        BuyGrave buyGrave = buyGraveDao.findByUserId(burial.getUserOid());//devredilecek mezar
        BuyGrave newBuyGrave = buyGraveDao.findByUserId(newUserOid);//devralacak kişinin mezarı var mı

        if (newBuyGrave == null) {
            newBuyGrave = new BuyGrave(); //devralacak kişinin yok ise oluşturuyoruz
            newBuyGrave.setUserOid(newUserOid);

            if (burial.getGraveType().equals(PriceType.FREE)) {

                newBuyGrave.setFreeGraveCount(1);
                newBuyGrave.setBuyDate(new Date());
                newBuyGrave.setGrave(buyGrave.getGrave());
                newBuyGrave.setInsular(buyGrave.getInsular());
                newBuyGrave.setParcel(buyGrave.getParcel());
                newBuyGrave.setStartGraveNo(buyGrave.getStartGraveNo());
                newBuyGrave.setLicenceNo(buyGrave.getLicenceNo());
                newBuyGrave.setLicenceDate(buyGrave.getLicenceDate());
                newBuyGrave.setLicenceMortar(buyGrave.getLicenceMortar());
                newBuyGrave.setTotalPrice(gravePriceFree.getUnitPrice());
                buyGrave.setTotalPrice(buyGrave.getTotalPrice() - gravePriceFree.getUnitPrice());
                buyGrave.setFreeGraveCount(buyGrave.getFreeGraveCount() - 1);
                receiptState(buyGrave);
            }
            if (burial.getGraveType().equals(PriceType.NEXT_TO_PAID)) {

                newBuyGrave.setPaidGraveCount(1);
                newBuyGrave.setBuyDate(new Date());
                newBuyGrave.setGrave(buyGrave.getGrave());
                newBuyGrave.setInsular(buyGrave.getInsular());
                newBuyGrave.setParcel(buyGrave.getParcel());
                newBuyGrave.setStartGraveNo(buyGrave.getStartGraveNo());
                newBuyGrave.setLicenceNo(buyGrave.getLicenceNo());
                newBuyGrave.setLicenceDate(buyGrave.getLicenceDate());
                newBuyGrave.setLicenceMortar(buyGrave.getLicenceMortar());
                newBuyGrave.setTotalPrice(gravePricePaid.getUnitPrice());
                buyGrave.setTotalPrice(buyGrave.getTotalPrice() - gravePricePaid.getUnitPrice());
                buyGrave.setPaidGraveCount(buyGrave.getPaidGraveCount() - 1);
                receiptState(buyGrave);
            }
            if (burial.getGraveType().equals(PriceType.NEXT_TO_FREE)) {

                newBuyGrave.setNextFreeGraveCount(1);
                newBuyGrave.setBuyDate(new Date());
                newBuyGrave.setGrave(buyGrave.getGrave());
                newBuyGrave.setInsular(buyGrave.getInsular());
                newBuyGrave.setParcel(buyGrave.getParcel());
                newBuyGrave.setStartGraveNo(buyGrave.getStartGraveNo());
                newBuyGrave.setLicenceNo(buyGrave.getLicenceNo());
                newBuyGrave.setLicenceDate(buyGrave.getLicenceDate());
                newBuyGrave.setLicenceMortar(buyGrave.getLicenceMortar());
                newBuyGrave.setTotalPrice(gravePriceNextToFree.getUnitPrice());
                buyGrave.setTotalPrice(buyGrave.getTotalPrice() - gravePriceNextToFree.getUnitPrice());
                buyGrave.setNextFreeGraveCount(buyGrave.getNextFreeGraveCount() - 1);
                receiptState(buyGrave);
            }
            buyGraveDao.create(newBuyGrave);
        } else { //devralacak kişinin var ise
            if (burial.getGraveType().equals(PriceType.FREE)) {

                newBuyGrave.setFreeGraveCount(newBuyGrave.getFreeGraveCount() + 1);
                buyGrave.setFreeGraveCount(buyGrave.getFreeGraveCount() - 1);
                newBuyGrave.setBuyDate(new Date());
                newBuyGrave.setGrave(buyGrave.getGrave());
                newBuyGrave.setInsular(buyGrave.getInsular());
                newBuyGrave.setParcel(buyGrave.getParcel());
                newBuyGrave.setStartGraveNo(buyGrave.getStartGraveNo());
                newBuyGrave.setLicenceNo(buyGrave.getLicenceNo());
                newBuyGrave.setLicenceDate(buyGrave.getLicenceDate());
                newBuyGrave.setLicenceMortar(buyGrave.getLicenceMortar());
                newBuyGrave.setTotalPrice(newBuyGrave.getTotalPrice() + gravePriceFree.getUnitPrice());
                buyGrave.setTotalPrice(buyGrave.getTotalPrice() - gravePriceFree.getUnitPrice());
                receiptState(buyGrave);
            }
            if (burial.getGraveType().equals(PriceType.NEXT_TO_PAID)) {

                newBuyGrave.setPaidGraveCount(newBuyGrave.getPaidGraveCount() + 1);
                buyGrave.setPaidGraveCount(buyGrave.getPaidGraveCount() - 1);
                newBuyGrave.setBuyDate(new Date());
                newBuyGrave.setGrave(buyGrave.getGrave());
                newBuyGrave.setInsular(buyGrave.getInsular());
                newBuyGrave.setParcel(buyGrave.getParcel());
                newBuyGrave.setStartGraveNo(buyGrave.getStartGraveNo());
                newBuyGrave.setLicenceNo(buyGrave.getLicenceNo());
                newBuyGrave.setLicenceDate(buyGrave.getLicenceDate());
                newBuyGrave.setLicenceMortar(buyGrave.getLicenceMortar());
                newBuyGrave.setTotalPrice(newBuyGrave.getTotalPrice() + gravePricePaid.getUnitPrice());
                buyGrave.setTotalPrice(buyGrave.getTotalPrice() - gravePricePaid.getUnitPrice());
                receiptState(buyGrave);
            }
            if (burial.getGraveType().equals(PriceType.NEXT_TO_FREE)) {

                newBuyGrave.setNextFreeGraveCount(newBuyGrave.getNextFreeGraveCount() + 1);
                buyGrave.setNextFreeGraveCount(buyGrave.getNextFreeGraveCount() - 1);
                newBuyGrave.setBuyDate(new Date());
                newBuyGrave.setGrave(buyGrave.getGrave());
                newBuyGrave.setInsular(buyGrave.getInsular());
                newBuyGrave.setParcel(buyGrave.getParcel());
                newBuyGrave.setStartGraveNo(buyGrave.getStartGraveNo());
                newBuyGrave.setLicenceNo(buyGrave.getLicenceNo());
                newBuyGrave.setLicenceDate(buyGrave.getLicenceDate());
                newBuyGrave.setLicenceMortar(buyGrave.getLicenceMortar());
                newBuyGrave.setTotalPrice(newBuyGrave.getTotalPrice() + gravePriceNextToFree.getUnitPrice());
                buyGrave.setTotalPrice(buyGrave.getTotalPrice() - gravePriceNextToFree.getUnitPrice());
                receiptState(buyGrave);
            }
            buyGraveDao.update(newBuyGrave);
        }


        burial.setUserOid(newUserOid);
        return burialDao.update(burial);
    }

    private void receiptState(BuyGrave buyGrave) {
        if (buyGrave.getFreeGraveCount() + buyGrave.getNextFreeGraveCount() + buyGrave.getPaidGraveCount() == 0) {
            buyGrave.setIsReceiptCancel(ReceiptState.YES);
        }
    }

}

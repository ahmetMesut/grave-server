package com.mebitech.grave.hibernate.dao;

import com.mebitech.grave.hibernate.entity.GravePrice;
import com.mebitech.grave.hibernate.enumtype.PriceType;
import io.robe.hibernate.dao.BaseDao;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import javax.inject.Inject;

public class GravePriceDao extends BaseDao<GravePrice> {

    @Inject
    public GravePriceDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }


    public GravePrice findUnitPrice(PriceType priceType) {
        Criteria criteria = currentSession().createCriteria(GravePrice.class);
        criteria.add(Restrictions.eq("priceType", priceType));
        return (GravePrice) criteria.uniqueResult();
    }
}

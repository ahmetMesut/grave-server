package com.mebitech.grave.hibernate.dao;

import com.mebitech.grave.hibernate.entity.BuyGrave;
import io.robe.hibernate.dao.BaseDao;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import javax.inject.Inject;

public class BuyGraveDao extends BaseDao<BuyGrave> {

    @Inject
    public BuyGraveDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public BuyGrave findByUserId(String userOid) {
        Criteria criteria = currentSession().createCriteria(BuyGrave.class);
        criteria.add(Restrictions.eq("userOid", userOid));
        return (BuyGrave) criteria.uniqueResult();
    }
}

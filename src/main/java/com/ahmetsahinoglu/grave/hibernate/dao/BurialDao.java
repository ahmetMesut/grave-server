package com.ahmetsahinoglu.grave.hibernate.dao;

import com.ahmetsahinoglu.grave.hibernate.entity.Burial;
import io.robe.hibernate.dao.BaseDao;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import javax.inject.Inject;
import java.util.List;

public class BurialDao extends BaseDao<Burial> {

    @Inject
    public BurialDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<Burial> findBurialByUserOid(String userOid) {
        Criteria criteria = currentSession().createCriteria(Burial.class);
        criteria.add(Restrictions.eq("userOid", userOid));
        return list(criteria);
    }
}

package com.ahmetsahinoglu.grave.hibernate.dao;

import com.ahmetsahinoglu.grave.hibernate.entity.District;
import io.robe.hibernate.dao.BaseDao;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import javax.inject.Inject;
import java.util.List;

public class DistrictDao extends BaseDao<District> {

    @Inject
    public DistrictDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<District> findDistrictByParentOid(String id) {
        Criteria criteria = currentSession().createCriteria(District.class);
        criteria.add(Restrictions.eq("provinceOid", id));
        return list(criteria);
    }
}

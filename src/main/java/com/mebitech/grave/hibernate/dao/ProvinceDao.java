package com.mebitech.grave.hibernate.dao;

import com.mebitech.grave.hibernate.entity.Province;
import io.robe.hibernate.dao.BaseDao;
import org.hibernate.SessionFactory;

import javax.inject.Inject;

public class ProvinceDao extends BaseDao<Province> {

    @Inject
    public ProvinceDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}

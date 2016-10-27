package com.mebitech.grave.hibernate.dao;

import com.mebitech.grave.hibernate.entity.Personnel;
import io.robe.hibernate.dao.BaseDao;
import org.hibernate.SessionFactory;

import javax.inject.Inject;

public class PersonnelDao extends BaseDao<Personnel> {

    @Inject
    public PersonnelDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}

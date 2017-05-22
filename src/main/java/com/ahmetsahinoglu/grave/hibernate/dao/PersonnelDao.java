package com.ahmetsahinoglu.grave.hibernate.dao;

import com.ahmetsahinoglu.grave.hibernate.entity.Personnel;
import io.robe.hibernate.dao.BaseDao;
import org.hibernate.SessionFactory;

import javax.inject.Inject;

public class PersonnelDao extends BaseDao<Personnel> {

    @Inject
    public PersonnelDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}

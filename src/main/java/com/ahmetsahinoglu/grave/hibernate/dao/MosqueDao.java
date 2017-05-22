package com.ahmetsahinoglu.grave.hibernate.dao;

import com.ahmetsahinoglu.grave.hibernate.entity.Mosque;
import io.robe.hibernate.dao.BaseDao;
import org.hibernate.SessionFactory;

import javax.inject.Inject;

public class MosqueDao extends BaseDao<Mosque> {

    @Inject
    public MosqueDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}

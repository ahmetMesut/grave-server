package com.ahmetsahinoglu.grave.hibernate.dao;

import com.ahmetsahinoglu.grave.hibernate.entity.BurialRecord;
import io.robe.hibernate.dao.BaseDao;
import org.hibernate.SessionFactory;

import javax.inject.Inject;

public class BurialRecordDao extends BaseDao<BurialRecord> {

    @Inject
    public BurialRecordDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}

package com.mebitech.grave.hibernate.dao;

import com.mebitech.grave.hibernate.entity.DeadOfCause;
import io.robe.hibernate.dao.BaseDao;
import org.hibernate.SessionFactory;

import javax.inject.Inject;

public class DeadOfCauseDao extends BaseDao<DeadOfCause> {

    @Inject
    public DeadOfCauseDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}

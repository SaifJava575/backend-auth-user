package com.auth.dao.impl;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.auth.dao.IGenericDao;


@Repository
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class GenericJpaDao extends AbstractJpaDao implements IGenericDao{

}

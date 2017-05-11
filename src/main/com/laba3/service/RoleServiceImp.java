package com.laba3.service;

import com.laba3.dao.RoleDao;
import com.laba3.pojo.Role;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Created by set on 23.04.17.
 */
@Service
public class RoleServiceImp implements RoleService {

    final static Logger logger = Logger.getLogger(RoleServiceImp.class);
    private  final RoleDao roleDao;

    @Autowired
    public RoleServiceImp(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    public Collection<Role> getAllRole() throws ClassNotFoundException {
        return roleDao.getAll();
    }

    public void setRoleDAO(com.laba3.dao.RoleDaoImp roleDAO) {
    }
}

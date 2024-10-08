package com.nailasahidah.cashier.service;

import com.nailasahidah.cashier.domain.Role;

import java.util.Collection;

public interface RoleService {
    Role getRoleByUserId(Long id);

    Collection<Role> getRoles();
}

package com.nailasahidah.pendaftaran.service;

import com.nailasahidah.pendaftaran.domain.Role;

import java.util.Collection;

public interface RoleService {
    Role getRoleByUserId(Long id);

    Collection<Role> getRoles();
}

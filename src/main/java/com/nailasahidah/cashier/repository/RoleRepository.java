package com.nailasahidah.cashier.repository;

import com.nailasahidah.cashier.domain.Role;

import java.util.Collection;

public interface RoleRepository <T extends Role> {
    /*  Basic CRUD Operation */
    T create (T data);
    Collection<T> list (); // pagination
    T get(Long id);
    T update (T data);
    Boolean delete (Long id);

    /* More Complex Operation */

    void addRoleToUser(Long userId, String roleName);
    Role getRoleByUserId(Long userId);
    Role getRoleByUserEmail(String email);
    void updateUserRole(Long userId, String roleName);
}

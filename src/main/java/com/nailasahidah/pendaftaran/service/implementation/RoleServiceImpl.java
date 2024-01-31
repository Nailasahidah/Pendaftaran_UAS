package com.nailasahidah.pendaftaran.service.implementation;

import com.nailasahidah.pendaftaran.domain.Role;
import com.nailasahidah.pendaftaran.repository.RoleRepository;
import com.nailasahidah.pendaftaran.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository<Role> roleRoleRepository;
    @Override
    public Role getRoleByUserId(Long id) {
        return roleRoleRepository.getRoleByUserId(id);
    }

    @Override
    public Collection<Role> getRoles() {
        return roleRoleRepository.list();
    }
}

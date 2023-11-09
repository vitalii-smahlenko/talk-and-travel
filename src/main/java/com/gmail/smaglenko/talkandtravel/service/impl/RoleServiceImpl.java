package com.gmail.smaglenko.talkandtravel.service.impl;

import com.gmail.smaglenko.talkandtravel.model.Role;
import com.gmail.smaglenko.talkandtravel.model.Role.RoleName;
import com.gmail.smaglenko.talkandtravel.repository.RoleRepository;
import com.gmail.smaglenko.talkandtravel.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository repository;

    @Override
    public Role save(Role role) {
        return repository.save(role);
    }

    @Override
    public Role findByRoleName(RoleName roleName) {
        return repository.findByRoleName(roleName);
    }
}

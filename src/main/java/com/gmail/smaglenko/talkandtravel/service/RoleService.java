package com.gmail.smaglenko.talkandtravel.service;

import com.gmail.smaglenko.talkandtravel.model.Role;
import com.gmail.smaglenko.talkandtravel.model.Role.RoleName;

public interface RoleService {
    Role save(Role role);

    Role findByRoleName(RoleName roleName);
}
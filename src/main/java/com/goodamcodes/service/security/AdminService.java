package com.goodamcodes.service.security;

import com.goodamcodes.enums.Role;
import com.goodamcodes.dto.security.RoleUpdateRequestDTO;
import com.goodamcodes.model.security.UserInfo;
import com.goodamcodes.repository.security.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserInfoRepository repository;

    public String assignRole(RoleUpdateRequestDTO request) {
        UserInfo user = getUserByUsername(request.getUsername());
        Set<Role> roles = new HashSet<>(user.getRoles());

        Role newRole = toRoleEnum(request.getRole());
        if (roles.contains(newRole)) {
            return "User already has this role.";
        }

        roles.add(newRole);
        saveRoles(user, roles);

        return "Role assigned successfully.";
    }

//    public String unassignRole(RoleUpdateRequestDTO request) {
//        UserInfo user = getUserByUsername(request.getUsername());
//        Set<Role> roles = new HashSet<>(user.getRoles());
//
//        Role roleToRemove = toRoleEnum(request.getRole());
//        if (!roles.contains(roleToRemove)) {
//            return "User does not have this role.";
//        }
//
//        roles.remove(roleToRemove);
//        saveRoles(user, roles);
//
//        return "Role unassigned successfully.";
//    }


    private UserInfo getUserByUsername(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    private Role toRoleEnum(String roleName) {
        return Role.valueOf(roleName.toUpperCase());
    }

    private void saveRoles(UserInfo user, Set<Role> roles) {
        user.setRoles(new ArrayList<>(roles));
        repository.save(user);
    }

}

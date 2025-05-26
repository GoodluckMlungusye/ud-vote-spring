package com.goodamcodes.service;

import com.goodamcodes.enums.Role;
import com.goodamcodes.dto.RoleUpdateRequestDTO;
import com.goodamcodes.model.UserInfo;
import com.goodamcodes.repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserInfoRepository repository;

    public String assignRole(RoleUpdateRequestDTO request) {
        UserInfo user = repository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Set<Role> roles = new HashSet<>(user.getRoles());

        Role newRole = Role.valueOf(request.getRole().toUpperCase());
        if (roles.contains(newRole)) {
            return "User already has this role.";
        }
        roles.add(newRole);

        user.setRoles(new ArrayList<>(roles));
        repository.save(user);

        return "Role assigned successfully.";
    }

}

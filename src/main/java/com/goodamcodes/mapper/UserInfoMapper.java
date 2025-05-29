package com.goodamcodes.mapper;

import com.goodamcodes.dto.UserInfoResponseDTO;
import com.goodamcodes.model.UserInfo;
import org.springframework.stereotype.Component;

@Component
public class UserInfoMapper {

    public UserInfoResponseDTO toUserInfoResponseDTO(UserInfo userInfo) {
        UserInfoResponseDTO userInfoResponseDTO = new UserInfoResponseDTO();
        userInfoResponseDTO.setId(userInfo.getId());
        userInfoResponseDTO.setFirstName(userInfo.getFirstName());
        userInfoResponseDTO.setLastName(userInfo.getLastName());
        userInfoResponseDTO.setUsername(userInfo.getUsername());
        userInfoResponseDTO.setEmail(userInfo.getEmail());
        return userInfoResponseDTO;
    }
}

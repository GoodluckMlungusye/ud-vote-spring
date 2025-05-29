package com.goodamcodes.mapper;

import com.goodamcodes.dto.security.UserInfoRequestDTO;
import com.goodamcodes.dto.security.UserInfoResponseDTO;
import com.goodamcodes.model.security.UserInfo;
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

    public UserInfo toUserInfo(UserInfoRequestDTO userInfoRequestDTO) {
        UserInfo userInfo = new UserInfo();
        userInfo.setFirstName(userInfoRequestDTO.getFirstName());
        userInfo.setLastName(userInfoRequestDTO.getLastName());
        userInfo.setUsername(userInfoRequestDTO.getUsername());
        userInfo.setEmail(userInfoRequestDTO.getEmail());
        return userInfo;
    }

}

package com.company.service;

import com.company.config.CustomUserDetails;
import com.company.dto.AuthDTO;
import com.company.dto.JwtDTO;
import com.company.dto.ResponseUserJwt;
import com.company.util.JwtUtil;
import com.company.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;

    public ResponseUserJwt login(AuthDTO authDTO) {
        Authentication authenticate = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(authDTO.getUsername(), MD5Util.getMd5(authDTO.getPassword())));
        CustomUserDetails user = (CustomUserDetails) authenticate.getPrincipal();


        ResponseUserJwt dtos = new ResponseUserJwt();
        dtos.setUsername(user.getUsername());

        JwtDTO dto = new JwtDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setRole(user.getRole().name());
        dto.setPassword(user.getPassword());
        dto.setStatus(user.getStatus().name());
        dtos.setJwt(JwtUtil.encode(dto));
        return dtos;
    }
}

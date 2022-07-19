package com.company.service;

import com.company.config.CustomUserDetails;
import com.company.dto.AuthDTO;
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

    public ResponseUserJwt login(AuthDTO dto) {
        Authentication authenticate = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), MD5Util.getMd5(dto.getPassword())));
        CustomUserDetails user = (CustomUserDetails) authenticate.getPrincipal();


        ResponseUserJwt dtos = new ResponseUserJwt();
        dtos.setUsername(user.getUsername());
        dtos.setJwt(JwtUtil.encode(user.getId()));
        return dtos;
    }
}

package com.company.config;

import com.company.dto.JwtDTO;
import com.company.entity.CompanyEntity;
import com.company.entity.ProfileEntity;
import com.company.enums.GeneralRole;
import com.company.enums.GeneralStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

public class CustomUserDetails implements UserDetails {

    private String id;
    private String username;
    private String password;
    private GeneralStatus status;



    private GeneralRole role; //BANK,PAYMENT, ADMIN,MODERATOR
    private  Boolean visible;

    public CustomUserDetails(ProfileEntity profile) {
        this.id = profile.getId();
        this.username = profile.getName();
        this.password = profile.getPassword();
        this.status = profile.getStatus();
        this.role = profile.getRole();
        this.visible = profile.getVisible();
    }
    public CustomUserDetails(CompanyEntity company) {
        this.id = company.getId();
        this.username = company.getName();
        this.password = company.getPassword();
        this.status = company.getStatus();
        this.role = company.getRole();
        this.visible = company.getVisible();
    }
    public CustomUserDetails(JwtDTO dto) {
        this.id = dto.getId();
        this.username = dto.getUsername();
        this.password = dto.getPassword();
        this.status = GeneralStatus.valueOf(dto.getStatus());
        this.role = GeneralRole.valueOf(dto.getRole());
        this.visible = dto.getVisible();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return new LinkedList<>(Collections.singletonList(new SimpleGrantedAuthority(role.name())));
    }

    @Override
    public String getPassword() {
        return password;
    }


    public String getId() {
        return id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        if (status.equals(GeneralStatus.ACTIVE)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return visible;
    }

    public GeneralRole getRole() {
        return role;
    }
    public GeneralStatus getStatus() {
        return status;
    }


}

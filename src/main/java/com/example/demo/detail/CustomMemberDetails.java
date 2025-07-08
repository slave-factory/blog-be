package com.example.demo.detail;

import com.example.demo.entity.Members;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomMemberDetails implements UserDetails {

    @JsonIgnore
    private final Members members;

    public CustomMemberDetails(Members members) {
        this.members = members;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return members.getPassword();
    }

    @Override
    public String getUsername() {
        return members.getUserId();
    }

    public String getNickname() {
        return members.getNickname();
    }

    public Long getId() {
        return members.getId();
    }
}

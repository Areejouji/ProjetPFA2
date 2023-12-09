package com.example.ProjectV1.model;

import com.example.ProjectV1.Token.Token;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Document(value="Users")
@Data
@AllArgsConstructor
@NoArgsConstructor


public class User implements UserDetails {
    @Id
    @MongoId
    private String user_Id;
    @NotNull(message = "the username must not be null")
    @NotEmpty(message = "the username must not be empty")
    private String username;
    @NotNull(message = "the password must not be null")
    @NotEmpty(message = "the password must not be empty")
    private String password;

    private ArrayList<String> roles = new ArrayList<>(); //rolename
    @DBRef
    private ArrayList<Token> tokens=new ArrayList<>();

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<String> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return mapRolesToAuthorities(roles);
    }

    @Override
    public String getPassword() {
        return password;
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
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
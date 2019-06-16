package de.digiwill.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.*;

public class AuthoritySet implements Serializable{
    private List<GrantedAuthority> authorities;

    public AuthoritySet(List<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public void addAuthority(String role) {
        authorities.add(new SimpleGrantedAuthority(role));
    }

    public GrantedAuthority getAuthorityByRoleName(String role) {
        for (GrantedAuthority a : authorities) {
            if (a.getAuthority().equals(role)) {
                return a;
            }
        }
        return null;
    }

    public void removeAuthority(GrantedAuthority auth){
        authorities.remove(auth);
    }

    public Collection<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String toString() {

        StringBuilder stringBuilder = new StringBuilder();
        for (GrantedAuthority a : authorities) {
            stringBuilder.append(a.getAuthority() + "\n");
        }
        return stringBuilder.toString();
    }
}

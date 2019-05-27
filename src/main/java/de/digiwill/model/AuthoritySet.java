package de.digiwill.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.*;

public class AuthoritySet {
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

    public static SortedSet<GrantedAuthority> sortAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Assert.notNull(authorities, "Cannot pass a null GrantedAuthority collection");
        // Ensure array iteration order is predictable (as per
        // UserDetails.getAuthorities() contract and SEC-717)
        SortedSet<GrantedAuthority> sortedAuthorities = new TreeSet<>(
                new AuthorityComparator());

        for (GrantedAuthority grantedAuthority : authorities) {
            Assert.notNull(grantedAuthority, "GrantedAuthority list cannot contain any null elements");
            sortedAuthorities.add(grantedAuthority);
        }
        return sortedAuthorities;
    }

    public static class AuthorityComparator implements Comparator<GrantedAuthority>,
            Serializable {
        private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

        public int compare(GrantedAuthority g1, GrantedAuthority g2) {
            // Neither should ever be null as each entry is checked before adding it to
            // the set.
            // If the authority is null, it is a custom authority and should precede
            // others.
            if (g2.getAuthority() == null) {
                return -1;
            }

            if (g1.getAuthority() == null) {
                return 1;
            }

            return g1.getAuthority().compareTo(g2.getAuthority());
        }
    }
}

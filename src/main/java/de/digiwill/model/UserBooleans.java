package de.digiwill.model;

import java.io.Serializable;

public class UserBooleans implements Serializable {
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean isVerified;

    public UserBooleans(boolean accountNonExpired, boolean accountNonLocked, boolean credentialsNonExpired, boolean isVerified) {
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.isVerified = isVerified;
    }

    public static UserBooleans getInitial(){
        return new UserBooleans(true, true, true, false);
    }

    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }
}

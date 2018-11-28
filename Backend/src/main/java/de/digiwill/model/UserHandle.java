package de.digiwill.model;

import org.springframework.security.core.GrantedAuthority;

import java.util.*;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.core.userdetails.UserDetails;

@Document(collection = "users")
@TypeAlias("user")
public class UserHandle implements UserDetails {
    @Id
    @Field("_id")
    private ObjectId UID;

    /**
     * Email Address
     */
    private String username;
    private String password;
    private Set<GrantedAuthority> authorities; //TODO final?
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean isVerified;

    /**
     * UTC timestamp
     */
    private long lastSignOfLife;
    private long lastReminder;
    private long deltaReminder;
    private long deltaDeathTime;
    private boolean isDead;

    private PersonalData personalData;

    private List<BaseAction> actions;

   /* @PersistenceConstructor
    public UserHandle(ObjectId UID, String emailAddress, PersonalData personalData, String alias, long lastSignOfLife, long lastReminder, long deltaReminder, long deltaDeathTime, boolean isDead, boolean isVerified, Iterable<BaseAction> actions) {
        this(emailAddress, personalData, alias, lastSignOfLife, lastReminder, deltaReminder, deltaDeathTime, isDead, isVerified, actions);
        this.UID = UID;
    }

    public UserHandle(String emailAddress, PersonalData personalData, String alias, long lastSignOfLife, long lastReminder, long deltaReminder, long deltaDeathTime, boolean isDead, boolean isVerified, Iterable<BaseAction> actions) {
        this.emailAddress = emailAddress;
        this.alias = alias;
        this.personalData = personalData;
        this.lastSignOfLife = lastSignOfLife;
        this.lastReminder = lastReminder;
        this.deltaReminder = deltaReminder;
        this.deltaDeathTime = deltaDeathTime;
        this.isDead = isDead();
        this.isVerified = isVerified;
        this.actions = actions;
    }*/


  /*  public UserHandle(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        user = new User(username, password, authorities);
    }*/

  public UserHandle(){

  }

    public UserHandle(String username, String password, Set<GrantedAuthority> authorities) {
        this(username, password, authorities, true, true, true,
                true /*TODO false*/, -1, -1, -1, -1, false,
                new PersonalData("User", "Userson"), new ArrayList<BaseAction>());


    }
    public UserHandle(String username, String password, Set<GrantedAuthority> authorities,
                      boolean accountNonExpired, boolean accountNonLocked, boolean credentialsNonExpired,
                      boolean isVerified, long lastSignOfLife, long lastReminder, long deltaReminder,
                      long deltaDeathTime, boolean isDead,
                      PersonalData personalData, List<BaseAction> actions) {
        //user = new User(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);

        this.username = username;
        this.password = password;
        this.authorities = authorities;//Collections.unmodifiableSet(UserHelper.sortAuthorities(authorities));
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.isVerified = isVerified;
        this.lastSignOfLife = lastSignOfLife;
        this.lastReminder = lastReminder;
        this.deltaReminder = deltaReminder;
        this.deltaDeathTime = deltaDeathTime;
        this.isDead = isDead;
        this.personalData = personalData;
        this.actions = actions;
    }

    public boolean isValidNewUser(){
        return false; //TODO implement
    }

    public ObjectId getUID() {
        return UID;
    }

    public long getLastSignOfLife() {
        return lastSignOfLife;
    }

    public void setLastSignOfLife(long lastSignOfLife) {
        this.lastSignOfLife = lastSignOfLife;
    }

    public long getLastReminder() {
        return lastReminder;
    }

    public void setLastReminder(long lastReminder) {
        this.lastReminder = lastReminder;
    }

    public long getDeltaReminder() {
        return deltaReminder;
    }

    public void setDeltaReminder(long deltaReminder) {
        this.deltaReminder = deltaReminder;
    }

    public long getDeltaDeathTime() {
        return deltaDeathTime;
    }

    public void setDeltaDeathTime(long deltaDeathTime) {
        this.deltaDeathTime = deltaDeathTime;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public Iterable<BaseAction> getActions() {
        return actions;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
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
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isVerified;
    }


}

package de.digiwill.model;

import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.*;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
    private List<GrantedAuthority> authorities; //TODO final?
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean isVerified;

    /**
     * Unix timestamp in seconds
     */
    private long lastSignOfLife;
    /**
     * Unix timestamp in seconds
     */
    private long lastReminder;
    /**
     * Time interval in seconds
     */
    private long deltaReminder;
    /**
     * Time interval in seconds
     */
    private long deltaDeathTime;
    private boolean isDead;
    private boolean allActionsCompleted;

    private PersonalData personalData;

    private List<BaseAction> actions;

   /* @PersistenceConstructor
    public UserHandle(ObjectId UID, String emailAddress, PersonalData personalData, String alias, long lastSignOfLife, long lastReminder, long deltaReminder, long deltaDeathTime, boolean isDead, boolean isVerified, Iterable<BaseAction> actions) {
        this(emailAddress, personalData, alias, lastSignOfLife, lastReminder, deltaReminder, deltaDeathTime, isDead, isVerified, actions);
        this.UID = UID;
    }
*/

    public UserHandle() {

    }

    public UserHandle(String emailAddress, String password, PersonalData personalData, List<GrantedAuthority> authorities) {
        this(emailAddress, password, authorities, true, true, true,
                true /*TODO false*/, -1, -1, -1, -1, false,
                personalData, new ArrayList<BaseAction>(), false);
    }

    public UserHandle(String emailAddress, String password, List<GrantedAuthority> authorities) {
        this(emailAddress, password, null, authorities);

    }

    public UserHandle(String emailAddress, String password, List<GrantedAuthority> authorities,
                      boolean accountNonExpired, boolean accountNonLocked, boolean credentialsNonExpired,
                      boolean isVerified, long lastSignOfLife, long lastReminder, long deltaReminder,
                      long deltaDeathTime, boolean isDead,
                      PersonalData personalData, List<BaseAction> actions, boolean allActionsCompleted) {

        this.username = emailAddress;
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
        this.allActionsCompleted = allActionsCompleted;
    }

    public boolean isValidNewUser() {
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

    public boolean areAllActionsCompleted() {
        return allActionsCompleted;
    }

    public void setAllActionsCompleted(boolean allActionsCompleted) {
        this.allActionsCompleted = allActionsCompleted;
    }

    public List<BaseAction> getActions() {
        return actions;
    }

    public void setActions(List<BaseAction> actions) {
        this.actions = actions;
    }

    public void addAction(BaseAction action) {
        this.actions.add(action);
    }

    public String getAuthoritiesAsString() {
        String out = "";
        for (GrantedAuthority a : authorities) {
            out += a.getAuthority() + "\n";
        }
        return out;
    }

    public void addAuthority(String role) {
        authorities.add(new SimpleGrantedAuthority(role));
    }

    public GrantedAuthority hasAuthority(String role) {
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

    @Deprecated
    public String getAlias() {
        return getUsername();
    }

    @Deprecated
    public String getEmailAddress() {
        return getUsername();
    }

    public PersonalData getPersonalData() {
        return personalData;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
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

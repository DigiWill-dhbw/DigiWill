package de.digiwill.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Document(collection = "users")
@TypeAlias("user")
public class UserHandle implements UserDetails {
    @Id
    @Field("_id")
    private ObjectId UID;

    /**
     * Email Address
     */
    private String emailAddress;
    private String password;
    private AuthoritySet authorities;
    private UserBooleans userBooleans;

    private UserTimestamps timestamps;

    /**
     * Time interval in seconds
     */
    private long deltaReminder;
    /**
     * Time interval in seconds
     */
    private long deltaDeathTime;
    private boolean isDead;

    private PersonalData personalData;

    private UserActionSet userActionSet;

   /* @PersistenceConstructor
    public UserHandle(ObjectId UID, String emailAddress, PersonalData personalData, String alias, long lastSignOfLife, long lastReminder, long deltaReminder, long deltaDeathTime, boolean isDead, boolean isVerified, Iterable<BaseAction> actions) {
        this(emailAddress, personalData, alias, lastSignOfLife, lastReminder, deltaReminder, deltaDeathTime, isDead, isVerified, actions);
        this.UID = UID;
    }
*/

    public UserHandle() {

    }

    public UserHandle(String emailAddress, String password, PersonalData personalData, AuthoritySet authorities) {
        this(emailAddress, password, authorities,
                UserBooleans.getInitial(), UserTimestamps.getInitial(), -1, -1, false,
                personalData, UserActionSet.getInitial());
    }

    public UserHandle(String emailAddress, String password, AuthoritySet authorities) {
        this(emailAddress, password, PersonalData.getInitial(), authorities);
    }

    public UserHandle(String emailAddress, String password, AuthoritySet authorities,
                      UserBooleans userBooleans, UserTimestamps timestamps, long deltaReminder,
                      long deltaDeathTime, boolean isDead,
                      PersonalData personalData, UserActionSet userActionSet) {

        this.emailAddress = emailAddress;
        this.password = password;
        this.authorities = authorities;//Collections.unmodifiableSet(AuthoritySet.sortAuthorities(authorities));
        this.userBooleans = userBooleans;
        this.timestamps = timestamps;
        this.deltaReminder = deltaReminder;
        this.deltaDeathTime = deltaDeathTime;
        this.isDead = isDead;
        this.personalData = personalData;
        this.userActionSet = userActionSet;
    }

    public ObjectId getUID() {
        return UID;
    }

    public long getLastSignOfLife() {
        return timestamps.getLastSignOfLife();
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

    public void setDead() {
        isDead = true;
        userBooleans.setAccountNonLocked(false);
    }

    public boolean isVerified() {
        return userBooleans.isVerified();
    }

    public void setVerified(boolean verified) {
        userBooleans.setVerified(verified);
    }

    public boolean areAllActionsCompleted() {
        return userActionSet.areAllActionsCompleted();
    }

    public void setAllActionsCompleted() {
        userActionSet.setAllActionsCompleted();
    }

    public List<BaseAction> getActions() {
        return userActionSet.getActions();
    }

    public void addAction(BaseAction action) {
        userActionSet.add(action);
    }

    public void replaceAction(int index, BaseAction action){
        userActionSet.replace(index, action);
    }

    public void removeAction(int index){
        userActionSet.remove(index);
    }

    public String getAuthoritiesAsString() {
       return authorities.toString();
    }

    public void addAuthority(String role) {
        authorities.addAuthority(role);
    }

    public GrantedAuthority getAuthorityByRoleName(String role) {
        return authorities.getAuthorityByRoleName(role);
    }

    public void removeAuthority(GrantedAuthority auth){
        authorities.removeAuthority(auth);
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public PersonalData getPersonalData() {
        return personalData;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities.getAuthorities();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return getEmailAddress();
    }

    @Override
    public boolean isAccountNonExpired() {
        return userBooleans.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return userBooleans.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return userBooleans.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return userBooleans.isVerified();
    }

    public long getLastInteractionWithUser(){
        return timestamps.getLastInteraction();
    }

    public void setLastReminder(long lastReminder){
        timestamps.setLastReminder(lastReminder);
    }

    public void sendSignOfLife() {
        timestamps.sendLifeSign();
    }
}
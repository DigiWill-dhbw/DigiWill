package de.digiwill.model;

import de.digiwill.util.SecurityHelper;
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
    private UserDeltaTimes deltaTimes;

    private boolean isDead;

    private PersonalData personalData;

    private ActionSet actionSet;

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
                UserBooleans.getInitial(), UserTimestamps.getInitial(), UserDeltaTimes.getInitial(), false,
                personalData, ActionSet.getInitial());
    }

    public UserHandle(String emailAddress, String password, AuthoritySet authorities) {
        this(emailAddress, password, PersonalData.getInitial(), authorities);
    }

    public UserHandle(String emailAddress, String password, AuthoritySet authorities,
                      UserBooleans userBooleans, UserTimestamps timestamps, UserDeltaTimes deltaTimes, boolean isDead,
                      PersonalData personalData, ActionSet actionSet) {

        this.emailAddress = emailAddress;
        this.password = password;
        this.authorities = authorities;
        this.userBooleans = userBooleans;
        this.timestamps = timestamps;
        this.deltaTimes = deltaTimes;
        this.isDead = isDead;
        this.personalData = personalData;
        this.actionSet = actionSet;
    }

    public ObjectId getUID() {
        return UID;
    }

    public long getLastSignOfLife() {
        return timestamps.getLastSignOfLife();
    }

    public long getDeltaReminder() {
        return deltaTimes.getDeltaReminder();
    }

    public void setDeltaReminder(long deltaReminder) {
        deltaTimes.setDeltaReminder(deltaReminder);
    }

    public long getDeltaDeathTime() {
        return deltaTimes.getDeltaDeathTime();
    }

    public void setDeltaDeathTime(long deltaDeathTime) {
        deltaTimes.setDeltaDeathTime(deltaDeathTime);
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

    public int getWebhookActionIdx() {
        int idx = -1;
        int counter = 0;
        List<BaseAction> actions = this.getActions();
        for (BaseAction action : actions
        ) {
            if (action instanceof WebhookAction) {
                idx = counter;
                break;
            } else {
                counter ++;
            }
        }
        return idx;
    }

    public WebhookAction getWebhook() {
        int idx = getWebhookActionIdx();
        if(idx != -1) {
            return (WebhookAction) this.getActions().get(idx);
        } else {
            return null;
        }
    }

    public boolean areAllActionsCompleted() {
        return actionSet.areAllActionsCompleted();
    }

    public List<BaseAction> getActions() {
        return actionSet.getActions();
    }

    public void addAction(BaseAction action) {
        actionSet.add(action);
    }

    public void replaceAction(int index, BaseAction action){
        actionSet.replace(index, action);
    }

    public void removeAction(int index){
        actionSet.remove(index);
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

    public void setPassword(String password) {
        this.password = SecurityHelper.encodePassword(password);
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

    public void executeActions() {
        actionSet.executeActions();
    }
}
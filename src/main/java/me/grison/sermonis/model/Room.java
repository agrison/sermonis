package me.grison.sermonis.model;

import org.springframework.util.StringUtils;

/**
 * A chat room.
 *
 * @author Alexandre Grison <a.grison@gmail.com>
 */
public class Room {
    public String roomId;
    public String password;

    public Room(String roomId, String password) {
        this.roomId = roomId;
        this.password = password;
    }
    
    public String getPassword() {
    	return this.password;
    }
    
    public Boolean getHasPassword() {
    	return !StringUtils.isEmpty(this.password);
    }
}

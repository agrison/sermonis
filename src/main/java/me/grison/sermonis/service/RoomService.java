package me.grison.sermonis.service;

import java.util.List;

import me.grison.sermonis.model.ChatMessage;
import me.grison.sermonis.model.Room;

/**
 * Room Service.
 *
 * @author Alexandre Grison <a.grison@gmail.com>
 */
public interface RoomService {
	/**
	 * Find the room with the given id.
	 * @return the room.
	 */
	public Room findRoom(final String roomId);
	
	/**
	 * Find all the rooms order by name asc.
	 * @return the list of rooms.
	 */
	public List<Room> findAllRooms();
	
	/**
	 * Add a room with the given id and password.
	 * @param roomId the room id
	 * @param roomPassword the password
	 */
	public void addRoom(final String roomId, final String roomPassword);
	
    /**
     * Find all the messages in a room ordered by date asc.
     * @param roomId the room id.
     * @return the messages in the given room.
     */
    public List<ChatMessage> findAllMessagesInRoom(String roomId);
    
    /**
     * Add a new message in a room.
     * @param roomId the room id.
     * @param user the user to whom the message belongs.
     * @param message the message.
     * @param color the user color.
     * @param markdown whether the message is in markdown.
     * @param thinking whether the user typed /me
     */
    public void addMessageInRoom(String roomId, String user, String message, String color, boolean markdown, boolean thinking);
}

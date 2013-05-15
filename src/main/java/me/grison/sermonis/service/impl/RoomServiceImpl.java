package me.grison.sermonis.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import me.grison.sermonis.model.ChatMessage;
import me.grison.sermonis.model.Room;
import me.grison.sermonis.repository.MessageRepository;
import me.grison.sermonis.repository.RoomRepository;
import me.grison.sermonis.service.RoomService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Room service implementation.
 *
 * @author Alexandre Grison <a.grison@gmail.com>
 */
@Service
public class RoomServiceImpl implements RoomService {
    @Autowired RoomRepository roomRepository;
    @Autowired MessageRepository messageRepository;

    public Room findRoom(String roomId) {
        return roomRepository.findByRoomId(roomId);
    }
    
    public List<Room> findAllRooms() {
    	List<Room> rooms = new ArrayList<Room>();
    	for (Room room : roomRepository.findAll()) {
    		rooms.add(room);
    	}
    	return rooms;
    }

    public void addRoom(String roomId, String password) {
    	roomRepository.save(new Room(roomId, password));
    }

    public List<ChatMessage> findAllMessagesInRoom(String roomId) {
        return messageRepository.findByRoomIdOrderByCreatedAtAsc(roomId);
    }
    
    public void addMessageInRoom(String roomId, String user, String message, String color, boolean markdown, boolean thinking) {
        messageRepository.save(new ChatMessage(UUID.randomUUID().toString(), roomId, new Date(), message, user, color, markdown, thinking));
    }
}

package me.grison.sermonis.repository;

import java.util.List;

import me.grison.sermonis.model.ChatMessage;

import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Message repository.
 *
 * @author Alexandre Grison <a.grison@gmail.com>
 */
public interface MessageRepository extends PagingAndSortingRepository<ChatMessage, String> {
	/**
	 * Find the messages in a room whose roomId is the given parameter.
	 * 
	 * @param roomId the room id.
	 * @return the list of messages in that room.
	 */
    List<ChatMessage> findByRoomIdOrderByCreatedAtAsc(String roomId);
}
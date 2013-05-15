package me.grison.sermonis.repository;

import me.grison.sermonis.model.Room;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Room repository.
 *
 * @author Alexandre Grison <a.grison@gmail.com>
 */
public interface RoomRepository extends PagingAndSortingRepository<Room, String> {
	/**
	 * Find a room with its roomId.
	 * 
	 * @param roomId the room id
	 * @return the room.
	 */
    Room findByRoomId(String roomId);
}
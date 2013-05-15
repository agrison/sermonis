package me.grison.sermonis.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import me.grison.sermonis.async.Message;
import me.grison.sermonis.async.Response;
import me.grison.sermonis.conf.SermonisConfig;
import me.grison.sermonis.cookie.CookieUtils;
import me.grison.sermonis.crypto.CryptoUtils;
import me.grison.sermonis.model.ChatMessage;
import me.grison.sermonis.model.Room;
import me.grison.sermonis.service.RoomService;

import org.atmosphere.cpr.Broadcaster;
import org.atmosphere.cpr.BroadcasterFactory;
import org.atmosphere.cpr.Meteor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

/**
 * Handles rooms requests.
 *
 * @author Alexandre Grison <a.grison@gmail.com>
 */
@Controller
public class RoomController {
    /** The Room Service to retrieve data from Mongodb */
    @Autowired
    private RoomService roomService;
    /** The Atmosphere BroadcasterFactory */
    private final BroadcasterFactory broadcasterFactory;
    @Autowired 
    private Gson gson;
    @Autowired
    private SermonisConfig config;
    
    private final Map<String, Broadcaster> broadCasts = new HashMap<String, Broadcaster>();

    /**
     * Instantiates a new instance of the CometController class.
     * @param bf A BroadcasterFactory (injected as a singleton) that enables client code to look up broadcasters.
     */
    @Autowired
    public RoomController(BroadcasterFactory broadcasterFactory) {
        this.broadcasterFactory = broadcasterFactory;
    }
    
    /**
     * A room does not exist already, a password may be provided in order to init it.
     * @return the room-init view.
     */
    @RequestMapping(value = "/room-{roomId}/init", method = RequestMethod.GET) 
    public String initRoom(final @PathVariable("roomId") String roomId, Model model) {
    	model.addAttribute("roomId", roomId);
    	return "room-init";
    }
    
    /**
     * Init a room with a password if it does not exist.
     * 
     * @param roomId the room Id
     * @param password the password.
     * @return the room view
     * @throws Exception 
     */
    @RequestMapping(value = "/room-{roomId}/init", method = RequestMethod.POST)
    public String initRoom(@PathVariable("roomId") String roomId, //
    		@RequestParam("password") String password, //
    		HttpServletResponse response) throws Exception {
    	Room room = roomService.findRoom(roomId);
    	if (room == null) {
    		final String encryptedPassword = encryptPassword(password);
    		roomService.addRoom(roomId, encryptedPassword);
    		// a user has just created a room, no need for him to re-type the password
    		CookieUtils.cookie(response, roomId + "-password", encryptedPassword);
    		// TODO: encrypt something proving he's the administrator
    		CookieUtils.cookie(response, roomId + "-admin", "true");
    	}
    	return "redirect:/room-" + roomId;
    }
    
    /**
     * Ask a user for a password to enter the room.
     * 
     * @return the room-init view.
     */
    @RequestMapping(value = "/room-{roomId}/access", method = RequestMethod.GET) 
    public String accessPasswordProtectedRoom(final @PathVariable("roomId") String roomId, Model model) {
    	model.addAttribute("roomId", roomId);
    	return "room-access";
    }
    
    /**
     * Init a room with a password if it does not exist.
     * 
     * @param roomId the room Id
     * @param password the password.
     * @return the room view
     * @throws Exception 
     */
    @RequestMapping(value = "/room-{roomId}/access", method = RequestMethod.POST)
    public String accessPasswordProtectedRoom(@PathVariable("roomId") String roomId, //
    		@RequestParam("password") String password, //
    		HttpServletRequest request, HttpServletResponse response) throws Exception {
    	Room room = roomService.findRoom(roomId);
    	if (room != null) {
    		final String encryptedPassword = encryptPassword(password);
    		if (encryptedPassword.equals(room.getPassword())) {
    			// set the cookie for further access
    			CookieUtils.cookie(response, roomId + "-password", encryptedPassword);
    		}
    	}
    	return "redirect:/room-" + roomId;
    }
    
    /**
     * Shows a specific room.
     * 
     * @param roomId the room id
     * @param model the Spring model
     * @param session the current session.
     * @return the view to be displayed (room.jsp)
     */
    @RequestMapping(value = "/room-{roomId}", method = RequestMethod.GET)
    public String room(@PathVariable("roomId") String roomId, //
    		@RequestParam(value="withPassword", required=false) String withPassword, //
    		Model model, HttpServletRequest request, HttpSession session) {
    	// guard
        if (roomId == null) {
            return newRoomUrl();
        }
        // if room does not exist
        Room room = roomService.findRoom(roomId);
        if (room == null) {
        	// create the room with no password
        	if (withPassword == null) {
        		roomService.addRoom(roomId, "");
        	} else {
        		// redirect to ask user for password
        		return "redirect:/room-" + roomId + "/init";
        	}
        }
        room = roomService.findRoom(roomId);
        if (!StringUtils.isEmpty(room.getPassword())) {
        	System.out.println("Room is password protected. Checking user password.");
        	// retrieve a password from cookie
        	final String password = getPasswordFromCookie(roomId, request);
        	if (!room.getPassword().equals(password)) {
        		return "redirect:/room-" + roomId + "/access";
        	}
        }
        System.out.println("Someone is entering room: " + roomId);
        Broadcaster broadcaster = getBroadcaster(roomId);
        broadcaster.awaitAndBroadcast(jsonResponse("@Sermonis", "has allowed someone to enter the room", "gray", false, true), 1, TimeUnit.SECONDS);
        List<ChatMessage> messages = roomService.findAllMessagesInRoom(roomId);
        model.addAttribute("messages", messages);
        model.addAttribute("messagesJson",gson.toJson(messages));
        model.addAttribute("roomId", roomId);
        return "room";
    }
    
    /**
     * Retrieve a password from the user cookies.
     * 
     * @param roomId the room Id
     * @param request the request.
     * @return the cookie value if found
     */
    private final String getPasswordFromCookie(final String roomId, final HttpServletRequest request) {
    	return CookieUtils.cookie(request, roomId + "-password");
    }
    
    /**
     * Get the broadcast from cache if exists.
     * @param roomId the room id
     * @return the Broadcaster associated to the room id
     */
    private Broadcaster getBroadcaster(String roomId) {
        if (broadCasts.containsKey(roomId))
            return broadCasts.get(roomId);
        final Broadcaster broadcaster = this.broadcasterFactory.lookup("/async/room-" + roomId, true);
        broadCasts.put(roomId, broadcaster);
        return broadcaster;
    }
    

    /**
     * Post a new message and broadcast to that specific room listeners.
     * @param message a message to be posted.
     */
    @RequestMapping(value = "/room-{roomId}", method = RequestMethod.POST)
    public @ResponseBody String newMessage(@RequestBody Message message, @PathVariable("roomId") String roomId) throws Exception {
        if (!roomId.equals(message.getRoomId())) {
            System.out.println("Mismatch! " + roomId + " / " + message.getRoomId());
            return "nok";
        }
        System.out.println("New message from user " + message.getFromUser() + " [" + message.getText() + "] for room: " + roomId);
        roomService.addMessageInRoom(roomId, message.getFromUser(), message.getText(), message.getColor(), message.getMarkdown(), message.getThinking());
        getBroadcaster(roomId).broadcast(jsonResponse(message.getFromUser(), message.getText(), message.getColor(), message.getMarkdown(), message.getThinking()));
        return "ok";
    }
    
    /**
     * Generate a new room url.
     * @return the new room url as a redirect action.
     */
    public String newRoomUrl() {
        return "redirect:/" + UUID.randomUUID().toString();
    }
    
    /**
     * Incoming comet request for a specific chat room.
     * @param meteor the current comet session.
     */
    @ResponseBody
    @RequestMapping(value = "/async/room-{roomId}")
    public void comet(final Meteor meteor, @PathVariable("roomId") String roomId) {
        System.out.println("Someone is connecting to room: " + roomId + " via comet");
        final Broadcaster b = getBroadcaster(roomId);
        meteor.setBroadcaster(b);
        meteor.suspend(-1);
    }
    
    /**
     * Return a response object as JSON.
     * @param user
     * @param text
     * @return
     */
    public String jsonResponse(final String user, final String text, final String color, boolean markdown, boolean thinking) {
        Response response = new Response(user, text, color, markdown, thinking);
        return gson.toJson(response);
    }
    
    /**
     * Encrypt the given password using the current Sermonis secret token.
     * @param password the password to be encrypted
     * @return the encrypted password
     */
    private String encryptPassword(String password) throws Exception {
    	return CryptoUtils.encrypt(config.getSecretToken(), password);
    }
}
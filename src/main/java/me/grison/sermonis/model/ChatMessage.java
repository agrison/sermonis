package me.grison.sermonis.model;
import java.util.Date;

/**
 * A chat message.
 *
 * @author Alexandre Grison <a.grison@gmail.com>
 */
public class ChatMessage {
    private String id;
    private String roomId;
    private Date createdAt;
    private String text;
    private String fromUser;
    private String color;
    private boolean markdown;
    private boolean thinking;

    /** Default constructor. */
    public ChatMessage() {
    }

    public ChatMessage(String id, String roomId, Date createdAt, String text, String fromUser, String color, boolean markdown, boolean thinking) {
        this.id = id;
        this.roomId = roomId;
        this.createdAt = createdAt;
        this.text = text;
        this.fromUser = fromUser;
        this.color = color;
        this.markdown = markdown;
        this.thinking = thinking;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean getMarkdown() {
        return markdown;
    }

    public void setMarkdown(boolean markdown) {
        this.markdown = markdown;
    }

    public boolean getThinking() {
        return thinking;
    }

    public void setThinking(boolean thinking) {
        this.thinking = thinking;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((createdAt == null) ? 0 : createdAt.hashCode());
        result = prime * result
                + ((fromUser == null) ? 0 : fromUser.hashCode());
        result = prime * result + ((text == null) ? 0 : text.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ChatMessage other = (ChatMessage) obj;
        if (createdAt == null) {
            if (other.createdAt != null)
                return false;
        } else if (!createdAt.equals(other.createdAt))
            return false;
        if (fromUser == null) {
            if (other.fromUser != null)
                return false;
        } else if (!fromUser.equals(other.fromUser))
            return false;
        if (text == null) {
            if (other.text != null)
                return false;
        } else if (!text.equals(other.text))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return String.format("%s@%s> %s", fromUser, createdAt, text);
    }

}
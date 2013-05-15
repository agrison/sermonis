package me.grison.sermonis.async;

/**
 * Message bean read from request.
 *
 * @author Alexandre Grison <a.grison@gmail.com>
 */
public class Message {
    private String roomId;
    private String text;
    private String fromUser;
    private String color;
    private boolean markdown;
    private boolean thinking;
	
	public Message() {}

    /**
     * @return the roomId
     */
    public String getRoomId() {
        return roomId;
    }

    /**
     * @param roomId the roomId to set
     */
    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @return the fromUser
     */
    public String getFromUser() {
        return fromUser;
    }

    /**
     * @param fromUser the fromUser to set
     */
    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    /**
     * @return the color
     */
    public String getColor() {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * @return the isMarkdown
     */
    public boolean getMarkdown() {
        return markdown;
    }

    /**
     * @param isMarkdown the isMarkdown to set
     */
    public void setMarkdown(boolean markdown) {
        this.markdown = markdown;
    }

    public boolean getThinking() {
        return thinking;
    }

    public void setThinking(boolean thinking) {
        this.thinking = thinking;
    }
}

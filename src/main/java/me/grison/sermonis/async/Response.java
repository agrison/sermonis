package me.grison.sermonis.async;

import java.util.Date;

/**
 * Response sent to ajax calls.
 *
 * @author Alexandre Grison <a.grison@gmail.com>
 */
public class Response {
	private String text;
	private String fromUser;
	private long time;
	private String color;
	private boolean markdown;
	private boolean thinking;
	
	public Response() {}
	
	public Response(final String user, final String text, final String color, final boolean markdown, final boolean thinking) {
		this.fromUser = user;
		this.text = text;
		this.color = color;
		this.markdown = markdown;
		this.time = new Date().getTime();
		this.thinking = thinking;
	}

	public String getText() {
		return text;
	}

	public String getFromUser() {
		return fromUser;
	}

	public long getTime() {
		return time;
	}
	
    public String getColor() {
        return color;
    }

    public boolean getMarkdown() {
        return markdown;
    }
    
    public boolean getThinking() {
        return thinking;
    }
}

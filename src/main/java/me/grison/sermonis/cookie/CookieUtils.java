package me.grison.sermonis.cookie;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Some cookie management utilities.
 *
 * @author Alexandre Grison <a.grison@gmail.com>
 */
public class CookieUtils {
	/**
	 * Add a cookie to the given response.
	 * 
	 * @param response the response
	 * @param name the name of the cookie
	 * @param value the cookie value
	 */
	public static void cookie(HttpServletResponse response, String name, String value) {
		response.addCookie(new Cookie(name, value));
	}
	
	/**
	 * Retrieve a cookie from the request whose name is the given parameter.
	 * 
	 * @param request the request
	 * @param name the cookie name
	 * @return the cookie value
	 */
	public static String cookie(HttpServletRequest request, String name) {
		for (Cookie cookie: request.getCookies()) {
    		if (cookie.getName().equals(name)) {
    			return cookie.getValue();
    		}
    	}
    	return "";
	}
}

package me.grison.sermonis.controller;

import static me.grison.sermonis.crypto.CryptoUtils.encrypt;
import static me.grison.sermonis.crypto.CryptoUtils.md5;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.grison.sermonis.conf.SermonisConfig;
import me.grison.sermonis.cookie.CookieUtils;
import me.grison.sermonis.service.RoomService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Handles admin requests.
 *
 * @author Alexandre Grison <a.grison@gmail.com>
 */
@Controller
public class AdminController {
    /** The Room Service to retrieve data from Mongodb */
    @Autowired
    private RoomService roomService;
    @Autowired
    private SermonisConfig config;
    
    /**
     * Admin view.
     * @return the admin view.
     */
    @RequestMapping(value = "/admin", method = RequestMethod.GET) 
    public String admin(HttpServletRequest request, Model model) throws Exception {
    	final String encryptedPassword = CookieUtils.cookie(request, "sermonis-admin-password");
    	if (!encryptedPassword.equals(config.getAdminPassword())) {
    		return "redirect:/admin/access";
    	}
    	model.addAttribute("rooms", roomService.findAllRooms());
    	return "admin";
    }
    
    /**
     * Admin view (password post from /admin/access).
     * @return the admin view.
     */
    @RequestMapping(value = "/admin", method = RequestMethod.POST) 
    public String admin(@RequestParam("password") String password, HttpServletResponse response) throws Exception {
    	final String encryptedPassword = md5(encrypt(config.getSecretToken(), password));
    	response.addCookie(new Cookie("sermonis-admin-password", encryptedPassword));
    	return "redirect:/admin";
    }
    
    /**
     * Admin access view.
     * @return the admin view.
     */
    @RequestMapping(value = "/admin/access", method = RequestMethod.GET) 
    public String adminAccess(Model model) {
    	return "admin-access";
    }

}

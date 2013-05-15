package me.grison.sermonis.conf;

import java.io.IOException;
import java.util.Properties;

/**
 * Sermonis config which contains the secret token and the admin password (hash).
 *
 * @author Alexandre Grison <a.grison@gmail.com>
 */
public class SermonisConfig {
	final String secretToken;
	final String adminPassword;
	
	public SermonisConfig(String secretToken, String adminPassword) {
		this.secretToken = secretToken;
		this.adminPassword = adminPassword;
	}
	
	public static SermonisConfig load() {
		System.out.println("Loading config from sermonis.properties");
		Properties props = new Properties();
		try {
			props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("/sermonis.properties"));
		} catch (IOException e) {
		}
		System.out.println("Secret token: " + props.getProperty("secretKey"));
		return new SermonisConfig(props.getProperty("secretKey"), props.getProperty("adminPassword"));
	}
	
	public String getSecretToken() {
		return this.secretToken;
	}
	
	public String getAdminPassword() {
		return this.adminPassword;
	}
}

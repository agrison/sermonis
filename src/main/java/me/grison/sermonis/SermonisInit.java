package me.grison.sermonis;

import static me.grison.sermonis.crypto.CryptoUtils.encrypt;
import static me.grison.sermonis.crypto.CryptoUtils.md5;
import static me.grison.sermonis.crypto.CryptoUtils.shuffleString;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.UUID;

/**
 * Class called from mvn java:exec to generate secret key and admin password.
 *
 * @author Alexandre Grison <a.grison@gmail.com>
 */
public class SermonisInit {
	public static final String LOGO = 
			"#                                          _     \n" +     //
			"#    ________  _________ ___  ____  ____  (_)____\n" +     //
			"#   / ___/ _ \\/ ___/ __ `__ \\/ __ \\/ __ \\/ / ___/\n" + //
			"#  (__  )  __/ /  / / / / / / /_/ / / / / (__  ) \n" +     //
			"# /____/\\___/_/  /_/ /_/ /_/\\____/_/ /_/_/____/  \n" +   //
			"#                                               \n";
	public static void main(String[] args) throws Exception {
		System.out.println(LOGO.replaceAll("#", "\t"));
		System.out.println("You're about to generate a secret key and admin password for sermonis.");
		System.out.println("\nGenerating the secret key...");
		String secretKey = shuffleString(
				UUID.randomUUID().toString() + "-" + //
						md5(UUID.randomUUID().toString() + "-" + new Date().toString())
		);
		System.out.println("Please provide an admin password: ");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String password = br.readLine();
		if ("".equals(password.trim())) {
			System.out.println("Leaving sermonis init.\nYou will have to modify the sermonis.properties file yourself.");
			return;
		}
		String encryptedPassword = md5(encrypt(secretKey, password));
		System.out.println("Updating sermonis.properties...");
		updatePropertyFile(secretKey, encryptedPassword);
		System.out.println("All done!");
	}
	
	private static void updatePropertyFile(String secretKey, String encryptedPassword) throws Exception {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		File props = new File(classLoader.getResource("sermonis.properties").getFile());
		if (!props.exists()) {
			props.createNewFile();
		}
		FileWriter fw = new FileWriter(props);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(String.format(//
				"%s\n" + //
				"# Sermonis property file generated on %s\n\n" +//
				"# Sermonis secret token used to encrypt password and browser request information into cookies\n" + // 
				"# to secure access to password protected room and admin interface\n" + //
				"secretKey=%s\n\n" + //
				"# MD5 hash of the admin password AES encrypted with the above secret token\n" + //
				"adminPassword=%s\n", //
					LOGO, new Date().toString(), secretKey, encryptedPassword
		));
		bw.close();
	}
}

package me.grison.sermonis.conf;

import org.cloudfoundry.runtime.env.CloudEnvironment;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Spring Application Context Initializer to change 
 * the Spring profile if we're running on CloudFoundry.
 *
 * @author Alexandre Grison <a.grison@gmail.com>
 */
public class CloudApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
	public void initialize(ConfigurableApplicationContext applicationContext) {
		CloudEnvironment env = new CloudEnvironment();
		if (env.getInstanceInfo() != null) {
			System.out.println("cloud API: " + env.getCloudApiUri());
			applicationContext.getEnvironment().setActiveProfiles("cloud");
			System.out.println("sermonis is using `cloud` profile");
		}
		else {
			applicationContext.getEnvironment().setActiveProfiles("default");
			System.out.println("sermonis is using `default` profile");
		}
	}
}
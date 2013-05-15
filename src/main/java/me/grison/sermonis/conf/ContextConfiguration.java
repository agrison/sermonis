package me.grison.sermonis.conf;

import org.atmosphere.cpr.BroadcasterFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.google.gson.Gson;

/**
 * Spring Web-App Configuration.
 */
@Configuration
@Import(WebAppConfiguration.class)
@ComponentScan(basePackages = "me.grison.sermonis", excludeFilters = @ComponentScan.Filter(Configuration.class))
@EnableMongoRepositories("me.grison.sermonis.repository")
@ImportResource("classpath:/spring/applicationContext.xml")
public class ContextConfiguration {
    @Bean
    public BroadcasterFactory broadcasterFactory() {
        return BroadcasterFactory.getDefault();
    }
    
    @Bean
    public Gson gson() {
        return new Gson();
    }
    
    @Bean
    public SermonisConfig sermonisConfig() {
    	return SermonisConfig.load();
    }
}

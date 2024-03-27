package framework.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
	
	@Value("${websocket.server}")
	private String server;
	
	@Value("${websocket.local}")
	private String local;

	@Value("${websocket.coffee-server}")
	private String coffeeServer;

	   @Override
	    public void registerStompEndpoints(StompEndpointRegistry registry) {
	        registry.addEndpoint("/socket")
	                .setAllowedOrigins(server,local,coffeeServer)
	                .withSockJS();
	    }
	   
	    @Override
	    public void configureMessageBroker(MessageBrokerRegistry registry) {
	        registry.setApplicationDestinationPrefixes("/app")
	                .enableSimpleBroker("/user", "/post" , "/recommend","/deposit","/withdraw" ,"/admin/notification/deposit","/admin/notification/withdraw","/admin/notification/promotion","/admin/notification/register");
	    }
	    

}

package es.urjc.TicTakTicket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

@EnableCaching
@SpringBootApplication
public class TicTakTicketApplication {

	public static void main(String[] args) {
		SpringApplication.run(TicTakTicketApplication.class, args);
		
	}
	
	@Bean
	  public JedisConnectionFactory redisConnectionFactory() {
	    return new JedisConnectionFactory();
	  }
	
	@Bean
	public CacheManager cacheManager() {
		return new ConcurrentMapCacheManager("eventos");
	}

}

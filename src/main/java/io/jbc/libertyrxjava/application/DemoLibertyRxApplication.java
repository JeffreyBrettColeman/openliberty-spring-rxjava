package io.jbc.libertyrxjava.application;

import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.repository.Tailable;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.mongodb.ConnectionString;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;

import io.netty.channel.nio.NioEventLoopGroup;
import io.reactivex.Flowable;
import io.reactivex.Single;
import reactor.core.publisher.Flux;

@SpringBootApplication
@RestController
@CrossOrigin
@EnableReactiveMongoRepositories
public class DemoLibertyRxApplication extends AbstractReactiveMongoConfiguration {

	@Autowired
	AccountRxJavaRepository accountReactiveRepository;

	private NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup();

	private final ConnectionString mongoConnection = new ConnectionString("mongodb://localhost:27017/test");

	public static void main(String[] args) {
		SpringApplication.run(DemoLibertyRxApplication.class, args);
	}

	@PreDestroy
	public void shutDownEventLoopGroup() {
		eventLoopGroup.shutdownGracefully();
	}

	@Override
	protected String getDatabaseName() {
		return "test";
	}

	@Override
	public MongoClient reactiveMongoClient() {
		return MongoClients.create(mongoConnection);
	}

	@GetMapping("/sse/accounts")
	public SseEmitter rxAccounts() {
		SseEmitter sseEmitter = new SseEmitter();
		accountReactiveRepository.findAll().doOnNext(sseEmitter::send);
		return sseEmitter;
	}
	
	@GetMapping("/accountAdd")
	public Single<Account> accountAdd() {
		return accountReactiveRepository.save(new Account());
	}

}

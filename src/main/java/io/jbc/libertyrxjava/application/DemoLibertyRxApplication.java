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

import com.mongodb.ConnectionString;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;

import io.netty.channel.nio.NioEventLoopGroup;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootApplication
@RestController
@CrossOrigin
@EnableReactiveMongoRepositories
public class DemoLibertyRxApplication extends AbstractReactiveMongoConfiguration {

	@Autowired
	AccountReactiveRepository accountReactiveRepository;

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

	@Tailable
	@GetMapping("/accounts")
	public Flux<Account> accounts() {
		return accountReactiveRepository.findAll();
	}

	@GetMapping("/accountAdd")
	public Mono<Account> accountAdd() {
		return accountReactiveRepository.insert(new Account());
	}

}

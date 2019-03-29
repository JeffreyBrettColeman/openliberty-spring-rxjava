package io.jbc.libertyrxjava.application;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.mongodb.ConnectionString;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;

import io.netty.channel.nio.NioEventLoopGroup;
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
	private Gson gson = new Gson();
	private final ConnectionString mongoConnection = new ConnectionString("mongodb://localhost:27017/test");

	public static void main(String[] args) {
		SpringApplication.run(DemoLibertyRxApplication.class, args);
	}

	@Override
	protected String getDatabaseName() {
		return "test";
	}

	@Override
	public MongoClient reactiveMongoClient() {
		return MongoClients.create(mongoConnection);
	}

	@GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE, path = "/account/{name}")
	Flux<Object> events(@PathVariable("name") String name) {

		return Flux.interval(Duration.ofSeconds(1))
				.map(sequence -> ServerSentEvent.<String>builder().id(String.valueOf(sequence)).event("periodic-event")
						.data(gson.toJson(accountReactiveRepository.findAll().blockingFirst())).build());
	}

	@GetMapping("/accountAdd")
	public Single<Account> accountAdd() {
		return accountReactiveRepository.save(new Account());
	}

}

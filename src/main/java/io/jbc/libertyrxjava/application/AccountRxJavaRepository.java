package io.jbc.libertyrxjava.application;

import org.springframework.data.repository.reactive.RxJava2CrudRepository;
import org.springframework.stereotype.Repository;

import com.mongodb.async.client.Observable;

@Repository
public interface AccountRxJavaRepository 
  extends RxJava2CrudRepository<Account, String> {
	
	Observable<Account> findByOwner(String owner);
}
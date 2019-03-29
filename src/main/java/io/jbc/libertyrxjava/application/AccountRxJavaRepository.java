package io.jbc.libertyrxjava.application;

import org.springframework.data.repository.reactive.RxJava2CrudRepository;
import org.springframework.stereotype.Repository;

import io.reactivex.Single;

@Repository
public interface AccountRxJavaRepository 
  extends RxJava2CrudRepository<Account, String> {
	
	Single<Account> findByOwner(String owner);

}
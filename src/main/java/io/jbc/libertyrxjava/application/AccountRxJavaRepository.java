package io.jbc.libertyrxjava.application;

import org.springframework.data.repository.reactive.RxJava2CrudRepository;
import org.springframework.stereotype.Repository;

import com.mongodb.async.client.Observable;

import io.reactivex.Single;

@Repository
public interface AccountRxJavaRepository 
  extends RxJava2CrudRepository<Account, String> {
 
    Observable<Account> findAllByValue(Double value);
    Single<Account> findFirstByOwner(Single<String> owner);
}
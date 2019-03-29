package com.react.demo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountReactiveRepository extends ReactiveMongoRepository<Account, String> {
}

package com.cyes.webserver.domain.problem.repository;

import com.cyes.webserver.domain.problem.entity.ProblemByUser;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProblemByUserRepository extends MongoRepository<ProblemByUser,String> {
}

package com.cyes.webserver.domain.problem.repository;
import com.cyes.webserver.domain.problem.entity.Problem;
import com.cyes.webserver.domain.problem.entity.ProblemCategory;
import com.cyes.webserver.domain.problem.entity.ProblemType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;


public interface ProblemRepository extends MongoRepository<Problem,String> ,ProblemCustomRepository {
    @Query("{ 'problem_category' : ?0 }")
    Page<Problem> findProblemByCategory(ProblemCategory category, Pageable pageable);

    @Query("{ 'problem_type' : ?0 }")
    Page<Problem> findProblemByType(ProblemType type, Pageable pageable);

    @Query("{ 'problem_category' : ?0, 'problem_type' : ?1 }")
    Page<Problem> findProblemByCategoryAndType(ProblemCategory category, ProblemType type, Pageable pageable);


}

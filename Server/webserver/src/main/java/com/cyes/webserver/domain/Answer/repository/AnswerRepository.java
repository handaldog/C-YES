package com.cyes.webserver.domain.Answer.repository;

import com.cyes.webserver.domain.Answer.entity.Answer;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.util.List;
import java.util.Optional;


public interface AnswerRepository extends MongoRepository<Answer,String> {
    @Override
    <S extends Answer> List<S> saveAll(Iterable<S> entities);

    @Query("{ 'member_id' : ?0, 'quiz_id' : ?1 }")
    List<Answer> findAnswerByMemberIdAndQuizId(Long memberId, Long quizId, Sort sort);

    @Query("{ 'member_id' : ?0, 'quiz_id' : ?1, 'problem_number' : ?2 }")
    Optional<Answer> findAnswerByMemberIdAndQuizIdAndProblemNumber(Long memberId, Long quizId, Integer problemNumber);


}

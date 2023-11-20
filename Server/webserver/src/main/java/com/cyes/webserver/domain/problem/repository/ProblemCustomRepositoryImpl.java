package com.cyes.webserver.domain.problem.repository;

import com.cyes.webserver.domain.problem.entity.Problem;
import com.cyes.webserver.domain.problem.entity.ProblemCategory;
import com.cyes.webserver.domain.problem.entity.ProblemType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;

import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProblemCustomRepositoryImpl implements  ProblemCustomRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public List<Problem> findProblemByCategoryAndTypeRandom(ProblemCategory category, ProblemType type, int size) {
        Criteria criteria = new Criteria();

        if (category != null) {
            criteria = Criteria.where("problem_category").is(category);
        }

        if (type != null) {
            if (criteria.getCriteriaObject().isEmpty()) {
                // If the first criteria was not set, initialize criteria with the second field
                criteria = Criteria.where("problem_type").is(type);
            } else {
                // If the first criteria is already set, add the second one to it
                criteria = criteria.and("problem_type").is(type);
            }
        }

        MatchOperation matchStage = Aggregation.match(criteria);
        Aggregation aggregation = Aggregation.newAggregation(
                matchStage,
                Aggregation.sample(size)
        );

        AggregationResults<Problem> results = mongoTemplate.aggregate(
                aggregation, "problem", Problem.class
        );

        return results.getMappedResults();
    }
}

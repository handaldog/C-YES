package com.cyes.webserver.domain.problem.repository;

import com.cyes.webserver.domain.problem.entity.Problem;
import com.cyes.webserver.domain.problem.entity.ProblemCategory;
import com.cyes.webserver.domain.problem.entity.ProblemType;

import java.util.List;

public interface ProblemCustomRepository {
    List<Problem> findProblemByCategoryAndTypeRandom(ProblemCategory category, ProblemType type, int Size);


}

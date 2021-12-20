package com.redhat.training.health.repository;

import com.redhat.training.health.model.CovidCase;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CovidCaseRepository extends CrudRepository<CovidCase, Integer> {
}

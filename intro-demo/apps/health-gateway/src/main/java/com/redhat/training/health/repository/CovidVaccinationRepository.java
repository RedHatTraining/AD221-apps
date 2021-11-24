package com.redhat.training.health.repository;

import javax.transaction.Transactional;

import com.redhat.training.health.model.CovidVaccinationEntity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CovidVaccinationRepository extends CrudRepository<CovidVaccinationEntity, Integer> {
}

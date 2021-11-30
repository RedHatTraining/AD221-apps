package com.redhat.training.health.repository;


import com.redhat.training.health.model.CovidVaccination;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CovidVaccinationRepository extends CrudRepository<CovidVaccination, Integer> {
}

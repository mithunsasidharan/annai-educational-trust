package com.ohack.aet.repository;

import org.springframework.data.repository.CrudRepository;

import com.ohack.aet.model.TrainingEvent;

public interface EventMongoRepository extends CrudRepository<TrainingEvent, String>{}
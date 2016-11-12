package com.ohack.aet.repository;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.ohack.aet.model.TrainingEvent;

@Repository
public class EventSearchMongoRepository {
	
	@Autowired
	MongoTemplate mongoTemplate;
	
	public Collection<TrainingEvent> getTrainingEventsNotEnrolled(List<String> idList) {
		Query query = new Query();
		query.addCriteria(Criteria.where("id").nin(idList));
		return mongoTemplate.find(query,TrainingEvent.class);
	}
	
	public Collection<TrainingEvent> getActiveTraining(){
		Query query = new Query();
		query.addCriteria(Criteria.where("endDate").gt(Calendar.getInstance().getTime()));
		return mongoTemplate.find(query,TrainingEvent.class);
	}

	public List<TrainingEvent> findUpcomingEvents() {
		
		Query query = new Query();
		query.limit(3);
		query.addCriteria(Criteria.where("endDate").gte(new Date()));
		return mongoTemplate.find(query,TrainingEvent.class);		
	}
	
}
package com.ohack.aet.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ohack.aet.model.TrainingEvent;
import com.ohack.aet.repository.EventMongoRepository;

@Controller
public class EventController {
	
	@Autowired
	EventMongoRepository eventRepository;
	
	@RequestMapping("/events")
	public String event(Model model) {
		System.out.println("coming here ");
		Iterable<TrainingEvent> events = eventRepository.findAll();
		System.out.println("Events in console :"+events);
		model.addAttribute("eventList", events);
		return "events";
	}
	
	@RequestMapping(value = "/addEvent", method = RequestMethod.POST)
	public String addEvent(@ModelAttribute TrainingEvent event) {
		System.out.println("event"+event.getEventName());
		eventRepository.save(event);
		return "redirect:events";
	}

}

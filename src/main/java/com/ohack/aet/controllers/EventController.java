package com.ohack.aet.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ohack.aet.model.TrainingEvent;
import com.ohack.aet.model.User;
import com.ohack.aet.repository.EventMongoRepository;
import com.ohack.aet.repository.EventSearchMongoRepository;
import com.ohack.aet.repository.UserMongoRepository;

@Controller
public class EventController {
	
	@Autowired
	EventMongoRepository eventRepository;
	
	@Autowired
	EventSearchMongoRepository eventSearchRepository;
	
	@Autowired
	UserMongoRepository userRepository;
	
	@RequestMapping("/allEvents")
	public String event(Model model) {
		System.out.println("coming here ");
		Iterable<TrainingEvent> events = eventRepository.findAll();
		System.out.println("Events in console :"+events);
		model.addAttribute("eventList", events);
		return "events";
	}
	
	@RequestMapping(value = "/addEvent", method = RequestMethod.POST)
	public String addEvent(@ModelAttribute TrainingEvent event,BindingResult bindingResult) {
		System.out.println(event.getEventName()+"_"+System.currentTimeMillis());
		event.setId(event.getEventName()+"_"+System.currentTimeMillis());
		System.out.println("event"+event.getId());
		eventRepository.save(event);
		return "redirect:allEvents";
	}
	
/*	@RequestMapping(value = "/getEventsforUser")
	public String search(Model model,@RequestParam String userId) {
		User user = userRepository.findOne(userId);
		List<TrainingEvent> eventIdList = user.getEnrolledEvents();
		model.addAttribute("eventList", eventSearchRepository.getTrainingEventsNotEnrolled(eventIdList));
		return "redirect:events";
	}
	*/
	@RequestMapping("/addEvent")
    public String addEvent(Model model) {
        return "addEvent";
    }
	
	
	@RequestMapping(value = "/enroll")

	public String enroll(Model model, @RequestParam String eventId) {

		Map<String, Object> modelMap = model.asMap();

		String userId = (String) modelMap.get("adharID");

		User user = userRepository.findOne(userId);

		if (user.getEnrolledEvents() != null)

		{
			user.getEnrolledEvents().add(eventId);
			userRepository.save(user);

		}
		else {
			List<String> enrolledEvents = new ArrayList<>();
			enrolledEvents.add(eventId);
			userRepository.save(user);
		}

		return "redirect:allEvents";

	}

}
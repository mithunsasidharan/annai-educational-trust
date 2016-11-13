package com.ohack.aet.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

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
import com.ohack.aet.util.SmsAlerts;

@Controller
public class EventController {
	
	@Autowired
	EventMongoRepository eventRepository;
	
	@Autowired
	EventSearchMongoRepository eventSearchRepository;
	
	@Autowired
	UserMongoRepository userRepository;
	
	@RequestMapping("/allEvents")
	public String event(Model model, HttpSession session) {
		
		
		
		System.out.println("coming here ");
		Iterable<TrainingEvent> events = eventRepository.findAll();

		String url = "images/bg/bg";
		int count = 1;
		for (TrainingEvent event : events) {

			event.setImageUrl(url + count + ".jpg");
			if (event.getStartDate() != null) {
				Calendar cal = toCalendar(event.getStartDate());
				event.setMonth(new SimpleDateFormat("MMM").format(cal.getTime()));
				event.setDay(String.valueOf(cal.get(Calendar.DAY_OF_MONTH)));
			}

			if (count == 4) {
				count = 1;
			} else {
				count++;
			}

		}
		System.out.println("Events in console :"+events);
		model.addAttribute("eventList", events);
		return "events";
	}
	
	@RequestMapping(value = "/saveEvent", method = RequestMethod.POST)
	public String addEvent(@ModelAttribute TrainingEvent event,BindingResult bindingResult, HttpSession session) {
		System.out.println(event.getEventName().trim()+"_"+System.currentTimeMillis());
		event.setId(event.getEventName().trim()+"_"+System.currentTimeMillis());
		System.out.println("event"+event.getId());
		eventRepository.save(event);
		try{
		//Sms Alert
		List<User> userList = eventSearchRepository.findEligibleUsers(event);
        SmsAlerts alertObj = new SmsAlerts();
        alertObj.sendSmsAlerts(event, userList);
		}
		catch(Exception ex){
			
		}


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

	public String enroll(Model model, @RequestParam String eventId, HttpSession session) {
		System.out.println("Event Id : " + eventId);
		String admin = (String) session.getAttribute("role");
		if (null != admin && admin.equals("A")) {
			return "redirect:allEvents";
		} else {

			String adharId = (String) session.getAttribute("adharId");
			System.out.println("Adhar ID is" + adharId);

			System.out.println("Event ID is" + eventId);

			User user = userRepository.findOne(adharId);

			if (user.getEnrolledEvents() != null)

			{
				user.getEnrolledEvents().add(eventId);
				userRepository.save(user);

			} else {
				List<String> enrolledEvents = new ArrayList<>();
				enrolledEvents.add(eventId);
				user.setEnrolledEvents(enrolledEvents);
				userRepository.save(user);
			}

			List<TrainingEvent> trainingEventsNotEnrolled = (List<TrainingEvent>) eventSearchRepository
					.getTrainingEventsNotEnrolled(user.getEnrolledEvents());
			if (trainingEventsNotEnrolled != null) {
				String url = "images/bg/bg";
				int count = 1;
				for (TrainingEvent event : trainingEventsNotEnrolled) {

					event.setImageUrl(url + count + ".jpg");
					if (event.getStartDate() != null) {
						Calendar cal = toCalendar(event.getStartDate());
						event.setMonth(new SimpleDateFormat("MMM").format(cal.getTime()));
						event.setDay(String.valueOf(cal.get(Calendar.DAY_OF_MONTH)));
					}

					if (count == 4) {
						count = 1;
					} else {
						count++;
					}

				}
			} else {
				trainingEventsNotEnrolled = new ArrayList<TrainingEvent>();
			}
			model.addAttribute("eventList", trainingEventsNotEnrolled);

			return "events";

		}

	}
	
	@RequestMapping(value = "/getEligibleUsers")
	public String eligibleUsers(Model model,@RequestParam String eventId){
		TrainingEvent event = eventRepository.findOne(eventId);
		List<User> userList = eventSearchRepository.findEligibleUsers(event);
		model.addAttribute("userList", userList);
		return "eligibleUsers";
	}
	
	public Calendar toCalendar(Date date){ 
		  Calendar cal = Calendar.getInstance();
		  cal.setTime(date);
		  return cal;
		}

}
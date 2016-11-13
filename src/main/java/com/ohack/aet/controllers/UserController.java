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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ohack.aet.model.TrainingEvent;
import com.ohack.aet.model.User;

import com.ohack.aet.repository.EventSearchMongoRepository;
import com.ohack.aet.repository.UserMongoRepository;

@Controller
public class UserController {

	@Autowired
	UserMongoRepository userRepository;
	
	@Autowired
	EventSearchMongoRepository eventSearchRepository;


	@RequestMapping("/home")
	public String home(Model model, HttpSession session) {
		
		//Load data of upcoming events
		List<TrainingEvent> upcomimgEvents = getUpComingEvents();
		int count =1 ;
		for(TrainingEvent event: upcomimgEvents){
			if (event.getStartDate() != null) {
				Calendar cal = toCalendar(event.getStartDate());
				event.setMonth(new SimpleDateFormat("MMM").format(cal.getTime()));
				event.setDay(String.valueOf(cal.get(Calendar.DAY_OF_MONTH)));
			}
			model.addAttribute("event" + count, event);
			count++;
		}

		return "home";
	}

	@RequestMapping("/login")
	public String login(Model model, HttpSession session) {
		return "login";
	}

	@RequestMapping("/logout")
	public String logout(Model model, HttpSession session) {
		session.setAttribute("authenticated", false);
		session.setAttribute("userName", null);
		return "redirect:home";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(Model model, @ModelAttribute User user, HttpSession session) {

		String pageName = "login";

		// find the user
		if (user != null && user.getAadharNo() != null && user.getPassword() != null) {
			User userFound = userRepository.findOne(user.getAadharNo());
			if (userFound != null) {
				if (userFound.getPassword().equals(user.getPassword())) {
					System.out.println("user role :"+userFound.getRole());
					// TODO add check on password
					session.setAttribute("authenticated", true);
					session.setAttribute("adharId", user.getAadharNo());
					session.setAttribute("role", userFound.getRole());
					session.setAttribute("userName", userFound.getFirstName());
					pageName = "redirect:home";
				} else {
					model.addAttribute("loginFailed", "Password IS Incorrect");
				}
			} else {
				model.addAttribute("loginFailed", "User does not exist");
			}

		} else {

			model.addAttribute("loginFailed", "AadharNo or Password is empty");
		}

		return pageName;

	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String register(Model model, @ModelAttribute User user, HttpSession session) {

		List<String> errorMsg = new ArrayList<String>();
		String pageName = "register";

		// find the user
		if (user != null && user.getAadharNo() != null) {
			user.setRole("A");
			userRepository.save(user);
			session.setAttribute("authenticated", true);
			session.setAttribute("adharId", user.getAadharNo());
			session.setAttribute("userName", user.getFirstName());
//			session.setAttribute("role", "A");
			System.out.println("User authenticated");
			pageName = "redirect:home";

		} else {

			errorMsg.add("Please enter all madnditory fields");
		}

		model.addAttribute("errors", errorMsg);

		return pageName;

	}

	@RequestMapping(value = "/register")
	public String register(Model model, HttpSession session) {

		return "register";

	}

	public List<TrainingEvent> getUpComingEvents() {

		List<TrainingEvent> upComingEvents = eventSearchRepository.findUpcomingEvents();
		return upComingEvents;
	}
	
	
	@RequestMapping(value = "/profile")
	public String profile(Model model, HttpSession session) {
		return "profile";

	}

	
	public Calendar toCalendar(Date date){ 
		  Calendar cal = Calendar.getInstance();
		  cal.setTime(date);
		  return cal;
		}

}

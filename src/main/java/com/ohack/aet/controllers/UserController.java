package com.ohack.aet.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ohack.aet.model.User;
import com.ohack.aet.repository.UserMongoRepository;

@Controller
public class UserController {

	@Autowired
	UserMongoRepository userRepository;

	@RequestMapping("/home")
	public String home(Model model) {
		return "home";
	}

	@RequestMapping("/login")
	public String login(Model model) {
		return "login";
	}

	@RequestMapping("/logout")
	public String logout(Model model) {
		model.addAttribute("authenticated", false);
		return "home";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(Model model, @ModelAttribute User user) {

		String pageName = "login";

		// find the user
		if (user != null && user.getAadharNo() != null && user.getPassword() != null) {
			User userFound = userRepository.findOne(user.getAadharNo());
			if (userFound != null) {
				if (userFound.getPassword().equals(user.getPassword())) {
					System.out.println(userFound.toString());
					// TODO add check on password
					model.addAttribute("authenticated", true);
					pageName = "home";
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
	public String register(Model model, @ModelAttribute User user) {

		List<String> errorMsg = new ArrayList<String>();
		String pageName = "register";

		// find the user
		if (user != null && user.getAadharNo() != null) {
			userRepository.save(user);
			model.addAttribute("authenticated", true);

			System.out.println("User authenticated");
			pageName = "home";

		} else {

			errorMsg.add("Please enter all madnditory fields");
		}

		model.addAttribute("errors", errorMsg);

		return pageName;

	}

	@RequestMapping(value = "/register")
	public String register(Model model) {

		return "register";

	}

}

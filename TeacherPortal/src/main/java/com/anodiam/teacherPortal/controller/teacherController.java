package com.anodiam.teacherPortal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.anodiam.teacherPortal.models.auth.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Controller
public class teacherController {
	
	@RequestMapping("/")
    public String index() {
        return "redirect:/login";
    }

	@RequestMapping("/login")
    public String login() {
        return "login";
    }
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
    public String myHome(
    		HttpServletRequest request,
			@ModelAttribute("username") String username,
			@ModelAttribute("password") String password,
			Model model
			) throws JSONException {
		try {
			System.out.println("1 SIGNUP");
			doLogin(username, password);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			System.out.println("2 SIGNUP");
			e.printStackTrace();
		}
		System.out.println("3 SIGNUP");
        return "myHome";
    }

	@RequestMapping("/signup")
    public String signup() {
        return "signup";
    }
	
	private void doLogin(String username, String password) throws JSONException{
		try {
			System.out.println("1 DOLOGIN");
			String logInUrl = "http://localhost:8090/api/auth/signin";
	        ObjectMapper objectMapper = new ObjectMapper();
	
	        System.out.println("2 DOLOGIN");
			RestTemplate restTemplate = new RestTemplate();
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        JSONObject logInObject = new JSONObject();
	        logInObject.put("username", username);
	        logInObject.put("password", password);
	
	        System.out.println("3 DOLOGIN");
			
	        HttpEntity<String> request =
	                new HttpEntity<String>(logInObject.toString(), headers);
	        
	        System.out.println("4 DOLOGIN");
			
	        ResponseEntity<User> user = 
	                restTemplate.postForEntity(logInUrl, request, User.class);
	        
	        System.out.println("5 DOLOGIN");
	        if(user!=null && user.getBody()!=null) {
	        	System.out.println(user.getBody().getAccessToken());
	        }
	        else {
	        	System.out.println("NULL user");	
			}
			
		}
		catch (Exception e) {
			System.out.println("Inside Catch of DOLOGIN method:" + e.getMessage());
			// TODO: handle exception
		}
    }

}

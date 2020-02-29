package ca.mcgill.ecse321.petshelter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.petshelter.dto.PersonDto;
import ca.mcgill.ecse321.petshelter.model.Person;
import ca.mcgill.ecse321.petshelter.service.PetShelterService;

@CrossOrigin(origins = "*")
@RestController
public class PetShelterController {

	@Autowired
	private PetShelterService service;
	
	//login person
	
	@PostMapping(value = {"/login/{tutorEmail}", "/login/{tutorEmail}/"})
	public void tutorLogin(@PathVariable("tutorEmail") String tutorEmail,
			@RequestParam String password) {
		service.loginAsTutor(tutorEmail, password);
	}
	
	@PutMapping(value = {"/logout", "/logout/"})
	public void logout() {
		service.logout();
	}
	
	@GetMapping(value = {"/user", "/user/"})
	public TutorDto getLoggedTutor() {
		return converToDto((Tutor)service.getLoggedInUser());
	}
	
	/*
	@PostMapping(value = {"/persons/{username}/{password}", "/persons/{username}/{password}/"})
	public PersonDto loginPerson(@PathVariable("username") String username, @PathVariable("password") String password) throws
	IllegalArgumentException{
		Person person = service.createPerson(username, password);
		return convertToDto(person);
	}
	
	private PersonDto convertToDto(Person p) {
		if(p == null) {
			throw new IllegalArgumentException("There is no such Person!");
		}
		PersonDto personDto = new PersonDto(p.getUsername(), p.getPassword());
		return personDto;
		
	}*/
}

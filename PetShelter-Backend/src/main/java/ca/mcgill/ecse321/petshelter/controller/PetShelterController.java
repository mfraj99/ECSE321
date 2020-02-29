package ca.mcgill.ecse321.petshelter.controller;

import java.security.InvalidParameterException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.petshelter.dao.PersonRepository;
import ca.mcgill.ecse321.petshelter.dto.AppAdminDto;
import ca.mcgill.ecse321.petshelter.dto.PersonDto;
import ca.mcgill.ecse321.petshelter.model.AppAdmin;
import ca.mcgill.ecse321.petshelter.model.Person;
import ca.mcgill.ecse321.petshelter.service.PetShelterService;

@CrossOrigin(origins = "*")
@RestController
public class PetShelterController {

	@Autowired
	private PetShelterService service;
	
	
	//DTO conversion methods
	private PersonDto convertToDto(Person p) {
		if(p == null) {
			throw new IllegalArgumentException("There is no such Person!");
		}
		PersonDto personDto = new PersonDto(p.getUsername(), p.getPassword());
		return personDto;
		
	}
	
//	private AppAdminDto convertToDto(AppAdmin a) {
//		if(a == null) {
//			throw new IllegalArgumentException("There is no such AppAdmin!");
//		}
//		Person appAdminDto = new appAdminDto(a.getUsername(), a.getPassword());
//		return appAdminDto;
//		
//	}
	
	//LOGIN AND LOGOUT
	
	//appUser login
	@PostMapping(value = {"/loginuser/{personUsername}", "/loginuser/{personUsername}/"})
	public void appUserLogin(@PathVariable("personUsername") String personUsername,
			@RequestParam String password) {
		service.loginAsAppUser(personUsername, password);
	}
	
	//appAdmin login
	@PostMapping(value = {"/loginadmin/{personUsername}", "/loginadmin/{personUsername}/"})
	public void appAdminLogin(@PathVariable("personUsername") String personUsername,
			@RequestParam String password) {
		service.loginAsAppAdmin(personUsername, password);
	}
	
	//logout
	@PutMapping(value = {"/logout", "/logout/"})
	public void logout() {
		service.logout();
	}
	
	@GetMapping(value = {"/user", "/user/"})
	public PersonDto getLoggedUser() {
		return convertToDto((Person)service.getLoggedInUser());
	}
	
	//APPADMIN
	@PostMapping(value = {"/adminregister/{adminUsername}", "/adminregister/{adminUsername}/"})
	public PersonDto registerAppAdmin(@PathVariable("adminUsername") String adminUsername,
			@RequestParam String password){

		AppAdmin appAdmin = service.createAppAdmin(adminUsername, password);

		return convertToDto(appAdmin);
	}
	
	
	
}

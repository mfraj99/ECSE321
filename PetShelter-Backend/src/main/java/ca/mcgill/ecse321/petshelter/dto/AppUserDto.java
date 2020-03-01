package ca.mcgill.ecse321.petshelter.dto;

import ca.mcgill.ecse321.petshelter.model.PersonRole;

public class AppUserDto {
	
	private PersonRole appUserRole;
	private String username;
	private String password;
	
	
	public AppUserDto(String username, String password, PersonRole appUserRole) {
		this.username = username;
		this.password = password;
		this.appUserRole = appUserRole;
	}
	
	public AppUserDto() {
		
	}
	
	
	public AppUserDto(PersonRole appUserRole) {
		this.appUserRole = appUserRole;
	}
	
	
	public PersonRole getAppUserRole() {
		return appUserRole;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
}

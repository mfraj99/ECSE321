package ca.mcgill.ecse321.petshelter.dto;

import java.util.Collections;
import java.util.Set;

import ca.mcgill.ecse321.petshelter.model.AdoptRequest;
import ca.mcgill.ecse321.petshelter.model.Donation;
import ca.mcgill.ecse321.petshelter.model.PetPost;
import ca.mcgill.ecse321.petshelter.model.Question;
import ca.mcgill.ecse321.petshelter.model.UserProfile;

public class PersonDto {
	private String username;
	private String password;
	private UserProfile creates;
	private Set<AdoptRequestDto> fillsIn;
	private Set<PetPostDto> owns;
	private Set<DonationDto> donation;
	private Set<QuestionDto> question;
	
	public PersonDto() {
		
	}
	
	public PersonDto(String username, String password) {
		this(username, password, null,Collections.EMPTY_SET, Collections.EMPTY_SET, Collections.EMPTY_SET, Collections.EMPTY_SET);
	}
	
	public PersonDto(String username, String password, UserProfile creates) {
		this(username, password, creates, Collections.EMPTY_SET, Collections.EMPTY_SET, Collections.EMPTY_SET, Collections.EMPTY_SET);
	}
	
	public PersonDto(String username, String password, UserProfile creates, Set<AdoptRequestDto> fillsIn, Set<PetPostDto> owns, Set<DonationDto> donation, Set<QuestionDto> question) {
		this.username = username;
		this.password = password;
		this.creates = creates;
		this.fillsIn = fillsIn;
		this.owns = owns;
		this.donation = donation;
		this.question = question;
	}
	
	public String getUsername() {
		return this.username;
	}

	public String getPassword() {
		return this.password;
	}

	public UserProfile getCreates() {
		return this.creates;
	}

	public Set<AdoptRequestDto> getFillsIn() {
		return this.fillsIn;
	}

	public Set<PetPostDto> getOwns() {
		return this.owns;
	}

	public Set<DonationDto> getDonation() {
		return this.donation;
	}

	public Set<QuestionDto> getQuestion() {
		return this.question;
	}

}

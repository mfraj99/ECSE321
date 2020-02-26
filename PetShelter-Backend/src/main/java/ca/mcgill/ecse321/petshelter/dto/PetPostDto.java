package ca.mcgill.ecse321.petshelter.dto;

import java.util.Collections;
import java.util.Set;

public class PetPostDto {
	private boolean availability;
	private PersonDto ownedBy;
	private Set<AdoptRequestDto> hasRequest;
	private String name;
	private String typeOfPet;
	private String description;
	private QuestionDto relatesTo;
	private Integer petPostId;
	
	public PetPostDto() {
		
	}
	
	// Pet Post with no description
	public PetPostDto(boolean availability, PersonDto ownedBy, Set<AdoptRequestDto> hasRequest, 
			String name, String typeOfPet, QuestionDto relatesTo, Integer petPostId) {
		this(availability, ownedBy, hasRequest, name, typeOfPet, "No description", relatedTo, petPostId);
	}
	
	public PetPostDto(boolean availability, PersonDto ownedBy, Set<AdoptRequestDto> hasRequest, String name, String typeOfPet, 
			String description, QuestionDto relatesTo, Integer petPostId) {
		this.availability = availability;
		this.ownedBy = ownedBy;
		this.hasRequest = hasRequest;
		this.name = name;
		this.typeOfPet = typeOfPet;
		this.description = description;
		this.relatesTo = relatesTo;
		this.petPostId = petPostId;
	}

	public boolean isAvailability() {
		return this.availability;
	}

	public PersonDto getOwnedBy() {
		return this.ownedBy;
	}
	
	public Set<AdoptRequestDto> getHasRequest() {
		return this.hasRequest;
	}

	public String getName() {
		return this.name;
	}

	public String getTypeOfPet() {
		return this.typeOfPet;
	}

	public String getDescription() {
		return this.description;
	}

	public QuestionDto getRelatesTo() {
		return this.relatesTo;
	}

	public Integer getPetPostId() {
		return this.petPostId;
	}

}

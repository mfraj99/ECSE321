package ca.mcgill.ecse321.petshelter.dto;

public class UserProfileDto {
	private String address;
	private Integer userProfileId;
	private PersonDto person;
	private Boolean hasExperienceWithPets;
	private Integer numberOfPetsCurrentlyOwned;
	private String typeOfLivingAccomodation;
	
	public UserProfileDto() {
		
	}
	
	public UserProfileDto(String address, Integer userProfileId, PersonDto person,
			Boolean hasExperienceWithPets, Integer numberOfPetsCurrentlyOwned, String typeOfLivingAccomodation) {
		this.address = address;
		this.userProfileId = userProfileId;
		this.person = person;
		this.hasExperienceWithPets = hasExperienceWithPets;
		this.numberOfPetsCurrentlyOwned = numberOfPetsCurrentlyOwned;
		this.typeOfLivingAccomodation = typeOfLivingAccomodation;
	}

	public Integer getUserProfileId() {
		return this.userProfileId;
	}

	public String getAddress() {
		return this.address;
	}

	public PersonDto getPerson() {
		return this.person;
	}

	public Boolean getHasExperienceWithPets() {
		return this.hasExperienceWithPets;
	}

	public Integer getNumberOfPetsCurrentlyOwned() {
		return this.numberOfPetsCurrentlyOwned;
	}

	public String getTypeOfLivingAccomodation() {
		return this.typeOfLivingAccomodation;
	}

}

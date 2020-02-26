package ca.mcgill.ecse321.petshelter.dto;

import java.util.Set;

import ca.mcgill.ecse321.petshelter.model.Person;

public class DonationDto {
	private double amount;
	private String comment;
	private boolean setNameAnonymous;
	private Set<PersonDto> person;
	private Integer donationId;
	
	public DonationDto() {
		
	}
	
	public DonationDto(double amount, String comment, boolean setNameAnonymous, Set<PersonDto> person, Integer donationId) {
		this.amount = amount;
		this.comment = comment;
		this.setNameAnonymous = setNameAnonymous;
		this.person = person;
		this.donationId = donationId;
	}
	
	public double getAmount() {
		return amount;
	}
	
	public String getComment() {
		return comment;
	}
	
	public boolean getSetNameAnonymous() {
		return setNameAnonymous;
	}
	
	public Set<PersonDto> getPerson() {
		return person;
	}
	
	public Integer getDonationId() {
		return donationId;
	}
}

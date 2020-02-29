package ca.mcgill.ecse321.petshelter.dto;

import java.util.Set;

public class DonationDto {
	private double amount;
	private String comment;
	private boolean setNameAnonymous;
	private Set<PersonDto> person;
	private Integer donationId;
	
	public DonationDto() {
		
	}
	
	// constructor with no anonymous option
	public DonationDto(double amount, String comment, Set<PersonDto> person, Integer donationId) {
		this(amount, comment, false, person, donationId);
	}
	
	// constructor with no comments
	public DonationDto(double amount, boolean setNameAnonymous, Set<PersonDto> person, Integer donationId) {
		this(amount, "No comment", setNameAnonymous, person, donationId);
	}
	
	// constructor with no anonymous and no comments
	public DonationDto(double amount, Set<PersonDto> person, Integer donationId) {
		this(amount, "No comment", false, person, donationId);
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

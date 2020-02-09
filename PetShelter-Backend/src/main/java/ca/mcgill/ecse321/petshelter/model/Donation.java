package ca.mcgill.ecse321.petshelter.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import java.util.Set;
import javax.persistence.ManyToMany;
import javax.persistence.Id;

@Entity
public class Donation {
	private double amount;

	public void setAmount(double value) {
		this.amount = value;
	}

	public double getAmount() {
		return this.amount;
	}

	private String comment;

	public void setComment(String value) {
		this.comment = value;
	}

	public String getComment() {
		return this.comment;
	}

	private boolean setNameAnonymous;

	public void setSetNameAnonymous(boolean value) {
		this.setNameAnonymous = value;
	}

	public boolean isSetNameAnonymous() {
		return this.setNameAnonymous;
	}

	private Set<Person> person;

	@ManyToMany(mappedBy = "donation")
	public Set<Person> getPerson() {
		return this.person;
	}

	public void setPerson(Set<Person> persons) {
		this.person = persons;
	}

	private Integer donationId;

	public void setDonationId(Integer value) {
		this.donationId = value;
	}

	@Id
	@GeneratedValue()
	public Integer getDonationId() {
		return this.donationId;
	}
}

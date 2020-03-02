package ca.mcgill.ecse321.petshelter.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.Set;
import javax.persistence.OneToMany;
import javax.persistence.ManyToMany;

@Entity
public class Person {
	private String username;

	public void setUsername(String value) {
		this.username = value;
	}

	@Id
	public String getUsername() {
		return this.username;
	}

	private String password;

	public void setPassword(String value) {
		this.password = value;
	}

	public String getPassword() {
		return this.password;
	}

	private UserProfile creates;

	@OneToOne
	public UserProfile getCreates() {
		return this.creates;
	}

	public void setCreates(UserProfile creates) {
		this.creates = creates;
	}

	private Set<AdoptRequest> fillsIn;

	@OneToMany(mappedBy = "requestedBy")
	public Set<AdoptRequest> getFillsIn() {
		return this.fillsIn;
	}

	public void setFillsIn(Set<AdoptRequest> fillsIns) {
		this.fillsIn = fillsIns;
	}

	private Set<PetPost> owns;

	@OneToMany(mappedBy = "ownedBy", cascade = { CascadeType.ALL })
	public Set<PetPost> getOwns() {
		return this.owns;
	}

	public void setOwns(Set<PetPost> ownss) {
		this.owns = ownss;
	}

	private Set<Donation> donation;

	@ManyToMany
	public Set<Donation> getDonation() {
		return this.donation;
	}

	public void setDonation(Set<Donation> donations) {
		this.donation = donations;
	}

	private Set<Question> question;

	@ManyToMany
	public Set<Question> getQuestion() {
		return this.question;
	}

	public void setQuestion(Set<Question> questions) {
		this.question = questions;
	}

}

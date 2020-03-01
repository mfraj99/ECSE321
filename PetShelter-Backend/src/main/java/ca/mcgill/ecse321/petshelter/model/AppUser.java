package ca.mcgill.ecse321.petshelter.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
public class AppUser extends Person {

	@Enumerated(EnumType.ORDINAL)
	private PersonRole appUserRole;

	public void setAppUserRole(PersonRole value) {
		this.appUserRole = value;
	}

	public PersonRole getAppUserRole() {
		return this.appUserRole;
	}
}

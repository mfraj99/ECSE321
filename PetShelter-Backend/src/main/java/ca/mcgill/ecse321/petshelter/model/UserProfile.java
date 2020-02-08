package ca.mcgill.ecse321.petshelter.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class UserProfile{
   private String address;
   private Integer userProfileId;
   
   public void setUserProfileId(Integer value) {
	   this.userProfileId = value;
   }
   
   @Id
   @GeneratedValue()
   public Integer getUserProfileId() {
	   return this.userProfileId;
   }

public void setAddress(String value) {
    this.address = value;
}
public String getAddress() {
    return this.address;
}
private Person person;

@OneToOne(mappedBy="creates" , optional=false)
public Person getPerson() {
   return this.person;
}

public void setPerson(Person person) {
   this.person = person;
}

private Boolean hasExperienceWithPets;

public void setHasExperienceWithPets(Boolean value) {
    this.hasExperienceWithPets = value;
}
public Boolean getHasExperienceWithPets() {
    return this.hasExperienceWithPets;
}
private Integer numberOfPetsCurrentlyOwned;

public void setNumberOfPetsCurrentlyOwned(Integer value) {
    this.numberOfPetsCurrentlyOwned = value;
}
public Integer getNumberOfPetsCurrentlyOwned() {
    return this.numberOfPetsCurrentlyOwned;
}
private String typeOfLivingAccomodation;

public void setTypeOfLivingAccomodation(String value) {
    this.typeOfLivingAccomodation = value;
}
public String getTypeOfLivingAccomodation() {
    return this.typeOfLivingAccomodation;
}
}

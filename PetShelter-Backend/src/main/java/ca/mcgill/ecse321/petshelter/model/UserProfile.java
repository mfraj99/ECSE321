package ca.mcgill.ecse321.petshelter.model;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class UserProfile{
   private String address;

public void setAddress(String value) {
    this.address = value;
}
public String getAddress() {
    return this.address;
}
private User user;

@OneToOne(mappedBy="creates" , optional=false)
public User getUser() {
   return this.user;
}

public void setUser(User user) {
   this.user = user;
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

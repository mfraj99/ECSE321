package ca.mcgill.ecse321.petshelter.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Set;
import javax.persistence.OneToMany;

@Entity
public class PetPost{
   private boolean availability;

public void setAvailability(boolean value) {
    this.availability = value;
}
public boolean isAvailability() {
    return this.availability;
}
private User ownedBy;

@ManyToOne(optional=false)
public User getOwnedBy() {
   return this.ownedBy;
}

public void setOwnedBy(User ownedBy) {
   this.ownedBy = ownedBy;
}

private Set<AdoptRequest> hasRequest;

@OneToMany(mappedBy="requesting" )
public Set<AdoptRequest> getHasRequest() {
   return this.hasRequest;
}

public void setHasRequest(Set<AdoptRequest> hasRequests) {
   this.hasRequest = hasRequests;
}

private String name;

public void setName(String value) {
    this.name = value;
}
public String getName() {
    return this.name;
}
private String typeOfPet;

public void setTypeOfPet(String value) {
    this.typeOfPet = value;
}
public String getTypeOfPet() {
    return this.typeOfPet;
}
private String description;

public void setDescription(String value) {
    this.description = value;
}
public String getDescription() {
    return this.description;
}
   private Question relatesTo;
   
   @ManyToOne
   public Question getRelatesTo() {
      return this.relatesTo;
   }
   
   public void setRelatesTo(Question relatesTo) {
      this.relatesTo = relatesTo;
   }
   
   }
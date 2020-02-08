package ca.mcgill.ecse321.petshelter.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.ManyToOne;
import java.util.Set;
import javax.persistence.OneToMany;
import javax.persistence.Id;

@Entity
public class PetPost{
   private boolean availability;

public void setAvailability(boolean value) {
    this.availability = value;
}
public boolean isAvailability() {
    return this.availability;
}
private Person ownedBy;

@ManyToOne(optional=false)
public Person getOwnedBy() {
   return this.ownedBy;
}

public void setOwnedBy(Person ownedBy) {
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

private Integer petPostId;

public void setPetPostId(Integer value) {
    this.petPostId = value;
}
@Id
@GeneratedValue()
public Integer getPetPostId() {
    return this.petPostId;
}
}

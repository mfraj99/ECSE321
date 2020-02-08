package ca.mcgill.ecse321.petshelter.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.ManyToOne;
import javax.persistence.Id;

@Entity
public class AdoptRequest{
   private Status status;

public void setStatus(Status value) {
    this.status = value;
}
public Status getStatus() {
    return this.status;
}
private Person requestedBy;

@ManyToOne(optional=false)
public Person getRequestedBy() {
   return this.requestedBy;
}

public void setRequestedBy(Person requestedBy) {
   this.requestedBy = requestedBy;
}

private PetPost requesting;

@ManyToOne(optional=false)
public PetPost getRequesting() {
   return this.requesting;
}

public void setRequesting(PetPost requesting) {
   this.requesting = requesting;
}

private Integer adoptRequestId;

public void setAdoptRequestId(Integer value) {
    this.adoptRequestId = value;
}
@Id
@GeneratedValue()
public Integer getAdoptRequestId() {
    return this.adoptRequestId;
}
}

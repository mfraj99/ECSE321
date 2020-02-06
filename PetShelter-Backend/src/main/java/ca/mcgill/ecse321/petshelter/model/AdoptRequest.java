package ca.mcgill.ecse321.petshelter.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class AdoptRequest{
   private Status status;

public void setStatus(Status value) {
    this.status = value;
}
public Status getStatus() {
    return this.status;
}
   private User requestedBy;
   
   @ManyToOne(optional=false)
   public User getRequestedBy() {
      return this.requestedBy;
   }
   
   public void setRequestedBy(User requestedBy) {
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
   
   }

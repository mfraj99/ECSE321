package ca.mcgill.ecse321.petshelter.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Donation{
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
   private AppUser donatedBy;
   
   @ManyToOne(optional=false)
   public AppUser getDonatedBy() {
      return this.donatedBy;
   }
   
   public void setDonatedBy(AppUser donatedBy) {
      this.donatedBy = donatedBy;
   }
   
   private AppAdmin receivedBy;
   
   @ManyToOne(optional=false)
   public AppAdmin getReceivedBy() {
      return this.receivedBy;
   }
   
   public void setReceivedBy(AppAdmin receivedBy) {
      this.receivedBy = receivedBy;
   }
   
   }

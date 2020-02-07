package ca.mcgill.ecse321.petshelter.model;

import java.util.Set;
import java.util.HashSet;

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
   
   /**
    * <pre>
    *           0..*     1..2
    * Donation ------------------------- Person
    *           donation        &gt;       person
    * </pre>
    */
   private Set<Person> person;
   
   public Set<Person> getPerson() {
      if (this.person == null) {
         this.person = new HashSet<Person>();
      }
      return this.person;
   }
   
   }

package ca.mcgill.ecse321.petshelter.model;

import java.util.Set;
import java.util.HashSet;

public class Person {
   private String username;
   
   public void setUsername(String value) {
      this.username = value;
   }
   
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
   
   /**
    * <pre>
    *           1..1     0..1
    * Person ------------------------- UserProfile
    *           person        &lt;       creates
    * </pre>
    */
   private UserProfile creates;
   
   public void setCreates(UserProfile value) {
      this.creates = value;
   }
   
   public UserProfile getCreates() {
      return this.creates;
   }
   
   /**
    * <pre>
    *           1..1     0..*
    * Person ------------------------- AdoptRequest
    *           requestedBy        &gt;       fillsIn
    * </pre>
    */
   private Set<AdoptRequest> fillsIn;
   
   public Set<AdoptRequest> getFillsIn() {
      if (this.fillsIn == null) {
         this.fillsIn = new HashSet<AdoptRequest>();
      }
      return this.fillsIn;
   }
   
   /**
    * <pre>
    *           1..1     0..*
    * Person ------------------------- PetPost
    *           ownedBy        &gt;       owns
    * </pre>
    */
   private Set<PetPost> owns;
   
   public Set<PetPost> getOwns() {
      if (this.owns == null) {
         this.owns = new HashSet<PetPost>();
      }
      return this.owns;
   }
   
   /**
    * <pre>
    *           1..2     0..*
    * Person ------------------------- Donation
    *           person        &lt;       donation
    * </pre>
    */
   private Set<Donation> donation;
   
   public Set<Donation> getDonation() {
      if (this.donation == null) {
         this.donation = new HashSet<Donation>();
      }
      return this.donation;
   }
   
   /**
    * <pre>
    *           1..*     0..*
    * Person ------------------------- Question
    *           person        &lt;       question
    * </pre>
    */
   private Set<Question> question;
   
   public Set<Question> getQuestion() {
      if (this.question == null) {
         this.question = new HashSet<Question>();
      }
      return this.question;
   }
   
   }

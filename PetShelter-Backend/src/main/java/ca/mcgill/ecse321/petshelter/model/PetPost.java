package ca.mcgill.ecse321.petshelter.model;

import java.util.Set;
import java.util.HashSet;

public class PetPost {
   private boolean availability;
   
   public void setAvailability(boolean value) {
      this.availability = value;
   }
   
   public boolean isAvailability() {
      return this.availability;
   }
   
   /**
    * <pre>
    *           0..*     1..1
    * PetPost ------------------------- Person
    *           owns        &lt;       ownedBy
    * </pre>
    */
   private Person ownedBy;
   
   public void setOwnedBy(Person value) {
      this.ownedBy = value;
   }
   
   public Person getOwnedBy() {
      return this.ownedBy;
   }
   
   /**
    * <pre>
    *           1..1     0..*
    * PetPost ------------------------- AdoptRequest
    *           requesting        &lt;       hasRequest
    * </pre>
    */
   private Set<AdoptRequest> hasRequest;
   
   public Set<AdoptRequest> getHasRequest() {
      if (this.hasRequest == null) {
         this.hasRequest = new HashSet<AdoptRequest>();
      }
      return this.hasRequest;
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
   
   /**
    * <pre>
    *           0..*     0..1
    * PetPost ------------------------- Question
    *           isRelatedTo        &lt;       relatesTo
    * </pre>
    */
   private Question relatesTo;
   
   public void setRelatesTo(Question value) {
      this.relatesTo = value;
   }
   
   public Question getRelatesTo() {
      return this.relatesTo;
   }
   
   }

package ca.mcgill.ecse321.petshelter.model;


public class UserProfile {
   private String address;
   
   public void setAddress(String value) {
      this.address = value;
   }
   
   public String getAddress() {
      return this.address;
   }
   
   /**
    * <pre>
    *           0..1     1..1
    * UserProfile ------------------------- Person
    *           creates        &gt;       person
    * </pre>
    */
   private Person person;
   
   public void setPerson(Person value) {
      this.person = value;
   }
   
   public Person getPerson() {
      return this.person;
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

package ca.mcgill.ecse321.petshelter.model;


public class AppUser extends Person {
   private PersonRole appUserRole;
   
   public void setAppUserRole(PersonRole value) {
      this.appUserRole = value;
   }
   
   public PersonRole getAppUserRole() {
      return this.appUserRole;
   }
   
   }

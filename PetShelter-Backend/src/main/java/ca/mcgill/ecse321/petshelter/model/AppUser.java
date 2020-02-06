package ca.mcgill.ecse321.petshelter.model;

import javax.persistence.Entity;
import java.util.Set;
import javax.persistence.OneToMany;

@Entity
public class AppUser extends User{
   private PersonRole appUserRole;

public void setAppUserRole(PersonRole value) {
    this.appUserRole = value;
}
public PersonRole getAppUserRole() {
    return this.appUserRole;
}
   private Set<Donation> donates;
   
   @OneToMany(mappedBy="donatedBy" )
   public Set<Donation> getDonates() {
      return this.donates;
   }
   
   public void setDonates(Set<Donation> donatess) {
      this.donates = donatess;
   }
   
   private Set<Question> asksOrAnswers;
   
   @OneToMany(mappedBy="askedByOrAnsweredBy" )
   public Set<Question> getAsksOrAnswers() {
      return this.asksOrAnswers;
   }
   
   public void setAsksOrAnswers(Set<Question> asksOrAnswerss) {
      this.asksOrAnswers = asksOrAnswerss;
   }
   
   }

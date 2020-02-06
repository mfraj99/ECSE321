package ca.mcgill.ecse321.petshelter.model;

import javax.persistence.Entity;
import java.util.Set;
import javax.persistence.OneToMany;

@Entity
public class AppAdmin extends User{
   private Set<Donation> receives;
   
   @OneToMany(mappedBy="receivedBy" )
   public Set<Donation> getReceives() {
      return this.receives;
   }
   
   public void setReceives(Set<Donation> receivess) {
      this.receives = receivess;
   }
   
   private Set<Question> answers;
   
   @OneToMany(mappedBy="answeredBy" )
   public Set<Question> getAnswers() {
      return this.answers;
   }
   
   public void setAnswers(Set<Question> answerss) {
      this.answers = answerss;
   }
   
   }

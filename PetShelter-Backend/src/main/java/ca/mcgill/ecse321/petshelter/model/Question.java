package ca.mcgill.ecse321.petshelter.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Set;
import javax.persistence.OneToMany;

@Entity
public class Question{
   private String question;

public void setQuestion(String value) {
    this.question = value;
}
public String getQuestion() {
    return this.question;
}
private String answer;

public void setAnswer(String value) {
    this.answer = value;
}
public String getAnswer() {
    return this.answer;
}
   private AppUser askedByOrAnsweredBy;
   
   @ManyToOne(optional=false)
   public AppUser getAskedByOrAnsweredBy() {
      return this.askedByOrAnsweredBy;
   }
   
   public void setAskedByOrAnsweredBy(AppUser askedByOrAnsweredBy) {
      this.askedByOrAnsweredBy = askedByOrAnsweredBy;
   }
   
   private AppAdmin answeredBy;
   
   @ManyToOne(optional=false)
   public AppAdmin getAnsweredBy() {
      return this.answeredBy;
   }
   
   public void setAnsweredBy(AppAdmin answeredBy) {
      this.answeredBy = answeredBy;
   }
   
   private Set<PetPost> isRelatedTo;
   
   @OneToMany(mappedBy="relatesTo" )
   public Set<PetPost> getIsRelatedTo() {
      return this.isRelatedTo;
   }
   
   public void setIsRelatedTo(Set<PetPost> isRelatedTos) {
      this.isRelatedTo = isRelatedTos;
   }
   
   }

package ca.mcgill.ecse321.petshelter.model;

import java.util.Set;
import java.util.HashSet;

public class Question {
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
   
   /**
    * <pre>
    *           0..1     0..*
    * Question ------------------------- PetPost
    *           relatesTo        &gt;       isRelatedTo
    * </pre>
    */
   private Set<PetPost> isRelatedTo;
   
   public Set<PetPost> getIsRelatedTo() {
      if (this.isRelatedTo == null) {
         this.isRelatedTo = new HashSet<PetPost>();
      }
      return this.isRelatedTo;
   }
   
   /**
    * <pre>
    *           0..*     1..*
    * Question ------------------------- Person
    *           question        &gt;       person
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

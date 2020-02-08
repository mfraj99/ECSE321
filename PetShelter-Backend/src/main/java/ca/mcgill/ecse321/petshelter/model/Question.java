package ca.mcgill.ecse321.petshelter.model;

import javax.persistence.Entity;
import java.util.Set;
import javax.persistence.OneToMany;
import javax.persistence.ManyToMany;

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
private Set<PetPost> isRelatedTo;

@OneToMany(mappedBy="relatesTo" )
public Set<PetPost> getIsRelatedTo() {
   return this.isRelatedTo;
}

public void setIsRelatedTo(Set<PetPost> isRelatedTos) {
   this.isRelatedTo = isRelatedTos;
}

private Set<Person> person;

@ManyToMany(mappedBy="question" )
public Set<Person> getPerson() {
   return this.person;
}

public void setPerson(Set<Person> persons) {
   this.person = persons;
}

private Integer questionId;

public void setQuestionId(Integer value) {
    this.questionId = value;
}
public Integer getQuestionId() {
    return this.questionId;
}
}

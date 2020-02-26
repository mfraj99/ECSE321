package ca.mcgill.ecse321.petshelter.dto;

import java.util.Set;

public class QuestionDto {
	private String question;
	private String answer;
	private Set<PetPostDto> isRelatedTo;
	private Set<PersonDto> person;
	private Integer questionId;
	
	public QuestionDto() {
		
	}
	
	
	public QuestionDto(String question, String answer, Set<PetPostDto> isRelatedTo, Set<PersonDto> person, Integer questionId) {
		this.question = question;
		this.answer = answer;
		this.isRelatedTo = isRelatedTo;
		this.person = person;
		this.questionId = questionId;
	}

	public String getQuestion() {
		return this.question;
	}

	public String getAnswer() {
		return this.answer;
	}

	public Set<PetPostDto> getIsRelatedTo() {
		return this.isRelatedTo;
	}

	public Set<PersonDto> getPerson() {
		return this.person;
	}

	public Integer getQuestionId() {
		return this.questionId;
	}

}

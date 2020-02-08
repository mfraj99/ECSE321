package ca.mcgill.ecse321.petshelter.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.petshelter.model.*;

public interface QuestionRepository extends CrudRepository<Question, Integer>{

	Question findQuestionById(int questionId);
	
	List<Question> findSentQuestionsFromPerson(Person person);
	List<Question> findReceivedQuestionsForPerson(Person person);
	
	List<Question> findSentQuestionsFromPersonByPetPost(Person person, PetPost post);
	List<Question> findReceivedQuestionsForPersonByPetPost(Person person, PetPost post);
	
	List<Question> findQuestionsByPetPost(PetPost post);
}

package ca.mcgill.ecse321.petshelter.dao;

import java.util.List;

import ca.mcgill.ecse321.petshelter.model.PetPost;

import org.springframework.data.repository.CrudRepository;

public interface PetPostRepository extends CrudRepository<PetPost, Integer> {

	PetPost findByPetPostId(int petPostId);

	/*
	 * List<PetPost> findPetPostsByPerson(Person person);
	 */
	List<PetPost> findByAvailability(boolean availability);
	  
	List<PetPost> findByTypeOfPet(String typeOfPet);
	
}

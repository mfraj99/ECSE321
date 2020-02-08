package ca.mcgill.ecse321.petshelter.dao;

import java.util.List;

import ca.mcgill.ecse321.petshelter.model.*;

import org.springframework.data.repository.CrudRepository;

public interface PetPostRepository extends CrudRepository<PetPost, Integer>{
	
	PetPost findByPetPostId(int petPostId);
	
	/*
	List<PetPost> findPetPostsByPerson(Person person);
	
	List<PetPost> findPetPostsByAvailability(boolean availability);
	
	List<PetPost> findPetPostsByTypeOfPet(String typeOfPet);
	*/
}

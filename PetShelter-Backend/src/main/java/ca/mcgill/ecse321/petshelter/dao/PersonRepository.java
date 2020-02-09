package ca.mcgill.ecse321.petshelter.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.petshelter.model.Person;

public interface PersonRepository extends CrudRepository<Person, Integer> {

	Person findByUsername(String username);
	// Person findByPetPost(PetPost petPost);

}

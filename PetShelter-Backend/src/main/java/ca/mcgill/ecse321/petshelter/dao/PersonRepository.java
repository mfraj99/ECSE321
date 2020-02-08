package ca.mcgill.ecse321.petshelter.dao;


import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.petshelter.model.*;


public interface PersonRepository extends CrudRepository<Person, String>{

  Person findPersonByUsername(String username);
  Person findPersonByPetPost(PetPost post);

}

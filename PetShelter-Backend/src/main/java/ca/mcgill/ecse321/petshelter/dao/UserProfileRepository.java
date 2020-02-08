package ca.mcgill.ecse321.petshelter.dao;
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.petshelter.model.*;


public interface UserProfileRepository extends CrudRepository<UserProfile, String>{
  UserProfile findUserProfileByUsername(String username);
  UserProfile findUserProfileByPerson(Person person);
}

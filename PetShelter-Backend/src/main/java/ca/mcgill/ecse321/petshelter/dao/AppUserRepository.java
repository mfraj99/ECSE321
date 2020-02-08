package ca.mcgill.ecse321.petshelter.dao;
import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.petshelter.model.*;


public interface AppUserRepository extends CrudRepository<AppUser, String>{

  AppUser findAppUserByUsername(String username);
  
}

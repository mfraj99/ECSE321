package ca.mcgill.ecse321.petshelter.dao;
import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.petshelter.model.AppUser;


public interface AppUserRepository extends CrudRepository<AppUser, Integer>{

  AppUser findByUsername(String username);
  
}

package ca.mcgill.ecse321.petshelter.dao;
import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.petshelter.model.*;


public interface AppAdminRepository extends CrudRepository<AppAdmin, String>{

  AppAdmin findByUsername(String username);
}

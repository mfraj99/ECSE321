package ca.mcgill.ecse321.petshelter.dao;
import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.petshelter.model.AppAdmin;


public interface AppAdminRepository extends CrudRepository<AppAdmin, Integer>{

  AppAdmin findByUsername(String username);
}

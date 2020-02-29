package ca.mcgill.ecse321.petshelter.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.petshelter.model.UserProfile;

public interface UserProfileRepository extends CrudRepository<UserProfile, Integer> {

	UserProfile findByUserProfileId(int id);
}

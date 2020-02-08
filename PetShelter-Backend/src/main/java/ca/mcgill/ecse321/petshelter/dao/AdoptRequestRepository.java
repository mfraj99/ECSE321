package ca.mcgill.ecse321.petshelter.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.petshelter.model.*;


public interface AdoptRequestRepository extends CrudRepository<AdoptRequest, Integer>{
	
	AdoptRequest findAdoptRequestById(int adoptRequestId);
	
	List<AdoptRequest> findSentAdoptRequestsFromPerson(Person person);
	List<AdoptRequest> findReceivedAdoptRequestsForPerson(Person person);
	
	List<AdoptRequest> findAdoptRequestsByPetPost(PetPost post);
}

package ca.mcgill.ecse321.petshelter.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.petshelter.model.AdoptRequest;


public interface AdoptRequestRepository extends CrudRepository<AdoptRequest, Integer>{
	
	AdoptRequest findByAdoptRequestId(int adoptRequestId);
	
	//List<AdoptRequest> findSentAdoptRequestsFromPerson(Person person);
	//List<AdoptRequest> findReceivedAdoptRequestsForPerson(Person person);
	
	// List<AdoptRequest> findAdoptRequestsByPetPost(PetPost petPost);
}

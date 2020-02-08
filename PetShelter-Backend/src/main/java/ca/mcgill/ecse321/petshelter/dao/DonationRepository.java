package ca.mcgill.ecse321.petshelter.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.petshelter.model.*;

public interface DonationRepository extends CrudRepository<Donation, Integer> {
	
	Donation findDonationById(int donationId);
	List<Donation> findDonationsFromPerson(Person person);
	List<Donation> findDonationsToPerson(Person person);

}

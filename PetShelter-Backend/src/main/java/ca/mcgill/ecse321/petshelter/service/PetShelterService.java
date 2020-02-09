package ca.mcgill.ecse321.petshelter.service;
import java.util.*; 


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.petshelter.dao.*;

import ca.mcgill.ecse321.petshelter.model.*;


@Service
public class PetShelterService {

	
	
	private static final Status PENDING = ca.mcgill.ecse321.petshelter.model.Status.PENDING;
	
	
	@Autowired
	private AdoptRequestRepository adoptRequestRepository;
	@Autowired
	private AppAdminRepository appAdminRepository;
	@Autowired
	private AppUserRepository appUserRepository;
	@Autowired
	private DonationRepository donationRepository;
	@Autowired
	private PersonRepository personRepository;
	@Autowired
	private PetPostRepository petPostRepository;
	@Autowired
    private QuestionRepository questionRepository;
	@Autowired
	private UserProfileRepository userProfileRepository;
//	
//	@Transactional
//	public Person createPerson(String username, String password) {
//		
//		
//		Person person = new Person();
//		person.setUsername(username);
//		person.setPassword(password);
//		personRepository.save(person);
//		return person;
//	}
//	
	
	//ADOPTREQUEST TESTS
	@Transactional
	public AdoptRequest createAdoptRequest() {
		AdoptRequest adoptRequest = new AdoptRequest();
		adoptRequest.setStatus(PENDING);
		adoptRequestRepository.save(adoptRequest);
		return adoptRequest;
		
	}
	
	@Transactional
	public AdoptRequest getAdoptRequest(Integer id) {
		AdoptRequest adoptRequest = adoptRequestRepository.findByAdoptRequestId(id);
		return adoptRequest;
	}
	
	public List<AdoptRequest> getAllAdoptRequests() {
		return toList(adoptRequestRepository.findAll());
	}
	
	//APPADMIN TESTS
	@Transactional
	public AppAdmin createAppAdmin(String username, String password) {
		AppAdmin appAdmin = new AppAdmin();
		appAdmin.setUsername(username);
		appAdmin.setPassword(password);
		appAdminRepository.save(appAdmin);
		return appAdmin;
	}
	
	@Transactional
	public AppAdmin getAppAdmin(String username) {
		AppAdmin appAdmin = appAdminRepository.findByUsername(username);
		return appAdmin;
	}
	
	//APPUSER TESTS
//	@Transactional
//	public AppUser createAppUser(String username, String password) {
//		
//	}
	
	//DONATION TESTS
	@Transactional
	public Donation createDonation(double amount, String comment, boolean setNameAnonymous) {
		Donation donation = new Donation();
		donation.setAmount(amount);
		donation.setComment(comment);
		donation.setSetNameAnonymous(setNameAnonymous);
		donationRepository.save(donation);
		return donation;
	}
	
	@Transactional
	public Donation getDonation(Integer id) {
		Donation donation = donationRepository.findByDonationId(id);
		return donation;
	}
	
	@Transactional
	public List<Donation> getAllDonations() {
		return toList(donationRepository.findAll());
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}
	
}

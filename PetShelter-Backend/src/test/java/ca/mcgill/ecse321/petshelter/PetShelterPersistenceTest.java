package ca.mcgill.ecse321.petshelter;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import ca.mcgill.ecse321.petshelter.dao.*;
import ca.mcgill.ecse321.petshelter.model.*;
import ca.mcgill.ecse321.petshelter.service.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class PetShelterPersistenceTest {
	
	@Autowired
	private PetShelterService pss;

//	@Autowired
//	AdoptRequest adoptRequest;
//	@Autowired
//	AppAdmin appAdmin;
//	@Autowired
//	AppUser appUser;
//	@Autowired
//	Donation donation;
//	@Autowired
//	Person person;
//	@Autowired
//	PetPost petPost;
//	@Autowired
//	Question question;
//	@Autowired
//	UserProfile userProfile;
	
	
	
//	@Autowired
//	private AdoptRequestRepository adoptRequestRepository;
//	@Autowired
//	private AppAdminRepository appAdminRepository;
//	@Autowired
//	private AppUserRepository appUserRepository;
	@Autowired
	private DonationRepository donationRepository;
//	@Autowired
//	private PersonRepository personRepository;
//	@Autowired
//	private PetPostRepository petPostRepository;
//	@Autowired
//	private QuestionRepository questionRepository;
//	@Autowired
//	private UserProfileRepository userProfileRepository;
//	
	
	
	
	@AfterEach
	public void clearDatabase() {
		//clearing tables
		//adoptRequestRepository.deleteAll();
//		appAdminRepository.deleteAll();
//		appUserRepository.deleteAll();
		donationRepository.deleteAll();
//		personRepository.deleteAll();
//		petPostRepository.deleteAll();
//		questionRepository.deleteAll();
//		userProfileRepository.deleteAll();
	}
	
	//testing Person
//	@Test
//	public void testPersistAndLoadPerson() {
//		String username = "TestPerson";
//		String password = "123test";
//		// First example for object save/load
//		// First example for attribute save/load
//		person.setUsername(username);
//		person.setPassword(password);
//		personRepository.save(person);
//		try {
//			
//		}catch (IllegalArgumentException e) {
//			fail();
//		}
//		person = null;
//
//		person = personRepository.findByUsername(username);
//		assertNotNull(person);
//		assertEquals(username, person.getUsername());
//	}
	
	//testing Donation
	@Test
	public void testCreateDonation() {
		assertEquals(0, pss.getAllDonations().size());
		
		double amount = 100;
		String comment = "WOW";
		boolean setNameAnonymous = false;
		
		try {
			pss.createDonation(amount, comment, setNameAnonymous);
		} catch (IllegalArgumentException e) {
			fail();
		}
		
		List <Donation> allDonations = pss.getAllDonations();
		assertEquals(1, allDonations.size());
		assertEquals(amount, allDonations.get(0).getAmount());
		assertEquals(comment, allDonations.get(0).getComment());
		assertEquals(setNameAnonymous, allDonations.get(0).isSetNameAnonymous());
		
	}
	
	
	
	
	
	
	
	
	
	
	
}

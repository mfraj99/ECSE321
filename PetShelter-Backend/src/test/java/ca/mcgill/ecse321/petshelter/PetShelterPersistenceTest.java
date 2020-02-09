package ca.mcgill.ecse321.petshelter;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ca.mcgill.ecse321.petshelter.dao.*;
import ca.mcgill.ecse321.petshelter.model.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PetShelterPersistenceTest {

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
	
	
	
	@AfterEach
	public void clearDatabase() {
		//clearing tables
		adoptRequestRepository.deleteAll();
		appAdminRepository.deleteAll();
		appUserRepository.deleteAll();
		donationRepository.deleteAll();
		personRepository.deleteAll();
		petPostRepository.deleteAll();
		questionRepository.deleteAll();
		userProfileRepository.deleteAll();
	}
	
	//testing Person
	@Test
	public void testPersistAndLoadPerson() {
		String username = "TestPerson";
		String password = "123test";
		// First example for object save/load
		Person person = new Person();
		// First example for attribute save/load
		person.setUsername(username);
		person.setPassword(password);
		personRepository.save(person);

		person = null;

		person = personRepository.findByUsername(username);
		assertNotNull(person);
		assertEquals(username, person.getUsername());
	}
	
	
	
	
	
	
	
	
	
	
	
}

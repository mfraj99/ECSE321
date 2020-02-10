package ca.mcgill.ecse321.petshelter;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.Before;
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
		// clearing tables
		adoptRequestRepository.deleteAll();
		appAdminRepository.deleteAll();
		appUserRepository.deleteAll();
		donationRepository.deleteAll();
		personRepository.deleteAll();
		petPostRepository.deleteAll();
		questionRepository.deleteAll();
		userProfileRepository.deleteAll();
	}

	/*
	 * /////////////////////////////////////////////////////////////////////////////
	 * / TESTING ADOPTREQUEST
	 * /////////////////////////////////////////////////////////////////////////////
	 * /
	 */

	// create
	@Test
	public void testCreateAdoptRequest() {
		assertEquals(0, pss.getAllAdoptRequests().size());
		pss.createAdoptRequest();
		List<AdoptRequest> allAdoptRequests = pss.getAllAdoptRequests();
		assertEquals(1, allAdoptRequests.size());
	}

	/*
	 * /////////////////////////////////////////////////////////////////////////////
	 * / TESTING APPADMIN
	 * /////////////////////////////////////////////////////////////////////////////
	 * /
	 */

	// create
	@Test
	public void testCreateAppAdmin() {
		assertEquals(0, pss.getAllAppAdmins().size());
		String username = "JWS";
		String password = "3GPA";
		pss.createAppAdmin(username, password);
		List<AppAdmin> allAppAdmins = pss.getAllAppAdmins();
		assertEquals(1, allAppAdmins.size());
	}

	// more than one admin
	@Test
	public void testCreateAppAdminMoreThanOne() {
		String error = null;
		String username = "JWS";
		String password = "3GPA";
		pss.createAppAdmin(username, password);
		int size = pss.getAllAppAdmins().size();
		username = "Karl";
		password = "wololo";
		try {
			pss.createAppAdmin(username, password);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("Can only have one Admin!", error);
		assertEquals(size, pss.getAllAppAdmins().size());
	}

	/*
	 * /////////////////////////////////////////////////////////////////////////////
	 * / TESTING APPUSER
	 * /////////////////////////////////////////////////////////////////////////////
	 * /
	 */

	// create AppUser Adopter
	@Test
	public void testCreateAppUserAdopter() {
		assertEquals(0, pss.getAllAppUsers().size());
		String username = "JWS";
		String password = "3GPA";
		PersonRole personRole = ca.mcgill.ecse321.petshelter.model.PersonRole.ADOPTER;

		pss.createAppUser(username, password, personRole);
		List<AppUser> allAppUsers = pss.getAllAppUsers();
		assertEquals(1, allAppUsers.size());
	}

	// create AppUser Owner
	@Test
	public void testCreateAppUserOwner() {
		assertEquals(0, pss.getAllAppUsers().size());
		String username = "JWS";
		String password = "3GPA";
		PersonRole personRole = ca.mcgill.ecse321.petshelter.model.PersonRole.OWNER;

		pss.createAppUser(username, password, personRole);
		List<AppUser> allAppUsers = pss.getAllAppUsers();
		assertEquals(1, allAppUsers.size());
	}

	/*
	 * /////////////////////////////////////////////////////////////////////////////
	 * / TESTING DONATION
	 * /////////////////////////////////////////////////////////////////////////////
	 * /
	 */

	// create Donation
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

		List<Donation> allDonations = pss.getAllDonations();
		assertEquals(1, allDonations.size());
		assertEquals(amount, allDonations.get(0).getAmount());
		assertEquals(comment, allDonations.get(0).getComment());
		assertEquals(setNameAnonymous, allDonations.get(0).isSetNameAnonymous());

	}

	// incorrect donation value
	@Test
	public void testCreateDonationInvalidAmount() {
		assertEquals(0, pss.getAllDonations().size());

		double amount = -1;
		String comment = "WOW";
		boolean setNameAnonymous = false;
		String error = null;

		try {
			pss.createDonation(amount, comment, setNameAnonymous);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("Donation amount cannot be 0!", error);
		assertEquals(0, pss.getAllDonations().size());

	}

	/*
	 * /////////////////////////////////////////////////////////////////////////////
	 * / TESTING USER PROFILE
	 * /////////////////////////////////////////////////////////////////////////////
	 * /
	 */
	@Test
	public void createUserProfile() {
		assertEquals(0, pss.getAllUserProfiles().size());

		String address = "12345 Street Blvd.";
		boolean petExperience = false;
		int petsOwned = 0;
		String livingAccommodations = "I live in an apartment. It is very small.";

		try {
			pss.createUserProfile(address, petExperience, petsOwned, livingAccommodations);
		} catch (IllegalArgumentException e) {
			fail();
		}

		List<UserProfile> allUserProfiles = pss.getAllUserProfiles();
		assertEquals(1, allUserProfiles.size());
		assertEquals(address, allUserProfiles.get(0).getAddress());
		assertEquals(petExperience, allUserProfiles.get(0).getHasExperienceWithPets());
		assertEquals(petsOwned, allUserProfiles.get(0).getNumberOfPetsCurrentlyOwned());
		assertEquals(livingAccommodations, allUserProfiles.get(0).getTypeOfLivingAccomodation());
	}

	// invalid number of pets value
	@Test
	public void testCreateUserProfileInvalidPetAmount() {
		assertEquals(0, pss.getAllUserProfiles().size());

		String address = "12345 Street Blvd.";
		boolean petExperience = false;
		int petsOwned = -1;
		String livingAccommodations = "I live in an apartment. It is very small.";
		String error = null;

		try {
			pss.createUserProfile(address, petExperience, petsOwned, livingAccommodations);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("Number of pets currently owned cannot be less than 0!", error);
		assertEquals(0, pss.getAllDonations().size());

	}

	// having no experience with pets but with numberOfPets>0
	@Test
	public void testCreateUserProfileOwnsPetsNoExperience() {
		assertEquals(0, pss.getAllUserProfiles().size());

		String address = "12345 Street Blvd.";
		boolean petExperience = false;
		int petsOwned = 1;
		String livingAccommodations = "I live in an apartment. It is very small.";
		String error = null;

		try {
			pss.createUserProfile(address, petExperience, petsOwned, livingAccommodations);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("Pet experience invalid! Cannot have no experience but own pets!", error);
		assertEquals(0, pss.getAllDonations().size());

	}

	// having an empty address field
	@Test
	public void testCreateUserProfileNoAddress() {
		assertEquals(0, pss.getAllUserProfiles().size());

		String address = "";
		boolean petExperience = false;
		int petsOwned = 0;
		String livingAccommodations = "I live in an apartment. It is very small.";
		String error = null;

		try {
			pss.createUserProfile(address, petExperience, petsOwned, livingAccommodations);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("Address cannot be empty!", error);
		assertEquals(0, pss.getAllDonations().size());

	}

	// having an living accommodations field
	@Test
	public void testCreateUserProfileNoLivingAccomodations() {
		assertEquals(0, pss.getAllUserProfiles().size());

		String address = "12345 Street Blvd.";
		boolean petExperience = false;
		int petsOwned = 0;
		String livingAccommodations = "";
		String error = null;

		try {
			pss.createUserProfile(address, petExperience, petsOwned, livingAccommodations);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("Living Accommodations cannot be empty!", error);
		assertEquals(0, pss.getAllDonations().size());

	}

	/*
	 * /////////////////////////////////////////////////////////////////////////////
	 * / TESTING PERSON
	 * /////////////////////////////////////////////////////////////////////////////
	 * /
	 */

	// create
	@Test
	public void testCreatePerson() {
		assertEquals(0, pss.getAllPersons().size());

		String username = "JiaWei";
		String password = "HelloWorld";

		try {
			pss.createPerson(username, password);
		} catch (IllegalArgumentException e) {
			fail();
		}

		List<Person> allPersons = pss.getAllPersons();

		assertEquals(1, allPersons.size());
		assertEquals(username, allPersons.get(0).getUsername());
	}

	// null username and password
	@Test
	public void testCreatePersonNullUandP() {
		assertEquals(0, pss.getAllPersons().size());

		String username = null;
		String password = null;
		String error = null;

		try {
			pss.createPerson(username, password);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertEquals("Person name and password cannot be empty!", error);
		assertEquals(0, pss.getAllPersons().size());

	}

	// empty username and password
	@Test
	public void testCreatePersonEmptyUandP() {
		assertEquals(0, pss.getAllPersons().size());

		String username = "";
		String password = "";
		String error = null;
		try {
			pss.createPerson(username, password);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertEquals("Person name and password cannot be empty!", error);
		assertEquals(0, pss.getAllPersons().size());
	}

	// space in username and password
	@Test
	public void testCreatePersonSpaceUandP() {
		assertEquals(0, pss.getAllPersons().size());

		String username = " ";
		String password = " ";
		String error = null;

		try {
			pss.createPerson(username, password);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertEquals("Person name and password cannot be empty!", error);
		assertEquals(0, pss.getAllPersons().size());

	}

	// empty username
	@Test
	public void testCreatePersonEmptyU() {
		assertEquals(0, pss.getAllPersons().size());

		String username = "";
		String password = "12344";
		String error = null;

		try {
			pss.createPerson(username, password);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("Person name cannot be empty!", error);
		assertEquals(0, pss.getAllPersons().size());

	}

	// empty password
	@Test
	public void testCreatePersonEmptyP() {
		assertEquals(0, pss.getAllPersons().size());

		String username = "english";
		String password = "";
		String error = null;

		try {
			pss.createPerson(username, password);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("Person password cannot be empty!", error);
		assertEquals(0, pss.getAllPersons().size());
	}

	// space in name
	@Test
	public void testCreatePersonSpaceU() {
		assertEquals(0, pss.getAllPersons().size());

		String username = " ";
		String password = "12344";
		String error = null;

		try {
			pss.createPerson(username, password);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("Person name cannot be empty!", error);
		assertEquals(0, pss.getAllPersons().size());

	}

	// space in password
	@Test
	public void testCreatePersonSpaceP() {
		assertEquals(0, pss.getAllPersons().size());

		String username = "tony";
		String password = " ";
		String error = null;

		try {
			pss.createPerson(username, password);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("Person password cannot be empty!", error);
		assertEquals(0, pss.getAllPersons().size());
	}

	/*
	 * /////////////////////////////////////////////////////////////////////////////
	 * / TESTING QUESTION
	 * /////////////////////////////////////////////////////////////////////////////
	 * /
	 */

	// create Question
	@Test
	public void testCreateQuestion() {
		assertEquals(0, pss.getAllQuestions().size());

		String question = "How much food does the pet need?";

		try {
			pss.createQuestion(question);
		} catch (IllegalArgumentException e) {
			fail();
		}

		List<Question> allQuestions = pss.getAllQuestions();
		assertEquals(1, allQuestions.size());
		assertEquals(question, allQuestions.get(0).getQuestion());
	}

	// empty question
	@Test
	public void testCreateEmptyQuestion() {
		assertEquals(0, pss.getAllDonations().size());

		String question = "";
		String error = "";

		try {
			pss.createQuestion(question);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertEquals("Question cannot be empty!", error);
		assertEquals(0, pss.getAllQuestions().size());
	}

	/*
	 * /////////////////////////////////////////////////////////////////////////////
	 * / TESTING PETPOST
	 * /////////////////////////////////////////////////////////////////////////////
	 * /
	 */

	// create Pet Post
	@Test
	public void testCreatePetPost() {
		assertEquals(0, pss.getAllPetPosts().size());

		boolean avail = true;
		String name = "Mike";
		String typeOfPet = "Cat";
		String desc = "Small domestic cat for sale";

		try {
			pss.createPetPost(avail, name, typeOfPet, desc);
		} catch (IllegalArgumentException e) {
			fail();
		}

		List<PetPost> allPetPosts = pss.getAllPetPosts();
		assertEquals(1, allPetPosts.size());
		assertEquals(name, allPetPosts.get(0).getName());
		assertEquals(typeOfPet, allPetPosts.get(0).getTypeOfPet());
		assertEquals(desc, allPetPosts.get(0).getDescription());
	}

	// Pet post with empty name
	@Test
	public void testCreatePetPostWithEmptyName() {
		assertEquals(0, pss.getAllPetPosts().size());

		boolean avail = true;
		String name = "";
		String typeOfPet = "Cat";
		String desc = "Small domestic cat for sale";
		String error = "";

		try {
			pss.createPetPost(avail, name, typeOfPet, desc);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertEquals("Pet name cannot be empty!", error);
		assertEquals(0, pss.getAllPetPosts().size());
	}

	// Pet post with empty type of pet
	@Test
	public void testCreatePetPostWithEmptyTypeOfPet() {
		assertEquals(0, pss.getAllPetPosts().size());

		boolean avail = true;
		String name = "Mike";
		String typeOfPet = "";
		String desc = "Small domestic cat for sale";
		String error = "";

		try {
			pss.createPetPost(avail, name, typeOfPet, desc);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertEquals("Pet type cannot be empty!", error);
		assertEquals(0, pss.getAllPetPosts().size());
	}

	// Pet post with empty description
	@Test
	public void testCreatePetPostWithEmptyDescription() {
		assertEquals(0, pss.getAllPetPosts().size());

		boolean avail = true;
		String name = "Mike";
		String typeOfPet = "Cat";
		String desc = "";
		String error = "";

		try {
			pss.createPetPost(avail, name, typeOfPet, desc);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertEquals("Pet must have a description!", error);
		assertEquals(0, pss.getAllPetPosts().size());
	}

}

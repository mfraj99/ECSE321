
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
public class PetShelterCrudTests {

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
		pss.createPerson("person1", "password");
		pss.createPerson("person2", "password");
		pss.createPetPost(true, "Ebola", "dog", "cute doggo", pss.getPerson("person1"));
		int id = pss.getAllPetPosts().get(0).getPetPostId();
		
		pss.createAdoptRequest(pss.getPerson("person2"), pss.getPetPost(id));
		List<AdoptRequest> allAdoptRequests = pss.getAllAdoptRequests();
		assertEquals(1, allAdoptRequests.size());
	}
	
	@Test
	public void testCreateAdoptRequestNullOwner() {
		assertEquals(0,pss.getAllAdoptRequests().size());
		pss.createPerson("person1", "password");
		pss.createPetPost(true, "CoronaVirus", "dog", "cute doggo", pss.getPerson("person1"));
		String error = null;
		try {
			pss.createAdoptRequest(null, pss.getAllPetPosts().get(0));
		} catch (IllegalArgumentException e) {
			error= e.getMessage();
		}
		
		assertEquals("Adoptrequest must have an owner!", error);
		assertEquals(0,pss.getAllAdoptRequests().size());
		
		
	}
	
	@Test
	public void testCreateAdoptRequestNullPetPost() {
		assertEquals(0,pss.getAllAdoptRequests().size());
		pss.createPerson("person1", "password");
		String error = null;
		
		try {
			pss.createAdoptRequest(pss.getPerson("person1"), null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertEquals("Adoptrequest must be associated with a pet post!", error);
		assertEquals(0,pss.getAllAdoptRequests().size());
	}
	
	
	@Test
	public void testDeleteAdoptRequest() {
		//creating an adoptrequest to delete
		assertEquals(0, pss.getAllAdoptRequests().size());
		pss.createPerson("person1", "password");
		pss.createPerson("person2", "password");
		pss.createPetPost(true, "Ebola", "dog", "cute doggo", pss.getPerson("person1"));
		int id = pss.getAllPetPosts().get(0).getPetPostId();
		
		pss.createAdoptRequest(pss.getPerson("person2"), pss.getPetPost(id));
		List<AdoptRequest> allAdoptRequests = pss.getAllAdoptRequests();
		assertEquals(1, allAdoptRequests.size());
		
		//deleting adoptRequest
		pss.deleteAdoptRequest(pss.getAllAdoptRequests().get(0).getAdoptRequestId());
		assertEquals(0, pss.getAllAdoptRequests().size());
	}
	
	@Test
	public void testDeleteAdoptRequestNullId() {
		assertEquals(0, pss.getAllAdoptRequests().size());
		String error = null;
		Integer Id = null;
		try {
			pss.deleteAdoptRequest(Id);
		} catch(IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertEquals("AdoptRequestId invalid!", error);
		assertEquals(0, pss.getAllAdoptRequests().size());
		
	}
	
	@Test
	public void testDeleteAdoptRequestInvalidId() {
		assertEquals(0, pss.getAllAdoptRequests().size());
		String error = null;
		Integer Id = 0000;
		try {
			pss.deleteAdoptRequest(Id);
		} catch(IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertEquals("AdoptRequest must be valid!", error);
		assertEquals(0, pss.getAllAdoptRequests().size());
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
	
	@Test
	public void testDeleteAppAdmin() {
		//creating person
		assertEquals(0, pss.getAllAppAdmins().size());
		String username = "tony";
		String password = "passowrd";
		
		pss.createAppAdmin(username, password);
		assertEquals(1, pss.getAllAppAdmins().size());
		//deleting person
		
		pss.deleteAppAdmin(username);
		assertEquals(0, pss.getAllAppAdmins().size());
	}
	
	@Test
	public void testDeleteAppAdminNullName() {
		assertEquals(0, pss.getAllAppAdmins().size());
		String error= null;
		try {
			pss.deleteAppAdmin("");
		} catch(IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("Username invalid!", error);
		assertEquals(0, pss.getAllAppAdmins().size());
		
	}
	
	@Test
	public void testDeleteAppAdminInvalidName() {
		assertEquals(0, pss.getAllAppAdmins().size());
		String error = null;
		try {
			pss.deleteAppAdmin("username");
		} catch(IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("No AppAdmin found with username!", error);
		assertEquals(0, pss.getAllAppAdmins().size());
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

	@Test
	public void testDeleteAppUser() {
		//creating person
		assertEquals(0, pss.getAllAppUsers().size());
		String username = "tony";
		String password = "passowrd";
		
		pss.createAppUser(username, password, PersonRole.ADOPTER);
		assertEquals(1, pss.getAllAppUsers().size());
		//deleting person
		
		pss.deleteAppUser(username);
		assertEquals(0, pss.getAllAppUsers().size());
	}
	
	@Test
	public void testDeleteAppUserNullName() {
		assertEquals(0, pss.getAllAppUsers().size());
		String error= null;
		try {
			pss.deleteAppUser("");
		} catch(IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("Username invalid!", error);
		assertEquals(0, pss.getAllAppUsers().size());
		
	}
	
	@Test
	public void testDeleteAppUserInvalidName() {
		assertEquals(0, pss.getAllAppUsers().size());
		String error = null;
		try {
			pss.deleteAppUser("username");
		} catch(IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("No AppUser found with username!", error);
		assertEquals(0, pss.getAllAppAdmins().size());
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
	
	@Test
	public void testDeleteDonation() {
		assertEquals(0, pss.getAllDonations().size());

		double amount = 20;
		String comment = "WOW";
		boolean setNameAnonymous = false;
		
		pss.createDonation(amount, comment, setNameAnonymous);
		
		assertEquals(1, pss.getAllDonations().size());
		
		
		pss.deleteDonation(pss.getAllDonations().get(0).getDonationId());
		assertEquals(0, pss.getAllDonations().size());
	}
	
	@Test
	public void testDeleteDonationNullId() {
		assertEquals(0, pss.getAllDonations().size());
		String error = null;
		try {
			pss.deleteDonation(null);
		}catch(IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("Donation ID invalid!", error);
		assertEquals(0, pss.getAllDonations().size());
		
	}
	
	@Test
	public void testDeleteDonationInvalidId() {
		assertEquals(0, pss.getAllDonations().size());
		String error = null;
		try {
			pss.deleteDonation(0000);
		}catch(IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("No donation found with Id!", error);
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
	
	@Test
	public void testDeleteUserProfile() {
		assertEquals(0, pss.getAllUserProfiles().size());
		
		String address = "12345 Street Blvd.";
		boolean petExperience = false;
		int petsOwned = 0;
		String livingAccommodations = "House";
		
		pss.createUserProfile(address, petExperience, petsOwned, livingAccommodations);
		
		assertEquals(1, pss.getAllUserProfiles().size());
		
		pss.deleteUserProfile(pss.getAllUserProfiles().get(0).getUserProfileId());
		
		assertEquals(0, pss.getAllUserProfiles().size());
		
	}
	
	@Test
	public void testDeleteUserProfileNullId() {
		assertEquals(0, pss.getAllUserProfiles().size());
		String error = null;
		try {
			pss.deleteUserProfile(null);
		}catch(IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("User Profile ID invalid!",error);
		assertEquals(0, pss.getAllUserProfiles().size());
	}
	
	@Test
	public void testDeleteUserProfileInvalidId() {
		assertEquals(0, pss.getAllUserProfiles().size());
		String error = null;
		try {
			pss.deleteUserProfile(0000);
		}catch(IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("No UserProfile found with Id!",error);
		assertEquals(0, pss.getAllUserProfiles().size());
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
	
	@Test
	public void testDeletePerson() {
		//creating person
		assertEquals(0, pss.getAllPersons().size());
		String username = "tony";
		String password = "passowrd";
		
		pss.createPerson(username, password);
		assertEquals(1, pss.getAllPersons().size());
		//deleting person
		
		pss.deletePerson(username);
		assertEquals(0, pss.getAllPersons().size());
	}
	
	@Test
	public void testDeletePersonNullName() {
		assertEquals(0, pss.getAllPersons().size());
		String error = null;
		try {
			pss.deletePerson(null);
		}catch(IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("Username invalid!", error);
		assertEquals(0, pss.getAllPersons().size());
	}
	
	@Test
	public void testDeletePersonInvalidName() {
		assertEquals(0, pss.getAllPersons().size());
		String error = null;
		try {
			pss.deletePerson("Michael");
		}catch(IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("Person not found with username!", error);
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
	
	@Test
	public void testDeleteQuestion() {
		assertEquals(0, pss.getAllQuestions().size());
		
		String question = "how are you";
		
		pss.createQuestion(question);
		
		assertEquals(1, pss.getAllQuestions().size());
		
		pss.deleteQuestion(pss.getAllQuestions().get(0).getQuestionId());
		
		assertEquals(0, pss.getAllQuestions().size());
	}
	
	@Test
	public void testDeleteQuestionNullId() {
		assertEquals(0, pss.getAllQuestions().size());
		String error = null;
		try {
			pss.deleteQuestion(null);
		}catch(IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("Question ID invalid!", error);
		assertEquals(0, pss.getAllQuestions().size());
	}
	
	@Test
	public void testDeleteQuestionInvalidId() {
		assertEquals(0, pss.getAllQuestions().size());
		String error = null;
		try {
			pss.deleteQuestion(000);
		}catch(IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("No question found with Id!", error);
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

		pss.createPerson("username", "password");
		
		try {
			pss.createPetPost(avail, name, typeOfPet, desc, pss.getPerson("username"));
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
		
		pss.createPerson("user", "password");
		

		try {
			pss.createPetPost(avail, name, typeOfPet, desc, pss.getPerson("user"));
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
		pss.createPerson("user", "password");

		try {
			pss.createPetPost(avail, name, typeOfPet, desc, pss.getPerson("user"));
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
		pss.createPerson("user", "password");

		try {
			pss.createPetPost(avail, name, typeOfPet, desc, pss.getPerson("user"));
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertEquals("Pet must have a description!", error);
		assertEquals(0, pss.getAllPetPosts().size());
	}
	
	@Test
	public void testDeletePetPost() {
		assertEquals(0, pss.getAllPetPosts().size());
		boolean avail = true;
		String name = "Mike";
		String typeOfPet = "Cat";
		String desc = "blablabla";
		pss.createPerson("user", "password");
		
		pss.createPetPost(avail,name, typeOfPet, desc, pss.getPerson("user"));
		
		assertEquals(1, pss.getAllPetPosts().size());
		
		pss.deletePetPost(pss.getAllPetPosts().get(0).getPetPostId());
		
		assertEquals(0, pss.getAllPetPosts().size());
		
	}
	
	@Test
	public void testDeletePetPostNullId() {
		assertEquals(0, pss.getAllPetPosts().size());
		String error = null;
		try {
			pss.deletePetPost(null);
		}catch(IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("Pet Post ID invalid!",error);
		assertEquals(0, pss.getAllPetPosts().size());
	}
	
	@Test
	public void testDeletePetPostInvalidId() {
		assertEquals(0, pss.getAllPetPosts().size());
		String error = null;
		try {
			pss.deletePetPost(000);
		}catch(IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("No PetPost found with Id!",error);
		assertEquals(0, pss.getAllPetPosts().size());
	}

}

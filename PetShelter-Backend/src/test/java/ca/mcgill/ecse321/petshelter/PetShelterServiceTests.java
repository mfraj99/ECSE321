package ca.mcgill.ecse321.petshelter;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.junit.Assert.assertNotNull;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

import ca.mcgill.ecse321.petshelter.dao.*;
import ca.mcgill.ecse321.petshelter.model.*;
import ca.mcgill.ecse321.petshelter.service.PetShelterService;

@ExtendWith(MockitoExtension.class)
public class PetShelterServiceTests {

	@Mock
	private AdoptRequestRepository adoptRequestDao;
	@Mock
	private AppAdminRepository appAdminDao;
	@Mock
	private AppUserRepository appUserDao;
	@Mock
	private DonationRepository donationDao;
	@Mock
	private PersonRepository personDao;
	@Mock
	private PetPostRepository petPostDao;
	@Mock
	private QuestionRepository questionDao;
	@Mock
	private UserProfileRepository userProfileDao;

	@InjectMocks
	private PetShelterService service;

	private static final String PERSON_KEY = "TestPerson";
	private static final String PASSWORD_KEY = "TestPassword";

	@BeforeEach
	public void setMockOutput() {
		lenient().when(personDao.findByUsername(anyString())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(PERSON_KEY)) {
				Person person = new Person();
				person.setUsername(PERSON_KEY);
				person.setPassword(PASSWORD_KEY);
				return person;
			} else {
				return null;
			}

		});
		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};

		lenient().when(appAdminDao.save(any(AppAdmin.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(appUserDao.save(any(AppUser.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(adoptRequestDao.save(any(AdoptRequest.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(donationDao.save(any(Donation.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(personDao.save(any(Person.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(petPostDao.save(any(PetPost.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(questionDao.save(any(Question.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(userProfileDao.save(any(UserProfile.class))).thenAnswer(returnParameterAsAnswer);

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
		Person person1 = null;
		Person person2 = null;
		try {
			person1 = service.createPerson("person1", "password");
			person2 = service.createPerson("person2", "password");
		} catch (IllegalArgumentException e) {
			fail();
		}
		PetPost petPost = null;
		try {
			petPost = service.createPetPost(true, "Ebola", "dog", "cute doggo", person1);
		} catch (IllegalArgumentException e) {
			fail();
		}

		AdoptRequest adoptRequest = null;
		try {
			adoptRequest = service.createAdoptRequest(person2, petPost);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(adoptRequest);
		assertEquals(person2, adoptRequest.getRequestedBy());
		assertEquals(petPost, adoptRequest.getRequesting());
	}

	@Test
	public void testCreateAdoptRequestNullOwner() {
		Person person1 = null;
		PetPost petPost = null;
		try {
			person1 = service.createPerson("person1", "password");
			petPost = service.createPetPost(true, "CoronaVirus", "dog", "cute doggo", person1);
		} catch (IllegalArgumentException e) {
			fail();
		}

		String error = null;
		AdoptRequest adoptRequest = null;
		try {
			adoptRequest = service.createAdoptRequest(null, petPost);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(adoptRequest);
		assertEquals("Adoptrequest must have an owner!", error);

	}

	@Test
	public void testCreateAdoptRequestNullPetPost() {
		Person person1 = null;
		try {
			person1 = service.createPerson("person1", "password");
		} catch (IllegalArgumentException e) {
			fail();
		}

		String error = null;
		AdoptRequest adoptRequest = null;
		try {
			adoptRequest = service.createAdoptRequest(person1, null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(adoptRequest);
		assertEquals("Adoptrequest must be associated with a pet post!", error);
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
		assertEquals(0, service.getAllAppAdmins().size());
		String username = "JWS";
		String password = "3GPA";
		AppAdmin appAdmin = null;
		try {
			appAdmin = service.createAppAdmin(username, password);
		} catch (IllegalArgumentException e) {
			fail();
		}

		assertNotNull(appAdmin);
		assertEquals(username, appAdmin.getUsername());
		assertEquals(password, appAdmin.getPassword());
	}

	// more than one admin, cannot do that test since mockito doesnt persist the
	// previous AppAdmin
//	@Test
//	public void testCreateAppAdminMoreThanOne() {
//		String error = null;
//		String username = "JWS";
//		String password = "3GPA";
//		service.createAppAdmin(username, password);
//		username = "Karl";
//		password = "wololo";
//		AppAdmin appAdmin= null;
//		try {
//			appAdmin = service.createAppAdmin(username, password);
//		} catch (IllegalArgumentException e) {
//			error = e.getMessage();
//		}
//		assertEquals("Can only have one Admin!", error);
//		assertNull(appAdmin);
//	}

	@Test
	public void testCreateAppAdminNullUandP() {
		String username = null;
		String password = null;
		String error = null;
		AppAdmin appAdmin = null;
		try {
			appAdmin = service.createAppAdmin(username, password);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertEquals("AppAdmin name and password cannot be empty!", error);
		assertNull(appAdmin);

	}

	// empty username and password
	@Test
	public void testCreateAppAdminEmptyUandP() {

		String username = "";
		String password = "";
		String error = null;
		AppAdmin appAdmin = null;
		try {
			appAdmin = service.createAppAdmin(username, password);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertEquals("AppAdmin name and password cannot be empty!", error);
		assertNull(appAdmin);
	}

	// space in username and password
	@Test
	public void testCreateAppAdminSpaceUandP() {

		String username = " ";
		String password = " ";
		String error = null;
		AppAdmin appAdmin = null;

		try {
			appAdmin = service.createAppAdmin(username, password);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertEquals("AppAdmin name and password cannot be empty!", error);
		assertNull(appAdmin);

	}

	// empty username
	@Test
	public void testCreateAppAdminEmptyU() {
		String username = "";
		String password = "12344";
		String error = null;
		AppAdmin appAdmin = null;

		try {
			appAdmin = service.createAppAdmin(username, password);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("AppAdmin name cannot be empty!", error);
		assertNull(appAdmin);

	}

	// empty password
	@Test
	public void testCreateAppAdminEmptyP() {

		String username = "english";
		String password = "";
		String error = null;
		AppAdmin appAdmin = null;

		try {
			appAdmin = service.createAppAdmin(username, password);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("AppAdmin password cannot be empty!", error);
		assertNull(appAdmin);
	}

	// space in name
	@Test
	public void testCreateAppAdminSpaceU() {

		String username = " ";
		String password = "12344";
		String error = null;
		AppAdmin appAdmin = null;

		try {
			appAdmin = service.createAppAdmin(username, password);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("AppAdmin name cannot be empty!", error);
		assertNull(appAdmin);

	}

	// space in password
	@Test
	public void testCreateAppAdminSpaceP() {
		String username = "tony";
		String password = " ";
		String error = null;
		AppAdmin appAdmin = null;

		try {
			appAdmin = service.createAppAdmin(username, password);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("AppAdmin password cannot be empty!", error);
		assertNull(appAdmin);
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
		String username = "JWS";
		String password = "3GPA";
		PersonRole personRole = ca.mcgill.ecse321.petshelter.model.PersonRole.ADOPTER;
		AppUser appUser = null;
		try {
			appUser = service.createAppUser(username, password, personRole);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(appUser);
		assertEquals(username, appUser.getUsername());
		assertEquals(password, appUser.getPassword());
		assertEquals(personRole, appUser.getAppUserRole());
	}

	// create AppUser Owner
	@Test
	public void testCreateAppUserOwner() {
		String username = "JWS";
		String password = "3GPA";
		PersonRole personRole = ca.mcgill.ecse321.petshelter.model.PersonRole.OWNER;
		AppUser appUser = null;
		try {
			appUser = service.createAppUser(username, password, personRole);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(appUser);
		assertEquals(username, appUser.getUsername());
		assertEquals(password, appUser.getPassword());
		assertEquals(personRole, appUser.getAppUserRole());
	}

	// create AppUser no role
	@Test
	public void testCreateAppUserNoRole() {
		String username = "JWS";
		String password = "3GPA";
		PersonRole personRole = null;
		AppUser appUser = null;
		try {
			appUser = service.createAppUser(username, password, personRole);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(appUser);
		assertEquals(username, appUser.getUsername());
		assertEquals(password, appUser.getPassword());
		assertEquals(null, appUser.getAppUserRole());
	}

	@Test
	public void testCreateAppUserNullUandP() {
		String username = null;
		String password = null;
		String error = null;
		AppUser appUser = null;
		try {
			appUser = service.createAppUser(username, password, null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertEquals("AppUser name and password cannot be empty!", error);
		assertNull(appUser);

	}

	// empty username and password
	@Test
	public void testCreateAppUserEmptyUandP() {

		String username = "";
		String password = "";
		String error = null;
		AppUser appUser = null;
		try {
			appUser = service.createAppUser(username, password, null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertEquals("AppUser name and password cannot be empty!", error);
		assertNull(appUser);
	}

	// space in username and password
	@Test
	public void testCreateAppUserSpaceUandP() {

		String username = " ";
		String password = " ";
		String error = null;
		AppUser appUser = null;

		try {
			appUser = service.createAppUser(username, password, null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertEquals("AppUser name and password cannot be empty!", error);
		assertNull(appUser);

	}

	// empty username
	@Test
	public void testCreateAppUserEmptyU() {
		String username = "";
		String password = "12344";
		String error = null;
		AppUser appUser = null;

		try {
			appUser = service.createAppUser(username, password, null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("AppUser name cannot be empty!", error);
		assertNull(appUser);

	}

	// empty password
	@Test
	public void testCreateAppUserEmptyP() {

		String username = "english";
		String password = "";
		String error = null;
		AppUser appUser = null;

		try {
			appUser = service.createAppUser(username, password, null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("AppUser password cannot be empty!", error);
		assertNull(appUser);
	}

	// space in name
	@Test
	public void testCreateAppUserSpaceU() {

		String username = " ";
		String password = "12344";
		String error = null;
		AppUser appUser = null;

		try {
			appUser = service.createAppUser(username, password, null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("AppUser name cannot be empty!", error);
		assertNull(appUser);

	}

	// space in password
	@Test
	public void testCreateAppUserSpaceP() {
		String username = "tony";
		String password = " ";
		String error = null;
		AppUser appUser = null;

		try {
			appUser = service.createAppUser(username, password, null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("AppUser password cannot be empty!", error);
		assertNull(appUser);
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
		Donation donation = null;
		double amount = 100;
		String comment = "WOW";
		boolean setNameAnonymous = false;

		try {
			donation = service.createDonation(amount, comment, setNameAnonymous);
		} catch (IllegalArgumentException e) {
			fail();
		}

		assertNotNull(donation);
		assertEquals(amount, donation.getAmount());
		assertEquals(comment, donation.getComment());
		assertEquals(setNameAnonymous, donation.isSetNameAnonymous());

	}

	// incorrect donation value
	@Test
	public void testCreateDonationInvalidAmount() {

		Donation donation = null;
		double amount = -1;
		String comment = "WOW";
		boolean setNameAnonymous = false;
		String error = null;

		try {
			donation = service.createDonation(amount, comment, setNameAnonymous);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(donation);
		assertEquals("Donation amount cannot be 0!", error);

	}

	/*
	 * /////////////////////////////////////////////////////////////////////////////
	 * / TESTING USER PROFILE
	 * /////////////////////////////////////////////////////////////////////////////
	 * /
	 */
	@Test
	public void createUserProfile() {

		UserProfile userProfile = null;
		String address = "12345 Street Blvd.";
		boolean petExperience = false;
		int petsOwned = 0;
		String livingAccommodations = "I live in an apartment. It is very small.";

		try {
			userProfile = service.createUserProfile(address, petExperience, petsOwned, livingAccommodations);
		} catch (IllegalArgumentException e) {
			fail();
		}

		assertNotNull(userProfile);
		assertEquals(address, userProfile.getAddress());
		assertEquals(petExperience, userProfile.getHasExperienceWithPets());
		assertEquals(petsOwned, userProfile.getNumberOfPetsCurrentlyOwned());
		assertEquals(livingAccommodations, userProfile.getTypeOfLivingAccomodation());
	}

	// invalid number of pets value
	@Test
	public void testCreateUserProfileInvalidPetAmount() {

		UserProfile userProfile = null;
		String address = "12345 Street Blvd.";
		boolean petExperience = false;
		int petsOwned = -1;
		String livingAccommodations = "I live in an apartment. It is very small.";
		String error = null;

		try {
			userProfile = service.createUserProfile(address, petExperience, petsOwned, livingAccommodations);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(userProfile);
		assertEquals("Number of pets currently owned cannot be less than 0!", error);

	}

	// having no experience with pets but with numberOfPets>0
	@Test
	public void testCreateUserProfileOwnsPetsNoExperience() {
		UserProfile userProfile = null;

		String address = "12345 Street Blvd.";
		boolean petExperience = false;
		int petsOwned = 1;
		String livingAccommodations = "I live in an apartment. It is very small.";
		String error = null;

		try {
			userProfile = service.createUserProfile(address, petExperience, petsOwned, livingAccommodations);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(userProfile);
		assertEquals("Pet experience invalid! Cannot have no experience but own pets!", error);

	}

	// having an empty address field
	@Test
	public void testCreateUserProfileNoAddress() {
		UserProfile userProfile = null;

		String address = "";
		boolean petExperience = false;
		int petsOwned = 0;
		String livingAccommodations = "I live in an apartment. It is very small.";
		String error = null;

		try {
			userProfile = service.createUserProfile(address, petExperience, petsOwned, livingAccommodations);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(userProfile);
		assertEquals("Address cannot be empty!", error);

	}

	// having an living accommodations field
	@Test
	public void testCreateUserProfileNoLivingAccomodations() {
		UserProfile userProfile = null;

		String address = "12345 Street Blvd.";
		boolean petExperience = false;
		int petsOwned = 0;
		String livingAccommodations = "";
		String error = null;

		try {
			userProfile = service.createUserProfile(address, petExperience, petsOwned, livingAccommodations);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(userProfile);
		assertEquals("Living Accommodations cannot be empty!", error);

	}

	/*
	 * /////////////////////////////////////////////////////////////////////////////
	 * / TESTING PERSON
	 * /////////////////////////////////////////////////////////////////////////////
	 * /
	 */

	@Test
	public void testCreatePerson() {
		assertEquals(0, service.getAllPersons().size());
		String username = "Michael";
		String password = "LMAO";
		Person person = null;
		try {
			person = service.createPerson(username, password);

		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(person);
		assertEquals(username, person.getUsername());
		assertEquals(password, person.getPassword());
	}

	@Test
	public void testCreatePersonNullUandP() {
		String username = null;
		String password = null;
		String error = null;
		Person person = null;
		try {
			person = service.createPerson(username, password);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertEquals("Person name and password cannot be empty!", error);
		assertNull(person);

	}

	// empty username and password
	@Test
	public void testCreatePersonEmptyUandP() {

		String username = "";
		String password = "";
		String error = null;
		Person person = null;
		try {
			person = service.createPerson(username, password);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertEquals("Person name and password cannot be empty!", error);
		assertNull(person);
	}

	// space in username and password
	@Test
	public void testCreatePersonSpaceUandP() {

		String username = " ";
		String password = " ";
		String error = null;
		Person person = null;

		try {
			person = service.createPerson(username, password);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertEquals("Person name and password cannot be empty!", error);
		assertNull(person);

	}

	// empty username
	@Test
	public void testCreatePersonEmptyU() {
		String username = "";
		String password = "12344";
		String error = null;
		Person person = null;

		try {
			person = service.createPerson(username, password);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("Person name cannot be empty!", error);
		assertNull(person);

	}

	// empty password
	@Test
	public void testCreatePersonEmptyP() {

		String username = "english";
		String password = "";
		String error = null;
		Person person = null;

		try {
			person = service.createPerson(username, password);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("Person password cannot be empty!", error);
		assertNull(person);
	}

	// space in name
	@Test
	public void testCreatePersonSpaceU() {

		String username = " ";
		String password = "12344";
		String error = null;
		Person person = null;

		try {
			person = service.createPerson(username, password);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("Person name cannot be empty!", error);
		assertNull(person);

	}

	// space in password
	@Test
	public void testCreatePersonSpaceP() {
		String username = "tony";
		String password = " ";
		String error = null;
		Person person = null;

		try {
			person = service.createPerson(username, password);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("Person password cannot be empty!", error);
		assertNull(person);
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
		Question question = null;
		String questionString = "How much food does the pet need?";

		try {
			question = service.createQuestion(questionString);
		} catch (IllegalArgumentException e) {
			fail();
		}

		assertNotNull(question);
		assertEquals(questionString, question.getQuestion());
	}

	// empty question
	@Test
	public void testCreateEmptyQuestion() {
		Question question = null;
		String questionString = "";
		String error = "";

		try {
			question = service.createQuestion(questionString);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(question);
		assertEquals("Question cannot be empty!", error);

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
		PetPost petPost = null;
		boolean avail = true;
		String name = "Mike";
		String typeOfPet = "Cat";
		String desc = "Small domestic cat for sale";

		Person person = null;

		try {
			person = service.createPerson("username", "password");
		} catch (IllegalArgumentException e) {
			fail();
		}

		try {
			petPost = service.createPetPost(avail, name, typeOfPet, desc, person);
		} catch (IllegalArgumentException e) {
			fail();
		}

		assertNotNull(petPost);
		assertEquals(name, petPost.getName());
		assertEquals(typeOfPet, petPost.getTypeOfPet());
		assertEquals(desc, petPost.getDescription());
	}

	// Pet post with empty name
	@Test
	public void testCreatePetPostWithEmptyName() {
		PetPost petPost = null;

		boolean avail = true;
		String name = "";
		String typeOfPet = "Cat";
		String desc = "Small domestic cat for sale";
		String error = "";
		Person person = null;

		try {
			person = service.createPerson("user", "password");
		} catch (IllegalArgumentException e) {
			fail();
		}
		try {
			petPost = service.createPetPost(avail, name, typeOfPet, desc, person);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(petPost);
		assertEquals("Pet name cannot be empty!", error);
	}

	// Pet post with empty type of pet
	@Test
	public void testCreatePetPostWithEmptyTypeOfPet() {
		PetPost petPost = null;
		boolean avail = true;
		String name = "Mike";
		String typeOfPet = "";
		String desc = "Small domestic cat for sale";
		String error = "";

		Person person = null;

		try {
			person = service.createPerson("user", "password");
		} catch (IllegalArgumentException e) {
			fail();
		}

		try {
			petPost = service.createPetPost(avail, name, typeOfPet, desc, person);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(petPost);
		assertEquals("Pet type cannot be empty!", error);
	}

	// Pet post with empty description
	@Test
	public void testCreatePetPostWithEmptyDescription() {

		PetPost petPost = null;

		boolean avail = true;
		String name = "Mike";
		String typeOfPet = "Cat";
		String desc = "";
		String error = "";

		Person person = null;

		try {
			person = service.createPerson("user", "password");
		} catch (IllegalArgumentException e) {
			fail();
		}

		try {
			petPost = service.createPetPost(avail, name, typeOfPet, desc, person);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(petPost);
		assertEquals("Pet must have a description!", error);
	}

	@Test
	public void testCreatePetPostWithNullOwner() {

		PetPost petPost = null;
		boolean avail = true;
		String name = "Mike";
		String typeOfPet = "Cat";
		String desc = "cute cat";
		String error = "";

		try {
			petPost = service.createPetPost(avail, name, typeOfPet, desc, null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(petPost);
		assertEquals("Pet must have an owner", error);
	}

	/*
	 * /////////////////////////////////////////////////////////////////////////////
	 * / TESTING LOG IN LOG OUT
	 * /////////////////////////////////////////////////////////////////////////////
	 * /
	 */

	// login logout needs persistence to work, tests in petsheltercrudtests.java

//	@Test
//	public void testLogInAppAdmin() {
//		AppAdmin appAdmin = null;
//		String username = "hello";
//		String password = "world";
//		
//		try {
//			appAdmin = service.createAppAdmin(username, password);
//		} catch (IllegalArgumentException e) {
//			fail();
//		}
//		AppAdmin appAdminService = null;
//		
//		try {
//			appAdminService = service.loginAsAppAdmin(username, password);
//		} catch (IllegalArgumentException e) {
//			fail();
//		}
//		assertNotNull(appAdminService);
//	}
//	
//	@Test
//	public void testLogInAppUser() {
//		AppUser appUser = null;
//		
//		try {
//			appUser = service.createAppUser("user", "password", null);
//		} catch (IllegalArgumentException e) {
//			fail();
//		}
//		AppUser appUserService = null;
//		try {
//			appUserService = service.loginAsAppUser(appUser.getUsername(), appUser.getPassword());
//		} catch (IllegalArgumentException e) {
//			fail();
//		}
//		assertNotNull(appUserService);	
//	}
//	
//	

}
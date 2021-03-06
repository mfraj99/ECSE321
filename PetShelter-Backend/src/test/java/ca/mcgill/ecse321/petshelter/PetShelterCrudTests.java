
package ca.mcgill.ecse321.petshelter;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ca.mcgill.ecse321.petshelter.dao.*;
import ca.mcgill.ecse321.petshelter.model.*;
import ca.mcgill.ecse321.petshelter.service.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
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
		pss.logout();
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
		assertEquals(0, pss.getAllAdoptRequests().size());
		pss.createPerson("person1", "password");
		pss.createPetPost(true, "CoronaVirus", "dog", "cute doggo", pss.getPerson("person1"));
		String error = null;
		try {
			pss.createAdoptRequest(null, pss.getAllPetPosts().get(0));
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertEquals("Adoptrequest must have an owner!", error);
		assertEquals(0, pss.getAllAdoptRequests().size());

	}

	@Test
	public void testCreateAdoptRequestNullPetPost() {
		assertEquals(0, pss.getAllAdoptRequests().size());
		pss.createPerson("person1", "password");
		String error = null;

		try {
			pss.createAdoptRequest(pss.getPerson("person1"), null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertEquals("Adoptrequest must be associated with a pet post!", error);
		assertEquals(0, pss.getAllAdoptRequests().size());
	}

	@Test
	public void testDeleteAdoptRequest() {
		// creating an adoptrequest to delete
		assertEquals(0, pss.getAllAdoptRequests().size());
		pss.createPerson("person1", "password");
		pss.createPerson("person2", "password");
		pss.createPetPost(true, "Ebola", "dog", "cute doggo", pss.getPerson("person1"));
		int id = pss.getAllPetPosts().get(0).getPetPostId();

		pss.createAdoptRequest(pss.getPerson("person2"), pss.getPetPost(id));
		List<AdoptRequest> allAdoptRequests = pss.getAllAdoptRequests();
		assertEquals(1, allAdoptRequests.size());

		// deleting adoptRequest
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
		} catch (IllegalArgumentException e) {
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
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertEquals("AdoptRequest must be valid!", error);
		assertEquals(0, pss.getAllAdoptRequests().size());
	}

	@Test
	public void testUpdateAdoptRequestStatus() {
		assertEquals(0, pss.getAllAdoptRequests().size());
		assertEquals(0, pss.getAllAdoptRequests().size());
		pss.createPerson("person1", "password");
		pss.createPerson("person2", "password");
		pss.createPetPost(true, "Ebola", "dog", "cute doggo", pss.getPerson("person1"));
		int id = pss.getAllPetPosts().get(0).getPetPostId();

		pss.createAdoptRequest(pss.getPerson("person2"), pss.getPetPost(id));
		id = pss.getAllAdoptRequests().get(0).getAdoptRequestId();
		Status status = Status.APPROVED;
		pss.changeAdoptRequestStatus(id, status);
		assertEquals(1, pss.getAllAdoptRequests().size());
		assertEquals(status, pss.getAdoptRequest(id).getStatus());
	}

	@Test
	public void testUpdateAdoptRequestInvalidStatus() {
		pss.createPerson("person1", "password");
		pss.createPerson("person2", "password");
		pss.createPetPost(true, "Ebola", "dog", "cute doggo", pss.getPerson("person1"));
		int id = pss.getAllPetPosts().get(0).getPetPostId();

		pss.createAdoptRequest(pss.getPerson("person2"), pss.getPetPost(id));
		id = pss.getAllAdoptRequests().get(0).getAdoptRequestId();
		String error = null;
		try {
			pss.changeAdoptRequestStatus(id, null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("AdoptRequest status cannot be empty!", error);
	}

	@Test
	public void testUpdateAdoptRequestInvalidId() {
		String error = null;
		try {
			pss.changeAdoptRequestStatus(null, Status.PENDING);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("AdoptRequest Id cannot be empty!", error);
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

	// null username and password
	@Test
	public void testCreateAppAdminNullUandP() {
		assertEquals(0, pss.getAllAppAdmins().size());

		String username = null;
		String password = null;
		String error = null;

		try {
			pss.createAppAdmin(username, password);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertEquals("AppAdmin name and password cannot be empty!", error);
		assertEquals(0, pss.getAllAppAdmins().size());

	}

	// empty username and password
	@Test
	public void testCreateAppAdminEmptyUandP() {
		assertEquals(0, pss.getAllAppAdmins().size());

		String username = "";
		String password = "";
		String error = null;
		try {
			pss.createAppAdmin(username, password);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertEquals("AppAdmin name and password cannot be empty!", error);
		assertEquals(0, pss.getAllAppAdmins().size());
	}

	// space in username and password
	@Test
	public void testCreateAppAdminSpaceUandP() {
		assertEquals(0, pss.getAllAppAdmins().size());

		String username = " ";
		String password = " ";
		String error = null;

		try {
			pss.createAppAdmin(username, password);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertEquals("AppAdmin name and password cannot be empty!", error);
		assertEquals(0, pss.getAllAppAdmins().size());

	}

	// empty username
	@Test
	public void testCreateAppAdminEmptyU() {
		assertEquals(0, pss.getAllAppAdmins().size());

		String username = "";
		String password = "12344";
		String error = null;

		try {
			pss.createAppAdmin(username, password);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("AppAdmin name cannot be empty!", error);
		assertEquals(0, pss.getAllAppAdmins().size());

	}

	// empty password
	@Test
	public void testCreateAppAdminEmptyP() {
		assertEquals(0, pss.getAllAppAdmins().size());

		String username = "english";
		String password = "";
		String error = null;

		try {
			pss.createAppAdmin(username, password);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("AppAdmin password cannot be empty!", error);
		assertEquals(0, pss.getAllAppAdmins().size());
	}

	// space in name
	@Test
	public void testCreateAppAdminSpaceU() {
		assertEquals(0, pss.getAllAppAdmins().size());

		String username = " ";
		String password = "12344";
		String error = null;

		try {
			pss.createAppAdmin(username, password);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("AppAdmin name cannot be empty!", error);
		assertEquals(0, pss.getAllAppAdmins().size());

	}

	// space in password
	@Test
	public void testCreateAdminSpaceP() {
		assertEquals(0, pss.getAllAppAdmins().size());

		String username = "tony";
		String password = " ";
		String error = null;

		try {
			pss.createAppAdmin(username, password);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("AppAdmin password cannot be empty!", error);
		assertEquals(0, pss.getAllAppAdmins().size());
	}

	@Test
	public void testDeleteAppAdmin() {
		// creating person
		assertEquals(0, pss.getAllAppAdmins().size());
		String username = "tony";
		String password = "passowrd";

		pss.createAppAdmin(username, password);
		assertEquals(1, pss.getAllAppAdmins().size());
		// deleting person

		pss.deleteAppAdmin(username);
		assertEquals(0, pss.getAllAppAdmins().size());
	}

	@Test
	public void testDeleteAppAdminNullName() {
		assertEquals(0, pss.getAllAppAdmins().size());
		String error = null;
		try {
			pss.deleteAppAdmin("");
		} catch (IllegalArgumentException e) {
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
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("No AppAdmin found with username!", error);
		assertEquals(0, pss.getAllAppAdmins().size());
	}

	@Test
	public void testUpdateAppAdminPass() {
		assertEquals(0, pss.getAllAppAdmins().size());
		String username = "JWS";
		String password = "3GPA";
		String newPassword = "hello";
		pss.createAppAdmin(username, password);
		List<AppAdmin> allAppAdmins = pss.getAllAppAdmins();
		pss.changeAppAdminPassword(username, "hello");
		assertEquals(newPassword, pss.getAllAppAdmins().get(0).getPassword());
		assertEquals(1, allAppAdmins.size());
	}

	@Test
	public void testUpdateAppAdminPassNullName() {
		assertEquals(0, pss.getAllAppAdmins().size());
		String error = null;
		try {
			pss.changeAppAdminPassword(null, "hello");
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("Username cannot be empty!", error);
	}

	@Test
	public void testUpdateAppAdminPassNullPass() {
		assertEquals(0, pss.getAllAppAdmins().size());
		String error = null;
		String username = "JWS";
		String password = "3GPA";
		pss.createAppAdmin(username, password);
		try {
			pss.changeAppAdminPassword(username, null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("New password cannot be empty!", error);
		assertEquals(1, pss.getAllAppAdmins().size());
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

	// null username and password
	@Test
	public void testCreateAppUserNullUandP() {
		assertEquals(0, pss.getAllAppUsers().size());

		String username = null;
		String password = null;
		String error = null;

		try {
			pss.createAppUser(username, password, PersonRole.ADOPTER);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertEquals("AppUser name and password cannot be empty!", error);
		assertEquals(0, pss.getAllAppUsers().size());

	}

	// empty username and password
	@Test
	public void testCreateAppUserEmptyUandP() {
		assertEquals(0, pss.getAllAppUsers().size());

		String username = "";
		String password = "";
		String error = null;
		try {
			pss.createAppUser(username, password, PersonRole.ADOPTER);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertEquals("AppUser name and password cannot be empty!", error);
		assertEquals(0, pss.getAllAppUsers().size());
	}

	// space in username and password
	@Test
	public void testCreateAppUserSpaceUandP() {
		assertEquals(0, pss.getAllAppUsers().size());

		String username = " ";
		String password = " ";
		String error = null;

		try {
			pss.createAppUser(username, password, PersonRole.ADOPTER);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertEquals("AppUser name and password cannot be empty!", error);
		assertEquals(0, pss.getAllAppUsers().size());

	}

	// empty username
	@Test
	public void testCreateAppUserEmptyU() {
		assertEquals(0, pss.getAllAppUsers().size());

		String username = "";
		String password = "12344";
		String error = null;

		try {
			pss.createAppUser(username, password, PersonRole.ADOPTER);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("AppUser name cannot be empty!", error);
		assertEquals(0, pss.getAllAppUsers().size());

	}

	// empty password
	@Test
	public void testCreateAppUserEmptyP() {
		assertEquals(0, pss.getAllAppUsers().size());

		String username = "english";
		String password = "";
		String error = null;

		try {
			pss.createAppUser(username, password, PersonRole.ADOPTER);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("AppUser password cannot be empty!", error);
		assertEquals(0, pss.getAllAppUsers().size());
	}

	// space in name
	@Test
	public void testCreateAppUserSpaceU() {
		assertEquals(0, pss.getAllAppUsers().size());

		String username = " ";
		String password = "12344";
		String error = null;

		try {
			pss.createAppUser(username, password, PersonRole.ADOPTER);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("AppUser name cannot be empty!", error);
		assertEquals(0, pss.getAllAppUsers().size());

	}

	// space in password
	@Test
	public void testCreateAppUserSpaceP() {
		assertEquals(0, pss.getAllAppUsers().size());

		String username = "tony";
		String password = " ";
		String error = null;

		try {
			pss.createAppUser(username, password, PersonRole.ADOPTER);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("AppUser password cannot be empty!", error);
		assertEquals(0, pss.getAllAppUsers().size());
	}

	@Test
	public void testDeleteAppUser() {
		// creating person
		assertEquals(0, pss.getAllAppUsers().size());
		String username = "tony";
		String password = "passowrd";

		pss.createAppUser(username, password, PersonRole.ADOPTER);
		assertEquals(1, pss.getAllAppUsers().size());
		// deleting person

		pss.deleteAppUser(username);
		assertEquals(0, pss.getAllAppUsers().size());
	}

	@Test
	public void testDeleteAppUserNullName() {
		assertEquals(0, pss.getAllAppUsers().size());
		String error = null;
		try {
			pss.deleteAppUser("");
		} catch (IllegalArgumentException e) {
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
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("No AppUser found with username!", error);
		assertEquals(0, pss.getAllAppAdmins().size());
	}

	@Test
	public void testUpdateAppUserPass() {
		assertEquals(0, pss.getAllAppUsers().size());
		String username = "JWS";
		String password = "3GPA";
		String newPassword = "hello";
		pss.createAppUser(username, password, PersonRole.ADOPTER);
		List<AppUser> allAppUsers = pss.getAllAppUsers();
		pss.changeAppUserPassword(username, "hello");
		assertEquals(newPassword, pss.getAllAppUsers().get(0).getPassword());
		assertEquals(1, allAppUsers.size());
	}

	@Test
	public void testUpdateAppUserPassNullName() {
		assertEquals(0, pss.getAllAppUsers().size());
		String error = null;
		try {
			pss.changeAppUserPassword(null, "hello");
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("Username cannot be empty!", error);
	}

	@Test
	public void testUpdateAppUserPassNullPass() {
		assertEquals(0, pss.getAllAppUsers().size());
		String username = "JWS";
		String password = "3GPA";
		String error = null;
		pss.createAppUser(username, password, PersonRole.ADOPTER);
		try {
			pss.changeAppUserPassword(username, null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("New password cannot be empty!", error);
		assertEquals(1, pss.getAllAppUsers().size());
	}

	@Test
	public void testUpdateAppUserRole() {
		assertEquals(0, pss.getAllAppUsers().size());
		String username = "JWS";
		String password = "3GPA";
		PersonRole personRole = PersonRole.OWNER;
		pss.createAppUser(username, password, PersonRole.ADOPTER);
		List<AppUser> allAppUsers = pss.getAllAppUsers();
		pss.changeAppUserPersonRole(username, personRole);
		assertEquals(personRole, pss.getAllAppUsers().get(0).getAppUserRole());
		assertEquals(1, allAppUsers.size());
	}

	@Test
	public void testUpdateAppUserRoleNullName() {
		assertEquals(0, pss.getAllAppUsers().size());
		String username = "JWS";
		String password = "3GPA";
		String error = null;
		PersonRole personRole = PersonRole.OWNER;
		pss.createAppUser(username, password, PersonRole.ADOPTER);
		try {
			pss.changeAppUserPersonRole(null, personRole);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("Username cannot be empty!", error);
		assertEquals(1, pss.getAllAppUsers().size());
	}

	@Test
	public void testUpdateAppUserRoleNullRole() {
		assertEquals(0, pss.getAllAppUsers().size());
		String username = "JWS";
		String password = "3GPA";
		String error = null;
		PersonRole personRole = PersonRole.OWNER;
		pss.createAppUser(username, password, PersonRole.ADOPTER);
		try {
			pss.changeAppUserPersonRole(null, personRole);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("New appuser role cannot be empty!", error);
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
		} catch (IllegalArgumentException e) {
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
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("No donation found with Id!", error);
		assertEquals(0, pss.getAllDonations().size());
	}

	@Test
	public void testUpdateDonationComment() {
		assertEquals(0, pss.getAllDonations().size());

		double amount = 20;
		String comment = "WOW";
		boolean setNameAnonymous = false;

		pss.createDonation(amount, comment, setNameAnonymous);
		comment = "LMAO";
		pss.changeDonationComment(pss.getAllDonations().get(0).getDonationId(), comment);
		assertEquals(comment, pss.getAllDonations().get(0).getComment());
		assertEquals(1, pss.getAllDonations().size());
	}

	@Test
	public void testUpdateDonationCommentNullId() {
		assertEquals(0, pss.getAllDonations().size());
		double amount = 20;
		String comment = "WOW";
		boolean setNameAnonymous = false;

		String error = null;
		pss.createDonation(amount, comment, setNameAnonymous);
		comment = "LMAO";
		try {
			pss.changeDonationComment(null, comment);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("Donation ID invalid!", error);
		assertEquals(1, pss.getAllDonations().size());
	}

	@Test
	public void testUpdateDonationCommentNullComment() {
		assertEquals(0, pss.getAllDonations().size());
		double amount = 20;
		String comment = "WOW";
		boolean setNameAnonymous = false;

		String error = null;
		pss.createDonation(amount, comment, setNameAnonymous);
		comment = "LMAO";
		try {
			pss.changeDonationComment(pss.getAllDonations().get(0).getDonationId(), null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("New comment cannot be empty", error);
		assertEquals(1, pss.getAllDonations().size());
	}

	@Test
	public void testUpdateDonationAnonymous() {
		assertEquals(0, pss.getAllDonations().size());

		double amount = 20;
		String comment = "WOW";
		boolean setNameAnonymous = false;

		pss.createDonation(amount, comment, setNameAnonymous);
		setNameAnonymous = true;
		pss.changeDonationAnonymous(pss.getAllDonations().get(0).getDonationId(), setNameAnonymous);
		assertEquals(setNameAnonymous, pss.getAllDonations().get(0).isSetNameAnonymous());
		assertEquals(1, pss.getAllDonations().size());
	}

	@Test
	public void testUpdateDonationAnonymousNullId() {
		assertEquals(0, pss.getAllDonations().size());

		double amount = 20;
		String comment = "WOW";
		boolean setNameAnonymous = false;
		String error = null;
		pss.createDonation(amount, comment, setNameAnonymous);
		setNameAnonymous = true;
		try {
			pss.changeDonationAnonymous(pss.getAllDonations().get(0).getDonationId(), setNameAnonymous);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("Donation ID invalid!", error);
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
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("User Profile ID invalid!", error);
		assertEquals(0, pss.getAllUserProfiles().size());
	}

	@Test
	public void testDeleteUserProfileInvalidId() {
		assertEquals(0, pss.getAllUserProfiles().size());
		String error = null;
		try {
			pss.deleteUserProfile(0000);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("No UserProfile found with Id!", error);
		assertEquals(0, pss.getAllUserProfiles().size());
	}

	@Test
	public void testUpdateUserProfileAddress() {
		assertEquals(0, pss.getAllUserProfiles().size());

		String address = "12345 Street Blvd.";
		boolean petExperience = false;
		int petsOwned = 0;
		String livingAccommodations = "I live in an apartment. It is very small.";
		pss.createUserProfile(address, petExperience, petsOwned, livingAccommodations);

		String newAddress = "6789 Boulevard";
		pss.changeUserProfileAddress(pss.getAllUserProfiles().get(0).getUserProfileId(), newAddress);
		assertEquals(newAddress, pss.getAllUserProfiles().get(0).getAddress());
	}

	@Test
	public void testUpdateUserProfileAddressNullId() {
		assertEquals(0, pss.getAllUserProfiles().size());

		String address = "12345 Street Blvd.";
		boolean petExperience = false;
		int petsOwned = 0;
		String livingAccommodations = "I live in an apartment. It is very small.";
		pss.createUserProfile(address, petExperience, petsOwned, livingAccommodations);

		String newAddress = "6789 Boulevard";
		String error = null;

		try {
			pss.changeUserProfileAddress(null, newAddress);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("Id cannot be empty!", error);
		assertEquals(1, pss.getAllUserProfiles().size());
	}

	@Test
	public void testUpdateUserProfileAddressNullAddress() {
		assertEquals(0, pss.getAllUserProfiles().size());

		String address = "12345 Street Blvd.";
		boolean petExperience = false;
		int petsOwned = 0;
		String livingAccommodations = "I live in an apartment. It is very small.";
		pss.createUserProfile(address, petExperience, petsOwned, livingAccommodations);
		String error = null;

		try {
			pss.changeUserProfileAddress(pss.getAllUserProfiles().get(0).getUserProfileId(), null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("New address cannot be empty", error);
		assertEquals(1, pss.getAllUserProfiles().size());
	}

	@Test
	public void testUpdateUserProfileHasExperienceWithPets() {
		assertEquals(0, pss.getAllUserProfiles().size());

		String address = "12345 Street Blvd.";
		boolean petExperience = false;
		int petsOwned = 0;
		String livingAccommodations = "I live in an apartment. It is very small.";
		pss.createUserProfile(address, petExperience, petsOwned, livingAccommodations);

		boolean hasExperienceWithPets = true;
		pss.changeUserProfileHasExperienceWithPets(pss.getAllUserProfiles().get(0).getUserProfileId(),
				hasExperienceWithPets);
		assertEquals(hasExperienceWithPets, pss.getAllUserProfiles().get(0).getHasExperienceWithPets());
	}

	@Test
	public void testUpdateUserProfileHasExperienceWithPetsNullId() {
		assertEquals(0, pss.getAllUserProfiles().size());

		String address = "12345 Street Blvd.";
		boolean petExperience = false;
		int petsOwned = 0;
		String livingAccommodations = "I live in an apartment. It is very small.";
		pss.createUserProfile(address, petExperience, petsOwned, livingAccommodations);
		String error = null;
		boolean hasExperienceWithPets = true;
		try {
			pss.changeUserProfileHasExperienceWithPets(null, hasExperienceWithPets);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("Id cannot be empty!", error);

	}

	@Test
	public void testUpdateUserProfileNumberOfPetsCurrentlyOwned() {
		assertEquals(0, pss.getAllUserProfiles().size());

		String address = "12345 Street Blvd.";
		boolean petExperience = false;
		int petsOwned = 0;
		String livingAccommodations = "I live in an apartment. It is very small.";
		pss.createUserProfile(address, petExperience, petsOwned, livingAccommodations);

		int newPetsOwned = 2;
		pss.changeUserProfileNumberOfPetsCurrentlyOwned(pss.getAllUserProfiles().get(0).getUserProfileId(),
				newPetsOwned);
		assertEquals(newPetsOwned, pss.getAllUserProfiles().get(0).getNumberOfPetsCurrentlyOwned());
	}

	@Test
	public void testUpdateUserProfileNumberOfPetsCurrentlyOwnedNullId() {
		assertEquals(0, pss.getAllUserProfiles().size());

		String address = "12345 Street Blvd.";
		boolean petExperience = false;
		int petsOwned = 0;
		String livingAccommodations = "I live in an apartment. It is very small.";
		pss.createUserProfile(address, petExperience, petsOwned, livingAccommodations);
		String error = null;

		int newPetsOwned = 2;
		try {
			pss.changeUserProfileNumberOfPetsCurrentlyOwned(null, newPetsOwned);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("Id cannot be empty!", error);
	}

	@Test
	public void testUpdateUserProfileNumberOfPetsCurrentlyOwnedNullNumber() {
		assertEquals(0, pss.getAllUserProfiles().size());

		String address = "12345 Street Blvd.";
		boolean petExperience = false;
		int petsOwned = 0;
		String livingAccommodations = "I live in an apartment. It is very small.";
		pss.createUserProfile(address, petExperience, petsOwned, livingAccommodations);

		String error = null;
		try {
			pss.changeUserProfileNumberOfPetsCurrentlyOwned(pss.getAllUserProfiles().get(0).getUserProfileId(), null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("Number of Pets cannot be empty", error);
	}

	@Test
	public void testUpdateUserProfileTypeOfLivingAccommodation() {
		assertEquals(0, pss.getAllUserProfiles().size());

		String address = "12345 Street Blvd.";
		boolean petExperience = false;
		int petsOwned = 0;
		String livingAccommodations = "I live in an apartment. It is very small.";
		pss.createUserProfile(address, petExperience, petsOwned, livingAccommodations);

		String newLivingAccommodations = "house";
		pss.changeUserProfileTypeOfLivingAccommodation(pss.getAllUserProfiles().get(0).getUserProfileId(),
				newLivingAccommodations);
		assertEquals(newLivingAccommodations, pss.getAllUserProfiles().get(0).getTypeOfLivingAccomodation());
	}

	@Test
	public void testUpdateUserProfileTypeOfLivingAccommodationNullId() {
		assertEquals(0, pss.getAllUserProfiles().size());

		String address = "12345 Street Blvd.";
		boolean petExperience = false;
		int petsOwned = 0;
		String livingAccommodations = "I live in an apartment. It is very small.";
		pss.createUserProfile(address, petExperience, petsOwned, livingAccommodations);

		String error = null;
		String newLivingAccommodations = "house";
		try {
			pss.changeUserProfileTypeOfLivingAccommodation(null, newLivingAccommodations);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("Id cannot be empty!", error);
	}

	@Test
	public void testUpdateUserProfileTypeOfLivingAccommodationNullString() {
		assertEquals(0, pss.getAllUserProfiles().size());

		String address = "12345 Street Blvd.";
		boolean petExperience = false;
		int petsOwned = 0;
		String livingAccommodations = "I live in an apartment. It is very small.";
		pss.createUserProfile(address, petExperience, petsOwned, livingAccommodations);

		String error = null;
		try {
			pss.changeUserProfileTypeOfLivingAccommodation(pss.getAllUserProfiles().get(0).getUserProfileId(), null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("Living accomodation cannot be empty", error);
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
		// creating person
		assertEquals(0, pss.getAllPersons().size());
		String username = "tony";
		String password = "passowrd";

		pss.createPerson(username, password);
		assertEquals(1, pss.getAllPersons().size());
		// deleting person

		pss.deletePerson(username);
		assertEquals(0, pss.getAllPersons().size());
	}

	@Test
	public void testDeletePersonNullName() {
		assertEquals(0, pss.getAllPersons().size());
		String error = null;
		try {
			pss.deletePerson(null);
		} catch (IllegalArgumentException e) {
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
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("Person not found with username!", error);
		assertEquals(0, pss.getAllPersons().size());
	}

	@Test
	public void testUpdatePersonPass() {
		assertEquals(0, pss.getAllPersons().size());
		String username = "JWS";
		String password = "3GPA";
		String newPassword = "hello";
		pss.createPerson(username, password);
		List<Person> allPersons = pss.getAllPersons();
		pss.changePersonPassword(username, "hello");
		assertEquals(newPassword, pss.getAllPersons().get(0).getPassword());
		assertEquals(1, allPersons.size());
	}

	@Test
	public void testUpdatePersonPassNullName() {
		assertEquals(0, pss.getAllPersons().size());
		String username = "JWS";
		String password = "3GPA";
		pss.createPerson(username, password);
		String error = null;
		try {
			pss.changePersonPassword(null, "hello");
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("Username cannot be empty!", error);
		assertEquals(1, pss.getAllPersons().size());
	}

	@Test
	public void testUpdatePersonPassNullPass() {
		assertEquals(0, pss.getAllPersons().size());
		String username = "JWS";
		String password = "3GPA";
		pss.createPerson(username, password);
		String error = null;
		try {
			pss.changePersonPassword(username, null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("New password cannot be empty!", error);
		assertEquals(1, pss.getAllPersons().size());
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
		} catch (IllegalArgumentException e) {
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
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("No question found with Id!", error);
		assertEquals(0, pss.getAllQuestions().size());
	}

	@Test
	public void testUpdateQuestionString() {
		assertEquals(0, pss.getAllQuestions().size());
		String question = "how are you";
		pss.createQuestion(question);
		String newQuestion = "hello hello";
		pss.changeQuestionString(pss.getAllQuestions().get(0).getQuestionId(), newQuestion);
		assertEquals(newQuestion, pss.getAllQuestions().get(0).getQuestion());
	}

	@Test
	public void testUpdateQuestionStringNullId() {
		assertEquals(0, pss.getAllQuestions().size());
		String question = "how are you";
		pss.createQuestion(question);
		String newQuestion = "hello hello";
		String error = null;
		try {
			pss.changeQuestionString(null, newQuestion);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("No question found with Id!", error);
		assertEquals(1, pss.getAllQuestions().size());
	}

	@Test
	public void testUpdateQuestionStringNullQuestion() {
		assertEquals(0, pss.getAllQuestions().size());
		String question = "how are you";
		pss.createQuestion(question);
		String error = null;
		try {
			pss.changeQuestionString(pss.getAllQuestions().get(0).getQuestionId(), null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("New question cannot be empty", error);
		assertEquals(1, pss.getAllQuestions().size());
	}

	@Test
	public void testUpdateQuestionAnswer() {
		assertEquals(0, pss.getAllQuestions().size());
		String question = "how are you";
		pss.createQuestion(question);
		String answer = "hello hello";
		pss.changeQuestionAnswer(pss.getAllQuestions().get(0).getQuestionId(), answer);
		assertEquals(answer, pss.getAllQuestions().get(0).getAnswer());
	}

	@Test
	public void testUpdateQuestionAnswerNullId() {
		assertEquals(0, pss.getAllQuestions().size());
		String question = "how are you";
		pss.createQuestion(question);
		String answer = "hello hello";
		String error = null;
		try {
			pss.changeQuestionAnswer(null, answer);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("No question found with Id!", error);
		assertEquals(1, pss.getAllQuestions().size());
	}

	@Test
	public void testUpdateQuestionAnswerNullAnswer() {
		assertEquals(0, pss.getAllQuestions().size());
		String question = "how are you";
		pss.createQuestion(question);
		String error = null;
		try {
			pss.changeQuestionAnswer(pss.getAllQuestions().get(0).getQuestionId(), null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("New answer cannot be empty", error);
		assertEquals(1, pss.getAllQuestions().size());
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
	public void testCreatePetPostWithNullOwner() {
		assertEquals(0, pss.getAllPetPosts().size());

		boolean avail = true;
		String name = "Mike";
		String typeOfPet = "Cat";
		String desc = "cute cat";
		String error = "";
		try {
			pss.createPetPost(avail, name, typeOfPet, desc, null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertEquals("Pet must have an owner", error);
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

		pss.createPetPost(avail, name, typeOfPet, desc, pss.getPerson("user"));

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
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("Pet Post ID invalid!", error);
		assertEquals(0, pss.getAllPetPosts().size());
	}

	@Test
	public void testDeletePetPostInvalidId() {
		assertEquals(0, pss.getAllPetPosts().size());
		String error = null;
		try {
			pss.deletePetPost(000);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("No PetPost found with Id!", error);
		assertEquals(0, pss.getAllPetPosts().size());
	}

	@Test
	public void testUpdatePetPostAvailability() {
		assertEquals(0, pss.getAllAppUsers().size());
		boolean avail = true;
		String name = "Mike";
		String typeOfPet = "Cat";
		String desc = "Small domestic cat for sale";

		pss.createPerson("username", "password");
		pss.createPetPost(avail, name, typeOfPet, desc, pss.getPerson("username"));
		boolean availability = false;

		pss.changePetPostAvailability(pss.getAllPetPosts().get(0).getPetPostId(), availability);
		assertEquals(availability, pss.getAllPetPosts().get(0).isAvailability());

	}

	@Test
	public void testUpdatePetPostAvailabilityNullId() {
		assertEquals(0, pss.getAllAppUsers().size());
		boolean avail = true;
		String name = "Mike";
		String typeOfPet = "Cat";
		String desc = "Small domestic cat for sale";

		pss.createPerson("username", "password");
		pss.createPetPost(avail, name, typeOfPet, desc, pss.getPerson("username"));
		boolean availability = false;
		String error = null;
		try {
			pss.changePetPostAvailability(null, availability);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("Pet Post ID invalid!", error);
	}

	@Test
	public void testUpdatePetPostName() {
		assertEquals(0, pss.getAllAppUsers().size());
		boolean avail = true;
		String name = "Mike";
		String typeOfPet = "Cat";
		String desc = "Small domestic cat for sale";

		pss.createPerson("username", "password");
		pss.createPetPost(avail, name, typeOfPet, desc, pss.getPerson("username"));
		String newName = "Drake";

		pss.changePetPostName(pss.getAllPetPosts().get(0).getPetPostId(), newName);
		assertEquals(newName, pss.getAllPetPosts().get(0).getName());
	}

	@Test
	public void testUpdatePetPostNameNullId() {
		assertEquals(0, pss.getAllAppUsers().size());
		boolean avail = true;
		String name = "Mike";
		String typeOfPet = "Cat";
		String desc = "Small domestic cat for sale";
		String error = null;
		pss.createPerson("username", "password");
		pss.createPetPost(avail, name, typeOfPet, desc, pss.getPerson("username"));
		String newName = "Drake";

		try {
			pss.changePetPostName(null, newName);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("Pet Post ID invalid!", error);
	}

	@Test
	public void testUpdatePetPostNameNullName() {
		assertEquals(0, pss.getAllAppUsers().size());
		boolean avail = true;
		String name = "Mike";
		String typeOfPet = "Cat";
		String desc = "Small domestic cat for sale";

		pss.createPerson("username", "password");
		pss.createPetPost(avail, name, typeOfPet, desc, pss.getPerson("username"));
		String error = null;
		try {
			pss.changePetPostName(pss.getAllPetPosts().get(0).getPetPostId(), null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("New name cannot be empty", error);
	}

	@Test
	public void testUpdatePetPostDescription() {
		assertEquals(0, pss.getAllAppUsers().size());
		boolean avail = true;
		String name = "Mike";
		String typeOfPet = "Cat";
		String desc = "Small domestic cat for sale";

		pss.createPerson("username", "password");
		pss.createPetPost(avail, name, typeOfPet, desc, pss.getPerson("username"));
		String description = "Very large dog";

		pss.changePetPostDescription(pss.getAllPetPosts().get(0).getPetPostId(), description);
		assertEquals(description, pss.getAllPetPosts().get(0).getDescription());
	}

	@Test
	public void testUpdatePetPostDescriptionNullId() {
		assertEquals(0, pss.getAllAppUsers().size());
		boolean avail = true;
		String name = "Mike";
		String typeOfPet = "Cat";
		String desc = "Small domestic cat for sale";

		pss.createPerson("username", "password");
		pss.createPetPost(avail, name, typeOfPet, desc, pss.getPerson("username"));
		String description = "Very large dog";

		String error = null;
		try {
			pss.changePetPostDescription(null, description);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("Pet Post ID invalid!", error);
	}

	@Test
	public void testUpdatePetPostDescriptionNullDescription() {
		assertEquals(0, pss.getAllAppUsers().size());
		boolean avail = true;
		String name = "Mike";
		String typeOfPet = "Cat";
		String desc = "Small domestic cat for sale";

		pss.createPerson("username", "password");
		pss.createPetPost(avail, name, typeOfPet, desc, pss.getPerson("username"));
		String error = null;
		try {
			pss.changePetPostDescription(pss.getAllPetPosts().get(0).getPetPostId(), null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("New description cannot be empty", error);
	}

	/*
	 * /////////////////////////////////////////////////////////////////////////////
	 * / TESTING LOG IN LOG OUT
	 * /////////////////////////////////////////////////////////////////////////////
	 * /
	 */

	@Test
	public void testLogInAppAdmin() {
		assertEquals(0, pss.getAllAppAdmins().size());
		String username = "hello";
		String password = "world";
		pss.createAppAdmin(username, password);
		pss.loginAsAppAdmin(username, password);
		assertNotNull(pss.getLoggedInUser());
		assertEquals(username, pss.getLoggedInUser().getUsername());
		assertEquals(password, pss.getLoggedInUser().getPassword());

	}

	@Test
	public void testLogInAppAdminNullName() {
		assertEquals(0, pss.getAllAppAdmins().size());
		String username = "";
		String password = "world";
		String error = null;
		try {
			pss.loginAsAppAdmin(username, password);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("Username cannot be empty.", error);
		assertNull(pss.getLoggedInUser());
	}

	@Test
	public void testLogInAppAdminNullPass() {
		assertEquals(0, pss.getAllAppAdmins().size());
		String username = "hello";
		String password = "";
		String error = null;
		try {
			pss.loginAsAppAdmin(username, password);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("Password cannot be empty.", error);
		assertNull(pss.getLoggedInUser());
	}

	@Test
	public void testLogInAppAdminInvalidUser() {
		assertEquals(0, pss.getAllAppAdmins().size());
		String username = "hello";
		String password = "world";
		String error = null;
		try {
			pss.loginAsAppAdmin(username, password);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("No admin found with this username!", error);
		assertNull(pss.getLoggedInUser());
	}

	@Test
	public void testLogInAppUser() {
		assertEquals(0, pss.getAllAppUsers().size());
		String username = "hello";
		String password = "world";
		pss.createAppUser(username, password, null);
		pss.loginAsAppUser(username, password);
		assertNotNull(pss.getLoggedInUser());
		assertEquals(username, pss.getLoggedInUser().getUsername());
		assertEquals(password, pss.getLoggedInUser().getPassword());
	}

	@Test
	public void testLogInAppUserNullName() {
		assertEquals(0, pss.getAllAppUsers().size());
		String username = "";
		String password = "world";
		String error = null;
		try {
			pss.loginAsAppUser(username, password);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("Username cannot be empty.", error);
		assertNull(pss.getLoggedInUser());
	}

	@Test
	public void testLogInAppUserNullPass() {
		assertEquals(0, pss.getAllAppUsers().size());
		String username = "hello";
		String password = "";
		String error = null;
		try {
			pss.loginAsAppUser(username, password);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("Password cannot be empty.", error);
		assertNull(pss.getLoggedInUser());
	}

	@Test
	public void testLogInAppUserInvalidUser() {
		assertEquals(0, pss.getAllAppUsers().size());
		String username = "hello";
		String password = "world";
		String error = null;
		try {
			pss.loginAsAppUser(username, password);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("This user account could not be found.", error);
		assertNull(pss.getLoggedInUser());
	}

}

package ca.mcgill.ecse321.petshelter;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.util.List;

import ca.mcgill.ecse321.petshelter.dao.*;
import ca.mcgill.ecse321.petshelter.model.*;
import ca.mcgill.ecse321.petshelter.service.PetShelterService;


@ExtendWith(MockitoExtension.class)
public class PetShelterServiceTests {
	
	@Mock 
	private PersonRepository personDao;
	@Mock 
	private AppAdminRepository appAdminDao;
	@Mock 
	private AppUserRepository appUserDao;
	
	@InjectMocks
	private PetShelterService service;
	
	private static final String PERSON_KEY = "TestPerson";
	private static final String PASSWORD_KEY = "TestPassword";
	
	@BeforeEach
	public void setMockOutput() {
		lenient().when(personDao.findByUsername(anyString())).thenAnswer((InvocationOnMock 
				invocation) -> {
					if(invocation.getArgument(0).equals(PERSON_KEY)) {
						Person person = new Person();
						person.setUsername(PERSON_KEY);
						person.setPassword(PASSWORD_KEY);
						return person;
					} else {
						return null;
					}
					
				});
		lenient().when(appAdminDao.findByUsername(anyString())).thenAnswer((InvocationOnMock 
				invocation) -> {
					if(invocation.getArgument(0).equals(PERSON_KEY)) {
						AppAdmin appAdmin = new AppAdmin();
						appAdmin.setUsername(PERSON_KEY);
						appAdmin.setPassword(PASSWORD_KEY);
						return appAdmin;
					} else {
						return null;
					}
					
				});
		lenient().when(appUserDao.findByUsername(anyString())).thenAnswer((InvocationOnMock 
				invocation) -> {
					if(invocation.getArgument(0).equals(PERSON_KEY)) {
						AppUser appUser = new AppUser();
						appUser.setUsername(PERSON_KEY);
						appUser.setPassword(PASSWORD_KEY);
						return appUser;
					} else {
						return null;
					}
					
				});
		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};
		lenient().when(personDao.save(any(Person.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(appAdminDao.save(any(AppAdmin.class))).thenAnswer(returnParameterAsAnswer);
	
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
		}catch(IllegalArgumentException e) {
			fail();
		}
		
		assertNotNull(appAdmin);
		assertEquals(username, appAdmin.getUsername());
		assertEquals(password, appAdmin.getPassword());
	}

	// more than one admin, cannot do that test since mockito doesnt persist the previous AppAdmin
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
			appAdmin =service.createAppAdmin(username, password);
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
			appAdmin =service.createAppAdmin(username, password);
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
			appAdmin =service.createAppAdmin(username, password);
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
			appAdmin =service.createAppAdmin(username, password);
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
			appAdmin =service.createAppAdmin(username, password);
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
			appAdmin =service.createAppAdmin(username, password);
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
		AppUser appUser= null;
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
			appUser =service.createAppUser(username, password, null);
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
			appUser =service.createAppUser(username, password, null);
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
			appUser =service.createAppUser(username, password, null);
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
			appUser =service.createAppUser(username, password, null);
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
			appUser =service.createAppUser(username, password, null);
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
			appUser =service.createAppUser(username, password, null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("AppUser password cannot be empty!", error);
		assertNull(appUser);
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
			person =service.createPerson(username, password);
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
			person =service.createPerson(username, password);
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
			person =service.createPerson(username, password);
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
			person =service.createPerson(username, password);
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
			person =service.createPerson(username, password);
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
			person =service.createPerson(username, password);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("Person password cannot be empty!", error);
		assertNull(person);
	}
	
	
	
	
}
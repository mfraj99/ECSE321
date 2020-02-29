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

import ca.mcgill.ecse321.petshelter.dao.*;
import ca.mcgill.ecse321.petshelter.model.*;
import ca.mcgill.ecse321.petshelter.service.PetShelterService;


@ExtendWith(MockitoExtension.class)
public class PetShelterServiceTests {
	
	@Mock 
	private PersonRepository personDao;
	
	@InjectMocks
	private PetShelterService service;
	
	private static final String PERSON_KEY = "TestPerson";
	
	@BeforeEach
	public void setMockOutput() {
		lenient().when(personDao.findByUsername(anyString())).thenAnswer((InvocationOnMock 
				invocation) -> {
					if(invocation.getArgument(0).equals(PERSON_KEY)) {
						Person person = new Person();
						person.setUsername(PERSON_KEY);
						return person;
					} else {
						return null;
					}
					
				});
	}
	
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
	}
	
	
	
	
}
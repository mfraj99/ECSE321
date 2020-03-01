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
	private static final PersonRole ADOPTER = ca.mcgill.ecse321.petshelter.model.PersonRole.ADOPTER;

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
	
	
	//variables for tracking the status of logged in users
	private static Person loggedInUser = null;
	
	public Person getLoggedInUser() {
		return loggedInUser;
	}

	// ADOPTREQUEST
	@Transactional
	public AdoptRequest createAdoptRequest(Person owner, PetPost petPost) {
		if(owner == null) {
			throw new IllegalArgumentException("Adoptrequest must have an owner!");
		}
		if(petPost == null) {
			throw new IllegalArgumentException("Adoptrequest must be associated with a pet post!");
		}
		AdoptRequest adoptRequest = new AdoptRequest();
		adoptRequest.setStatus(PENDING);
		adoptRequest.setRequestedBy(owner);
		adoptRequest.setRequesting(petPost);
		adoptRequestRepository.save(adoptRequest);
		return adoptRequest;
	}

	@Transactional
	public AdoptRequest getAdoptRequest(Integer id) {
		AdoptRequest adoptRequest = adoptRequestRepository.findByAdoptRequestId(id);
		if(adoptRequest ==null) {
			throw new IllegalArgumentException("No Adoptrequest found with this Id!");
		}
		return adoptRequest;
	}

	@Transactional
	public List<AdoptRequest> getAllAdoptRequests() {
		return toList(adoptRequestRepository.findAll());
	}
	
	@Transactional
	public boolean deleteAdoptRequest(Integer adoptRequestId) {
		if (adoptRequestId == null) {
			throw new IllegalArgumentException("AdoptRequestId invalid!");
		}
	
		boolean deleted = false; //not deleted yet
		AdoptRequest adoptRequest = adoptRequestRepository.findByAdoptRequestId(adoptRequestId); 
		if (adoptRequest != null) {
			adoptRequestRepository.delete(adoptRequest);
			deleted = true;
		} else { throw new IllegalArgumentException("AdoptRequest must be valid!");}
		return deleted;
	}

	// APPADMIN
	@Transactional
	public AppAdmin createAppAdmin(String username, String password) {
		if (this.getAllAppAdmins().size() == 1) {
			throw new IllegalArgumentException("Can only have one Admin!");
		}
		if ((username == null || username.trim().length() == 0)
				&& (password == null || password.trim().length() == 0)) {
			throw new IllegalArgumentException("AppAdmin name and password cannot be empty!");
		}
		if (username == null || username.trim().length() == 0) {
			throw new IllegalArgumentException("AppAdmin name cannot be empty!");
		}
		if (password == null || password.trim().length() == 0) {
			throw new IllegalArgumentException("AppAdmin password cannot be empty!");
		}
		AppAdmin appAdmin = new AppAdmin();
		appAdmin.setUsername(username);
		appAdmin.setPassword(password);
		appAdminRepository.save(appAdmin);
		return appAdmin;
	}

	@Transactional
	public AppAdmin getAppAdmin(String username) {
		AppAdmin appAdmin = appAdminRepository.findByUsername(username);
		if(appAdmin == null) {
			throw new IllegalArgumentException("No admin found with this username!");
		}
		return appAdmin;
	}

	@Transactional
	public List<AppAdmin> getAllAppAdmins() {
		return toList(appAdminRepository.findAll());
	}
	
	@Transactional
	public boolean deleteAppAdmin(String username) {
		if (username == null || username.trim().length() == 0) {
			throw new IllegalArgumentException("Username invalid!");
		}
	
		boolean deleted = false; //not deleted yet
		AppAdmin appAdmin = appAdminRepository.findByUsername(username);
		if (appAdmin != null) {
			appAdminRepository.delete(appAdmin);
			deleted = true;
		} else { throw new IllegalArgumentException("No AppAdmin found with username!");}
		return deleted;
	}
	

	// APPUSER
	@Transactional
	public AppUser createAppUser(String username, String password, PersonRole personRole) {

		if ((username == null || username.trim().length() == 0)
				&& (password == null || password.trim().length() == 0)) {
			throw new IllegalArgumentException("AppUser name and password cannot be empty!");
		}
		if (username == null || username.trim().length() == 0) {
			throw new IllegalArgumentException("AppUser name cannot be empty!");
		}
		if (password == null || password.trim().length() == 0) {
			throw new IllegalArgumentException("AppUser password cannot be empty!");
		}
		
		

		AppUser appUser = new AppUser();
		appUser.setUsername(username);
		appUser.setPassword(password);
		appUser.setAppUserRole(personRole);
		appUserRepository.save(appUser);
		return appUser;
	}

	@Transactional
	public AppUser getAppUser(String username) {
		if (username == null || username.trim().length() == 0) {
			throw new IllegalArgumentException("No AppUser found with username!");
		}
		AppUser appUser = appUserRepository.findByUsername(username);
		return appUser;
	}

	@Transactional
	public List<AppUser> getAllAppUsers() {
		return toList(appUserRepository.findAll());
	}
	
	
	@Transactional
	public boolean deleteAppUser(String username) {
		if (username == null || username.trim().length()==0) {
			throw new IllegalArgumentException("Username invalid!");
		}
	
		boolean deleted = false; //not deleted yet
		AppUser appUser = appUserRepository.findByUsername(username);
		if (appUser != null) {
			appUserRepository.delete(appUser);
			deleted = true;
		} else { throw new IllegalArgumentException("No AppUser found with username!");}
		return deleted;
	}


	// DONATION
	@Transactional
	public Donation createDonation(double amount, String comment, boolean setNameAnonymous) {
		if (amount <= 0) {
			throw new IllegalArgumentException("Donation amount cannot be 0!");
		}
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
		if(donation ==null) {
			throw new IllegalArgumentException("No donation found with Id!");
		}
		return donation;
	}

	@Transactional
	public List<Donation> getAllDonations() {
		return toList(donationRepository.findAll());
	}
	
		
	@Transactional
	public boolean deleteDonation(Integer donationId) {
		if (donationId == null) {
			throw new IllegalArgumentException("Donation ID invalid!");
		}
		boolean deleted = false; //not deleted yet
		Donation donation = donationRepository.findByDonationId(donationId);
		if (donation != null) {
			donationRepository.delete(donation);
			deleted = true;
		} else { throw new IllegalArgumentException("No donation found with Id!");}
		return deleted;
	}

	// PERSON

	@Transactional
	public Person createPerson(String username, String password) {
		if ((username == null || username.trim().length() == 0)
				&& (password == null || password.trim().length() == 0)) {
			throw new IllegalArgumentException("Person name and password cannot be empty!");
		}
		if (username == null || username.trim().length() == 0) {
			throw new IllegalArgumentException("Person name cannot be empty!");
		}
		if (password == null || password.trim().length() == 0) {
			throw new IllegalArgumentException("Person password cannot be empty!");
		}
		Person person = new Person();
		person.setUsername(username);
		person.setPassword(password);
		personRepository.save(person);
		return person;
	}
	
	


	@Transactional
	public Person getPerson(String username) {
		if (username == null || username.trim().length() == 0) {
			throw new IllegalArgumentException("Person name cannot be empty!");
		}

		Person person = personRepository.findByUsername(username);
		if(person == null) {
			throw new IllegalArgumentException("Person not found with username!");
		}
		return person;
	}

	@Transactional
	public List<Person> getAllPersons() {
		return toList(personRepository.findAll());
	}
	
	@Transactional
	public boolean deletePerson(String username) {
		if (username == null) {
			throw new IllegalArgumentException("Username invalid!");
		}
	
		boolean deleted = false; //not deleted yet
		Person person = personRepository.findByUsername(username);
		if (person != null) {
			personRepository.delete(person);
			deleted = true;
		} else { throw new IllegalArgumentException("Person not found with username!");}
		return deleted;
	}

	// PETPOST

	@Transactional
	public PetPost createPetPost(boolean availability, String name, String typeOfPet, String description, Person owner) {
		if ((name == null || name.trim().length() == 0)) {
			throw new IllegalArgumentException("Pet name cannot be empty!");
		}
		if (typeOfPet == null || typeOfPet.trim().length() == 0) {
			throw new IllegalArgumentException("Pet type cannot be empty!");
		}
		if (description == null || description.trim().length() == 0) {
			throw new IllegalArgumentException("Pet must have a description!");
		}

		PetPost petPost = new PetPost();
		petPost.setAvailability(availability);
		petPost.setName(name);
		petPost.setTypeOfPet(typeOfPet);
		petPost.setDescription(description);
		petPost.setOwnedBy(owner);
		petPostRepository.save(petPost);
		return petPost;
	}

	@Transactional
	public PetPost getPetPost(Integer id) {
		PetPost petPost = petPostRepository.findByPetPostId(id);
		if(petPost == null) {
			throw new IllegalArgumentException("No PetPost found with Id!");
		}
		return petPost;
	}

	@Transactional
	public List<PetPost> getAllPetPosts() {
		return toList(petPostRepository.findAll());
	}
	
	
	@Transactional
	public boolean deletePetPost(Integer petPostId) {
		if (petPostId == null) {
			throw new IllegalArgumentException("Pet Post ID invalid!");
		}
	
		boolean deleted = false; //not deleted yet
		PetPost petPost = petPostRepository.findByPetPostId(petPostId);
		if (petPost != null) {
			petPostRepository.delete(petPost);
			deleted = true;
		} else { throw new IllegalArgumentException("No PetPost found with Id!");}
		return deleted;
	}

	// QUESTION

	@Transactional
	public Question createQuestion(String ques) {
		if (ques == null || ques.trim().length() == 0) {
			throw new IllegalArgumentException("Question cannot be empty!");
		}
		Question question = new Question();
		question.setQuestion(ques);
		questionRepository.save(question);
		return question;
	}
	
	@Transactional
	public Question getQuestion(Integer id) {
		Question question = questionRepository.findByQuestionId(id);
		if(question == null) {
			throw new IllegalArgumentException("No question found with Id!");
		}
		return question;
	}

	@Transactional
	public List<Question> getAllQuestions() {
		return toList(questionRepository.findAll());
	}
	
	@Transactional
	public boolean deleteQuestion(Integer questionId) {
		if (questionId == null) {
			throw new IllegalArgumentException("Question ID invalid!");
		}
	
		boolean deleted = false; //not deleted yet
		Question question = questionRepository.findByQuestionId(questionId);
		if (question != null) {
			questionRepository.delete(question);
			deleted = true;
		} else { throw new IllegalArgumentException("No question found with Id!");}
		return deleted;
	}

	// USERPROFILE

	@Transactional
	public UserProfile createUserProfile(String address, boolean hasExperienceWithPets,
			Integer numberOfPetsCurrentlyOwned, String typeOfLivingAccommodation) {
		if ((numberOfPetsCurrentlyOwned > 0) && (hasExperienceWithPets == false)) {
			throw new IllegalArgumentException("Pet experience invalid! Cannot have no experience but own pets!");
		}
		if ((address == null || address.trim().length() == 0)) {
			throw new IllegalArgumentException("Address cannot be empty!");
		}
		if ((typeOfLivingAccommodation == null || typeOfLivingAccommodation.trim().length() == 0)) {
			throw new IllegalArgumentException("Living Accommodations cannot be empty!");
		}
		if (numberOfPetsCurrentlyOwned < 0) {
			throw new IllegalArgumentException("Number of pets currently owned cannot be less than 0!");
		}
		UserProfile userProfile = new UserProfile();
		userProfile.setAddress(address);
		userProfile.setHasExperienceWithPets(hasExperienceWithPets);
		userProfile.setNumberOfPetsCurrentlyOwned(numberOfPetsCurrentlyOwned);
		userProfile.setTypeOfLivingAccomodation(typeOfLivingAccommodation);
		userProfileRepository.save(userProfile);
		return userProfile;
	}

	@Transactional
	public UserProfile getUserProfile(Integer id) {
		UserProfile userProfile = userProfileRepository.findByUserProfileId(id);
		if(userProfile == null) {
			throw new IllegalArgumentException("No UserProfile found with Id!");
		}
		return userProfile;
	}

	@Transactional
	public List<UserProfile> getAllUserProfiles() {
		return toList(userProfileRepository.findAll());
	}
	
	@Transactional
	public boolean deleteUserProfile(Integer userProfileId) {
		if (userProfileId == null) {
			throw new IllegalArgumentException("User Profile ID invalid!");
		}
	
		boolean deleted = false; //not deleted yet
		UserProfile userProfile = userProfileRepository.findByUserProfileId(userProfileId);
		if (userProfile != null) {
			userProfileRepository.delete(userProfile);
			deleted = true;
		} else { throw new IllegalArgumentException("No UserProfile found with Id!");}
		return deleted;
	}
	
	
	

	private <T> List<T> toList(Iterable<T> iterable) {
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}
	
	//LOGIN AND LOGOUT
	
	//login for appusers
	@Transactional
	public Person loginAsAppUser(String username, String password) {
		
		if(username == null || username.trim().length() == 0) {
			throw new IllegalArgumentException("Username cannot be empty.");
		}
		if(password == null || password.trim().length() == 0) {
			throw new IllegalArgumentException("Password cannot be empty.");
		}

		List<AppUser> appUsers = getAllAppUsers();

		AppUser foundAppUser = null;
		for(AppUser appUser : appUsers) {
			if(appUser.getUsername().equals(username) && appUser.getPassword().equals(password)) {
				loggedInUser = appUser;
				foundAppUser = appUser;
				break;
			}
		}

		if(foundAppUser == null) {
			throw new IllegalArgumentException("This user account could not be found.");
		}

		return foundAppUser;
		
	}
	
	//login for appadmins
	@Transactional
	public Person loginAsAppAdmin(String username, String password) {
		
		if(username == null || username.trim().length() == 0) {
			throw new IllegalArgumentException("Username cannot be empty.");
		}
		if(password == null || password.trim().length() == 0) {
			throw new IllegalArgumentException("Password cannot be empty.");
		}

		AppAdmin appAdmin = this.getAppAdmin(username);

		AppAdmin foundAppAdmin = null;
		if(appAdmin.getUsername().equals(username) && appAdmin.getPassword().equals(password)) {
			loggedInUser = appAdmin;
			foundAppAdmin = appAdmin;
		}

		if(foundAppAdmin == null) {
			throw new IllegalArgumentException("This admin account could not be found.");
		}

		return foundAppAdmin;
		
	}
	
	@Transactional
	public void logout() {
		loggedInUser = null;
		
	}
	
}

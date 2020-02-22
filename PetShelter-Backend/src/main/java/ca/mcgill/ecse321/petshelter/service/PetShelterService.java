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

	// ADOPTREQUEST
	@Transactional
	public AdoptRequest createAdoptRequest(Person owner, PetPost petPost) {
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
		return adoptRequest;
	}

	@Transactional
	public List<AdoptRequest> getAllAdoptRequests() {
		return toList(adoptRequestRepository.findAll());
	}

	// APPADMIN
	@Transactional
	public AppAdmin createAppAdmin(String username, String password) {
		if (this.getAllAppAdmins().size() == 1) {
			throw new IllegalArgumentException("Can only have one Admin!");
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
		return appAdmin;
	}

	@Transactional
	public List<AppAdmin> getAllAppAdmins() {
		return toList(appAdminRepository.findAll());
	}

	// APPUSER
	@Transactional
	public AppUser createAppUser(String username, String password, PersonRole personRole) {

		if (username == null || username.trim().length() == 0) {
			throw new IllegalArgumentException("AppUser name cannot be empty!");
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
			throw new IllegalArgumentException("AppUser name cannot be empty!");
		}
		AppUser appUser = appUserRepository.findByUsername(username);
		return appUser;
	}

	@Transactional
	public List<AppUser> getAllAppUsers() {
		return toList(appUserRepository.findAll());
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
		return donation;
	}

	@Transactional
	public List<Donation> getAllDonations() {
		return toList(donationRepository.findAll());
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
		return person;
	}

	@Transactional
	public List<Person> getAllPersons() {
		return toList(personRepository.findAll());
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
		return petPost;
	}

	@Transactional
	public List<PetPost> getAllPetPosts() {
		return toList(petPostRepository.findAll());
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
		return question;
	}

	@Transactional
	public List<Question> getAllQuestions() {
		return toList(questionRepository.findAll());
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
		return userProfile;
	}

	@Transactional
	public List<UserProfile> getAllUserProfiles() {
		return toList(userProfileRepository.findAll());
	}

	private <T> List<T> toList(Iterable<T> iterable) {
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}

}

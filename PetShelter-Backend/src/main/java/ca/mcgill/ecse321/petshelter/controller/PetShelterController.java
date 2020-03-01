package ca.mcgill.ecse321.petshelter.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ca.mcgill.ecse321.petshelter.dto.PersonDto;
import ca.mcgill.ecse321.petshelter.model.AppAdmin;
import ca.mcgill.ecse321.petshelter.model.AppUser;
import ca.mcgill.ecse321.petshelter.model.Person;
import ca.mcgill.ecse321.petshelter.model.PersonRole;
import ca.mcgill.ecse321.petshelter.dto.AdoptRequestDto;
import ca.mcgill.ecse321.petshelter.dto.AppUserDto;
import ca.mcgill.ecse321.petshelter.dto.DonationDto;
import ca.mcgill.ecse321.petshelter.dto.PetPostDto;
import ca.mcgill.ecse321.petshelter.dto.QuestionDto;
import ca.mcgill.ecse321.petshelter.dto.UserProfileDto;
import ca.mcgill.ecse321.petshelter.model.AdoptRequest;
import ca.mcgill.ecse321.petshelter.model.Donation;
import ca.mcgill.ecse321.petshelter.model.PetPost;
import ca.mcgill.ecse321.petshelter.model.Question;
import ca.mcgill.ecse321.petshelter.model.UserProfile;
import ca.mcgill.ecse321.petshelter.service.PetShelterService;

@CrossOrigin(origins = "*")
@RestController
public class PetShelterController {
	
	@Autowired
	private PetShelterService service;
	
	// QUESTION //
	
	//get all questions
	@GetMapping(value = {"/questions", "/questions/"})
	public List<QuestionDto> getAllQuestions(){
		return service.getAllQuestions().stream().map(q -> convertToDto(q)).collect(Collectors.toList());
	}
	
	//create new question
	@PostMapping(value = {"/questions/{question}", "/questions/{question}/"})
	public QuestionDto createQuestionDto(@PathVariable("question") String ques) 
			throws IllegalArgumentException {
		Question question = service.createQuestion(ques);
		return convertToDto(question);
	} 
	
	//delete question by id
	@DeleteMapping(value = {"/questions/{questionId}", "/questions/{questionId}"})
	public List<QuestionDto> deleteQuestionById(@PathVariable("questionId") Integer questionId) {
		service.deleteQuestion(questionId);

		List<QuestionDto> questions = new ArrayList<QuestionDto>();
		for(Question question : service.getAllQuestions()) {
			questions.add(convertToDto(question));
		}

		return questions;
	}
	
	//convert question to questiondto
	private QuestionDto convertToDto(Question q) {
		if (q == null) {
			throw new IllegalArgumentException("There is no such Question!");
		}
		
		Set<PetPostDto> setPetPostDtos = createSetPetPostDto(q.getIsRelatedTo());
		Set<PersonDto> setPersonDtos = createSetPersonDto(q.getPerson());

		QuestionDto questionDto = new QuestionDto(q.getQuestion(), q.getAnswer(),
				setPetPostDtos, setPersonDtos, q.getQuestionId());
				
		return questionDto;
	}
	
	// USER PROFILE //
	
	@GetMapping(value = {"/userprofile", "/userprofile/"})
	public List<UserProfileDto> getAllUserProfiles(){
		return service.getAllUserProfiles().stream().map(u -> convertToDto(u)).collect(Collectors.toList());
	}
	
	@PostMapping(value = {"/userprofile/{username}", "/userprofile/{username}/"})
	public UserProfileDto createUserProfileDto(@PathVariable("username") String username,
			@RequestParam() String address,
			@RequestParam() Boolean hasExperienceWithPets,
			@RequestParam() int numberOfPetsCurrentlyOwned,
			@RequestParam() String typeOfLivingAccommodation)
			throws IllegalArgumentException {

		UserProfile userProfile = service.createUserProfile(address, hasExperienceWithPets, numberOfPetsCurrentlyOwned, typeOfLivingAccommodation);
		userProfile.setPerson(service.getPerson(username));
		return convertToDto(userProfile);
	} 
	
	private UserProfileDto convertToDto(UserProfile u) {
		if (u == null) {
			throw new IllegalArgumentException("There is no such user profile!");
		}
		PersonDto person = convertToDto(u.getPerson());

		UserProfileDto UserProfileDto = new UserProfileDto(u.getAddress(), u.getUserProfileId(),
				person, u.getHasExperienceWithPets(), u.getNumberOfPetsCurrentlyOwned(),
				u.getTypeOfLivingAccomodation());
		return UserProfileDto;
	}	
	
	@DeleteMapping(value = {"/userprofile", "/userprofile/"})
	public boolean deleteUserProfile(@PathVariable(name = "userProfileId") Integer userProfileId){
		if (userProfileId == null) {
			throw new IllegalArgumentException("There is no such user profile Id!");
		}
		else {
			return service.deleteUserProfile(userProfileId);
		}
	}
	
//	@GetMapping(value = {"/userprofile, /userprofile/"})
//	public UserProfileDto getUserProfile(@PathVariable(name = "userProfile") Integer userProfileId){
//		if (userProfileId == null) {
//			throw new IllegalArgumentException("There is no such user profile Id!");
//		} 
//		else {
//			UserProfile userProfile = service.getUserProfile(userProfileId);
//			if (userProfile != null) {
//				return convertToDto(userProfile);
//			} 
//			else { 
//				throw new IllegalArgumentException("No user profile found with Id!");
//			}
//		}
//	}
	
	// APP USER //
	
	@GetMapping(value = {"/appuser", "/appuser/"})
	public List<AppUserDto> getAllAppUser(){
		return service.getAllAppUsers().stream().map(a -> convertToDto(a)).collect(Collectors.toList());
	}
	
	@PostMapping(value = {"/appuser/{username}", "/appuser/{username}/"})
	public AppUserDto createAppUserDto(@PathVariable("username") String username,
			@RequestParam String password,
			@RequestParam PersonRole appUserRole) 
			throws IllegalArgumentException {
		AppUser appUser = service.createAppUser(username, password, appUserRole);
		return convertToDto(appUser);
	} 
	
	private AppUserDto convertToDto(AppUser a) {
		if (a == null) {
			throw new IllegalArgumentException("There is no such app user!");
		}
		AppUserDto appUserDto = new AppUserDto(a.getUsername(), a.getPassword(), a.getAppUserRole());
		return appUserDto;
	}
	
	// LOGIN AND LOGOUT //
	
	//appUser login
	@PostMapping(value = {"/loginuser/{personusername}", "/loginuser/{personusername}/"})
	public String appUserLogin(@PathVariable("personusername") String personUsername,
			@RequestParam String password) {
		try{
			service.loginAsAppUser(personUsername, password);
			return "Login Successful!";
		}catch(IllegalArgumentException e) {
			return e.getMessage();
		}
		
	}
	
	//appAdmin login
	@PostMapping(value = {"/loginadmin/{personusername}", "/loginadmin/{personusername}/"})
	public String appAdminLogin(@PathVariable("personusername") String personUsername,
			@RequestParam String password) {
		try{
			service.loginAsAppAdmin(personUsername, password);
			return "Login Successful!";
		}catch(IllegalArgumentException e) {
			return e.getMessage();
		}
		
	}
	
	//logout
	@PutMapping(value = {"/logout", "/logout/"})
	public String logout() {
			service.logout();
			return "Logout Successful!";
	}
	
	@GetMapping(value = {"/user", "/user/"})
	public PersonDto getLoggedUser() {
		return convertToDto((Person)service.getLoggedInUser());
	}
	
	// APP ADMIN //
	
	
	@PostMapping(value = {"/appadmin/{adminUsername}", "/appadmin/{adminUsername}/"})
	public PersonDto registerAppAdmin(@PathVariable("adminUsername") String adminUsername,
			@RequestParam String password){

		AppAdmin appAdmin = service.createAppAdmin(adminUsername, password);

		return convertToDto(appAdmin);
	}
	

	// DONATION //
	
	//get all donations
	@GetMapping(value = { "/donations", "/donations/" })
	public List<DonationDto> getAllDonations() {
		return service.getAllDonations().stream().map(d ->
		convertToDto(d)).collect(Collectors.toList());
	}

	//create new donation
	@PostMapping(value = { "/donations", "/donations/" })
	public DonationDto createDonationDto(
			@RequestParam("amount") double amount,
			@RequestParam("comment") String comment,
			@RequestParam("setNameAnonymous") boolean setNameAnonymous,
			@RequestParam("donationId") Integer donationId) 
			throws IllegalArgumentException {
		Donation donation = service.createDonation(amount, comment, setNameAnonymous);
		return convertToDto(donation);
		
	}
	
	//get donation by id
	@GetMapping(value = { "/donations/{donationId}", "/donations/{donationId}/" })
	public DonationDto getDonation(@PathVariable(name = "donationId") Integer donationId){
		if (donationId == null) {
			throw new IllegalArgumentException("There is no such Donation Id!");
		} 
		else {
			Donation donation = service.getDonation(donationId);
			if (donation != null) {
				return convertToDto(donation);
			} 
			else { 
				throw new IllegalArgumentException("No donation found with Id!");
			}
		}
	}
	
	//delete donation by id
	@DeleteMapping(value = { "/donations/{donationId}", "/donations/{donationId}/" })
	public boolean deleteDonation(@PathVariable(name = "donationId") Integer donationId){
		if (donationId == null) {
			
			throw new IllegalArgumentException("There is no such Donation Id!");
		}
		else {
			return service.deleteDonation(donationId);
		}
	}

	//convert donation to donationdto	
	private DonationDto convertToDto(Donation d) {
		if (d == null) {
			throw new IllegalArgumentException("There is no such Donation!");
		}
	
		DonationDto donationDto = new DonationDto(d.getAmount(), d.getComment(),
				false, createSetPersonDto(d.getPerson()), d.getDonationId());
		return donationDto;
	}
	
	private PersonDto convertToDto(Person p) {
		if (p == null) {
			throw new IllegalArgumentException("There is no such Person!");
		}
	
		PersonDto personDto = new PersonDto(p.getUsername(),
				p.getPassword(), p.getCreates());
		return personDto;
	}
	
	private Set<PersonDto> createSetPersonDto(Set<Person> p) {
		if (p == null) {
			throw new IllegalArgumentException("There is no such Person!");
		}
		
		Set<PersonDto> personDtoSet = new HashSet<PersonDto>();
		for (Person person : p) {
			PersonDto personDto = new PersonDto(person.getUsername(),
					person.getPassword(), person.getCreates());
			personDtoSet.add(personDto);
		}
		return personDtoSet;
	}
	
	// PET POST //
	
	//get list of pet posts
	@GetMapping(value = { "/petpost", "/petpost/" })
	public List<PetPostDto> getAllPetPosts() {
		return service.getAllPetPosts().stream().map(pp ->
		convertToDto(pp)).collect(Collectors.toList());
	}

	//create a new pet post
	@PostMapping(value = { "/petposts", "/petposts/" })
	public PetPostDto createPetPostDto(
			@RequestParam("availability") boolean availability,
			@RequestParam("ownedBy") String username,
			@RequestParam("name") String petname,
			@RequestParam("typeOfPet") String typeOfPet,
			@RequestParam("description") String description)
			throws IllegalArgumentException {
		Person owner = service.getAppUser(username);
		if(owner == null) {
			owner = service.getAppAdmin(username);
			if(owner == null) {
			throw new IllegalArgumentException("There is no such owner in the database.");
			}
		}
		PetPost petPost = service.createPetPost(availability, petname, typeOfPet, description, owner);
		return convertToDto(petPost);
	}
	
	//get pet post by id
	@GetMapping(value = { "/petposts/{petPostId}", "/petposts/{petPostId}/" })
	public PetPostDto getPetPost(@PathVariable(name = "petPostId") Integer petPostId){
		
		if (petPostId == null) {
			
			throw new IllegalArgumentException("There is no such Pet Post Id!");
		} 
		else {
			
			PetPost petPost = service.getPetPost(petPostId);
			
			if (petPost != null) {
				return convertToDto(petPost);
			} 
			else { 
				throw new IllegalArgumentException("No pet post was found with Id!");
			}
		}
	}
	
	//delete pet post by id
	@DeleteMapping(value = { "/petposts/{petPostId}", "/petposts/{petPostId}/" })
	public boolean deletePetPost(@PathVariable(name = "petPostId") Integer petPostId){
		if (petPostId == null) {
			
			throw new IllegalArgumentException("There is no such Pet Post Id!");
		}
		else {
			return service.deletePetPost(petPostId);
		}
	}
	
	private PetPostDto convertToDto(PetPost pp) {
		if (pp == null) {
			throw new IllegalArgumentException("There is no such Pet Post!");
		}
		
		PersonDto owner = convertToDto(pp.getOwnedBy());
		Set<AdoptRequestDto> requests = createSetAdoptRequestDto(pp.getHasRequest());
		QuestionDto question = convertToDto(pp.getRelatesTo());
	
		PetPostDto petPostDto = new PetPostDto(pp.isAvailability(),
				owner, requests, pp.getName(), pp.getTypeOfPet(), pp.getDescription(),
				question, pp.getPetPostId());
				
		return petPostDto;
	}
	
	private Set<PetPostDto> createSetPetPostDto(Set<PetPost> pps) {
		if (pps == null) {
			throw new IllegalArgumentException("There is no such Pet Post!");
		}
		
		Set<PetPostDto> petPostDtoSet = new HashSet<PetPostDto>();
		for (PetPost petPost : pps) {
			PetPostDto petPostDto = convertToDto(petPost);
			petPostDtoSet.add(petPostDto);
		}
		return petPostDtoSet;
	}
	
	// ADOPT REQUEST //
	
	//get all adopt requests
	@GetMapping(value = { "/adoptrequest", "/adoptrequest/" })
	public List<AdoptRequestDto> getAllAdoptRequest() {
		return service.getAllAdoptRequests().stream().map(ar ->
		convertToDto(ar)).collect(Collectors.toList());
	}
	
	//create adopt request by pet post
	@PostMapping(value = { "/adoptrequest/{petPostId}", "/adoptrequest/{petPostId}/" })
	public AdoptRequestDto createAdoptRequestDto(@PathVariable("petPostId") Integer petPostId)
			throws IllegalArgumentException {
		PetPost petPost = service.getPetPost(petPostId);
		if(petPost == null) {
			throw new IllegalArgumentException("There is no such pet post in the database.");
		}
		Person owner = service.getAppUser(petPost.getName());
		AdoptRequest adoptRequest = service.createAdoptRequest(owner, petPost);
		return convertToDto(adoptRequest);
	}
	
//	@GetMapping(value = { "/adoptRequest/", "/adoptRequest/" })
//	public AdoptRequestDto getAdoptRequest(@PathVariable(
//			name = "adoptRequestId") Integer adoptRequestId){
//		
//		if (adoptRequestId == null) {
//			
//			throw new IllegalArgumentException("There is no such Adopt Request Id!");
//		} 
//		else {
//			
//			AdoptRequest adoptRequest = service.getAdoptRequest(adoptRequestId);
//			
//			if (adoptRequest != null) {
//				return convertToDto(adoptRequest);
//			} 
//			else { 
//				throw new IllegalArgumentException("No AdoptRequest was found with Id!");
//			}
//		}
//	}
	
	//delete adopt request by id
	@DeleteMapping(value = { "/adoptrequest/{adoptRequestId}", "/adoptrequest/{adoptRequestId}/" })
	public boolean deleteAdoptRequest(@PathVariable(name = "adoptRequestId") Integer adoptRequestId){
		if (adoptRequestId == null) {
			
			throw new IllegalArgumentException("There is no such Adopt Request Id!");
		}
		else {
			return service.deleteAdoptRequest(adoptRequestId);
		}
	}
	
	//convert adoptrequest to adoptrequestdto
	private AdoptRequestDto convertToDto(AdoptRequest ar) {
		if (ar == null) {
			throw new IllegalArgumentException("There is no such Adopt Request!");
		}
	
		PersonDto personDto = convertToDto(ar.getRequestedBy());
		PetPostDto petPostDto = convertToDto(ar.getRequesting());
		AdoptRequestDto adoptRequestDto = new AdoptRequestDto(ar.getStatus(),
				personDto, petPostDto, ar.getAdoptRequestId());
				
		return adoptRequestDto;
	}
	
	private Set<AdoptRequestDto> createSetAdoptRequestDto(Set<AdoptRequest> ars) {
		if (ars == null) {
			throw new IllegalArgumentException("There is no such Adopt Request!");
		}
		
		Set<AdoptRequestDto> adoptRequestDtoSet = new HashSet<AdoptRequestDto>();
		for (AdoptRequest adoptRequest : ars) {			
			AdoptRequestDto adoptRequestDto = convertToDto(adoptRequest);
			adoptRequestDtoSet.add(adoptRequestDto);
		}
		return adoptRequestDtoSet;
	}
}

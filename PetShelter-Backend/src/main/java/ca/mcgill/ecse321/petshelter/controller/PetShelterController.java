package ca.mcgill.ecse321.petshelter.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.petshelter.dto.AdoptRequestDto;
import ca.mcgill.ecse321.petshelter.dto.DonationDto;
import ca.mcgill.ecse321.petshelter.dto.PersonDto;
import ca.mcgill.ecse321.petshelter.dto.PetPostDto;
import ca.mcgill.ecse321.petshelter.dto.QuestionDto;
import ca.mcgill.ecse321.petshelter.model.AdoptRequest;
import ca.mcgill.ecse321.petshelter.model.Donation;
import ca.mcgill.ecse321.petshelter.model.Person;
import ca.mcgill.ecse321.petshelter.model.PetPost;
import ca.mcgill.ecse321.petshelter.model.Question;
import ca.mcgill.ecse321.petshelter.service.PetShelterService;

@CrossOrigin(origins = "*")
@RestController
public class PetShelterController {
	
	@Autowired
	private PetShelterService service;
	
	// DONATION //
	
	@GetMapping(value = { "/donations", "/donations/" })
	public List<DonationDto> getAllDonations() {
		return service.getAllDonations().stream().map(d ->
		convertToDto(d)).collect(Collectors.toList());
	}

	@PostMapping(value = { "/donations/", "/donations/" })
	public DonationDto createDonationDto(@PathVariable("amount") double amount,
			@RequestParam("comment") String comment,
			@RequestParam("setNameAnonymous") boolean setNameAnonymous,
			@RequestParam("donationId") Integer donationId) 
			throws IllegalArgumentException {
		Donation donation = service.createDonation(amount, comment, setNameAnonymous);
		return convertToDto(donation);
	}
	
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
	
	@GetMapping(value = { "/petpost/", "/petpost/" })
	public List<PetPostDto> getAllPetPosts() {
		return service.getAllPetPosts().stream().map(pp ->
		convertToDto(pp)).collect(Collectors.toList());
	}

	@PostMapping(value = { "/petposts/", "/petposts/" })
	public PetPostDto createPetPostDto(@PathVariable("availability") boolean availability,
			@RequestParam("ownedBy") Person owner,
			@RequestParam("name") String name,
			@RequestParam("typeOfPet") String typeOfPet,
			@RequestParam("description") String description)
			throws IllegalArgumentException {
		PetPost petPost = service.createPetPost(availability, name, typeOfPet, description, owner);
		return convertToDto(petPost);
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
	
	// ADOPT REQUEST //
		
	@GetMapping(value = { "/adoptRequest/", "/adoptRequest/" })
	public List<AdoptRequestDto> getAllAdoptRequest() {
		return service.getAllAdoptRequests().stream().map(ar ->
		convertToDto(ar)).collect(Collectors.toList());
	}
	
	@PostMapping(value = { "/adoptRequest/", "/adoptRequest/" })
	public AdoptRequestDto createAdoptRequestDto(@PathVariable("owner") Person owner,
			@RequestParam("petPost") PetPost petPost)
			throws IllegalArgumentException {
		AdoptRequest adoptRequest = service.createAdoptRequest(owner, petPost);
		return convertToDto(adoptRequest);
	}
	
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
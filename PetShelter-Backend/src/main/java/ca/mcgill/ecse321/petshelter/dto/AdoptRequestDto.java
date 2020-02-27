package ca.mcgill.ecse321.petshelter.dto;

import ca.mcgill.ecse321.petshelter.model.Status;

public class AdoptRequestDto {
	private Status status;
	private PersonDto requestedBy;
	private PetPostDto requesting;
	private Integer adoptRequestId;
	
	public AdoptRequestDto() {
		// to be initialized
	}
	
	public AdoptRequestDto(Status status, PersonDto requestedBy, PetPostDto requesting, Integer adoptRequestId) {
		this.status = status;
		this.requestedBy = requestedBy;
		this.requesting = requesting;
		this.adoptRequestId = adoptRequestId;
	}
	
	public Status getStatus() {
		return status;
	}
	
	public PersonDto getRequestedBy() {
		return requestedBy;
	}
	
	public PetPostDto getRequesting() {
		return requesting;
	}
	
	public Integer getAdoptRequestId() {
		return adoptRequestId;
	}
}

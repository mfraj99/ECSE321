package ca.mcgill.ecse321.petshelter.model;


public class AdoptRequest {
   private Status status;
   
   public void setStatus(Status value) {
      this.status = value;
   }
   
   public Status getStatus() {
      return this.status;
   }
   
   /**
    * <pre>
    *           0..*     1..1
    * AdoptRequest ------------------------- Person
    *           fillsIn        &lt;       requestedBy
    * </pre>
    */
   private Person requestedBy;
   
   public void setRequestedBy(Person value) {
      this.requestedBy = value;
   }
   
   public Person getRequestedBy() {
      return this.requestedBy;
   }
   
   /**
    * <pre>
    *           0..*     1..1
    * AdoptRequest ------------------------- PetPost
    *           hasRequest        &gt;       requesting
    * </pre>
    */
   private PetPost requesting;
   
   public void setRequesting(PetPost value) {
      this.requesting = value;
   }
   
   public PetPost getRequesting() {
      return this.requesting;
   }
   
   }

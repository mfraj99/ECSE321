package ca.mcgill.ecse321.petshelter.dao;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.petshelter.model.Person;

@Repository
public class PetShelterRepository {

  @Autowired
  EntityManager entityManager;
  
  @Transactional
  public Person createPerson(String username, String password) {
      Person p = new Person();
      p.setUsername(username);
      p.setPassword(password);
      this.entityManager.persist(p);
      return p;
  }
  
  @Transactional
  public Person getPerson(String username) {
      Person p = this.entityManager.find(Person.class, username);
      return p;
  }
  
}

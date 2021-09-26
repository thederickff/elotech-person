package br.com.elotech.person.services;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.elotech.person.models.Person;
import br.com.elotech.person.repositories.PersonRepository;
import br.com.elotech.person.utilities.EmailUtility;
import br.com.elotech.person.utilities.SocialSecurityNumberUtility;

@Service
public class PersonService {
  
  private final PersonRepository repository;

  @Autowired
  public PersonService(PersonRepository repository)
  {
    this.repository = repository;
  }

  public Page<Person> findAll(String term, Pageable pageable)
  {
    if (term != null) {
      return repository.findAll(term, pageable);
    }

    return repository.findAll(pageable);
  }

  public Optional<Person> findById(Long id)
  {
    return repository.findById(id);
  }

  public void save(Person person)
  {
    if (person.getName() == null) {
      throw new RuntimeException("PersonNameRequired");
    }

    if (person.getSocialSecurityNumber() == null) {
      throw new RuntimeException("PersonSocialSecurityNumberRequired");
    }
    
    if (!SocialSecurityNumberUtility.isValid(person.getSocialSecurityNumber())) {
      throw new RuntimeException("PersonSocialSecurityNumberMustBeValid");
    }

    if (person.getDateOfBirth() == null) {
      throw new RuntimeException("PersonDateOfBirthRequired");
    }
    
    if (person.getDateOfBirth().isAfter(LocalDate.now())) {
      throw new RuntimeException("PersonDateOfBirthMustBeInThePast");
    }

    if (person.getContacts() == null) {
      throw new RuntimeException("PersonContactsRequired");
    }

    if (person.getContacts().size() < 1) {
      throw new RuntimeException("PersonContactsAtLeastOneRequired");
    }

    person.getContacts().forEach(contact -> {
      if (contact.getName() == null) {
        throw new RuntimeException("ContactNameRequired");
      }

      if (contact.getTelephone() == null) {
        throw new RuntimeException("ContactTelephoneRequired");
      }

      if (contact.getEmail() == null) {
        throw new RuntimeException("ContactEmailRequired");
      }
      
      if (!EmailUtility.isValid(contact.getEmail())) {
        throw new RuntimeException("ContactEmailInvalid");
      }
    });

    repository.save(person);
  }
}

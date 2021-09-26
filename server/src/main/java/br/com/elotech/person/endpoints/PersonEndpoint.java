package br.com.elotech.person.endpoints;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.elotech.person.models.Person;
import br.com.elotech.person.services.PersonService;
import br.com.elotech.person.utilities.ResponseUtility;
import br.com.elotech.person.utilities.ResponseUtility.MessageResponse;

@RestController
@RequestMapping("/api/persons")
public class PersonEndpoint {

  private final PersonService service;

  @Autowired
  public PersonEndpoint(PersonService service)
  {
    this.service = service;
  }

  @GetMapping
  public ResponseEntity<Page<Person>> findAll(@Param("term") String term, Pageable pageable)
  {
    return ResponseEntity.ok(service.findAll(term, pageable));
  }

  @GetMapping("{id}")
  public ResponseEntity<Optional<Person>> findById(@PathVariable("id") Long id)
  {
    return ResponseEntity.ok(service.findById(id));
  }

  @PostMapping
  public ResponseEntity<MessageResponse> store(@RequestBody Person person)
  {
    try {
      service.store(person);
      
      return ResponseUtility.ok();
    } catch (Exception e) {
      return ResponseUtility.exception(e);
    }
  }

  @PutMapping("{id}")
  public ResponseEntity<MessageResponse> update(@PathVariable("id") Long id, @RequestBody Person person)
  {
    try {
      service.update(id, person);
      return ResponseUtility.ok();
    } catch (Exception e) {
      return ResponseUtility.exception(e);
    }
  }

  @DeleteMapping("{id}")
  public ResponseEntity<MessageResponse> delete(@PathVariable("id") Long id)
  {
    try {
      service.delete(id);
      return ResponseUtility.ok();
    } catch (Exception e) {
      return ResponseUtility.exception(e);
    }
  }
}
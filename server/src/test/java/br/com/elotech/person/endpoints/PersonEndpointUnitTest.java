package br.com.elotech.person.endpoints;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;

import br.com.elotech.person.models.Contact;
import br.com.elotech.person.models.Person;
import br.com.elotech.person.services.PersonService;
import br.com.elotech.person.utilities.ResponseUtility.MessageResponse;

@ExtendWith(MockitoExtension.class)
public class PersonEndpointUnitTest {
  
  @InjectMocks
  PersonEndpoint endpoint;
  @Mock
  PersonService service;

  Person person1;
  Person person2;

  @BeforeEach
  void init()
  {
    List<Contact> contacts1 = Arrays.asList(
      new Contact(null, "Carl Krank", "85986469382", "carlkrank@mail.com", null)
    );

    List<Contact> contacts2 = Arrays.asList(
      new Contact(null, "Bob Odenkirk", "85987527584", "bobodenkirk@mail.com", null)
    );

    person1 = new Person(1l, "Bob Odenkirk", "81644673088", LocalDate.of(1998, 1, 23), contacts1);
    person2 = new Person(2l, "Carl Krank", "50023712058", LocalDate.of(1992, 4, 1), contacts2);
  }

  @Test
  void testFindAll()
  {
    when(
      service.findAll(null, null)
    ).thenReturn(
      new PageImpl<>(Arrays.asList(person1, person2))
    );

    ResponseEntity<Page<Person>> response = endpoint.findAll(null, null);
    verify(service).findAll(null, null);

    assertThat(response.getStatusCodeValue()).isEqualTo(200);
    assertThat(response.getBody().getContent().size()).isEqualTo(2);
    assertThat(response.getBody().getContent().get(0).getName()).isEqualTo(person1.getName());
    
    when(
      service.findAll("carl", null)
    ).thenReturn(
      new PageImpl<>(Arrays.asList(person2))
    );
    
    response = endpoint.findAll("carl", null);
    verify(service).findAll("carl", null);

    assertThat(response.getStatusCodeValue()).isEqualTo(200);
    assertThat(response.getBody().getContent().size()).isEqualTo(1);
    assertThat(response.getBody().getContent().get(0).getName()).isEqualTo(person2.getName());
  }

  @Test
  void testFindById()
  {
    when(
      service.findById(1l)
    ).thenReturn(
      Optional.of(person1)
    );

    ResponseEntity<Optional<Person>> response = endpoint.findById(1l);
    verify(service).findById(1l);
    assertThat(response.getStatusCodeValue()).isEqualTo(200);
    assertThat(response.getBody().get().getName()).isEqualTo(person1.getName());
    
    when(
      service.findById(2l)
    ).thenReturn(
      Optional.of(person2)
    );

    response = endpoint.findById(2l);
    verify(service).findById(2l);
    assertThat(response.getStatusCodeValue()).isEqualTo(200);
    assertThat(response.getBody().get().getName()).isEqualTo(person2.getName());
  }

  @Test
  void testStore()
  {
    ResponseEntity<MessageResponse> response = endpoint.store(person1);
    verify(service).store(person1);
    assertThat(response.getStatusCodeValue()).isEqualTo(200);
    assertThat(response.getBody().getMessage()).isEqualTo("Ok");
    
    when(endpoint.store(person2)).thenThrow(new RuntimeException("AnyKindOfError"));
    response = endpoint.store(person2);
    verify(service).store(person2);
    assertThat(response.getStatusCodeValue()).isEqualTo(400);
    assertThat(response.getBody().getMessage()).isEqualTo("AnyKindOfError");
  }

  @Test
  void testUpdate()
  {
    ResponseEntity<MessageResponse> response = endpoint.update(1l, person1);
    verify(service).update(1l, person1);
    assertThat(response.getStatusCodeValue()).isEqualTo(200);
    assertThat(response.getBody().getMessage()).isEqualTo("Ok");

    person2.setName(null);
    when(endpoint.update(2l, person2)).thenThrow(new RuntimeException("AnyKindOfError"));
    response = endpoint.update(2l, person2);
    verify(service).update(2l, person2);
    assertThat(response.getStatusCodeValue()).isEqualTo(400);
    assertThat(response.getBody().getMessage()).isEqualTo("AnyKindOfError");
  }
}

package br.com.elotech.person.endpoints;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
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

import br.com.elotech.person.models.Person;
import br.com.elotech.person.services.PersonService;

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
    person1 = new Person(1l, "Bob Odenkirk", "81644673088", LocalDate.of(1998, 1, 23), null);
    person2 = new Person(2l, "Carl Krank", "50023712058", LocalDate.of(1992, 4, 1), null);
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

    assertThat(response.getStatusCodeValue()).isEqualTo(200);
    assertThat(response.getBody().getContent().size()).isEqualTo(2);
    assertThat(response.getBody().getContent().get(0).getName()).isEqualTo(person1.getName());
    
    when(
      service.findAll("carl", null)
    ).thenReturn(
      new PageImpl<>(Arrays.asList(person2))
    );

    response = endpoint.findAll("carl", null);
    
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

    when(
      service.findById(2l)
    ).thenReturn(
      Optional.of(person2)
    );

    ResponseEntity<Optional<Person>> response = endpoint.findById(1l);
    
    assertThat(response.getStatusCodeValue()).isEqualTo(200);
    assertThat(response.getBody().get().getName()).isEqualTo(person1.getName());
    
    response = endpoint.findById(2l);
    assertThat(response.getStatusCodeValue()).isEqualTo(200);
    assertThat(response.getBody().get().getName()).isEqualTo(person2.getName());
  }
}

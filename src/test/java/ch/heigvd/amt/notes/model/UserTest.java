package ch.heigvd.amt.notes.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserTest {

  @Test
  void itShouldHaveAConstructor() {
    User user = User.builder()
      .username("oliechti")
      .firstName("olivier")
      .lastName("liechti")
      .email("olivier.liechti@heig-vd.ch")
      .hashedPassword("xxxx")
      .build();
    assertEquals("olivier", user.getFirstName());
    assertEquals("liechti", user.getLastName());
    assertEquals("olivier.liechti@heig-vd.ch", user.getEmail());
    assertEquals("xxxx", user.getHashedPassword());
  }
}
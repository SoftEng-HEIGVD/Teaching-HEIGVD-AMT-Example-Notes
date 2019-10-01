package ch.heigvd.amt.notes.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

  @Test
  void itShouldHaveAConstructor() {
    User user = new User("oliechti");
    user.setFirstName("olivier");
    user.setLastName("liechti");
    user.setEmail("olivier.liechti@heig-vd.ch");
    user.setHashedPassword("xxxx");
    assertEquals("olivier", user.getFirstName());
    assertEquals("liechti", user.getLastName());
    assertEquals("olivier.liechti@heig-vd.ch", user.getEmail());
    assertEquals("xxxx", user.getHashedPassword());
  }
}
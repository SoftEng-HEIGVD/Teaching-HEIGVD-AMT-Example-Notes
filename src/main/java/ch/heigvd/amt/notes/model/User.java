package ch.heigvd.amt.notes.model;

import lombok.Data;

@Data
public class User {
  private final String username;
  private String firstName;
  private String lastName;
  private String email;
  private String hashedPassword;
}

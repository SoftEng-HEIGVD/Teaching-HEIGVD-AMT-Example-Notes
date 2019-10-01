package ch.heigvd.amt.notes.model;

import lombok.Data;

@Data
public class Note {
  private String content;
  private final User author;
  private boolean isDeleted = false;
}

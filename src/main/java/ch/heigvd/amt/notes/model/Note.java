package ch.heigvd.amt.notes.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder(toBuilder = true)
@EqualsAndHashCode
@Getter
public class Note {
  private long id;
  private String content;
  private final User author;
  private boolean isDeleted = false;
}

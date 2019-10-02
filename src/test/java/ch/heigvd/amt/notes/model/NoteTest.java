package ch.heigvd.amt.notes.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class NoteTest {

  @Test
  public void itShouldBePossibleToBuildANote() {
    User author = User.builder().username("oliechti").build();
    Note note = Note.builder()
      .author(author)
      .content("hello world")
      .id(99)
      .isDeleted(false)
      .build();
    assertEquals("oliechti", note.getAuthor().getUsername());
    assertEquals("hello world", note.getContent());
    assertEquals(99, note.getId());
    assertFalse(note.isDeleted());
  }

  @Test
  public void itShouldBePossibleToCloneANote() {
    User author = User.builder().username("oliechti").build();
    Note note = Note.builder()
      .author(author)
      .content("hello world")
      .id(99)
      .isDeleted(false)
      .build();
    Note cloned = note.toBuilder().build();
    assertEquals(note, cloned);
    assertFalse(note == cloned);
  }

}
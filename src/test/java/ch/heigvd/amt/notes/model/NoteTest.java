package ch.heigvd.amt.notes.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NoteTest {

  @Test
  public void itShouldHaveAConstructor() {
    User author = new User("oliechti");
    Note note = new Note(author);
    note.setContent("hello world");
    assertEquals("oliechti", note.getAuthor().getUsername());
    assertEquals("hello world", note.getContent());
  }

}
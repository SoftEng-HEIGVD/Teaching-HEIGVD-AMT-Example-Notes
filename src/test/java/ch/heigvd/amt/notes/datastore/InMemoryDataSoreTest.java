package ch.heigvd.amt.notes.datastore;

import ch.heigvd.amt.notes.model.Note;
import ch.heigvd.amt.notes.model.User;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryDataSoreTest {

  @Test
  void itShouldBePossibleToStoreAndRetrieveUsers() {
    IInMemoryDatastore store = new InMemoryDataSore();

    List<User> users = store.getAllUsers();
    assertEquals(0, users.size());

    User olivier = User.builder().username("olivier").build();
    store.insertUser(olivier);
    assertEquals(1, store.getAllUsers().size());

    User sacha = User.builder().username("sacha").build();
    store.insertUser(sacha);
    assertEquals(2, store.getAllUsers().size());
  }

  @Test
  void getAllUsersShouldReturnObjectClones() throws DataNotFoundException {
    IInMemoryDatastore store = new InMemoryDataSore();
    User olivier = User.builder().username("olivier").build();
    store.insertUser(olivier);
    User olivierLoaded = store.loadUserByUsername("olivier");
    assertTrue(olivier != olivierLoaded);
    assertEquals(olivier, olivierLoaded);
  }

  @Test
  void itShouldBePossibleToUpdateAUser() throws DataNotFoundException {
    IInMemoryDatastore store = new InMemoryDataSore();
    User olivier = User.builder()
      .username("oliechti")
      .firstName("olivier")
      .build();
    store.insertUser(olivier);
    User updatedOlivier = olivier.toBuilder().firstName("christopher").build();
    store.updateUser(updatedOlivier);
    User loaded = store.loadUserByUsername("oliechti");
    assertEquals("christopher", loaded.getFirstName());
    assertEquals(updatedOlivier, loaded);
  }

  @Test
  void itShouldBePossibleToInsertAndRetrieveNotesForAUser() throws DataNotFoundException {
    IInMemoryDatastore store = new InMemoryDataSore();
    User olivier = User.builder().username("oliechti").build();
    store.insertUser(olivier);

    assertEquals(0, store.getNotesByAuthorUsername("oliechti", false).size());

    Note note1 = Note.builder().author(olivier).content("hello 1").build();
    store.insertNote(olivier.getUsername(), note1);
    List userNotes = store.getNotesByAuthorUsername("oliechti", false);
    assertEquals(1, userNotes.size());

    Note note2 = Note.builder().author(olivier).content("hello 2").build();
    store.insertNote(olivier.getUsername(), note2);
    assertEquals(2, store.getNotesByAuthorUsername("oliechti", false).size());
  }

  @Test
  void itShouldBePossibleToMarkANoteAsDeleted() throws DataNotFoundException {
    IInMemoryDatastore store = new InMemoryDataSore();
    User olivier = User.builder().username("oliechti").build();
    store.insertUser(olivier);
    Note note1 = Note.builder().author(olivier).content("hello 1").build();
    long noteId = store.insertNote(olivier.getUsername(), note1);

    Note loadedNote = store.getNoteById(noteId);
    assertFalse(loadedNote.isDeleted());
    assertFalse(store.getNotesByAuthorUsername("oliechti", InMemoryDataSore.INCLUDE_DELETED).get(0).isDeleted());
    store.deleteNote(noteId);
    loadedNote = store.getNoteById(noteId);
    assertTrue(loadedNote.isDeleted());
    assertTrue(store.getNotesByAuthorUsername("oliechti", InMemoryDataSore.INCLUDE_DELETED).get(0).isDeleted());
  }

  @Test
  void itShouldAllowMeToRetrieveDeletedAndUndeletedNotes() throws DataNotFoundException {
    IInMemoryDatastore store = new InMemoryDataSore();
    User olivier = User.builder().username("oliechti").build();
    store.insertUser(olivier);
    Note note1 = Note.builder().author(olivier).content("hello 1").build();
    long note1Id = store.insertNote(olivier.getUsername(), note1);
    Note note2 = Note.builder().author(olivier).content("hello 2").build();
    long note2Id = store.insertNote(olivier.getUsername(), note2);
    store.deleteNote(note1Id);

    assertEquals(2, store.getNotesByAuthorUsername("oliechti", InMemoryDataSore.INCLUDE_DELETED).size());
    assertEquals(1, store.getNotesByAuthorUsername("oliechti", !InMemoryDataSore.INCLUDE_DELETED).size());
  }

  @Test
  void itShouldThrowAnExceptionWhenFetchingInvalidNoteId() {
    IInMemoryDatastore store = new InMemoryDataSore();
    assertThrows(DataNotFoundException.class, () -> {
      store.getNoteById(99);
    });
  }

  @Test
  void itShouldThrowAnExceptionWhenFetchingInvalidUsername() {
    IInMemoryDatastore store = new InMemoryDataSore();
    assertThrows(DataNotFoundException.class, () -> {
      store.loadUserByUsername("doesnotexist");
    });
  }

  @Test
  void itShouldThrowAnExceptionWhenUpdatingMissingUser() {
    IInMemoryDatastore store = new InMemoryDataSore();
    assertThrows(DataNotFoundException.class, () -> {
      store.updateUser(User.builder().username("doesnotexist").build());
    });
  }

  @Test
  void itShouldThrowAnExceptionWhenLoadingNotesWithMissingUser() {
    IInMemoryDatastore store = new InMemoryDataSore();
    assertThrows(DataNotFoundException.class, () -> {
      store.getNotesByAuthorUsername("doesnotexist", InMemoryDataSore.INCLUDE_DELETED);
    });
  }

  @Test
  void itShouldThrowAnExceptionWhenInsertingNoteForMissingUser() {
    IInMemoryDatastore store = new InMemoryDataSore();
    assertThrows(DataNotFoundException.class, () -> {
      store.insertNote("doesnotexist", Note.builder().build());
    });
  }

  @Test
  void itShouldThrowAnExceptionWhenUpdatingNoteWithMissingUser() {
    IInMemoryDatastore store = new InMemoryDataSore();
    assertThrows(DataNotFoundException.class, () -> {
      User author = User.builder().username("doesnotexit").build();
      store.updateNote(Note.builder().author(author).build());
    });
  }

  @Test
  void itShouldThrowAnExceptionWhenDeletingNoteWithMissingId() {
    IInMemoryDatastore store = new InMemoryDataSore();
    assertThrows(DataNotFoundException.class, () -> {
      store.deleteNote(999);
    });
  }
}
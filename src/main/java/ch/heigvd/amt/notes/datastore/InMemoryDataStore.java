package ch.heigvd.amt.notes.datastore;

import ch.heigvd.amt.notes.datastore.exceptions.DuplicateKeyException;
import ch.heigvd.amt.notes.datastore.exceptions.KeyNotFoundException;
import ch.heigvd.amt.notes.model.Note;
import ch.heigvd.amt.notes.model.User;

import javax.ejb.Singleton;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Singleton
public class InMemoryDataStore implements IInMemoryDatastore {

  public static final boolean INCLUDE_DELETED = true;

  /*
   * We store users and notes in concurrent hashmaps, with values indexed
   * by unique ids (username for Users, id for Notes).
   *
   * In the methods, you will see that we clone objects (on the way in and out of the
   * data store). The reason is that we don't want a client to pass an object to the
   * data store (by reference), modify it on its side, and have an impact on the object
   * under the control of the data store. Since our Model objects are immutable, this
   * would not be strictly necessary.
   */
  ConcurrentHashMap<String, User> storeUsers = new ConcurrentHashMap<>();
  ConcurrentHashMap<Long, Note> storeNotes = new ConcurrentHashMap<>();

  /*
   * This variable is used to allocate unique IDs to notes
   */
  long lastNoteId = 0;

  @Override
  public List<User> getAllUsers() {
    return storeUsers
      .values()
      .stream()
      .map(user -> {
        User clone = user.toBuilder().build();
        return clone;
      }).collect(Collectors.toList());
  }

  @Override
  public void insertUser(User user) throws DuplicateKeyException {
    if (storeUsers.get(user.getUsername()) != null) {
      throw new DuplicateKeyException("User with username " + user.getUsername() + " already exists.");
    }
    User clone = user.toBuilder().build();
    storeUsers.put(user.getUsername(), clone);
  }

  @Override
  public User loadUserByUsername(String username) throws KeyNotFoundException {
    User user = storeUsers.get(username);
    if (user == null) {
      throw new KeyNotFoundException("Could not find user " + username);
    }
    User clone = user.toBuilder().build();
    return clone;
  }

  @Override
  public void updateUser(User user) throws KeyNotFoundException {
    User storedUser = storeUsers.get(user.getUsername());
    if (storedUser == null) {
      throw new KeyNotFoundException("Could not find user wit username " + user.getUsername());
    }
    User clone = user.toBuilder().build();
    storeUsers.put(user.getUsername(), clone);
  }

  @Override
  public Note getNoteById(long noteId) throws KeyNotFoundException {
    Note storedNote = storeNotes.get(noteId);
    if (storedNote == null) {
      throw new KeyNotFoundException("Could not find note wit id " + noteId);
    }
    return storedNote.toBuilder().build();
  }

  @Override
  public List<Note> getNotesByAuthorUsername(String username, boolean includeDeleted) throws KeyNotFoundException {
    if (storeUsers.get(username) == null) {
      throw new KeyNotFoundException("Could not find user " + username );
    }
    List<Note> userNotes = storeNotes.values()
      .stream()
      .filter(note -> note.getAuthor().getUsername().equals(username))
      .filter(note -> includeDeleted || !note.isDeleted())
      .collect(Collectors.toList());

    return userNotes
      .stream()
      .map(note -> note.toBuilder().build())
      .collect(Collectors.toList());
  }

  @Override
  public synchronized long insertNote(String username, Note note) throws KeyNotFoundException {
    if (storeUsers.get(username) == null) {
      throw new KeyNotFoundException("Could not find user " + username );
    }
    User authorStoredInDB = storeUsers.get(username).toBuilder().build();
    lastNoteId++;
    Note storedNote = note.toBuilder()
      .id(lastNoteId)
      .author(authorStoredInDB)
      .build();
    storeNotes.put(lastNoteId, storedNote);
    return lastNoteId;
  }

  @Override
  public void updateNote(Note note) throws KeyNotFoundException {
    Note storedNote = storeNotes.get(note.getId());
    if (storedNote == null) {
      throw new KeyNotFoundException("Could not find note with id " + note.getId());
    }
    storeNotes.put(note.getId(), note.toBuilder().build());
  }

  @Override
  public void deleteNote(long noteId) throws KeyNotFoundException {
    Note storedNote = storeNotes.get(noteId);
    if (storedNote == null) {
      throw new KeyNotFoundException("Could not find note with id " + noteId);
    }
    Note deletedNote = storedNote.toBuilder().isDeleted(true).build();
    updateNote(deletedNote);
  }
}

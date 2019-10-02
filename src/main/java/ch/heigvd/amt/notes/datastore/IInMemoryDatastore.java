package ch.heigvd.amt.notes.datastore;

import ch.heigvd.amt.notes.model.Note;
import ch.heigvd.amt.notes.model.User;

import java.util.List;

public interface IInMemoryDatastore {

  List<User> getAllUsers();

  void insertUser(User user);

  User loadUserByUsername(String username) throws DataNotFoundException;

  void updateUser(User user) throws DataNotFoundException;

  Note getNoteById(long noteId) throws DataNotFoundException;

  List<Note> getNotesByAuthorUsername(String username, boolean includeDeleted) throws DataNotFoundException;

  long insertNote(String username, Note note) throws DataNotFoundException;

  void updateNote(Note note) throws DataNotFoundException;

  void deleteNote(long noteId) throws DataNotFoundException;

}

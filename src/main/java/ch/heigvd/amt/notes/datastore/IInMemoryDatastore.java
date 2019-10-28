package ch.heigvd.amt.notes.datastore;

import ch.heigvd.amt.notes.datastore.exceptions.DuplicateKeyException;
import ch.heigvd.amt.notes.datastore.exceptions.KeyNotFoundException;
import ch.heigvd.amt.notes.model.Note;
import ch.heigvd.amt.notes.model.User;

import javax.ejb.Local;
import java.util.List;

@Local
public interface IInMemoryDatastore {

  List<User> getAllUsers();

  void insertUser(User user) throws DuplicateKeyException;

  User loadUserByUsername(String username) throws KeyNotFoundException;

  void updateUser(User user) throws KeyNotFoundException;

  Note getNoteById(long noteId) throws KeyNotFoundException;

  List<Note> getNotesByAuthorUsername(String username, boolean includeDeleted) throws KeyNotFoundException;

  long insertNote(String username, Note note) throws KeyNotFoundException;

  void updateNote(Note note) throws KeyNotFoundException;

  void deleteNote(long noteId) throws KeyNotFoundException;

}

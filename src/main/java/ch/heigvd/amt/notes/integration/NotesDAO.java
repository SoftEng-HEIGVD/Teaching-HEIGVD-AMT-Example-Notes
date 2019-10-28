package ch.heigvd.amt.notes.integration;

import ch.heigvd.amt.notes.datastore.IInMemoryDatastore;
import ch.heigvd.amt.notes.datastore.exceptions.DuplicateKeyException;
import ch.heigvd.amt.notes.model.Note;

import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class NotesDAO implements INotesDAO {

  @EJB
  IInMemoryDatastore datastore;

  @Override
  public Note create(Note entity) throws DuplicateKeyException {
    return null;
  }

  @Override
  public Note findById(String id) {
    return null;
  }

  @Override
  public void update(Note entity) {

  }

  @Override
  public void deleteById(String id) {

  }
}

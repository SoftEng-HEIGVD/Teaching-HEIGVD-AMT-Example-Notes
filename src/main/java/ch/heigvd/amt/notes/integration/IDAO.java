package ch.heigvd.amt.notes.integration;

import ch.heigvd.amt.notes.datastore.exceptions.DuplicateKeyException;
import ch.heigvd.amt.notes.datastore.exceptions.KeyNotFoundException;

import java.sql.SQLException;

public interface IDAO<PK, E> {
  E create(E entity) throws DuplicateKeyException;
  E findById(PK id) throws KeyNotFoundException;
  void update(E entity) throws KeyNotFoundException;
  void deleteById(PK id) throws KeyNotFoundException;
}

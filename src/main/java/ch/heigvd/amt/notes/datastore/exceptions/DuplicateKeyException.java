package ch.heigvd.amt.notes.datastore.exceptions;

import java.util.DuplicateFormatFlagsException;

public class DuplicateKeyException extends Exception {
  public DuplicateKeyException(String message) {
    super(message);
  }
}

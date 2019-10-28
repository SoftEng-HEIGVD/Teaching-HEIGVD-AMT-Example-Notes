package ch.heigvd.amt.notes.integration;

import ch.heigvd.amt.notes.model.Note;

import javax.ejb.Local;

@Local
public interface INotesDAO extends IDAO<String, Note> {

}

package ch.heigvd.amt.notes.integration;

import ch.heigvd.amt.notes.model.User;

import javax.ejb.Local;

@Local
public interface IUsersDAO extends IDAO<String, User> {

}

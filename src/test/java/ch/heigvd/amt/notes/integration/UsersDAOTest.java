package ch.heigvd.amt.notes.integration;

import ch.heigvd.amt.notes.datastore.exceptions.DuplicateKeyException;
import ch.heigvd.amt.notes.datastore.exceptions.KeyNotFoundException;
import ch.heigvd.amt.notes.model.User;
import org.arquillian.container.chameleon.deployment.api.DeploymentParameters;
import org.arquillian.container.chameleon.deployment.maven.MavenBuild;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import java.sql.SQLException;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
@MavenBuild
@DeploymentParameters(testable = true)
public class UsersDAOTest {

  @EJB
  IUsersDAO usersDao;

  /*
  @Deployment
  public static JavaArchive createDeployment() {
    return ShrinkWrap.create(JavaArchive.class, "test.jar")
      .addPackages(true, "ch.heigvd");
  }
  */

  @Test
  @Transactional(TransactionMode.COMMIT)
  public void itShouldBePossibleToCreateAUser() throws DuplicateKeyException, SQLException {
    User olivier = User.builder().username("oliechti_" + System.currentTimeMillis()).firstName("Olivier").lastName("Liechti").build();
    usersDao.create(olivier);
  }

    @Test
  @Transactional(TransactionMode.COMMIT)
  public void itShouldBePossibleToCreateAndRetrieveAUserViaTheUsersDAO() throws DuplicateKeyException, KeyNotFoundException {
    User olivier = User.builder().username("oliechti" + System.currentTimeMillis()).firstName("Olivier").lastName("Liechti").build();
    User olivierCreated = usersDao.create(olivier);
    User olivierLoaded = usersDao.findById(olivierCreated.getUsername());
    assertEquals(olivier, olivierCreated);
    assertEquals(olivier, olivierLoaded);
    assertSame(olivier, olivierCreated);
    assertNotSame(olivier, olivierLoaded);
  }

  @Test
  public void itShouldBePossibleToDeleteAUser() throws DuplicateKeyException, KeyNotFoundException {
    User olivier = User.builder().username("oliechti" + System.currentTimeMillis()).firstName("Olivier").lastName("Liechti").build();
    User olivierCreated = usersDao.create(olivier);
    User olivierLoaded = usersDao.findById(olivierCreated.getUsername());
    assertEquals(olivier, olivierCreated);
    usersDao.deleteById(olivierCreated.getUsername());
    boolean hasThrown = false;
    try {
      olivierLoaded = usersDao.findById(olivierCreated.getUsername());
    } catch (KeyNotFoundException e) {
      hasThrown = true;
    }
    assertTrue(hasThrown);
  }

  @Test
  public void itShouldBePossibleToUpdateAUser() throws DuplicateKeyException, KeyNotFoundException {
    User olivier = User.builder().username("oliechti" + System.currentTimeMillis()).firstName("Olivier").lastName("Liechti").build();
    User olivierCreated = usersDao.create(olivier);
    User olivierModified = olivier.toBuilder().firstName("john").lastName("doe").build();
    usersDao.update(olivierModified);
    User olivierModifiedInDB = usersDao.findById(olivier.getUsername());
    assertEquals(olivierModified, olivierModifiedInDB);
    assertNotEquals(olivierCreated, olivierModifiedInDB);

  }
}
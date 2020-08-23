package ru.stqa.pft.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.mantis.model.UserData;
import ru.stqa.pft.mantis.model.Users;

import javax.mail.MessagingException;
import java.io.IOException;

import static org.testng.Assert.assertTrue;

public class ResetPassword extends TestBase {

  @BeforeMethod
  public void startMailServer() {
    app.mail().start();
  }

  @Test
  public void resetPasswordTest() throws IOException, MessagingException {
    Users users = app.db().users();
    UserData admin = new UserData();
    for (UserData user : users) {
      if (user.getUsername().equals("administartor")) {
        admin = user;
      }
    }
    users.remove(admin);
    UserData user = users.iterator().next();
    String newPassword = "QQqq2222!";
    app.rsPass().login(app.getProperty("web.adminLogin"), app.getProperty("web.adminPassword"));
    app.rsPass().resetPassword(user.getUsername());
    app.rsPass().confirmResetPassword(user, newPassword);
    assertTrue(app.newSession().login(user.getUsername(), newPassword));
  }

  @AfterMethod(alwaysRun = true)
  public void stopMailServer() {
    app.mail().stop();
  }
}
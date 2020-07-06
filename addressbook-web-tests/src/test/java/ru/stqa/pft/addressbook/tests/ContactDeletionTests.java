package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.TestBase;

public class ContactDeletionTests extends TestBase {

  @Test
  public void testContactDeletion() throws Exception {
    app.getNavigationHelper().gotoHomePage();
    app.getContactHelper().selectContact();
    app.getContactHelper().deleteSelectedContacts();
    app.getContactHelper().confirmSelection();
    app.getNavigationHelper().gotoHomePage();
  }

}

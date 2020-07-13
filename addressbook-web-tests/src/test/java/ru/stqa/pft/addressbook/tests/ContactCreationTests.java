package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() throws Exception {
    app.getNavigationHelper().gotoContactPage();
    app.getContactHelper().fillContactForm(new ContactData("Test", "Testov", "88008008888", "test@test.ts"));
    app.getContactHelper().submitContactCreation();
    app.getContactHelper().returnToHomePage();
  }

}
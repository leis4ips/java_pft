package ru.stqa.pft.addressbook;

import org.testng.annotations.Test;

public class ContactCreationTests extends TestBase{

  @Test
  public void testContactCreation() throws Exception {
    gotoContactPage();
    fillContactForm(new ContactData("Test", "Testov", "88008008888", "test@test.ts"));
    submitContactCreation();
    returnToHomePage();
  }

}

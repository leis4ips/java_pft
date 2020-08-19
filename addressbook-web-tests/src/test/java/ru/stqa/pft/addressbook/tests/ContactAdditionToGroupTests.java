package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;
import ru.stqa.pft.addressbook.model.ContactData;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactAdditionToGroupTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    Contacts contacts = app.db().contacts();
    Contacts contactsWithoutGroup = new Contacts();
    if (app.db().groups().size() == 0) {
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("test 1").withHeader("test 2").withFooter("test 3"));
    }
    if (contacts.size() == 0) {
      app.goTo().homePage();
      app.contact().create(new ContactData().withLastname("Фиона").withAddress("Наседкина 3/8").withFirstname("Жукова")
              .withHomePhone("+79009009090").withEmail("test@test.com").inGroup(app.db().groups().iterator().next()));
    }
    for (ContactData contact : contacts) {
      if (contact.getGroups().size() == app.db().groups().size()) {
        contactsWithoutGroup.add(contact);
      }
    }
    contacts.removeAll(contactsWithoutGroup);
    if (contacts.size() == 0) {
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("test 1").withHeader("test 2").withFooter("test 3"));
    }
  }

  @Test
  public void additionToGroupTest() {
    app.goTo().homePage();
    Groups groups = app.db().groups();
    Contacts contacts = app.db().contacts();
    Contacts contactInAllGroups = new Contacts();
    for (ContactData contact : contacts) {
      if (contact.getGroups().size() == groups.size()) {
        contactInAllGroups.add(contact);
      }
    }
    contacts.removeAll(contactInAllGroups);
    ContactData contact = contacts.iterator().next();
    Groups before = contact.getGroups();
    groups.removeAll(contact.getGroups());
    GroupData additionGroup = groups.iterator().next();
    app.contact().addToGroup(contact, additionGroup);
    before.add(additionGroup);
    Contacts afterUpd = app.db().contacts();
    ContactData updContact = new ContactData();
    for (ContactData contactAfterUpd : afterUpd) {
      if (contactAfterUpd.getId() == contact.getId()) {
        updContact = contactAfterUpd;
      }
    }
    Groups after = updContact.getGroups();
    assertThat(after, equalTo(before));
  }
}
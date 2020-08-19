package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;
import ru.stqa.pft.addressbook.model.ContactData;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactDeletionFromGroupTests extends TestBase {
  @BeforeMethod
  public void ensurePreconditions() {
    Contacts contacts = app.db().contacts();
    Contacts contactsWithoutGroup = new Contacts();
    if (app.db().groups().size() == 0) {
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("test 1").withHeader("test 2").withFooter("test 3"));
    }
    if (app.db().contacts().size() == 0) {
      app.goTo().homePage();
      app.contact().create(new ContactData().withLastname("Фиона").withAddress("Наседкина 3/8").withFirstname("Жукова")
              .withHomePhone("+79009009090").withEmail("test@test.com").inGroup(app.db().groups().iterator().next()));
    }
    for (ContactData contact : contacts) {
      if (contact.getGroups().size() == 0) {
        contactsWithoutGroup.add(contact);
      }
    }
    contacts.removeAll(contactsWithoutGroup);
    if (contacts.size() == 0) {
      app.goTo().groupPage();
      app.contact().addToGroup(app.db().contacts().iterator().next(), app.db().groups().iterator().next());
    }
  }

  @Test
  public void contactDeletionFromGroupTest() {
    app.goTo().homePage();
    Contacts contacts = app.db().contacts();
    Contacts contactsWithoutGroup = new Contacts();
    for (ContactData contact : contacts) {
      if (contact.getGroups().size() == 0) {
        contactsWithoutGroup.add(contact);
      }
    }
    contacts.removeAll(contactsWithoutGroup);
    ContactData contact = contacts.iterator().next();
    Groups before = contact.getGroups();
    GroupData group = contact.getGroups().iterator().next();
    app.contact().deletionContactFromGroup(group, contact);
    before.remove(group);
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
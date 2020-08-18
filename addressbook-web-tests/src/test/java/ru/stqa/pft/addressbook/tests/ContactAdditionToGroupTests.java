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
    if (app.db().groups().size() == 0) {
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("test 1").withHeader("test 2").withFooter("test 3"));
    }
    if (contacts.size() == 0) {
      app.goTo().homePage();
      app.contact().create(new ContactData()
              .withLastname("Фиона")
              .withAddress("Наседкина 3/8")
              .withFirstname("Жукова")
              .withHomePhone("+79009009090")
              .withEmail("test@test.com")
              .inGroup(app.db().groups().iterator().next()));
    }
    for (ContactData contact : contacts) {
      if (contact.getGroups().size() == app.db().groups().size()) {
        contacts.remove(contact);
      }
    }
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
    for (ContactData contact : contacts) {
      if (contact.getGroups().size() == app.db().groups().size()) {
        contacts.remove(contact);
      }
    }
    ContactData contact = contacts.iterator().next();
    GroupData additionGroup = app.contact().groupsWithoutContact(contact, groups).iterator().next();

    Groups before = contact.getGroups();

    app.contact().addToGroup(contact, additionGroup);

    before.add(additionGroup);
    Groups after = contact.getGroups();
    assertThat(after, equalTo(before));
  }
}
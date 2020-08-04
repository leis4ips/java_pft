package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

  @DataProvider
  public Iterator<Object[]> validContacts() {
    List<Object[]> list = new ArrayList<Object[]>();
    list.add(new Object[] {new ContactData().withFirstname("Testa").withLastname("Testova").withMobilePhone("89-89-78").withEmail("testa@ts.ts")});
    list.add(new Object[] {new ContactData().withFirstname("Test").withLastname("Testov").withMobilePhone("555555").withEmail("test@test.ts")});
    list.add(new Object[] {new ContactData().withFirstname("Klara").withLastname("Wow").withMobilePhone("02").withEmail("klara@kl.kl")});
    return list.iterator();
  }

  @Test(dataProvider = "validContacts")
  public void testContactCreation(ContactData contact) throws Exception {
      File photo = new File("src/test/resources/IMG.jpg");
      Contacts before = app.contact().all();
      app.contact().create(contact);
      assertThat(app.contact().count(), equalTo(before.size() + 1));
      Contacts after = app.contact().all();
      assertThat(after, equalTo(
              before.withAdded(contact.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));
    }

  @Test(enabled = false)
  public void testBadContactCreation() throws Exception {
    Contacts before = app.contact().all();
    ContactData contact = new ContactData().withFirstname("Tanya'").withLastname("Testova").withMobilePhone("88006661122").withEmail("test666@test.test");
    app.contact().create(contact);
    assertThat(app.contact().count(), equalTo(before.size()));
    Contacts after = app.contact().all();
    assertThat(after, equalTo(before));
  }

}

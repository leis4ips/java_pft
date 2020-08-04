package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

  @DataProvider
  public Iterator<Object[]> validContacts() throws IOException {
    List<Object[]> list = new ArrayList<Object[]>();
    BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.csv")));
    String line = reader.readLine();
    while (line != null ) {
      String[] split = line.split(";");
      list.add(new Object[] {new ContactData().withFirstname(split[0]).withLastname(split[1]).withHomePhone(split[2])
              .withMobilePhone(split[3]).withEmail(split[4]).withAddress(split[5])});
      line = reader.readLine();
    }
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

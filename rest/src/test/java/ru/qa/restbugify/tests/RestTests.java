package ru.qa.restbugify.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.qa.restbugify.model.Issue;

import java.io.IOException;
import java.util.Set;

public class RestTests extends TestBase {

  @Test
  public void testCreateIssue() throws IOException {
    skipIfNotFixed(90);
    Set<Issue> oldIssues = app.rest().getIssuesById(90);
    Issue newIssue = new Issue().withSubject("First test REST").withDescription("New test issue");
    int issueId = app.rest().createIssue(newIssue);
    Set<Issue> newIssues = app.rest().getIssues();
    oldIssues.add(new Issue().withId(issueId));
    Assert.assertEquals(newIssues, oldIssues);
  }
}
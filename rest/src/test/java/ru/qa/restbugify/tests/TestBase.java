package ru.qa.restbugify.tests;

import org.testng.SkipException;
import org.testng.annotations.BeforeSuite;
import ru.qa.restbugify.appmanager.ApplicationManager;
import ru.qa.restbugify.model.Issue;

import java.io.IOException;
import java.util.Set;

public class TestBase {


  protected static final ApplicationManager app = new ApplicationManager();

  @BeforeSuite
  public void setUp() throws Exception {
    app.init();
  }

  public boolean isIssueOpen(int issueId) throws IOException {
    Set<Issue> issues = app.rest().getIssuesById(issueId);
    Issue issue = issues.iterator().next();
    return issue.getState_name().equals("Open")
            || issue.getState_name().equals("In Progress")
            || issue.getState_name().equals("Reopened");
  }

  public void skipIfNotFixed(int issueId) throws IOException {
    if (isIssueOpen(issueId)) {
      throw new SkipException("Ignored because of issue " + issueId);
    }
  }
}
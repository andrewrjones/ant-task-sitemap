package uk.co.arjones.ant.task;

import org.apache.tools.ant.BuildFileTest;

public class SitemapTest extends BuildFileTest {

    public SitemapTest(String s) {
        super(s);
    }

    public void setUp() {
        // initialize Ant
        configureProject("build.xml");
    }

    public void testWithoutFiles() {
        expectBuildException("use.without", "Fail requested.");
    }
}
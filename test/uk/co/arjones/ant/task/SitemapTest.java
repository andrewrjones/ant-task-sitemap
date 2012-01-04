package uk.co.arjones.ant.task;

import org.apache.tools.ant.BuildFileTest;
import com.redfin.sitemapgenerator.SitemapValidator;
import java.io.File;

public class SitemapTest extends BuildFileTest {

    public SitemapTest(String s) {
        super(s);
    }

    public void setUp() {
        // initialize Ant
        configureProject("build.xml");
    }
    
    public void testWithoutURL() {
        expectBuildException("use.without.url", "Fail requested.");
    }
    
    public void testWithoutDestDir() {
        expectBuildException("use.without.destdir", "Fail requested.");
    }

    public void testWithoutFiles() {
        expectBuildException("use.without.files", "Fail requested.");
    }
    
    public void testDefault() {
        executeTarget("use.default");

        // check sitemap is valid
        File sitemap = new File(getProjectDir()+"/test-resources/sitemap.xml");
        try {
            SitemapValidator.validateWebSitemap(sitemap);
        } catch( Exception e ){
            fail("exception thrown when validating sitemap");
        }
        
        sitemap.deleteOnExit();
    }
}
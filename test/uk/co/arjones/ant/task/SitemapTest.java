package uk.co.arjones.ant.task;

import org.apache.tools.ant.BuildFileTest;
import com.redfin.sitemapgenerator.SitemapValidator;
import java.io.File;

public class SitemapTest extends BuildFileTest {

    // the directory where we do our tests
    private String resourceDir = "/test-resources";

    public SitemapTest(String s) {
        super(s);
    }

    public void setUp() {
        // initialize Ant
        configureProject("build.xml");
    }
    
    public void tearDown() {
        // remove files
        getSitemapFile().delete();
        getSitemapGzipFile().delete();
    }
    
    public void testWithoutURL() {
        expectBuildException("use.without.url", "Fail requested.");
        
        // no sitemap
        assertFalse(sitemapExists());
        
        // no sitemap gzip
        assertFalse(sitemapGzipExists());
    }
    
    public void testWithoutDestDir() {
        expectBuildException("use.without.destdir", "Fail requested.");
        
        // no sitemap
        assertFalse(sitemapExists());
        
        // no sitemap gzip
        assertFalse(sitemapGzipExists());
    }

    public void testWithoutFiles() {
        expectBuildException("use.without.files", "Fail requested.");
        
        // no sitemap
        assertFalse(sitemapExists());
        
        // no sitemap gzip
        assertFalse(sitemapGzipExists());
    }
    
    public void testDefault() {
        // run target
        executeTarget("use.default");
        
        // check sitemap is valid
        File sitemap = getSitemapFile();
        assertTrue(sitemap.exists());
        try {
            SitemapValidator.validateWebSitemap(sitemap);
        } catch( Exception e ){
            fail("exception thrown when validating sitemap");
        }
        
        assertFalse(sitemapGzipExists());
    }
    
    public void testGzip() {
        executeTarget("use.gzip");

        // check sitemap is valid
        File sitemap = getSitemapFile();
        assertTrue(sitemap.exists());
        try {
            SitemapValidator.validateWebSitemap(sitemap);
        } catch( Exception e ){
            fail("exception thrown when validating sitemap");
        }
        
        assertTrue(sitemapGzipExists());
    }
    
    /**
     * Utility function to check if sitemap.xml exists.
     * @return true if it does exist, false if not.
     */
    private boolean sitemapExists(){
        return getSitemapFile().exists();
    }
    
    /**
     * Utility function to check if sitemap.xml.gz exists.
     * @return true if it does exist, false if not.
     */
    private boolean sitemapGzipExists(){
        return getSitemapGzipFile().exists();
    }
    
    /**
     * Returns the File for the sitemap.xml
     * @return new File object.
     */
    private File getSitemapFile(){
        return new File(
            getProjectDir() +
            this.resourceDir +
            "/sitemap.xml"
        );
    }
    
    /**
     * Returns the File for the sitemap.xml.gz
     * @return new File object.
     */
    private File getSitemapGzipFile(){
        return new File(
            getProjectDir() +
            this.resourceDir +
            "/sitemap.xml.gz"
        );
    }
}
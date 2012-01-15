package uk.co.arjones.ant.task;

import org.apache.tools.ant.Task;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.util.FileUtils;

import com.redfin.sitemapgenerator.WebSitemapGenerator;
import com.redfin.sitemapgenerator.WebSitemapUrl;

import java.util.Vector;
import java.io.File;
import java.util.Date;

public class Sitemap extends Task {
    
    private Vector<FileSet> filesets = new Vector<FileSet>();
    private File destdir;
    private String url;
    private String index = "index.*";
    private String lastmod;
    private boolean gzip = false;
    
    /**
     * Receives a nested fileset from the ant task
     * @param fileset The nested fileset to recieve.
     */
    public void addFileSet(FileSet fileset) {
        if (!filesets.contains(fileset)) {
            filesets.add(fileset);
        }
    }
    
    /**
    * Recieves the url attribute from the ant task
    * @param url
    */
    public void setUrl(String url) {
        this.url = url;
    }
    
    /**
    * Recieves the index attribute from the ant task
    * @param index
    */
    public void setIndex(String index) {
        this.index = index;
    }
    
    /**
    * Recieves the lastmod attribute from the ant task
    * @param lastmod
    */
    public void setLastmod(String lastmod) {
        this.lastmod = lastmod;
    }
    
    /**
     * Receives the destdir attribute from the ant task.
     * @param destdir
     */
    public void setDestdir (File destdir) {
        this.destdir = destdir;
    }
    
    /**
     * If true, also generate a gzip file
     * @param gzip
     */
    public void setGzip(boolean gzip) {
        this.gzip = gzip;
    }
    
    /**
     * Executes the task
     */
     public void execute() throws BuildException {
        if(this.url == null){
            throw new BuildException("You must specify the url");
        }
        
        if(this.destdir == null){
            throw new BuildException("You must specify the destdir");
        }
        
        if (filesets.size() == 0) {
            throw new BuildException("You must specify one or more fileset child elements");
        }
        
        try {
            WebSitemapGenerator wsg = new WebSitemapGenerator(this.url, this.destdir);
            WebSitemapGenerator wsgzip = null;
            if(this.gzip == true){
                wsgzip = WebSitemapGenerator.builder(this.url, this.destdir).gzip(true).build();
            }
            
            // Loop through fileset
            for (int i = 0; i < filesets.size(); i++) {
            
                // Get current fileset
                FileSet fs = filesets.elementAt(i);
                DirectoryScanner ds = fs.getDirectoryScanner(getProject());

                // Get base directory from fileset
                File dir = ds.getBasedir();

                // Get included files from fileset
                String[] srcs = ds.getIncludedFiles();

                // Loop through files
                for (int j = 0; j < srcs.length; j++) {

                    // Make file object from base directory and filename
                    File temp = new File(dir,srcs[j]);
                    
                    String path = this.url + "/" + temp.getName();
                    if(temp.getName().matches(this.index)){
                        path = this.url + "/";
                    }
                    
                    // create the url
                    WebSitemapUrl url;
                    if(this.lastmod != null){
                        if(this.lastmod.toLowerCase().equals("today")){
                            url = new WebSitemapUrl.Options(path).lastMod(new Date()).build();
                        } else if(this.lastmod.toLowerCase().equals("fromfile")){
                            url = new WebSitemapUrl.Options(path).lastMod(new Date(temp.lastModified())).build();
                        } else {
                            throw new BuildException("lastmod needs to be either 'today' or 'fromfile'");
                        }
                    } else {
                        url = new WebSitemapUrl.Options(path).build();
                    }

                    // add to sitemap
                    wsg.addUrl(url);
                    
                    // add to sitemap gzip
                    if(wsgzip != null){
                        wsgzip.addUrl(url);
                    }
                }
                wsg.write();
                
                if(wsgzip != null){
                    wsgzip.write();
                }
            }
        } catch(Exception e) {
            throw new BuildException(e);
        }            
    }
}
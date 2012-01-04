package uk.co.arjones.ant.task;

import org.apache.tools.ant.Task;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.types.FileSet;

import java.util.Vector;

public class Sitemap extends Task {
    
    private Vector filesets = new Vector();
    
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
     * Executes the task
     */
     public void execute() throws BuildException {
        if (filesets.size() != 0) {
            System.out.println("Hello World");
        } else {
            throw new BuildException("You must specify one or more fileset child elements");
        }
    }
}
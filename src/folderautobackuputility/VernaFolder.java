/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package folderautobackuputility;

import java.awt.Component;
import java.io.File;
import java.net.URI;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A class that will take a folder path and store the locations of all of the files within and subfolders.
 * @author Sweord
 */
public class VernaFolder {

    private HashSet<URI> fileSet;
    private HashSet<VernaFolder> subFolders;

    public HashSet<VernaFolder> getSubFolders() {
        return subFolders;
    }

    private URI FolderURI;
    private String FolderName;

    public void loadSubfolders(){
        File tempFile;
        if(subFolders == null) subFolders = new HashSet<VernaFolder>();
        for(URI fileURI : fileSet){
            tempFile = new File(fileURI);
            if(tempFile.isDirectory()) subFolders.add(new VernaFolder(tempFile));
        }
    }

    VernaFolder(File selectedFile) {
        try {
            //System.out.println("Loading File");
            File tempFile;

            tempFile = selectedFile;

            if (tempFile.isDirectory()) {
                System.out.println("Converting to URI and storing folder path");
                FolderURI = tempFile.toURI();
                System.out.println(tempFile.getName());
                setFolderName(tempFile.getName());
            }


            //System.out.println("Constructor done!");


        }//END OF TRY BLOCK
        catch (Exception ex) {
            //System.out.println("Error in VernaFolder(String FolderPath)");
            Logger.getLogger(VernaFolder.class.getName()).log(Level.SEVERE, null, ex);
        }//END OF CATCH BLOCK
    }


    public HashSet<URI> getFileSet() {
        return fileSet;
    }
    
    /**
     * Gets the current folder name/description.
     * @return
     */
    public String getFolderName() {
        return FolderName;
    }

    /**
     * Sets the folders describing string. This does not have to be unique.
     * @param FolderName
     */
    public void setFolderName(String FolderName) {
        this.FolderName = FolderName;
    }

    /**
     * Get's the folder's current URI.
     * @return
     */
    public URI getFolderURI() {
        return FolderURI;
    }

    public void reloadFileSet() {
        String line = "";
        fileSet = new HashSet<URI>();
        try {
            System.out.println("ReloadFileSet!");
            System.out.println("Converting URI to FILE");
            File currentFolder = new File(FolderURI);

            File[] fileList = null;
            if (currentFolder.isDirectory()) {
                //System.out.println("Loading filelist into File array");
                fileList = currentFolder.listFiles();
                URI tempURI = null;
                for (File subfile : fileList) {
                    //System.out.println("Converting Files to URIs");
                    
                    tempURI = subfile.toURI();
                    
                    fileSet.add(tempURI);
                }
            }
        } catch (Exception ex) {
            System.err.println("-=-=-Error-=-=-=-=-");
            System.err.println("Exception: " + ex);
            System.err.println("Cause:     " + ex.getCause());
            System.err.println("Class:     " + ex.getClass());
            System.err.println("Line:      " + ex);
            System.err.println("-=-=-Error-=-=-=-=-");
        }
    }

    public VernaFolder(String FolderPath) {
        try {
            //System.out.println("Loading File");
            File tempFile;

            tempFile = new File(FolderPath);
            
            if (tempFile.isDirectory()) {
                System.out.println("Converting to URI and storing folder path");
                FolderURI = tempFile.toURI();
                System.out.println(tempFile.getName());
                setFolderName(tempFile.getName());
            }


            //System.out.println("Constructor done!");


        }//END OF TRY BLOCK
        catch (Exception ex) {
            //System.out.println("Error in VernaFolder(String FolderPath)");
            Logger.getLogger(VernaFolder.class.getName()).log(Level.SEVERE, null, ex);
        }//END OF CATCH BLOCK

    }//END OF "VernaFolder" CONSTRUCTOR


    public void outputFilenames() {
        for (URI rawr : fileSet) {
            System.out.println(rawr.toString());
        }
    }

    @Override
    public String toString() {
        return FolderName;
    }


    
}

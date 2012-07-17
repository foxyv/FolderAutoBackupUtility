/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package folderautobackuputility;

import java.io.File;
import java.io.Serializable;
import java.net.URI;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A class that will take a folder path and store the locations of all of the
 * files within and subfolders.
 *
 * @author Sweord
 */
public class VernaFolder implements Serializable {

    private LinkedList<URI> fileSet;
    private HashSet<URI> ignoreFiles;
    private URI FolderURI;
    private String FolderName;

    VernaFolder(File selectedFolder) {
        try {

            if (selectedFolder.isDirectory()) {
                FolderURI = selectedFolder.toURI();
                setFolderName(selectedFolder.getName());
            } else {
                Logger.getLogger(VernaFolder.class.getName()).log(Level.SEVERE, selectedFolder.getName() + " is not a folder!");
            }


        }//END OF TRY BLOCK
        catch (Exception ex) {
            //System.out.println("Error in VernaFolder(String FolderPath)");
            Logger.getLogger(VernaFolder.class.getName()).log(Level.SEVERE, null, ex);
        }//END OF CATCH BLOCK
    }
    
    

    public LinkedList<URI> getFileSet() {
        reloadFolderContents();
        Collections.sort(fileSet,new toFileComparator());
        return fileSet;
    }

    /**
     * Gets the current folder name/description.
     *
     * @return
     */
    public String getFolderName() {
        return FolderName;
    }

    /**
     * Sets the folders describing string. This does not have to be unique.
     *
     * @param FolderName
     */
    public final void setFolderName(String FolderName) {
        this.FolderName = FolderName;
    }

    /**
     * Gets the folder's URI.
     *
     * @return
     */
    public URI getFolderURI() {
        return FolderURI;
    }

    public void reloadFolderContents() {

        fileSet = new LinkedList<URI>();
        try {
            File currentFolder = new File(FolderURI);

            File[] fileList;
            if (currentFolder.isDirectory()) {
                //System.out.println("Loading filelist into File array");
                fileList = currentFolder.listFiles();
                URI tempURI;
                if (fileList != null) {
                    for (File subfile : fileList) {
                        //System.out.println("Converting Files to URIs");

                        tempURI = subfile.toURI();

                        fileSet.add(tempURI);
                    }
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(VernaFolder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public VernaFolder(String FolderPath) {
        try {

            File tempFile = new File(FolderPath);

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

    public void addIgnoreFile(File aFile) {
        if (ignoreFiles == null) {
            ignoreFiles = new HashSet<URI>();
        }
        ignoreFiles.add(aFile.toURI());
    }

    @Override
    public String toString() {
        return FolderName;
    }
    private static String endl = "\n"; //LOL C++

    public FileWrapper getFolderFile() {
        return new FileWrapper(this.FolderURI.getPath());
    }
}

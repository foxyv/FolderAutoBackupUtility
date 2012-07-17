/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package folderautobackuputility.Controller;

import folderautobackuputility.Model.FbcFileFilter;
import folderautobackuputility.Model.FileWrapper;
import folderautobackuputility.Model.VernaFolder;
import folderautobackuputility.View.SortedListModel;
import java.awt.Component;
import java.awt.Frame;
import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;

/**
 * A file object holding a list of folders with serialization support.
 *
 * @author Sweord
 */
public class FolderList {

    boolean folderListModified = false;
    final static long serialVersionUID = 564546564l;
    ArrayList<VernaFolder> folders = new ArrayList<VernaFolder>();
    public static final int SUCCESS = 1;
    public static final int CANCELLED = 2;
    

    /**
     * Gets a copy of the folders from the array.
     *
     * @return
     */
    public ArrayList<VernaFolder> getFolderArrayCopy() {
        return new ArrayList<VernaFolder>(folders);
    }

    public int addFolderDialog(Component parent) {
        //Initialize a fileChooser
        JFileChooser openDialog = new JFileChooser();

        //Get a file from the user
        openDialog.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = openDialog.showDialog(parent, "Open");

        if (result == JFileChooser.APPROVE_OPTION) {
            //Get the folder the user chose
            VernaFolder folderToAdd = new VernaFolder(openDialog.getSelectedFile());

            this.addFolder(folderToAdd);
            return SUCCESS;
        }
        else if(result == JFileChooser.CANCEL_OPTION){
            return CANCELLED;
        }
        return 0;
    }

    /**
     * Generates a SortedListModel for use in a JList and returns it.
     *
     * @return A SortedListModel for use in a JList
     */
    public SortedListModel getFolderListModel() {
        SortedListModel newModel = new SortedListModel();
        for (VernaFolder aFolder : folders) {
            newModel.addElement(aFolder);

        }
        return newModel;
    }

    /**
     * Add a folder to the list of folders.
     *
     * @param e
     * @return
     */
    public boolean addFolder(VernaFolder e) {
        boolean success = folders.add(e);
        folderListModified = true;
        return success;
    }

    /**
     * Removes a VernaFolder from the LinkedList contained in this class.
     *
     * @param folderToRemove The folder to remove from the list.
     */
    public void removeFolder(VernaFolder folderToRemove) {
        folders.remove(folderToRemove);
        folderListModified = true;
    }

    /**
     * Read a file to retrieve a list of VernaFolders
     *
     * @param aFile File containing object information for an array list of
     * VernaFolders
     */
    public void readFromFile(File aFile) {
        FileInputStream FileIS = null;
        try {
            //Check to make sure the file is there...
            if (!aFile.exists()) {
                throw new Exception("Tried to read from non-existant file: " + aFile.getAbsolutePath());
            }

            //Open the file for reading
            FileIS = new FileInputStream(aFile);
            ObjectInputStream rawr = new ObjectInputStream(FileIS);

            //Read an object from the file
            Object ReadObject = rawr.readObject();

            //Check to make sure that the object is actually an array list
            if (ReadObject.getClass() == ArrayList.class) {
                //Make a new folder list to load the list into
                folders = new ArrayList<VernaFolder>();

                //Read each object from the ArrayList and check to make sure they are all VernaFolders
                for (Object anObject : (ArrayList) ReadObject) {
                    if (anObject.getClass() == VernaFolder.class) {
                        folders.add((VernaFolder) anObject);
                    } else {
                        //If an object isn't a VernaFolder, skip it and log an error.
                        String errorString = aFile.getName() + " contains a non VernaFolder object!";
                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, errorString);
                    }
                }

            } else {
                throw new Exception("Class mismatch when loading configuration file.");
            }

        }//End of TRY block 
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-readFromFile Exception Handling-=-=-=-=-=-=-=-=-=-=-=-
        catch (Exception ex) {
            if (ex != null) {
                Logger.getLogger(FolderList.class.getName()).log(Level.SEVERE, null, ex);
            }
        } //Make sure that the file stream gets closed if there is a problem.
        finally {
            try {
                FileIS.close();
            } catch (IOException ex) {
                Logger.getLogger(FolderList.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Saves the list of folders.
     *
     * @param target The file target to save the list of folders into.
     */
    public void writeToFile(File target) {
        FileOutputStream fout = null;
        try {
            System.out.println("Saving : " + target.getName());
            if (!target.exists()) {
                target.createNewFile();
            }
            fout = new FileOutputStream(target);

            ObjectOutputStream rawr = new ObjectOutputStream(fout);
            rawr.writeObject(folders);

        } catch (Exception ex) {
            Logger.getLogger(FolderList.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fout.close();
            } catch (IOException ex) {
                Logger.getLogger(FolderList.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Creates a save dialog for the user to specify a file to save the folder
     * list to.
     *
     * @param Parent
     * @return An integer indicating if the dialog resulted in a save.
     */
    public int performSaveAsDialog(Frame Parent) {
        JFileChooser rawr = new JFileChooser(new File("./"));
        rawr.setFileFilter(new FbcFileFilter());
        rawr.setDialogType(JFileChooser.SAVE_DIALOG);


        int option = rawr.showDialog(Parent, null);

        //If the user clicks the save button to complete the save as, save to the selected file.
        if (option == JFileChooser.APPROVE_OPTION) {

            //Check to see if there is a file extension
            String Extension = FileWrapper.getExtension(rawr.getSelectedFile());
            if (Extension.isEmpty()) {
                //TODO: Add support for auto adding an extension to the file.
                System.out.println("No extension on file!");
            }

            //Write to the file
            writeToFile(rawr.getSelectedFile());
            return SUCCESS;
        } else {
            return CANCELLED;
        }

    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package folderautobackuputility;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sweord
 */
public class folderConfigFile extends File {

    final static long serialVersionUID = 564546564l;
    ArrayList<VernaFolder> folders;

    public void readFolders() {
        BufferedReader readConfig = null;
        String tempLine;
        File tempFile;

        try {
            //Check to make sure the file exists if it doesn't exit the method
            if (folders == null) {
                return;
            }

            //Load up the BufferedReader for the file
            readConfig = new BufferedReader(new FileReader(this));

            //Keep reading lines into tempLine until we run out
            while ((tempLine = readConfig.readLine()) != null) {
                //Trim the string we load of whitespace
                tempLine = tempLine.trim();

                //Check to see if the line starts with the "folder:" prefix
                if (tempLine.startsWith("folder:") && (tempLine.isEmpty() != true)) {

                    //Remove the folder prefix from the temporary string
                    tempLine.replace("folder:", "");

                    //Make a new file with the temporary string
                    tempFile = new File(tempLine);

                    //Check to make sure the file exists and is in fact a directory
                    if (tempFile.canRead() && tempFile.isDirectory()) {
                        //Add the directory to the folder list
                        folders.add(new VernaFolder(tempFile));
                    }
                }
            }
        }//end of try block
        catch (Exception ex) {

            Logger.getLogger(folderConfigFile.class.getName()).log(Level.SEVERE, null, ex);

        }//End of catch
        finally {

            try {
                readConfig.close();
            } catch (IOException ex) {
                Logger.getLogger(folderConfigFile.class.getName()).log(Level.SEVERE, null, ex);
            }

        }//end of finally
    } //End of method

    public void writeToFolderConfig(VernaFolder aFolder) {
        //Load up the BufferedReader for the file
        BufferedWriter writeConfig = null;
        try {
            BufferedWriter readConfig = null;
            writeConfig = new //Load up the BufferedReader for the file
            BufferedWriter(new FileWriter(this));
            
        }
        catch (Exception ex) {
            Logger.getLogger(folderConfigFile.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {

            try {
                writeConfig.close();
            } catch (IOException ex) {
                Logger.getLogger(folderConfigFile.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }

    }

    // <editor-fold defaultstate="collapsed" desc="Constructors">
    public folderConfigFile(URI uri) {
        super(uri);
    }

    public folderConfigFile(File parent, String child) {
        super(parent, child);
    }

    public folderConfigFile(String parent, String child) {
        super(parent, child);
    }

    public folderConfigFile(String pathname) {
        super(pathname);
    }// </editor-fold>
}

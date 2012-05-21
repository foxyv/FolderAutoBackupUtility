/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package folderautobackuputility;

import java.io.File;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 *
 * @author Sweord
 */
public class VernaZippingPractice {

    private ZipOutputStream archive;
    LinkedList<ZipEntry> entries;
    File zipArchiveLocation;

    /**
     * Adds a ZipEntry to the linked list of entries.
     * @param e The zip entry to add
     * @return Boolean indicating whether or not the addition was successful
     */
    public boolean addEntry(ZipEntry e) {
        return entries.add(e);
}

    /**
     *
     * @param e
     * @return
     */
    public boolean addEntry(File e) {
        ZipEntry entry = new ZipEntry(e.getAbsolutePath());
        
        return entries.add(entry);
    }

    /**
     *
     */
    public void writeFile(){

    }

    /**
     *
     * @param zipArchiveToWriteTo
     */
    public VernaZippingPractice(File zipArchiveToWriteTo) {
        try {
            zipArchiveLocation = zipArchiveToWriteTo;



        } catch (Exception ex) {
            Logger.getLogger(VernaZippingPractice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package folderautobackuputility.View;

import java.io.File;
import java.util.LinkedList;

/**
 * A container holding the history of opened folder backup lists so the user can reopen them quickly
 * @author Sweord
 */
public class RecentlyOpenedFolderLists {
    LinkedList<File> fileList = new LinkedList<File>();

    public boolean remove(File o) {
        return fileList.remove(o);
    }

    public boolean add(File e) {
        return fileList.add(e);
    }

    public RecentlyOpenedFolderLists() {
    }
    
}

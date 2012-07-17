/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package folderautobackuputility;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author Sweord
 */
public class FbcFileFilter extends FileFilter {

    public boolean accept(File f) {
        if (f.getName().endsWith(".fbc")) {
            return true;
        }
        if (f.isDirectory()) {
            return true;
        }
        return false;
    }

    public String getDescription() {
        return "FolderBackupConfiguration Files";
    }
}

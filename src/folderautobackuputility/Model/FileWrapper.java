/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package folderautobackuputility.Model;

import java.io.File;
import java.net.URI;

/**
 * A File with a different toString method that will return it's name.
 *
 * @author Sweord
 */
public class FileWrapper extends File {

    public FileWrapper(URI uri) {
        super(uri);
    }

    public FileWrapper(File aFile) {
        super(aFile.toURI());
    }

    public FileWrapper(String pathname) {
        super(pathname);
    }

    public FileWrapper(File parent, String child) {
        super(parent, child);
    }

    public FileWrapper(String parent, String child) {
        super(parent, child);
    }

    public static String getExtension(File aFile) {

        String name = aFile.getName();
        int pos = name.lastIndexOf('.');
        return name.substring(pos + 1);
    }

    @Override
    public String toString() {
        return this.getName();
    }
}

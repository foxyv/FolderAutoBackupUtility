/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package folderautobackuputility;

import java.io.File;
import java.net.URI;

/**
 * A File with a different toString method that will return it's name instead of a full path.
 * @author Sweord
 */
public class FileWrapper extends File{

    public FileWrapper(URI uri) {
        super(uri);
    }


    public FileWrapper(String pathname) {
        super(pathname);
    }




    @Override
    public String toString() {
        return this.getName();
    }


}

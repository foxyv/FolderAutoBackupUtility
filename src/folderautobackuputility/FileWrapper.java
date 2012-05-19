/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package folderautobackuputility;

import java.io.File;
import java.net.URI;

/**
 *
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

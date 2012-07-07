/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package folderautobackuputility;

import java.io.*;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 *
 * @author Sweord
 */
public class ZipPractice {

    FileInputStream fileIn;
    FileOutputStream fos;
    ZipInputStream zipIn;
    ZipOutputStream zipOut;
    Boolean overwrite = false;
    static String outputFolder = "C:\\Users\\Sweord\\Downloads\\lzma920 (1)\\rawr\\";
    static int BUFFERSIZE = 2048;

    public static void main(String[] args) {
        ZipPractice rawr = new ZipPractice();
    }

    public ZipPractice() {
        URL ZipFile = this.getClass().getResource("/folderautobackuputility/7zr.zip");


        extractFiles(ZipFile, outputFolder,false);
    }

    public static void extractFiles(URL ZipFile, String targetFolder, Boolean overwrite) {
        FileInputStream fileIn;
        FileOutputStream fos;
        ZipInputStream zipIn = null;
        ZipOutputStream zipOut;

        try {
            fileIn = new FileInputStream(ZipFile.getFile());
            zipIn = new ZipInputStream(new BufferedInputStream(fileIn));
            BufferedOutputStream BOutput;

            byte[] bufferBytes = new byte[BUFFERSIZE];

            ZipEntry entry;
            String writeFilePath;
            File OutputFile;

            //Keep writing files until we reach the end of the stream
            while ((entry = zipIn.getNextEntry()) != null) {

                writeFilePath = targetFolder + entry.getName();
                OutputFile = new File(writeFilePath);
                
                //Check to make sure a file isn't already there
                if (!OutputFile.canRead() || overwrite) {
                    fos = new FileOutputStream(OutputFile);
                    BOutput = new BufferedOutputStream(fos, BUFFERSIZE);
                    int count;
                    System.out.println("Extracting : " + entry.getName());

                    while ((count = zipIn.read(bufferBytes, 0, BUFFERSIZE)) != -1) {
                        BOutput.write(bufferBytes, 0, count);
                    }
                    BOutput.flush();
                    BOutput.close();
                }
                else{
                    Exception ex = new Exception(entry.getName() + " already exists.");
                    Logger.getLogger(ZipPractice.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            zipIn.close();

        } catch (Exception ex) {
            Logger.getLogger(ZipPractice.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                zipIn.close();
            } catch (IOException ex) {
                Logger.getLogger(ZipPractice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}

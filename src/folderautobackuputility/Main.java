/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package folderautobackuputility;

import java.awt.AWTException;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Sweord
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            BufferedImage bufferedImage;
                URI iconURI = Main.class.getResource("/folderautobackuputility/FolderIcon.png").toURI();
                File Image = new File(iconURI);
                bufferedImage = ImageIO.read(Image);
                
           
            

            BackupUtilityTrayItem rawr = new BackupUtilityTrayItem(bufferedImage);
            rawr.setImageAutoSize(true);
            bufferedImage.flush();
            FolderBackupUI mainWindow = new FolderBackupUI();
            mainWindow.setVisible(true);
            rawr.setBackupUIWindow(mainWindow);
            SystemTray.getSystemTray().add(rawr);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Main() {
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package folderautobackuputility;

import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JToolTip;

/**
 *
 * @author Sweord
 */
public class BackupUtilityTrayItem extends TrayIcon {

    JFrame BackupUIWindow;
    PopupMenu rawr;
    MenuItem Exit = new MenuItem("Exit");
    JToolTip StatusTooltip = new JToolTip();
    
    public JFrame getBackupUIWindow() {
        return BackupUIWindow;
    }

    public void setBackupUIWindow(JFrame BackupUIWindow) {
        this.BackupUIWindow = BackupUIWindow;
    }
    
    ActionListener exitAction = new ActionListener() {

        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    };
            
    ActionListener trayIconActionListener = new ActionListener() {

        public void actionPerformed(ActionEvent e) {
            System.out.println("RAWR! Someone performed the tray Icon actions!");
            System.out.println(BackupUIWindow);
            if(BackupUIWindow != null) {
                System.out.println("rawr!");
                BackupUIWindow.setExtendedState(JFrame.NORMAL);
                BackupUIWindow.setVisible(true);
                BackupUIWindow.toFront();
            }
        }
    };

    public BackupUtilityTrayItem(Image image) {
        super(image);
        init();
    }

    public BackupUtilityTrayItem(Image image, String tooltip) {
        super(image, tooltip);
        init();
    }

    public BackupUtilityTrayItem(Image image, String tooltip, PopupMenu popup) {
        super(image, tooltip, popup);
        init();
    }

    private void init() {
        this.addActionListener(trayIconActionListener);
        rawr = new PopupMenu();
        Exit.addActionListener(exitAction);
        rawr.add(Exit);        
        this.setPopupMenu(rawr);
        this.setToolTip("Folder Auto Backup Utility");

    }
}

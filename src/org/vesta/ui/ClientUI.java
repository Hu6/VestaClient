/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.vesta.ui;

import org.vesta.loader.RSLoader;
import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author James
 */
public class ClientUI extends JFrame implements WindowListener {

    private RSLoader loader;

    /**
     *
     * @param world The world to load.
     * @param signed Should the client be a signed, or unsiged client?
     */
    public ClientUI(int world, boolean signed) {
        super();
        addWindowListener(this);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setTitle("VestaClient for RS2 by Jimmy5410 -- Edited for Oldscape by Longbottom/!Hu6");
        URL imageURL = getClass().getResource("icon.png");
        setIconImage(new ImageIcon(imageURL).getImage());
        try {
            loader = new RSLoader(world, signed);
            Applet applet = loader.getApplet();
            applet.setPreferredSize(new Dimension(765, 503));
            getContentPane().add(applet, BorderLayout.CENTER);
        } catch (Exception e) {
            setVisible(false);
            e.printStackTrace(System.err);
            JOptionPane.showMessageDialog(null,
                    "Fatal IOException while loading client!", "Error!",
                    JOptionPane.ERROR_MESSAGE);
            if (loader != null) {
                Applet applet = loader.getApplet();
                if (applet != null) {
                    applet.destroy();
                }
            }
            dispose();
        }
        pack();
    }

    public void windowActivated(WindowEvent e) {
    }

    public void windowClosed(WindowEvent e) {
    }

    public void windowClosing(WindowEvent e) {
        setVisible(false);
        loader.getApplet().destroy();
        dispose();
        System.exit(0);
    }

    public void windowDeactivated(WindowEvent e) {
    }

    public void windowDeiconified(WindowEvent e) {
    }

    public void windowIconified(WindowEvent e) {
    }

    public void windowOpened(WindowEvent e) {
    }
}

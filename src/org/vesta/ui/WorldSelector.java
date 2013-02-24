/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.vesta.ui;

import javax.swing.JOptionPane;

/**
 *
 * @author James
 */
public class WorldSelector {

    public WorldSelector() {
        //String worldID = JOptionPane.showInputDialog(null, "Please input a world number.", JOptionPane.QUESTION_MESSAGE);
        try {
            int world = 1;
            //int world = Integer.parseInt(worldID);
            (new ClientUI(world, true)).setVisible(true);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid world number!", "Fatal Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

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
public class GameAndWorldSelector {

    public GameAndWorldSelector() {
    	String[] options = new String[] {"Runescape Classic", "Runescape 2007", "Runescape - EoC"};
        int gameID=JOptionPane.showOptionDialog(null, "Which version of Runescape would you like to play?", "Message", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        
    	String worldID = JOptionPane.showInputDialog(null, "Please input a world number (1-3 for Classic, 1-78 for RS2007, 1-139 for EoC)", JOptionPane.QUESTION_MESSAGE);
        
        
        
        try {
            int world = Integer.parseInt(worldID);
            (new ClientUI(world, gameID, true)).setVisible(true);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid world number!", "Fatal Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

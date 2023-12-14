package controllerview;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Class to represent title of game. Used only by MasterView class sin this package hence
 * made it package private.
 */
class GameTitlePanel extends JPanel {

  GameTitlePanel() {
    JLabel gameTitle = new JLabel("<html><h1><font color='#b400b4'>Dungeons and Demons</font>"
            + "</h1><html>");

    gameTitle.setSize(new Dimension(this.getWidth(),this.getHeight()));
    this.add(gameTitle);
    this.setBackground(new Color(155,155,255));


  }
}

package controllerview;

import dungeon.DungeonReadOnly;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 * Used to display Status of actions. Used only by MasterView class sin this package hence
 * made it package private.
 */
class StatusPanel extends JPanel {

  JLabel infoLabel;

  StatusPanel(DungeonReadOnly model) {
    infoLabel = new JLabel("<html>Welcome to the Game! <br> Use arrow keys to move around, "
            + "Press 's' followed by an arrow key to shoot, Press 'p' to pickup items in the "
            + "cave</html>");
    this.add(infoLabel);
    this.setBackground(new Color(180,180,255));
    infoLabel.setForeground(new Color(70, 0, 204));
  }

  void updateText(String s) {

    infoLabel.setText(s);

  }

}

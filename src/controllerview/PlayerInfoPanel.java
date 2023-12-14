package controllerview;

import dungeon.DungeonReadOnly;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Used to display player info on the screen. Used only by MasterView class sin this package hence
 * made it package private.
 */
class PlayerInfoPanel extends JPanel {

  JLabel infoLabel;
  DungeonReadOnly model;

  PlayerInfoPanel(DungeonReadOnly model) {

    infoLabel = new JLabel();

    this.model = model;
    this.add(infoLabel);
    this.setBackground(new Color(180,180,255));
    infoLabel.setForeground(new Color(0, 103, 35));
    updateText();

  }


  void updateText() {
    infoLabel.setText("<html> <p><b>Arrows: </b>" + String.valueOf(model
            .getPlayerArrowsCollected()) + "</p> "
            + "<br> <p><b>Rubies: <b>" + model.getPlayerDetails().getOrDefault(
                    "ruby", 0) + "</p>"
            + "<br> <p><b>Diamonds: <b>" + model.getPlayerDetails().getOrDefault(
                    "diamond", 0) + "</p>"
            + "<br> <p><b>Sapphires: <b>" + model.getPlayerDetails().getOrDefault(
                    "sapphire", 0) + "</p></html>");

  }
}

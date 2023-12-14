package controllerview;

import dungeon.DungeonReadOnly;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Used to display info of the current cell. USed only by MasterView class sin this package hence
 * made it package private.
 */
class CellInfoPanel extends JPanel {

  JLabel infoLabel;
  DungeonReadOnly model;

  CellInfoPanel(DungeonReadOnly model) {

    if (model == null) {
      throw new IllegalArgumentException(" Model passed is null");
    }

    infoLabel = new JLabel();

    this.model = model;
    this.add(infoLabel);
    this.setBackground(new Color(180,180,255));
    infoLabel.setForeground(new Color(0, 103, 35));
    updateText();
  }

  public void updateText() {

    String sound = "No";
    if (model.getPitSound()) {
      sound = "Yes";
    }

    infoLabel.setText("<html> <p> <b> Arrows: </b>" + String.valueOf(model
            .getCurrentLocArrowDetails()) + "</p> "
           + "<br> <p><b>Rubies: <b> " + model.getCurrentLocTreasureDetails()
            .getOrDefault("ruby", 0) + "</p>"
            + "<br> <p><b>Diamonds: <b>" + model.getCurrentLocTreasureDetails()
            .getOrDefault("diamond", 0) + "</p>"
            + "<br> <p><b>Sapphires: <b>" + model.getCurrentLocTreasureDetails()
            .getOrDefault("sapphire", 0) + "</p>"
            + "<br> <p><b>Smell: <b>" + model.getDemonSmell() + "</p>"
            + "<br> <p><b>Sound: <b>" + sound + "</p></html>");
  }
}

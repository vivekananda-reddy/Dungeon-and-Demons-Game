package controllerview;

import dungeon.CellReadOnly;
import dungeon.DungeonReadOnly;

import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JPanel;


/**
 * Used to layout the dungeon. Accessed only by Master view hence made it package private.
 */
class DungeonPanel extends JPanel {

  DungeonPanel(DungeonReadOnly model) {

    if (model == null) {
      throw new IllegalArgumentException("Null model passed");
    }
    CellReadOnly[][] grid;

    grid = model.getDungeon();


    this.setLayout(new GridLayout(grid.length, grid[0].length));

    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[0].length; j++) {
        CellPanel cellPanel = new CellPanel(i, j, model);
        cellPanel.setSize(new Dimension(200,200));
        this.add(cellPanel);
      }
    }

  }

}

package controllerview;

import constants.DemonSmell;
import constants.Direction;
import dungeon.CellReadOnly;
import dungeon.DungeonReadOnly;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.JPanel;


/**
 * Used to display cells of dungeon. Used only by MasterView class sin this package hence
 * made it package private.
 */
class CellPanel extends JPanel {

  private final int x;
  private final int y;
  private final DungeonReadOnly model;


  CellPanel(int x, int y, DungeonReadOnly model) {

    if (model == null) {
      throw new IllegalArgumentException("Model is null");
    }

    if (x < 0 || y < 0 || model.getDungeon().length < x || model.getDungeon()[0].length < y) {
      throw new IllegalArgumentException("Invalid cell coordinates passed");
    }

    this.model = model;

    this.x = x;
    this.y = y;

  }

  @Override
  public void paintComponent(Graphics g) {

    CellReadOnly cell = model.getDungeon()[x][y];
    StringBuilder fileName = new StringBuilder();

    if (!model.isVisited(x, y)) {
      fileName.append("blank.png");
      try {
        BufferedImage cellBackground = ImageIO.read(ClassLoader.getSystemResource("dungeonimages/"
                + fileName.toString()));
        Image v = cellBackground.getScaledInstance(this.getWidth(),this.getHeight(),
                Image.SCALE_SMOOTH);
        g.drawImage(v, 0, 0, null);
      }
      catch (IOException e) {
        e.printStackTrace();
      }

      return;

    }

    else {
      if (model.getPlayerCurrentLocation().getX() == x && model.getPlayerCurrentLocation()
              .getY() == y) {
        fileName.append("C");
      }

      List<Direction> list = cell.getPossibleDirections();

      if (list.contains(Direction.NORTH)) {
        fileName.append("N");
      }
      if (list.contains(Direction.EAST)) {
        fileName.append("E");
      }
      if (list.contains(Direction.SOUTH)) {
        fileName.append("S");
      }
      if (list.contains(Direction.WEST)) {
        fileName.append("W");
      }
    }

    fileName.append(".png");

    try {

      BufferedImage cellBackground = ImageIO.read(ClassLoader.getSystemResource("dungeonimages/"
              + fileName.toString()));
      Image v = cellBackground.getScaledInstance(this.getWidth(),this.getHeight(),
              Image.SCALE_SMOOTH);
      g.drawImage(v, 0, 0, null);


      if (cell.containsPit()) {
        BufferedImage pit = ImageIO.read(ClassLoader.getSystemResource("dungeonimages/pit.png"));
        Image p = pit.getScaledInstance(this.getWidth() / 4,this.getHeight() / 4,
                Image.SCALE_SMOOTH);
        g.drawImage(p, this.getWidth() / 3, this.getHeight() / 3, null);
      }

      if (cell.containsThief()) {
        BufferedImage thief = ImageIO.read(ClassLoader.getSystemResource(
                "dungeonimages/thief.png"));
        Image t = thief.getScaledInstance(this.getWidth() / 4,this.getHeight() / 4,
                Image.SCALE_SMOOTH);
        g.drawImage(t, this.getWidth() / 3, this.getHeight() / 3, null);
      }


      Map<String,Integer> treasure = cell.getTreasure();

      if (treasure.containsKey("diamond") ) {
        BufferedImage diamond = ImageIO.read(ClassLoader.getSystemResource(
                "dungeonimages/diamond.png"));
        Image d = diamond.getScaledInstance(this.getWidth() / 5,this.getHeight() / 5,
                Image.SCALE_SMOOTH);
        g.drawImage(d, this.getWidth() / 4, this.getHeight() / 2, null);
      }

      if (treasure.containsKey("ruby")) {
        BufferedImage ruby = ImageIO.read(ClassLoader.getSystemResource(
                "dungeonimages/ruby.png"));
        Image r = ruby.getScaledInstance(this.getWidth() / 5,this.getHeight() / 5,
                Image.SCALE_SMOOTH);
        g.drawImage(r, this.getWidth() / 4, this.getHeight() / 4, null);
      }

      if (treasure.containsKey("sapphire")) {
        BufferedImage sapphire = ImageIO.read(ClassLoader.getSystemResource(
                "dungeonimages/emerald.png"));
        Image s = sapphire.getScaledInstance(this.getWidth() / 5,this.getHeight() / 5,
                Image.SCALE_SMOOTH);
        g.drawImage(s, this.getWidth() / 2, this.getHeight() / 2, null);
      }

      if (cell.getArrowCount() > 0) {
        BufferedImage arrow;
        if (model.getPlayerCurrentLocation().getX() == x && model.getPlayerCurrentLocation()
                .getY() == y) {
          arrow = ImageIO.read(ClassLoader.getSystemResource("dungeonimages/arrow-white.png"));

        }
        else {
          arrow = ImageIO.read(ClassLoader.getSystemResource("dungeonimages/arrow-black.png"));

        }
        Image a = arrow.getScaledInstance(this.getWidth() / 4,this.getHeight() / 10,
                Image.SCALE_SMOOTH);

        if (fileName.toString().equals("EW.png") || fileName.toString().equals("CEW.png")) {
          g.drawImage(a, this.getWidth() / 2, (this.getHeight() / 3) + (this.getHeight() / 10),
                  null);
        }

        else if (fileName.toString().equals("NE.png") || fileName.toString().equals("CNE.png")) {
          g.drawImage(a, this.getWidth() / 2, (this.getHeight() / 4) , null);
        }

        else if (fileName.toString().equals("ES.png") || fileName.toString().equals("CES.png")) {
          g.drawImage(a, (this.getWidth() / 2) + (this.getWidth() / 8) , this.getHeight() / 2,
                  null);
        }

        else if (fileName.toString().equals("SW.png") || fileName.toString().equals("CSW.png")) {
          g.drawImage(a, this.getWidth() / 5, this.getHeight() / 2, null);
        }

        else if (fileName.toString().equals("NS.png") || fileName.toString().equals("CNS.png")) {
          g.drawImage(a, (this.getWidth() / 3) + (this.getWidth() / 20), this.getHeight() / 5,
                  null);
        }

        else {
          g.drawImage(a, this.getWidth() / 3, this.getHeight() / 5, null);
        }

      }


      if (cell.containsDemons()) {
        BufferedImage demon = ImageIO.read(ClassLoader.getSystemResource(
                "dungeonimages/otyugh.png"));
        Image d = demon.getScaledInstance(this.getWidth() / 4,this.getHeight() / 4,
                Image.SCALE_SMOOTH);
        g.drawImage(d, this.getWidth() / 2, this.getHeight() / 3, null);
      }

      if (model.getPlayerCurrentLocation().getX() == x && model.getPlayerCurrentLocation()
              .getY() == y) {

        BufferedImage smell;
        if (model.getDemonSmell() == DemonSmell.STRONG) {
          smell = ImageIO.read(ClassLoader.getSystemResource("dungeonimages/stench02.png"));
          Image s = smell.getScaledInstance(this.getWidth() / 3,this.getHeight() / 3,
                  Image.SCALE_SMOOTH);
          positionSmell(fileName.toString(), g, s);
        }

        else if (model.getDemonSmell() == DemonSmell.WEAK) {
          smell = ImageIO.read(ClassLoader.getSystemResource("dungeonimages/stench01.png"));
          Image s = smell.getScaledInstance(this.getWidth() / 3,this.getHeight() / 3,
                  Image.SCALE_SMOOTH);
          positionSmell(fileName.toString(), g, s);
        }

      }

    }
    catch (IOException e) {
      e.printStackTrace();
    }

    //this.setVisible(true);

  }

  private void positionSmell(String fileName, Graphics g, Image s) {
    if (fileName.equals("NW.png") || fileName.equals("CNW.png")) {
      g.drawImage(s, this.getWidth() / 4, (this.getHeight() / 10) , null);
    }

    else if (fileName.equals("NE.png") || fileName.equals("CNE.png")) {
      g.drawImage(s, (this.getWidth() / 2) + (this.getWidth() / 10), this.getHeight() / 5 , null);
    }

    else if (fileName.equals("ES.png") || fileName.equals("CES.png")) {
      g.drawImage(s, (this.getWidth() / 2) + (this.getWidth() / 18) , this.getHeight() / 2, null);
    }

    else if (fileName.equals("SW.png") || fileName.equals("CSW.png")) {
      g.drawImage(s, this.getWidth() / 4, this.getHeight() / 2, null);
    }

    else {
      g.drawImage(s, this.getWidth() / 3, this.getHeight() / 3, null);
    }

  }


}

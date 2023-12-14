package controllerview;

import constants.Direction;
import dungeon.CellReadOnly;
import dungeon.DungeonReadOnly;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;
import javax.swing.border.Border;


/**
 * A class to manage all GUI components of the view for the game. Displays all states of the game
 * and takes user inputs and feeds it to the controller. Displays result form read only model and
 * controller on the screen.
 */

public class MasterView extends JFrame implements View {

  private DungeonReadOnly model;
  private final JMenuItem gameSettings;
  private final JMenuItem restartSameGame;
  private final JMenuItem restartSameSpec;
  private final JMenuItem quit;
  private PlayerInfoPanel playerInfoPanel;
  private CellInfoPanel cellInfoPanel;
  private StatusPanel statusPanel;
  private DungeonPanel dungeonPanel;
  private JPanel parentDungeonPanel;

  /**
   * Constructs a view object with read only model.
   * @param model read only version of the dungeon
   */
  public MasterView(DungeonReadOnly model) {

    super("Dungeons and Demons");
    if (model == null) {
      throw new IllegalStateException("Model is null");
    }

    this.model = model;

    //Setting layout for frame
    this.setLayout(new GridBagLayout());


    //Creating options menu on top
    JMenu options = new JMenu("Options");



    restartSameSpec = new JMenuItem("New Game");
    options.add(restartSameSpec);

    restartSameGame = new JMenuItem("Restart Game");
    options.add(restartSameGame);

    gameSettings = new JMenuItem("Settings");
    options.add(gameSettings);

    options.addSeparator();

    quit = new JMenuItem("Quit");
    options.add(quit);

    JMenuBar menuBar = new JMenuBar();
    menuBar.add(options);
    this.setJMenuBar(menuBar);
    this.getContentPane().setBackground(new Color(180,180,255));

    GridBagConstraints c = new GridBagConstraints();

    //Adding a title for game

    c.fill = GridBagConstraints.HORIZONTAL;
    c.gridx = 0;
    c.gridy = 0;
    c.gridwidth = 10;
    c.gridheight = 1;
    c.weightx = 1.0;
    c.anchor = GridBagConstraints.PAGE_START;
    GameTitlePanel gameTitlePanel = new GameTitlePanel();
    this.add(gameTitlePanel,c);


    setUpView();


    this.setSize(new Dimension(1100,800));
    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    //this.setVisible(true);
  }

  private void setUpView() {

    GridBagConstraints c;

    //Adding a status panel to display feedback
    Border border;
    c = new GridBagConstraints();
    statusPanel = new StatusPanel(model);
    border = BorderFactory.createTitledBorder("Status");
    statusPanel.setBorder(border);
    c.fill = GridBagConstraints.HORIZONTAL;
    c.gridx = 0;
    c.gridy = 1;
    c.gridwidth = 10;
    c.gridheight = 1;
    c.weightx = 0.8;
    c.anchor = GridBagConstraints.LINE_START;
    this.add(statusPanel,c);

    //Adding a panel to display dungeon with scrolling capability
    c = new GridBagConstraints();
    dungeonPanel = new DungeonPanel(model);
    parentDungeonPanel = new JPanel();
    parentDungeonPanel.setLayout(new BorderLayout());

    dungeonPanel.setPreferredSize(new Dimension(100 * model.getDungeon().length,100
            * model.getDungeon()[0].length));

    JScrollPane p = new JScrollPane(dungeonPanel);
    parentDungeonPanel.add(p);
    border = BorderFactory.createTitledBorder("Dungeon");
    parentDungeonPanel.setBorder(border);

    c.fill = GridBagConstraints.BOTH;
    c.gridx = 0;
    c.gridy = 2;
    c.weightx = 0.9;
    c.weighty = 0.9;
    c.gridwidth = 5;
    c.gridheight = 5;
    this.add(parentDungeonPanel,c);

    //Adding a panel to display player inventory
    c = new GridBagConstraints();
    playerInfoPanel = new PlayerInfoPanel(model);
    border = BorderFactory.createTitledBorder("Player Info");
    playerInfoPanel.setBorder(border);


    c.gridx = 6;
    c.gridy = 2;

    c.ipadx = 30;

    c.gridwidth = 4;
    c.gridheight = 3;

    this.add(playerInfoPanel,c);

    //Adding a cell panel to display current cell info
    c = new GridBagConstraints();
    cellInfoPanel = new CellInfoPanel(model);
    border = BorderFactory.createTitledBorder("Cell Details");
    cellInfoPanel.setBorder(border);

    c.gridx = 6;
    c.gridy = 4;

    c.ipadx = 30;

    c.gridwidth = 4;
    c.gridheight = 3;

    this.add(cellInfoPanel,c);


  }

  @Override
  public void setModel(DungeonReadOnly model, ControllerFeatures listener) {
    if (model == null || listener == null) {
      throw new IllegalArgumentException("Model and controller can't be null");
    }
    this.model = model;

    this.getContentPane().remove(playerInfoPanel);
    this.getContentPane().remove(cellInfoPanel);
    this.getContentPane().remove(statusPanel);
    this.getContentPane().remove(parentDungeonPanel);

    setUpView();
    setMouseListenerForDungeon(listener);

  }

  private void setMouseListenerForDungeon(ControllerFeatures listener) {

    if (listener == null) {
      throw new IllegalArgumentException("Controller passed can't be null");
    }

    MouseAdapter clickAdapter = new MouseAdapter() {

      @Override
      public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        //System.out.println("Mouse clicked");

        CellReadOnly[][] grid = model.getDungeon();
        int xGrid;
        int yGrid;

        xGrid = e.getX() / (dungeonPanel.getSize().width / grid[0].length);
        yGrid = e.getY() / (dungeonPanel.getSize().height / grid.length);

        System.out.println(xGrid + " " + yGrid);

        listener.handleDungeonClick(yGrid, xGrid);

      }
    };

    dungeonPanel.addMouseListener(clickAdapter);
  }

  @Override
  public void setFeatures(ControllerFeatures listener) {

    if (listener == null) {
      throw new IllegalArgumentException("Controller passed can't be null");
    }



    setMouseListenerForDungeon(listener);


    quit.addActionListener(l -> listener.exitProgram());
    restartSameSpec.addActionListener(l -> listener.initiateNewGame());

    restartSameGame.addActionListener(l -> listener.restartGame());

    ActionListener gameSettingsListener = new GameSettings(listener);
    gameSettings.addActionListener(gameSettingsListener);
    CellReadOnly[][] grid = model.getDungeon();

    this.addKeyListener(new KeyAdapter() {
      boolean flag  = false;

      private int getDistance() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Choose distance to shoot:"));
        DefaultComboBoxModel<Integer> distances = new DefaultComboBoxModel<>();

        for (int i = 1; i < grid.length * grid[0].length; i++) {
          distances.addElement(i);
        }


        JComboBox<Integer> comboBox = new JComboBox<>(distances);
        panel.add(comboBox);

        int result = JOptionPane.showConfirmDialog(null, panel, "Shoot distance",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
          return (int) comboBox.getSelectedItem();
        }
        return -1;
      }


      @Override
      public void keyReleased(KeyEvent e) {

        if (model.isGameOver()) {
          return;
        }

        if (e.getKeyCode() == KeyEvent.VK_UP && !flag) {
          listener.movePlayer(Direction.NORTH);
        }
        else if (e.getKeyCode() == KeyEvent.VK_DOWN && !flag) {
          listener.movePlayer(Direction.SOUTH);
        }

        else if (e.getKeyCode() == KeyEvent.VK_RIGHT && !flag) {
          listener.movePlayer(Direction.EAST);
        }

        else if (e.getKeyCode() == KeyEvent.VK_LEFT && !flag) {
          listener.movePlayer(Direction.WEST);
        }

        else if (e.getKeyCode() == KeyEvent.VK_UP && flag) {
          int d = getDistance();
          flag = false;
          if (d != -1) {
            listener.shootArrow(Direction.NORTH, d);
          }

        }
        else if (e.getKeyCode() == KeyEvent.VK_DOWN && flag) {
          int d = getDistance();
          flag = false;
          if (d != -1) {
            listener.shootArrow(Direction.SOUTH, d);
          }

        }

        else if (e.getKeyCode() == KeyEvent.VK_RIGHT && flag) {
          int d = getDistance();
          flag = false;
          if (d != -1) {
            listener.shootArrow(Direction.EAST, d);
          }

        }

        else if (e.getKeyCode() == KeyEvent.VK_LEFT && flag) {
          int d = getDistance();
          flag = false;
          if (d != -1) {
            listener.shootArrow(Direction.WEST, d);
          }

        }

        else if (e.getKeyCode() == KeyEvent.VK_P) {
          flag = false;
          listener.pickItems();

        }

        else if (e.getKeyCode() == KeyEvent.VK_S) {
          flag = true;
        }


        else {
          flag = false;
        }
      }
    });
  }

  @Override
  public void showErrorMessage(String error) {

    if (error == null || error.equals("")) {
      throw new IllegalArgumentException("Status can't be null or empty");
    }

    JOptionPane.showMessageDialog(this, error);
  }

  @Override
  public void makeVisible(boolean visible) {
    this.setVisible(visible);
  }

  @Override
  public void refresh(String status) {

    if (status == null || status.equals("")) {
      throw new IllegalArgumentException("Status can't be null or empty");
    }

    repaint();
    playerInfoPanel.updateText();
    cellInfoPanel.updateText();
    statusPanel.updateText(status);

  }
}

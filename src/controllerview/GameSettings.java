package controllerview;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


/**
 * Used to take in game settings. USed only by master view hence made it package private.
 */
class GameSettings implements ActionListener {


  JComboBox<Integer> comboBoxInterCon;
  JComboBox<Integer> comboBoxRows;
  JComboBox<Integer> comboBoxCols;
  JComboBox<Integer> comboBoxTreasure;
  JComboBox<Integer> comboBoxDemon;
  JComboBox<String> comboBoxWrap;
  JComboBox<Integer> comboBoxPit;
  JComboBox<Integer> comboBoxThief;
  ControllerFeatures controller;


  GameSettings(ControllerFeatures controller) {
    if (controller == null) {
      throw new IllegalArgumentException("Null controller passed");
    }

    this.controller = controller;
    comboBoxRows = null;
    comboBoxInterCon = null;
    comboBoxDemon = null;
    comboBoxTreasure = null;
    comboBoxCols = null;
  }

  private DefaultComboBoxModel<Integer> getComboBoxModel(int minValue, int maxValue) {
    DefaultComboBoxModel<Integer> item = new DefaultComboBoxModel<>();

    for (int i = minValue; i <= maxValue; i++) {
      item.addElement(i);
    }

    return item;
  }

  private JComboBox<Integer> addComboBoxToPopup(JPanel panel, DefaultComboBoxModel<Integer> item) {

    JComboBox<Integer> comboBox = new JComboBox<>(item);
    panel.add(comboBox);
    return comboBox;
  }

  private void addListenerToComboBox(JComboBox<Integer> comboBox) {
    comboBox.addActionListener((ActionEvent ae) -> {
      comboBoxInterCon.setModel(getComboBoxModel(0, (int)comboBoxRows.getSelectedItem()
              * (int)comboBoxCols.getSelectedItem()));
      comboBoxDemon.setModel(getComboBoxModel(1, ((int)comboBoxRows.getSelectedItem()
              * (int)comboBoxCols.getSelectedItem()) / 3));
      comboBoxPit.setModel(getComboBoxModel(0, ((int)comboBoxRows.getSelectedItem()
              * (int)comboBoxCols.getSelectedItem()) / 20));
      comboBoxThief.setModel(getComboBoxModel(1, ((int)comboBoxRows.getSelectedItem()
              * (int)comboBoxCols.getSelectedItem()) / 20));
    });
  }

  @Override
  public void actionPerformed(ActionEvent e) {

    JPanel panel = new JPanel();
    panel.setLayout(new GridLayout(8,8));
    panel.add(new JLabel("Number of rows for dungeon:"));

    comboBoxRows = addComboBoxToPopup(panel, getComboBoxModel(6, 50));

    addListenerToComboBox(comboBoxRows);



    panel.add(new JLabel("Number of Columns for dungeon:"));
    comboBoxCols = addComboBoxToPopup(panel, getComboBoxModel(6, 50));
    addListenerToComboBox(comboBoxCols);


    panel.add(new JLabel("Interconnectivity:"));
    comboBoxInterCon = addComboBoxToPopup(panel, getComboBoxModel(0,
            (int)comboBoxRows.getSelectedItem() * (int)comboBoxCols.getSelectedItem()));

    panel.add(new JLabel("Percentage of Treasure and Arrows:"));
    comboBoxTreasure = addComboBoxToPopup(panel, getComboBoxModel(0, 100));

    panel.add(new JLabel("Demon count:"));
    comboBoxDemon = addComboBoxToPopup(panel, getComboBoxModel(1, (
            (int)comboBoxRows.getSelectedItem() * (int)comboBoxCols.getSelectedItem()) / 3));

    panel.add(new JLabel("Number of Pits:"));
    comboBoxPit = addComboBoxToPopup(panel, getComboBoxModel(1, (
            (int)comboBoxRows.getSelectedItem() * (int)comboBoxCols.getSelectedItem()) / 20));

    panel.add(new JLabel("Number of Thief:"));
    comboBoxThief = addComboBoxToPopup(panel, getComboBoxModel(1, (
            (int)comboBoxRows.getSelectedItem() * (int)comboBoxCols.getSelectedItem()) / 20));


    panel.add(new JLabel("Wrapping needed:"));
    DefaultComboBoxModel<String> temp = new DefaultComboBoxModel<>();
    temp.addElement("Yes");
    temp.addElement("No");
    comboBoxWrap = new JComboBox<>(temp);
    panel.add(comboBoxWrap);


    int result = JOptionPane.showConfirmDialog(null, panel, "Game Settings",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
    if (result == JOptionPane.OK_OPTION) {

      boolean wrapping = comboBoxWrap.getSelectedItem().equals("Yes");


      controller.gameSettings((int)comboBoxRows.getSelectedItem(),
              (int)comboBoxCols.getSelectedItem(), (int)comboBoxInterCon.getSelectedItem(),
              wrapping, (int)comboBoxTreasure.getSelectedItem(),
              (int)comboBoxDemon.getSelectedItem(), (int)comboBoxPit.getSelectedItem(),
              (int)comboBoxThief.getSelectedItem());
    }

  }
}

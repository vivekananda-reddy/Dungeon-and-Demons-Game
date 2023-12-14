package controllerview;

import dungeon.DungeonReadOnly;

/**
 * An interface to keep track of all operations related to the GUI of the game. Provides methods
 * for all GUI interactions.
 */
public interface View {

  /**
   * Sets the model in the View with the provided object.
   * @param model Read only  model object
   * @param listener controller object
   */
  void setModel(DungeonReadOnly model, ControllerFeatures listener);

  /**
   * Sets all listeners on the GUI and passed inputs to controller as per the action performed.
   * @param listener Controller object
   */
  void setFeatures(ControllerFeatures listener);

  /**
   * Makes the frame visible to the user.
   * @param visible makes visible if true else hides the frame
   */
  void makeVisible(boolean visible);

  /**
   * Refreshes the view with the status provided.
   * @param status Status to be displayed on the screen
   */
  void refresh(String status);

  /**
   * Shows any errors occurred in the program.
   * @param error error message
   */
  void showErrorMessage(String error);

}

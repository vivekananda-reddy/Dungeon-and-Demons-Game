package test;

import java.io.IOException;

import controllerview.ControllerFeatures;
import controllerview.View;
import dungeon.DungeonReadOnly;

/**
 * A mock view for testing the controller and view interaction.
 */
public class MockView implements View {

  Appendable log;

  /**
   * Builds a mock view object with a appendable log.
   * @param log appendable log for tracking the method calls
   */
  public MockView(Appendable log) {
    this.log = log;
  }

  @Override
  public void setModel(DungeonReadOnly model, ControllerFeatures listener) {
    try {
      log.append("model controller added\n");
    }
    catch (IOException e) {
      //expected to do nothing
    }

  }

  @Override
  public void setFeatures(ControllerFeatures listener) {
    try {
      log.append("controller added\n");
    }
    catch (IOException e) {
      //expected to do nothing
    }

  }

  @Override
  public void makeVisible(boolean visible) {
    try {
      log.append(visible + "\n");
    }
    catch (IOException e) {
      //expected to do nothing
    }

  }

  @Override
  public void refresh(String status) {

    try {
      log.append(status + "\n");
    }
    catch (IOException e) {
      //expected to do nothing
    }


  }

  @Override
  public void showErrorMessage(String error) {
    try {
      log.append(error + "\n");
    }
    catch (IOException e) {
      //expected to do nothing
    }

  }

}

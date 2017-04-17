package org.netbeans.modules.autoupdate.silentupdate.gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.netbeans.api.autoupdate.UpdateUnit;
import org.netbeans.modules.autoupdate.silentupdate.UpdateHandler;

/**
 * @author Niels
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private Label lblAmountLoaded;
    @FXML
    private VBox moduleContainer;

    private List<UpdateUnit> allModules; // de custom moduler der kan installeres
    //private Collection<UpdateElement> installedModules; // de custom moduler der er installeret pt

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        allModules = UpdateHandler.getSilentUpdateProvider().getUpdateUnits();
        updateAmountLabel();
        createModuleCheckBoxes();
    }

    private void createModuleCheckBoxes() {
        for (UpdateUnit unit : allModules) {
            CheckBox checkBox = new CheckBox(unit.getCodeName().replace("rpg.", ""));
            checkBox.setSelected(unit.getInstalled() != null);
            checkBox.setOnAction((e) -> {
                new Thread(() -> {
                    update(unit.getCodeName(), checkBox.isSelected());
                }).start();
            });
            moduleContainer.getChildren().add(checkBox);
        }
    }

    private void update(String codeName, boolean load) {
        if (load) {
            UpdateHandler.load(codeName);
        }
        else {
            UpdateHandler.unload(codeName);
        }
        //UpdateHandler.checkAndHandleUpdates();
        updateAmountLabel();
    }

    private void updateAmountLabel() {
        Platform.runLater(() -> {
            int amountOfModules = UpdateHandler.getLocallyInstalled().size();
            lblAmountLoaded.setText(amountOfModules + (amountOfModules == 1 ? " module currently loaded" : " modules currently loaded"));
        });
    }

}

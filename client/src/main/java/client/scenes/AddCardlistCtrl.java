package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Cardlist;
import jakarta.ws.rs.WebApplicationException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AddCardlistCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private Stage stage;
    @FXML
    private Button cancel, ok;
    @FXML
    private TextField listName;


    @Inject
    public AddCardlistCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    /**
     * stop everything
     */
    public void cancel() {
        stage = (Stage) cancel.getScene().getWindow();
        listName.clear();
        stage.close();
    }

    /**
     * Send the list to the repo.
     */
    public void ok() {
        stage = (Stage) cancel.getScene().getWindow();
        String name;
        if(listName.getText().isEmpty()) name = "New Board";
        else name = listName.getText();

        try {
            server.addCardList(new Cardlist(name));
        } catch (WebApplicationException e) {

            var alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return;
        }
        stage.close();
    }

    /**
     * Add functionality for kew presses.
     * @param e a kew press
     */
    public void keyPressed(KeyEvent e) {
        switch (e.getCode()) {
            case ENTER:
                ok();
                break;
            case ESCAPE:
                cancel();
                break;
            default:
                break;
        }
    }

}
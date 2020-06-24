package sample;

import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.EventObject;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public int getRows() {
        return rows.get();
    }

    public IntegerProperty rowsProperty() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows.set(rows);
    }

    public int getColumns() {
        return columns.get();
    }

    public IntegerProperty columnsProperty() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns.set(columns);
    }

    IntegerProperty rows = new SimpleIntegerProperty();
    IntegerProperty columns = new SimpleIntegerProperty();
    BooleanProperty canSubmit = new SimpleBooleanProperty(false);

    public boolean isRowsOk() {
        return rowsOk.get();
    }

    public BooleanProperty rowsOkProperty() {
        return rowsOk;
    }

    public void setRowsOk(boolean rowsOk) {
        this.rowsOk.set(rowsOk);
    }

    public boolean isColumnsOk() {
        return columnsOk.get();
    }

    public BooleanProperty columnsOkProperty() {
        return columnsOk;
    }

    public void setColumnsOk(boolean columnsOk) {
        this.columnsOk.set(columnsOk);
    }

    BooleanProperty rowsOk = new SimpleBooleanProperty(false);
    BooleanProperty columnsOk = new SimpleBooleanProperty(false);

    public boolean isCanSubmit() {
        return canSubmit.get();
    }

    public BooleanProperty canSubmitProperty() {
        return canSubmit;
    }

    public void setCanSubmit(boolean canSubmit) {
        this.canSubmit.set(canSubmit);
    }

    public void submit(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)((Node)((EventObject) actionEvent).getSource()).getScene().getWindow();

        FXMLLoader fxmlLoader = new FXMLLoader();
        Pane p = fxmlLoader.load(getClass().getResource("MainWin.fxml").openStream());
        MainWin mainController = (MainWin) fxmlLoader.getController();


        mainController.setRows(this.getRows());
        mainController.setColumns(this.getColumns());
        stage.setTitle("Create a Map");
        stage.setScene(new Scene(p, 300, 275));
        stage.show();

    }

    public void columnsChanged(KeyEvent keyEvent) {
        TextField tf = (TextField) keyEvent.getSource();
        try {
            int val = Integer.parseInt(tf.getText());
            if (val == 0) throw new NumberFormatException(); // it is not possible to 0 columns
            this.columns.set(val);
            this.columnsOk.set(true);
        } catch (NumberFormatException e) {
            this.columnsOk.set(false);
        }
    }

    public void rowsChanged(KeyEvent keyEvent) {
        TextField tf = (TextField) keyEvent.getSource();
        try {
            int val = Integer.parseInt(tf.getText());
            if (val == 0) throw new NumberFormatException(); // it is not possible to 0 rows
            this.rows.set(val);
            this.rowsOk.set(true);
        } catch (NumberFormatException e) {
            this.rowsOk.set(false);
        }
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.canSubmit.bind(this.columnsOk.and(this.rowsOk));
    }
}

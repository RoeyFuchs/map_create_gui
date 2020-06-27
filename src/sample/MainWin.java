package sample;

import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import static javafx.application.Platform.exit;

public class MainWin {
    public GridPane mainGrid;

    IntegerProperty[][] values;

    private int rows;
    private int columns;


    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }


    public GridPane createMapGrid() {
        this.values = new SimpleIntegerProperty[this.getRows()][this.getColumns()];
        int pad = 2;
        int menuBarSize = 25;
        GridPane mapGrid = new GridPane();
        int rows = this.getRows();
        int columns = this.getColumns();
        mapGrid.setGridLinesVisible(true);
        Scene scene = this.mainGrid.getScene();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                Rectangle rec = new ResizableRectangle();
                //width
                rec.widthProperty().bind(scene.widthProperty().divide(columns).add(-2 * pad));
                //height
                rec.heightProperty().bind(scene.heightProperty().add(-menuBarSize).divide(rows).add(-2 * pad));

                IntegerProperty value = new SimpleIntegerProperty();

                rec.fillProperty().bind(Bindings.createObjectBinding(() -> IntToColor.getColor(value.get()), value.asObject()));
                rec.onMouseClickedProperty().setValue((e) -> value.setValue((value.getValue() + 1) % 3));
                this.values[i][j] = value;

                if (i == rows - 1 || i == 0 || j == 0 || j == columns - 1) this.values[i][j].setValue(1); // border


                GridPane.setConstraints(rec, j, i);
                GridPane.setMargin(rec, new Insets(pad, pad, pad, pad));
                mapGrid.getChildren().add(rec);

            }
        }
        GridPane.setConstraints(mapGrid, 0, 1);
        this.mainGrid.getChildren().add(mapGrid);
        return mapGrid;
    }

    @FXML
    private void save() {
        List<List<String>> list = new LinkedList<>();
        for (int i = 0; i < this.rows; i++) {
            list.add(new LinkedList<>());
            for (int j = 0; j < this.columns; j++) {
                list.get(i).add(String.valueOf(convert(this.values[i][j].get())));
            }
        }


        StringBuilder stringMap = new StringBuilder();
        String newLine = System.getProperty("line.separator");
        stringMap.append(this.rows + "," + this.columns).append(newLine);
        for (int i = 0; i < this.rows; i++) {
            stringMap.append(String.join(" ", list.get(i))).append(newLine);
        }


        DirectoryChooser chooser = new DirectoryChooser();
        File selectedDirectory = chooser.showDialog(this.mainGrid.getScene().getWindow());

        if (selectedDirectory==null) return; //if the user didn't choose a directory

        String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss-SSS").format(new Date());
        String fileName = timeStamp+".txt";

        String fullPath = selectedDirectory.getAbsolutePath()+"/"+fileName;

        try {
            FileWriter myWriter = new FileWriter(fullPath);
            myWriter.write(stringMap.toString());
            myWriter.close();
            AlertBox.display("File saved", "File saved");
        } catch (IOException e) {
            AlertBox.display("Error while saving map file");
        }

    }

    private int convert(int i) {
        if(i==1) return 3;
        if(i==0) return 1;
        if(i==2) return 9;
        return -1;
    }


    public void exitgui(ActionEvent actionEvent) {
        exit();
    }
}

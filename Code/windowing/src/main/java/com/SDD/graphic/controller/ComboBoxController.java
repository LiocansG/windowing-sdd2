package com.SDD.graphic.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * An abstract class for controlling a combo box.
 */
public abstract class ComboBoxController {

    /**
     * Fills the combo box with items corresponding to the segment data files in the current
     * directory path.
     *
     * @param directoryComboBox The combo box to fill with items.
     * @param path The path of the directory to look for segment data files in.
     */
    public static void fillComboBoxItem(ComboBox<String> directoryComboBox, String path){
        desactivateEventComboBox(directoryComboBox);
        File file = new File(path);
        File[] files = file.listFiles();
        ArrayList<String> listFiles = new ArrayList<>();
        if (files == null) throw new AssertionError();
        for (File f : files) {
            listFiles.add(f.getName());
        }

        directoryComboBox.getItems().addAll(listFiles);
        directoryComboBox.setValue(listFiles.get(0));
    }

    /**
     * Fills the combo box with items corresponding to the segment data files in the current
     * directory path.
     *
     * @param directoryComboBox The combo box to fill with items.
     */
    public static void fillComboBoxItem(ComboBox<String> directoryComboBox, InputStream inputStream ){
        desactivateEventComboBox(directoryComboBox);
        Scanner scanner = new Scanner(inputStream);
        ArrayList<String> listFiles = new ArrayList<>();
        while (scanner.hasNextLine()) {
            listFiles.add(scanner.nextLine());
        }
        scanner.close();

        directoryComboBox.getItems().addAll(listFiles);
        directoryComboBox.setValue(listFiles.get(0));
    }

    /**
     * Deactivates the action event of the combo box temporarily to remove all items from it.
     *
     * @param directoryComboBox The combo box to deactivate the event for.
     */
    private static void desactivateEventComboBox(ComboBox<String> directoryComboBox){
        EventHandler<ActionEvent> handler = directoryComboBox.getOnAction();
        directoryComboBox.setOnAction(null);
        directoryComboBox.getItems().removeAll(directoryComboBox.getItems());
        directoryComboBox.setOnAction(handler);
    }
}

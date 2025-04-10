package com.example.imgprocessing;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.geometry.Insets;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Button openImg = new Button();
        openImg.setText("Open image");

        ImageView original = new ImageView();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image files","*.png","*.jpg","*.jpeg"));
        openImg.setOnAction(e -> {
            File file = fileChooser.showOpenDialog(stage);
            Image originalImage = new Image(file.toURI().toString(), 410, 520, false, false);
            original.setImage(originalImage);
        });

        Button save = new Button("Save");

        ComboBox comboBox = new ComboBox();
        comboBox.getItems().addAll(
                "Grayscale",
                "Inversion",
                "Blur"
        );
        comboBox.setValue("Grayscale");


        ImageView resultImg = new ImageView();
        Button process = new Button("Process");
        process.setOnAction(e -> {
            String selected = (String) comboBox.getValue();
            switch (selected) {
                case "Grayscale":
                    resultImg.setImage(ImgProcessing.grayscale(original.getImage()));
                    break;
                case "Inversion":
                    resultImg.setImage(ImgProcessing.inversion(original.getImage()));
                    break;
                case "Blur":
                    resultImg.setImage(ImgProcessing.blur(original.getImage()));
                    break;
            }

        });

        save.setOnAction(e -> {
            Image imageToSave = resultImg.getImage();
            if (imageToSave != null) {
                FileChooser saveFileChooser = new FileChooser();
                saveFileChooser.setTitle("Save Image");
                saveFileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("PNG Files", "*.png")
                );
                File file = saveFileChooser.showSaveDialog(stage);
                if (file != null) {
                    try {
                        BufferedImage bufferedImage = ImgProcessing.convertToBufferedImage(imageToSave);
                        ImageIO.write(bufferedImage, "png", file);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        HBox buttons = new HBox(openImg, comboBox, process, save);
        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(10);
        buttons.setPadding(new Insets(10, 0, 10, 0));
        buttons.setStyle("-fx-background-color: lightgray; -fx-border-color:  #545454;");

        HBox images = new HBox(original, resultImg);
        images.setSpacing(10);
        images.setStyle("");
        VBox root = new VBox(buttons, images);

        Scene scene = new Scene(root, 840, 640);
        stage.setTitle("Image processing");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

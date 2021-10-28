package com.example.javafx5;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;

public class HelloApplication extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        HelloController controller = fxmlLoader.getController();
        scene.setOnKeyPressed(event -> {
            if (event.isControlDown()) {
                switch (event.getCode()) {
                    case ADD -> controller.zoomIn();
                    case SUBTRACT -> controller.zoomOut();
                    case DIGIT1, NUMPAD0, INSERT -> controller.zoomReset();
                    case R -> controller.refreshPage();
                    case H -> controller.history();
                    case LEFT -> controller.back();
                    case RIGHT -> controller.forward();
                }

            }
        });
        stage.getIcons().add(new Image(new FileInputStream("D:\\JavaProjects\\javafx5\\target\\classes\\com\\example\\javafx5\\k.png")));
        stage.setMaximized(true);
        stage.setTitle("Kolev Browser");
        stage.setScene(scene);
        stage.show();
    }
}
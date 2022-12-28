package com.andile.todolist;

import com.andile.todolist.controller.TodoListController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TodoListApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TodoListApplication.class.getResource("todolist-view.fxml"));
        TodoListController controller = fxmlLoader.getController();
        Scene scene = new Scene(fxmlLoader.load(), 640, 480);
        stage.setTitle("Todo List!");
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(event -> {
            controller.writeTodoListToFile();
            Platform.exit();
            System.exit(0);
        });
    }

    public static void main(String[] args) {
        launch();
    }
}
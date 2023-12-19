package com.example.team1project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // FXML 파일 이름을 "LoginController.fxml"로 변경
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("LoginController.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("강원도 물가 가격 측정 및 분석 프로그램");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

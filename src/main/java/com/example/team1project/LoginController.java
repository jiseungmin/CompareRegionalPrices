package com.example.team1project;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class LoginController {

    @FXML
    private TextField user; // 사용자 이메일 필드

    @FXML
    private PasswordField password; // 비밀번호 필드

    public void handleLogin(ActionEvent actionEvent) {
        try {
            String userEmail = user.getText(); // 이메일 필드에서 텍스트 가져오기
            String userPassword = password.getText(); // 비밀번호 필드에서 텍스트 가져오기

            System.out.println("로그인 시도: 이메일=" + userEmail + ", 비밀번호=" + userPassword);
            sendLoginRequest(userEmail, userPassword);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void sendLoginRequest(String email, String password) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            String json = String.format("{\"email\":\"%s\", \"password\":\"%s\"}", email, password);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:4000/auth/login"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .thenAccept(response -> {
                        if ("OK".equals(response)) {
                            System.out.println("로그인 성공");
                            Platform.runLater(() -> {
                                try {
                                    Stage stage = (Stage) user.getScene().getWindow();
                                    Parent mainView = FXMLLoader.load(getClass().getResource("MainView.fxml"));
                                    stage.setScene(new Scene(mainView));
                                    stage.show();
                                } catch (IOException e) {
                                    e.printStackTrace();

                                }
                            });
                        } else {
                            System.out.println(response);
                            System.out.println("로그인 실패");
                            // 로그인 실패 시 수행할 작업
                            Platform.runLater(() -> {
                                user.clear();
                                this.password.clear();
                            });
                        }
                    })
                    .join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void handleSignup(ActionEvent actionEvent) {
        // 회원가입 처리 로직
        try {
            // 새로운 뷰 로드
            Parent mainView = FXMLLoader.load(getClass().getResource("SignupController.fxml"));

            // 현재 스테이지 찾기
            Stage stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();

            // 새로운 씬 설정 및 보여주기
            stage.setScene(new Scene(mainView));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

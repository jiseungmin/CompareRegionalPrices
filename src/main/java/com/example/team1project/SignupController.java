package com.example.team1project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SignupController {
    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;
    public void handleSignup(ActionEvent actionEvent) {
        String userEmail = emailField.getText();
        String userPassword = passwordField.getText();

        if (sendSignupRequest(userEmail, userPassword) == true){
            try {
                Parent mainView = FXMLLoader.load(getClass().getResource("LoginController.fxml"));
                Stage stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
                stage.setScene(new Scene(mainView));
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else  {
            System.out.println("Err");
        };
    }
    private boolean sendSignupRequest(String email, String password) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            String json = String.format("{\"email\":\"%s\", \"password\":\"%s\"}", email, password);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:4000/auth/signup"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .thenAccept(response -> {
                        System.out.println("서버 응답: " + response);
                    })
                    .join();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public void handleBack(ActionEvent actionEvent) {
        try {
            Parent mainView = FXMLLoader.load(getClass().getResource("LoginController.fxml"));
            Stage stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(mainView));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

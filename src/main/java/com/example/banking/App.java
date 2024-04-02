package com.example.banking;

import com.example.banking.Models.Model;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage)  {

        Model.getInstance().getViewFactory().showLoginWindow();


    }
}

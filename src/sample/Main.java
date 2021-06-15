package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Подгрузка сцены из FXML
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        //Заголовок для окна
        primaryStage.setTitle("CubicCurve by Ilyushin Yaroslav BIS-18-01");
        //Всегда повверх других окон
        primaryStage.setAlwaysOnTop(true);
        //Сетаем сцену с размерами
        primaryStage.setScene(new Scene(root, 600, 500));
        //Конфиг для окна
        primaryStage.setWidth(600);
        primaryStage.setHeight(500);
        //Минимальный размер окна
        primaryStage.setMinHeight(240);
        primaryStage.setMinWidth(380);
        //Показать окно
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

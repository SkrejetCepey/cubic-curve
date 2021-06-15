package sample;

import com.sun.javafx.geom.Vec2d;
import javafx.animation.AnimationTimer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Pane pane;

    @FXML
    private ComboBox<String> cmbY;

    @FXML
    private TextField point1x;

    @FXML
    private TextField point1y;

    @FXML
    private TextField point2x;

    @FXML
    private TextField point2y;

    @FXML
    private TextField point3x;

    @FXML
    private TextField point3y;

    @FXML
    private TextField point4x;

    @FXML
    private TextField point4y;

    @FXML
    private Button btn;

    @FXML
    private Button start_btn;

    public Controller() throws IOException {
    }

    int quadStatus = 0;//Переменная, запоминающая индекс массивов координат анимированного синего квадрата
    boolean isOn = false;//Проверка на нажатие кнопки button1 ("Create")

    Vec2d point0;
    Vec2d point1;
    Vec2d point2;
    Vec2d point3;

    //Обработчик координатных систем
    CoordinateSystem crdnsys;

    //Эвент выбора в комбобоксе
    public void cmbChooseEvent() {
        //Если выбран первый элемент, то рисуем декартовую сетку
        //Иначе полярную
        //А если еще и запущена анимация, то отрисовываем и ее
        if (getSelectedIndexCombobox() == 0) {
            crdnsys.DrawDescartes();
        } else {
            crdnsys.DrawPolar();
        }
        if (isOn) {
            CreateCubicCurve();
        }
    }

    //Функция создания кривой кубики
    public void create() {
        //Щелкаем isOn
        isOn = !isOn;
        //Чистим полотно
        pane.getChildren().clear();
        //Отрисовываем ту систему координат, которая выбрана в комбобоксе
        if (getSelectedIndexCombobox() == 0) {
            crdnsys.DrawDescartes();
        } else {
            crdnsys.DrawPolar();
        }

        try {
            point0 = new Vec2d(Double.parseDouble(point1x.getText()), Double.parseDouble(point1y.getText()));
            point1 = new Vec2d(Double.parseDouble(point2x.getText()), Double.parseDouble(point2y.getText()));
            point2 = new Vec2d(Double.parseDouble(point3x.getText()), Double.parseDouble(point3y.getText()));
            point3 = new Vec2d(Double.parseDouble(point4x.getText()), Double.parseDouble(point4y.getText()));

        } catch (Exception e) {
            //Если ловим какую-нибудь ошибку, то сбрасываем все текстбоксы и выходим из функции

            point1x.setText("");
            point1y.setText("");

            point2x.setText("");
            point2y.setText("");

            point3x.setText("");
            point3y.setText("");

            point4x.setText("");
            point4y.setText("");
            return;
        }

        //Если кнопка нажата, то включаем кнопку начала анимации + рисуем кубику
        //и меняем название с Create на Destroy у данной кнопки
        //иначе выставляем все на дефолтные значения
        if (isOn) {
            btn.setText("Destroy");
            start_btn.setDisable(false);

            point1x.setDisable(true);
            point1y.setDisable(true);
            point2x.setDisable(true);
            point2y.setDisable(true);
            point3x.setDisable(true);
            point3y.setDisable(true);
            point4x.setDisable(true);
            point4y.setDisable(true);


            CreateCubicCurve();
        } else {
            btn.setText("Create");
            start_btn.setDisable(true);

            point1x.setDisable(false);
            point1y.setDisable(false);
            point2x.setDisable(false);
            point2y.setDisable(false);
            point3x.setDisable(false);
            point3y.setDisable(false);
            point4x.setDisable(false);
            point4y.setDisable(false);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Инициализатор
        //Объявляем новый обработчик систем счисления
        crdnsys = new CoordinateSystem(pane);
        //Рисуем по дефолту декратовую систему координат
        crdnsys.DrawDescartes();
        //Выключаем колллизию у двух окон (графика и систем счисления)
        pane.setMouseTransparent(true);
        //Заполняем комбобокс вариациями систем счисления
        ObservableList<String> items = FXCollections.observableArrayList(
                "Descartes",
                "Polar"
        );
        cmbY.setItems(items);
        //Выбираем первый предмет по умолчанию
        cmbY.getSelectionModel().selectFirst();

        //Объявляем листенер на изменения длины окна приложения
        rootPane.widthProperty().addListener((observableValue, number, t1) -> {
            //Чистим полотно
            pane.getChildren().clear();
            System.out.println(rootPane.getWidth() + " " + rootPane.getHeight());
            //Рисуем ту систему координат, которая выбрана в комбобоксе
            if (getSelectedIndexCombobox() == 0) {
                crdnsys.DrawDescartes();
            } else {
                crdnsys.DrawPolar();
            }
            //Также рисуем кривую, если кнопка "Создать" нажата
            if (isOn) {
                CreateCubicCurve();
            }
        });

        //Объявляем листенер на изменения ширины окна приложения
        rootPane.heightProperty().addListener((observableValue, number, t1) -> {
            //Чистим полотно
            pane.getChildren().clear();
            //Рисуем ту систему координат, которая выбрана в комбобоксе
            if (getSelectedIndexCombobox() == 0) {
                crdnsys.DrawDescartes();
            } else {
                crdnsys.DrawPolar();
            }
            //Также рисуем кривую, если кнопка "Создать" нажата
            if (isOn) {
                CreateCubicCurve();
            }
        });
    }

    //Функция для простоты обращения к индексу комбобокса
    public int getSelectedIndexCombobox() {
        return cmbY.getSelectionModel().getSelectedIndex();
    }

    //Функция рисования куба по траектории кривой
    public void DrawCube() {
        //Запускаем таймер
        at.start();
    }

    //Запоминаем предыдущий отрисованный квадрат
    Rectangle last_quad;

    public void QuadDraw()//Функция для отрисовки синего квадрата в системах координат
    {
        //Если запущена анимация, то чистим предыдущий квадрат с холста
        if (quadStatus > 0) {
            pane.getChildren().remove(last_quad);
        }

        //Получаем новый массив точек для кривой (совпадают с той, которая изображена на холсте)
        sample.CubicCurve curv = new CubicCurve();

        curv.point0 = point0;
        curv.point1 = point1;
        curv.point2 = point2;
        curv.point3 = point3;

        //Высчитываем координаты для квадрата в зависимости от выбранной системы счисления
        if (getSelectedIndexCombobox() == 0) {
            //Достаем массив точек от кривой
            Vec2d[] mass = curv.getCubicCurveCoordinatesMass();

            //Создаем квадрат
            Rectangle quad = new Rectangle(crdnsys.getX0() + (crdnsys.getDx() * mass[quadStatus].x) - 5,
                    crdnsys.getY0() - (crdnsys.getDy() * mass[quadStatus].y) - 5,
                    10,
                    10);
            //Двигаемся к следуюшему
            quadStatus++;
            //Заполняем его синим цветом
            quad.setFill(Color.BLUE);
            //Запоминаем ссылку на текущий квадрат
            last_quad = quad;
            //Добавляем квадрат на полотно
            pane.getChildren().add(quad);
        } else {
            //Коэффициент относительности
            double k = (pane.getWidth() / crdnsys.getMultiСhart()) + (pane.getHeight() / crdnsys.getMultiСhart());
            //Достаем массив точек с координатами кривой
            Vec2d[] mass = curv.getCubicCurveCoordinatesMass();
            //Создаем квадрат
            Rectangle quad = new Rectangle(crdnsys.getX0() + (mass[quadStatus].x * k * 1.15) - 5,
                    crdnsys.getY0() - (mass[quadStatus].y * k * 1.15) - 5, 10, 10);
            //Двигаемся к следующему
            quadStatus++;
            //Заполняем его синим цветом
            quad.setFill(Color.BLUE);
            //Запоминаем ссылку на текущий квадрат
            last_quad = quad;
            //Добавляем квадрат на полотно
            pane.getChildren().add(quad);

        }

        //Если квадрат обошел сто точек графика
        if (quadStatus >= 100) {
            //То откидываем квадрат назад на нулевую позицию
            quadStatus = 0;
            //Останавливаем таймер
            at.stop();
            //Очищаем последний запомненный квадрат
            pane.getChildren().remove(last_quad);
            //Выходим из функции
            return;
        }
    }

    //Функция генерации кубической кривой из массива точек
    public void CreateCubicCurve() {
        //Объявляем новый вычислитель точек для кривой
        sample.CubicCurve curv = new sample.CubicCurve();

        //Если хоть один текстбокс не заполнен - сбрасываем все текстбоксы
        if (point1x.getText().trim().isEmpty() ||
                point1y.getText().trim().isEmpty() ||
                point2x.getText().trim().isEmpty() ||
                point2y.getText().trim().isEmpty() ||
                point3x.getText().trim().isEmpty() ||
                point3y.getText().trim().isEmpty() ||
                point4x.getText().trim().isEmpty() ||
                point4y.getText().trim().isEmpty()) {
            point1x.setText("");
            point1y.setText("");

            point2x.setText("");
            point2y.setText("");

            point3x.setText("");
            point3y.setText("");

            point4x.setText("");
            point4y.setText("");

            return;
        }

        //Пытаемся вытащить точки из текстбоксов на форме
        curv.point0 = point0;
        curv.point1 = point1;
        curv.point2 = point2;
        curv.point3 = point3;

        //Переменная с первой точкой в массиве точек кривой
        int startnum = 1;
        //В зависимости от выбранной системы счисления создаем кубическую кривую
        if (getSelectedIndexCombobox() == 0) {
            //Вытаскиваем массив точек
            Vec2d[] mass = curv.getCubicCurveCoordinatesMass();
            //Проходимся по всему массиву
            for (int tmp = 0; tmp < mass.length - 3; tmp++) {
                //Если точки существует
                if (mass[startnum] != null || mass[startnum - 1] != null) {
                    //Проводим линию между двумя точками
                    if (mass[startnum].x > crdnsys.getxMax() ||
                            mass[startnum].x < crdnsys.getxMin() ||
                                mass[startnum].y < crdnsys.getyMin() ||
                                    mass[startnum].y > crdnsys.getyMax()){
                        continue;
                    }
                    pane.getChildren().add(new Line(crdnsys.getX0() + (crdnsys.getDx() * mass[startnum - 1].x),
                            crdnsys.getY0() - (crdnsys.getDy() * mass[startnum - 1].y),
                            crdnsys.getX0() + (crdnsys.getDx() * mass[startnum].x),
                            crdnsys.getY0() - (crdnsys.getDy() * mass[startnum].y)));
                    startnum++;
                }
            }
        } else {
            //Коэффициент относительности
            double k = (pane.getWidth() / crdnsys.getMultiСhart()) + (pane.getHeight() / crdnsys.getMultiСhart());
            //Вытаскиваем массив точек
            Vec2d[] mass = curv.getCubicCurveCoordinatesMass();
            for (int tmp = 0; tmp < mass.length - 3; tmp++) {
                //Если точки существуют
                if (mass[startnum] != null || mass[startnum - 1] != null) {
                    //Проводим линию
                    pane.getChildren().add(new Line(crdnsys.getX0() + (mass[startnum - 1].x * k * 1.15),
                            crdnsys.getY0() - (mass[startnum - 1].y * k * 1.15),
                            crdnsys.getX0() + (mass[startnum].x * k * 1.15),
                            crdnsys.getY0() - (mass[startnum].y) * k * 1.15));
                    startnum++;
                }
            }
        }
    }

    //Таймер с вызовом функции отрисовки квадрата каждый тик
    protected AnimationTimer at = new AnimationTimer() {

        @Override
        public void handle(long l) {
            QuadDraw();
        }
    };
}

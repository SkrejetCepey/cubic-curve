package sample;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

//Класс, отвечающий за рассчет и отрисовку координатных систем (в данном случае полярной и декартовой)
public class CoordinateSystem {

    private double dx = 0, dy = 0;//Шаг между двумя соседними значениями на осях x и y в области приложения(px)
    private double x0 = 0, y0 = 0;//Центры осей x и y (Координаты x и y будут указывать на середину окна)

    private Pane pane;//Площадка, на которой будет отрисована система

    //Переменные для масштабирования полярной системы координат
    double coinNum = 0;//Количество колец, которые будут отрисованы на интерфейсе
    double coinDistance = 0;//Диаметр кольца
    double coinStep = 0;//Шаг между кольцами
    int multiDeg = 10;//Множитель, влияющий на дальность отрисовки подписей градусных осей
    int multiСhart = 0;//Множитель, влияющий на масштаб графика

    //Переменные краев полотна, на котором идет отрисовка
    private int xMax = 0;
    private int xMin = 0;
    private int yMax = 0;
    private int yMin = 0;

    //Глобальный шрифт
    private Font f = new Font("Arial", 9);

    //Конструктор, который принимает ссылку на полотно
    CoordinateSystem(Pane pane) {
        this.pane = pane;
    }

    //Далее определяются геттеры

    public double getX0() {
        return x0;
    }

    public double getY0() {
        return y0;
    }

    public double getDx() {
        return dx;
    }

    public double getDy() {
        return dy;
    }

    public int getxMax(){return xMax;}

    public int getxMin(){return xMin;}

    public int getyMin(){return yMin;}

    public int getyMax(){return yMax;}

    //Переменная множителя полярной системы
    public int getMultiСhart(){
        return multiСhart;
    }

    //Отрисовка декартовой системы
    public void DrawDescartes() {
        //Очищаем полотно на всякий случай
        pane.getChildren().clear();

        //Вычисления
        dx = pane.getWidth() / (xMax - xMin);//Высчитываем шаг между двумя соседними единицами измерения для оси x
        dy = pane.getHeight() / (yMax - yMin);//Высчитываем шаг между двумя соседними единицами измерения для оси y
        x0 = -dx * xMin;//Высчитываем середину длины окна приложения
        y0 = dy * yMax;//Высчитываем середину ширины окна приложения

        Scale(0);//масштабирование (0 - декартовая система)

        //Ось x
        Line axisX = new Line(0, y0, pane.getWidth(), y0);
        axisX.setStrokeLineCap(StrokeLineCap.SQUARE);//Квадратные концы
        axisX.setSmooth(true);//on anti-aliasing
        axisX.setStrokeWidth(2);//Толщина

        pane.getChildren().add(axisX);// ось абсцисс

        //Ось y
        Line axisY = new Line(x0, pane.getHeight(), x0, 0);
        axisY.setStrokeLineCap(StrokeLineCap.SQUARE);//Квадратные концы
        axisY.setSmooth(true);//on anti-aliasing
        axisY.setStrokeWidth(2);//Толщина

        pane.getChildren().add(axisY);// ось ординат

        for (int xe = xMin; xe <= xMax; xe++)//сетка вертикальная
        {
            Line tmp = new Line(x0 + xe * dx, 0, x0 + xe * dx, pane.getHeight());
            tmp.setStroke(Color.LIGHTSLATEGRAY);
            tmp.setSmooth(true);//on anti-aliasing
            tmp.setStrokeWidth(1);//Толщина
            pane.getChildren().add(tmp);
        }

        for (int ye = yMin; ye <= yMax; ye++)//сетка горизонтальная
        {
            Line tmp = new Line(0, y0 - ye * dy, pane.getWidth(), y0 - ye * dy);
            tmp.setStroke(Color.LIGHTSLATEGRAY);
            tmp.setSmooth(true);//on anti-aliasing
            tmp.setStrokeWidth(1);//Толщина
            pane.getChildren().add(tmp);
        }


        for (double xe = xMin; xe <= xMax; xe++)//подписываем иксы
        {
            Text numeratorText = new Text(x0 + xe * dx + 5, pane.getHeight() / 2 - 3, String.valueOf((int) xe));

            numeratorText.setFont(f);//Устанавливаем шрифт
            pane.getChildren().add(numeratorText);
        }

        double countkek = 1;//Изменение переменной, которая используется для отрисовки подписей(Используется ее значение)
        double countkek1 = -1;//Изменение переменной, которая используется для отрисовки подписей(Используется ее значение)

        for (double ye = yMin; ye <= yMax; ye++)//подписываем положительные игреки
        {
            Text numeratorText = new Text(x0 + 5, y0 - dy - (y0 + ye * dy) + 15, String.valueOf((int) countkek));
            Text numeratorText1 = new Text(x0 + 5, y0 + dy + (y0 + ye * dy) - 5, String.valueOf((int) countkek1));

            numeratorText.setFont(f);//Устанавливаем шрифт
            numeratorText1.setFont(f);//Устанавливаем шрифт

            pane.getChildren().add(numeratorText);
            pane.getChildren().add(numeratorText1);

            countkek++;//Изменение переменной, которая используется для отрисовки подписей(Используется ее значение)
            countkek1--;//Изменение переменной, которая используется для отрисовки подписей(Используется ее значение)
        }
    }

    //Отрисовка полярной системы
    public void DrawPolar() {
        //Чистим на всякий случай
        pane.getChildren().clear();

        //Вычисления

        dx = pane.getWidth() / (xMax - xMin);//Высчитываем шаг между двумя соседними единицами измерения для оси x
        dy = pane.getHeight() / (yMax - yMin);//Высчитываем шаг между двумя соседними единицами измерения для оси y
        x0 = -dx * xMin;//Высчитываем середину длины окна приложения
        y0 = dy * yMax;//Высчитываем середину ширины окна приложения

        for (int k = 0; k <= 11; k++)//Постройка прямых под каждые 30 градусов
        {
            double tmp1 = (x0 + (int) ((pane.getWidth() / 2 + pane.getHeight() / 2) * Math.sin(Math.PI * k / 6)));
            double tmp2 = (y0 - (int) ((pane.getWidth() / 2 + pane.getHeight() / 2) * Math.cos(Math.PI * k / 6)));
            double tmp3 = (x0 - (int) ((pane.getWidth() / 2 + pane.getHeight() / 2) * Math.sin(Math.PI * k / 6)));
            double tmp4 = (y0 + (int) ((pane.getWidth() / 2 + pane.getHeight() / 2) * Math.cos(Math.PI * k / 6)));

            Line tmp = new Line(tmp1, tmp2, tmp3, tmp4);
            tmp.setStroke(Color.LIGHTSLATEGRAY);
            tmp.setSmooth(true);//on anti-aliasing
            tmp.setStrokeWidth(1);
            pane.getChildren().add(tmp);
        }
        int countkek = 330;//Перменная, необходимая для подписей
        for (int k = 4; k <= 14; k++)//Подписи для прямых каждые 30 градусов
        {
            double tmp1 = (x0 + (int) ((pane.getWidth() / multiDeg + pane.getHeight() / multiDeg) * Math.sin(Math.PI * k / 6)));
            double tmp2 = (y0 - (int) ((pane.getWidth() / multiDeg + pane.getHeight() / multiDeg) * Math.cos(Math.PI * k / 6)));

            Text numeratorText = new Text(tmp1, tmp2, String.valueOf((int) countkek) + '°');
            pane.getChildren().add(numeratorText);

            countkek -= 30;
        }

        // ось абсцисс
        countkek = 1;
        Scale(1);//Масштабирование ( 1 - полярная система)
        for (double CountCoin = coinDistance; CountCoin <= coinNum; CountCoin += (float) coinStep)//Функция для рисования колец
        {
            Ellipse ellipse = new Ellipse(pane.getWidth() / 2 , pane.getHeight()/ 2, CountCoin/2, CountCoin/2);//Кольцо
            ellipse.setStroke(Color.BLACK);
            ellipse.setFill(null);//Только контур
            pane.getChildren().add(ellipse);

            Text numeratorText = new Text(x0 + CountCoin / 2, pane.getHeight() / 2, String.valueOf((int) countkek));
            pane.getChildren().add(numeratorText);

            countkek++;
        }
    }

    public void Scale(int indx)//Функция масштабирования для всех систем
    {
        /*
        Системы масштабируются по заготовленным и просчитанным шаблонам!
         */

        //Каст к рутовскому полотно (+UI)
        AnchorPane rootPane = (AnchorPane) pane.getParent();

        if (indx == 0)//для DecartSys(Декартовой системы координат)
        {
            if (rootPane.getHeight() + rootPane.getWidth() <= 640)//Считаем периметр окна приложения и сравниваем с условием
            {
                xMin = -2;//Установка минимального значения зоны отрисовки по x
                xMax = 2;//Установка максимального значения зоны отрисовки по x
                yMin = -2;//Установка минимального значения зоны отрисовки по y
                yMax = 2;//Установка максимального значения зоны отрисовки по y
                f = new Font("Arial", 13);//Установка фонта
            } else if (rootPane.getHeight() + rootPane.getWidth() > 640 && rootPane.getHeight() + rootPane.getWidth() <= 960)//Считаем периметр окна приложения и сравниваем с условием
            {
                xMin = -4;//Установка минимального значения зоны отрисовки по x
                xMax = 4;//Установка максимального значения зоны отрисовки по x
                yMin = -4;//Установка минимального значения зоны отрисовки по y
                yMax = 4;//Установка максимального значения зоны отрисовки по y
                f = new Font("Arial", 12);//Установка фонта
            } else if (rootPane.getHeight() + rootPane.getWidth() > 960 && rootPane.getHeight() + rootPane.getWidth() <= 1440)//Считаем периметр окна приложения и сравниваем с условием
            {
                xMin = -6;//Установка минимального значения зоны отрисовки по x
                xMax = 6;//Установка максимального значения зоны отрисовки по x
                yMin = -6;//Установка минимального значения зоны отрисовки по y
                yMax = 6;//Установка максимального значения зоны отрисовки по y
                f = new Font("Arial", 11);//Установка фонта
            } else if (rootPane.getHeight() + rootPane.getWidth() > 1440 && rootPane.getHeight() + rootPane.getWidth() <= 2160)//Считаем периметр окна приложения и сравниваем с условием
            {
                xMin = -8;//Установка минимального значения зоны отрисовки по x
                xMax = 8;//Установка максимального значения зоны отрисовки по x
                yMin = -8;//Установка минимального значения зоны отрисовки по y
                yMax = 8;//Установка максимального значения зоны отрисовки по y
                f = new Font("Arial", 10);//Установка фонта
            } else if (rootPane.getHeight() + rootPane.getWidth() > 2160 && rootPane.getHeight() + rootPane.getWidth() <= 3240)//Считаем периметр окна приложения и сравниваем с условием
            {
                xMin = -10;//Установка минимального значения зоны отрисовки по x
                xMax = 10;//Установка максимального значения зоны отрисовки по x
                yMin = -10;//Установка минимального значения зоны отрисовки по y
                yMax = 10;//Установка максимального значения зоны отрисовки по y
                f = new Font("Arial", 9);//Установка фонта
                //was default settings
            }
        }
        if (indx == 1)//для PolarSys
        {
            if (pane.getHeight() + pane.getWidth() <= 800)//Считаем периметр окна приложения и сравниваем с условием
            {
                coinNum = ((pane.getWidth() / 2) + (pane.getHeight() / 2)) / 1;//Установка количества колец, которые будут отрисованы
                coinDistance = ((pane.getWidth() / 10) + (pane.getHeight() / 10)) / 0.5;//Установка диаметра кольца
                coinStep = coinDistance;//Установка шага между кольцами
                multiDeg = 5;//Установка множителя, влияющего на дальность отрисовки подписей градусных осей
                multiСhart = 12;//Установка множителя, влияющего на масштаб графика
                f = new Font("Arial", 10);//Установка нового фонта
            } else if (pane.getHeight() + pane.getWidth() > 800 && pane.getHeight() + pane.getWidth() <= 1200)//Считаем периметр окна приложения и сравниваем с условием
            {
                coinNum = ((pane.getWidth() / 2) + (pane.getHeight() / 2)) / 1.1;//Установка количества колец, которые будут отрисованы
                coinDistance = ((pane.getWidth() / 10) + (pane.getHeight() / 10)) / 1;//Установка диаметра кольца
                coinStep = coinDistance;//Установка шага между кольцами
                multiDeg = 6;//Установка множителя, влияющего на дальность отрисовки подписей градусных осей
                multiСhart = 24;//Установка множителя, влияющего на масштаб графика
                f = new Font("Arial", 11);//Установка нового фонта
            } else if (pane.getHeight() + pane.getWidth() > 1200 && pane.getHeight() + pane.getWidth() <= 1600)//Считаем периметр окна приложения и сравниваем с условием
            {
                coinNum = ((pane.getWidth() / 2) + (pane.getHeight() / 2)) / 1.2;//Установка количества колец, которые будут отрисованы
                coinDistance = ((pane.getWidth() / 10) + (pane.getHeight() / 10)) / 1.5;//Установка диаметра кольца
                coinStep = coinDistance;//Установка шага между кольцами
                multiDeg = 7;//Установка множителя, влияющего на дальность отрисовки подписей градусных осей
                multiСhart = 36;//Установка множителя, влияющего на масштаб графика
                f = new Font("Arial", 12);//Установка нового фонта
            } else if (pane.getHeight() + pane.getWidth() > 1600 && pane.getHeight() + pane.getWidth() <= 2000)//Считаем периметр окна приложения и сравниваем с условием
            {
                coinNum = ((pane.getWidth() / 2) + (pane.getHeight() / 2)) / 1.3;//Установка количества колец, которые будут отрисованы
                coinDistance = ((pane.getWidth() / 10) + (pane.getHeight() / 10)) / 2;//Установка диаметра кольца
                coinStep = coinDistance;//Установка шага между кольцами
                multiDeg = 8;//Установка множителя, влияющего на дальность отрисовки подписей градусных осей
                multiСhart = 48;//Установка множителя, влияющего на масштаб графика
                f = new Font("Arial", 13);//Установка нового фонта
            } else if (pane.getHeight() + pane.getWidth() > 2000)//Считаем периметр окна приложения и сравниваем с условием
            {
                coinNum = ((pane.getWidth() / 2) + (pane.getHeight() / 2)) / 1.4;//Установка количества колец, которые будут отрисованы
                coinDistance = ((pane.getWidth() / 10) + (pane.getHeight() / 10)) / 2.5;//Установка диаметра кольца
                coinStep = coinDistance;//Установка шага между кольцами
                multiDeg = 9;//Установка множителя, влияющего на дальность отрисовки подписей градусных осей
                multiСhart = 60;//Установка множителя, влияющего на масштаб графика
                f = new Font("Arial", 13);//Установка нового фонта
            }
        }
    }
}

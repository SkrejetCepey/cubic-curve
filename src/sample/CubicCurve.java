package sample;

import com.sun.javafx.geom.Vec2d;

//Биновский класс для рассчета точек кубической кривой
public class CubicCurve {
    public Vec2d point0, point1, point2, point3;
    //Количество точек из которых собирается график
    private int numPoints = 100;
    //Массив точек
    private Vec2d[] positions = new Vec2d[numPoints];

    //Заполнение массива точками
    public Vec2d[] getCubicCurveCoordinatesMass() {
        for (int i = 1; i < numPoints + 1; i++) {
            float t = i / (float) numPoints;
            positions[i - 1] = CalculateCubicBezierPoint(t, point0, point1, point2, point3);
        }
        return positions;
    }

    //Вычисление каждой точки
    private Vec2d CalculateCubicBezierPoint(float t, Vec2d p0, Vec2d p1, Vec2d p2, Vec2d p3) {

        //(1 - t)3 P0 + 3(1 - t)2tP1 + 3(1 - t)t2 P2 + t3P3
        //  uuu  * p0 + 3 * uu  * t * p1 + 3 * u *  tt * p2 + ttt * p3

        float u = 1 - t;
        float tt = t * t;
        float uu = u * u;
        float uuu = uu * u;
        float ttt = tt * t;

        Vec2d p = new Vec2d(uuu * p0.x, uuu * p0.y);

        p.x += 3 * uu * t * p1.x;
        p.y += 3 * uu * t * p1.y;

        p.x += 3 * u * tt * p2.x;
        p.y += 3 * u * tt * p2.y;

        p.x += ttt * p3.x;
        p.y += ttt * p3.y;

        return p;
    }
}

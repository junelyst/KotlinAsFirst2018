@file:Suppress("UNUSED_PARAMETER")
package lesson8.task1

import lesson1.task1.sqr
import kotlin.math.*

/**
 * Точка на плоскости
 */
data class Point(val x: Double, val y: Double) {
    /**
     * Пример
     *
     * Рассчитать (по известной формуле) расстояние между двумя точками
     */
    fun distance(other: Point): Double = sqrt(sqr(x - other.x) + sqr(y - other.y))
}

/**
 * Треугольник, заданный тремя точками (a, b, c, см. constructor ниже).
 * Эти три точки хранятся в множестве points, их порядок не имеет значения.
 */
class Triangle private constructor(private val points: Set<Point>) {

    private val pointList = points.toList()

    val a: Point get() = pointList[0]

    val b: Point get() = pointList[1]

    val c: Point get() = pointList[2]

    constructor(a: Point, b: Point, c: Point): this(linkedSetOf(a, b, c))
    /**
     * Пример: полупериметр
     */
    fun halfPerimeter() = (a.distance(b) + b.distance(c) + c.distance(a)) / 2.0

    /**
     * Пример: площадь
     */
    fun area(): Double {
        val p = halfPerimeter()
        return sqrt(p * (p - a.distance(b)) * (p - b.distance(c)) * (p - c.distance(a)))
    }

    /**
     * Пример: треугольник содержит точку
     */
    fun contains(p: Point): Boolean {
        val abp = Triangle(a, b, p)
        val bcp = Triangle(b, c, p)
        val cap = Triangle(c, a, p)
        return abp.area() + bcp.area() + cap.area() <= area()
    }

    override fun equals(other: Any?) = other is Triangle && points == other.points

    override fun hashCode() = points.hashCode()

    override fun toString() = "Triangle(a = $a, b = $b, c = $c)"
}

/**
 * Окружность с заданным центром и радиусом
 */
data class Circle(val center: Point, val radius: Double) {
    /**
     * Простая
     *
     * Рассчитать расстояние между двумя окружностями.
     * Расстояние между непересекающимися окружностями рассчитывается как
     * расстояние между их центрами минус сумма их радиусов.
     * Расстояние между пересекающимися окружностями считать равным 0.0.
     */
    fun distance(other: Circle): Double {
        val res = center.distance(other.center) - (other.radius + radius)
        return if (res >= 0) res
        else 0.0
    }

    /**
     * Тривиальная
     *
     * Вернуть true, если и только если окружность содержит данную точку НА себе или ВНУТРИ себя
     */
    fun contains(p: Point): Boolean = sqr(p.x - center.x) + sqr(p.y - center.y) <= sqr(radius)
}

/**
 * Отрезок между двумя точками
 */
data class Segment(val begin: Point, val end: Point) {
    override fun equals(other: Any?) =
            other is Segment && (begin == other.begin && end == other.end || end == other.begin && begin == other.end)

    override fun hashCode() =
            begin.hashCode() + end.hashCode()
}

/**
 * Средняя
 *
 * Дано множество точек. Вернуть отрезок, соединяющий две наиболее удалённые из них.
 * Если в множестве менее двух точек, бросить IllegalArgumentException
 */
fun diameter(vararg points: Point): Segment {
    if (points.size < 2) throw IllegalArgumentException()
    var max = -1.0
    var res = Segment(Point(0.0, 0.0), Point(0.0, 0.0))
    for (i in 0 until points.size) {
        for (k in i + 1 until points.size) {
            if (points[i].distance(points[k]) > max) {
                max = points[i].distance(points[k])
                res = Segment(points[i], points[k])
            }
        }
    }
    return res
}

/**
 * Простая
 *
 * Построить окружность по её диаметру, заданному двумя точками
 * Центр её должен находиться посередине между точками, а радиус составлять половину расстояния между ними
 */
fun circleByDiameter(diameter: Segment): Circle {
    val radius = diameter.begin.distance(diameter.end) / 2
    val x = (diameter.begin.x + diameter.end.x) / 2
    val y = (diameter.begin.y + diameter.end.y) / 2
    return Circle(Point(x, y), radius)
}

/**
 * Прямая, заданная точкой point и углом наклона angle (в радианах) по отношению к оси X.
 * Уравнение прямой: (y - point.y) * cos(angle) = (x - point.x) * sin(angle)
 * или: y * cos(angle) = x * sin(angle) + b, где b = point.y * cos(angle) - point.x * sin(angle).
 * Угол наклона обязан находиться в диапазоне от 0 (включительно) до PI (исключительно).
 */
class Line private constructor(val b: Double, val angle: Double) {
    init {
        require(angle >= 0 && angle < PI) { "Incorrect line angle: $angle" }
    }

    constructor(point: Point, angle: Double): this(point.y * cos(angle) - point.x * sin(angle), angle)

    /**
     * Средняя
     *
     * Найти точку пересечения с другой линией.
     * Для этого необходимо составить и решить систему из двух уравнений (каждое для своей прямой)
     */
    fun crossPoint(other: Line): Point {
        val y = (other.b * sin(this.angle) - this.b * sin(other.angle)) / sin(this.angle - other.angle)
        val x = if (this.angle != 0.0) (y * cos(this.angle) - this.b) / sin(this.angle)
        else (y * cos(other.angle) - other.b) / sin(other.angle)
        return Point(x, y)
    }

    override fun equals(other: Any?) = other is Line && angle == other.angle && b == other.b

    override fun hashCode(): Int {
        var result = b.hashCode()
        result = 31 * result + angle.hashCode()
        return result
    }

    override fun toString() = "Line(${cos(angle)} * y = ${sin(angle)} * x + $b)"
}

/**
 * Средняя
 *
 * Построить прямую по отрезку
 */
fun lineBySegment(s: Segment): Line {
    val hyp = s.begin.distance(s.end)
    val cath = s.end.distance(Point(s.end.x, s.begin.y))
    var ang = asin(cath / hyp)
    val a = s.begin
    val b = s.end
    if ((a.x > b.x) && (a.y < b.y) || (a.x < b.x) && (a.y > b.y))
        ang = PI - ang
    if (ang == PI) ang = 0.0
    return Line(s.begin, ang)
}

/**
 * Средняя
 *
 * Построить прямую по двум точкам
 */
fun lineByPoints(a: Point, b: Point): Line {
    val hyp = a.distance(b)
    val cath = b.distance(Point(b.x, a.y))
    var ang = asin(cath / hyp)
    if ((a.x > b.x) && (a.y < b.y) || (a.x < b.x) && (a.y > b.y))
        ang = PI - ang
    if (ang == PI) ang = 0.0
    return Line(a, ang)
}

/**
 * Сложная
 *
 * Построить серединный перпендикуляр по отрезку или по двум точкам
 */
fun bisectorByPoints(a: Point, b: Point): Line {
    val center = Point((a.x + b.x) / 2, (a.y + b.y) / 2)
    val hyp = a.distance(b)
    val cath = b.distance(Point(b.x, a.y))
    val ang = asin(cath / hyp) * 180.0 / PI
    var ang2 = 0.0
    val line = lineBySegment(Segment (a, b))
    val k = line.angle * 180.0 / PI
    ang2 = if (k > 90)
        90 - ang
    else 180 - (90 - ang)

    ang2 = if (ang2 * PI / 180 == PI) 0.0
    else ang2 * PI / 180
    return Line(center, ang2)
}

/**
 * Средняя
 *
 * Задан список из n окружностей на плоскости. Найти пару наименее удалённых из них.
 * Если в списке менее двух окружностей, бросить IllegalArgumentException
 */
fun findNearestCirclePair(vararg circles: Circle): Pair<Circle, Circle> {
    if (circles.size < 2) throw IllegalArgumentException()
    var min = Double.POSITIVE_INFINITY
    var res = Pair(circles[0], circles[0])
    for (i in 0 until circles.size) {
        for (k in i + 1 until circles.size) {
            if (circles[i].distance(circles[k]) < min) {
                min = circles[i].distance(circles[k])
                res = Pair(circles[i], circles[k])
            }
        }
    }
    return res
}

/**
 * Сложная
 *
 * Дано три различные точки. Построить окружность, проходящую через них
 * (все три точки должны лежать НА, а не ВНУТРИ, окружности).
 * Описание алгоритмов см. в Интернете
 * (построить окружность по трём точкам, или
 * построить окружность, описанную вокруг треугольника - эквивалентная задача).
 */
fun circleByThreePoints(a: Point, b: Point, c: Point): Circle {
    val bisector1 = bisectorByPoints(a, b)
    val bisector2 = bisectorByPoints(b, c)
    val center = bisector1.crossPoint(bisector2)
    val radius = a.distance(center)
    return Circle(center, radius)
}

/**
 * Очень сложная
 *
 * Дано множество точек на плоскости. Найти круг минимального радиуса,
 * содержащий все эти точки. Если множество пустое, бросить IllegalArgumentException.
 * Если множество содержит одну точку, вернуть круг нулевого радиуса с центром в данной точке.
 *
 * Примечание: в зависимости от ситуации, такая окружность может либо проходить через какие-либо
 * три точки данного множества, либо иметь своим диаметром отрезок,
 * соединяющий две самые удалённые точки в данном множестве.
 */
fun minContainingCircle(vararg points: Point): Circle {
    if (points.isEmpty()) throw IllegalArgumentException()
    if (points.size == 1) return Circle(points[0], 0.0)
    var res = Circle(points[0], 0.0)
    val diam = diameter(*points)
    res = circleByDiameter(diam)
    var b = false
    for (element in points) {
        if (!res.contains(element)) b = true
    }
    if (b) {
        val c = res.center
        var max = -1.0
        var p3 = Point(0.0, 0.0)
        for (element in points) {
            if (c.distance(element) > max) {
                max = c.distance(element)
                p3 = element
            }
        }
        res = circleByThreePoints(diam.begin, diam.end, p3)
    }
    return res
}


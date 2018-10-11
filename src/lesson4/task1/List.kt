@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson4.task1

import lesson1.task1.discriminant
import kotlin.math.sqrt
import kotlin.math.pow
import lesson3.task1.isPrime

/**
 * Пример
 *
 * Найти все корни уравнения x^2 = y
 */
fun sqRoots(y: Double) =
        when {
            y < 0 -> listOf()
            y == 0.0 -> listOf(0.0)
            else -> {
                val root = sqrt(y)
                // Результат!
                listOf(-root, root)
            }
        }

/**
 * Пример
 *
 * Найти все корни биквадратного уравнения ax^4 + bx^2 + c = 0.
 * Вернуть список корней (пустой, если корней нет)
 */
fun biRoots(a: Double, b: Double, c: Double): List<Double> {
    if (a == 0.0) {
        return if (b == 0.0) listOf()
        else sqRoots(-c / b)
    }
    val d = discriminant(a, b, c)
    if (d < 0.0) return listOf()
    if (d == 0.0) return sqRoots(-b / (2 * a))
    val y1 = (-b + sqrt(d)) / (2 * a)
    val y2 = (-b - sqrt(d)) / (2 * a)
    return sqRoots(y1) + sqRoots(y2)
}

/**
 * Пример
 *
 * Выделить в список отрицательные элементы из заданного списка
 */
fun negativeList(list: List<Int>): List<Int> {
    val result = mutableListOf<Int>()
    for (element in list) {
        if (element < 0) {
            result.add(element)
        }
    }
    return result
}

/**
 * Пример
 *
 * Изменить знак для всех положительных элементов списка
 */
fun invertPositives(list: MutableList<Int>) {
    for (i in 0 until list.size) {
        val element = list[i]
        if (element > 0) {
            list[i] = -element
        }
    }
}

/**
 * Пример
 *
 * Из имеющегося списка целых чисел, сформировать список их квадратов
 */
fun squares(list: List<Int>) = list.map { it * it }

/**
 * Пример
 *
 * Из имеющихся целых чисел, заданного через vararg-параметр, сформировать массив их квадратов
 */
fun squares(vararg array: Int) = squares(array.toList()).toTypedArray()

/**
 * Пример
 *
 * По заданной строке str определить, является ли она палиндромом.
 * В палиндроме первый символ должен быть равен последнему, второй предпоследнему и т.д.
 * Одни и те же буквы в разном регистре следует считать равными с точки зрения данной задачи.
 * Пробелы не следует принимать во внимание при сравнении символов, например, строка
 * "А роза упала на лапу Азора" является палиндромом.
 */
fun isPalindrome(str: String): Boolean {
    val lowerCase = str.toLowerCase().filter { it != ' ' }
    for (i in 0..lowerCase.length / 2) {
        if (lowerCase[i] != lowerCase[lowerCase.length - i - 1]) return false
    }
    return true
}

/**
 * Пример
 *
 * По имеющемуся списку целых чисел, например [3, 6, 5, 4, 9], построить строку с примером их суммирования:
 * 3 + 6 + 5 + 4 + 9 = 27 в данном случае.
 */
fun buildSumExample(list: List<Int>) = list.joinToString(separator = " + ", postfix = " = ${list.sum()}")

/**
 * Простая
 *
 * Найти модуль заданного вектора, представленного в виде списка v,
 * по формуле abs = sqrt(a1^2 + a2^2 + ... + aN^2).
 * Модуль пустого вектора считать равным 0.0.
 */
fun abs(v: List<Double>): Double {
    var abs = 0.0
    for (i in 0 until v.size) {
      abs += v[i] * v[i]
    }
    return sqrt (abs)
}

/**
 * Простая
 *
 * Рассчитать среднее арифметическое элементов списка list. Вернуть 0.0, если список пуст
 */
fun mean(list: List<Double>): Double {
    return when {
        list.isNotEmpty() -> list.sum() / list.size
        else -> 0.0
    }
}

/**
 * Средняя
 *
 * Центрировать заданный список list, уменьшив каждый элемент на среднее арифметическое всех элементов.
 * Если список пуст, не делать ничего. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun center(list: MutableList<Double>): MutableList<Double> {
    val num = list.sum() / list.size
    if (list.isEmpty()) return list
    else for (i in 0 until list.size) {
        list [i] -= num
    }
    return list
}

/**
 * Средняя
 *
 * Найти скалярное произведение двух векторов равной размерности,
 * представленные в виде списков a и b. Скалярное произведение считать по формуле:
 * C = a1b1 + a2b2 + ... + aNbN. Произведение пустых векторов считать равным 0.0.
 */
fun times(a: List<Double>, b: List<Double>): Double {
    val result = mutableListOf<Double>()
    if (a.isEmpty() || b.isEmpty()) return 0.0
    else for (i in 0 until a.size) {
        result.add (a[i] * b[i])
    }
    return result.sum()
}

/**
 * Средняя
 *
 * Рассчитать значение многочлена при заданном x:
 * p(x) = p0 + p1*x + p2*x^2 + p3*x^3 + ... + pN*x^N.
 * Коэффициенты многочлена заданы списком p: (p0, p1, p2, p3, ..., pN).
 * Значение пустого многочлена равно 0.0 при любом x.
 */
fun polynom(p: List<Double>, x: Double): Double {
    var pol = 0.0
    if (p.isEmpty()) return 0.0
    else for (i in 0 until p.size) {
        pol += p[i] * Math.pow (x, i.toDouble())
    }
    return pol
}

/**
 * Средняя
 *
 * В заданном списке list каждый элемент, кроме первого, заменить
 * суммой данного элемента и всех предыдущих.
 * Например: 1, 2, 3, 4 -> 1, 3, 6, 10.
 * Пустой список не следует изменять. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun accumulate(list: MutableList<Double>): MutableList<Double> {
    var sum = 0.0
    var k = 0.0
    if (list.isEmpty()) return list
    else for (i in 0 until list.size) {
        k = list [i]
        list [i] += sum
        sum += k
    }
    return list
}

/**
 * Средняя
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде списка множителей, например 75 -> (3, 5, 5).
 * Множители в списке должны располагаться по возрастанию.
 */
fun factorize(n: Int): List<Int> {
    var factorize = mutableListOf<Int>()
    var factor = 2
    var num = n
    while (num != 1) {
        if ((num % factor == 0) && (isPrime(factor))) {
            factorize.add(factor)
            num /= factor
        }
        if (num % factor != 0) factor++
    }
    return factorize.sorted()
}

/**
 * Сложная
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде строки, например 75 -> 3*5*5
 * Множители в результирующей строке должны располагаться по возрастанию.
 */
fun factorizeToString(n: Int): String  {
    var factorize = mutableListOf<Int>()
    var factor = 2
    var num = n
    if (isPrime(n)) return "$n"
    while (num != 1) {
        if ((num % factor == 0) && (isPrime(factor))) {
            factorize.add(factor)
            num /= factor
        }
        if (num % factor != 0) factor++
    }
    return factorize.sorted().joinToString(separator = "*")
}

/**
 * Средняя
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием base > 1.
 * Результат перевода вернуть в виде списка цифр в base-ичной системе от старшей к младшей,
 * например: n = 100, base = 4 -> (1, 2, 1, 0) или n = 250, base = 14 -> (1, 3, 12)
 */
fun convert(n: Int, base: Int): List<Int> {
    var result = mutableListOf<Int>()
    var num = n
    while (num != 0) {
        result.add (num % base)
        num /= base
    }
    var resultSort = mutableListOf<Int>()
    for (i in 0 until result.size) {
        resultSort.add (result [result.size - i - 1])
    }
    return resultSort
}

/**
 * Сложная
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием 1 < base < 37.
 * Результат перевода вернуть в виде строки, цифры более 9 представлять латинскими
 * строчными буквами: 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: n = 100, base = 4 -> 1210, n = 250, base = 14 -> 13c
 */
fun convertToString(n: Int, base: Int): String {
    var letters = ""
    var x = 'a'
    var number = ""
    var b = 0
    var num = n
    while ((base > 9) && (b + 9 != base)) {
        letters += "$x"
        x++
        b++
    }
    if (base < 10) return convert (n, base).joinToString(separator = "")
    else while (num != 0) {
        if (num % base < 10) number += (num % base).toString()
        else number += letters [num % base - 10]
        num /= base
    }
    var result = ""
    for (i in 0 until number.length) {
        result += number [number.length - i - 1]
    }
    return result
}

/**
 * Средняя
 *
 * Перевести число, представленное списком цифр digits от старшей к младшей,
 * из системы счисления с основанием base в десятичную.
 * Например: digits = (1, 3, 12), base = 14 -> 250
 */
fun decimal(digits: List<Int>, base: Int): Int {
    var result = 0.0
    for (i in 0 until digits.size) {
        result += digits [i] * base.toDouble().pow (digits.size - i - 1)
    }
    return result.toInt()
}

/**
 * Сложная
 *
 * Перевести число, представленное цифровой строкой str,
 * из системы счисления с основанием base в десятичную.
 * Цифры более 9 представляются латинскими строчными буквами:
 * 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: str = "13c", base = 14 -> 250
 */
fun decimalFromString(str: String, base: Int): Int {
    var letters = ""
    var x = 'a'
    var b = 0
    var count =  mutableListOf<Int>()
    while ((base > 9) && (b + 10 != base)) {
        letters += "$x"
        x++
        b++
    }
    for (i in 0 until str.length) {
        if (str[i] in letters) count.add (letters.indexOf(str [i], 0) + 10)
        else count.add (str.substring (i, i + 1).toInt())
    }
    return decimal (count, base)
}

/**
 * Сложная
 *
 * Перевести натуральное число n > 0 в римскую систему.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: 23 = XXIII, 44 = XLIV, 100 = C
 */
fun roman(n: Int): String {
    var num = n
    var res = ""
    if (num >= 1000)  {
        res += "M".repeat(num / 1000)
        num %= 1000
    }
    when {
        num / 100 == 9 -> {
            res += "CM"
            num %= 100
        }
        num / 100 in 5..8 -> {
            res += "D" + "C".repeat(num / 100 - 5)
            num %= 100
        }
        num /100 == 4 -> {
            res += "CD"
            num %= 100
        }
        num / 100 in 1..3 -> {
            res += "C".repeat(num / 100)
            num %= 100
        }
    }
    when {
        num / 10 == 9 -> {
            res += "XC"
            num %= 10
        }
        num / 10 in 5..8 -> {
            res += "L" + "X".repeat(num / 10 - 5)
            num %= 10
        }
        num / 10 == 4 -> {
            res += "XL"
            num %= 10
        }
        num / 10 in 1..3 -> {
            res += "X".repeat(num / 10)
            num %= 10
        }
    }
    when {
        num == 9 -> res += "IX"
        num in 5..8 -> res += "V" + "I".repeat(num - 5)
        num == 4 -> res += "IV"
        num in 1..3 -> res += "I".repeat(num)
    }
    return res
    }

/**
 * Очень сложная
 *
 * Записать заданное натуральное число 1..999999 прописью по-русски.
 * Например, 375 = "триста семьдесят пять",
 * 23964 = "двадцать три тысячи девятьсот шестьдесят четыре"
 */
fun russian(n: Int): String {
    val dig = listOf<String>("-", "один", "два", "три", "четыре", "пять", "шесть", "семь", "восемь", "девять")
    val num = listOf<String>("десять", "одиннадцать", "двенадцать", "тринадцать", "четырнадцать",
            "пятнадцать", "шестнадцать", "семнадцать", "восемнадцать", "девятнадцать")
    val ten = listOf<String>("-", "-", "двадцать", "тридцать", "сорок", "пятьдесят", "шестьдесят",
            "семьдесят", "восемьдесят", "девяносто")
    val hund = listOf<String>("-", "сто", "двести", "триста", "четыреста", "пятьсот", "шестьсот",
            "семьсот", "восемьсот", "девятьсот")
    val th = listOf<String>("-", "одна", "две",  "три", "четыре", "пять", "шесть", "семь", "восемь", "девять")
    var res = mutableListOf<String>()
    var part1 = n / 1000
    var part2 = n % 1000
    if (n in 1..9) return dig [n]
    if (n in 10..19) return num [n - 10]
        if (part1 / 100 > 0) {
            if (part1 % 100 == 0) res.add (hund [part1 / 100] + " тысяч")
            else res.add (hund [part1 / 100])
            part1 %= 100
            }
        if (part1 in 10..19) {
                res.add (num [part1 - 10] + " тысяч")
                part1 = -1
            }
        if (part1 / 10 > 1) {
                res.add (ten [part1 / 10])
                part1 %= 10
            }
        if (part1 in 1..9) res.add (th [part1] + " тысячи")
        if (part2 / 100 > 0)  {
            res.add (hund [part2 / 100])
            part2 %= 100
        }
        if (part2 / 10 > 1)  {
            res.add (ten [part2 / 10])
            part2 %= 10
        }
        if (part2 in 1..9)  res.add (dig [part2])
    return res.joinToString (separator = " ")
}
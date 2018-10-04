@file:Suppress("UNUSED_PARAMETER")
package lesson3.task1

import kotlin.math.sqrt

/**
 * Пример
 *
 * Вычисление факториала
 */
fun factorial(n: Int): Double {
    var result = 1.0
    for (i in 1..n) {
        result = result * i // Please do not fix in master
    }
    return result
}

/**
 * Пример
 *
 * Проверка числа на простоту -- результат true, если число простое
 */
fun isPrime(n: Int): Boolean {
    if (n < 2) return false
    if (n == 2) return true
    if (n % 2 == 0) return false
    for (m in 3..sqrt(n.toDouble()).toInt() step 2) {
        if (n % m == 0) return false
    }
    return true
}

/**
 * Пример
 *
 * Проверка числа на совершенность -- результат true, если число совершенное
 */
fun isPerfect(n: Int): Boolean {
    var sum = 1
    for (m in 2..n/2) {
        if (n % m > 0) continue
        sum += m
        if (sum > n) break
    }
    return sum == n
}

/**
 * Пример
 *
 * Найти число вхождений цифры m в число n
 */
fun digitCountInNumber(n: Int, m: Int): Int =
        when {
            n == m -> 1
            n < 10 -> 0
            else -> digitCountInNumber(n / 10, m) + digitCountInNumber(n % 10, m)
        }

/**
 * Тривиальная
 *
 * Найти количество цифр в заданном числе n.
 * Например, число 1 содержит 1 цифру, 456 -- 3 цифры, 65536 -- 5 цифр.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun digitNumber(n: Int): Int {
    var number = n
    var count = 0
    do {
        number /= 10
        count++
    } while (number > 0)
    return count
}

/**
 * Простая
 *
 * Найти число Фибоначчи из ряда 1, 1, 2, 3, 5, 8, 13, 21, ... с номером n.
 * Ряд Фибоначчи определён следующим образом: fib(1) = 1, fib(2) = 1, fib(n+2) = fib(n) + fib(n+1)
 */
fun fib(n: Int): Int {
    var fb1 = 1
    var fb2 = 1
    var fib = 1
    for (i in 1..(n - 2)) {
        fb1 = fb2
        fb2 = fib
        fib = fb1 + fb2
    }
    return fib
    }

/**
 * Простая
 *
 * Для заданных чисел m и n найти наименьшее общее кратное, то есть,
 * минимальное число k, которое делится и на m и на n без остатка
 */
fun lcm(m: Int, n: Int): Int {
    var k = 0
    for (i in Math.min(m, n)..m * n + 1) {
        if (i % m == 0 && i % n == 0) {
            k = i
            break
        }
    }
      return k
}

/**
 * Простая
 *
 * Для заданного числа n > 1 найти минимальный делитель, превышающий 1
 */
fun minDivisor(n: Int): Int {
    var k = 0
    for (i in 2..n) {
        if (n % i == 0) {
          k = i
          break
        }
    }
    return k
}

/**
 * Простая
 *
 * Для заданного числа n > 1 найти максимальный делитель, меньший n
 */
fun maxDivisor(n: Int): Int {
    var k = 0
    for (i in n - 1 downTo 1) {
        if (n % i == 0) {
            k = i
            break
        }
    }
    return k
}

/**
 * Простая
 *
 * Определить, являются ли два заданных числа m и n взаимно простыми.
 * Взаимно простые числа не имеют общих делителей, кроме 1.
 * Например, 25 и 49 взаимно простые, а 6 и 8 -- нет.
 */
fun isCoPrime(m: Int, n: Int): Boolean {
    var i = 1
    do {
        i++
        if ((m % i == 0) && (n % i == 0)) return false
    } while (i <= Math.min (m, n))
    return true
}

/**
 * Простая
 *
 * Для заданных чисел m и n, m <= n, определить, имеется ли хотя бы один точный квадрат между m и n,
 * то есть, существует ли такое целое k, что m <= k*k <= n.
 * Например, для интервала 21..28 21 <= 5*5 <= 28, а для интервала 51..61 квадрата не существует.
 */
fun squareBetweenExists(m: Int, n: Int): Boolean {
    var sq = 0
    for (i in m..n) {
        sq = sqrt (i.toDouble()).toInt()
        if (sq * sq == i) return true
    }
    return false
}

/**
 * Средняя
 *
 * Гипотеза Коллатца. Рекуррентная последовательность чисел задана следующим образом:
 *
 *   ЕСЛИ (X четное)
 *     Xслед = X /2
 *   ИНАЧЕ
 *     Xслед = 3 * X + 1
 *
 * например
 *   15 46 23 70 35 106 53 160 80 40 20 10 5 16 8 4 2 1 4 2 1 4 2 1 ...
 * Данная последовательность рано или поздно встречает X == 1.
 * Написать функцию, которая находит, сколько шагов требуется для
 * этого для какого-либо начального X > 0.
 */
fun collatzSteps(x: Int): Int {
    var count = 0
    var num = x
    while (num != 1) {
        if (num % 2 == 0) num /= 2
        else num = 3 * num + 1
        count++
    }
    return count
}

/**
 * Средняя
 *
 * Для заданного x рассчитать с заданной точностью eps
 * sin(x) = x - x^3 / 3! + x^5 / 5! - x^7 / 7! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю
 */
 fun sin(x: Double, eps: Double): Double {
    var res = 2.0
    var count = 0
    var sin = 0.0
    val angle = x % (2 * Math.PI)
        for (i in 1..Int.MAX_VALUE step 2) {
            if (Math.abs (res) >= eps) {
                res = Math.pow(angle, i.toDouble()) / factorial (i)
                count++
                if (count % 2 == 0) res = -res
                sin += res
            }
            else break
        }
    return sin
 }

/**
 * Средняя
 *
 * Для заданного x рассчитать с заданной точностью eps
 * cos(x) = 1 - x^2 / 2! + x^4 / 4! - x^6 / 6! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю
 */
fun cos(x: Double, eps: Double): Double {
    var res = 2.0
    var count = 0
    var cos = 0.0
    val angle = x % (2 * Math.PI)
    for (i in 0..Int.MAX_VALUE step 2) {
        if (Math.abs (res) >= eps) {
            res = Math.pow(angle, i.toDouble()) / factorial (i)
            count++
            if (count % 2 == 0) res = -res
            cos += res
        }
        else break
    }
    return cos
}

/**
 * Средняя
 *
 * Поменять порядок цифр заданного числа n на обратный: 13478 -> 87431.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun revert(n: Int): Int {
    var num = n
    var count = 0.0
    var x = 0
    var res = 0.0
    while (num > 0){
        num /= 10
        count++
    }
    if (count == 0.0) return n
        else {
        for (i in 1..count.toInt()) {
            x = (n / Math.pow (10.0, count - i)).toInt() % 10
            res += x * Math.pow ( 10.0, i.toDouble() - 1)
        }
    }
    return res.toInt()
}

/**
 * Средняя
 *
 * Проверить, является ли заданное число n палиндромом:
 * первая цифра равна последней, вторая -- предпоследней и так далее.
 * 15751 -- палиндром, 3653 -- нет.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun isPalindrome(n: Int): Boolean {
    var number = n
    var count = 0.0
    var i = 1.0
    var num1 = 0
    var num2 = 0
    while (number > 0){
        number /= 10
        count++
    }
    if (count == 1.0) return true
        else {
        while (i < count) {
            num1 = ((n % Math.pow(10.0, i)).toInt()) / Math.pow(10.0, i - 1).toInt()
            num2 = (n / Math.pow(10.0, count - i)).toInt() % 10
            if (num1 != num2) return false
            i++
        }
         return true
    }
}

/**
 * Средняя
 *
 * Для заданного числа n определить, содержит ли оно различающиеся цифры.
 * Например, 54 и 323 состоят из разных цифр, а 111 и 0 из одинаковых.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun hasDifferentDigits(n: Int): Boolean {
    var number = n
    var m = n % 10
    do {
        if (number % 10 != m) return true
        number /= 10
    } while (number > 0)
    return false
}

/**
 * Сложная
 *
 * Найти n-ю цифру последовательности из квадратов целых чисел:
 * 149162536496481100121144...
 * Например, 2-я цифра равна 4, 7-я 5, 12-я 6.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun squareSequenceDigit(n: Int): Int {
    var count = 3.0
    var num = 0.0
    var res = 0
    var sum = 0.0
    var vr = 0
    var i = 1
    var time = 0.0
    var square = 4.0
    if (n < 4) return n * n
    else while (count != n.toDouble()) {
        num = square * square
        vr = num.toInt()
        while (vr > 0){
            vr /= 10
            sum++
        }
        time = sum
        while ((time != 0.0) && (count != n.toDouble())) {
            res = (num / Math.pow (10.0, sum - i) % 10).toInt()
            count++
            i++
            time--
        }
        if (count != n.toDouble()) {
            square++
            sum = 0.0
            i = 1
        } else break
    }
    return res
}

/**
 * Сложная
 *
 * Найти n-ю цифру последовательности из чисел Фибоначчи (см. функцию fib выше):
 * 1123581321345589144...
 * Например, 2-я цифра равна 1, 9-я 2, 14-я 5.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun fibSequenceDigit(n: Int): Int {
    var count = 6.0
    var num = 0.0
    var res = 0
    var sum = 0.0
    var vr = 0
    var i = 1
    var time = 0.0
    var fb = 7
    if (n < 7) return fib (n)
    else while (count != n.toDouble()) {
        num = fib (fb).toDouble()
        vr = num.toInt()
        while (vr > 0){
            vr /= 10
            sum++
        }
        time = sum
        while ((time != 0.0) && (count != n.toDouble())) {
            res = (num / Math.pow (10.0, sum - i) % 10).toInt()
            count++
            i++
            time--
        }
        if (count != n.toDouble()) {
            fb++
            sum = 0.0
            i = 1
        } else break
    }
    return res
}

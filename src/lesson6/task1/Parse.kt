@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson6.task1
import lesson2.task2.daysInMonth

/**
 * Пример
 *
 * Время представлено строкой вида "11:34:45", содержащей часы, минуты и секунды, разделённые двоеточием.
 * Разобрать эту строку и рассчитать количество секунд, прошедшее с начала дня.
 */
fun timeStrToSeconds(str: String): Int {
    val parts = str.split(":")
    var result = 0
    for (part in parts) {
        val number = part.toInt()
        result = result * 60 + number
    }
    return result
}

/**
 * Пример
 *
 * Дано число n от 0 до 99.
 * Вернуть его же в виде двухсимвольной строки, от "00" до "99"
 */
fun twoDigitStr(n: Int) = if (n in 0..9) "0$n" else "$n"

/**
 * Пример
 *
 * Дано seconds -- время в секундах, прошедшее с начала дня.
 * Вернуть текущее время в виде строки в формате "ЧЧ:ММ:СС".
 */
fun timeSecondsToStr(seconds: Int): String {
    val hour = seconds / 3600
    val minute = (seconds % 3600) / 60
    val second = seconds % 60
    return String.format("%02d:%02d:%02d", hour, minute, second)
}

/**
 * Пример: консольный ввод
 */
fun main(args: Array<String>) {
    println("Введите время в формате ЧЧ:ММ:СС")
    val line = readLine()
    if (line != null) {
        val seconds = timeStrToSeconds(line)
        if (seconds == -1) {
            println("Введённая строка $line не соответствует формату ЧЧ:ММ:СС")
        }
        else {
            println("Прошло секунд с начала суток: $seconds")
        }
    }
    else {
        println("Достигнут <конец файла> в процессе чтения строки. Программа прервана")
    }
}


/**
 * Средняя
 *
 * Дата представлена строкой вида "15 июля 2016".
 * Перевести её в цифровой формат "15.07.2016".
 * День и месяц всегда представлять двумя цифрами, например: 03.04.2011.
 * При неверном формате входной строки вернуть пустую строку.
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30.02.2009) считается неверными
 * входными данными.
 */
val months = listOf("января", "февраля", "марта", "апреля", "мая", "июня", "июля",
        "августа", "сентября", "октября", "ноября", "декабря")

fun dateStrToDigit(str: String): String {
    val date = str.split(" ")
    if (date.size != 3) return ""
    try {
        val day = date[0].toInt()
        val month = months.indexOf(date[1]) + 1
        val year = date[2].toInt()
        if ((month == 0) || (day > daysInMonth(month, year)) || (day < 1) || (year < 0)) return ""
        return String.format("%02d.%02d.%d", day, month, year)
    }
    catch (e: NumberFormatException) {
        return ""
    }
}

/**
 * Средняя
 *
 * Дата представлена строкой вида "15.07.2016".
 * Перевести её в строковый формат вида "15 июля 2016".
 * При неверном формате входной строки вернуть пустую строку
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30 февраля 2009) считается неверными
 * входными данными.
 */
fun dateDigitToStr(digital: String): String {
    val date = digital.split(".")
    try {
        if (date.size != 3) return ""
        val day = date[0].toInt()
        val year = date[2].toInt()
        val m = date[1].toInt()
        if (m !in 1..12) return ""
        val month = months[m - 1]
        if ((month.isEmpty()) || (day > daysInMonth(m, year)) || (day < 1) || (year < 0)) return ""
        return String.format("%d %s %d", day, month, year)
    }
    catch (e: NumberFormatException) {
        return ""
    }
}

/**
 * Средняя
 *
 * Номер телефона задан строкой вида "+7 (921) 123-45-67".
 * Префикс (+7) может отсутствовать, код города (в скобках) также может отсутствовать.
 * Может присутствовать неограниченное количество пробелов и чёрточек,
 * например, номер 12 --  34- 5 -- 67 -98 тоже следует считать легальным.
 * Перевести номер в формат без скобок, пробелов и чёрточек (но с +), например,
 * "+79211234567" или "123456789" для приведённых примеров.
 * Все символы в номере, кроме цифр, пробелов и +-(), считать недопустимыми.
 * При неверном формате вернуть пустую строку
 */
fun flattenPhoneNumber(phone: String): String {
    val str = phone.filter { (it != ' ') && (it != '-') }
    val reg = Regex("""\+*\d+(\(\d+\))?\d*""")
    if ((phone.isEmpty()) || (!str.matches(reg))) return ""
    return str.filter { (it != '(') && (it != ')') }
}

/**
 * Средняя
 *
 * Результаты спортсмена на соревнованиях в прыжках в длину представлены строкой вида
 * "706 - % 717 % 703".
 * В строке могут присутствовать числа, черточки - и знаки процента %, разделённые пробелами;
 * число соответствует удачному прыжку, - пропущенной попытке, % заступу.
 * Прочитать строку и вернуть максимальное присутствующее в ней число (717 в примере).
 * При нарушении формата входной строки или при отсутствии в ней чисел, вернуть -1.
 */
fun bestLongJump(jumps: String): Int {
    val listNum = mutableListOf<Int>()
    for (element in jumps.split(Regex(""" +"""))) {
        if (element.matches(Regex("""\d+"""))) listNum.add(element.toInt())
        else if ((element != "%") && (element != "-")) return -1
    }
    return listNum.max() ?: -1
}

/**
 * Сложная
 *
 * Результаты спортсмена на соревнованиях в прыжках в высоту представлены строкой вида
 * "220 + 224 %+ 228 %- 230 + 232 %%- 234 %".
 * Здесь + соответствует удачной попытке, % неудачной, - пропущенной.
 * Высота и соответствующие ей попытки разделяются пробелом.
 * Прочитать строку и вернуть максимальную взятую высоту (230 в примере).
 * При нарушении формата входной строки вернуть -1.
 */
fun bestHighJump(jumps: String): Int {
    val see = jumps.filter { it != ' ' }
    for (element in see) {
        if ((!element.isDigit()) && (element != '+') && (element != '-') && (element != '%'))
            return -1
    }
    val str = jumps.split(" ")
    var max = -1
    try {
        for (i in 0 until str.size - 1 step 2) {
            val num = str[i].toInt()
            if (('+' in str[i + 1]) && (num > max)) max = num
        }
    }
    catch (e: NumberFormatException) {
        return -1
    }
    return max
 }

/**
 * Сложная
 *
 * В строке представлено выражение вида "2 + 31 - 40 + 13",
 * использующее целые положительные числа, плюсы и минусы, разделённые пробелами.
 * Наличие двух знаков подряд "13 + + 10" или двух чисел подряд "1 2" не допускается.
 * Вернуть значение выражения (6 для примера).
 * Про нарушении формата входной строки бросить исключение IllegalArgumentException
 */
fun plusMinus(expression: String): Int {
    val exp = expression.split(" ")
    if (exp.size % 2 == 0) throw IllegalArgumentException()
    try {
        if (!Regex("\\d+").matches(exp[0])) throw IllegalArgumentException()
        var res = exp[0].toInt()
        for (i in 0 until exp.size - 1 step 2) {
            val num = exp[i + 2].toInt()
            if (num < 0) throw IllegalArgumentException()
            when (exp[i + 1]) {
                "+" -> res += num
                "-" -> res -= num
                else -> throw IllegalArgumentException()
            }
        }
        return res
    }
    catch (e: NumberFormatException) {
        throw IllegalArgumentException()
    }
}

/**
 * Сложная
 *
 * Строка состоит из набора слов, отделённых друг от друга одним пробелом.
 * Определить, имеются ли в строке повторяющиеся слова, идущие друг за другом.
 * Слова, отличающиеся только регистром, считать совпадающими.
 * Вернуть индекс начала первого повторяющегося слова, или -1, если повторов нет.
 * Пример: "Он пошёл в в школу" => результат 9 (индекс первого 'в')
 */
fun firstDuplicateIndex(str: String): Int {
    val x = str.toLowerCase()
    val words = x.split(" ").zipWithNext()
    var count = 0
    for ((first, second) in words) {
        if (first == second) return count
        count += first.length + 1
    }
    return -1
}

/**
 * Сложная
 *
 * Строка содержит названия товаров и цены на них в формате вида
 * "Хлеб 39.9; Молоко 62; Курица 184.0; Конфеты 89.9".
 * То есть, название товара отделено от цены пробелом,
 * а цена отделена от названия следующего товара точкой с запятой и пробелом.
 * Вернуть название самого дорогого товара в списке (в примере это Курица),
 * или пустую строку при нарушении формата строки.
 * Все цены должны быть больше либо равны нуля.
 */
fun mostExpensive(description: String): String {
    val reg = Regex(""".+ \d+(\.\d+)?""")
    val list = description.split("; ")
    for (element in list) {
        if (!element.matches(reg)) return ""
    }
    val prices = description.split(" ", "; ")
    var max = -1.0
    var name = String()
    try {
        for (i in 0 until prices.size - 1 step 2) {
            val x = prices[i + 1].toDouble()
            if (x > max) {
                max = x
                name = prices[i]
            }
        }
    }
    catch (e: NumberFormatException) {
        return ""
    }
    return name
}

/**
 * Сложная
 *
 * Перевести число roman, заданное в римской системе счисления,
 * в десятичную систему и вернуть как результат.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: XXIII = 23, XLIV = 44, C = 100
 *
 * Вернуть -1, если roman не является корректным римским числом
 */
fun fromRoman(roman: String): Int {
    var res = 0
    val str = roman.split("").filter { it != "" }
    val num = mapOf("M" to 1000, "CM" to 900, "D" to 500, "CD" to 400,
            "C" to 100, "XC" to 90, "L" to 50, "XL" to 40, "X" to 10, "IX" to 9, "V" to 5,
            "IV" to 4, "I" to 1)
    if (roman.isEmpty()) return -1
    if ((str[0] in num) && (str.size < 2)) return num[str[0]]!!
    if (str[0] !in num) return -1
    var i = 0
    while (i < str.size - 1) {
        when {
            str[i] + str[i + 1] in num -> {
                res += num[str[i] + str[i + 1]]!!
                i += 2
            }
            str[i] in num -> {
                res += num[str[i]]!!
                i++
            }
            else -> return -1
        }
    }
    if ((str[str.size - 1] !in num)) return -1
    if (i == str.size - 1) res += num[str[str.size - 1]]!!
    return res
}

/**
 * Очень сложная
 *
 * Имеется специальное устройство, представляющее собой
 * конвейер из cells ячеек (нумеруются от 0 до cells - 1 слева направо) и датчик, двигающийся над этим конвейером.
 * Строка commands содержит последовательность команд, выполняемых данным устройством, например +>+>+>+>+
 * Каждая команда кодируется одним специальным символом:
 *	> - сдвиг датчика вправо на 1 ячейку;
 *  < - сдвиг датчика влево на 1 ячейку;
 *	+ - увеличение значения в ячейке под датчиком на 1 ед.;
 *	- - уменьшение значения в ячейке под датчиком на 1 ед.;
 *	[ - если значение под датчиком равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей следующей командой ']' (с учётом вложенности);
 *	] - если значение под датчиком не равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей предыдущей командой '[' (с учётом вложенности);
 *      (комбинация [] имитирует цикл)
 *  пробел - пустая команда
 *
 * Изначально все ячейки заполнены значением 0 и датчик стоит на ячейке с номером N/2 (округлять вниз)
 *
 * После выполнения limit команд или всех команд из commands следует прекратить выполнение последовательности команд.
 * Учитываются все команды, в том числе несостоявшиеся переходы ("[" при значении под датчиком не равном 0 и "]" при
 * значении под датчиком равном 0) и пробелы.
 *
 * Вернуть список размера cells, содержащий элементы ячеек устройства после завершения выполнения последовательности.
 * Например, для 10 ячеек и командной строки +>+>+>+>+ результат должен быть 0,0,0,0,0,1,1,1,1,1
 *
 * Все прочие символы следует считать ошибочными и формировать исключение IllegalArgumentException.
 * То же исключение формируется, если у символов [ ] не оказывается пары.
 * Выход за границу конвейера также следует считать ошибкой и формировать исключение IllegalStateException.
 * Считать, что ошибочные символы и непарные скобки являются более приоритетной ошибкой чем выход за границу ленты,
 * то есть если в программе присутствует некорректный символ или непарная скобка, то должно быть выброшено
 * IllegalArgumentException.
 * IllegalArgumentException должен бросаться даже если ошибочная команда не была достигнута в ходе выполнения.
 *
 */
fun computeDeviceCells(cells: Int, commands: String, limit: Int): List<Int> {
    val res = mutableListOf<Int>()
    if (commands.count { it == ']' } != commands.count { it == '[' }) throw IllegalArgumentException()
    var num = 0
    for (element in commands) {
        when {
            element == '[' -> num++
            (element == ']') && (num == 0) -> throw IllegalArgumentException()
            (element == ']') && (num > 0) -> num--
        }
    }
    for (j in 0 until cells) res.add(0)
    if (commands.isEmpty()) return res
    var i = cells / 2   // ячейка, где указатель сейчас
    var count = 0           // количество команд
    var k = 0             // переключение между командами
    while (count < limit) {
        when (commands[k]) {
            '>' -> i++
            '<' -> i--
            '+' -> res[i]++
            '-' -> res[i]--
            ' ' -> k
            '[' -> if (res[i] == 0) {
                var bkt1 = 0
                while (true) {
                    k++
                    if (commands[k] == '[') bkt1++
                    if ((commands[k] == ']') && (bkt1 == 0)) break
                    if ((commands[k] == ']') && (bkt1 > 0)) bkt1--
                }
            }
            ']' -> if (res[i] != 0) {
                var bkt2 = 0
                while (true) {
                    k--
                    if (commands[k] == ']') bkt2++
                    if ((commands[k] == '[') && (bkt2 == 0)) break
                    if ((commands[k] == '[') && (bkt2 > 0)) bkt2--
                }
            }
            else -> throw IllegalArgumentException()
        }
        k++
        if ((i >= cells) || (i < 0)) throw IllegalStateException()
        if (k == commands.length) break
        count++
    }

    return res
}

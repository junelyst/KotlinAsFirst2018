@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson7.task1

import java.io.File

/**
 * Пример
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * Вывести его в выходной файл с именем outputName, выровняв по левому краю,
 * чтобы длина каждой строки не превосходила lineLength.
 * Слова в слишком длинных строках следует переносить на следующую строку.
 * Слишком короткие строки следует дополнять словами из следующей строки.
 * Пустые строки во входном файле обозначают конец абзаца,
 * их следует сохранить и в выходном файле
 */
fun alignFile(inputName: String, lineLength: Int, outputName: String) {
    val outputStream = File(outputName).bufferedWriter()
    var currentLineLength = 0
    for (line in File(inputName).readLines()) {
        if (line.isEmpty()) {
            outputStream.newLine()
            if (currentLineLength > 0) {
                outputStream.newLine()
                currentLineLength = 0
            }
            continue
        }
        for (word in line.split(" ")) {
            if (currentLineLength > 0) {
                if (word.length + currentLineLength >= lineLength) {
                    outputStream.newLine()
                    currentLineLength = 0
                }
                else {
                    outputStream.write(" ")
                    currentLineLength++
                }
            }
            outputStream.write(word)
            currentLineLength += word.length
        }
    }
    outputStream.close()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * На вход подаётся список строк substrings.
 * Вернуть ассоциативный массив с числом вхождений каждой из строк в текст.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 */
fun countSubstrings(inputName: String, substrings: List<String>): Map<String, Int> {
    val res = mutableMapOf<String, Int>()
    val str = File(inputName).readText().toLowerCase()
    for (element in substrings) {
        var count = 0
        val sub = element.toLowerCase()
        if (sub in str) {
            val s = Regex(sub).findAll(str, 0)
            for (el in s) {
                count++
            }
        }
        res[element] = count
    }
    return res
}


/**
 * Средняя
 *
 * В русском языке, как правило, после букв Ж, Ч, Ш, Щ пишется И, А, У, а не Ы, Я, Ю.
 * Во входном файле с именем inputName содержится некоторый текст на русском языке.
 * Проверить текст во входном файле на соблюдение данного правила и вывести в выходной
 * файл outputName текст с исправленными ошибками.
 *
 * Регистр заменённых букв следует сохранять.
 *
 * Исключения (жюри, брошюра, парашют) в рамках данного задания обрабатывать не нужно
 *
 */
fun sibilants(inputName: String, outputName: String) {
    File(outputName).bufferedWriter().use {
        for (line in File(inputName).readLines()) {
            var new = line
            new = Regex("""(?<=[жчшщЖЧШЩ])ы""").replace(new, "и")
            new = Regex("""(?<=[жчшщЖЧШЩ])я""").replace(new, "а")
            new = Regex("""(?<=[жчшщЖЧШЩ])ю""").replace(new, "у")
            new = Regex("""(?<=[жчшщЖЧШЩ])Ы""").replace(new, "И")
            new = Regex("""(?<=[жчшщЖЧШЩ])Я""").replace(new, "А")
            new = Regex("""(?<=[жчшщЖЧШЩ])Ю""").replace(new, "У")
            it.write(new)
            it.newLine()
        }
    }
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по центру
 * относительно самой длинной строки.
 *
 * Выравнивание следует производить путём добавления пробелов в начало строки.
 *
 *
 * Следующие правила должны быть выполнены:
 * 1) Пробелы в начале и в конце всех строк не следует сохранять.
 * 2) В случае невозможности выравнивания строго по центру, строка должна быть сдвинута в ЛЕВУЮ сторону
 * 3) Пустые строки не являются особым случаем, их тоже следует выравнивать
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых)
 *
 */
fun centerFile(inputName: String, outputName: String) {
    var max = -1
    File(outputName).bufferedWriter().use {
        for (line in File(inputName).readLines()) {
            val x = line.trim()
            if (x.length > max) max = x.length
        }
        for (line in File(inputName).readLines()) {
            val new = line.trim()
            val ind = (max - new.length) / 2
            it.write(" ".repeat(ind))
            it.write(new)
            it.newLine()
        }
    }
}

/**
 * Сложная
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по левому и правому краю относительно
 * самой длинной строки.
 * Выравнивание производить, вставляя дополнительные пробелы между словами: равномерно по всей строке
 *
 * Слова внутри строки отделяются друг от друга одним или более пробелом.
 *
 * Следующие правила должны быть выполнены:
 * 1) Каждая строка входного и выходного файла не должна начинаться или заканчиваться пробелом.
 * 2) Пустые строки или строки из пробелов трансформируются в пустые строки без пробелов.
 * 3) Строки из одного слова выводятся без пробелов.
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых).
 *
 * Равномерность определяется следующими формальными правилами:
 * 5) Число пробелов между каждыми двумя парами соседних слов не должно отличаться более, чем на 1.
 * 6) Число пробелов между более левой парой соседних слов должно быть больше или равно числу пробелов
 *    между более правой парой соседних слов.
 *
 * Следует учесть, что входной файл может содержать последовательности из нескольких пробелов  между словами. Такие
 * последовательности следует учитывать при выравнивании и при необходимости избавляться от лишних пробелов.
 * Из этого следуют следующие правила:
 * 7) В самой длинной строке каждая пара соседних слов должна быть отделена В ТОЧНОСТИ одним пробелом
 * 8) Если входной файл удовлетворяет требованиям 1-7, то он должен быть в точности идентичен выходному файлу
 */
fun alignFileByWidth(inputName: String, outputName: String) {
    File(outputName).bufferedWriter().use {
        var max = -1
        val info = mutableListOf<Pair<Int, Int>>()
        var countMax = 0
        var space = 0
        for (line in File(inputName).readLines()) {
            var count = 0
            var length = 0
            for (word in line.trim().split(Regex("\\s"))) {
                length += word.length
                count++
            }
            info.add(length to count)
            if (length > max) {
                max = length
                countMax = count
            }
        }
        val maxLen = max
        max += countMax - 1
        var i = 0 // счётчик для списка info
        for (line in File(inputName).readLines()) {
            if (info[i].first == 0)
                it.newLine()
            else {
                space = if (info[i].first == maxLen) 1
                else (max - info[i].first) / info[i].second
                val possible = info[i].first + space * (info[i].second - 1)
                var diff = max - possible
                var new = line
                for (word in line.trim().split(Regex("\\s"))) {
                    new = new.replaceFirst("$word ", word + " ".repeat(space))
                }
                while (diff != 0) {
                    for (word in line.trim().split(Regex("\\s"))) {
                        if (diff != 0) {
                            new = new.replaceFirst(word + " ".repeat(space), word + " ".repeat(space + 1))
                            diff--
                        }
                        else break
                    }
                    space++
                    }
                it.write(new.trim())
                it.newLine()
            }
            i++
        }
    }
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * Вернуть ассоциативный массив, содержащий 20 наиболее часто встречающихся слов с их количеством.
 * Если в тексте менее 20 различных слов, вернуть все слова.
 *
 * Словом считается непрерывная последовательность из букв (кириллических,
 * либо латинских, без знаков препинания и цифр).
 * Цифры, пробелы, знаки препинания считаются разделителями слов:
 * Привет, привет42, привет!!! -привет?!
 * ^ В этой строчке слово привет встречается 4 раза.
 *
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 * Ключи в ассоциативном массиве должны быть в нижнем регистре.
 *
 */
fun top20Words(inputName: String): Map<String, Int> {
    val res = mutableMapOf<String, Int>()
    for (line in File(inputName).readLines()) {
        for (word in line.toLowerCase().split(Regex("""[^а-яa-zё]+""")).filter { it != "" }) {
            res[word] = res[word]?.plus(1) ?: 1
        }
    }
    val resSort = res.toList().sortedByDescending { it.second }
    var count = 0
    val top = mutableMapOf<String, Int>()
    for ((name, num) in resSort) {
        top += name to num
        count++
        if (count == 20) break
    }
    return top
}

/**
 * Средняя
 *
 * Реализовать транслитерацию текста из входного файла в выходной файл посредством динамически задаваемых правил.

 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * В ассоциативном массиве dictionary содержится словарь, в котором некоторым символам
 * ставится в соответствие строчка из символов, например
 * mapOf('з' to "zz", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "yy", '!' to "!!!")
 *
 * Необходимо вывести в итоговый файл с именем outputName
 * содержимое текста с заменой всех символов из словаря на соответствующие им строки.
 *
 * При этом регистр символов в словаре должен игнорироваться,
 * но при выводе символ в верхнем регистре отображается в строку, начинающуюся с символа в верхнем регистре.
 *
 * Пример.
 * Входной текст: Здравствуй, мир!
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Пример 2.
 *
 * Входной текст: Здравствуй, мир!
 * Словарь: mapOf('з' to "zZ", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "YY", '!' to "!!!")
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun transliterate(inputName: String, dictionary: Map<Char, String>, outputName: String) {
    File(outputName).bufferedWriter().use {
        val dict = mutableMapOf<Char, String>()
        for ((el, str) in dictionary) {
            dict[el.toLowerCase()] = str.toLowerCase()
        }
        var new = String()
        for (line in File(inputName).readLines()) {
            new = line
            for (element in line) {
                val low = element.toLowerCase()
                val reg = element.isUpperCase()
                if (low in dict) {
                    if (!reg) {
                        new = new.replace(low.toString(), dict[low]!!)
                    }
                    else {
                        var x = String()
                        val str = dict[low]!!
                        for (i in 1 until str.length) {
                            x += str[i].toLowerCase()
                        }
                        new = new.replace(element.toString(), dict[low]!![0].toUpperCase() + x)
                    }
                }
            }
            it.write(new)
            it.newLine()
        }
    }
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName имеется словарь с одним словом в каждой строчке.
 * Выбрать из данного словаря наиболее длинное слово,
 * в котором все буквы разные, например: Неряшливость, Четырёхдюймовка.
 * Вывести его в выходной файл с именем outputName.
 * Если во входном файле имеется несколько слов с одинаковой длиной, в которых все буквы разные,
 * в выходной файл следует вывести их все через запятую.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 * Пример входного файла:
 * Карминовый
 * Боязливый
 * Некрасивый
 * Остроумный
 * БелогЛазый
 * ФиолетОвый

 * Соответствующий выходной файл:
 * Карминовый, Некрасивый
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun chooseLongestChaoticWord(inputName: String, outputName: String) {
    File(outputName).bufferedWriter().use {
        var max = 0
        val res = mutableListOf<String>()
        for (line in File(inputName).readLines()) {
            if ((line.toLowerCase().toSet().size == line.length) && (line.length >= max)) {
                max = line.length
                res.add(line)
            }
        }
        val result = mutableListOf<String>()
        for (element in res) {
            if (element.length == max) {
                result.add(element)
            }
        }
        it.write(result.joinToString(separator = ", "))
    }
}

/**
 * Сложная
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе элементы текстовой разметки следующих типов:
 * - *текст в курсивном начертании* -- курсив
 * - **текст в полужирном начертании** -- полужирный
 * - ~~зачёркнутый текст~~ -- зачёркивание
 *
 * Следует вывести в выходной файл этот же текст в формате HTML:
 * - <i>текст в курсивном начертании</i>
 * - <b>текст в полужирном начертании</b>
 * - <s>зачёркнутый текст</s>
 *
 * Кроме того, все абзацы исходного текста, отделённые друг от друга пустыми строками, следует обернуть в теги <p>...</p>,
 * а весь текст целиком в теги <html><body>...</body></html>.
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 * Отдельно следует заметить, что открывающая последовательность из трёх звёздочек (***) должна трактоваться как "<b><i>"
 * и никак иначе.
 *
 * Пример входного файла:
Lorem ipsum *dolor sit amet*, consectetur **adipiscing** elit.
Vestibulum lobortis, ~~Est vehicula rutrum *suscipit*~~, ipsum ~~lib~~ero *placerat **tortor***,

Suspendisse ~~et elit in enim tempus iaculis~~.
 *
 * Соответствующий выходной файл:
<html>
    <body>
        <p>
            Lorem ipsum <i>dolor sit amet</i>, consectetur <b>adipiscing</b> elit.
            Vestibulum lobortis. <s>Est vehicula rutrum <i>suscipit</i></s>, ipsum <s>lib</s>ero <i>placerat <b>tortor</b></i>.
        </p>
        <p>
            Suspendisse <s>et elit in enim tempus iaculis</s>.
        </p>
    </body>
</html>
 *
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */
fun markdown(str: String, reg: Regex, murkup: String, r1: String, r2: String): String {
    var count = 0
    var new = str
    val s = reg.findAll(str, 0)
    for (el in s) {
        count++
    }
    if ((count % 2 == 0) && (count > 0)) {
        while (murkup in new) {
            new = new.replaceFirst(murkup, r1)
            new = new.replaceFirst(murkup, r2)
        }
    }
    return new
}

fun markdownToHtmlSimple(inputName: String, outputName: String) {
    var input = File(inputName).readText()
    File(outputName).bufferedWriter().use {
        it.write("<html><body><p>")
        if ("***" in input) {
            input = markdown(input, Regex("""\*\*\*"""), "***", "<b><i>", "</b></i>")
        }
        if ("**" in input) {
            input = markdown(input, Regex("""\*\*"""), "**", "<b>", "</b>")
        }
        if ("*" in input) {
            input = markdown(input, Regex("""\*"""), "*", "<i>", "</i>")
        }
        if ("~~" in input) {
            input = markdown(input, Regex("~~"), "~~", "<s>", "</s>")
        }
        val reg = Regex("""\n\n""")
        while (reg in input) {
            input = reg.replace(input, "</p><p>")
        }
        it.write(input)
        it.write("</p></body></html>")
    }
}

/**
 * Сложная
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе набор вложенных друг в друга списков.
 * Списки бывают двух типов: нумерованные и ненумерованные.
 *
 * Каждый элемент ненумерованного списка начинается с новой строки и символа '*', каждый элемент нумерованного списка --
 * с новой строки, числа и точки. Каждый элемент вложенного списка начинается с отступа из пробелов, на 4 пробела большего,
 * чем список-родитель. Максимально глубина вложенности списков может достигать 6. "Верхние" списки файла начинются
 * прямо с начала строки.
 *
 * Следует вывести этот же текст в выходной файл в формате HTML:
 * Нумерованный список:
 * <ol>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ol>
 *
 * Ненумерованный список:
 * <ul>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ul>
 *
 * Кроме того, весь текст целиком следует обернуть в теги <html><body>...</body></html>
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 *
 * Пример входного файла:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
* Утка по-пекински
    * Утка
    * Соус
* Салат Оливье
    1. Мясо
        * Или колбаса
    2. Майонез
    3. Картофель
    4. Что-то там ещё
* Помидоры
* Фрукты
    1. Бананы
    23. Яблоки
        1. Красные
        2. Зелёные
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 *
 *
 * Соответствующий выходной файл:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
<html>
  <body>
    <ul>
      <li>
        Утка по-пекински
        <ul>
          <li>Утка</li>
          <li>Соус</li>
        </ul>
      </li>
      <li>
        Салат Оливье
        <ol>
          <li>Мясо
            <ul>
              <li>
                  Или колбаса
              </li>
            </ul>
          </li>
          <li>Майонез</li>
          <li>Картофель</li>
          <li>Что-то там ещё</li>
        </ol>
      </li>
      <li>Помидоры</li>
      <li>
        Яблоки
        <ol>
          <li>Красные</li>
          <li>Зелёные</li>
        </ol>
      </li>
    </ul>
  </body>
</html>
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */
fun markdownToHtmlLists(inputName: String, outputName: String) {
    TODO()
}

/**
 * Очень сложная
 *
 * Реализовать преобразования из двух предыдущих задач одновременно над одним и тем же файлом.
 * Следует помнить, что:
 * - Списки, отделённые друг от друга пустой строкой, являются разными и должны оказаться в разных параграфах выходного файла.
 *
 */
fun markdownToHtml(inputName: String, outputName: String) {
    TODO()
}

/**
 * Средняя
 *
 * Вывести в выходной файл процесс умножения столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 111):
   19935
*    111
--------
   19935
+ 19935
+19935
--------
 2212785
 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 * Нули в множителе обрабатывать так же, как и остальные цифры:
  235
*  10
-----
    0
+235
-----
 2350
 *
 */
fun printMultiplicationProcess(lhv: Int, rhv: Int, outputName: String) {
    File(outputName).bufferedWriter().use {
        val lhvLen = lhv.toString().length
        val rhvLen = rhv.toString().length
        val max =
                if (rhvLen == 1) ((rhv.toString()[0] - '0') * lhv).toString().length + rhvLen
                else ((rhv.toString().toInt()) * lhv).toString().length + 1
        val res = lhv * rhv
        it.write(" ".repeat(max - lhvLen) + lhv + "\n")
        it.write("*" + " ".repeat(max - rhvLen - 1) + rhv + "\n")
        it.write("-".repeat(max) + "\n")
        var num = rhv
        var count = 0
        while (num > 0) {
            val x = (num % 10) * lhv
            num /= 10
            if (count > 0) {
                it.write("+" + " ".repeat(max - x.toString().length - count - 1) + x + "\n")
            }
            else {
                it.write(" ".repeat(max - x.toString().length) + x + "\n")
            }
            count++
        }
        it.write("-".repeat(max) + "\n")
        when {
            (max - "$res".length > 1) -> it.write(" ".repeat(max - "$res".length) + "$res")
            ("$res".length >= max) -> it.write("$res")
            else -> it.write(" $res")
        }
    }
}


/**
 * Сложная
 *
 * Вывести в выходной файл процесс деления столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 22):
  19935 | 22
 -198     906
 ----
    13
    -0
    --
    135
   -132
   ----
      3

 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 *
 */
fun printDivisionProcess(lhv: Int, rhv: Int, outputName: String) {
    File(outputName).bufferedWriter().use {
        val lhvLen = lhv.toString().length
        val rhvLen = rhv.toString().length
        val lhvStr = lhv.toString()
        val rhvStr = rhv.toString()
        val res = lhv / rhv
        val resStr = res.toString()
        var count = 0
        var num = 0  // число, из которого вычитаем
        var numStr = String()
        var ind = 0  // индекс делимого
        val k = (lhv % rhv).toString()  // остаток
        it.write(" $lhv | $rhv\n")
        for (element in res.toString()) {
            val sub = (element - '0') * rhv // вычитаемое
            val subLen = sub.toString().length
            when (count) {
                0 -> {
                    it.write("-$sub" + " ".repeat(lhvLen + 3 - subLen) + "$res" + "\n")  // вторая строка
                    it.write("-".repeat(subLen + 1) + "\n")
                    for ((i, el) in lhvStr.withIndex()) {
                        numStr += el
                        num = numStr.toInt()
                        if (num >= rhv) {
                            ind = i + 1
                            break
                        }
                    }
                    num -= sub
                    numStr = num.toString()
                }
                else -> {
                    if (ind < lhvLen) {
                        val space = ind - numStr.length + 1
                        numStr += lhvStr[ind]
                        it.write(" ".repeat(space) + numStr + "\n")
                        it.write(" ".repeat(ind - subLen - 1 + 2) + "-$sub" + "\n")
                        if (ind != lhvLen - 1) {
                            it.write(" ".repeat(ind - subLen - 1 + 2) + "-".repeat(subLen + 1) + "\n")
                        }
                        else {
                            if (k.length > subLen + 1) {
                                it.write(" ".repeat(ind - k.length + 2) + "-".repeat(k.length) + "\n")
                            }
                            else it.write(" ".repeat(ind - subLen - 1 + 2) + "-".repeat(subLen + 1) + "\n")
                        }
                        ind++
                        num = numStr.toInt()
                        num -= sub
                        numStr = num.toString()
                    }
                }
            }
            count++
        }
        if (ind != 0) it.write(" ".repeat(ind - k.length - 1 + 2) + k)
        else it.write(" ".repeat(lhvLen + 1 - numStr.length) + k)
    }
}


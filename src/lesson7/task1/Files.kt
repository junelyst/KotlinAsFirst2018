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
    for (element in substrings) {
        if (element !in res) res += element to 0
        for (line in File(inputName).readLines()) {
            if (element.toLowerCase() in line.toLowerCase()) {
                val count = line.toLowerCase().split(element.toLowerCase()).size - 1
                res[element] = res[element]!!.plus(count)
            }
        }
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
        val letters = listOf('ж', 'ч', 'ш', 'щ', 'Ж', 'Ч', 'Ш', 'Щ')
        for (line in File(inputName).readLines()) {
            var new = line
            for (element in letters) {
                val str = Regex("$element[ыяюЫЯЮ]")
                while (str in new) {
                    new = new.replace("$element" + "ы", "$element" + "и")
                    new = new.replace("$element" + "я", "$element" + "а")
                    new = new.replace("$element" + "ю", "$element" + "у")
                    new = new.replace("$element" + "Ы", "$element" + "И")
                    new = new.replace("$element" + "Я", "$element" + "А")
                    new = new.replace("$element" + "Ю", "$element" + "У")
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
        var ind = -1
        var new = String()
        for (line in File(inputName).readLines()) {
            new = line.trim()
            ind = (max - new.length) / 2
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
                if (info[i].first == maxLen) space = 1
                else space = (max - info[i].first) / info[i].second
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
        for (word in line.split(Regex("[\\p{Punct}\\s\\d«»—]")).filter { it != "" }) {
            if (word.toLowerCase() !in res) res += word.toLowerCase() to 1
            else res[word.toLowerCase()] = res[word.toLowerCase()]!!.plus(1)
        }
    }
    val resSort = res.toList().sortedBy { it.second }.reversed().toMap()
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
        for (line in File(inputName).readLines()) {
            for (word in line.split(Regex("\\n"))) {
                var new = word
                for (i in 0 until word.length) {
                    val low = word[i].toLowerCase()
                    val up = word[i].toUpperCase()
                    var reg = false
                    if (low != word[i]) reg = true
                    when {
                        low in dictionary -> {
                            new = new.replace(low.toString(), dictionary[low]!!.toLowerCase(), ignoreCase = true)
                            if (reg) {
                                val letters = dictionary[low]!!
                                new = new.replaceFirst(letters[0], letters[0].toUpperCase())
                            }
                        }
                        up in dictionary -> {
                            new = new.replace(up.toString(), dictionary[up]!!.toLowerCase(), ignoreCase = true)
                            if (reg) {
                                val letters = dictionary[low]!!
                                new = new.replaceFirst(letters[0], letters[0].toUpperCase())
                            }
                        }
                    }
                }
                it.write(new)
                it.newLine()
            }
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
        var count = 0
        for (element in res) {
            if (element.length == max) {
                it.write(element)
                count++
                if (count < res.size) it.write(", ")
            }
        }
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
fun markdownToHtmlSimple(inputName: String, outputName: String) {
    val murkup = listOf( "**" to "<b>", "**" to "</b>", "*" to "<i>", "*" to "</i>", "~~" to "<s>",
            "~~" to "</s>")
    File(outputName).bufferedWriter().use {
        it.write("<html><body><p>")
        for (line in File(inputName).readLines()) {
            if (line.isEmpty()) it.write("</p><p>")
            var new = line
            for (i in 0 until murkup.size - 1 step(2)) {
                while (new.contains(murkup[i].first)) {
                    val x = murkup[i].first
                    new = new.replaceFirst(x, murkup[i].second)
                    new = new.replaceFirst(x, (murkup[i + 1].second))
                }
            }
            it.write(new)
        }
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
    val murkup = listOf("*" to "ol", "*" to "ul", "    *" to "li",
            Regex("^\\d$" + ".").toString() to "li")
    var countOl = 0
    var countUl = 0
    File(outputName).bufferedWriter().use {
        it.write("<html><body>")
        val listStr = File(inputName).readLines()
        for (i in 0 until listStr.size - 1) {
            var new = listStr[i]
            if ((new.contains("*")) && (countUl == 0)) {
                new = "<ul><li>$new</li>"
                countUl++
            }
            if ((new.contains(Regex("^\\d$" + ".").toString())) && (countOl == 0)) {
                new = "<ol><li>$new</li>"
                countOl++
            }
            it.write(new + "\n")
        }
        it.write("</body></html>")
    }
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
        val max = ((rhv.toString()[0] - '0') * lhv).toString().length + rhvLen
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
        it.write(" $res")
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
        val max = lhvLen + rhvLen
        val res = lhv / rhv
        var count = 0
        var i = 0
        var numStr = String()
        var num = 0
        var space = 0
        it.write(" $lhv | $rhv\n")
        for (element in res.toString()) {
            val x = (element - '0') * rhv // вычитаемое
            when {
                count == 0 -> {
                    it.write("-$x" + " ".repeat(max - rhvLen - 1) + res + "\n")
                    it.write("-".repeat(x.toString().length + 1) + "\n")
                }
                count == 1 -> {
                    while (i < x.toString().length) { //собираем вычитаемое из lhv
                        numStr += lhv.toString()[i]
                        i++ // счётчик разрядов
                    }
                    num = numStr.toInt()
                    val a = "${num - x}" + "${lhv.toString()[i + 2]}" // число под чертой
                    it.write(" ".repeat(a.length + 1)) //пробелы перед этим числом
                    it.write(a + "\n")
                    num = a.toInt()
                    space = 2 * a.length - "${(element - '0') * rhv}".length
                    it.write( " ".repeat(space) + "-${(element - '0') * rhv}" + "\n")
                    it.write(" ".repeat(space) + "-".repeat("${(element - '0') * rhv}".length + 1))
                }
                else -> {

                }
            }
            count++
        }
    }
}


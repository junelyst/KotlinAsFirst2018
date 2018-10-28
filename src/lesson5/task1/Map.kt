@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson5.task1

/**
 * Пример
 *
 * Для заданного списка покупок `shoppingList` посчитать его общую стоимость
 * на основе цен из `costs`. В случае неизвестной цены считать, что товар
 * игнорируется.
 */
fun shoppingListCost(
        shoppingList: List<String>,
        costs: Map<String, Double>): Double {
    var totalCost = 0.0

    for (item in shoppingList) {
        val itemCost = costs[item]
        if (itemCost != null) {
            totalCost += itemCost
        }
    }

    return totalCost
}

/**
 * Пример
 *
 * Для набора "имя"-"номер телефона" `phoneBook` оставить только такие пары,
 * для которых телефон начинается с заданного кода страны `countryCode`
 */
fun filterByCountryCode(
        phoneBook: MutableMap<String, String>,
        countryCode: String) {
    val namesToRemove = mutableListOf<String>()

    for ((name, phone) in phoneBook) {
        if (!phone.startsWith(countryCode)) {
            namesToRemove.add(name)
        }
    }

    for (name in namesToRemove) {
        phoneBook.remove(name)
    }
}

/**
 * Пример
 *
 * Для заданного текста `text` убрать заданные слова-паразиты `fillerWords`
 * и вернуть отфильтрованный текст
 */
fun removeFillerWords(
        text: List<String>,
        vararg fillerWords: String): List<String> {
    val fillerWordSet = setOf(*fillerWords)

    val res = mutableListOf<String>()
    for (word in text) {
        if (word !in fillerWordSet) {
            res += word
        }
    }
    return res
}

/**
 * Пример
 *
 * Для заданного текста `text` построить множество встречающихся в нем слов
 */
fun buildWordSet(text: List<String>): MutableSet<String> {
    val res = mutableSetOf<String>()
    for (word in text) res.add(word)
    return res
}

/**
 * Средняя
 *
 * Объединить два ассоциативных массива `mapA` и `mapB` с парами
 * "имя"-"номер телефона" в итоговый ассоциативный массив, склеивая
 * значения для повторяющихся ключей через запятую.
 * В случае повторяющихся *ключей* значение из mapA должно быть
 * перед значением из mapB.
 *
 * Повторяющиеся *значения* следует добавлять только один раз.
 *
 * Например:
 *   mergePhoneBooks(
 *     mapOf("Emergency" to "112", "Police" to "02"),
 *     mapOf("Emergency" to "911", "Police" to "02")
 *   ) -> mapOf("Emergency" to "112, 911", "Police" to "02")
 */
fun mergePhoneBooks(mapA: Map<String, String>, mapB: Map<String, String>): Map<String, String> {
    val res = mutableMapOf<String, String>()
    val phoneRes = StringBuilder()
    for ((nameA, phoneA) in mapA) {
        for ((nameB, phoneB) in mapB) {
            if ((nameA == nameB) && (phoneA != phoneB)) {
                phoneRes.append(phoneA).append(", ").append(phoneB)
                res += nameA to phoneRes.toString()
                }
            if (nameA !in res) res += nameA to phoneA
            if (nameB !in res) res += nameB to phoneB
        }
    }
    if (res.isEmpty()) {
        return if (mapA.size >= mapB.size) mapA
        else mapB
    }
    return res
}

/**
 * Простая
 *
 * По заданному ассоциативному массиву "студент"-"оценка за экзамен" построить
 * обратный массив "оценка за экзамен"-"список студентов с этой оценкой".
 *
 * Например:
 *   buildGrades(mapOf("Марат" to 3, "Семён" to 5, "Михаил" to 5))
 *     -> mapOf(5 to listOf("Семён", "Михаил"), 3 to listOf("Марат"))
 */
fun buildGrades(grades: Map<String, Int>): Map<Int, List<String>> {
    val res = mutableMapOf<Int, MutableList<String>>()
    for ((name, value) in grades) {
        if (value in res) res[value]?.plusAssign(name)
        else {
            val students = mutableListOf(name)
            res += value to students
        }
    }
    // сортировка
    val gradeList = mutableListOf<Int>()
    for ((value, students) in res) {
        gradeList.add(value)
    }
    val gradeSort = gradeList.sortedDescending()
    val resSort = mutableMapOf<Int, MutableList<String>>()
    for ((value, students) in res) {
        for (i in 0 until gradeSort.size) {
            val studentsSort = res[gradeSort[i]]?.sorted()!!.toMutableList()
            resSort += gradeSort[i] to studentsSort
        }
    }
    return resSort
}

/**
 * Простая
 *
 * Определить, входит ли ассоциативный массив a в ассоциативный массив b;
 * это выполняется, если все ключи из a содержатся в b с такими же значениями.
 *
 * Например:
 *   containsIn(mapOf("a" to "z"), mapOf("a" to "z", "b" to "sweet")) -> true
 *   containsIn(mapOf("a" to "z"), mapOf("a" to "zee", "b" to "sweet")) -> false
 */
fun containsIn(a: Map<String, String>, b: Map<String, String>): Boolean {
    var x = 0
    for ((keyB, valueB) in b) {
        for ((keyA, valueA) in a) {
            if ((keyB == keyA) && (valueB == valueA)) x++
        }
    }
    return (x == a.size)
}

/**
 * Средняя
 *
 * Для заданного списка пар "акция"-"стоимость" вернуть ассоциативный массив,
 * содержащий для каждой акции ее усредненную стоимость.
 *
 * Например:
 *   averageStockPrice(listOf("MSFT" to 100.0, "MSFT" to 200.0, "NFLX" to 40.0))
 *     -> mapOf("MSFT" to 150.0, "NFLX" to 40.0)
 */
fun averageStockPrice(stockPrices: List<Pair<String, Double>>): Map<String, Double> {
    val res = mutableMapOf<String, Double>()
    for (i in 0 until stockPrices.size) {
        var stock = stockPrices[i].first
        var sum = stockPrices[i].second
        var count = 1
        for (k in i + 1 until stockPrices.size) {
            when {
                stock == stockPrices[k].first -> {
                    count++
                    sum += stockPrices[k].second
                    stock = stockPrices[k].first
                }
                stockPrices[i].first !in res -> res += stockPrices[k]
            }
        }
        if (count > 1) res += stock to sum / count
        if (stock !in res) res += stock to stockPrices[i].second
    }
    return res
}

/**
 * Средняя
 *
 * Входными данными является ассоциативный массив
 * "название товара"-"пара (тип товара, цена товара)"
 * и тип интересующего нас товара.
 * Необходимо вернуть название товара заданного типа с минимальной стоимостью
 * или null в случае, если товаров такого типа нет.
 *
 * Например:
 *   findCheapestStuff(
 *     mapOf("Мария" to ("печенье" to 20.0), "Орео" to ("печенье" to 100.0)),
 *     "печенье"
 *   ) -> "Мария"
 */
fun findCheapestStuff(stuff: Map<String, Pair<String, Double>>, kind: String): String? {
    val price = mutableListOf<Double>()
    for ((name, type) in stuff) {
        if (type.first.equals(kind)) price.add(type.second)
    }
    var res = ""
    for ((name, type) in stuff) {
        if (type.second == price.min()) res = name
    }
    return if (res == "") null
    else res
}

/**
 * Сложная
 *
 * Для заданного ассоциативного массива знакомых через одно рукопожатие `friends`
 * необходимо построить его максимальное расширение по рукопожатиям, то есть,
 * для каждого человека найти всех людей, с которыми он знаком через любое
 * количество рукопожатий.
 * Считать, что все имена людей являются уникальными, а также что рукопожатия
 * являются направленными, то есть, если Марат знает Свету, то это не означает,
 * что Света знает Марата.
 *
 * Например:
 *   propagateHandshakes(
 *     mapOf(
 *       "Marat" to setOf("Mikhail", "Sveta"),
 *       "Sveta" to setOf("Marat"),
 *       "Mikhail" to setOf("Sveta")
 *     )
 *   ) -> mapOf(
 *          "Marat" to setOf("Mikhail", "Sveta"),
 *          "Sveta" to setOf("Marat", "Mikhail"),
 *          "Mikhail" to setOf("Sveta", "Marat")
 *        )
 */
fun propagateHandshakes(friends: Map<String, Set<String>>): Map<String, Set<String>> {
    val res = mutableMapOf<String, MutableSet<String>>()
    val listOfNames = mutableListOf<String>()
    for ((person, people) in friends) {
        if (person !in listOfNames) listOfNames.add(person)
        for (element in people) {
            if (element !in listOfNames) listOfNames.add(element)
        }
    }
    for ((person, people) in friends) {
        res += person to people.toMutableSet()
    }
    for (i in 0 until listOfNames.size) {
        val name = listOfNames[i]
        for ((person, people) in friends) {
            if (name !in res) res += name to mutableSetOf()
            else if (res[person]!!.contains(name))
                (res[person]!!.union(res[name]!!.toMutableSet())).toMutableSet()
        }
    }
    return res
}

/**
 * Простая
 *
 * Удалить из изменяемого ассоциативного массива все записи,
 * которые встречаются в заданном ассоциативном массиве.
 * Записи считать одинаковыми, если и ключи, и значения совпадают.
 *
 * ВАЖНО: необходимо изменить переданный в качестве аргумента
 *        изменяемый ассоциативный массив
 *
 * Например:
 *   subtractOf(a = mutableMapOf("a" to "z"), mapOf("a" to "z"))
 *     -> a changes to mutableMapOf() aka becomes empty
 */
fun subtractOf(a: MutableMap<String, String>, b: Map<String, String>): Unit {
    for ((keyB, valueB) in b) {
        if ((keyB in a) && (valueB == a[keyB])) {
            a.remove(keyB)
            if (a.size <= 1) break
        }
    }
}

/**
 * Простая
 *
 * Для двух списков людей найти людей, встречающихся в обоих списках
 */
fun whoAreInBoth(a: List<String>, b: List<String>): List<String> {
    val res = mutableListOf<String>()
    for (i in 0 until maxOf(a.size, b.size)) {
        if ((a[i] in b) && (a[i] !in res)) res.add(a[i])
        if (i == minOf(a.size, b.size) - 1) break
    }
    return res
}

/**
 * Средняя
 *
 * Для заданного набора символов определить, можно ли составить из него
 * указанное слово (регистр символов игнорируется)
 *
 * Например:
 *   canBuildFrom(listOf('a', 'b', 'o'), "baobab") -> true
 */
fun canBuildFrom(chars: List<Char>, word: String): Boolean {
    for (i in 0 until word.length) {
        if (word[i].toLowerCase() !in chars.toString().toLowerCase()) return false
    }
    return true
}

/**
 * Средняя
 *
 * Найти в заданном списке повторяющиеся элементы и вернуть
 * ассоциативный массив с информацией о числе повторений
 * для каждого повторяющегося элемента.
 * Если элемент встречается только один раз, включать его в результат
 * не следует.
 *
 * Например:
 *   extractRepeats(listOf("a", "b", "a")) -> mapOf("a" to 2)
 */
fun extractRepeats(list: List<String>): Map<String, Int> {
    var count = 1
    val res = mutableMapOf<String, Int>()
    for (i in 0 until list.size) {
        for (k in i + 1 until list.size) {
            if (list[i] == list[k]) count++
        }
        if ((count > 1) && (list[i] !in res)) res[list[i]] = count
        count = 1
    }
    return res
}

/**
 * Средняя
 *
 * Для заданного списка слов определить, содержит ли он анаграммы
 * (два слова являются анаграммами, если одно можно составить из второго)
 *
 * Например:
 *   hasAnagrams(listOf("тор", "свет", "рот")) -> true
 */
fun hasAnagrams(words: List<String>): Boolean {
    val mn = mutableSetOf<Set<Char>>()
    val part = mutableSetOf<Char>()
    if (words.isEmpty()) return false
    for (i in 0 until words.size) {
        for (k in 0 until words[i].length) {
            part.add(words[i][k])
        }
        if (part.size != words[i].length) part += '!'
        mn.add(part)
        part.clear()
    }
    return mn.size != words.size
}

/**
 * Сложная
 *
 * Для заданного списка неотрицательных чисел и числа определить,
 * есть ли в списке пара чисел таких, что их сумма равна заданному числу.
 * Если да, верните их индексы в виде Pair<Int, Int>;
 * если нет, верните пару Pair(-1, -1).
 *
 * Индексы в результате должны следовать в порядке (меньший, больший).
 *
 * Постарайтесь сделать ваше решение как можно более эффективным,
 * используя то, что вы узнали в данном уроке.
 *
 * Например:
 *   findSumOfTwo(listOf(1, 2, 3), 4) -> Pair(0, 2)
 *   findSumOfTwo(listOf(1, 2, 3), 6) -> Pair(-1, -1)
 */
fun findSumOfTwo(list: List<Int>, number: Int): Pair<Int, Int> {
    for (i in 0 until list.size) {
        val num = list[i]
        for (k in 1 until list.size) {
            if ((num + list[k] == number) && (k != i)) return minOf(i, k) to maxOf(i, k)
        }
    }
    return -1 to -1
}

/**
 * Очень сложная
 *
 * Входными данными является ассоциативный массив
 * "название сокровища"-"пара (вес сокровища, цена сокровища)"
 * и вместимость вашего рюкзака.
 * Необходимо вернуть множество сокровищ с максимальной суммарной стоимостью,
 * которые вы можете унести в рюкзаке.
 *
 * Например:
 *   bagPacking(
 *     mapOf("Кубок" to (500 to 2000), "Слиток" to (1000 to 5000)),
 *     850
 *   ) -> setOf("Кубок")
 *   bagPacking(
 *     mapOf("Кубок" to (500 to 2000), "Слиток" to (1000 to 5000)),
 *     450
 *   ) -> emptySet()
 */
fun bagPacking(treasures: Map<String, Pair<Int, Int>>, capacity: Int): Set<String> = TODO()

package lesson6.task1

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test

class Tests {
    @Test
    @Tag("Example")
    fun timeStrToSeconds() {
        assertEquals(36000, timeStrToSeconds("10:00:00"))
        assertEquals(41685, timeStrToSeconds("11:34:45"))
        assertEquals(86399, timeStrToSeconds("23:59:59"))
    }

    @Test
    @Tag("Example")
    fun twoDigitStr() {
        assertEquals("00", twoDigitStr(0))
        assertEquals("09", twoDigitStr(9))
        assertEquals("10", twoDigitStr(10))
        assertEquals("99", twoDigitStr(99))
    }

    @Test
    @Tag("Example")
    fun timeSecondsToStr() {
        assertEquals("10:00:00", timeSecondsToStr(36000))
        assertEquals("11:34:45", timeSecondsToStr(41685))
        assertEquals("23:59:59", timeSecondsToStr(86399))
    }

    @Test
    @Tag("Normal")
    fun dateStrToDigit() {
        assertEquals("15.07.2016", dateStrToDigit("15 июля 2016"))
        assertEquals("", dateStrToDigit("3 мартобря 1918"))
        assertEquals("18.11.2018", dateStrToDigit("18 ноября 2018"))
        assertEquals("", dateStrToDigit("23"))
        assertEquals("03.04.2011", dateStrToDigit("3 апреля 2011"))
        assertEquals("", dateStrToDigit("32 сентября 2011"))
        assertEquals("", dateStrToDigit("29 февраля 1993"))
        assertEquals("29.02.0", dateStrToDigit("29 февраля 0"))
        assertEquals("", dateStrToDigit("^chB(?Y[nLa8o ;US#A6^n,R[8k}]UH* C2m>93tptFLp"))
    }

    @Test
    @Tag("Normal")
    fun dateDigitToStr() {
        assertEquals("15 декабря 2016", dateDigitToStr("15.12.2016"))
        assertEquals("15 июля 2016", dateDigitToStr("15.07.2016"))
        assertEquals("", dateDigitToStr("01.02.20.19"))
        assertEquals("", dateDigitToStr("28.00.2000"))
        assertEquals("3 апреля 2011", dateDigitToStr("03.04.2011"))
        assertEquals("", dateDigitToStr("ab.cd.ef"))
        assertEquals("", dateDigitToStr("32.09.2011"))
        assertEquals("", dateDigitToStr("29.02.1993"))
        assertEquals("29 февраля 0", dateDigitToStr("29.02.0"))
        assertEquals("", dateDigitToStr("4LXmg'4kf:\\\\YhE\\\"?s=%HX Yd#&* @\\\"H>v>gy^A SY+eR{]uzr_kuRu1" +
                "*\\\"Isq{HQoLAi)?`69p-[!a0\\\\jxZQBUZah9Dg 2B}d>1/9.P4s{')d[!NiHyykMw8i>@Gg:^9\\\"ji+ucI8/pIX}}.>" +
                "&]w~_ry+3uyN\\n*_e}W"))
    }

    @Test
    @Tag("Normal")
    fun flattenPhoneNumber() {
        assertEquals("+79211234666", flattenPhoneNumber("+7( 9  2 1  )12 346 - 66"))
        assertEquals("+79211234567", flattenPhoneNumber("+7 (921) 123 - 4567"))
        assertEquals("9211234567", flattenPhoneNumber("(921) 123 - 4567"))
        assertEquals("123456798", flattenPhoneNumber("12 --  34- 5 -- 67 -98"))
        assertEquals("", flattenPhoneNumber("ab-123"))
        assertEquals("4", flattenPhoneNumber("4"))
        assertEquals("+12345", flattenPhoneNumber("+12 (3) 4-5"))
        assertEquals("", flattenPhoneNumber("+(123)4567890"))
        assertEquals("", flattenPhoneNumber("1(234)567890"))
        assertEquals("", flattenPhoneNumber("+++12 (3) 4-5"))
        assertEquals("", flattenPhoneNumber("134_+874"))
        assertEquals("", flattenPhoneNumber("a134 874"))
        assertEquals("", flattenPhoneNumber(" "))
    }

    @Test
    @Tag("Normal")
    fun bestLongJump() {
        assertEquals(717, bestLongJump("706 % - 717 - 703"))
        assertEquals(-1, bestLongJump("% - - % -"))
        assertEquals(754, bestLongJump("700 717 707 % 754"))
        assertEquals(-1, bestLongJump("700 + 700"))
        assertEquals(-1, bestLongJump("706 %% - 717 - 703"))
        assertEquals(-1, bestLongJump("706% - 717 - 703"))
        assertEquals(-1, bestLongJump("706- - 717 - 703"))
        assertEquals(2147483647, bestLongJump("- 2147483647 - 0 2147483647  0  -  -   1  % %  -  1  2147483647 - - -  -  2147483647 1710063417 -  1034878216 %   1262677429 -   - %  -  - - -  2147483647 % - % 0 0 - % -  916864931 %  - 1 1  % -  % -    % 356721671 -    -  - - -  %  2147483647 -   1971396269  1  -   0   % - 1604748775 1 2147483647 - 1236040480  - 670343304  2147483647 -  - 2147483647   %  - 1  % 1397461123 - -     842403378 2147483647  0  % 1 1759834439 -   %     % -   1794144217 0 895937763   %  -   1 -    2147483647   968981147  -   - -   1  1110736684    2147483647 - - - - 1 -  % - 2118909005 761790373   -  0  - % - - -  1641850916 %  %  - -  347639758 - - % 0  -  -  %   %   1 - 0  -  -"))
    }

    @Test
    @Tag("Hard")
    fun bestHighJump() {
        assertEquals(226, bestHighJump("226 +"))
        assertEquals(-1, bestHighJump("???"))
        assertEquals(230, bestHighJump("220 + 224 %+ 228 %- 230 + 232 %%- 234 %"))
        assertEquals(-1, bestHighJump("220 224 %+ 228 %- 230 + 232 %%- 234 %"))
    }

    @Test
    @Tag("Hard")
    fun plusMinus() {
        assertEquals(0, plusMinus("0"))
        assertEquals(4, plusMinus("2 + 2"))
        assertEquals(6, plusMinus("2 + 31 - 40 + 13"))
        assertEquals(-1, plusMinus("0 - 1"))
        assertThrows(IllegalArgumentException::class.java) { plusMinus("1 9oJRvvmS2e/v%]LdZ!") }
        assertThrows(IllegalArgumentException::class.java) { plusMinus("+2") }
        assertThrows(IllegalArgumentException::class.java) { plusMinus("+ 4") }
        assertThrows(IllegalArgumentException::class.java) { plusMinus("4 - -2") }
        assertThrows(IllegalArgumentException::class.java) { plusMinus("44 - - 12") }
        assertThrows(IllegalArgumentException::class.java) { plusMinus("4 - + 12") }
    }

    @Test
    @Tag("Hard")
    fun firstDuplicateIndex() {
        assertEquals(-1, firstDuplicateIndex("Привет"))
        assertEquals(9, firstDuplicateIndex("Он пошёл в в школу"))
        assertEquals(40, firstDuplicateIndex("Яблоко упало на ветку с ветки оно упало на на землю"))
        assertEquals(9, firstDuplicateIndex("Мы пошли прямо Прямо располагался магазин"))
    }

    @Test
    @Tag("Hard")
    fun mostExpensive() {
        assertEquals("", mostExpensive(""))
        assertEquals("Вино", mostExpensive("Вино 255.0"))
        assertEquals("", mostExpensive("Вино 255.0.0"))
        assertEquals("", mostExpensive("Вино 255f0"))
        assertEquals("Курица", mostExpensive("Хлеб 39.9; Молоко 62.5; Курица 184.0; Конфеты 89.9"))
        assertEquals("Tea", mostExpensive("Tea 255.0"))
        assertEquals("sfjkJ&$1", mostExpensive("sfjkJ&$1 255.0"))
    }

    @Test
    @Tag("Hard")
    fun fromRoman() {
        assertEquals(1, fromRoman("I"))
        assertEquals(3000, fromRoman("MMM"))
        assertEquals(1978, fromRoman("MCMLXXVIII"))
        assertEquals(694, fromRoman("DCXCIV"))
        assertEquals(49, fromRoman("XLIX"))
        assertEquals(-1, fromRoman("Z"))
        assertEquals(-1, fromRoman("LR"))
        assertEquals(-1, fromRoman(""))
        assertEquals(17011, fromRoman("MMMMMMMMMMMMMMMMMXI"))
    }

    @Test
    @Tag("Impossible")
    fun computeDeviceCells() {
        assertThrows(IllegalArgumentException::class.java) { computeDeviceCells(20, "[[]-]-<<-ab", 6) }
        assertEquals(listOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0), computeDeviceCells(10, "", 17))
        assertEquals(listOf(1, 1, 1, 0, 0, -1, 0, 0, 0, 0), computeDeviceCells(10, "- <<<<< +[>+]", 17))
        assertEquals(listOf(0, 6, 5, 4, 3, 2, 1, 0, -1, -1, -2),
                computeDeviceCells(11, "<<<<< + >>>>>>>>>> --[<-] >+[>+] >++[--< <[<] >+[>+] >++]", 256))
        assertEquals(listOf(1, 1, 1, 0, 0, -1, 0, 0, 0, 0), computeDeviceCells(10, "- <<<<< +[>+]", 17))
        assertEquals(listOf(0, 0, 0, 0, 0, 1, 1, 0, 0, 0), computeDeviceCells(10, "+>+>+>+>+", 4))
        assertEquals(listOf(0, 0, 0, 0, 0, 1, 1, 1, 1, 1), computeDeviceCells(10, "+>+>+>+>+", 10000))
        assertEquals(listOf(-1, -1, -1, -1, -1, 0, 0, 0, 0, 0), computeDeviceCells(10, "<-<-<-<-<-", 10000))
        assertEquals(listOf(1, 1, 1, 1, 1, 0, 0, 0, 0, 0), computeDeviceCells(10, "- <<<<< +[>+]", 10000))
        assertEquals(listOf(0, 8, 7, 6, 5, 4, 3, 2, 1, 0, 0),
                computeDeviceCells(11, "<<<<< + >>>>>>>>>> --[<-] >+[>+] >++[--< <[<] >+[>+] >++]", 10000))
        assertEquals(listOf(0, 0, -1, -1, -1, 0, 0, 0, 0, 0), computeDeviceCells(10, "<-<-<-<-<-", 6))
        assertEquals(listOf(1, 1, 1, 0, 0, -1, 0, 0, 0, 0), computeDeviceCells(10, "- <<<<< +[>+]", 17))
        assertThrows(IllegalArgumentException::class.java) { computeDeviceCells(5, "+-+-<<<+a", 2) }
        assertThrows(IllegalArgumentException::class.java) { computeDeviceCells(10, "===", 3) }
        assertThrows(IllegalArgumentException::class.java) { computeDeviceCells(10, "+>+>[+>", 3) }
        assertThrows(IllegalArgumentException::class.java) { computeDeviceCells(20, "[[]-]-<<][-<[<]", 333) }
        assertThrows(IllegalStateException::class.java) { computeDeviceCells(20, ">>>>>>>>>>>>>", 12) }
        assertThrows(IllegalStateException::class.java) { computeDeviceCells(1, "< - <+-<+<-+++<+>+ -++<+---", 120) }
        assertThrows(IllegalStateException::class.java) { computeDeviceCells(1, ">", 12) }
    }
}
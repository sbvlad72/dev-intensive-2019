package ru.skillbranch.devintensive.utils

object Utils {
    fun parseFullName(fullName:String?): Pair<String?, String?> {
        // TO DO испрвить обработку пустых значений
        val parts : List<String>? = fullName?.trim()?.split(" ")
        var firstName =parts?.getOrNull(0)
        var lastName =parts?.getOrNull(1)
        if (firstName == null || firstName == "" || firstName == " ") {
            firstName = null
            lastName = null

        }
        //return Pair(firstName, lastName)
        return firstName to lastName
    }

    fun transliteration(payload: String, divider:String = " "): String {
        //реализовать транслитерацию
        return "not implemented"
    }

    fun toInitials(firstName: String?, lastName: String?): String? {
        //реализовать ннициалы
        return "not implemented"
    }
}
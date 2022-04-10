fun printARGB() {
    val values = readLine()!!.split(Regex("\\s+")).map { it.toInt() }
    if (values.any { it !in 0..255 }) {
        println("Invalid input")
    } else {
        println(Color(values[1], values[2], values[3], values[0]).rgb.toUInt())
    }
}
package seamcarving

data class Gradient(private val colorDiff: ColorDiff) {

    private fun square(value: Int) = value * value

    val value: Int
        get() = square(colorDiff.red) + square(colorDiff.green) + square(colorDiff.blue)

    override fun toString(): String = "Gradient($value)"
}
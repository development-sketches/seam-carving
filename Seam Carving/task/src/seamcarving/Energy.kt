package seamcarving

import kotlin.math.sqrt

data class Energy(private val xGradient: Gradient, private val yGradient: Gradient) {

    val value: Double
        get() = sqrt((xGradient.value + yGradient.value).toDouble())

}
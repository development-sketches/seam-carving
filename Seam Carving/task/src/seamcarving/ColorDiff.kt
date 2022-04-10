package seamcarving

import java.awt.Color

data class ColorDiff(private val color1: Color, private val color2: Color) {

    constructor(rgb1: Int, rgb2: Int) : this(Color(rgb1), Color(rgb2))

    val red: Int
        get() = color2.red - color1.red

    val green: Int
        get() = color2.green - color1.green

    val blue: Int
        get() = color2.blue - color1.blue

    override fun toString(): String = "ColorDiff(red=$red,green=$green,blue=$blue)"

}
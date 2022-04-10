import java.awt.Color
import java.awt.image.BufferedImage

fun drawLines(): BufferedImage {
    val height = 200
    val width = 200
    val image = BufferedImage(height, width, BufferedImage.TYPE_INT_RGB)
    val graphics = image.createGraphics()
    graphics.color = Color.RED
    graphics.drawLine(0, 0, width, height)
    graphics.color = Color.GREEN
    graphics.drawLine(width, 0, 0, height)
    return image
}
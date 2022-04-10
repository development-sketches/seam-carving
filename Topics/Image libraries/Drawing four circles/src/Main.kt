import java.awt.Color
import java.awt.image.BufferedImage

fun drawCircles(): BufferedImage {
    val height = 200
    val width = 200
    val image = BufferedImage(height, width, BufferedImage.TYPE_INT_RGB)
    val graphics = image.createGraphics()
    graphics.color = Color.RED
    graphics.drawOval(50, 50, width / 2, height / 2)
    graphics.color = Color.YELLOW
    graphics.drawOval(50, 75, width / 2, height / 2)
    graphics.color = Color.GREEN
    graphics.drawOval(75, 50, width / 2, height / 2)
    graphics.color = Color.BLUE
    graphics.drawOval(75, 75, width / 2, height / 2)
    return image
}
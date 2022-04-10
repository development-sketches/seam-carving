package seamcarving

import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

fun main(args: Array<String>) {
    val argMap = argumentMap(args)
    val inFile = File(argMap["in"]!!)
    require(inFile.exists() && inFile.isFile) {
        "Input file is not found"
    }
    var image: BufferedImage = ImageIO.read(inFile)

    val width = argMap["width"]!!.toInt()
    val height = argMap["height"]!!.toInt()

    repeat(width) {
        val seam = Seam(image, SeamMode.VERTICAL).seam()
        val newImage = BufferedImage(image.width - 1, image.height, image.type)
        (0 until newImage.width).forEach { x ->
            (0 until newImage.height).forEach { y ->
                val exclude = seam[y]
                val sourceX = if (x < exclude) x else x + 1
                newImage.setRGB(x, y, image.getRGB(sourceX, y))
            }
        }
        image = newImage
    }

    repeat(height) {
        val seam = Seam(image, SeamMode.HORIZONTAL).seam()
        val newImage = BufferedImage(image.width, image.height - 1, image.type)
        println("Height: ${image.height}")
        (0 until newImage.width).forEach { x ->
            (0 until newImage.height).forEach { y ->
                val exclude = seam[x]
                val sourceY = if (y < exclude) y else y + 1
                newImage.setRGB(x, y, image.getRGB(x, sourceY))
            }
        }
        image = newImage
    }

    ImageIO.write(image, "png", File(argMap["out"]!!))
}

private fun argumentMap(args: Array<String>): MutableMap<String, String> {
    val argMap = mutableMapOf<String, String>()
    for (i in args.indices) {
        if (args[i].startsWith("-")) {
            argMap[args[i].substring(1)] = if ((i + 1) < args.size && !args[i + 1].startsWith("-")) args[i + 1] else ""
        }
    }
    return argMap
}

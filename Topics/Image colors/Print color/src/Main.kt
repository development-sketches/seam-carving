fun printColor(myImage: BufferedImage) {
    val coors = readLine()!!.split(Regex("\\s+")).map { it.toInt() }
    val rgb = myImage.getRGB(coors[0], coors[1])
    val color = Color(rgb, true)
    println("${color.red} ${color.green} ${color.blue} ${color.alpha}")
}
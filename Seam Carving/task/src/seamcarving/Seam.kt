package seamcarving

import java.awt.image.BufferedImage
import java.util.*

enum class SeamMode { HORIZONTAL, VERTICAL }

interface Path {

    fun path(): List<Int>

}

class HorizontalPath(
    private val image: BufferedImage,
    private val nodeTable: MutableMap<Coors, Node>,
) : Path {

    override fun path(): List<Int> =
        sequence {
            var node = (0 until image.height)
                .map { y -> Coors(image.width - 1, y) }
                .mapNotNull { nodeTable[it] }
                .minByOrNull { it.length }

            while (node != null) {
                if (node.coors.y >= 0) yield(node.coors.y)
                node = node.origin
            }
        }.toList().reversed()

}

class VerticalPath(
    private val image: BufferedImage,
    private val nodeTable: MutableMap<Coors, Node>,
) : Path {

    override fun path(): List<Int> =
        sequence {
            var node = (0 until image.width)
                .map { x -> Coors(x, image.height - 1) }
                .mapNotNull { nodeTable[it] }
                .minByOrNull { it.length }

            while (node != null) {
                if (node.coors.x >= 0) yield(node.coors.x)
                node = node.origin
            }
        }.toList().reversed()

}

class Seam(
    private val image: BufferedImage,
    private val seamMode: SeamMode
) {

    fun seam(): List<Int> {
        val nodeTable = mutableMapOf<Coors, Node>()
        val root = RootNode(image, nodeTable, seamMode)
        val queue = LinkedList<Node>()
        queue.add(root)
        while (queue.isNotEmpty()) {
            val node = queue.removeFirst()
            val children = node.children
            children.forEach { it.updateLength(node) }
            if (queue.isEmpty()) {
                when (seamMode) {
                    SeamMode.HORIZONTAL -> {
                        val x = node.coors.x + 1
                        if (x < image.width) {
                            (0 until image.height).forEach { y ->
                                queue.add(nodeTable[Coors(x, y)]!!)
                            }
                        }
                    }
                    SeamMode.VERTICAL -> {
                        val y = node.coors.y + 1
                        if (y < image.height) {
                            (0 until image.width).forEach { x ->
                                queue.add(nodeTable[Coors(x, y)]!!)
                            }
                        }
                    }
                }
            }
//            queue.addAll(children.filter { !queue.contains(it) })
        }

        return when (seamMode) {
            SeamMode.HORIZONTAL -> HorizontalPath(image, nodeTable).path()
            SeamMode.VERTICAL -> VerticalPath(image, nodeTable).path()
        }
    }

}
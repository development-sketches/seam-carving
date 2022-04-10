package seamcarving

import java.awt.image.BufferedImage
import kotlin.math.max
import kotlin.math.min

class HorizontalChildCoors(
    private val image: BufferedImage,
    private val coors: Coors
) : Iterable<Coors> {

    override fun iterator(): Iterator<Coors> {
        val x = coors.x + 1
        if (x >= image.width) return emptyList<Coors>().iterator()
        return ((max(0, coors.y - 1)..min(image.height - 1, coors.y + 1)))
            .map { y -> Coors(x, y) }.iterator()
    }

}

class VerticalChildCoors(
    private val image: BufferedImage,
    private val coors: Coors
) : Iterable<Coors> {

    override fun iterator(): Iterator<Coors> {
        val y = coors.y + 1
        if (y >= image.height) return emptyList<Coors>().iterator()
        return ((max(0, coors.x - 1)..min(image.width - 1, coors.x + 1)))
            .map { x -> Coors(x, y) }.iterator()
    }

}

class ChildNode(
    private val image: BufferedImage,
    private val nodeTable: MutableMap<Coors, Node>,
    override val coors: Coors,
    private val seamMode: SeamMode
) : Node {

    override var length: Double = Double.POSITIVE_INFINITY

    override var origin: Node? = null
        private set

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Node) return false
        return coors == other.coors
    }

    override fun hashCode(): Int = coors.hashCode()

    private fun childCoors(): Iterable<Coors> =
        when (seamMode) {
            SeamMode.HORIZONTAL -> HorizontalChildCoors(image, coors)
            SeamMode.VERTICAL -> VerticalChildCoors(image, coors)
        }

    override val children: Collection<Node>
        get() {
            return childCoors().map { childCoors ->
                nodeTable.computeIfAbsent(childCoors) { ChildNode(image, nodeTable, childCoors, seamMode) }
            }.sortedBy { it.energy }
        }

    override val energy: Double by lazy { energy() }

    private fun energy(): Double = Energy(xGradient(), yGradient()).value

    private fun yGradient(): Gradient {
        val y = max(1, min(image.height - 2, coors.y))
        return Gradient(
            ColorDiff(image.getRGB(coors.x, y - 1), image.getRGB(coors.x, y + 1))
        )
    }

    private fun xGradient(): Gradient {
        val x = max(1, min(image.width - 2, coors.x))
        return Gradient(
            ColorDiff(image.getRGB(x - 1, coors.y), image.getRGB(x + 1, coors.y))
        )
    }

    override fun updateLength(parent: Node) {
        val newLen = parent.length + energy
        if (newLen < length) {
            origin = parent
            length = newLen
        }
    }

}
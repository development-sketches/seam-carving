package seamcarving

import java.awt.image.BufferedImage

class RootNode(
    private val image: BufferedImage,
    private val nodeTable: MutableMap<Coors, Node>,
    private val seamMode: SeamMode
) : Node {

    private fun childCoors(): Collection<Coors> =
        when (seamMode) {
            SeamMode.HORIZONTAL -> (0 until image.height).map { Coors(0, it) }
            SeamMode.VERTICAL -> (0 until image.width).map { Coors(it, 0) }
        }

    override val children: Collection<Node>
        get() = childCoors().map { childCoors ->
            nodeTable.computeIfAbsent(childCoors) { ChildNode(image, nodeTable, childCoors, seamMode) }
        }.sortedBy { it.energy }

    override val coors: Coors = Coors(-1, -1)

    override val energy: Double = 0.0

    override val length: Double = 0.0

    override val origin: Node? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Node) return false
        return coors == other.coors
    }

    override fun hashCode(): Int = coors.hashCode()

    override fun updateLength(parent: Node) {
        throw UnsupportedOperationException()
    }

}
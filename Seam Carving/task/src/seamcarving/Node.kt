package seamcarving

interface Node {

    val children: Collection<Node>

    val coors: Coors

    val energy: Double

    val length: Double

    val origin: Node?

    fun updateLength(parent: Node)
}

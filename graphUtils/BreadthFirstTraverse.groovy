public class BreadthFirstTraverse {
    def traversedOrder = []
    def nodesVisited = [:]
    def nodeList = [:]
    def nodesToVisit = []

    def traverseGraph(def startNode, def graph = [:]) {
        nodeList = graph
        nodesToVisit.push(startNode)
        return doBFT()
    }

    def doBFT() {
        if (nodesToVisit) {
            def frontNode = nodesToVisit.first()
            if (! nodesVisited[frontNode]) {
                traversedOrder.push(frontNode)
                nodesVisited[frontNode] = true
                addAdjNodes(nodeList[frontNode])
            }
            nodesToVisit.removeAt(0)
            doBFT()
        } else {
            return traversedOrder
        }
    }

    def addAdjNodes(def adjList = []) {
        if(adjList) {
            adjList.each { node ->
                if (! nodesVisited[node]) {
                    nodesToVisit.push(node)
                }
            }
        }
    }

}

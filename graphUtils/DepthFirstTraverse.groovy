public class DepthFirstTraverse {
    def traversedOrder = []
    def nodesVisited = [:]
    def nodeList = [:]
    def nodesToVisit = []

    def traverseGraph(def startNode, def graph = [:]) {
        nodeList = graph
        nodesToVisit.push(startNode)
        return doDFT()
    }

    def doDFT() {
        if (nodesToVisit) {
            def frontNode = nodesToVisit.pop()
            if (! nodesVisited[frontNode]) {
                traversedOrder.push(frontNode)
                nodesVisited[frontNode] = true
                //Order is important here we need the adjacent nodes loaded first for back tracking.
                addAdjNodes(nodeList[frontNode])
                addFirstChildNode(frontNode)
            }
            doDFT()
        } else {
            return traversedOrder
        }
    }

    def addFirstChildNode(def node) {
        if(nodesToVisit) {
            def childNodes = nodeList[nodesToVisit.last()]
            //Nesting here to prevent issue with last() when a list is empty
            if (childNodes) {
                if (! nodesVisited[childNodes.first()]) {
                    nodesToVisit.plus(nodesToVisit.size() - 1, [childNodes.first()])
                }
            }
        }
    }

    def addAdjNodes(def adjList = []) {
        def adjNodes = []
        if(adjList) {
            adjList.each { node ->
                if (! nodesVisited[node]) {
                    adjNodes.push(node)
                }
            }
            if (adjNodes) {
                nodesToVisit.addAll(adjNodes.reverse())
            }
        }
    }

}

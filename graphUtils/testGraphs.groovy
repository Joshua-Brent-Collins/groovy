import BreadthFirstTraverse
import DepthFirstTraverse

public static main(String[] args) {
    def graph = [
        1:[5,2,3],
        2:[7,6,1],
        3:[4,5,2],
        4:[1,2,3],
        5:[1],
        6:[7],
        7:[1,2,3,4,5,6,7]
    ]

    //println testBFT(graph)
    println testDFT(graph)
}

def testBFT(def graph) {
    def bft = new BreadthFirstTraverse()
    return bft.traverseGraph(7, graph)
}

def testDFT(def graph) {
    def dft = new DepthFirstTraverse()
    return dft.traverseGraph(4, graph)
}

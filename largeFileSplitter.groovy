
public class FileSplitter {

    private fileBuffers = [:]

    def readFile(def path, def maxLineCount = 15000) {
        def fileBuffer = []
        def lrgFile = new File(path).readLines()
        def maxLineCountIndex = 0
        def bufferIndex = 0
        lrgFile.eachWithIndex { line, index ->
            if(maxLineCountIndex < maxLineCount && index < lrgFile.size()) {
                fileBuffer.add(line)
                maxLineCountIndex++
            }

            if (maxLineCountIndex >= maxLineCount || index >= (lrgFile.size() - 1)) {
                fileBuffers[bufferIndex] =  fileBuffer
                fileBuffer = []
                bufferIndex++
                maxLineCountIndex = 0
            }
        }
    }

    def writeFile(def path, def buffers = fileBuffers, def nameSuffix = "splitFile") {
        buffers.each { key, value ->
            def tmpFile = new File(path + nameSuffix + "${key}")
            value.each { line ->
                tmpFile.append(line + "\n")
            }
            println "Done Writing ${value.size()} line(s)."
        }

    }

    def getBuffers() {
        return fileBuffers
    }

}



public static main(String[] args) {

    def splitter = new FileSplitter()

    splitter.readFile(args[0])
    splitter.getBuffers().each { key, value ->
        println "${key} : ${value.size()}"
    }
    splitter.writeFile(args[1],splitter.getBuffers(),"myFile")

}


def fname = args[0]
def logs = new File(fname)

def lineBuffer = []
def newBlock = false
def criticalPriority = false
def priorityLevel = "[Priority: 1]"

logs.each { line ->
    if (line.contains("[**]")) {
        newBlock = true
    }
    if (line.contains(priorityLevel)) {
        criticalPriority = true
    }
    if ((line == '') && criticalPriority) {
        newBlock = false
        criticalPriority = false
        println lineBuffer.join('\n')
        println " "
        lineBuffer = []
    } else if ((line == '') && ! criticalPriority) {
        lineBuffer = []
    }
    if (newBlock) {
        lineBuffer.push(line)
    }
}

return 0
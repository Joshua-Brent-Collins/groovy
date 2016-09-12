
public class CsvToInsertConverter {

    def TABLE_NAME
    def FIELD_TYPES = []
    def FIELD_NAMES = []

    private def csvFileData

    CsvToInsertConverter(def tableName, def fieldTypes ,def fieldNames = []) {
        TABLE_NAME = tableName
        FIELD_NAMES = fieldNames
        FIELD_TYPES = fieldTypes
    }

    def readFile(String path) {
        println "Reading File."
        csvFileData = new File(path).readLines()
        csvFileData = csvFileData.collect { line ->
            line = line.split(',')
        }
    }

    def replaceNulls() {
        println "Replacing Nulls."
        csvFileData = csvFileData.collect { line ->
            def index = 0
            line = line.collect { field ->
                if (! field) {
                    field = "null"
                }
                index++
                return field
            }
        }
    }

    def applyTypeWrappers() {
        println "Applying Type Wrappers."
        csvFileData = csvFileData.collect { line ->
            def index = 0
            line = line.collect { field ->
                switch (FIELD_TYPES[index]) {
                    case "timestamp":
                        field = "'" + field + "'"
                        break
                    case "varchar":
                        field = "'" + field + "'"
                        break
                    case "bool":
                        field = "'" + field + "'"
                        break
                }
                index++
                return field
            }
        }
    }

    def convertToInserts(String path, String dest) {
        def insertFile = new File(dest)
        readFile(path)
        applyTypeWrappers()
        replaceNulls()
        println "Generating Inserts."
        csvFileData.each { line ->
            insertFile.append("INSERT INTO ${TABLE_NAME} (${FIELD_NAMES.join(',')}) values (${line.join(',')});\n")
        }
        println "Done."
    }

}

public static main(String[] args) {

    def fieldTypes = []
    def fieldNames = []

    def csvConverter = new CsvToInsertConverter("your_table_here", fieldTypes, fieldNames)

    csvConverter.convertToInserts(args[0], args[1])

}

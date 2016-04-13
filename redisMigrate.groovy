@Grapes([
    @Grab(group='redis.clients', module='jedis', version='2.8.1'),
    @GrabConfig(systemClassLoader=true)
])
import redis.clients.jedis.*

def TIME_OUT_MS = 5000
def PORT = 6379
def KEY_PATTEREN = '*'

def SOURCE_CACHE_URL=''
def DESTINATION_CACHE_URL=''

def errorCount = 0

Jedis sourceCache = new Jedis(SOURCE_CACHE_URL,PORT,TIME_OUT_MS)
Jedis destinationCache = new Jedis(DESTINATION_CACHE_URL,PORT,TIME_OUT_MS)

sourceCache.keys(KEY_PATTEREN).each { key ->

    def keyType = sourceCache.type(key)
    def ttl = sourceCache.pttl(key)

    //Small fix here since restore will not take a negative for keys which do not expire.
    if (ttl < 0) {
        ttl = 0
    }

    switch (keyType) {

        case "string":
        case "list":
        case "set":
        case "zset":
        case "hash":
            println "Migrating key ${key}"
            def keyDump = sourceCache.dump(key)
            try {
                destinationCache.restore(key,ttl.intValue(),keyDump)
                if (destinationCache.exists(key)) {
                    println "The key was migrated to the destination cache."
                } else {
                    println "Key could not be found in the destination cache migration failed."
                    errorCount += 1
                }
            } catch (Exception e) {
                if (e.toString().contains("BUSYKEY")) {
                    println "Key already exists in cache and will not be updated."
                } else {
                    println "Unknow exception occured during migration ensure all key data is correct!"
                    errorCount += 1
                }
            }

            break

        default:
            println "Unknown type key will not be migrated type:${keyType} Key:${key}"
    }

}

if (errorCount > 0) {
    println "Migration finished with ${errorCount} error(s)! Please ensure the integrity of all key data."
} else {
    println "Migration finished with no errors."
}

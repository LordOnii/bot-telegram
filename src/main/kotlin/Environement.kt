import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths
// https://github.com/hawolt/twitch-bot/blob/main/src/main/java/com/hawolt/Environment.java
class Environment : HashMap<String?, String?>() {
    init {
        // get the current running directory
        val dir = System.getProperty("user.dir")
        // obtain path to hopefully present .env file
        val path = Paths.get(".env")
        try {
            // read every line within the file
            // put the values into our map
            val lines = Files.readAllLines(path)
            for (line in lines) {
                val pair = line.split("=".toRegex(), limit = 2).toTypedArray()
                if (pair.size != 2) continue
                put(pair[0], pair[1])
            }
        } catch (e: IOException) {
            System.err.println("Failed to read file '.env' in '$dir'.")
        }
    }

    val isProperlyConfigured: Boolean
        // method to check if all values we need are configured
        get() = containsKey("ACCESS_TOKEN") && containsKey("USERNAME")
}
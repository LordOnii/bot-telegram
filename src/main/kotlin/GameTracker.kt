import java.util.*

class GameTracker {
    var passwords: Stack<String> = Stack<String>()
    var fail = 0
    var currentLevel = 0

    init {
        // Pusing passwords in the stack form last to first
        passwords.push("8")
        passwords.push("Gilles")
        passwords.push("Macron")
        passwords.push("iambarelyhuman")
    }

    public fun submitPassword(attempt: String): Int {
        if (passwords.isEmpty()) { return -1 }

        if (passwords.peek().lowercase() == attempt.lowercase()) {
            passwords.pop()
            currentLevel++
            return 0
        } else {
            fail++
            return 1
        }
    }

    public fun getCurrentGame(): MutableList<String> {
        val answer = mutableListOf<String>()
        when (currentLevel) {
            0 -> createGameInstruction(
                "You are currently in level $currentLevel",
                "To submit a password for the next level please use `/submit attempted_password`",
                "The first password is `iambarelyhuman`",
                list = answer
            )

            1 -> createGameInstruction(
                "You are currently in level $currentLevel",
                "The next password is pretty simple, it is only the last name of the official France ennemi",
                "Be careful with your answer because it is not Hitler nor the world's cup referee",
                list = answer
            )

            2 -> createGameInstruction(
                "You are currently in level $currentLevel",
                "The next password is the first name of the second witer of \"Calculateurs, calculs, calculabilité\"",
                "Pretty specific right ? I don't really care it is still the password",
                list = answer
            )

            3 -> createGameInstruction(
                "You are currently in level $currentLevel",
                "Next password is the last digit of pi",
                "That's it, one of the 10 possible",
                list = answer
            )

            else -> {
                createGameInstruction(
                    "You are currently in level $currentLevel",
                    "It seems like you have completed the game, congratulation",
                    "Anyways it was just $currentLevel questions not a big deal",
                    list = answer
                )
            }
        }
        return answer
    }

    private fun createGameInstruction(vararg instructions: String, list: MutableList<String>) {
        for (inst in instructions) {
            list.add(inst)
        }
    }
}
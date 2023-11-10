import dev.inmo.tgbotapi.types.ChatId
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update

class Bot : TelegramLongPollingBot() {
    val env = Environment()
    val game = GameTracker()

    override fun getBotToken(): String {
        return env["ACCESS_TOKEN"].toString().replace("'", "")
    }

    override fun getBotUsername(): String {
        return env["USERNAME"].toString().replace("'", "")
    }

    override fun onUpdateReceived(update: Update?) {
        val messageReceived = update?.message?.text
        val id = update?.message?.chatId
        println("> $id to you : $messageReceived")

        if (messageReceived != null) {
            if (messageReceived.startsWith("/")) {
                if (id != null) {
                    command(messageReceived, id)
                }
            }
        }
    }

    private fun command(message: String, id: Long) {
        when {
            message == "/start" -> {
                sendMessage(id, "Congratulation you started our game")
                sendMessage(id, game.getCurrentGame())
            }

            message == "/help" -> {
                sendMessage(id, "`/start` -> start the game")
                sendMessage(id, "`/submit` -> submit a password for the current level")
                sendMessage(id, "`/help` -> get this menu")
            }

            message.startsWith("/submit") -> {
                val attempt = message.split(' ')[1]
                when (game.submitPassword(attempt)) {
                    0 -> { // Correct password
                        sendMessage(id, "Correct ! Here is the next level")
                        sendMessage(id, game.getCurrentGame())
                    }
                    1 -> { // Incorrect password
                        sendMessage(id, "I won't let you pass with shitty passwords like this one")
                    }
                    -1 -> {
                        sendMessage(id, "You don't have any level left to do, stop trying")
                    }


                }

            }

            else -> {
                sendMessage(id, "Unknown command, please use `/help`")
            }
        }
    }

    private fun sendMessage(to: Long, text: String) {
        val response = SendMessage()
        response.setChatId(to.toString())
        response.text = text
        execute(response)
    }

    private fun sendMessage(to: Long, texts: MutableList<String>) {
        val response = SendMessage()
        response.setChatId(to.toString())
        for (text in texts) {
            response.text = text
            println("< to $to : $text")
            execute(response)
        }
    }
}




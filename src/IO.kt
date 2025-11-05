import java.math.BigDecimal

interface IO {
    fun println(msg: String)
    fun readLine(prompt: String): String
    fun readMoney(prompt: String): Money
    fun readOptionalMoney(prompt: String): Money?
    fun readMoneyOrCancel(prompt: String): Money?          // NEW
    fun readFactor(prompt: String): BigDecimal
    fun chooseFrom(prompt: String, options: List<String>): Int
}

class ConsoleIO : IO {
    override fun println(msg: String) {
        kotlin.io.println(msg)
    }

    override fun readLine(prompt: String): String {
        kotlin.io.print(prompt)
        return readlnOrNull()?.trim().orEmpty()
    }

    override fun readMoney(prompt: String): Money {
        while (true) {
            val s = readLine("$prompt (e.g., 12.50): ")
            try {
                return Money.ofPounds(s)
            } catch (_: Exception) {
                println("Invalid amount. Use two decimals, e.g. 12.50")
            }
        }
    }

    override fun readOptionalMoney(prompt: String): Money? {
        val s = readLine("$prompt (blank = keep): ")
        if (s.isBlank()) return null
        return try {
            Money.ofPounds(s)
        } catch (_: Exception) {
            println("Invalid amount. Skipping change."); null
        }
    }

    override fun readMoneyOrCancel(prompt: String): Money? {
        val s = readLine("$prompt (type CANCEL to abort): ")
        if (s.equals("CANCEL", ignoreCase = true)) return null
        return try {
            Money.ofPounds(s)
        } catch (_: Exception) {
            println("Invalid amount. Try again."); readMoneyOrCancel(prompt)
        }
    }

    override fun readFactor(prompt: String): BigDecimal {
        while (true) {
            val s = readLine("$prompt (e.g., 0.90 for -10%, 1.15 for +15%): ")
            try {
                val f = BigDecimal(s)
                require(f > BigDecimal.ZERO)
                return f
            } catch (_: Exception) {
                println("Invalid factor. Must be a number > 0.")
            }
        }
    }

    override fun chooseFrom(prompt: String, options: List<String>): Int {
        require(options.isNotEmpty())
        // Just show the numbered options, no "Choose an option:" line at the top
        options.forEachIndexed { i, it -> println("${i + 1}. $it") }

        while (true) {
            val s = readLine("Choose an option: ")
            val n = s.toIntOrNull()
            if (n != null && n in 1..options.size) return n - 1
            println("Invalid choice.")
        }
    }
}

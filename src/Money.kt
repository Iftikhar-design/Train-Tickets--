import java.math.BigDecimal
import java.math.RoundingMode

@JvmInline
value class Money(val amount: BigDecimal) : Comparable<Money> {
    operator fun plus(other: Money): Money =
        Money(this.amount.add(other.amount).setScale(2, RoundingMode.HALF_UP))

    operator fun minus(other: Money): Money =
        Money(this.amount.subtract(other.amount).setScale(2, RoundingMode.HALF_UP))

    operator fun times(factor: BigDecimal): Money =
        Money(this.amount.multiply(factor).setScale(2, RoundingMode.HALF_UP))

    override fun compareTo(other: Money): Int = this.amount.compareTo(other.amount)

    fun isPositive(): Boolean = this.amount > BigDecimal.ZERO
    fun isZero(): Boolean = this.amount.compareTo(BigDecimal.ZERO) == 0

    override fun toString(): String =
        "£" + amount.setScale(2, RoundingMode.HALF_UP).toPlainString()

    companion object {
        val ZERO = Money(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP))

        fun ofPounds(input: String): Money {
            val cleaned = input.trim().removePrefix("£").replace(",", "")
            val bd = BigDecimal(cleaned).setScale(2, RoundingMode.HALF_UP)
            require(bd >= BigDecimal.ZERO) { "Money cannot be negative." }
            return Money(bd)
        }

        fun of(value: Double): Money =
            Money(BigDecimal(value).setScale(2, RoundingMode.HALF_UP))
    }
}

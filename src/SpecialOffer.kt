import java.time.LocalDate

data class SpecialOffer(
    val id: Int,
    val stationName: String,
    val description: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val discountPercent: Int    // e.g. 10 means 10% off
)

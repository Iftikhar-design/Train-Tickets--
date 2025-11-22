import java.time.LocalDate

class SpecialOfferManager {

    private val offers = mutableListOf<SpecialOffer>()
    private var nextId = 1

    fun addOffer(
        stationName: String,
        description: String,
        startDate: LocalDate,
        endDate: LocalDate,
        discountPercent: Int
    ): SpecialOffer {
        val offer = SpecialOffer(
            id = nextId++,
            stationName = stationName,
            description = description,
            startDate = startDate,
            endDate = endDate,
            discountPercent = discountPercent
        )
        offers.add(offer)
        return offer
    }

    fun searchOffers(stationName: String?): List<SpecialOffer> {
        return if (stationName.isNullOrBlank()) {
            offers.toList()
        } else {
            offers.filter { it.stationName.equals(stationName, ignoreCase = true) }
        }
    }

    fun deleteOffer(id: Int): Boolean {
        val it = offers.iterator()
        while (it.hasNext()) {
            val offer = it.next()
            if (offer.id == id) {
                it.remove()
                return true
            }
        }
        return false
    }

    /**
     * Find the first active offer for this station on the given date.
     */
    fun findActiveOffer(stationName: String, onDate: LocalDate): SpecialOffer? {
        return offers.firstOrNull { offer ->
            offer.stationName.equals(stationName, ignoreCase = true) &&
                    !onDate.isBefore(offer.startDate) &&
                    !onDate.isAfter(offer.endDate)
        }
    }
}

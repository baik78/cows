package test.mts.ru.testcows.dataflow

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*
import java.util.Calendar.YEAR

enum class Breed {
    BreedOne, BreedTwo, BreedThree;

    companion object {
        fun from(index: Int) : Breed =
                when(index) {
                    0 -> BreedOne
                    1 -> BreedTwo
                    2 -> BreedThree
                    else -> BreedOne
                }
    }

    fun index(): Int =
        when(this) {
            BreedOne -> 0
            BreedTwo -> 1
            BreedThree -> 2
        }

}

enum class Suit {
    SuitOne, SuitTwo, SuitThree;

    companion object {
        fun from(index: Int) : Suit =
                when(index) {
                    0 -> SuitOne
                    1 -> SuitTwo
                    2 -> SuitThree
                    else -> SuitOne
                }
    }
    fun index(): Int =
            when(this) {
                SuitOne -> 0
                SuitTwo -> 1
                SuitThree -> 2
            }
}

fun getCalendar(date: Date): Calendar {
    val cal = Calendar.getInstance(Locale.US)
    cal.time = date
    return cal
}

open class Cow : RealmObject() {
    @PrimaryKey
    var Id : String = ""
    private var breed : String = Breed.BreedOne.toString()
    private var suit : String = Suit.SuitOne.toString()
    var beath : Date  = Date()
    var chartDate : RealmList<ChartData> = RealmList()

    fun saveBreed(br: Breed) {
        this.breed = br.toString()
    }

    fun getBreed() : Breed {
        return Breed.valueOf(this.breed)
    }

    fun saveSuit(br: Suit) {
        this.suit = br.toString()
    }

    fun getSuit() : Suit {
        return Suit.valueOf(this.suit)
    }

    fun ages() : Int {
        val a = getCalendar(this.beath)
        val b = getCalendar(Date())
        return b.get(YEAR) - a.get(YEAR)
    }

}

open class ChartData : RealmObject() {
    var date : Date = Date()
    var weight : Int = 0
    var nada : Int = 0
    var mdg : Int = 0
}
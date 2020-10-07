package com.sushakov.unolingo.data.word

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Word(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo val lang: String,
    @ColumnInfo val text: String
) : Serializable, Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        p0?.writeLong(id)
        p0?.writeString(lang)
        p0?.writeString(text)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Word> {
        override fun createFromParcel(parcel: Parcel): Word {
            return Word(parcel)
        }

        override fun newArray(size: Int): Array<Word?> {
            return arrayOfNulls(size)
        }

        // Edit distance with Wagner-Fischer algorithm
        // See https://en.wikipedia.org/wiki/Wagner%E2%80%93Fischer_algorithm
        fun editDistance(a: Word, b: Word): Int {
            val m = a.text.length
            val n = b.text.length

            val d: Array<IntArray> = Array(m + 1) {
                IntArray(n + 1) { 0 }
            } // set all (m+1) * (n+1) elements to zero

            for (i in 1..m) d[i][0] = i
            for (j in 1..n) d[0][j] = j

            for (j in 0 until n) {
                for (i in 0 until m) {
                    val letter1 = a.text[i]
                    val letter2 = b.text[j]
                    val substitutionCost = if (letter1 == letter2) {
                        0
                    } else {
                        1
                    }

                    val deletion = d[i][j + 1] + 1
                    val insertion = d[i + 1][j] + 1
                    val substitution = d[i][j] + substitutionCost

                    d[i + 1][j + 1] = listOf(
                        deletion,
                        insertion,
                        substitution
                    ).min()!!
                }
            }

            return d[m][n]
        }
    }
}


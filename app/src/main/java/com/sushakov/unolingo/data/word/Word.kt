package com.sushakov.unolingo.data.word

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.squareup.moshi.Json

@Entity
data class Word(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo val lang: String,
    @ColumnInfo val text: String
) {

    companion object {
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
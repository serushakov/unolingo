package com.sushakov.unolingo.data.word

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Word(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo val lang: String,
    @ColumnInfo val text: String
) {
// TODO: Setup relationship data

//    private val translations = mutableMapOf<String, MutableSet<Word>>()
//
//    fun addTranslation(t: Word) {
//        (translations.getOrPut(t.lang) { mutableSetOf() }) += t
//    }
//
//    fun addTranslations(ts: Set<Word>) {
//        ts.forEach(::addTranslation)
//    }
//
//    fun isTranslation(word: Word): Boolean {
//        return translations.getOrElse(word.lang) {
//            return false
//        }.contains(word)
//    }

//    fun translationCount(lang: String): Int = translations.getOrDefault(lang, mutableSetOf()).size

    // Edit distance with Wagner-Fischer algorithm
    // See https://en.wikipedia.org/wiki/Wagner%E2%80%93Fischer_algorithm
    fun editDistance(another: Word): Int {
        val m = this.text.length
        val n = another.text.length

        val d: Array<IntArray> = Array(m + 1) {
            IntArray(n + 1) { 0 }
        } // set all (m+1) * (n+1) elements to zero

        for (i in 1..m) d[i][0] = i
        for (j in 1..n) d[0][j] = j

        for (j in 0 until n) {
            for (i in 0 until m) {
                val letter1 = text[i]
                val letter2 = another.text[j]
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
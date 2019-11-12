package com.adindaef.ohquizapp.model

class Category {
    companion object{
        val MATEMATIKA = "Matematika"
        val IPA = "Ilmu Pengetahuan Alam"
        val BAHASA_INDONESIA = "Bahasa Indonesia"
    }

    var id: Int = 0
    var name: String = ""

    constructor(){}

    constructor(name: String) {
        this.id = id
        this.name = name
    }

    override fun toString(): String {
        return name
    }
}
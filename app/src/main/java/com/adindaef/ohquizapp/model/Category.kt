package com.adindaef.ohquizapp.model

class Category {
    companion object{
        val MATEMATIKA = 1
        val IPA = 2
        val BAHASA_INDONESIA = 3
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
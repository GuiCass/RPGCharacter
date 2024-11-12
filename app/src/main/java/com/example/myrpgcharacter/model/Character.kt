package com.example.myrpgcharacter.model

data class Character(
    val id: Int = 0,
    val name: String,
    val race: String,
    val classe: String,
    val notes: String,
    val strength: Int,
    val dexterity: Int,
    val constitution: Int,
    val intelligence: Int,
    val wisdom: Int,
    val charisma: Int,
    val image: ByteArray? = null,
    val life: Int,
    val mana: Int,
    val overlife: Int,
    val armor: Int,
    val maxLife: Int,
    val maxMana: Int,
    val lvl: Int
)
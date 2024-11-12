package com.example.myrpgcharacter.databaseHelper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues
import android.database.Cursor
import com.example.myrpgcharacter.model.Ability
import com.example.myrpgcharacter.model.Character

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "CharacterDB"
        private const val DATABASE_VERSION = 1

        private const val COLUMN_IMAGE = "image"
        const val TABLE_CHARACTERS = "characters"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_RACE = "race"
        const val COLUMN_CLASS = "classe"
        const val COLUMN_NOTES = "notes"
        const val COLUMN_STRENGTH = "strength"
        const val COLUMN_DEXTERITY = "dexterity"
        const val COLUMN_CONSTITUTION = "constitution"
        const val COLUMN_INTELLIGENCE = "intelligence"
        const val COLUMN_WISDOM = "wisdom"
        const val COLUMN_CHARISMA = "charisma"
        const val COLUMN_LIFE = "life"
        const val COLUMN_MANA = "mana"
        const val COLUMN_OVERLIFE = "overlife"
        const val COLUMN_AMOR = "armor"
        const val COLUMN_MAX_LIFE = "max_life"
        const val COLUMN_MAX_MANA = "max_mana"
        const val COLUMN_LVL = "lvl"

        const val TABLE_ABILITIES = "Abilities"
        const val COLUMN_ABILITY_ID = "id"
        const val COLUMN_ABILITY_TITLE = "title"
        const val COLUMN_ABILITY_DESCRIPTION = "description"
        const val COLUMN_CHARACTER_ID = "characterId"

    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = ("CREATE TABLE $TABLE_CHARACTERS ("
                + "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "$COLUMN_NAME TEXT, "
                + "$COLUMN_RACE TEXT, "
                + "$COLUMN_CLASS TEXT, "
                + "$COLUMN_NOTES TEXT, "
                + "$COLUMN_STRENGTH INTEGER, "
                + "$COLUMN_DEXTERITY INTEGER, "
                + "$COLUMN_CONSTITUTION INTEGER, "
                + "$COLUMN_INTELLIGENCE INTEGER, "
                + "$COLUMN_WISDOM INTEGER, "
                + "$COLUMN_CHARISMA INTEGER, "
                + "$COLUMN_IMAGE BLOB, "
                + "$COLUMN_LIFE INTEGER, "
                + "$COLUMN_MANA INTEGER, "
                + "$COLUMN_OVERLIFE INTEGER, "
                + "$COLUMN_AMOR INTEGER, "
                + "$COLUMN_MAX_LIFE INTEGER, "
                + "$COLUMN_MAX_MANA INTEGER, "
                + "$COLUMN_LVL INTEGER)"
                )
        db.execSQL(createTable)

        // Criação da tabela de habilidades
        val createAbilitiesTable = ("CREATE TABLE Abilities ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "title TEXT NOT NULL, "
                + "description TEXT, "
                + "characterId INTEGER, "
                + "FOREIGN KEY(characterId) REFERENCES $TABLE_CHARACTERS($COLUMN_ID) ON DELETE CASCADE)"
                )
        db.execSQL(createAbilitiesTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS Abilities")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_CHARACTERS")
        onCreate(db)
    }

    fun addCharacter(
        name: String,
        race: String,
        classe: String,
        notes: String,
        strength: Int,
        dexterity: Int,
        constitution: Int,
        intelligence: Int,
        wisdom: Int,
        charisma: Int,
        image: ByteArray?,
        life: Int,
        mana: Int,
        overlife: Int,
        armor: Int,
        max_life: Int,
        max_mana: Int,
        lvl: Int
    ): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, name)
            put(COLUMN_RACE, race)
            put(COLUMN_CLASS, classe)
            put(COLUMN_NOTES, notes)
            put(COLUMN_STRENGTH, strength)
            put(COLUMN_DEXTERITY, dexterity)
            put(COLUMN_CONSTITUTION, constitution)
            put(COLUMN_INTELLIGENCE, intelligence)
            put(COLUMN_WISDOM, wisdom)
            put(COLUMN_CHARISMA, charisma)
            put(COLUMN_IMAGE, image)
            put(COLUMN_LIFE, life)
            put(COLUMN_MANA, mana)
            put(COLUMN_OVERLIFE, overlife)
            put(COLUMN_AMOR, armor)
            put(COLUMN_MAX_LIFE, max_life)
            put(COLUMN_MAX_MANA, max_mana)
            put(COLUMN_LVL, lvl)
        }

        val result = db.insert(TABLE_CHARACTERS, null, values)
        db.close()
        return result
    }

    fun updateCharacter(
        id: Int,
        name: String,
        race: String,
        classe: String,
        notes: String,
        strength: Int,
        dexterity: Int,
        constitution: Int,
        intelligence: Int,
        wisdom: Int,
        charisma: Int,
        image: ByteArray?,
        life: Int,
        mana: Int,
        overlife: Int,
        armor: Int,
        max_life: Int,
        max_mana: Int,
        lvl: Int
    ): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, name)
            put(COLUMN_RACE, race)
            put(COLUMN_CLASS, classe)
            put(COLUMN_NOTES, notes)
            put(COLUMN_STRENGTH, strength)
            put(COLUMN_DEXTERITY, dexterity)
            put(COLUMN_CONSTITUTION, constitution)
            put(COLUMN_INTELLIGENCE, intelligence)
            put(COLUMN_WISDOM, wisdom)
            put(COLUMN_CHARISMA, charisma)
            put(COLUMN_IMAGE, image)
            put(COLUMN_LIFE, life)
            put(COLUMN_MANA, mana)
            put(COLUMN_OVERLIFE, overlife)
            put(COLUMN_AMOR, armor)
            put(COLUMN_MAX_LIFE, max_life)
            put(COLUMN_MAX_MANA, max_mana)
            put(COLUMN_LVL, lvl)
        }

        // Atualizar o registro onde o ID corresponde ao ID fornecido
        return db.update(TABLE_CHARACTERS, values, "$COLUMN_ID = ?", arrayOf(id.toString()))
    }

    fun updateCharacter(
        id: Int,
        name: String,
        race: String,
        classe: String,
        notes: String,
        strength: Int,
        dexterity: Int,
        constitution: Int,
        intelligence: Int,
        wisdom: Int,
        charisma: Int,
        life: Int,
        mana: Int,
        overlife: Int,
        armor: Int,
        max_life: Int,
        max_mana: Int,
        lvl: Int
    ): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, name)
            put(COLUMN_RACE, race)
            put(COLUMN_CLASS, classe)
            put(COLUMN_NOTES, notes)
            put(COLUMN_STRENGTH, strength)
            put(COLUMN_DEXTERITY, dexterity)
            put(COLUMN_CONSTITUTION, constitution)
            put(COLUMN_INTELLIGENCE, intelligence)
            put(COLUMN_WISDOM, wisdom)
            put(COLUMN_CHARISMA, charisma)
            put(COLUMN_LIFE, life)
            put(COLUMN_MANA, mana)
            put(COLUMN_OVERLIFE, overlife)
            put(COLUMN_AMOR, armor)
            put(COLUMN_MAX_LIFE, max_life)
            put(COLUMN_MAX_MANA, max_mana)
            put(COLUMN_LVL, lvl)
        }

        // Atualizar o registro onde o ID corresponde ao ID fornecido
        return db.update(TABLE_CHARACTERS, values, "$COLUMN_ID = ?", arrayOf(id.toString()))
    }

    fun getAllCharacters(): List<Character> {
        val characterList = mutableListOf<Character>()
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM $TABLE_CHARACTERS", null)

        if (cursor.moveToFirst()) {
            do {
                val character = Character(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)),
                    race = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RACE)),
                    classe = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CLASS)),
                    notes = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOTES)),
                    strength = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_STRENGTH)),
                    dexterity = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_DEXTERITY)),
                    constitution = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CONSTITUTION)),
                    intelligence = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_INTELLIGENCE)),
                    wisdom = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_WISDOM)),
                    charisma = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CHARISMA)),
                    image = cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_IMAGE)),
                    life = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_LIFE)),
                    mana = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MANA)),
                    overlife = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_OVERLIFE)),
                    armor = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_AMOR)),
                    maxLife = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MAX_LIFE)),
                    maxMana = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MAX_MANA)),
                    lvl = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_LVL))
                )
                characterList.add(character)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return characterList
    }

    fun getCharacterById(id: Int): Character? {
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_CHARACTERS,
            arrayOf(COLUMN_ID, COLUMN_NAME, COLUMN_CLASS, COLUMN_NOTES, COLUMN_RACE, COLUMN_STRENGTH, COLUMN_DEXTERITY, COLUMN_CONSTITUTION, COLUMN_INTELLIGENCE, COLUMN_WISDOM, COLUMN_CHARISMA, COLUMN_IMAGE, COLUMN_LIFE, COLUMN_MANA, COLUMN_OVERLIFE, COLUMN_AMOR, COLUMN_MAX_LIFE, COLUMN_MAX_MANA, COLUMN_LVL),
            "$COLUMN_ID = ?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )

        return if (cursor != null && cursor.moveToFirst()) {
            val character = Character(
                id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)),
                race = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RACE)),
                classe = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CLASS)),
                notes = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOTES)),
                strength = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_STRENGTH)),
                dexterity = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_DEXTERITY)),
                constitution = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CONSTITUTION)),
                intelligence = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_INTELLIGENCE)),
                wisdom = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_WISDOM)),
                charisma = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CHARISMA)),
                image = cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_IMAGE)),
                life = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_LIFE)),
                mana = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MANA)),
                overlife = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_OVERLIFE)),
                armor = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_AMOR)),
                maxLife = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MAX_LIFE)),
                maxMana = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MAX_MANA)),
                lvl = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_LVL))
            )
            cursor.close()
            db.close()
            character
        } else {
            cursor?.close()
            db.close()
            null
        }
    }

    fun getAbilitiesByCharacterId(characterId: Int): List<Ability> {
        val abilities = mutableListOf<Ability>()
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_ABILITIES,
            arrayOf(COLUMN_ABILITY_ID, COLUMN_ABILITY_TITLE, COLUMN_ABILITY_DESCRIPTION, COLUMN_CHARACTER_ID),
            "$COLUMN_CHARACTER_ID = ?",
            arrayOf(characterId.toString()),
            null,
            null,
            null
        )

        if (cursor.moveToFirst()) {
            do {
                val ability = Ability(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ABILITY_ID)),
                    title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ABILITY_TITLE)),
                    description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ABILITY_DESCRIPTION)),
                    characterId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CHARACTER_ID))
                )
                abilities.add(ability)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        // Log para verificar o número de habilidades retornadas
        println("getAbilitiesByCharacterId: ${abilities.size} habilidades encontradas para o personagem $characterId")
        return abilities
    }

    fun updateAbility(ability: Ability): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_ABILITY_TITLE, ability.title)
            put(COLUMN_ABILITY_DESCRIPTION, ability.description)
        }
        return db.update(TABLE_ABILITIES, values, "$COLUMN_ABILITY_ID = ?", arrayOf(ability.id.toString()))
    }

    fun deleteAbility(abilityId: Int): Int {
        val db = this.writableDatabase
        return db.delete(TABLE_ABILITIES, "$COLUMN_ABILITY_ID = ?", arrayOf(abilityId.toString()))
    }


    fun deleteCharacterById(id: Int) {
        val db = this.writableDatabase
        db.delete(TABLE_CHARACTERS, "$COLUMN_ID=?", arrayOf(id.toString()))
        db.close()
    }

    fun addAbility(characterId: Int, ability: Ability): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_ABILITY_TITLE, ability.title)
            put(COLUMN_ABILITY_DESCRIPTION, ability.description)
            put(COLUMN_CHARACTER_ID, characterId)
        }
        val result = db.insert(TABLE_ABILITIES, null, values)
        db.close()
        return result
    }
}
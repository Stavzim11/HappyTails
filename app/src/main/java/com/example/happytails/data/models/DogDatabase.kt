package com.example.happytails.data.models

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


@Database(entities = arrayOf(Dog::class), version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class DogDatabase : RoomDatabase() {

    abstract fun dogDao(): DogDao

    companion object {

        @Volatile
        private var instance: DogDatabase? = null

        fun getDatabase(context: Context) = instance ?: synchronized(DogDatabase::class.java) {
            Room.databaseBuilder(context.applicationContext, DogDatabase::class.java, "items_db")
                .addMigrations(MIGRATION_1_2).build().also { instance = it }

        }

        private val MIGRATION_1_2 = object : Migration(1,2){
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE items ADD COLUMN isFavorite INTEGER DEFAULT 0 NOT NULL")
            }
        }

    }

}
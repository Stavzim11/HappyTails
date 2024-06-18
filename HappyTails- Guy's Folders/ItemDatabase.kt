package com.example.happytails

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


@Database(entities = arrayOf(Item::class), version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class ItemDatabase : RoomDatabase() {

    abstract fun itemDao(): ItemDao

    companion object {

        @Volatile
        private var instance: ItemDatabase? = null

        fun getDatabase(context: Context) = instance ?: synchronized(ItemDatabase::class.java) {


            //Stav needs to change after coroutines the .allowMainThreadQuiries!!!!!!
            Room.databaseBuilder(context.applicationContext, ItemDatabase::class.java, "items_db")
                .addMigrations(MIGRATION_1_2).build().also { instance = it }

        }

        private val MIGRATION_1_2 = object : Migration(1,2){
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE items ADD COLUMN isFavorite INTEGER DEFAULT 0 NOT NULL")
            }
        }

    }

}
package com.example.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.model.Appointment

@Database(entities = [Appointment::class], version = 1, exportSchema = false)
abstract class BarbershopDatabase : RoomDatabase() {
    abstract fun appointmentDao(): AppointmentDao

    companion object {
        @Volatile
        private var INSTANCE: BarbershopDatabase? = null

        fun getDatabase(context: Context): BarbershopDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BarbershopDatabase::class.java,
                    "sharp_scissors_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

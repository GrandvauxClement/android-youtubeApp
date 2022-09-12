package fr.grandvauxclement.bestyoutubeapp.pojo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [YoutubeVideo::class], version = 1, exportSchema = false)
abstract class YoutubeVideoRoomDatabase : RoomDatabase() {

    abstract fun youtubeVideoDao(): YoutubeVideoDao

    companion object {
        @Volatile
        private var INSTANCE: YoutubeVideoRoomDatabase? = null

        fun getDatabase(context: Context): YoutubeVideoRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    YoutubeVideoRoomDatabase::class.java,
                    "youtube_video_database"
                )
                    // Wipes and rebuilds instead of migrating if no Migration object.
                    // Migration is not part of this codelab.
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}
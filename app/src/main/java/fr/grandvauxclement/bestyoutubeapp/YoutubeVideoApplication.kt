package fr.grandvauxclement.bestyoutubeapp

import android.app.Application
import fr.grandvauxclement.bestyoutubeapp.pojo.YoutubeVideoRoomDatabase

class YoutubeVideoApplication: Application() {

    val database: YoutubeVideoRoomDatabase by lazy { YoutubeVideoRoomDatabase.getDatabase(this)}
}
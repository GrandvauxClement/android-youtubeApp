package fr.grandvauxclement.bestyoutubeapp.pojo

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface YoutubeVideoDao {

    @Query("SELECT * FROM youtubeVideo")
    fun getAllYoutubeVideo(): Flow<List<YoutubeVideo>>

    @Query("SELECT * FROM youtubeVideo WHERE id = :id")
    fun getYoutubeVideoById(id: Int): Flow<YoutubeVideo>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(youtubeVideo: YoutubeVideo)

    @Update
    suspend fun update(youtubeVideo: YoutubeVideo)

    @Delete
    suspend fun delete(youtubeVideo: YoutubeVideo)
}
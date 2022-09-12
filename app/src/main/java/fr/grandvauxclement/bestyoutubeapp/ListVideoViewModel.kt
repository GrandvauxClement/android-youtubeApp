package fr.grandvauxclement.bestyoutubeapp

import android.content.ClipData
import android.provider.SyncStateContract.Helpers.update
import androidx.lifecycle.*
import fr.grandvauxclement.bestyoutubeapp.pojo.YoutubeVideoDao
import fr.grandvauxclement.bestyoutubeapp.pojo.YoutubeVideo
import kotlinx.coroutines.launch

class ListVideoViewModel(private val youtubeVideoDao: YoutubeVideoDao): ViewModel() {

    // Cache all items form the database using LiveData.
    val allVideos: LiveData<List<YoutubeVideo>> = youtubeVideoDao.getAllYoutubeVideo().asLiveData()

    /**
     * Returns true if stock is available to sell, false otherwise.
     */
    fun isStockAvailable(item: YoutubeVideo): Boolean {
        return true
    }

    /**
     * Updates an existing Item in the database.
     */
    fun updateItem(
        videoId: Int,
        videoTitle: String,
        videoUrl: String,
        videoDescription: String,
        videoCategory: String
    ) {
        val updatedItem = getUpdatedItemEntry(videoId, videoTitle, videoUrl, videoDescription, videoCategory)
        updateItem(updatedItem)
    }


    /**
     * Launching a new coroutine to update an item in a non-blocking way
     */
    private fun updateItem(item: YoutubeVideo) {
        viewModelScope.launch {
            youtubeVideoDao.update(item)
        }
    }

    /**
     * Decreases the stock by one unit and updates the database.
     */
    fun sellItem(item: YoutubeVideo) {
       /* if (item.quantityInStock > 0) {
            // Decrease the quantity by 1
            val newItem = item.copy(quantityInStock = item.quantityInStock - 1)
            updateItem(newItem)
        }*/
    }

    /**
     * Inserts the new Item into database.
     */
    fun addNewItem( videoTitle: String,
                    videoUrl: String,
                    videoDescription: String,
                    videoCategory: String) {
        val newItem = getNewItemEntry(videoTitle, videoUrl, videoDescription, videoCategory)
        insertItem(newItem)
    }

    /**
     * Launching a new coroutine to insert an item in a non-blocking way
     */
    private fun insertItem(video: YoutubeVideo) {
        viewModelScope.launch {
            youtubeVideoDao.insert(video)
        }
    }

    /**
     * Launching a new coroutine to delete an item in a non-blocking way
     */
    fun deleteItem(video: YoutubeVideo) {
        viewModelScope.launch {
            youtubeVideoDao.delete(video)
        }
    }

    /**
     * Retrieve an item from the repository.
     */
    fun retrieveItem(id: Int): LiveData<YoutubeVideo> {
        return youtubeVideoDao.getYoutubeVideoById(id).asLiveData()
    }

    /**
     * Returns true if the EditTexts are not empty
     */
    fun isEntryValid(itemName: String, itemPrice: String, itemCount: String): Boolean {
        if (itemName.isBlank() || itemPrice.isBlank() || itemCount.isBlank()) {
            return false
        }
        return true
    }

    /**
     * Returns an instance of the [Item] entity class with the item info entered by the user.
     * This will be used to add a new entry to the Inventory database.
     */
    private fun getNewItemEntry(
        videoTitle: String,
        videoUrl: String,
        videoDescription: String,
         videoCategory: String
    ): YoutubeVideo {
        return YoutubeVideo(
            title = videoTitle,
            url = videoUrl,
            description = videoDescription,
            category = videoCategory
        )
    }

    /**
     * Called to update an existing entry in the Inventory database.
     * Returns an instance of the [Item] entity class with the item info updated by the user.
     */
    private fun getUpdatedItemEntry(
        itemId: Int,
        videoTitle: String,
        videoUrl: String,
        videoDescription: String,
        videoCategory: String
    ): YoutubeVideo {
        return YoutubeVideo(
            id = itemId,
            title = videoTitle,
            url = videoUrl,
            description = videoDescription,
            category = videoCategory
        )
    }
}

/**
 * Factory class to instantiate the [ViewModel] instance.
 */
class InventoryViewModelFactory(private val youtubeVideoDao: YoutubeVideoDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListVideoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ListVideoViewModel(youtubeVideoDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
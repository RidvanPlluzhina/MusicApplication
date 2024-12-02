package it.unibz.engineeringofmobilesystems.musicapplication

import android.app.Application
//import android.media.MediaPlayer
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MusicViewModel(application: Application) : AndroidViewModel(application) {

    val songs = listOf(
        SongClass(R.drawable.album_song, "Trendeline", "Artist: Chicho", R.raw.audio1),
        SongClass(R.drawable.album_song1, "Kriminal", "Artist: Mc Kresha", R.raw.audio2),
        SongClass(R.drawable.album_song2, "Semafori", "Artist: Lyrical Son", R.raw.audio3),
        SongClass(R.drawable.album_song3, "Kuq e Zi", "Artist: Lyrical Son", R.raw.audio3),
        SongClass(R.drawable.album_song4, "JBMTQR", "Artist: Lyrical Son", R.raw.audio3),
        SongClass(R.drawable.album_song5, "Era", "Artist: Lyrical Son", R.raw.audio3),
        SongClass(R.drawable.album_song6, "Sje Mo", "Artist: Mc Kresha", R.raw.audio3)
    )

    private val _currentSongIndex = MutableStateFlow(0)
    val currentSongIndex: StateFlow<Int> = _currentSongIndex

    // Mutable state flow for the current song
    private val _currentSong = MutableStateFlow(songs[0])
    val currentSong: StateFlow<SongClass> = _currentSong

//    private var mediaPlayer: MediaPlayer? = null

//    fun playMusic() {
//        mediaPlayer?.start()
//    }

    fun switchSong(index: Int) {
        if (index in songs.indices) {
//            initializeMediaPlayer(songs[index].audioResourceId)
            _currentSongIndex.value = index
            _currentSong.value = songs[index] // Update the current song with new song details
//            playMusic()
        }
    }

//    private fun initializeMediaPlayer(songResId: Int) {
//        mediaPlayer?.release()
//        mediaPlayer = MediaPlayer.create(getApplication(), songResId)
//    }
//
//    override fun onCleared() {
//        super.onCleared()
//        mediaPlayer?.release()
//    }
}
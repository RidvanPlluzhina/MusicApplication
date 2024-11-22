package it.unibz.engineeringofmobilesystems.musicapplication

import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import it.unibz.engineeringofmobilesystems.musicapplication.ui.theme.MusicApplicationTheme
import androidx.compose.material3.Text

class MainActivity : ComponentActivity() {

    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Initialize MediaPlayer with the first song
        val songs = listOf(
            R.raw.audio1, // First song resource ID
            R.raw.audio2, // Second song resource ID
            R.raw.audio3  // Third song resource ID
        )
        initializeMediaPlayer(songs[0]) // Initialize MediaPlayer with the first song

        setContent {
            MusicApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MusicPlayerUI(
                        onPlay = { playMusic() },
                        onPause = { pauseMusic() },
                        onSwitchSong = { songIndex -> switchSong(songIndex) },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    private fun initializeMediaPlayer(songResId: Int) {
        mediaPlayer?.release() // Release any previous MediaPlayer instance
        mediaPlayer = MediaPlayer.create(this, songResId)
    }

    private fun playMusic() {
        mediaPlayer?.start()
    }

    private fun pauseMusic() {
        mediaPlayer?.pause()
    }

    private fun switchSong(songIndex: Int) {
        val songs = listOf(
            R.raw.audio1, // First song resource ID
            R.raw.audio2, // Second song resource ID
            R.raw.audio3  // Third song resource ID
        )
        if (songIndex in songs.indices) {
            initializeMediaPlayer(songs[songIndex]) // Load the selected song into MediaPlayer
            playMusic() // Start playing the new song
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release() // Release MediaPlayer resources
    }
}

// This is a data class used to collect everything
data class Collection<A, B, C, D>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D
)

@Composable
fun MusicPlayerUI(
    onPlay: () -> Unit,
    onPause: () -> Unit,
    onSwitchSong: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    // State for the current song index
    var currentSongIndex by remember { mutableStateOf(0) }

    // List of songs (album image, title, artist, song resource)
    val songs = listOf(
        Collection(R.drawable.album_song, "Trendeline", "Artist: Mc Kresha", R.raw.audio1),
        Collection(R.drawable.album_song1, "Song 2", "Artist: Dua Lipa", R.raw.audio2),
        Collection(R.drawable.album_song2, "Song 3", "Artist: Calvin Harris", R.raw.audio3)
    )

    // Get the current song based on the index
    val currentSong = songs[currentSongIndex]

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.LightGray)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        // Album Art
        Image(
            painter = painterResource(id = currentSong.first),
            contentDescription = "Album Art",
            modifier = Modifier
                .size(500.dp)
                .background(Color.White)
        )

        // Song Title and Artist
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = currentSong.second,
                fontSize = 24.sp,
                color = Color.Black
            )
            Text(
                text = currentSong.third,
                fontSize = 18.sp,
                color = Color.Black
            )
        }

        // Playback Buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Back Button
            Image(
                painter = painterResource(id = R.drawable.back),
                contentDescription = "Back",
                modifier = Modifier
                    .size(64.dp)
                    .clickable {
                        currentSongIndex = if (currentSongIndex > 0) {
                            currentSongIndex - 1
                        } else {
                            songs.lastIndex
                        }
                        onSwitchSong(currentSongIndex) // Sync MediaPlayer with the new index
                    }
            )

            // Play Button
            Image(
                painter = painterResource(id = R.drawable.play),
                contentDescription = "Play",
                modifier = Modifier
                    .size(64.dp)
                    .clickable { onPlay() }
            )

            // Next Button
            Image(
                painter = painterResource(id = R.drawable.next),
                contentDescription = "Next",
                modifier = Modifier
                    .size(64.dp)
                    .clickable {
                        currentSongIndex = if (currentSongIndex < songs.lastIndex) {
                            currentSongIndex + 1
                        } else {
                            0
                        }
                        onSwitchSong(currentSongIndex) // Sync MediaPlayer with the new index
                    }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MusicPlayerPreview() {
    MusicApplicationTheme {
        MusicPlayerUI(
            onPlay = {},
            onPause = {},
            onSwitchSong = {}
        )
    }
}

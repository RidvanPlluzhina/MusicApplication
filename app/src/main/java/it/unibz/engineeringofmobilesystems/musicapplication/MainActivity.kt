package it.unibz.engineeringofmobilesystems.musicapplication

import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import it.unibz.engineeringofmobilesystems.musicapplication.ui.theme.MusicApplicationTheme
import it.unibz.engineeringofmobilesystems.musicapplication.model.Affirmation
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text

class MainActivity : ComponentActivity() {

    // Media player variable declaration
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

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

    // This function initializes the MediaPlayer object, songResId specifies the audio resource
    private fun initializeMediaPlayer(songResId: Int) {
        mediaPlayer?.release() // Release any previous MediaPlayer instance
        mediaPlayer = MediaPlayer.create(this, songResId)
    }

    // This function starts playing the current audio track.
    private fun playMusic() {
        mediaPlayer?.start()
    }

    // This function pauses the currently playing audio.
    private fun pauseMusic() {
        mediaPlayer?.pause()
    }

    // This function switches to a specific song based on the given index.
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
    // This is a lifecycle method that is automatically called when the activity is destroyed.
    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
    }
}
// The MusicPlayerUI is a composable that defines the main user interface (UI) of the app
@Composable
fun MusicPlayerUI(
    onPlay: () -> Unit,
    onPause: () -> Unit,
    onSwitchSong: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    // This line creates an instance of the Datasource class.
    val datasource = Datasource()
    // This line calls the loadAffirmations function on the Datasource instance
    val affirmations = datasource.loadAffirmations()
    // This is the background color of the app declared in the column
    val customGradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF3A403D), // Dark greenish-gray
            Color(0xFF1F241F)  // Slightly darker shade
        )
    )
    // The currentSongIndex variable is mutable state that keeps track of the index of the song.
    var currentSongIndex by remember { mutableIntStateOf(0) }

    // This is a list of the SongClass that represents all the data used in my app
    val songs = listOf(
        SongClass(R.drawable.album_song, "Trendeline", "Artist: Chicho", R.raw.audio1),
        SongClass(R.drawable.album_song1, "Kriminal", "Artist: Mc Kresha", R.raw.audio2),
        SongClass(R.drawable.album_song2, "Semafori", "Artist: Lyrical Son", R.raw.audio3),
        SongClass(R.drawable.album_song3, "Kuq e Zi", "Artist: Lyrical Son", R.raw.audio3),
        SongClass(R.drawable.album_song4, "JBMTQR", "Artist: Lyrical Son", R.raw.audio3),
        SongClass(R.drawable.album_song5, "Era", "Artist: Lyrical Son", R.raw.audio3),
        SongClass(R.drawable.album_song6, "Sje Mo", "Artist: Mc Kresha", R.raw.audio3)

    )

    val currentSong = songs[currentSongIndex]

    // Single Column for the entire layout
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(customGradient)
            .padding(25.dp)
    ) {
        // Album Art at the top of the UI
        Image(
            painter = painterResource(id = currentSong.albumArtResourceId),
            contentDescription = "Album Art",
            modifier = Modifier
                .size(300.dp)
                .border(16.dp, Color.Black)
                .align(Alignment.CenterHorizontally) // Center the image horizontally
        )
        // The spacer is used to make empty space in our layout
        Spacer(modifier = Modifier.height(70.dp))

        // Here is the row declaration for icons
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
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
                        onSwitchSong(currentSongIndex)
                    }
            )
            Image(
                painter = painterResource(id = R.drawable.play),
                contentDescription = "Play",
                modifier = Modifier
                    .size(64.dp)
                    .clickable { onPlay() }
            )
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
                        onSwitchSong(currentSongIndex)
                    }
            )
        }
        // The spacer is used to make empty space in our layout
        Spacer(modifier = Modifier.height(55.dp))

        // LazyColumn declaration
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f) // This ensures that lazy column takes the remaining space
        ) {
            itemsIndexed(songs) { index, song ->
                AlbumItem(affirmation = affirmations[index]) {
                    // Play the selected song when the item is clicked
                    currentSongIndex = index
                    onSwitchSong(currentSongIndex)
                }
            }
        }
    }
    }

    @Composable
    fun AlbumItem(affirmation: Affirmation, onClick: () -> Unit) {
        // The album items are represented as rows
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .background(Color.Black)
                .clickable { onClick() }
        ) {
            // There will be a image that is declared at affirmation
            Image(
                painter = painterResource(id = affirmation.imageResourceId),
                contentDescription = null,
                modifier = Modifier
                    .size(64.dp)
                    .padding(8.dp)
            )
            // The text will be shown after image which is declared at affirmation
            Text(
                text = stringResource(id = affirmation.stringResourceId),
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .align(Alignment.CenterVertically)
            )
        }
    }
// It provides a real-time preview of the MusicPlayerUI
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

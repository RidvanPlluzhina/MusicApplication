package it.unibz.engineeringofmobilesystems.musicapplication

//import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import it.unibz.engineeringofmobilesystems.musicapplication.ui.theme.MusicApplicationTheme

class MainActivity : ComponentActivity() {

    // Using MusicViewModel
    private val musicViewModel: MusicViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MusicApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MusicPlayerUI(
                        viewModel = musicViewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
@Composable
fun MusicPlayerUI(
    viewModel: MusicViewModel,
    modifier: Modifier = Modifier
) {
    val currentSong by viewModel.currentSong.collectAsState()
    val currentSongIndex by viewModel.currentSongIndex.collectAsState()
    val songs = viewModel.songs

    // This is the background color of the app declared in the column
    val customGradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF3A403D), // Dark greenish-gray
            Color(0xFF1F241F)  // Slightly darker shade
        )
    )

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
                        val newIndex = if (currentSongIndex > 0) {
                            currentSongIndex - 1
                        } else {
                            songs.lastIndex
                        }
                        viewModel.switchSong(newIndex)
                    }
            )
            Image(
                painter = painterResource(id = R.drawable.play),
                contentDescription = "Play",
                modifier = Modifier
                    .size(64.dp)
                // Uncomment if using playMusic functionality
//                 .clickable { viewModel.playMusic() }
            )
            Image(
                painter = painterResource(id = R.drawable.next),
                contentDescription = "Next",
                modifier = Modifier
                    .size(64.dp)
                    .clickable {
                        val newIndex = if (currentSongIndex < songs.lastIndex) {
                            currentSongIndex + 1
                        } else {
                            0
                        }
                        viewModel.switchSong(newIndex)
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
                SongItem(song = song) {
                    // Play the selected song when the item is clicked
                    viewModel.switchSong(index)
                }
            }
        }
    }
}

@Composable
fun SongItem(song: SongClass, onClick: () -> Unit) {
    // The album items are represented as rows
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.Black)
            .clickable { onClick() }
    ) {
        // Display the album art, title, and artist
        Image(
            painter = painterResource(id = song.albumArtResourceId),
            contentDescription = null,
            modifier = Modifier
                .size(64.dp)
                .padding(8.dp)
        )
        // The text will be shown after the image which is declared in SongClass
        Column(
            modifier = Modifier
                .padding(start = 8.dp)
                .align(Alignment.CenterVertically)
        ) {
            Text(
                text = song.title,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = song.artist,
                color = Color.White
            )
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun MusicPlayerPreview() {
//    val mockViewModel = MusicViewModel(Application())
//    MusicApplicationTheme {
//        MusicPlayerUI(
//            viewModel = mockViewModel
//        )
//    }
//}

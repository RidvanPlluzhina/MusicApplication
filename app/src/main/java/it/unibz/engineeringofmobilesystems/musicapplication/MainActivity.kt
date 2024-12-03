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
//import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import it.unibz.engineeringofmobilesystems.musicapplication.ui.theme.MusicApplicationTheme

class MainActivity : ComponentActivity() {

    // This declares the MusicViewModel as a property of MainActivity.
    private val musicViewModel: MusicViewModel by viewModels()

    // This is the entry point for the MainActivity when the activity is first created.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Makes the content fill the entire screen.
        enableEdgeToEdge()

        // This sets the content for the activity using Jetpack Compose.
        setContent {
            MusicApplicationTheme {
                // NavController creation to manage navigation in the application.
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavigationComponent(
                        navController = navController, // handles the navigation logic
                        viewModel = musicViewModel, // provides data management for the app.
                        modifier = Modifier.padding(innerPadding) // ensures proper padding
                    )
                }
            }
        }
    }
}
@Composable
fun MusicPlayerUI(
    viewModel: MusicViewModel,
    navController: NavHostController,
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

    // Declares a mutable variable named searchQuery that stores the user's current search query.
    var searchQuery by remember { mutableStateOf("") }

    // Filter the list of songs based on the search query
    val filteredSongs = if (searchQuery.isEmpty()) {
        songs
    } else {
        songs.filter { song ->
            song.title.contains(searchQuery, ignoreCase = true)
        }
    }

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
        Spacer(modifier = Modifier.height(40.dp))

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
                // .clickable { viewModel.playMusic() }
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
        Spacer(modifier = Modifier.height(40.dp))

        // TextField for the search bar
        TextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Search Songs") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
        )

        // LazyColumn declaration
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f) // This ensures that LazyColumn takes the remaining space
        ) {
            itemsIndexed(filteredSongs) { index, song ->
                SongItem(song = song) {
                    // Play the selected song when the item is clicked
                    val actualIndex =
                        songs.indexOf(song) // Find the index of the song in the original list
                    viewModel.switchSong(actualIndex)
                }
            }
        }
        // Row for navigation buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Button to navigate to All Artists & Songs Screen
            Button(
                onClick = { navController.navigate("allArtistsSongs") }
            ) {
                Text("All Artists & Songs")
            }

            // Button to navigate to Application Info Screen
            Button(
                onClick = { navController.navigate("appInfo") }
            ) {
                Text("App Info")
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
            .padding(7.88.dp)
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

@Composable
fun NavigationComponent(
    navController: NavHostController,
    viewModel: MusicViewModel,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            MusicPlayerUI(
                viewModel = viewModel,
                navController = navController,
                modifier = modifier
            )
        }
        composable("appInfo") {
            AppInfoScreen(navController)
        }
        composable("allArtistsSongs") {
            AllArtistsSongsScreen(viewModel, navController)
        }
    }
}
@Composable
fun AppInfoScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(40.dp)
    ) {
        Text("App Information", fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        Text("This app allows you to explore various songs,artist information, and more.")
        Spacer(modifier = Modifier.height(16.dp))
        Text("Developed by: Ridvan Plluzhina")
        Text("Version: 1.0.0 (First One)")
        Spacer(modifier = Modifier.height(32.dp))
        // Back Button
        Button(onClick = { navController.popBackStack() }) {
            Text("Back")
        }
    }
}
@Composable
fun AllArtistsSongsScreen(viewModel: MusicViewModel, navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(40.dp)
    ) {
        Text("All Artists & Songs", fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(viewModel.songs) { song ->
                Text(text = "Title: ${song.title} - ${song.artist}")
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
        // Back Button
        Button(onClick = { navController.popBackStack() }) {
            Text("Back")
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun MusicPlayerPreview() {
//    val mockViewModel = MusicViewModel(Application())
//    MusicApplicationTheme {
//        val navController = rememberNavController()
//        MusicPlayerUI(
//            viewModel = mockViewModel,
//            navController = navController
//        )
//    }
//}

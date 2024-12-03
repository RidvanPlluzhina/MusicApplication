# Music Player Application

## Overview

This project is developed for the subject **Engineering of Mobile Systems** taught by Prof. Nicolo Pretto at the Free University of Bolzano.
This project is a simple yet functional Music Player application developed using Jetpack Compose and Kotlin. The app allows users to explore various songs, navigate through different screens, view artist information, and see app details. It also features a search functionality to help users find songs easily.

## Features

- **Play Music**: Play, pause, and navigate between songs using simple controls.
- **LazyColumn for Song List**: Displays a list of songs using LazyColumn, allowing users to scroll through available songs.
- **ViewModel Integration**: The app uses the ViewModel to manage and persist UI-related data. The `MusicViewModel` is used for keeping track of the current song and managing interactions with the music player.
- **Navigation**: Implemented a navigation system with a starting destination and two additional destinations:
  - **Home Screen**: The main screen where users can play, pause, and navigate between songs.
  - **App Information Screen**: Displays information about the application, the developer, and the version.
  - **All Artists and Songs Screen**: Displays all artists and their respective songs.
- **Back Button Navigation**: Each navigation screen includes a back button to return to the previous screen.
- **Search Functionality**: Users can search for songs using the search bar, filtering the song list in real-time.

## Screens

### 1. Home Screen
- The main screen allows users to:
  - Play, pause, or skip to the next/previous song.
  - View album art, song title, and artist.
  - Search for a song using the search bar.
  - Navigate to the "App Info" or "All Artists and Songs" screens using buttons at the bottom of the screen.

### 2. App Information Screen
- Displays details about the application, such as:
  - Developer name: Ridvan Plluzhina
  - Version: 1.0.0 (First One)
  - General description of the app.

### 3. All Artists and Songs Screen
- Displays a list of all the artists and their respective songs. Users can navigate back to the main screen using the back button.

## Architecture

- **ViewModel**: The `MusicViewModel` is responsible for handling the state of the current song, managing the song list, and ensuring UI data is retained during configuration changes.
- **Navigation Component**: The app uses the Navigation component to switch between the home screen, app information screen, and all artists and songs screen.

## How to Run
1. Clone this repository.
2. Open the project in Android Studio.
3. Build and run the application on an Android emulator or a physical device.

## Dependencies
- **Jetpack Compose**: For building the UI.
- **Navigation**: For implementing the navigation between different screens.
- **ViewModel**: To manage UI-related data lifecycle consciously.

## Future Improvements
- **Enhanced Music Playback**: Adding play/pause functionality.
- **More Artist Details**: Providing detailed artist biographies.
- **Lyrics Feature**: Adding lyrics for each song.

## Author
- Ridvan Plluzhina

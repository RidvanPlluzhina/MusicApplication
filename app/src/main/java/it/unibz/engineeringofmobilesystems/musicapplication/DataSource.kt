package it.unibz.engineeringofmobilesystems.musicapplication

import it.unibz.engineeringofmobilesystems.musicapplication.model.Affirmation

class Datasource {
    fun loadAffirmations(): List<Affirmation> {
        return listOf(
            Affirmation(R.string.affirmation1, R.drawable.album_song),
            Affirmation(R.string.affirmation2, R.drawable.album_song1),
            Affirmation(R.string.affirmation3, R.drawable.album_song2),
            Affirmation(R.string.affirmation3, R.drawable.album_song),
            Affirmation(R.string.affirmation3, R.drawable.album_song2),
            Affirmation(R.string.affirmation3, R.drawable.album_song),
            Affirmation(R.string.affirmation3, R.drawable.album_song1)

        )
    }
}

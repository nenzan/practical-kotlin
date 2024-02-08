package com.example.practicalkotlin.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.res.AssetManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONArray
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.io.OutputStream

data class Game(
    val id: Int,
    val title: String,
    val thumbnail: String,
    val shortDescription: String,
    val gameUrl: String,
    val genre: String,
    val platform: String,
    val publisher: String,
    val developer: String,
    val releaseDate: String,
    val freetogameProfileUrl: String,
    var rating: Int
)

fun loadGamesFromJson(context: Context): List<Game> {
    val json = context.assets.open("data.json").bufferedReader().use { it.readText() }
    val jsonArray = JSONArray(json)
    val games = mutableListOf<Game>()
    for (i in 0 until jsonArray.length()) {
        val jsonObject = jsonArray.getJSONObject(i)
        val id = jsonObject.getInt("id")
        val title = jsonObject.getString("title")
        val thumbnail = jsonObject.getString("thumbnail")
        val shortDescription = jsonObject.getString("short_description")
        val gameUrl = jsonObject.getString("game_url")
        val genre = jsonObject.getString("genre")
        val platform = jsonObject.getString("platform")
        val publisher = jsonObject.getString("publisher")
        val developer = jsonObject.getString("developer")
        val releaseDate = jsonObject.getString("release_date")
        val freetogameProfileUrl = jsonObject.getString("freetogame_profile_url")
        val rating = jsonObject.getInt("rating")
        games.add(
            Game(
                id, title, thumbnail, shortDescription, gameUrl, genre, platform,
                publisher, developer, releaseDate, freetogameProfileUrl, rating
            )
        )
    }
    return games
}

fun getGameById(context: Context, id: Int): Game? {
    return loadGamesFromJson(context).find { it.id == id }
}
fun editGameData(id: Int, rating: Int, context: Context) {
    val jsonString = loadJSONFromAssets("data.json", context)
    val gameListType = object : TypeToken<List<Game>>() {}.type
    val games: MutableList<Game> = Gson().fromJson(jsonString, gameListType)
    val game = games.find { it.id == id }
    if (game != null) {
        game.rating = rating
        val updatedJsonString = Gson().toJson(games)
        writeJSONToFile(updatedJsonString, "data.json", context)
    }
}

fun loadJSONFromAssets(filename: String, context: Context): String {
    return try {
        val assetManager: AssetManager = context.assets
        val inputStream = assetManager.open(filename)
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        val stringBuilder = StringBuilder()
        var line: String? = bufferedReader.readLine()
        while (line != null) {
            stringBuilder.append(line)
            line = bufferedReader.readLine()
        }
        inputStream.close()
        stringBuilder.toString()
    } catch (ex: Exception) {
        ex.printStackTrace()
        ""
    }
}

fun writeJSONToFile(jsonString: String, filename: String, context: Context) {
    try {
        val outputStream: OutputStream = context.openFileOutput(filename, MODE_PRIVATE)
        outputStream.write(jsonString.toByteArray())
        outputStream.close()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}


package io.mattmoore.sound

import kotlin.system.exitProcess
import kotlinx.cinterop.*
import SDL.*

fun main(args: Array<String>) {
    // Check path to sound file was provided.
    if (args.size == 0) {
        println("No sound file provided!")
        exitProcess(1)
    }

    // Initialize the SDL audio subsystem.
    if (SDL_Init(SDL_INIT_AUDIO) == -1) {
        println("Error initializing SDL audio.")
        exitProcess(1)
    }

    // Initialize the audio device.
    if (Mix_OpenAudio(44100, MIX_DEFAULT_FORMAT, 2, 2048) < 0) {
        println("Error initializing Open Audio.")
        exitProcess(1)
    }

    // Load the music file to be played.
    val sound = Mix_LoadMUS(args[0])
    if (sound == null) {
        println("Error loading sound file.")
        exitProcess(1)
    }

    // Play the music.
    println("Playing sound from: ${args[0]}")
    Mix_PlayMusic(sound, 1)
    // Make sure to keep SDL alive while the audio file is playing.
    while (Mix_PlayingMusic() == 1) {
        SDL_Delay(100)
    }

    // Free the sound file we loaded.
    Mix_FreeMusic(sound)

    // Quit SDL and the mixer.
    Mix_Quit()
    SDL_Quit()
}

package org.example.dialoguehelper.Navigation

import kotlinx.serialization.Serializable

// Creates routes
@kotlinx.serialization.Serializable
object FrontScreen
@Serializable
object CharacterScreen

// Creates the NavController

// Creates the NavHost with the navigation graph consisting of supplied destinations
NavHost(navController = navController, startDestination = Profile) {
    composable<Profile> { ProfileScreen( /* ... */ ) }
    composable<FriendsList> { FriendsListScreen( /* ... */ ) }
    // You can add more destinations similarly
}
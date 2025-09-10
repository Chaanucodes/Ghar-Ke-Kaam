package com.faultyplay.gharkekaam.core.data.repository

import com.faultyplay.gharkekaam.core.data.model.User
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.FirebaseUser
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.FirebaseFirestore

interface AuthRepository {
    suspend fun signIn(email: String, password: String): Result<User>
    suspend fun signUp(email: String, password: String): Result<User>
    fun getCurrentUser(): User?
    suspend fun signOut()
}

class AuthRepositoryImpl(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : AuthRepository {

    private val usersCollection = firestore.collection("users")

    override suspend fun signIn(email: String, password: String): Result<User> {
        return try {
            val authResult = firebaseAuth.signInWithEmailAndPassword(email, password)
            val firebaseUser = authResult.user
            if (firebaseUser != null) {
                Result.success(firebaseUser.toDomainUser())
            } else {
                Result.failure(Exception("Sign in failed: Firebase user is null."))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun signUp(email: String, password: String): Result<User> {
        return try {
            val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password)
            val firebaseUser = authResult.user
            if (firebaseUser != null) {
                val user = User(
                    uid = firebaseUser.uid,
                    email = firebaseUser.email
                )
                usersCollection.document(firebaseUser.uid).set(user)
                Result.success(user)
            } else {
                Result.failure(Exception("Sign up failed: Firebase user is null."))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getCurrentUser(): User? {
        return Firebase.auth.currentUser?.toDomainUser()
    }

    override suspend fun signOut() {
        firebaseAuth.signOut()
    }

    private fun FirebaseUser.toDomainUser(): User {
        return User(uid = this.uid, email = this.email, name = this.displayName)
    }
}

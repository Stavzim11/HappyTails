package com.example.happytails.repository.FirebaseImpl

import com.example.happytails.data.models.User
import com.example.happytails.repository.UserRepository
import com.example.happytails.utils.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import safeCall


class UserRepositoryImpl : UserRepository {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val userRef = FirebaseFirestore.getInstance().collection("users")

    override suspend fun currentUser(): Resource<User> {
        return withContext(Dispatchers.IO) {
            safeCall<User> {
                val user = userRef.document(firebaseAuth.currentUser!!.uid).get().await()
                    .toObject(User::class.java)
                Resource.Success(user!!)
            }
        }
    }

    override suspend fun login(userName: String, password: String): Resource<User> {
        return withContext(Dispatchers.IO) {
            safeCall<User> {
                val result = firebaseAuth.signInWithEmailAndPassword(userName, password).await()
                val user =
                    userRef.document(result.user?.uid!!).get().await().toObject(User::class.java)!!
                Resource.Success(user)
            }
        }
    }

    override suspend fun createUser(
        userName: String,
        userEmail: String,
        userPhone: String,
        userPass: String
    ): Resource<User> {
        return withContext(Dispatchers.IO) {
            safeCall<User> {
                val registrationResult =
                    firebaseAuth.createUserWithEmailAndPassword(userEmail, userPass).await()
                val userId = registrationResult.user?.uid!!
                val newUser = User(userName, userEmail, userPhone)
                userRef.document(userId).set(newUser).await()
                Resource.Success(newUser)
            }

        }


    }

    override suspend fun getUserFavs(): List<String> {
        val user = userRef.document(firebaseAuth.currentUser!!.uid).get().await()
            .toObject(User::class.java)
        return user?.favorites!!
    }

    override suspend fun isConnected(): Boolean {
        val user = firebaseAuth.currentUser?.uid.let {
            if (it != null) {
                userRef.document(it).get().await().toObject(User::class.java)
            }
        }
        return true
    }

    override fun logout() {
        firebaseAuth.signOut()
    }
}
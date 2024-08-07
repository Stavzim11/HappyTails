package com.example.happytails.repository.implementations

import com.example.happytails.data.models.User
import com.example.happytails.repository.UserRepository
import com.example.happytails.utils.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import com.example.happytails.utils.safeCall


class UserRepositoryImpl : UserRepository {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val userRef = FirebaseFirestore.getInstance().collection("UserCollection")

    override suspend fun currentUser(): Resource<User> {
        return withContext(Dispatchers.IO) {
            safeCall {
                val user = userRef.document(firebaseAuth.currentUser!!.uid).get().await()
                    .toObject(User::class.java)
                Resource.Success(user!!)
            }
        }
    }

    override suspend fun login(email: String, password: String): Resource<User> {
        return withContext(Dispatchers.IO) {
            safeCall {
                val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
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
            safeCall {
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
        val user=firebaseAuth.currentUser
        return (user!=null)
    }

    override fun logout() {
        firebaseAuth.signOut()
    }
}
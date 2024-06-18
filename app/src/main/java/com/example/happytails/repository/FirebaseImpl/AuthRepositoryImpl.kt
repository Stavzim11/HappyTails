package com.example.happytails.repository.FirebaseImpl

import com.example.happytails.data.models.User
import com.example.happytails.repository.AuthRepository
import com.example.happytails.utils.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import safeCall


class AuthRepositoryImpl : AuthRepository {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val userRef = FirebaseFirestore.getInstance().collection("users")

    override suspend fun currentUser(): Resource<User> {
       return withContext(Dispatchers.IO) {
           safeCall<User> {
               val user = userRef.document(firebaseAuth.currentUser!!.uid).get().await().toObject(User::class.java)
               Resource.Success(user!!)
           }
       }
    }

    override suspend fun login(userName: String, password: String): Resource<User> {
        return withContext(Dispatchers.IO) {
            safeCall<User> {
                val result  = firebaseAuth.signInWithEmailAndPassword(userName,password).await()
                val user = userRef.document(result.user?.uid!!).get().await().toObject(User::class.java)!!
                Resource.Success(user)
            }
        }
    }

    override suspend fun createUser(
        userName: String,
        userLoginPass: String,
        userPhone: String,
        name: String
    ) : Resource<User> {
        return withContext(Dispatchers.IO) {
            safeCall<User> {
                val registrationResult  = firebaseAuth.createUserWithEmailAndPassword(userName,userLoginPass).await()
                val userId = registrationResult.user?.uid!!
                val newUser = User(userName,userLoginPass,userPhone,name)
                userRef.document(userId).set(newUser).await()
                Resource.Success(newUser)
            }

        }


    }

    override fun logout() {
        firebaseAuth.signOut()
    }
}
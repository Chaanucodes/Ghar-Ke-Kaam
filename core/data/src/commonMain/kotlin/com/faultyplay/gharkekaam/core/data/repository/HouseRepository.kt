package com.faultyplay.gharkekaam.core.data.repository

import com.faultyplay.gharkekaam.core.data.model.House
import dev.gitlive.firebase.firestore.FieldValue
import dev.gitlive.firebase.firestore.FirebaseFirestore

interface HouseRepository {
    suspend fun createHouse(houseName: String, creatorId: String, allowlist: List<String>): Result<String>
    suspend fun joinHouse(houseCode: String, userId: String, userEmail: String): Result<Unit>
    suspend fun getUserHouses(userId: String): Result<List<House>>
}

class HouseRepositoryImpl(
    private val firestore: FirebaseFirestore
) : HouseRepository {

    private val housesCollection = firestore.collection("houses")
    private val houseCodesCollection = firestore.collection("houseCodes")

    override suspend fun createHouse(houseName: String, creatorId: String, allowlist: List<String>): Result<String> {
        return try {
            val houseCode = generateUniqueHouseCode()
            val newHouseRef = housesCollection.document // Auto-generate ID
            val house = House(
                houseId = newHouseRef.id,
                name = houseName,
                members = listOf(creatorId),
                allowlist = allowlist.map { it.trim().lowercase() }
            )

            newHouseRef.set(house)
            houseCodesCollection.document(houseCode).set(mapOf("houseId" to newHouseRef.id))

            Result.success(houseCode)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun joinHouse(houseCode: String, userId: String, userEmail: String): Result<Unit> {
        return try {
            val codeDoc = houseCodesCollection.document(houseCode).get()
            if (!codeDoc.exists) {
                return Result.failure(Exception("Invalid house code."))
            }

            val houseId = codeDoc.data<Map<String, Any?>>()["houseId"] as? String ?: return Result.failure(Exception("House ID not found."))
            val houseDocRef = housesCollection.document(houseId)
            val house = houseDocRef.get().data<House>()

            if (house.members.contains(userId)) {
                return Result.success(Unit) // Already a member
            }

            if (!house.allowlist.contains(userEmail.trim().lowercase())) {
                return Result.failure(Exception("You are not invited to join this house."))
            }

            houseDocRef.update("members" to FieldValue.arrayUnion(userId))
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getUserHouses(userId: String): Result<List<House>> {
        return try {
            val querySnapshot = housesCollection.where{ all ("members" equalTo userId)}.get()
            val houses = querySnapshot.documents.map { it.data<House>() }
            Result.success(houses)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private suspend fun generateUniqueHouseCode(): String {
        var code: String
        var isUnique = false
        while (!isUnique) {
            code = (100000..999999).random().toString()
            val doc = houseCodesCollection.document(code).get()
            if (!doc.exists) {
                isUnique = true
                return code
            }
        }
        // This part should ideally not be reached
        throw Exception("Failed to generate a unique house code.")
    }
}

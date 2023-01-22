package com.priyank.drdelivery.shipmentDetails.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.priyank.drdelivery.shipmentDetails.domain.model.InterestingLink

@Dao
interface InteresingEmailDao {

    @Query("SELECT * FROM interestinglink")
    suspend fun getEmails(): List<InterestingLink>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEmail(email: InterestingLink)
}

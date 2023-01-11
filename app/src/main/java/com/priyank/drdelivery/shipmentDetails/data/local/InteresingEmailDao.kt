package com.priyank.drdelivery.shipmentDetails.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.priyank.drdelivery.shipmentDetails.domain.model.InterestingEmail

@Dao
interface InteresingEmailDao {

    @Query("SELECT * FROM interestingemail")
    suspend fun getEmails(): List<InterestingEmail>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEmail(email: InterestingEmail)
}

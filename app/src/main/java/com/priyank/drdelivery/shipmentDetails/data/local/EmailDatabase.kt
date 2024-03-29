package com.priyank.drdelivery.shipmentDetails.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.priyank.drdelivery.shipmentDetails.domain.model.InterestingEmail

@Database(entities = [InterestingEmail::class], version = 1)
abstract class EmailDatabase : RoomDatabase() {
    abstract val emailDao: InteresingEmailDao
}

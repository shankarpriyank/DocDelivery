package com.priyank.drdelivery.shipmentDetails.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.priyank.drdelivery.shipmentDetails.domain.model.InterestingLink

@Database(entities = [InterestingLink::class], version = 1)
abstract class EmailDatabase : RoomDatabase() {
    abstract val emailDao: InteresingEmailDao
}

package com.example.mainactivity.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(foreignKeys = arrayOf(ForeignKey(entity = Store::class,
                                        parentColumns = arrayOf("id"),
                                        childColumns = arrayOf("store_id"),
                                        onDelete = 5)))
class Product constructor(@PrimaryKey(autoGenerate = true)
                          var id: Long?,
                          @ColumnInfo(name = "store_id", index = true)
                          var storeId: Long,
                          var title: String,
                          var description: String,
                          var icon: String,
                          var price: Int,
                          var discount: Int,
                          @ColumnInfo(name = "discount_price")
                          var discountPrice: Int,
                          var favourite: Boolean) {
    constructor():this(null, 0, "", "", "", 0, 0, 0, false)
}
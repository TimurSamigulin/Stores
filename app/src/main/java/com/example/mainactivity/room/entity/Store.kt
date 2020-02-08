package com.example.mainactivity.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Store constructor(@PrimaryKey(autoGenerate = true) var id: Long?,
                        var title: String,
                        var description: String,
                        var icon: String,
                        var favourite: Boolean,
                        var apiHref: String?) {
    constructor():this(null, "","","", false, "lenta.json")
}
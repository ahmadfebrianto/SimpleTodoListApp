package com.test.stechoq

import android.app.Application
import com.test.stechoq.database.AppDatabase

class TaskApplication: Application() {
 val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
}
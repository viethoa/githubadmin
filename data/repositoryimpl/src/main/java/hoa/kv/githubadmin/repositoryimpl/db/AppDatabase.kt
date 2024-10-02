package hoa.kv.githubadmin.repositoryimpl.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import hoa.kv.githubadmin.repository.model.UserEntity
import hoa.kv.githubadmin.repositoryimpl.user.UserDao

@Database(
    entities = [UserEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        private const val APP_DB_NAME = "hoa.kv.github.admin.db"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun buildDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room
                    .databaseBuilder(context.applicationContext, AppDatabase::class.java, APP_DB_NAME)
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}
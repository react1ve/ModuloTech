package com.example.data.database.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Transaction
import androidx.room.Update


/**
 * Contains base database calls.
 * See SQL documentation for rowId: https://www.sqlite.org/rowidtable.html
 */
internal abstract class BaseDao<T> {

    private val FAILED_OPERATION: Long get() = -1L

    /**
     * Insert object in DB.
     * @return Long             - new rowId for the inserted item
     * @return FAILED_OPERATION - if item is not inserted
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(obj: T): Long

    /**
     * Insert list of objects in DB.
     * @return list of new rowIds for the inserted items
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(obj: List<T>): List<Long>

    /**
     * Update object in DB by PrimaryKey.
     * @return number of rows updated
     */
    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun update(obj: T): Int

    /**
     * Update list of objects in DB by PrimaryKey.
     * @return number of rows updated
     */
    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun update(obj: List<T>): Int

    /**
     * Remove object of DB by PrimaryKey.
     * @return number of rows removed
     */
    @Delete
    abstract suspend fun delete(obj: T): Int

    /**
     * Remove list of objects of DB by PrimaryKey.
     * @return number of rows removed
     */
    @Delete
    abstract suspend fun delete(obj: List<T>): Int

    /**
     * [insert] object: on conflict [update].
     */
    @Transaction
    open suspend fun upsert(obj: T) {
        val result = insert(obj)
        if (result == FAILED_OPERATION) {
            update(obj)
        }
    }

    /**
     * [insert] list of objects: on conflict [update].
     */
    @Transaction
    open suspend fun upsert(objList: List<T>) {
        val result = insert(objList)
        val updateIndex = result.indices.filter { result[it] == FAILED_OPERATION }
        updateIndex.takeIf { it.isNotEmpty() }
            ?.let { list -> update(list.map { objList[it] }) }
    }

}

package org.example.project.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import org.example.project.domain.models.Resource

/**
 * Extension functions for Repository operations
 */

/**
 * Maps the success data of a Resource to another type
 */


/**
 * Handles errors in the flow and converts them to Resource.Error
 */
fun <T> Flow<Resource<T>>.handleErrors(): Flow<Resource<T>> = catch { exception ->
    emit(Resource.Error(exception))
}

/**
 * Ensures the flow starts with a loading state
 */
fun <T> Flow<Resource<T>>.startWithLoading(): Flow<Resource<T>> = onStart {
    emit(Resource.Loading)
}

/**
 * Filters successful resources and returns only the data
 */
fun <T> Flow<Resource<T>>.filterSuccess(): Flow<T> = map { resource ->
    when (resource) {
        is Resource.Success -> resource.data
        else -> throw IllegalStateException("Resource is not successful")
    }
}
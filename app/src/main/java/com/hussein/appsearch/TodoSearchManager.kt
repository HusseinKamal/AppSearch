package com.hussein.appsearch

import android.content.Context
import androidx.appsearch.app.AppSearchSession
import androidx.appsearch.app.PutDocumentsRequest
import androidx.appsearch.app.SearchSpec
import androidx.appsearch.app.SetSchemaRequest
import androidx.appsearch.localstorage.LocalStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TodoSearchManager(
    private val appContext:Context
) {
    private var session : AppSearchSession ? = null

    suspend fun init(){
        withContext(Dispatchers.IO){
            val sessionFuture =  LocalStorage.createSearchSessionAsync(
                LocalStorage.SearchContext.Builder(
                    appContext,
                    "todo",

                ).build()
            )

            val setSchemaRequest = SetSchemaRequest.Builder()
                .addDocumentClasses(Todo::class.java)
                .build()

            session = sessionFuture.get()

            session?.setSchemaAsync(setSchemaRequest)
        }

    }
    suspend fun putTodos(todos: List<Todo>) : Boolean{
        return withContext(Dispatchers.IO){
            session?.putAsync(
                PutDocumentsRequest.Builder()
                    .addDocuments(todos)
                    .build()
            )?.get()?.isSuccess == true
        }
    }
    suspend fun searchTodo(searchQuery:String):List<Todo>{
        return withContext(Dispatchers.IO){
            var searhSpec = SearchSpec.Builder()
                .setSnippetCount(10)
                .addFilterNamespaces("my_todos")
                .setRankingStrategy(SearchSpec.RANKING_STRATEGY_USAGE_COUNT)//filter data search by time , sort etc
                .build()
            val result = session?.search(
                searchQuery,
                searhSpec
            )?:return@withContext emptyList()

            val page = result.nextPageAsync.get() // iterate to get all result by looping
            page.mapNotNull {
                if(it.genericDocument.schemaType == Todo::class.java.simpleName){
                    it.getDocument( Todo::class.java)
                } else null
            }
        }
    }

    fun closeSession(){
        session?.close()
        session = null
    }
}
package com.hussein.appsearch

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hussein.appsearch.ui.theme.TodoListState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Random
import java.util.UUID

class MainViewModel(
    private val todoSearchManager: TodoSearchManager
    ):ViewModel() {

        var state by mutableStateOf(TodoListState())
            private set

        private var searchJob : Job? = null

    init {
        viewModelScope.launch {
            todoSearchManager.init()
            val todos = (1..100).map{//return list
                Todo(
                    namespace = "my_todos",
                    id = UUID.randomUUID().toString(),
                    score = 1,
                    title = "Todo $it",
                    text = "Description $it",
                    isDone = Random().nextBoolean()

                )
            }
            todoSearchManager.putTodos(todos)
        }
    }

    fun onSearchQueryChange(query:String){
        //update current state item with new query
        state = state.copy(searchQuery = query)
        //To make on Job run at a time
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500L)
            val todos = todoSearchManager.searchTodo(query)
            state = state.copy(todos = todos)
        }
    }

    fun onDoneChange(todo: Todo, isDone:Boolean){
        viewModelScope.launch {
            todoSearchManager.putTodos(
                listOf(todo.copy(isDone = isDone))
            )
            state =state.copy(
                todos = state.todos.map {
                    if(it.id == todo.id){
                        it.copy(isDone = isDone)
                    }else it
                }
            )
        }
    }

    override fun onCleared() {
        //Close session for search manager
        todoSearchManager.closeSession()
        super.onCleared()
    }
}
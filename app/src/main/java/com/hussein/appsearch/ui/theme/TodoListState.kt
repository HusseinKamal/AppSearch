package com.hussein.appsearch.ui.theme

import com.hussein.appsearch.Todo

data class TodoListState(
    val todos:List<Todo> = emptyList(),
    val searchQuery :String = ""
)

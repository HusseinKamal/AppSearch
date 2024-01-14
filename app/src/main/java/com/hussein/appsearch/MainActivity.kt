package com.hussein.appsearch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hussein.appsearch.ui.theme.AppSearchTheme

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {

    private val viewModel : MainViewModel by viewModels<MainViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory{
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return MainViewModel(TodoSearchManager(appContext = applicationContext)) as T
                }
            }
        }
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppSearchTheme {
                val state =viewModel.state
               Column(modifier = Modifier.fillMaxSize()) {
                   TextField(
                       value = state.searchQuery,
                       onValueChange = viewModel::onSearchQueryChange,
                       modifier = Modifier
                           .padding(8.dp)
                           .fillMaxWidth()
                   )
                   
                   LazyColumn(contentPadding = PaddingValues(8.dp) , modifier = Modifier
                       .weight(1f)
                       .fillMaxWidth()){
                       items(state.todos){ todo->
                           TodoItem(
                               todo = todo,
                               onDoneChange = { isDone->
                                   viewModel.onDoneChange(todo, isDone = isDone)

                               }
                           )
                       }
                   }
               }
            }
        }
    }

    @Composable
    private fun TodoItem(todo: Todo, onDoneChange:(Boolean)-> Unit , modifier: Modifier=Modifier) {
        Row(modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)) {
            Column(modifier = Modifier.weight(1f)) {
               Text(text = todo.title, fontSize = 16.sp)
                Text(text = todo.text, fontSize = 10.sp)
            }
            Checkbox(checked = todo.isDone, onCheckedChange = onDoneChange)

        }
    }
}
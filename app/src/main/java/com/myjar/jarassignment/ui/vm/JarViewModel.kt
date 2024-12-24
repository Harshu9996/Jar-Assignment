package com.myjar.jarassignment.ui.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myjar.jarassignment.createRetrofit
import com.myjar.jarassignment.data.model.ComputerItem
import com.myjar.jarassignment.data.repository.JarRepository
import com.myjar.jarassignment.data.repository.JarRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class JarViewModel : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery

    private val _listStringData = MutableStateFlow<List<ComputerItem>>(emptyList())
    @OptIn(FlowPreview::class)
    val listStringData: StateFlow<List<ComputerItem>>
            get() = searchQuery.debounce(1000L)
    .combine(_listStringData){ query,computerItemList->
        if (!query.isNullOrEmpty()){
            computerItemList.filter {computerItem->
                computerItem.doesSatisfyQuery(query)
            }
        }else{
            computerItemList
        }

    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L),_listStringData.value)




    private val repository: JarRepository = JarRepositoryImpl(createRetrofit())

    fun fetchData() {
        viewModelScope.launch {
            repository.fetchResults().collectLatest {computerItemList->
                _listStringData.update {
                    computerItemList
                }

            }
        }
    }

    fun onSearchQueryEntered(query:String){
        _searchQuery.update {
            query
        }
    }
}
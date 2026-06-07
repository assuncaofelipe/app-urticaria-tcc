package com.assuncao.ufsc.urticaria.ui.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private val stateHolder = MenuStateHolder()

    val isGridView: StateFlow<Boolean> = stateHolder.isGridView
    val menuItems: List<MenuSection> = MenuSection.entries

    fun toggleLayout() = stateHolder.toggleLayout()
}

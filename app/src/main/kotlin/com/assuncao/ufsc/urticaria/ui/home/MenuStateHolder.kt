package com.assuncao.ufsc.urticaria.ui.home

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MenuStateHolder {
    private val _isGridView = MutableStateFlow(false)
    val isGridView: StateFlow<Boolean> = _isGridView.asStateFlow()

    fun toggleLayout() {
        _isGridView.value = !_isGridView.value
    }
}

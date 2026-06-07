package com.assuncao.ufsc.urticaria.ui.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Phone
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class MenuItemUi(
    val section: MenuSection,
    val icon: ImageVector,
    val containerColor: Color,
    val iconColor: Color,
)

fun MenuSection.toMenuItemUi(): MenuItemUi = when (this) {
    MenuSection.QUESTIONNAIRES -> MenuItemUi(
        section = this,
        icon = Icons.AutoMirrored.Filled.Assignment,
        containerColor = Color(0xFFEDE7F6),
        iconColor = Color(0xFF5E35B1),
    )
    MenuSection.REGISTER_LESION -> MenuItemUi(
        section = this,
        icon = Icons.Filled.CameraAlt,
        containerColor = Color(0xFFFCE4EC),
        iconColor = Color(0xFFC62828),
    )
    MenuSection.WHAT_IS_URTICARIA -> MenuItemUi(
        section = this,
        icon = Icons.AutoMirrored.Filled.MenuBook,
        containerColor = Color(0xFFE3F2FD),
        iconColor = Color(0xFF1565C0),
    )
    MenuSection.CONTACT -> MenuItemUi(
        section = this,
        icon = Icons.Filled.Phone,
        containerColor = Color(0xFFE8F5E9),
        iconColor = Color(0xFF2E7D32),
    )
}

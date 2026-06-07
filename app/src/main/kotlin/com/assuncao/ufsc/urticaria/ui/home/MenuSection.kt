package com.assuncao.ufsc.urticaria.ui.home

import androidx.annotation.StringRes
import com.assuncao.ufsc.urticaria.R

enum class MenuSection(
    @param:StringRes val titleRes: Int,
    @param:StringRes val descriptionRes: Int,
) {
    QUESTIONNAIRES(
        titleRes = R.string.menu_questionnaires_title,
        descriptionRes = R.string.menu_questionnaires_desc,
    ),
    REGISTER_LESION(
        titleRes = R.string.menu_register_lesion_title,
        descriptionRes = R.string.menu_register_lesion_desc,
    ),
    WHAT_IS_URTICARIA(
        titleRes = R.string.menu_what_is_urticaria_title,
        descriptionRes = R.string.menu_what_is_urticaria_desc,
    ),
    CONTACT(
        titleRes = R.string.menu_contact_title,
        descriptionRes = R.string.menu_contact_desc,
    ),
}

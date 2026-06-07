package com.assuncao.ufsc.urticaria.domain.menu

enum class MenuSection(
    val title: String,
    val description: String,
) {
    QUESTIONNAIRES(
        title = "Questionários",
        description = "Avalie o controle da doença",
    ),
    REGISTER_LESION(
        title = "Registrar lesão",
        description = "Foto e sintomas do dia",
    ),
    WHAT_IS_URTICARIA(
        title = "O que é urticária",
        description = "Conheça a sua condição",
    ),
    CONTACT(
        title = "Contato",
        description = "Fale com seu médico",
    ),
}

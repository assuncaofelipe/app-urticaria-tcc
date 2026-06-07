# app-urticaria-tcc — CLAUDE.md

TCC project for tracking urticaria (hives) symptoms, built with Kotlin Multiplatform Mobile (KMM) and Clean Architecture — targeting Android and iOS.

## Módulos Gradle

```
:app     → Android application (Compose UI, Hilt DI, Navigation)
:domain  → Pure KMP library (entities, repository interfaces, use cases)
:data    → KMP library (repository implementations — in-memory, commonMain)
iosApp/  → iOS application (SwiftUI, consumes UrticariaShared XCFramework)
```

Dependency direction: `:app` → `:data` → `:domain`  
`:domain` has **zero** Android/iOS imports — only `kotlinx-coroutines-core`.

## Arquitetura (Clean Architecture)

```
app/
  ui/
    home/          HomeScreen, HomeViewModel, MenuSection, MenuItemUi,
                   MenuStateHolder — tudo que é específico da tela Home
  di/              Hilt modules (AppModule — wires data ↔ domain)
  theme/           MaterialTheme wrapper

domain/
  entity/          Pure Kotlin data classes (e.g. Urticaria)
  usecase/         Casos de uso (sem dependência de framework)
  repository/      Interfaces de repositório (implementadas em :data)

data/
  repository/      Implementações das interfaces definidas em :domain
```

### Regra de camadas para modelos

| Tipo | Onde fica | Exemplo |
|------|-----------|---------|
| Entidade de negócio | `:domain/entity/` | `Urticaria` |
| Interface de repositório | `:domain/repository/` | `UrticariaRepository` |
| Implementação de repositório | `:data/repository/` | `UrticariaRepositoryImpl` |
| Modelo de UI (com tipos Android) | `:app/ui/<feature>/` | `MenuItemUi` |
| Estado de UI / toggle de layout | `:app/ui/<feature>/` | `MenuStateHolder` |

> Conceitos com `ImageVector`, `Color`, `@StringRes` ou qualquer tipo Android **nunca entram em `:domain`**.

## Padrões de UI adotados

### Strings
- Todos os textos visíveis ao usuário ficam em `app/src/main/res/values/strings.xml`.
- Enums e data classes que representam seções de UI usam `@param:StringRes val xyzRes: Int` em vez de `String` hardcoded.
- Composables resolvem o texto com `stringResource(item.section.titleRes)`.

### Mapper de domínio → UI
Cada feature tem uma extension function que converte o modelo de domínio no modelo visual:

```kotlin
// MenuItemUi.kt — em app/ui/home/
fun MenuSection.toMenuItemUi(): MenuItemUi = when (this) { ... }
```

O `HomeScreen` consome via `remember` para evitar remapeamento desnecessário:

```kotlin
val menuItemsUi = remember(viewModel.menuItems) {
    viewModel.menuItems.map { it.toMenuItemUi() }
}
```

O ViewModel expõe apenas tipos do domínio (`List<MenuSection>`); a conversão para tipos visuais acontece exclusivamente na camada de UI.

### Composables reutilizáveis
Componentes de card (`MenuListCard`, `MenuGridCard`) são declarados sem `private` para permitir reuso entre telas. Recebem `MenuItemUi` como parâmetro — nunca acessam o ViewModel diretamente.

## Tecnologias e versões

| Camada   | Tecnologia                     | Versão (libs.versions.toml)    |
|----------|--------------------------------|-------------------------------|
| Build    | Kotlin                         | `kotlin`                      |
| Build    | AGP                            | `agp`                         |
| Build    | KSP (substitui KAPT)           | `ksp`                         |
| app      | Jetpack Compose BOM             | `composeBom`                  |
| app      | Material3                      | via BOM                       |
| app      | Hilt                           | `hilt`                        |
| app      | Navigation Compose             | `navigation`                  |
| app      | ViewModel / Lifecycle          | `lifecycle`                   |
| domain   | kotlinx-coroutines-core        | `coroutines`                  |

Todas as versões são centralizadas em `gradle/libs.versions.toml`.

## Source sets (KMP)

`:domain` e `:data` usam o plugin `kotlin.multiplatform` com targets Android e iOS.

```
domain/src/commonMain/   ← entidades do domínio (Android + iOS)
data/src/commonMain/     ← implementações de repositório
app/src/main/            ← Activity, Composables, DI (Android only)
iosApp/iosApp/           ← SwiftUI app (iOS only)
```

### Targets ativos

Por enquanto apenas `androidTarget()` em `:domain` e `:data`.  
Targets iOS (`iosArm64`, `iosSimulatorArm64`, `iosX64`) e XCFramework serão adicionados quando o `iosApp` for configurado.

## Injeção de dependências (Hilt)

`AppModule` (em `:app`) está vazio por enquanto — sem repositório ou casos de uso registrados.

## Regras de desenvolvimento

- **Nunca adicionar imports Android em `:domain`** — o CI pode verificar com `grep -r "android\." domain/src/commonMain`.
- **KSP apenas** — não usar `kapt` (Hilt usa KSP em `:app`).
- Novos casos de uso vão em `domain/usecase/`, sem dependência de framework.
- Novos destinos de navegação são registrados no `NavHost` dentro de `:app`.
- **Strings hardcoded proibidas em Composables** — usar sempre `stringResource()` + `strings.xml`.
- **Modelos de UI ficam em `:app`** — nunca criar enums/data classes com tipos Android em `:domain`.

## Comandos úteis

```bash
# Build debug Android
./gradlew :app:assembleDebug

# Instalar no dispositivo/emulador Android
./gradlew :app:installDebug

# Verificar domínio sem Android
./gradlew :domain:compileKotlinAndroid

# Limpar caches
./gradlew clean
```

## Configuração iOS

### Targets KMP ativos
`:domain` e `:data` compilam para `androidTarget()`, `iosX64()`, `iosArm64()` e `iosSimulatorArm64()`.

### XCFramework
O módulo `:data` empacota tudo em `UrticariaShared.xcframework` exportando `:domain`:

```bash
# Debug (desenvolvimento)
./gradlew :data:assembleUrticariaSharedDebugXCFramework

# Release (produção)
./gradlew :data:assembleUrticariaSharedReleaseXCFramework
```

Output: `data/build/XCFrameworks/{debug|release}/UrticariaShared.xcframework`

> A task retorna SKIPPED em Linux (exige macOS + Xcode para linkar os iOS SDKs). A compilação Kotlin para iOS (`:data:compileKotlinIosX64`) funciona em qualquer host.

### Projeto Xcode
O `iosApp/` usa **XcodeGen** — gerar o `.xcodeproj` em macOS:

```bash
brew install xcodegen
cd iosApp
xcodegen generate
```

O `project.yml` configura:
- Fase de pre-build que chama `./gradlew :data:assembleUrticaria...XCFramework` automaticamente
- Embed + code-sign do `UrticariaShared.xcframework`
- Target iOS 16+, Swift 5.9

### Localização iOS
Strings em `iosApp/iosApp/pt-BR.lproj/Localizable.strings` — mesmas chaves do `strings.xml` Android.
`HomeView.swift` usa `String(localized: "chave")` para resolução em runtime.

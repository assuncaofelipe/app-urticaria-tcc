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
  ui/            Composables + ViewModels (Hilt @HiltViewModel)
  di/            Hilt modules (AppModule — wires data ↔ domain)
  theme/         MaterialTheme wrapper

domain/
  entity/        Pure Kotlin data classes (e.g. Urticaria)

data/            (vazio por enquanto — sem repositório ou banco de dados)

iosApp/
  iosApp/        SwiftUI entry point (iOSApp.swift, ContentView.swift)
```

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
data/src/commonMain/     ← vazio por enquanto
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

## Configuração Xcode (iOS — pendente)

Quando o suporte iOS for necessário:
1. Adicionar `iosArm64()`, `iosSimulatorArm64()`, `iosX64()` em `:domain` e `:data`.
2. Configurar XCFramework em `:data` com `export(project(":domain"))`.
3. Criar projeto Xcode em `iosApp/` e vincular o framework gerado.

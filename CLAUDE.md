# app-urticaria-tcc — CLAUDE.md

TCC project for tracking urticaria (hives) symptoms on Android, built with Kotlin Multiplatform Mobile and Clean Architecture.

## Módulos Gradle

```
:app     → Android application (Compose UI, Hilt DI, Navigation)
:domain  → Pure KMP library (entities, repository interfaces, use cases)
:data    → KMP library (repository implementations — sem banco de dados por enquanto)
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
  repository/    Interfaces (UrticariaRepository)
  usecase/       Orchestration classes (GetUrticariaListUseCase)

data/
  repository/    UrticariaRepositoryImpl (in-memory, commonMain)
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

`:domain` e `:data` usam o plugin `kotlin.multiplatform`.  
Por enquanto só `androidTarget()` está declarado — iOS pode ser adicionado sem mudar `:domain`.

```
domain/src/commonMain/   ← todo código do domínio
data/src/commonMain/     ← UrticariaRepositoryImpl (in-memory)
app/src/main/            ← Activity, Composables, DI (Android only)
```

## Injeção de dependências (Hilt)

`AppModule` (em `:app`) provê:
- `UrticariaRepository` — implementação `UrticariaRepositoryImpl` (in-memory)

Use cases são instanciados manualmente no ViewModel ou num módulo Hilt específico dentro de `:app`.

## Regras de desenvolvimento

- **Nunca adicionar imports Android em `:domain`** — o CI pode verificar com `grep -r "android\." domain/src/commonMain`.
- **KSP apenas** — não usar `kapt` (Hilt usa KSP em `:app`).
- Novos casos de uso vão em `domain/usecase/`, sem dependência de framework.
- Novos destinos de navegação são registrados no `NavHost` dentro de `:app`.

## Comandos úteis

```bash
# Build debug
./gradlew :app:assembleDebug

# Instalar no dispositivo/emulador
./gradlew :app:installDebug

# Verificar domínio sem Android
./gradlew :domain:compileKotlinAndroid

# Limpar caches
./gradlew clean
```

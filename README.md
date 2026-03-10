![Kotlin](https://img.shields.io/badge/Kotlin-2.0-blue)
![Compose](https://img.shields.io/badge/Jetpack%20Compose-UI-green)
![Architecture](https://img.shields.io/badge/Architecture-Clean-orange)
![Ktor](https://img.shields.io/badge/Network-Ktor-purple)

# Cryptocurrencies

A sample Android application demonstrating a **modern Android architecture** using Kotlin, Jetpack Compose, and Clean Architecture principles.

The application fetches cryptocurrency market data from a public API and displays a list of currencies along with price changes and historical price charts.

This project was created as an **architecture playground** to experiment with modern Android development tools, scalable project structure, and clean separation of concerns.

---

## Architecture

This project follows a layered architecture inspired by **Clean Architecture** combined with **MVVM**.

The application is structured around clear separation of responsibilities between layers.
```
UI Layer (Jetpack Compose)
        ↓
Presentation Layer (ViewModel)
        ↓
Domain / Data Layer (Repository)
        ↓
Network Layer (Ktor Client)
```

### Key architectural principles

- Unidirectional data flow
- Single source of truth for UI state
- Separation of concerns
- Testable business logic
- Dependency injection

---

## Tech Stack

### Language

- Kotlin

### UI

- Jetpack Compose  
- Material 3  
- Adaptive layouts

### Architecture

- MVVM  
- Clean Architecture principles  
- StateFlow for UI state management  

### Networking

- Ktor Client  
- Kotlinx Serialization  

### Dependency Injection

- Koin

### Async Programming

- Kotlin Coroutines  
- Kotlin Flow  

### Build System

- Gradle Kotlin DSL  
- Version Catalog  

---

## Project Structure

The project uses a **feature-oriented structure** with shared components placed under `core`.
```
app
├── core
│   ├── data
│   ├── domain
│   ├── navigation
│   └── ui
│
└── crypto
    ├── data
    ├── domain
    └── presentation
```

### core

Shared infrastructure used across the application.

Examples:

- networking utilities
- common domain utilities
- navigation components
- reusable UI elements

### crypto

Feature module responsible for cryptocurrency functionality.

Includes:

- API integration
- domain models
- business logic
- presentation layer

---

## Features

- Fetch cryptocurrency market data from API
- Display list of cryptocurrencies
- Show price change percentages
- Display historical price charts
- Adaptive list-detail UI layout

---

<details>
<summary><b>Architectural Decisions</b></summary>

### Ktor instead of Retrofit

The project uses **Ktor Client** for networking instead of the more common Retrofit.

Reasons:

- Fully Kotlin-based
- Better coroutine integration
- Flexible request pipeline
- Lightweight configuration

---

### BigDecimal for Financial Data

Cryptocurrency prices are represented using **BigDecimal** rather than floating point types.

This avoids precision errors when working with financial values.

---

### Version Catalog for Dependency Management

Dependencies are managed using **Gradle Version Catalog**.

Benefits:

- centralized dependency management
- easier version upgrades
- cleaner Gradle files

---

### Result Wrapper for Network Responses

Network responses are wrapped inside a `Result` type.

Benefits:

- consistent error handling
- avoids exception-driven flow
- simplifies UI state management

</details>

---

<details>
<summary><b>Possible Improvements</b></summary>

Future improvements that could be added:

- Unit tests
- Use-case layer
- Local caching
- Multi-module architecture
- Improved error handling
- Offline support

</details>

---

## Purpose of the Project

This project serves as a **learning and experimentation environment** for modern Android development.

It focuses on:

- scalable Android architecture
- modern Android libraries
- clean separation of layers
- maintainable project structure

---

## Author

**Servet Can Asutay**

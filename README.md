# Westech Ecommerce Mobile App

## Descripción General
Westech Ecommerce Mobile es una aplicación Android desarrollada con Kotlin y Jetpack Compose. La aplicación muestra un catálogo de productos organizados por categorías, permitiendo al usuario filtrar los productos según su interés. La aplicación se conecta a un backend PHP/MySQL para obtener los datos de los productos y categorías.

## Tecnologías Utilizadas

### Frontend (Android)
- **Kotlin 2.0.21**: Lenguaje de programación principal
- **Jetpack Compose**: Framework de UI declarativo para construir interfaces de usuario nativas
- **Material 3**: Sistema de diseño más reciente de Google con componentes modernos
- **ViewModel**: Para manejar la lógica de negocio y el estado de la aplicación
- **Coroutines**: Para operaciones asíncronas y concurrentes
- **Retrofit 2**: Cliente HTTP para comunicación con la API REST
- **Gson**: Biblioteca para serialización/deserialización de JSON
- **Coil**: Biblioteca de carga de imágenes para Compose
- **OkHttp**: Cliente HTTP con capacidades de interceptación y registro de peticiones

### Backend
El backend espera encontrarse en un servidor web local con la siguiente estructura:
- **PHP**: Para los endpoints de la API
- **MySQL**: Base de datos para almacenar productos y categorías
- **Endpoints**:
  - `/api/categories.php`: Devuelve todas las categorías disponibles
  - `/api/products.php`: Devuelve todos los productos o filtrados por categoría

## Arquitectura

La aplicación sigue el patrón de arquitectura MVVM (Model-View-ViewModel):

### Capas
1. **Modelo (Model)**
   - `data/model/`: Contiene las clases de datos que representan las entidades de negocio
     - `Product.kt`: Define la estructura de un producto
     - `Category.kt`: Define la estructura de una categoría
  
2. **Vista (View)**
   - `ui/theme/`: Contiene los componentes de UI y definiciones de tema
     - `ProductsScreen.kt`: Pantalla principal que muestra los productos y categorías
     - `Color.kt`, `Theme.kt`, `Type.kt`: Definen el tema visual de la aplicación

3. **ViewModel**
   - `ui/theme/ProductsViewModel.kt`: Gestiona el estado de la aplicación y la comunicación con la capa de datos

4. **Red (Network)**
   - `data/network/`:
     - `ApiService.kt`: Define los endpoints de la API
     - `RetrofitClient.kt`: Configuración del cliente HTTP

### Flujo de Datos
1. El ViewModel inicializa y solicita datos al backend a través de Retrofit
2. Los datos se reciben como respuestas JSON y se convierten a objetos Kotlin usando Gson
3. El estado se mantiene en el ViewModel usando objetos MutableState
4. La UI observa los cambios en el estado y se actualiza automáticamente gracias a la reactividad de Compose

## Flujo de Usuario

1. Al iniciar la aplicación, se cargan automáticamente todas las categorías y productos
2. El usuario puede ver la lista completa de productos inicialmente
3. El usuario puede abrir el menú desplegable para seleccionar una categoría específica
4. Al seleccionar una categoría, la lista de productos se actualiza automáticamente para mostrar solo los productos de esa categoría
5. El usuario puede volver a la opción "Todas" para ver todos los productos nuevamente

## Configuración del Proyecto

### Requisitos
- Android Studio Iguana 2023.2.1 o superior
- JDK 11 o superior
- Android SDK 24+ (Android 7.0 Nougat mínimo)
- Servidor web local con PHP y MySQL para el backend

### Compilación
La aplicación está configurada con Gradle usando el sistema de versiones de dependencias centralizado (libs.versions.toml):

- Android Gradle Plugin: 8.9.2
- Kotlin: 2.0.21
- Compose BOM: 2024.09.00
- Minimum SDK: 24
- Target SDK: 35
- Compile SDK: 35

### Dependencias Principales
- Compose UI y Foundation
- Material 3
- Lifecycle Components
- Retrofit y Gson
- Coil para carga de imágenes
- OkHttp para interceptores de red

## Guía para Desarrolladores

### Cómo extender la aplicación
1. **Nuevas funcionalidades de UI**: Agregar nuevas pantallas en el paquete `ui` siguiendo el patrón de composables existentes
2. **Nuevos endpoints de API**: Extender la interfaz `ApiService` con nuevos métodos
3. **Nuevos modelos de datos**: Crear nuevas data classes en el paquete `data/model`
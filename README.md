# DS6P2 Mobile App

## Descripción General
DS6P2 Mobile es una aplicación Android moderna desarrollada con Kotlin y Jetpack Compose. La aplicación muestra un catálogo de productos organizados por categorías, permitiendo al usuario filtrar los productos según su interés. La aplicación se conecta a un backend PHP/MySQL para obtener los datos de los productos y categorías.

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

## Estructura del Proyecto

```
ds6p2mobile/
├── app/
│   ├── build.gradle.kts             # Configuración de dependencias del módulo app
│   ├── src/
│   │   ├── main/
│   │   │   ├── AndroidManifest.xml  # Declaraciones de permisos y componentes
│   │   │   ├── java/
│   │   │   │   └── es/gian/ds6p2_mobile/
│   │   │   │       ├── data/
│   │   │   │       │   ├── model/
│   │   │   │       │   │   ├── Category.kt
│   │   │   │       │   │   └── Product.kt
│   │   │   │       │   └── network/
│   │   │   │       │       ├── ApiService.kt
│   │   │   │       │       └── RetrofitClient.kt
│   │   │   │       ├── MainActivity.kt  # Punto de entrada de la app
│   │   │   │       └── ui/
│   │   │   │           └── theme/
│   │   │   │               ├── Color.kt
│   │   │   │               ├── ProductsScreen.kt
│   │   │   │               ├── ProductsViewModel.kt
│   │   │   │               ├── Theme.kt
│   │   │   │               └── Type.kt
│   │   │   └── res/
│   │   │       ├── drawable/       # Recursos gráficos
│   │   │       ├── mipmap/         # Iconos de la app
│   │   │       ├── values/         # Strings, colores, estilos
│   │   │       └── xml/
│   │   │           └── network_security_config.xml  # Config. de seguridad de red
├── build.gradle.kts                # Configuración de dependencias del proyecto
├── gradle/
│   └── libs.versions.toml         # Versiones centralizadas de dependencias
└── settings.gradle.kts            # Configuración de proyectos incluidos
```

## Modelos de Datos

### Product
```kotlin
data class Product(
    val id: Int,
    val nombre: String,
    val descripcion: String?,
    val precio: Double,
    val stock: Int,
    @SerializedName("imagen_url") val imagenUrl: String?,
    @SerializedName("id_categoria") val categoriaId: Int?
)
```

### Category
```kotlin
data class Category(
    val id: Int,
    val nombre: String
)
```

## Componentes Principales

### MainActivity
Punto de entrada de la aplicación que configura la UI principal utilizando Compose.

```kotlin
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Ds6p2mobileTheme {
                ProductsScreen()
            }
        }
    }
}
```

### ProductsScreen
Define la interfaz de usuario principal con un menú desplegable para filtrar por categorías y una lista de productos.

La pantalla implementa:
- Un `ExposedDropdownMenuBox` para la selección de categorías
- Un `LazyColumn` para mostrar la lista de productos
- Tarjetas (`Card`) para cada producto con imagen, nombre y precio

### ProductsViewModel
Gestiona la lógica de negocio y el estado de la UI.

Funcionalidades:
- Carga de categorías desde el backend
- Carga de productos, con filtrado opcional por categoría
- Mantenimiento del estado de la categoría seleccionada
- Reacción a las acciones del usuario (selección de categoría)

### ApiService
Define los endpoints de la API REST:

```kotlin
interface ApiService {
    @GET("api/categories.php")
    suspend fun getCategories(): CategoriesResponse

    @GET("api/products.php")
    suspend fun getProducts(
        @Query("category") categoryId: Int? = null
    ): ProductsResponse
}
```

### RetrofitClient
Configura el cliente HTTP para comunicarse con la API.

```kotlin
object RetrofitClient {
    // Configuración de logging y cliente HTTP
    private const val BASE_URL = "http://10.0.2.2/ds6p2/"

    val api: ApiService by lazy {
        // Inicialización de Retrofit
    }
}
```

## Configuración de Red y Seguridad

La aplicación está configurada para permitir tráfico HTTP sin cifrar durante el desarrollo, especialmente útil para trabajar con un servidor local:

```xml
<network-security-config>
    <base-config cleartextTrafficPermitted="true" />
</network-security-config>
```

## Interfaz de Usuario

La interfaz de usuario se construye completamente con Jetpack Compose, siguiendo los principios de Material Design 3:

- **Sistema de Tema** con soporte para modo claro/oscuro y colores dinámicos en Android 12+
- **Componentes Material 3** como Cards, DropdownMenus, etc.
- **Tipografía** personalizada para una experiencia visual coherente
- **Imágenes asíncronas** cargadas eficientemente con Coil

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

### Prácticas recomendadas
- Mantener la separación de responsabilidades según la arquitectura MVVM
- Utilizar Coroutines para operaciones asíncronas
- Manejar los estados y errores adecuadamente
- Seguir los principios de diseño de Material Design 3

## Estructura del Backend (Esperada)

El servidor debe proveer dos endpoints JSON:

### GET /api/categories.php
Devuelve todas las categorías disponibles.

Respuesta esperada:
```json
{
  "success": true,
  "categories": [
    {
      "id": 1,
      "nombre": "Electrónica"
    },
    {
      "id": 2,
      "nombre": "Ropa"
    }
  ]
}
```

### GET /api/products.php?category=X
Devuelve productos, opcionalmente filtrados por categoría.

Respuesta esperada:
```json
{
  "success": true,
  "products": [
    {
      "id": 1,
      "nombre": "Smartphone XYZ",
      "descripcion": "Un smartphone avanzado",
      "precio": 299.99,
      "stock": 10,
      "imagen_url": "http://ejemplo.com/img/smartphone.jpg",
      "id_categoria": 1
    }
  ]
}
```

## Conclusiones

DS6P2 Mobile es una aplicación Android moderna que demuestra el uso de las tecnologías más recientes en el desarrollo de aplicaciones móviles, incluyendo Jetpack Compose, Material 3, y una arquitectura MVVM bien definida. La aplicación proporciona una interfaz de usuario fluida y reactiva para explorar un catálogo de productos, con capacidad para filtrar por categorías.

La aplicación está diseñada para ser escalable y mantenible, con una clara separación de responsabilidades entre la UI, la lógica de negocio y la comunicación con el backend.
# 🛒 WalaSpringBoot - Tienda de Segunda Mano 2025

[![Java](https://img.shields.io/badge/Java-21-orange)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.0-brightgreen)](https://spring.io/projects/spring-boot)
[![Gradle](https://img.shields.io/badge/Gradle-8.11.1-blue)](https://gradle.org/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

Migración completa y modernizada del proyecto [WalaSpringBoot2020](https://github.com/joseluisgs/WalaSpringBoot2020) con las últimas tecnologías 2025. Una aplicación web de comercio electrónico de segunda mano con características avanzadas de seguridad, internacionalización y gestión de usuarios.

## 📑 Tabla de Contenidos

- [Características](#-características)
- [Tecnologías](#-tecnologías)
- [Requisitos](#-requisitos)
- [Instalación](#-instalación-y-configuración)
- [Uso](#-uso)
- [Documentación](#-documentación)
- [Arquitectura](#-arquitectura)
- [Migración](#-migración-desde-walaspringboot2020)
- [Contribución](#-contribución)
- [Autor](#-autor)

## ✨ Características

### Funcionalidades Principales

- 🛍️ **Marketplace de Segunda Mano**: Compra y vende productos usados
- 🔐 **Sistema de Roles**: ADMIN, USER, MODERATOR con permisos diferenciados
- 🌍 **Internacionalización**: Soporte completo para Español e Inglés
- 📧 **Notificaciones por Email**: Confirmación automática de compras
- 📊 **Dashboard Administrativo**: Estadísticas y gráficos con Chart.js
- 🔍 **Búsqueda Avanzada**: Filtros por nombre, categoría y precio
- 🖼️ **Gestión de Imágenes**: Subida, validación y redimensionado automático
- 📱 **Responsive Design**: Bootstrap 5.3 optimizado para todos los dispositivos
- ⚡ **Cache Inteligente**: Mejora de rendimiento con Spring Cache
- 📄 **Generación de PDFs**: Facturas automáticas de compras

### Productos 2024-2025

La aplicación incluye productos actuales y relevantes:

- 📱 **Smartphones**: iPhone 15 Pro Max, Samsung Galaxy S24 Ultra, Google Pixel 8 Pro
- 💻 **Laptops**: MacBook Pro M3
- 🎧 **Audio**: AirPods Pro 2ª Generación
- 🎮 **Gaming**: Steam Deck OLED

## 🚀 Tecnologías

### Backend

| Tecnología | Versión | Descripción |
|------------|---------|-------------|
| **Java** | 21 (LTS) | Lenguaje de programación moderno y robusto |
| **Spring Boot** | 3.4.0 | Framework para aplicaciones empresariales |
| **Spring Security** | 6.x | Autenticación y autorización con configuración 2025 |
| **Spring Data JPA** | - | Persistencia de datos con Hibernate |
| **Spring Cache** | - | Sistema de caché para mejorar rendimiento |
| **Spring Mail** | - | Envío de emails de confirmación |
| **Gradle** | 8.11.1 | Build tool con Kotlin DSL |
| **H2 Database** | - | Base de datos embebida para desarrollo |

### Frontend

| Tecnología | Versión | Descripción |
|------------|---------|-------------|
| **Pebble Template Engine** | 3.2.2 | Motor de plantillas moderno y eficiente |
| **Bootstrap** | 5.3 (CDN) | Framework CSS responsive |
| **Bootstrap Icons** | 1.11.3 | Iconografía moderna |
| **Chart.js** | 4.4.0 | Gráficos interactivos para el dashboard |

### Seguridad

- ✅ **Spring Security 6** con configuración moderna
- ✅ **SecurityFilterChain** (no WebSecurityConfigurerAdapter deprecated)
- ✅ **BCrypt Password Encoder** para encriptación de contraseñas
- ✅ **CSRF Protection** habilitada
- ✅ **Method-level Security** con @PreAuthorize
- ✅ **Role-Based Access Control** (RBAC)

## 📋 Requisitos

### Software Necesario

- **Java JDK 21 o superior** ([Descargar](https://adoptium.net/))
- **Git** ([Descargar](https://git-scm.com/))
- **IntelliJ IDEA** (recomendado) o cualquier IDE Java ([Descargar](https://www.jetbrains.com/idea/download/))

### Opcional

- **Postman** para probar endpoints (si se agregan APIs REST)
- **Cliente SMTP** configurado para pruebas de email

## 🔧 Instalación y Configuración

### 1. Clonar el Repositorio

```bash
git clone https://github.com/joseluisgs/PruebaWalaSpringBoot.git
cd PruebaWalaSpringBoot
```

### 2. Configuración en IntelliJ IDEA

#### Opción A: Importar Proyecto

1. Abre IntelliJ IDEA
2. `File` → `Open...`
3. Selecciona la carpeta del proyecto
4. IntelliJ detectará automáticamente el proyecto Gradle
5. Espera a que se descarguen las dependencias

#### Opción B: Clonar desde IntelliJ

1. Abre IntelliJ IDEA
2. `Git` → `Clone...`
3. Pega la URL: `https://github.com/joseluisgs/PruebaWalaSpringBoot.git`
4. Haz clic en `Clone`

### 3. Configurar el JDK

1. `File` → `Project Structure...` → `Project`
2. En `SDK`, selecciona Java 21 o superior
3. Si no está disponible, haz clic en `Add SDK` → `Download JDK...`
4. Selecciona Eclipse Temurin (AdoptOpenJDK) versión 21

### 4. Compilar el Proyecto

```bash
./gradlew build
```

En Windows:
```cmd
gradlew.bat build
```

### 5. Ejecutar la Aplicación

#### Desde Terminal:

```bash
./gradlew bootRun
```

#### Desde IntelliJ IDEA:

1. Abre `WalaSpringBootApplication.java`
2. Haz clic derecho → `Run 'WalaSpringBootApplication'`
3. O usa el botón verde de play ▶️

La aplicación estará disponible en: **http://localhost:8080**

### 6. Configuración de Email (Opcional)

Para habilitar el envío de emails de confirmación:

1. Abre `src/main/resources/application.properties`
2. Configura las propiedades SMTP:

```properties
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=tu-email@gmail.com
spring.mail.password=tu-app-password
```

Para Gmail, necesitas generar una [contraseña de aplicación](https://support.google.com/accounts/answer/185833).

## 📖 Uso

### Usuarios de Prueba

La aplicación inicializa automáticamente con usuarios de diferentes roles:

| Email | Password | Rol | Descripción |
|-------|----------|-----|-------------|
| `admin@walaspringboot.com` | `admin` | **ADMIN** | Acceso total al dashboard y gestión |
| `prueba@prueba.com` | `prueba` | **USER** | Usuario estándar con sus productos |
| `moderador@walaspringboot.com` | `moderador` | **MODERATOR** | Permisos intermedios |
| `otro@otro.com` | `otro` | **USER** | Otro usuario estándar |

### Flujo de Usuario Normal

1. **Navegación**: Explora los productos disponibles en la página principal
2. **Filtrado**: Usa los filtros por categoría, precio y búsqueda
3. **Login**: Inicia sesión con un usuario de prueba
4. **Compra**: Añade productos al carrito y finaliza la compra
5. **Mis Compras**: Revisa tus compras en el historial
6. **Mis Productos**: Gestiona tus propios productos

### Flujo de Administrador

1. **Login**: Inicia sesión como `admin@walaspringboot.com`
2. **Dashboard**: Accede al panel de control desde el menú superior
3. **Estadísticas**: Visualiza métricas de productos, usuarios y compras
4. **Gestión de Usuarios**: Administra los usuarios registrados
5. **Gestión de Productos**: Supervisa todos los productos del sistema

### Cambio de Idioma

1. Haz clic en el icono del globo 🌍 en la barra de navegación
2. Selecciona Español o English
3. La interfaz cambiará automáticamente

### Consola H2 (Base de Datos)

Accede a la consola de base de datos en modo desarrollo:

- URL: **http://localhost:8080/h2-console**
- JDBC URL: `jdbc:h2:./walaspringboot`
- Usuario: `sa`
- Contraseña: *(vacío)*

## 📚 Documentación

### Tutoriales Incluidos

- **[SpringMVC.md](SpringMVC.md)**: Tutorial completo de Spring MVC para aplicaciones web dinámicas
- **[Pebble.md](Pebble.md)**: Guía exhaustiva del motor de plantillas Pebble

### Perfiles de Aplicación

La aplicación soporta dos perfiles:

#### Perfil DEV (Desarrollo)

```properties
spring.profiles.active=dev
```

- ✅ Logging detallado (DEBUG)
- ✅ Caché deshabilitado para hot-reload
- ✅ Consola H2 habilitada
- ✅ Directorio de uploads limpiado al iniciar
- ✅ SQL queries visibles

#### Perfil PROD (Producción)

```properties
spring.profiles.active=prod
```

- ✅ Logging optimizado (WARN)
- ✅ Caché habilitado
- ✅ Consola H2 deshabilitada
- ✅ Directorio de uploads persistente
- ✅ SQL queries ocultas

## 🏗️ Arquitectura

### Estructura del Proyecto

```
src/main/
├── java/com/joseluisgs/walaspringboot/
│   ├── configuracion/          # Configuraciones (i18n, validación, auditoría)
│   ├── controladores/          # Controllers MVC
│   │   ├── AdminController     # Dashboard administrativo
│   │   ├── CompraController    # Gestión de compras
│   │   ├── LoginController     # Autenticación
│   │   ├── ProductoController  # CRUD de productos
│   │   └── ZonaPublicaController # Área pública
│   ├── modelos/                # Entities JPA
│   │   ├── Compra             # Compras realizadas
│   │   ├── Producto           # Productos del marketplace
│   │   └── Usuario            # Usuarios del sistema
│   ├── repositorios/           # Repositories JPA
│   ├── seguridad/              # Security configuration
│   │   ├── SeguridadConfig    # Spring Security setup
│   │   └── UserDetailsServiceImpl
│   ├── servicios/              # Business logic
│   │   ├── CompraServicio
│   │   ├── EmailService       # Envío de emails
│   │   ├── ImageService       # Procesamiento de imágenes
│   │   ├── ProductoServicio
│   │   └── UsuarioServicio
│   ├── upload/                 # File storage
│   ├── utilidades/             # Utilities (PDF generation)
│   └── validacion/             # Custom validators
└── resources/
    ├── application.properties          # Configuración principal
    ├── application-dev.properties      # Perfil desarrollo
    ├── application-prod.properties     # Perfil producción
    ├── messages_es.properties          # i18n Español
    ├── messages_en.properties          # i18n Inglés
    ├── static/                         # Recursos estáticos
    │   ├── css/
    │   └── images/
    └── templates/                      # Plantillas Pebble
        ├── admin/                      # Templates admin
        │   ├── dashboard.peb
        │   ├── productos.peb
        │   └── usuarios.peb
        ├── app/                        # Templates privadas
        │   ├── compra/
        │   └── producto/
        ├── fragments/                  # Componentes reutilizables
        │   ├── head.peb
        │   ├── navbar.peb
        │   └── footer.peb
        ├── index.peb                   # Página principal
        └── login.peb                   # Login/Registro
```

### Patrón de Diseño

La aplicación sigue el patrón **MVC (Model-View-Controller)**:

- **Model**: Entidades JPA (Producto, Usuario, Compra)
- **View**: Plantillas Pebble (.peb)
- **Controller**: Controllers Spring MVC

Además implementa:

- **Repository Pattern**: Para acceso a datos
- **Service Layer**: Para lógica de negocio
- **Dependency Injection**: Con Spring IoC

## 📝 Migración desde WalaSpringBoot2020

### Cambios Principales

| Aspecto | 2020 | 2025 |
|---------|------|------|
| **Build System** | Maven | Gradle + Kotlin DSL |
| **Java** | 8 | 21 (LTS) |
| **Spring Boot** | 2.2.2 | 3.4.0 |
| **Template Engine** | Thymeleaf | Pebble 3.2.2 |
| **Bootstrap** | 3.3.7 (WebJars) | 5.3 (CDN) |
| **Security** | WebSecurityConfigurerAdapter | SecurityFilterChain |
| **Jakarta** | javax.* | jakarta.* |
| **Internationalization** | No | Sí (ES/EN) |
| **Roles** | No | Sí (ADMIN/USER/MODERATOR) |
| **Email** | No | Sí (confirmaciones) |
| **Admin Dashboard** | No | Sí (con gráficos) |
| **Cache** | No | Sí (@Cacheable) |
| **Image Processing** | No | Sí (redimensionado) |

### Mejoras Implementadas 2025

1. ✅ **Localización i18n**: Soporte multiidioma completo
2. ✅ **Gestión automática de directorios**: Por perfil (dev/prod)
3. ✅ **Productos actualizados 2024-2025**: Tecnología actual
4. ✅ **Sistema de roles y permisos**: RBAC con Spring Security
5. ✅ **Gestión de imágenes**: Validación y redimensionado
6. ✅ **Búsqueda y filtros**: Por nombre, categoría, precio
7. ✅ **Dashboard administrativo**: Estadísticas y gráficos Chart.js
8. ✅ **Validación avanzada**: Custom validators y mensajes localizados
9. ✅ **Logging enriquecido**: Perfiles dev/prod diferenciados
10. ✅ **Email de confirmación**: Solo para compras realizadas

## 🤝 Contribución

Las contribuciones son bienvenidas. Para cambios importantes:

1. Fork el repositorio
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## 📄 Licencia

Este proyecto está bajo la Licencia MIT. Ver el archivo `LICENSE` para más detalles.

## 👨‍💻 Autor

**José Luis González Sánchez**

- 🐦 Twitter: [@joseluisgonsan](https://twitter.com/joseluisgonsan)
- 💼 LinkedIn: [joseluisgonsan](https://www.linkedin.com/in/joseluisgonsan/)
- 🏫 Centro: [CIFP Virgen de Gracia](http://www.cifpvirgendegracia.com/)
- 📧 Email: joseluis.gonzalez@cifpvirgendegracia.com

## 🙏 Agradecimientos

- Al [CIFP Virgen de Gracia](http://www.cifpvirgendegracia.com/) por el apoyo educativo
- A la comunidad de Spring Boot por la excelente documentación
- Al equipo de Pebble Template Engine por su motor eficiente

---

**Versión:** 2.0 (2025)  
**Migrado desde:** [WalaSpringBoot2020](https://github.com/joseluisgs/WalaSpringBoot2020)  
**Última actualización:** Enero 2025

⭐ Si te ha gustado este proyecto, ¡dale una estrella en GitHub!

# WalaSpringBoot - Tienda de Segunda Mano

Migración completa y modernizada del proyecto [WalaSpringBoot2020](https://github.com/joseluisgs/WalaSpringBoot2020) con las últimas tecnologías 2025.

## 🚀 Tecnologías

### Backend
- **Java 21** (LTS) - Preparado para Java 25
- **Spring Boot 3.4.0** - Última versión con soporte para Java 21+
- **Gradle 8.11.1** con **Kotlin DSL** - Sistema de construcción moderno
- **Spring Data JPA** - Persistencia de datos
- **H2 Database** - Base de datos embebida
- **Spring Security 6** - Seguridad con configuración moderna 2025
  - SecurityFilterChain en lugar de WebSecurityConfigurerAdapter
  - AuthenticationManager moderno
  - BCrypt Password Encoder
  - CSRF Protection moderna
  - Session Management actualizado

### Frontend
- **Pebble Template Engine 3.2.2** - Motor de plantillas moderno (migrado desde Thymeleaf)
- **Bootstrap 5.3** via CDN - Framework CSS última versión
- **Bootstrap Icons** - Iconografía moderna

### Características
- **Caché en Servicios** - @EnableCaching, @Cacheable, @CacheEvict
- **Validación Jakarta** - Validación de datos con Jakarta Bean Validation
- **PDF Generation** - iText y html2pdf para generación de facturas
- **File Upload** - Sistema de almacenamiento de archivos
- **Spring DevTools** - Desarrollo ágil con hot reload

## 📋 Requisitos

- **Java 21 o superior** (JDK)
- **Gradle 8.x** (incluido via wrapper)
- No se requiere instalación de dependencias adicionales

## 🔧 Instalación y Ejecución

### Clonar el repositorio
```bash
git clone https://github.com/joseluisgs/PruebaWalaSpringBoot.git
cd PruebaWalaSpringBoot
```

### Compilar el proyecto
```bash
./gradlew build
```

### Ejecutar la aplicación
```bash
./gradlew bootRun
```

La aplicación estará disponible en: **http://localhost:8080**

## 🗄️ Base de Datos

### Consola H2
Accede a la consola H2 en: **http://localhost:8080/h2-console**

**Credenciales:**
- JDBC URL: `jdbc:h2:./walaspringboot`
- Usuario: `sa`
- Contraseña: *(vacío)*

### Datos de Prueba

La aplicación inicializa automáticamente con usuarios y productos de prueba:

**Usuarios:**
- Email: `prueba@prueba.com` / Password: `prueba`
- Email: `otro@otro.com` / Password: `otro`

**Productos:**
- 6 productos de prueba distribuidos entre los usuarios

## 📝 Migración desde WalaSpringBoot2020

### Cambios Principales

1. **Build System**: Maven → Gradle con Kotlin DSL
2. **Java**: 8 → 21
3. **Spring Boot**: 2.2.2 → 3.4.0
4. **Template Engine**: Thymeleaf → Pebble
5. **Bootstrap**: 3.3.7 (WebJars) → 5.3 (CDN)
6. **Security**: WebSecurityConfigurerAdapter → SecurityFilterChain
7. **Jakarta**: javax.* → jakarta.*
8. **Cache**: Nuevo - @EnableCaching y anotaciones de cache

## 👨‍💻 Autor

**José Luis González Sánchez**
- Twitter: [@joseluisgonsan](https://twitter.com/joseluisgonsan)
- Centro: [CIFP Virgen de Gracia](http://www.cifpvirgendegracia.com/)

---

**Versión:** 2.0 (2025)  
**Migrado desde:** [WalaSpringBoot2020](https://github.com/joseluisgs/WalaSpringBoot2020)

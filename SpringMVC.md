# 📖 Tutorial de Spring MVC - Aplicaciones Web Dinámicas

## Tabla de Contenidos

- [Introducción](#introducción)
- [Arquitectura MVC](#arquitectura-mvc)
- [Configuración Básica](#configuración-básica)
- [Controllers](#controllers)
- [Modelos y Vista](#modelos-y-vista)
- [Manejo de Formularios](#manejo-de-formularios)
- [Validación](#validación)
- [Seguridad](#seguridad)
- [Internacionalización](#internacionalización)
- [Mejores Prácticas](#mejores-prácticas)

## Introducción

Spring MVC es un framework web basado en el patrón Model-View-Controller que facilita la creación de aplicaciones web robustas y escalables en Java.

### ¿Qué es MVC?

**Model-View-Controller (MVC)** es un patrón de diseño que separa la lógica de negocio de la presentación:

- **Model (Modelo)**: Representa los datos y la lógica de negocio
- **View (Vista)**: Presenta los datos al usuario (HTML, JSON, etc.)
- **Controller (Controlador)**: Maneja las peticiones HTTP y coordina Model y View

### Ventajas de Spring MVC

✅ **Separación de responsabilidades**: Código más mantenible y testeable  
✅ **Configuración flexible**: Basada en anotaciones o XML  
✅ **Integración con Spring**: Acceso a todas las características de Spring Framework  
✅ **Soporte para múltiples vistas**: Pebble, Thymeleaf, JSP, FreeMarker  
✅ **RESTful**: Fácil creación de APIs REST  

## Arquitectura MVC

### Flujo de una Petición HTTP

```
1. Usuario → Navegador
2. Navegador → HTTP Request → DispatcherServlet
3. DispatcherServlet → Handler Mapping (encuentra el Controller)
4. Controller → procesa la petición → devuelve Model y View
5. DispatcherServlet → View Resolver (encuentra la vista)
6. View → renderiza con el Model
7. DispatcherServlet → HTTP Response → Navegador
8. Navegador → Usuario
```

### Componentes Principales

#### 1. DispatcherServlet

El servlet frontal que recibe todas las peticiones HTTP y las delega a los controllers apropiados.

```java
// Configuración automática en Spring Boot
// No requiere configuración manual
```

#### 2. Controllers

Clases anotadas con `@Controller` que manejan peticiones HTTP.

```java
@Controller
@RequestMapping("/productos")
public class ProductoController {
    
    @GetMapping("/lista")
    public String listar(Model model) {
        model.addAttribute("productos", productoServicio.findAll());
        return "productos/lista";
    }
}
```

#### 3. Model

Contenedor de datos que se pasan a la vista.

```java
@GetMapping("/detalle/{id}")
public String detalle(@PathVariable Long id, Model model) {
    Producto producto = productoServicio.findById(id);
    model.addAttribute("producto", producto);
    return "productos/detalle";
}
```

#### 4. View Resolver

Resuelve el nombre lógico de la vista a una plantilla física.

```properties
# application.properties
pebble.suffix=.peb
pebble.cache=false
```

## Configuración Básica

### Estructura del Proyecto

```
src/main/
├── java/
│   └── com.ejemplo.app/
│       ├── configuracion/
│       │   └── WebConfig.java
│       ├── controladores/
│       │   ├── HomeController.java
│       │   └── ProductoController.java
│       ├── modelos/
│       │   └── Producto.java
│       ├── repositorios/
│       │   └── ProductoRepository.java
│       └── servicios/
│           └── ProductoServicio.java
└── resources/
    ├── application.properties
    ├── templates/
    │   ├── index.peb
    │   └── productos/
    │       ├── lista.peb
    │       └── detalle.peb
    └── static/
        ├── css/
        ├── js/
        └── images/
```

### Dependencias Gradle

```kotlin
dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("io.pebbletemplates:pebble-spring-boot-starter:3.2.2")
}
```

## Controllers

### Anotaciones Básicas

#### @Controller vs @RestController

```java
// Para aplicaciones web con vistas
@Controller
public class WebController {
    @GetMapping("/home")
    public String home(Model model) {
        return "home"; // Retorna nombre de vista
    }
}

// Para APIs REST que retornan JSON
@RestController
@RequestMapping("/api")
public class ApiController {
    @GetMapping("/productos")
    public List<Producto> productos() {
        return productoServicio.findAll(); // Retorna JSON automáticamente
    }
}
```

#### @RequestMapping

Mapea URLs a métodos del controller:

```java
@Controller
@RequestMapping("/productos") // Base URL
public class ProductoController {
    
    @GetMapping // GET /productos
    public String lista() { }
    
    @GetMapping("/{id}") // GET /productos/123
    public String detalle(@PathVariable Long id) { }
    
    @PostMapping // POST /productos
    public String crear(@ModelAttribute Producto producto) { }
    
    @PutMapping("/{id}") // PUT /productos/123
    public String actualizar(@PathVariable Long id, @ModelAttribute Producto producto) { }
    
    @DeleteMapping("/{id}") // DELETE /productos/123
    public String eliminar(@PathVariable Long id) { }
}
```

### Parámetros de Método

#### @PathVariable

Extrae variables de la URL:

```java
@GetMapping("/producto/{id}")
public String detalle(@PathVariable Long id, Model model) {
    Producto producto = productoServicio.findById(id);
    model.addAttribute("producto", producto);
    return "producto/detalle";
}

// URL: /producto/123 → id = 123
```

#### @RequestParam

Extrae parámetros de query string:

```java
@GetMapping("/buscar")
public String buscar(
    @RequestParam(required = false) String q,
    @RequestParam(defaultValue = "0") int page,
    Model model
) {
    List<Producto> productos = productoServicio.buscar(q, page);
    model.addAttribute("productos", productos);
    return "productos/lista";
}

// URL: /buscar?q=laptop&page=2
// q = "laptop", page = 2
```

#### @ModelAttribute

Vincula datos de formulario a objetos Java:

```java
@PostMapping("/producto/crear")
public String crear(@ModelAttribute Producto producto) {
    productoServicio.guardar(producto);
    return "redirect:/productos";
}
```

#### @RequestBody

Recibe datos JSON (para APIs REST):

```java
@PostMapping("/api/productos")
@ResponseBody
public Producto crearApi(@RequestBody Producto producto) {
    return productoServicio.guardar(producto);
}
```

### Model y ModelAndView

#### Usando Model

```java
@GetMapping("/productos")
public String lista(Model model) {
    model.addAttribute("productos", productoServicio.findAll());
    model.addAttribute("titulo", "Lista de Productos");
    return "productos/lista";
}
```

#### Usando ModelAndView

```java
@GetMapping("/productos")
public ModelAndView lista() {
    ModelAndView mav = new ModelAndView("productos/lista");
    mav.addObject("productos", productoServicio.findAll());
    mav.addObject("titulo", "Lista de Productos");
    return mav;
}
```

### Atributos Globales

Usa `@ModelAttribute` a nivel de método para datos comunes:

```java
@Controller
public class BaseController {
    
    @ModelAttribute("appName")
    public String appName() {
        return "WalaSpringBoot";
    }
    
    @ModelAttribute("authenticated")
    public boolean isAuthenticated(Principal principal) {
        return principal != null;
    }
}
```

## Modelos y Vista

### Creando Modelos (Entities)

```java
@Entity
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @NotEmpty(message = "El nombre es obligatorio")
    private String nombre;
    
    @Min(value = 0, message = "El precio debe ser positivo")
    private float precio;
    
    @Column(length = 1000)
    private String descripcion;
    
    private String categoria;
    
    @ManyToOne
    private Usuario propietario;
    
    // Getters y setters
}
```

### Repositories

```java
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByNombreContainsIgnoreCase(String nombre);
    List<Producto> findByCategoria(String categoria);
    List<Producto> findByPrecioBetween(float min, float max);
}
```

### Services

```java
@Service
public class ProductoServicio {
    
    @Autowired
    private ProductoRepository repositorio;
    
    @Cacheable("productos")
    public List<Producto> findAll() {
        return repositorio.findAll();
    }
    
    @Cacheable(value = "productos", key = "#id")
    public Producto findById(Long id) {
        return repositorio.findById(id).orElse(null);
    }
    
    @CacheEvict(value = "productos", allEntries = true)
    public Producto guardar(Producto producto) {
        return repositorio.save(producto);
    }
}
```

## Manejo de Formularios

### Formulario de Creación

**Controller:**

```java
@Controller
@RequestMapping("/productos")
public class ProductoController {
    
    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("producto", new Producto());
        return "productos/form";
    }
    
    @PostMapping("/guardar")
    public String guardar(
        @Valid @ModelAttribute Producto producto,
        BindingResult result,
        RedirectAttributes redirectAttributes
    ) {
        if (result.hasErrors()) {
            return "productos/form";
        }
        
        productoServicio.guardar(producto);
        redirectAttributes.addFlashAttribute("mensaje", "Producto guardado correctamente");
        return "redirect:/productos";
    }
}
```

**Vista (Pebble):**

```html
<form method="POST" action="/productos/guardar">
    <div class="mb-3">
        <label for="nombre" class="form-label">Nombre</label>
        <input type="text" class="form-control" id="nombre" name="nombre" 
               value="{{ producto.nombre }}" required>
    </div>
    
    <div class="mb-3">
        <label for="precio" class="form-label">Precio</label>
        <input type="number" step="0.01" class="form-control" id="precio" 
               name="precio" value="{{ producto.precio }}" required>
    </div>
    
    <div class="mb-3">
        <label for="descripcion" class="form-label">Descripción</label>
        <textarea class="form-control" id="descripcion" name="descripcion" 
                  rows="3">{{ producto.descripcion }}</textarea>
    </div>
    
    <button type="submit" class="btn btn-primary">Guardar</button>
</form>
```

### Upload de Archivos

**Controller:**

```java
@PostMapping("/producto/crear")
public String crear(
    @ModelAttribute Producto producto,
    @RequestParam("file") MultipartFile file
) {
    if (!file.isEmpty()) {
        String filename = storageService.store(file);
        producto.setImagen(filename);
    }
    
    productoServicio.guardar(producto);
    return "redirect:/productos";
}
```

**Vista:**

```html
<form method="POST" action="/producto/crear" enctype="multipart/form-data">
    <div class="mb-3">
        <label for="file" class="form-label">Imagen</label>
        <input type="file" class="form-control" id="file" name="file" 
               accept="image/*" required>
    </div>
    <button type="submit" class="btn btn-primary">Crear</button>
</form>
```

## Validación

### Bean Validation

```java
@Entity
public class Usuario {
    @NotEmpty(message = "{usuario.nombre.vacio}")
    private String nombre;
    
    @Email(message = "{usuario.email.invalido}")
    @NotEmpty
    private String email;
    
    @Size(min = 6, message = "{usuario.password.corta}")
    private String password;
    
    @Min(value = 18, message = "{usuario.edad.minima}")
    private int edad;
}
```

### Custom Validator

**Anotación:**

```java
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidImageValidator.class)
public @interface ValidImage {
    String message() default "Formato de imagen inválido";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
```

**Validator:**

```java
public class ValidImageValidator implements ConstraintValidator<ValidImage, MultipartFile> {
    
    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        if (file == null || file.isEmpty()) {
            return true;
        }
        
        String contentType = file.getContentType();
        return contentType != null && 
               (contentType.equals("image/jpeg") || 
                contentType.equals("image/png") ||
                contentType.equals("image/gif"));
    }
}
```

### Mostrar Errores en la Vista

```html
{% if errors %}
<div class="alert alert-danger">
    <ul>
        {% for error in errors %}
        <li>{{ error }}</li>
        {% endfor %}
    </ul>
</div>
{% endif %}
```

## Seguridad

### Configuración de Spring Security

```java
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SeguridadConfig {
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/public/**", "/css/**", "/images/**").permitAll()
                .requestMatchers("/admin/**").hasAuthority("ADMIN")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/auth/login")
                .defaultSuccessUrl("/")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/auth/logout")
                .logoutSuccessUrl("/")
                .permitAll()
            );
        
        return http.build();
    }
}
```

### Method-Level Security

```java
@Controller
@RequestMapping("/admin")
public class AdminController {
    
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        // Solo accesible para ADMIN
        return "admin/dashboard";
    }
    
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODERATOR')")
    @GetMapping("/usuarios")
    public String usuarios(Model model) {
        // Accesible para ADMIN y MODERATOR
        return "admin/usuarios";
    }
}
```

### Obtener Usuario Autenticado

```java
@GetMapping("/perfil")
public String perfil(Principal principal, Model model) {
    String email = principal.getName();
    Usuario usuario = usuarioServicio.buscarPorEmail(email);
    model.addAttribute("usuario", usuario);
    return "perfil";
}
```

## Internacionalización

### Configuración i18n

```java
@Configuration
public class ConfiguracionI18n implements WebMvcConfigurer {
    
    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(new Locale("es"));
        return slr;
    }
    
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
}
```

### Archivos de Mensajes

**messages_es.properties:**

```properties
producto.nombre=Nombre del Producto
producto.precio=Precio
producto.crear=Crear Producto
usuario.nombre=Nombre de Usuario
```

**messages_en.properties:**

```properties
producto.nombre=Product Name
producto.precio=Price
producto.crear=Create Product
usuario.nombre=Username
```

### Uso en Plantillas

```html
<h1>{{ i18n('producto.crear') }}</h1>

<label>{{ i18n('producto.nombre') }}</label>
<input type="text" name="nombre">

<label>{{ i18n('producto.precio') }}</label>
<input type="number" name="precio">
```

### Cambio de Idioma

```html
<a href="?lang=es">Español</a>
<a href="?lang=en">English</a>
```

## Mejores Prácticas

### 1. Separación de Responsabilidades

❌ **Mal:**

```java
@Controller
public class ProductoController {
    @Autowired
    private ProductoRepository repositorio;
    
    @GetMapping("/productos")
    public String lista(Model model) {
        model.addAttribute("productos", repositorio.findAll());
        return "productos/lista";
    }
}
```

✅ **Bien:**

```java
@Controller
public class ProductoController {
    @Autowired
    private ProductoServicio servicio;
    
    @GetMapping("/productos")
    public String lista(Model model) {
        model.addAttribute("productos", servicio.findAll());
        return "productos/lista";
    }
}
```

### 2. Manejo de Errores

```java
@ExceptionHandler(ProductoNotFoundException.class)
public String handleProductoNotFound(ProductoNotFoundException ex, Model model) {
    model.addAttribute("error", ex.getMessage());
    return "error/404";
}

@ExceptionHandler(Exception.class)
public String handleGenericError(Exception ex, Model model) {
    model.addAttribute("error", "Error interno del servidor");
    return "error/500";
}
```

### 3. Redirect After Post (PRG Pattern)

```java
@PostMapping("/producto/crear")
public String crear(@ModelAttribute Producto producto, RedirectAttributes redirectAttributes) {
    productoServicio.guardar(producto);
    redirectAttributes.addFlashAttribute("mensaje", "Producto creado correctamente");
    return "redirect:/productos"; // Evita reenvío del formulario
}
```

### 4. DTOs para Formularios

```java
public class ProductoFormDTO {
    @NotEmpty
    private String nombre;
    
    @Min(0)
    private float precio;
    
    @ValidImage
    private MultipartFile imagen;
    
    // Getters y setters
    
    public Producto toEntity() {
        Producto producto = new Producto();
        producto.setNombre(this.nombre);
        producto.setPrecio(this.precio);
        return producto;
    }
}
```

### 5. Cache Inteligente

```java
@Service
public class ProductoServicio {
    
    @Cacheable("productos")
    public List<Producto> findAll() {
        return repositorio.findAll();
    }
    
    @CacheEvict(value = "productos", allEntries = true)
    public Producto guardar(Producto producto) {
        return repositorio.save(producto);
    }
}
```

### 6. Logging

```java
@Controller
public class ProductoController {
    private static final Logger logger = LoggerFactory.getLogger(ProductoController.class);
    
    @GetMapping("/productos")
    public String lista(Model model) {
        logger.info("Listando productos");
        List<Producto> productos = productoServicio.findAll();
        logger.debug("Encontrados {} productos", productos.size());
        model.addAttribute("productos", productos);
        return "productos/lista";
    }
}
```

## Recursos Adicionales

- [Spring MVC Documentation](https://docs.spring.io/spring-framework/docs/current/reference/html/web.html)
- [Spring Boot Reference](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Pebble Template Engine](https://pebbletemplates.io/)

---

**Última actualización:** Enero 2025  
**Autor:** José Luis González Sánchez

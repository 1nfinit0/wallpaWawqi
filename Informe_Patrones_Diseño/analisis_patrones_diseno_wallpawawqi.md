# Análisis Detallado de Patrones de Diseño

## Proyecto: WallpaWawqi
### Backend - Sistema de Gestión de Restaurante

---

## Tabla de Contenidos

1. [Introducción](#capítulo-1-introducción)
2. [Estructura del Proyecto](#capítulo-2-estructura-del-proyecto)
3. [Patrones de Diseño Identificados](#capítulo-3-patrones-de-diseño-identificados)
4. [Análisis Integrado](#capítulo-4-análisis-integrado)
5. [Evaluación y Recomendaciones](#capítulo-5-evaluación-y-recomendaciones)
6. [Conclusiones](#capítulo-6-conclusiones)
7. [Anexo: Referencias de Patrones](#anexo-referencias-de-patrones)

---

# Capítulo 1: Introducción

El presente informe realiza un análisis detallado de los patrones de diseño implementados en el backend del proyecto WallpaWawqi, un sistema de gestión integral para restaurantes desarrollado en Java.

## Contexto del Proyecto

WallpaWawqi es una aplicación que gestiona la operación de un restaurante, incluyendo:

- Gestión de empleados por rol
- Catálogo de platillos y menú
- Persistencia de datos en formato JSON
- API REST para consultas y registro de platillos

## Objetivo

Identificar, documentar y analizar los patrones de diseño implementados en la lógica del backend, evaluando su efectividad, coherencia arquitectónica y aplicabilidad a los requisitos del sistema.

## Alcance

El análisis se enfoca exclusivamente en la lógica backend, excluyendo la interfaz web (carpeta webAPP). Se examinan:

- Estructura de clases y herencia
- Servicios de persistencia
- Manejo de solicitudes HTTP
- Uso de genéricos y programación funcional

---

# Capítulo 2: Estructura del Proyecto

## Arquitectura General

La arquitectura del backend se organiza en las siguientes capas:

- **Capa de Modelos**: Clases que representan entidades de dominio
- **Capa de Servicios**: Lógica de persistencia y acceso a datos
- **Capa de Presentación**: Servidor HTTP que expone endpoints REST

## Componentes Principales

### Jerarquía de Empleados

```
Empleado (abstract)
  ├── Cocinero
  ├── Mozo
  └── PersonalDeLimpieza
```

### Entidades de Negocio

- **Platillo**: Representa los platos disponibles en el menú
- **Empleado**: Entidad base para todos los tipos de empleados

### Servicios

- **JsonPersistenceService**: Gestiona la persistencia genérica de datos

---

# Capítulo 3: Patrones de Diseño Identificados

## 1. Template Method Pattern

### Descripción

El patrón Template Method define el esqueleto de un algoritmo en una clase base, permitiendo que las subclases sobrescriban pasos específicos sin cambiar la estructura del algoritmo.

### Aplicación en WallpaWawqi

La clase abstracta `Empleado` implementa este patrón al:

- Definir una estructura común para todos los empleados (propiedades básicas)
- Proporcionar métodos plantilla como `registrarAsistencia()`
- Permitir que cada subclase especializada implemente sus propios comportamientos específicos

### Diagrama Conceptual

```
┌──────────────────────────────────────┐
│        Empleado (Abstract)           │
├──────────────────────────────────────┤
│ - id: long                           │
│ - name: String                       │
│ - position: String                   │
│ - email: String                      │
│ - phone: String                      │
├──────────────────────────────────────┤
│ + registrarAsistencia(): void        │
│ + getters/setters                    │
└──────────────┬───────────────────────┘
               │ heredan
      ┌────────┼─────────────┐
      │        │             │
      ▼        ▼             ▼
  ┌────────┐┌────────┐┌──────────────┐
  │Cocinero││ Mozo   ││PersonalLimpz.│
  └────────┘└────────┘└──────────────┘
```

### Código: Clase Base Abstracta

> **Nota**: Ver archivo `src/main/java/com/wallpawawqi/Class/Empleado.java` (líneas 1-35)

### Beneficios

- **Reutilización de código**: Las propiedades comunes se definen una sola vez
- **Cohesión**: Todos los empleados comparten la misma interfaz base
- **Extensibilidad**: Nuevos tipos de empleados se agregan fácilmente
- **Polimorfismo**: Se pueden tratar todos los empleados uniformemente

---

## 2. Strategy Pattern

### Descripción

El patrón Strategy define una familia de algoritmos, encapsula cada uno y los hace intercambiables. Permite que el algoritmo varíe independientemente de los clientes que lo usan.

### Aplicación en WallpaWawqi

Las subclases de `Empleado` representan diferentes **estrategias** de trabajo:

- **Cocinero**: Estrategia de preparación de platillos
- **Mozo**: Estrategia de atención al cliente
- **PersonalDeLimpieza**: Estrategia de mantenimiento

Cada estrategia implementa sus propios métodos específicos que definen cómo ejecuta sus funciones.

### Diagrama Conceptual

```
┌─────────────────────────────────────┐
│         Strategy (Empleado)         │
│      Interfaz: registrarAsistencia()│
└─────────────┬───────────────────────┘
              │
    ┌─────────┼──────────┐
    │         │          │
    ▼         ▼          ▼
┌─────────┐┌─────────┐┌──────────────┐
│ Cooking ││ Service ││Maintenance   │
│Strategy ││Strategy ││Strategy      │
└─────────┘└─────────┘└──────────────┘
```

### Código: Estrategia 1 - Cocinero

> **Nota**: Ver archivo `src/main/java/com/wallpawawqi/Class/Subclases/Cocinero.java` (líneas 1-22)

### Código: Estrategia 2 - Mozo

> **Nota**: Ver archivo `src/main/java/com/wallpawawqi/Class/Subclases/Mozo.java` (líneas 1-26)

### Código: Estrategia 3 - PersonalDeLimpieza

> **Nota**: Ver archivo `src/main/java/com/wallpawawqi/Class/Subclases/PersonalDeLimpieza.java` (líneas 1-19)

### Ventajas

- **Flexibilidad**: Las estrategias pueden cambiar en tiempo de ejecución
- **Modularidad**: Cada estrategia es independiente y auto-contenida
- **Testabilidad**: Las estrategias se pueden probar aisladamente
- **Mantenibilidad**: Los cambios en una estrategia no afectan otras

---

## 3. Service Layer Pattern

### Descripción

El patrón Service Layer encapsula la lógica de negocio y acceso a datos en una capa separada. Proporciona una interfaz clara para operaciones específicas del dominio.

### Aplicación en WallpaWawqi

La clase `JsonPersistenceService<T>` actúa como servicio reutilizable que:

- Maneja la carga de datos desde JSON
- Persiste datos en archivos JSON
- Administra la generación de IDs
- Abstrae la complejidad de I/O de archivos

### Responsabilidades

```
JsonPersistenceService
├── load()    → Carga datos del archivo JSON
├── save()    → Persiste datos en JSON
└── add()     → Agrega nuevo elemento y asigna ID
```

### Código: Clase Servicio

> **Nota**: Ver archivo `src/main/java/com/wallpawawqi/services/JsonPersistenceService.java` (líneas 1-40)

### Beneficios

- **Separación de responsabilidades**: Lógica de datos separada del dominio
- **Reutilización**: El servicio funciona con cualquier tipo de datos
- **Testabilidad**: El servicio puede mockarse fácilmente
- **Mantenibilidad**: Cambios en la persistencia no afectan el dominio

---

## 4. Generic Types Pattern

### Descripción

Los genéricos de Java permiten escribir código parametrizado por tipos, mejorando la reutilización y seguridad de tipos sin sacrificar la flexibilidad.

### Aplicación en WallpaWawqi

`JsonPersistenceService<T>` utiliza un parámetro de tipo genérico `T` para:

- Trabajar con cualquier clase sin duplicar código
- Mantener seguridad de tipos en tiempo de compilación
- Evitar casting explícito innecesario

### Ejemplo de Uso

```java
// Para persistencia de Platillos
JsonPersistenceService<Platillo> servicePlatillos =
    new JsonPersistenceService<>();
List<Platillo> platillos = servicePlatillos.load(
    "platillos.json",
    Platillo[].class
);

// Para persistencia de Empleados
JsonPersistenceService<Empleado> serviceEmpleados =
    new JsonPersistenceService<>();
List<Empleado> empleados = serviceEmpleados.load(
    "empleados.json",
    Empleado[].class
);
```

### Ventajas

- **DRY (Don't Repeat Yourself)**: Una sola implementación para múltiples tipos
- **Type Safety**: El compilador valida los tipos
- **Legibilidad**: El código es más claro y auto-documentado
- **Rendimiento**: Sin sobrecarga de casting dinámico

---

## 5. Functional Programming Pattern

### Descripción

La programación funcional trata las funciones como ciudadanos de primera clase, permitiendo pasar comportamiento como parámetros mediante interfaces funcionales o method references.

### Aplicación en WallpaWawqi

El método `add()` de `JsonPersistenceService` utiliza este patrón:

```java
public long add(
    String filePath,
    List<T> data,
    T nuevo,
    IdSetter<T> idSetter,    // Comportamiento: set ID
    IdGetter<T> idGetter     // Comportamiento: get ID
) throws IOException {
    // Calcula nuevo ID
    long nuevoId = data.stream()
        .mapToLong(idGetter::getId)    // Method reference
        .max()
        .orElse(0) + 1;

    idSetter.setId(nuevo, nuevoId);
    data.add(nuevo);
    save(filePath, data);

    return nuevoId;
}
```

### Interfaces Funcionales Definidas

> **Nota**: Ver archivo `src/main/java/com/wallpawawqi/services/JsonPersistenceService.java` (líneas 35-40)

### Uso en la Práctica

En el controlador HTTP se utiliza así:

```java
long nuevoId = service.add(
    DATA_FILE,
    platillos,
    nuevoPlatillo,
    Platillo::setId,    // Method reference a setter
    Platillo::getId     // Method reference a getter
);
```

### Beneficios

- **Flexibilidad**: El comportamiento se inyecta como parámetro
- **Reutilización**: La lógica de `add()` es agnóstica del tipo
- **Concisión**: Los method references hacen el código más legible
- **Expresividad**: El código describe *qué* hacer, no *cómo*

---

## 6. Dependency Injection (Implícito)

### Descripción

Aunque no es explícito mediante frameworks como Spring, el código utiliza patrones que simulan inyección de dependencias.

### Aplicación

En `WallpaWawqi.main()`:

```java
Gson gson = new Gson();  // Inyección manual
JsonPersistenceService<Platillo> service =
    new JsonPersistenceService<>();  // Inyección manual

server.createContext("/platillos", exchange -> {
    // Uso de dependencias
    List<Platillo> platillos = service.load(...);
    String json = gson.toJson(platillos);
});
```

Este patrón permite:

- Desacoplamiento de dependencias
- Facilidad de testing (reemplazar con mocks)
- Flexibilidad en la configuración

---

# Capítulo 4: Análisis Integrado

## Interacción de Patrones

Los patrones se integran de forma coherente:

1. **Template Method + Strategy**: Define roles y comportamientos
2. **Service Layer**: Abstrae la persistencia
3. **Generics**: Hace el servicio reutilizable
4. **Functional Programming**: Inyecta comportamientos específicos

## Flujo de Datos

```
HTTP Request
    ↓
WallpaWawqi (Main)
    ↓
JsonPersistenceService<T>
    ↓
Gson (JSON Serialization)
    ↓
File System (JSON)
```

## Ejemplo Completo: Agregar un Platillo

> **Nota**: Ver archivo `src/main/java/com/wallpawawqi/WallpaWawqi.java` (líneas 45-65)

Este código demuestra:

- **Generics**: `JsonPersistenceService<Platillo>`
- **Service Layer**: Delegación al servicio
- **Functional Programming**: `Platillo::setId`
- **Dependency Injection**: Instancias inyectadas

---

# Capítulo 5: Evaluación y Recomendaciones

## Fortalezas

1. **Arquitectura Modular**: Separación clara de responsabilidades
2. **Reutilización de Código**: Service genérico evita duplicación
3. **Extensibilidad**: Fácil agregar nuevos tipos de empleados
4. **Buenas Prácticas**: Uso correcto de patrones GOF
5. **Type Safety**: Uso de genéricos y tipos fuerte

## Áreas de Mejora

### 1. Implementación de Métodos Plantilla

**Recomendación**: Los métodos plantilla en `Empleado` tienen solo TODOs.

```java
// Actual
public void registrarAsistencia() {
    // TODO registrar asistencia del empleado
}

// Propuesto
public void registrarAsistencia() {
    System.out.println("Asistencia registrada para: " + name);
    // Persistir en base de datos
}
```

### 2. Persistencia de Empleados

**Recomendación**: Extender `JsonPersistenceService` para persistir empleados.

```java
// Nuevo endpoint HTTP
server.createContext("/empleados", exchange -> {
    if ("GET".equals(exchange.getRequestMethod())) {
        JsonPersistenceService<Empleado> service =
            new JsonPersistenceService<>();
        List<Empleado> empleados = service.load(
            "src/main/resources/empleados.json",
            Empleado[].class
        );
        // Responder con JSON
    }
});
```

### 3. Patrón Data Access Object (DAO)

**Recomendación**: Crear una interfaz DAO para abstraer mejor la persistencia.

```java
public interface Dao<T> {
    List<T> findAll() throws IOException;
    Optional<T> findById(long id) throws IOException;
    void save(T entity) throws IOException;
    void delete(long id) throws IOException;
}

public class JsonDao<T> implements Dao<T> {
    private JsonPersistenceService<T> service;
    private String filePath;

    @Override
    public List<T> findAll() throws IOException {
        return service.load(filePath, getClazz());
    }
}
```

### 4. Manejo de Errores

**Recomendación**: Implementar excepciones personalizadas.

```java
public class DataPersistenceException extends Exception {
    public DataPersistenceException(String msg, Throwable cause) {
        super(msg, cause);
    }
}

public class InvalidEmployeeException extends Exception {
    public InvalidEmployeeException(String msg) {
        super(msg);
    }
}
```

### 5. Validación de Datos

**Recomendación**: Agregar validación en el servicio.

```java
public long add(String filePath, List<T> data, T nuevo,
                IdSetter<T> idSetter, IdGetter<T> idGetter)
        throws IOException, InvalidDataException {
    if (nuevo == null) {
        throw new InvalidDataException("El objeto no puede ser nulo");
    }
    // ... resto del método
}
```

---

# Capítulo 6: Conclusiones

## Resumen de Patrones

El backend de WallpaWawqi implementa correctamente una combinación coherente de patrones de diseño:

| Patrón | Componente | Propósito |
|---|---|---|
| Template Method | Empleado (abstract) | Estructura común |
| Strategy | Cocinero, Mozo, PersonalLimpieza | Comportamientos específicos |
| Service Layer | JsonPersistenceService | Abstracción de datos |
| Generic Types | JsonPersistenceService<T> | Reutilización sin duplicación |
| Functional | IdSetter/IdGetter | Inyección de comportamiento |
| Dependency Injection | Manual (main) | Desacoplamiento |

## Eficacia Arquitectónica

La arquitectura es **efectiva** porque:

- Cumple con el principio de Single Responsibility
- Facilita la extensión sin modificar código existente (Open/Closed)
- Permite sustituir implementaciones (Dependency Inversion)
- Evita duplicación mediante genéricos
- Mantiene el código limpio y comprensible

## Aplicabilidad a Requisitos

Los patrones seleccionados son **apropiados** para:

1. **Gestión de múltiples roles**: Strategy facilita roles diferentes
2. **Persistencia flexible**: Genéricos permiten múltiples tipos
3. **API REST**: Service Layer expone lógica clara
4. **Extensibilidad**: Nuevos tipos se agregan sin refactoring mayor

## Recomendación Final

Se recomienda mantener esta arquitectura de patrones como base, implementando las mejoras sugeridas en el Capítulo 5, particularmente:

- Patrón DAO para persistencia más robusta
- Validación y manejo de excepciones mejorado
- Completar la implementación de métodos plantilla
- Extensión de servicios para persistencia de Empleados

Con estos ajustes, el sistema estará mejor preparado para evolución futura y mantenimiento a largo plazo.

---

# Anexo: Referencias de Patrones

## Patrones de Diseño GOF

- **Creational Patterns**: Abstract Factory, Builder, Factory Method, Prototype, Singleton
- **Structural Patterns**: Adapter, Bridge, Composite, Decorator, Facade, Flyweight, Proxy
- **Behavioral Patterns**: Chain of Responsibility, Command, Interpreter, Iterator, Mediator, Memento, Observer, State, Strategy, Template Method, Visitor

## Libros Recomendados

- Design Patterns: Elements of Reusable Object-Oriented Software (Gang of Four)
- Head First Design Patterns (Freeman & Freeman)
- Effective Java (Joshua Bloch)
- Clean Code (Robert C. Martin)

## Recursos Online

- Refactoring.guru - Comprehensive Design Patterns
- Oracle Java Tutorials - Generics
- Java Functional Interfaces Documentation

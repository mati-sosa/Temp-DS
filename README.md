# DonaTrack — Entrega 1: Arquitectura y Modelado en Objetos

Sistema de gestión y trazabilidad de donaciones desarrollado como Trabajo Práctico Anual Integrador para la materia **Diseño de Sistemas de Información** — UTN FRBA 2026.

---

## Índice

1. [Descripción del sistema](#1-descripción-del-sistema)
2. [Arquitectura multimódulo](#2-arquitectura-multimódulo)
3. [Modelado del dominio — Servicio de Donaciones](#3-modelado-del-dominio--servicio-de-donaciones)
   - [Jerarquía de Bienes](#31-jerarquía-de-bienes)
   - [Jerarquía de Donantes](#32-jerarquía-de-donantes)
   - [Jerarquía de Necesidades](#33-jerarquía-de-necesidades)
   - [Entidades Beneficiarias](#34-entidades-beneficiarias)
   - [Categorías y Subcategorías](#35-categorías-y-subcategorías)
   - [Donación y Segmentación](#36-donación-y-segmentación)
4. [Servicio de Notificaciones](#4-servicio-de-notificaciones)
5. [Patrones de diseño aplicados](#5-patrones-de-diseño-aplicados)
6. [Modelo rico vs. modelo anémico](#6-modelo-rico-vs-modelo-anémico)
7. [Tests y validación del diseño](#7-tests-y-validación-del-diseño)
8. [Decisiones de diseño justificadas](#8-decisiones-de-diseño-justificadas)

---

## 1. Descripción del sistema

DonaTrack es una plataforma orientada a organizar, registrar y monitorear donaciones de bienes materiales desde su recepción en el depósito de UTN Solidaria hasta su entrega a entidades beneficiarias.

La **Entrega 1** cubre el modelado inicial del dominio e implementa los siguientes módulos funcionales:

- Gestión de **personas donantes** (humanas y jurídicas)
- Gestión de **donaciones** y su segmentación interna por subcategoría
- Gestión de **entidades beneficiarias** y sus necesidades (recurrentes y extraordinarias)
- **Importación masiva de donantes** por CSV *(diseñado, pendiente de implementación)*
- **Servicio de notificaciones** (primera iteración: simulación por stdout)

---

## 2. Arquitectura multimódulo

El sistema se construye como un proyecto **Maven multimódulo** con tres subproyectos independientes:

```
2026-tpa-lu-no-grupo-8/          ← pom.xml raíz (parent)
├── common-lib/                   ← utilidades compartidas entre servicios
├── donaciones-service/           ← lógica de dominio principal (Spring Boot)
└── notificaciones-service/       ← servicio de envío de notificaciones (Spring Boot)
```

**Stack tecnológico:** Java 21 · Spring Boot 4.0.5 · Spring Cloud 2025.1.1 · Lombok 1.18.34 · JUnit 5

### Por qué separar en módulos

La separación en módulos independientes refleja desde el inicio la **arquitectura distribuida** requerida por la consigna. Cada módulo:

- Tiene su propio contexto de Spring (`@SpringBootApplication`) y puede desplegarse de forma independiente.
- Depende de `common-lib` para código compartido (clases de utilidad base), evitando duplicación.
- Establece la base para que, en entregas futuras, cada servicio exponga su propia API REST y se integre con los demás mediante comunicación HTTP o colas de mensajes.

Esta decisión aplica el principio de **bajo acoplamiento** desde la etapa de modelado: el `donaciones-service` no conoce la implementación interna del `notificaciones-service`; solo conoce el contrato (la interfaz `Notificador` y el endpoint `/enviar-notificacion`).

---

## 3. Modelado del dominio — Servicio de Donaciones

El servicio de donaciones sigue un **modelo de dominio rico**: las entidades encapsulan tanto datos como comportamiento y reglas de negocio, validando invariantes en sus constructores.

### 3.1 Jerarquía de Bienes

```
Bien  (abstract)
├── BienPerecible
└── BienNoPerecible
```

**`Bien` (clase abstracta)** — [`bienes/Bien.java`](donaciones-service/src/main/java/ar/edu/utn/frba/ddsi/donaciones/models/entities/bienes/Bien.java)

Es la clase base que define los atributos comunes a todo bien donado:

| Atributo | Tipo | Descripción |
|---|---|---|
| `descripcion` | `String` | Descripción del bien (obligatorio) |
| `subcategoria` | `SubcategoriaBien` | Categorización del bien (obligatorio) |
| `foto` | `String` | URL de foto asociada (opcional) |
| `cantidad` | `double` | Cantidad en la unidad correspondiente (> 0) |
| `yaFueDonado` | `Boolean` | Marca si el bien ya fue procesado en una donación |

El método `fueDonado()` marca el bien como ya utilizado, previniendo que el mismo bien físico sea asignado dos veces a necesidades distintas.

**`BienPerecible`** — [`bienes/BienPerecible.java`](donaciones-service/src/main/java/ar/edu/utn/frba/ddsi/donaciones/models/entities/bienes/BienPerecible.java)

Agrega `fechaDeVencimiento: LocalDate` y el comportamiento específico de los bienes perecederos:

```java
public boolean estaVencido() {
    return LocalDate.now().isAfter(fechaDeVencimiento);
}
```

La fecha de vencimiento es obligatoria (se valida en el constructor). Esta subclase permite que el sistema verifique si los alimentos o medicamentos están aptos para entrega antes de asignarlos.

**`BienNoPerecible`** — [`bienes/BienNoPerecible.java`](donaciones-service/src/main/java/ar/edu/utn/frba/ddsi/donaciones/models/entities/bienes/BienNoPerecible.java)

Agrega dos atributos que modelan el estado del bien (nuevo/usado), relevante para categorías como mobiliario y vestimenta:

| Atributo | Tipo | Descripción |
|---|---|---|
| `tieneEstado` | `boolean` | Si la categoría requiere declarar estado |
| `estado` | `boolean` | `true` = nuevo, `false` = usado |

**Justificación de la herencia sobre `Bien`:**

La disyuntiva era modelar un único `Bien` con flags condicionales, o usar herencia. Se eligió herencia porque:

- `BienPerecible` y `BienNoPerecible` tienen **atributos y comportamientos distintos** que no aplican al otro tipo. Un `BienNoPerecible` nunca necesitará `fechaDeVencimiento`, y un `BienPerecible` nunca tendrá `tieneEstado`.
- Evita lógica condicional dispersa (`if (esPerecedero) { ... }`) que viola el principio Open/Closed.
- Permite agregar nuevos tipos de bienes en el futuro sin modificar la clase base.

---

### 3.2 Jerarquía de Donantes

```
Donante  (abstract)
├── DonanteHumano
└── DonanteJuridico
```

**`Donante` (clase abstracta)** — [`donantes/Donante.java`](donaciones-service/src/main/java/ar/edu/utn/frba/ddsi/donaciones/models/entities/donantes/Donante.java)

Define el comportamiento común: toda persona donante tiene un `nombre` y una lista de `mediosDeContacto`. La clase base garantiza que la lista nunca esté vacía y provee métodos para agregar y eliminar medios de contacto con validaciones:

```java
public Donante(String nombre, List<MedioDeContacto> mediosDeContacto) {
    if (mediosDeContacto == null || mediosDeContacto.isEmpty()) {
        throw new IllegalArgumentException("¡El donante debe tener al menos un medio de contacto!");
    }
    // ...
}
```

**`DonanteHumano`** — [`donantes/humano/DonanteHumano.java`](donaciones-service/src/main/java/ar/edu/utn/frba/ddsi/donaciones/models/entities/donantes/humano/DonanteHumano.java)

Representa a una persona física. Atributos adicionales:

| Atributo | Tipo | Descripción |
|---|---|---|
| `edad` | `int` | Debe ser >= 18 |
| `numeroDocumento` | `String` | Número de documento |
| `tipoDocumento` | `TipoDocumento` | `DNI`, `CUIT`, `PASAPORTE`, etc. |
| `genero` | `Genero` | `MASCULINO`, `FEMENINO`, `NO_BINARIO`, `PREFIERO_NO_DECIR` |
| `direccion` | `Direccion` | Dirección física |
| `medioPredeterminado` | `MedioDeContacto` | Canal preferido para notificaciones |

Reglas de negocio específicas de la persona humana validadas en el constructor:
- Debe ser mayor de edad (`edad >= 18`).
- Debe tener al menos un contacto de tipo `EMAIL`.
- El `medioPredeterminado` debe existir en la lista de medios.

**Override de `eliminarMedio()`:** `DonanteHumano` sobreescribe el método de la clase base para agregar protecciones específicas:

```java
@Override
public void eliminarMedio(MedioDeContacto medioDeContactoEliminado) {
    if (this.medioPredeterminado == medioDeContactoEliminado) {
        throw new IllegalArgumentException("¡No se puede eliminar el medio de contacto predeterminado!");
    }
    long cantidadEmails = this.mediosDeContacto.stream()
            .filter(m -> m.getTipoMedioContacto() == TipoMedioContacto.EMAIL)
            .count();
    if (medioDeContactoEliminado.getTipoMedioContacto() == TipoMedioContacto.EMAIL && cantidadEmails == 1) {
        throw new IllegalArgumentException("¡No es posible eliminar el único email del donante!");
    }
    super.eliminarMedio(medioDeContactoEliminado);
}
```

Este override es un ejemplo directo del principio de **especialización**: la subclase extiende el comportamiento del padre sin romper su contrato (al final llama a `super.eliminarMedio()`).

**`DonanteJuridico`** — [`donantes/juridico/DonanteJuridico.java`](donaciones-service/src/main/java/ar/edu/utn/frba/ddsi/donaciones/models/entities/donantes/juridico/DonanteJuridico.java)

Representa a una organización. Atributos adicionales:

| Atributo | Tipo | Descripción |
|---|---|---|
| `tipo` | `TipoPersonaJuridica` | `GUBERNAMENTAL`, `ONG`, `EMPRESA`, `INSTITUCION` |
| `representantes` | `List<Representante>` | Personas físicas que operan en su nombre (mínimo 1) |
| `rubro` | `Rubro` | Sector de actividad |

La persona jurídica opera a través de representantes: siempre debe tener al menos uno, y `eliminarRepresentante()` lo verifica:

```java
public void eliminarRepresentante(Representante representanteEliminado) {
    if (this.representantes.size() == 1) {
        throw new IllegalArgumentException("¡El donante jurídico debe tener al menos un representante!");
    }
    this.representantes.remove(representanteEliminado);
}
```

**Comparativa de las subclases:**

| Aspecto | `DonanteHumano` | `DonanteJuridico` |
|---|---|---|
| Identificación | DNI/Pasaporte + dirección | Razón social + rubro |
| Representación | Sí misma | A través de `Representante` |
| Email obligatorio | Sí (al menos 1 directo) | A través de representantes |
| Medio predeterminado | Sí | No aplica |
| Validación de edad | Sí (>= 18) | No aplica |

---

### 3.3 Jerarquía de Necesidades

```
Necesidad  (abstract)
├── NecesidadRecurrente
└── NecesidadExtraordinaria
```

Esta jerarquía es el ejemplo más claro del patrón **Template Method** en el proyecto.

**`Necesidad` (clase abstracta)** — [`necesidades/Necesidad.java`](donaciones-service/src/main/java/ar/edu/utn/frba/ddsi/donaciones/models/entities/necesidades/Necesidad.java)

Define el esqueleto del algoritmo de recepción de donaciones. El método `recibirDonacion()` está completamente implementado en la clase base y es el mismo para ambos subtipos:

```java
public void recibirDonacion(Bien bienDonado) {
    if (this.subcategoria != bienDonado.getSubcategoria()) {
        throw new IllegalArgumentException("¡El bien no pertenece a la subcategoría de la necesidad!");
    }
    if (bienDonado.getYaFueDonado()) {
        throw new IllegalArgumentException("¡El bien ya fue donado previamente!");
    }
    sumarBienes(bienDonado.getCantidad());
}

private void sumarBienes(Double cantidadDonada) {
    this.cantidadCubierta += cantidadDonada;
}

public abstract Boolean estaSatisfecha();
```

El paso que varía (el criterio de satisfacción) se delega a las subclases mediante `estaSatisfecha()` abstracto. La lógica de validación y acumulación es heredada sin repetición.

**`NecesidadExtraordinaria`** — [`necesidades/NecesidadExtraordinaria.java`](donaciones-service/src/main/java/ar/edu/utn/frba/ddsi/donaciones/models/entities/necesidades/NecesidadExtraordinaria.java)

Modela una necesidad puntual (ej.: una inundación destruyó 30 sillas). Se satisface cuando la cantidad acumulada alcanza o supera el total requerido:

```java
@Override
public Boolean estaSatisfecha() {
    return getCantidadCubierta() >= getCantidadRequerida();
}
```

Acepta donaciones parciales: si se necesitan 30 sillas, se puede recibir 2 de una persona y 20 de otra. La necesidad se satisface cuando `cantidadCubierta >= 30`.

**`NecesidadRecurrente`** — [`necesidades/NecesidadRecurrente.java`](donaciones-service/src/main/java/ar/edu/utn/frba/ddsi/donaciones/models/entities/necesidades/NecesidadRecurrente.java)

Modela un consumo habitual periódico (ej.: 100 paquetes de fideos por semana). Se satisface cuando la cantidad cubierta alcanza la cantidad objetivo del período, no la total acumulada:

```java
@Override
public Boolean estaSatisfecha() {
    return getCantidadCubierta() >= cantidadPorPeriodo;
}
```

Atributos adicionales: `cantidadPorPeriodo` y `periodo` (con `TipoPeriodo`: `DIA`, `SEMANA`, `MES`, `AÑO`).

**Diferencia clave entre las dos subclases:**

| Criterio | `NecesidadExtraordinaria` | `NecesidadRecurrente` |
|---|---|---|
| Naturaleza | Evento puntual | Consumo periódico |
| Se satisface cuando... | `cubierta >= requerida` | `cubierta >= cantidadPorPeriodo` |
| Ejemplo | 30 sillas tras inundación | 100 paquetes/semana |

---

### 3.4 Entidades Beneficiarias

**`EntidadBeneficiaria`** — [`entidades_beneficiarias/EntidadBeneficiaria.java`](donaciones-service/src/main/java/ar/edu/utn/frba/ddsi/donaciones/models/entities/entidades_beneficiarias/EntidadBeneficiaria.java)

Representa organizaciones que reciben donaciones (comedores, escuelas rurales, hogares, etc.). Usa **composición** como mecanismo principal:

```
EntidadBeneficiaria
  ├── TipoEntidadBeneficiaria   (tipo + descripción de la entidad)
  ├── Direccion                  (calle, ciudad, provincia, código postal)
  ├── MedioDeContacto            (DEBE ser de tipo TELEFONO)
  ├── List<Representante>        (mínimo 1 requerido)
  └── List<Necesidad>            (recurrentes y/o extraordinarias)
```

El método `registrarNecesidad()` permite que las entidades declaren sus necesidades materiales en cualquier momento, sin duplicados.

**Nota sobre `TipoEntidadBeneficiaria`:** Es una clase con `tipoEntidad` y `descripcion`, no un enum. Esta decisión permite que los administradores del sistema definan nuevos tipos de entidades sin modificar el código (ver sección de [decisiones de diseño](#8-decisiones-de-diseño-justificadas)).

---

### 3.5 Categorías y Subcategorías

```
CategoriaBien  (ej.: "Alimentos")
  └── SubcategoriaBien  (ej.: "Arroz", KILOGRAMOS, no perecedero)
  └── SubcategoriaBien  (ej.: "Frutas", KILOGRAMOS, perecedero)
```

**`SubcategoriaBien`** es la unidad mínima de segmentación del sistema. Sus atributos:

| Atributo | Tipo | Descripción |
|---|---|---|
| `descripcion` | `String` | Nombre de la subcategoría |
| `esPerecedero` | `Boolean` | Determina qué subtipo de `Bien` aplica |
| `categoriaBien` | `CategoriaBien` | Categoría padre (referencia bidireccional) |
| `unidadDeMedida` | `UnidadDeMedida` | `KILOGRAMOS`, `LITROS`, `UNIDADES` |

La subcategoría vincula donaciones con necesidades: `Necesidad.recibirDonacion()` valida que el `Bien` tenga exactamente la misma `SubcategoriaBien` que la necesidad. Esto garantiza que nadie entregue fideos a una necesidad de sillas.

---

### 3.6 Donación y Segmentación

**`Donacion`** — [`donaciones/Donacion.java`](donaciones-service/src/main/java/ar/edu/utn/frba/ddsi/donaciones/models/entities/donaciones/Donacion.java)

Una donación contiene una descripción general, una lista de bienes (`List<Bien>`) y la referencia al donante. Provee el método `cantidadDeUnidadesTotales()` que suma las cantidades de todos los bienes vía stream:

```java
public Double cantidadDeUnidadesTotales() {
    return bienes.stream().mapToDouble(Bien::getCantidad).sum();
}
```

**Proceso de segmentación:**

Cuando un donante trae múltiples tipos de bienes en una sola carga, el sistema genera donaciones independientes agrupadas por `SubcategoriaBien`. Por ejemplo, si alguien dona 100 paquetes de fideos y 50 tetras de tomate, el sistema crea dos donaciones separadas (una por subcategoría), cada una asignable de forma independiente a las necesidades de las entidades beneficiarias.

Esta segmentación asegura coherencia en el proceso de matchmaking entre donaciones y necesidades, ya que una `Necesidad` solo puede recibir bienes de su subcategoría específica.

> **Nota sobre `EstadoDonacion`:** El enum `EstadoDonacion` existe en el código pero está comentado, dado que la trazabilidad de estados (`EN_DEPOSITO`, `ASIGNACION_REALIZADA`, `EN_TRASLADO`, etc.) corresponde al alcance de la **Entrega 2**.

---

## 4. Servicio de Notificaciones

El `notificaciones-service` implementa la primera iteración del envío de notificaciones. En esta entrega, **simula** la integración con servicios externos imprimiendo por stdout.

### Componentes

```
Notificador  (interface)
├── EmailNotificador    (@Service)
├── SmsNotificador
└── WhatsappNotificador
```

**`Notificador`** — [`notificadores/Notificador.java`](notificaciones-service/src/main/java/ar/edu/utn/frba/ddsi/notificaciones/services/notificadores/Notificador.java)

```java
public interface Notificador {
    void enviar(String destino, String mensaje);
}
```

Interfaz con un único método que define el contrato de envío. Cada implementación concreta encapsula la lógica del canal correspondiente.

**`NotificacionService`** — [`services/NotificacionService.java`](notificaciones-service/src/main/java/ar/edu/utn/frba/ddsi/notificaciones/services/NotificacionService.java)

Orquesta la selección del notificador según el `TipoMedioContacto` del request y valida el formato de destino:

```java
switch (tipo) {
    case EMAIL:    notificador = new EmailNotificador(); break;
    case SMS:      notificador = new SmsNotificador(); break;
    case WHATSAPP: notificador = new WhatsappNotificador(); break;
}
validarDestino(tipo, destino);
notificador.enviar(destino, mensaje);
```

Las validaciones de formato:
- **Email:** regex `^[A-Za-z0-9+_.-]+@(.+)$`
- **SMS / WhatsApp:** regex `^\+?[0-9]{8,15}$`

**`NotificacionController`** expone el endpoint `POST /enviar-notificacion` que recibe un `NotificacionRequest` (con `medioDeContacto` y `mensaje`) y delega al servicio.

---

## 5. Patrones de diseño aplicados

### Template Method — `Necesidad`

El patrón está implementado en la jerarquía de necesidades:

- **Clase abstracta (`Necesidad`):** define el algoritmo completo en `recibirDonacion()` (validar subcategoría → validar que no fue donado → acumular cantidad). El paso variable (`estaSatisfecha()`) es abstracto.
- **Subclases:** solo implementan `estaSatisfecha()` con su criterio particular.

Esto evita duplicar la lógica de validación y acumulación en cada subtipo.

### Strategy — `Notificador`

El patrón está implementado en el servicio de notificaciones:

- **Interfaz (`Notificador`):** define el contrato `enviar(destino, mensaje)`.
- **Estrategias concretas:** `EmailNotificador`, `SmsNotificador`, `WhatsappNotificador`.
- **Contexto (`NotificacionService`):** selecciona la estrategia en tiempo de ejecución según el tipo de medio de contacto.

Agregar un nuevo canal de notificación (ej.: push notification) no requiere modificar el `NotificacionService` ni las estrategias existentes: solo se implementa `Notificador` y se agrega el caso al switch.

### Polimorfismo por herencia

- **`Bien`:** el sistema puede tratar uniformemente a `BienPerecible` y `BienNoPerecible` como `Bien`. `Necesidad.recibirDonacion()` recibe un `Bien` sin necesidad de conocer si es perecedero.
- **`Donante`:** las operaciones de gestión de medios de contacto funcionan sobre el tipo abstracto `Donante`.
- **`Necesidad`:** `estaSatisfecha()` se llama polimórficamente sin conocer el subtipo concreto.

### Value Objects

Clases simples e inmutables que representan conceptos del dominio sin identidad propia:

| Clase | Descripción |
|---|---|
| `MedioDeContacto` | Canal de contacto (tipo + dirección) |
| `Direccion` | Dirección física completa |
| `Representante` | Persona que actúa en nombre de otra |
| `Rubro` | Sector de actividad de una organización |
| `Periodo` | Período temporal con tipo y cantidad |

---

## 6. Modelo rico vs. modelo anémico

La cátedra plantea como pregunta de discusión: *¿Un modelo de dominio rico es una inversión necesaria para capturar la complejidad del negocio o una sobreingeniería innecesaria frente a un modelo anémico más simple?*

En este proyecto se optó por un **modelo rico**, y se evidencia en:

**Validaciones en constructores (fail-fast):**

Cada entidad valida sus invariantes al momento de crearse. Si los datos son inválidos, la excepción se lanza inmediatamente, antes de que el objeto entre en un estado inconsistente. Un `DonanteHumano` sin email o con edad menor a 18 nunca puede ser instanciado:

```java
boolean tieneEmail = mediosDeContacto.stream()
        .anyMatch(m -> m.getTipoMedioContacto() == TipoMedioContacto.EMAIL);
if (!tieneEmail) {
    throw new IllegalArgumentException("¡El donante debe tener al menos un email!");
}
if (edad < 18) {
    throw new IllegalArgumentException("¡El donante debe ser mayor de edad!");
}
```

**Comportamiento encapsulado en las entidades:**

- `Bien.fueDonado()` protege contra doble asignación de un mismo bien.
- `Necesidad.recibirDonacion()` encapsula todo el proceso de matching y acumulación.
- `DonanteHumano.eliminarMedio()` protege el medio predeterminado y el último email.
- `DonanteJuridico.eliminarRepresentante()` garantiza al menos un representante activo.

Un modelo anémico hubiera delegado toda esta lógica a servicios externos, dispersando las reglas de negocio y dificultando su localización y testeo.

---

## 7. Tests y validación del diseño

Los tests están organizados por entidad en `donaciones-service` y por funcionalidad en `notificaciones-service`.

### Tests del Servicio de Donaciones

| Clase de test | Qué cubre |
|---|---|
| `DonantesTests` | Creación de donantes humanos y jurídicos, medios de contacto, representantes |
| `DonacionesTest` | Creación de bienes, subcategorías, donaciones; recepción en necesidades |
| `EntidadesBeneficiariasTest` | Creación de entidades beneficiarias, registro de necesidades |

Los tests del servicio de donaciones son de tipo **exploratorio**: instancian objetos y verifican el comportamiento imprimiendo resultados. Sirven como tests de humo para validar que el modelo puede construirse correctamente.

### Tests del Servicio de Notificaciones

Los tests de notificaciones usan **assertions de JUnit 5** explícitas:

```
NotificacionesTest
├── deberiaEnviarEmailCorrectamente()      → assertDoesNotThrow (email válido)
├── deberiaFallarSiEmailEsInvalido()       → assertThrows RuntimeException (sin @)
└── deberiaFallarSiTelefonoEsInvalido()    → assertThrows RuntimeException (< 8 dígitos)
```

- `assertDoesNotThrow`: valida que el camino feliz no lanza excepciones.
- `assertThrows`: valida que las validaciones de formato rechazan entradas inválidas.

`NotificacionesServiceApplicationTests` verifica que el contexto de Spring carga correctamente (`contextLoads()`).

### Cómo ejecutar los tests

```bash
mvn test
```

---

## 8. Decisiones de diseño justificadas

### `TipoEntidadBeneficiaria` es clase, no enum

Se eligió clase sobre enum porque los tipos de entidades beneficiarias son datos configurables del negocio, no constantes del sistema. Un comedor, una escuela rural o un hogar de niños son conceptos que pueden crecer o cambiar sin necesidad de modificar y recompilar el código. Un enum hubiera acoplado la taxonomía de entidades al código fuente.

### `SubcategoriaBien` tiene referencia a `CategoriaBien`

La referencia bidireccional `SubcategoriaBien` → `CategoriaBien` permite navegar de una subcategoría a su categoría padre sin búsquedas adicionales. Esto facilita la futura implementación de filtros por categoría en la UI (ej.: "mostrar todas las donaciones de Alimentos") y el algoritmo de compatibilidad semántica planificado para la Entrega 2.

### `Notificador` es interfaz, no clase abstracta

Se eligió interfaz porque los notificadores no comparten estado ni lógica de implementación: en esta iteración, cada uno únicamente imprime por stdout (simulación). Una clase abstracta hubiera sido la opción correcta si hubiera comportamiento común a reutilizar, pero en esta entrega aportaría complejidad sin beneficio. En la Entrega 2, cuando se integre con servicios reales (SendGrid, Twilio, etc.), cada implementación tendrá su propio cliente y configuración, reforzando que interfaz fue la decisión correcta.

### `EstadoDonacion` está comentado

El enum `EstadoDonacion` con sus valores (`EN_DEPOSITO`, `ASIGNACION_REALIZADA`, `EN_TRASLADO`, etc.) se diseñó pero se dejó comentado porque la **máquina de estados de las donaciones** corresponde al alcance de la Entrega 2, que incluye trazabilidad completa e integración con el Servicio de Logística. Dejarlo comentado en lugar de eliminarlo documenta la intención de diseño para la próxima iteración.

### Validación del medio predeterminado extraída a método privado

En `DonanteHumano`, la lógica `validarMedioPredeterminado()` se extrajo a un método privado porque es invocada tanto en el constructor como en `cambiarMedioPredeterminado()`. Esta extracción evita duplicación y centraliza la regla en un único lugar (principio DRY).

### Copia defensiva de listas en constructores

Tanto `Donante` como `DonanteJuridico` realizan `new ArrayList<>(lista)` al recibir listas en el constructor:

```java
this.mediosDeContacto = new ArrayList<>(mediosDeContacto);
this.representantes = new ArrayList<>(representantes);
```

Esto evita que el código externo pueda modificar el estado interno de las entidades a través de la referencia original, protegiendo la encapsulación y garantizando que las invariantes validadas en el constructor no puedan ser violadas posteriormente.

---

## 9. Preguntas frecuentes de defensa oral

### P1: ¿Por qué `Necesidad` es una clase abstracta y no una interfaz?

Porque `Necesidad` tiene **comportamiento concreto compartido** que todas las subclases deben heredar sin modificar: la validación de subcategoría, la verificación de `yaFueDonado` y la acumulación en `cantidadCubierta` dentro de `recibirDonacion()`. Una interfaz solo puede definir el contrato (`estaSatisfecha()` abstracto), pero no implementar esa lógica común. Si se hubiera usado interfaz, cada subclase tendría que repetir la misma validación y acumulación, violando DRY. La clase abstracta es la herramienta correcta cuando hay algoritmo compartido con pasos variables: exactamente el patrón Template Method.

---

### P2: ¿Qué patrón de diseño ven en la jerarquía de `Necesidad` y por qué lo eligieron?

Template Method. La clase base define el esqueleto completo del algoritmo en `recibirDonacion()`: validar subcategoría → validar que no fue donado → acumular cantidad. El único paso que varía entre subclases es el criterio de satisfacción, delegado al método abstracto `estaSatisfecha()`. Las subclases solo implementan ese paso sin tocar el resto. Esto garantiza que ninguna subclase pueda saltarse las validaciones comunes, porque no las controla: las hereda.

---

### P3: ¿Por qué eligieron herencia en la jerarquía de `Bien` y no composición?

La disyuntiva era: un único `Bien` con un flag `esPerecedero` y lógica condicional, o herencia. Se eligió herencia porque `BienPerecible` y `BienNoPerecible` tienen **atributos estructuralmente distintos** que no aplican al otro tipo: `fechaDeVencimiento` y `estaVencido()` solo tienen sentido en bienes perecederos; `tieneEstado` y `estado` solo en no perecederos. Si se hubiera usado composición o un único `Bien`, el objeto podría existir en un estado inconsistente (un alimento sin fecha de vencimiento, o un mueble con fecha de vencimiento). La herencia hace esas diferencias imposibles de violar en tiempo de compilación.

---

### P4: ¿Qué problema resuelve el override de `eliminarMedio()` en `DonanteHumano`?

`Donante` ya valida que el medio exista en la lista antes de eliminarlo. Pero `DonanteHumano` tiene dos invariantes adicionales que la clase base no puede conocer: que el medio predeterminado nunca puede eliminarse, y que el único email tampoco. Sin el override, un cliente podría eliminar el medio predeterminado de una persona humana y dejar al sistema sin saber a dónde enviarle notificaciones, o eliminar el último email violando el requisito de la consigna. El override agrega esas verificaciones y luego llama a `super.eliminarMedio()`, reutilizando la validación base. Es especialización sin duplicación.

---

### P5: ¿Un modelo de dominio rico no es sobreingeniería para esta etapa del proyecto?

No, porque la complejidad de negocio ya existe en la Entrega 1. Las reglas como "un donante humano debe tener al menos un email", "el medio predeterminado debe estar en la lista", o "un bien no puede cubrir dos necesidades distintas" son invariantes del dominio, no detalles de implementación. Si se las delega a un servicio externo (modelo anémico), esa lógica se dispersa y puede ser ignorada o repetida inconsistentemente. Al encapsularla en los constructores y métodos de las entidades, el sistema es incorrecto en cualquier estado inválido: es imposible instanciar un `DonanteHumano` sin email. Eso no es sobreingeniería, es correctitud por diseño.

---

### P6: ¿Por qué los dos servicios (`donaciones` y `notificaciones`) son módulos Maven separados y no paquetes dentro de un mismo proyecto?

Porque la consigna plantea una **arquitectura distribuida** desde el inicio: cada servicio debe poder desplegarse y evolucionar de forma independiente. Separarlos como módulos Maven distintos, cada uno con su propio `@SpringBootApplication`, garantiza que no haya dependencias de compilación cruzadas entre la lógica de donaciones y la de notificaciones. Si fueran paquetes del mismo proyecto, un cambio en el servicio de notificaciones podría romper la compilación del servicio de donaciones aunque fueran lógicamente independientes. La separación en módulos también es el primer paso hacia el despliegue en contenedores independientes, que se abordará en entregas futuras.

---

### P7: ¿Cómo saben, al recibir un `Bien` en `recibirDonacion()`, que la subcategoría coincide? ¿No sería más flexible comparar por nombre?

Se compara por **referencia de objeto** (`this.subcategoria != bienDonado.getSubcategoria()`), no por nombre. Esto es intencional: dos subcategorías distintas podrían llamarse igual en categorías distintas (ej.: "Grande" en Ropa y "Grande" en Muebles). Comparar por nombre crearía falsos positivos. Al comparar por referencia, se garantiza que es exactamente la misma instancia de `SubcategoriaBien`, eliminando toda ambigüedad. La contracara es que el sistema que crea la necesidad y el que crea el bien deben compartir la misma instancia del catálogo de subcategorías, lo cual es un requisito del diseño.

---

### P8: `NecesidadRecurrente` satisface su condición comparando contra `cantidadPorPeriodo`, no contra `cantidadRequerida`. ¿Para qué existe entonces `cantidadRequerida`?

`cantidadRequerida` es el total histórico acumulado que la entidad necesita a lo largo de toda la vigencia de la necesidad (ej.: en un año necesita 5.200 paquetes). `cantidadPorPeriodo` es el objetivo de cada ciclo (ej.: 100 por semana). `estaSatisfecha()` evalúa si se cubrió el período actual, no el total. La `cantidadRequerida` sirve como referencia de planificación a largo plazo y como dato para los algoritmos de asignación de la Entrega 2, que pueden priorizar entidades cuyo total histórico está muy lejos de cubrirse. Ambos datos son necesarios con semánticas distintas.

---

### P9: ¿Por qué `NotificacionService` instancia los notificadores con `new` dentro del `switch` en lugar de inyectarlos con Spring?

Es una limitación reconocida del diseño actual. Instanciar con `new` dentro del servicio acopla `NotificacionService` a las implementaciones concretas y dificulta el testeo unitario (no se puede mockear fácilmente). La alternativa correcta sería inyectar un `Map<TipoMedioContacto, Notificador>` vía Spring y resolver la implementación por clave, eliminando el `switch` por completo. Se decidió mantener la versión simple en esta entrega porque los notificadores son simulaciones sin estado ni dependencias propias, y la integración real con SendGrid o Twilio (Entrega 2) requerirá refactorizar este punto de todas formas. Es una deuda técnica consciente y acotada.

---

### P10: Si mañana el negocio pide agregar un nuevo tipo de necesidad (ej.: `NecesidadEstacional`), ¿qué habría que modificar?

Solo habría que crear una nueva clase que extienda `Necesidad` e implemente `estaSatisfecha()` con su criterio propio. No se modifica `Necesidad`, ni `EntidadBeneficiaria`, ni ningún otro componente existente. Esto es el principio **Open/Closed** en acción: el sistema está abierto a la extensión (nuevos tipos de necesidad) y cerrado a la modificación (el comportamiento existente no cambia). El Template Method de `Necesidad` es el mecanismo que lo hace posible: el nuevo tipo hereda toda la lógica de validación y acumulación gratuitamente, y solo define su criterio de satisfacción.

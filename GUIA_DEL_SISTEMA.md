# DonaTrack — Guía del Sistema

> Este documento explica **cómo funciona el sistema** desde el punto de vista de sus actores y flujos de trabajo, con ejemplos concretos extraídos del dominio. Está pensado como mapa de lectura previo al [README técnico](README.md).

---

## ¿Qué hace DonaTrack?

DonaTrack conecta a **personas que quieren donar bienes materiales** con **organizaciones que los necesitan**. El sistema registra cada donación desde el momento en que llega al depósito hasta que es entregada, garantizando trazabilidad y transparencia.

Los tres grandes actores del sistema son:

| Actor | Rol |
|---|---|
| **Persona donante** | Lleva bienes al depósito o se registra para donar |
| **Entidad beneficiaria** | Organización que declara qué necesita y recibe donaciones |
| **Administrador/a** | Opera el sistema: registra donaciones, asigna destinos, gestiona el depósito |

---

## Mapa general del sistema

```
  PERSONA DONANTE                ADMINISTRADOR/A              ENTIDAD BENEFICIARIA
       │                               │                              │
       │  Se registra o               │                              │  Se registra
       │  ya tiene usuario            │                              │  y declara necesidades
       ▼                               ▼                              ▼
  ┌─────────────┐          ┌─────────────────────┐         ┌──────────────────────┐
  │  Donante    │ ──────►  │  Donación recibida  │         │  Necesidad declarada │
  │  (humano o  │          │  en el depósito     │         │  (recurrente o       │
  │   jurídico) │          │                     │         │   extraordinaria)    │
  └─────────────┘          └─────────┬───────────┘         └──────────┬───────────┘
                                     │                                │
                              Segmentación                            │
                              por subcategoría                        │
                                     │                                │
                                     ▼                                │
                          ┌──────────────────────┐                   │
                          │  Donación por        │◄──────────────────┘
                          │  subcategoría        │   Matchmaking (Entrega 2)
                          │  (unidad mínima)     │
                          └──────────────────────┘
                                     │
                              [Entrega 2+]
                                     ▼
                          ┌──────────────────────┐
                          │  Entrega a la        │
                          │  entidad beneficiaria│
                          └──────────────────────┘
```

---

## Flujo 1: Registrar una persona donante

Cuando alguien llega al depósito por primera vez, el administrador la registra en el sistema. El proceso varía según si es persona humana o jurídica.

### Caso A: Persona humana

**Ejemplo:** Ana Pérez, 32 años, quiere donar ropa de abrigo.

El administrador ingresa:

```
Nombre:           Ana Pérez
Edad:             32
Documento:        DNI 12345678
Género:           Femenino
Dirección:        Av. Corrientes 1234, CABA
Medios contacto:  ana@mail.com (EMAIL)          ← obligatorio
                  +54 11 5555-5555 (WHATSAPP)   ← opcional
Medio predeterminado: ana@mail.com              ← recibe notificaciones aquí
```

**Reglas que aplica el sistema:**
- El EMAIL es obligatorio: si Ana no tiene email, el sistema rechaza el registro.
- Debe ser mayor de edad: si tuviera 17 años, el sistema lanza un error.
- El medio predeterminado debe estar en la lista: no puede indicar un WhatsApp como predeterminado si no lo cargó antes.

### Caso B: Persona jurídica

**Ejemplo:** Arcos Plateados S.A. quiere donar muebles de su oficina.

```
Razón social:     Arcos Plateados S.A.
Tipo:             Empresa
Rubro:            Tecnología
Medios contacto:  contacto@empresa.com (EMAIL)
Representantes:   María González (opera en nombre de la empresa)
```

**Reglas que aplica el sistema:**
- Debe tener al menos un representante: es la persona física que opera en nombre de la empresa.
- El representante no puede eliminarse si es el único que queda.

---

## Flujo 2: Registrar una donación y entender la segmentación

Una vez registrada la persona donante, el administrador carga los bienes que trae. Este es el proceso más importante del sistema porque introduce la **segmentación automática**.

### ¿Qué es la segmentación?

Cuando alguien dona varios tipos de bienes a la vez, el sistema **no los trata como un bloque único**. Los divide en donaciones independientes según su subcategoría. Esto permite asignar cada tipo de bien a la entidad que más lo necesita.

### Caso A: Donación de bienes no perecederos (muebles usados)

**Ejemplo:** Arcos Plateados dona el mobiliario de su oficina.

```
Descripción general: "Mudanza de oficina Arcos Plateados"

Bienes:
  ┌────────────┬────────────────┬──────────┬──────────┬────────┐
  │ Descripción│ Subcategoría   │ Cantidad │ Unidad   │ Estado │
  ├────────────┼────────────────┼──────────┼──────────┼────────┤
  │ Sillas de  │ Sillas         │    6     │ unidades │ usado  │
  │ escritorio │ (Mobiliario)   │          │          │        │
  ├────────────┼────────────────┼──────────┼──────────┼────────┤
  │ Mesa       │ Mesas          │    1     │ unidades │ usado  │
  │ rectangular│ (Mobiliario)   │          │          │        │
  └────────────┴────────────────┴──────────┴──────────┴────────┘
```

El sistema genera **dos donaciones independientes**:
- Donación A: 6 sillas usadas (subcategoría: Sillas)
- Donación B: 1 mesa rectangular usada (subcategoría: Mesas)

Cada una puede asignarse a una entidad beneficiaria distinta según sus necesidades.

> Los bienes de mobiliario son **no perecederos** con estado relevante: el sistema registra si son nuevos o usados, dato que las entidades beneficiarias pueden considerar al declarar sus necesidades.

### Caso B: Donación de bienes perecederos (alimentos)

**Ejemplo:** Una planta industrial de pastas dona alimentos.

```
Descripción general: "Donación planta industrial de pastas"

Bienes:
  ┌──────────────────┬──────────────────┬──────────┬──────────┬──────────────┐
  │ Descripción      │ Subcategoría     │ Cantidad │ Unidad   │ Vencimiento  │
  ├──────────────────┼──────────────────┼──────────┼──────────┼──────────────┤
  │ Fideos secos     │ Fideos           │   100    │ unidades │ 01/01/2027   │
  │ (paquetes 500g)  │ (Alimentos)      │          │          │              │
  ├──────────────────┼──────────────────┼──────────┼──────────┼──────────────┤
  │ Tomate triturado │ Conservas        │    50    │ unidades │ 01/01/2027   │
  │ (tetra-pack)     │ (Alimentos)      │          │          │              │
  └──────────────────┴──────────────────┴──────────┴──────────┴──────────────┘
```

El sistema genera **dos donaciones independientes**:
- Donación A: 100 paquetes de fideos (vence 01/01/2027)
- Donación B: 50 tetras de tomate (vence 01/01/2027)

> Los bienes perecederos **siempre requieren fecha de vencimiento**. Si dos bienes de la misma subcategoría tienen fechas distintas, el sistema genera donaciones separadas para cada fecha.

**¿Qué pasa si un bien está vencido?**

El sistema puede verificar en cualquier momento si un `BienPerecible` está vencido:

```
"¿Están aptos los fideos hoy (27/04/2026)?"
→ Vencen el 01/01/2027
→ LocalDate.now().isAfter(01/01/2027) = false
→ No están vencidos. Aptos para entrega.
```

Si el administrador detecta que un bien venció antes de ser entregado, puede marcar la donación como "Vencida" (funcionalidad completa en Entrega 2).

---

## Flujo 3: Registrar una entidad beneficiaria y sus necesidades

Las entidades beneficiarias se registran una vez y luego declaran sus necesidades de forma continua a medida que surgen.

### Registro de la entidad

**Ejemplo:** Escuela Rural N°10, ubicada en la provincia de Buenos Aires.

```
Razón social:    Escuela Rural N°10
Dirección:       Ruta 6 km 45, General Paz, Buenos Aires
Teléfono:        +54 2225 123456
Representante:   Prof. Carlos Ruiz — cruiz@escuela10.edu.ar
Tipo:            Institución educativa
```

### Necesidad extraordinaria

**¿Cuándo se usa?** Cuando ocurre un evento puntual que genera una demanda concreta y acotada.

**Ejemplo:** Una inundación en un aula destruyó el mobiliario.

```
Subcategoría:      Sillas (Mobiliario)
Descripción:       "Inundación en Aula 3 — reposición urgente de mobiliario"
Cantidad requerida: 30 unidades
Tipo:              Extraordinaria
```

La necesidad se satisface cuando la cantidad acumulada de sillas recibidas llega o supera 30.

**¿Cómo se cubren las 30 sillas?**

```
Estado inicial:
  Sillas requeridas: 30
  Sillas cubiertas:   0

Donación 1 → Ana Pérez dona 2 sillas
  Sillas cubiertas:   2  (30 - 2 = 28 restantes)

Donación 2 → Arcos Plateados dona 6 sillas
  Sillas cubiertas:   8  (30 - 8 = 22 restantes)

Donación 3 → otra empresa dona 22 sillas
  Sillas cubiertas:  30  ← estaSatisfecha() = true ✓
```

### Necesidad recurrente

**¿Cuándo se usa?** Cuando la organización necesita un bien de forma periódica para su funcionamiento habitual.

**Ejemplo:** El comedor infantil "Escobar Sonrisas" necesita fideos cada semana.

```
Subcategoría:        Fideos (Alimentos)
Descripción:         "Fideos para preparación de viandas semanales"
Cantidad por período: 100 paquetes
Período:             SEMANA
Tipo:                Recurrente
```

La necesidad se satisface dentro de cada período. Al comenzar la semana siguiente, el contador vuelve a 0 y la necesidad debe cubrirse nuevamente.

```
Semana del 21/04:
  Fideos requeridos por semana: 100
  Lunes:   recibe 40 paquetes → cubiertos: 40
  Miércoles: recibe 60 paquetes → cubiertos: 100 → estaSatisfecha() = true ✓

Semana del 28/04:
  Contador reiniciado → cubiertos: 0
  (el comedor vuelve a necesitar 100 paquetes)
```

---

## Flujo 4: Cómo una donación cubre una necesidad

Este es el proceso central del sistema. Cada vez que se registra que una necesidad recibe bienes, el sistema verifica:

1. **¿La subcategoría coincide?** Una necesidad de "Fideos" no puede cubrirse con "Arroz". El sistema valida que el bien donado pertenezca exactamente a la misma subcategoría.

2. **¿El bien ya fue donado?** Un mismo bien físico no puede cubrir dos necesidades distintas. El sistema marca cada bien como `yaFueDonado = true` una vez procesado.

3. **¿Cuánto se acumula?** La cantidad del bien se suma a `cantidadCubierta` de la necesidad.

4. **¿Está satisfecha?** Dependiendo del tipo de necesidad, el criterio varía (ver Flujo 3).

**Ejemplo completo:**

```
Necesidad: 30 sillas (Escuela Rural N°10) — Extraordinaria
Donación disponible: 6 sillas usadas (Arcos Plateados)

El sistema ejecuta recibirDonacion(bien):
  1. bien.getSubcategoria() == necesidad.getSubcategoria() → "Sillas" == "Sillas" ✓
  2. bien.getYaFueDonado() == false ✓
  3. cantidadCubierta += 6 → cantidadCubierta = 6
  4. estaSatisfecha() → 6 >= 30 → false (aún no satisfecha)

Próxima donación: 24 sillas adicionales
  cantidadCubierta += 24 → cantidadCubierta = 30
  estaSatisfecha() → 30 >= 30 → true ✓ Necesidad cubierta.
```

---

## Flujo 5: Envío de notificaciones

El sistema de notificaciones es un servicio independiente que puede ser invocado ante distintos eventos. En esta primera iteración, simula el envío (imprime en consola). En entregas futuras se integrará con servicios reales de email, SMS y WhatsApp.

### ¿Cómo funciona?

Cualquier parte del sistema puede pedir el envío de una notificación especificando:
- **Destinatario:** dirección de email, número de teléfono o número de WhatsApp.
- **Mensaje:** el texto a enviar.
- **Canal:** EMAIL, SMS o WHATSAPP.

```
Solicitud de notificación:
  Canal:      EMAIL
  Destino:    ana@mail.com
  Mensaje:    "Tu donación de fideos fue asignada al comedor Escobar Sonrisas."

El sistema:
  1. Valida que "ana@mail.com" tenga formato de email válido ✓
  2. Selecciona EmailNotificador como canal de envío
  3. Simula el envío (stdout): "Enviando email a ana@mail.com: Tu donación..."
```

**¿Qué validaciones hace?**

| Canal | Formato válido | Ejemplo inválido |
|---|---|---|
| EMAIL | Debe contener `@` y dominio | `anamail.com` → rechazado |
| SMS | Entre 8 y 15 dígitos, opcionalmente `+` al inicio | `asdasd123` → rechazado |
| WHATSAPP | Igual que SMS | `123` → rechazado (muy corto) |

### ¿En qué situaciones se notifica? (Entrega 1 — simuladas)

| Evento | Quién recibe |
|---|---|
| Nueva persona donante registrada | Persona donante (email de bienvenida) |
| Importación masiva — usuario creado | Persona donante (credenciales de acceso) |

En entregas siguientes se agregarán: donación asignada, misión cumplida, cambio de categoría, inicio de ruta de entrega, etc.

---

## Resumen de entidades y sus relaciones

```
DonanteHumano ──────┐
                     ├── Donante ──► Donacion ──► Bien ──┬── BienPerecible
DonanteJuridico ────┘              (segmentada)           └── BienNoPerecible
     │                               por SubcategoriaBien
     └── Representante                    │
                                          │ matching (Entrega 2)
                                          ▼
EntidadBeneficiaria ──► Necesidad ──┬── NecesidadExtraordinaria
                                     └── NecesidadRecurrente
                                              │
                                              └── SubcategoriaBien (misma que Bien)


Notificacion ──► MedioDeContacto ──► TipoMedioContacto (EMAIL / SMS / WHATSAPP)
                      │
                      └── elegido según medioPredeterminado del DonanteHumano
```

---

## Restricciones importantes del dominio

Estas son las reglas de negocio que el sistema garantiza en todo momento:

| Restricción | Entidad | Qué pasa si se viola |
|---|---|---|
| Un donante debe tener al menos un medio de contacto | `Donante` | Error en el constructor |
| Una persona humana debe tener al menos un EMAIL | `DonanteHumano` | Error en el constructor |
| El medio predeterminado debe estar en la lista de medios | `DonanteHumano` | Error en el constructor |
| No se puede eliminar el medio predeterminado | `DonanteHumano` | Error en `eliminarMedio()` |
| No se puede eliminar el único email | `DonanteHumano` | Error en `eliminarMedio()` |
| Un donante jurídico necesita al menos un representante | `DonanteJuridico` | Error en el constructor |
| No se puede eliminar el último representante | `DonanteJuridico` | Error en `eliminarRepresentante()` |
| Un bien debe tener descripción, subcategoría y cantidad > 0 | `Bien` | Error en el constructor |
| Un bien perecedero debe tener fecha de vencimiento | `BienPerecible` | Error en el constructor |
| La subcategoría de un bien debe coincidir con la de la necesidad | `Necesidad` | Error en `recibirDonacion()` |
| Un bien no puede cubrir dos necesidades distintas | `Necesidad` | Error en `recibirDonacion()` |

---

## Para seguir leyendo

Una vez entendido el flujo general, el [README técnico](README.md) explica en detalle:

- Las jerarquías de clases y por qué se eligió herencia en cada caso.
- Los patrones de diseño aplicados (Template Method, Strategy, polimorfismo).
- Cada decisión de diseño con su justificación.
- Cómo ejecutar los tests del proyecto.

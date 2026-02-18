# Introducción

El objetivo de este proyecto es desarrollar una aplicación que facilite la compraventa de productos de segunda mano. La plataforma, `SegundUM`, permitirá a los usuarios publicar productos para su venta y a otros usuarios adquirirlos de manera sencilla. Los productos estarán organizados en categorías, y los usuarios podrán recibir valoraciones tanto como vendedores como compradores, promoviendo la confianza dentro de la comunidad.

El primer entregable de la asignatura se centra en la implementación de la funcionalidad relacionada con la gestión de usuarios, productos y categorías: `ServicioUsuarios`, `ServicioProductos` y `ServicioCategorias`

---

## Historias de usuario

1.  Como usuario, quiero registrarme en la aplicación para poder acceder a sus funcionalidades.
2.  Como usuario, quiero modificar mis datos personales para mantener mi información actualizada.
3.  Como usuario, quiero dar de alta un producto para ponerlo a la venta.
4.  Como usuario, quiero modificar mis productos a la venta para cambiar su precio y/o descripción para que sus datos estén actualizados.
5.  Como usuario, quiero asociar un lugar de recogida a un producto que he puesto a la venta para facilitar su entrega.
6.  Como usuario, quiero obtener un resumen mensual de mis productos en venta y sus visualizaciones.
7.  Como usuario, quiero consultar los productos a la venta filtrando por descripción, categoría, estado y precio para localizar productos que me interesan.
8.  Como administrador, quiero cargar nuevas categorías para clasificar productos.
9.  Como administrador, quiero modificar las categorías existentes para que tengan una descripción.

---

## Modelo del dominio

| Usuario |
| :--- |
| id: texto |
| email: texto |
| nombre: texto |
| apellidos: texto |
| clave: texto |
| fecha de nacimiento: fecha |
| telefono: texto |
| administrador: booleano |

| Categoría |
| :--- |
| id: texto |
| nombre: texto |
| descripción: texto |
| ruta: texto |
| subcategorias: [Categoría] |

| Producto |
| :--- |
| id: texto |
| título: texto |
| descripción: texto |
| precio: número |
| estado: enum{nuevo, como nuevo, buen estado, aceptable, para piezas o reparar} |
| fecha publicacion: fecha y hora |
| categoria: Categoría |
| visualizaciones: número |
| envio disponible: booleano |
| recogida: Lugar de recogida |
| vendedor: Usuario |

| Lugar de recogida |
| :--- |
| descripción: texto |
| longitud: número |
| latitud: número |

---

## Funcionalidad

### servicioCategorias
El servicio `servicioCategorias` define la siguiente funcionalidad:
* **Cargar jerarquía de categorías**: Recibe la ruta de un fichero `xml` con la jerarquía de categorías. Procesa y almacena la jerarquía, pero no debe cargar categorías principales que ya existan.
* **Modificar una categoría**: Añade o cambia la descripción de una categoría a partir de su identificador.
* **Recuperar categorías raíz**: Devuelve un listado con las categorías que no son subcategoría de ninguna otra.
* **Recuperar descendientes de una categoría**: Recibe un identificador de categoría y devuelve un listado de todas sus subcategorías.

### servicioUsuarios
El servicio `servicioUsuarios` ofrece la siguiente funcionalidad:
* **Alta de un usuario**: Registra un usuario con su nombre, apellidos, email, clave, fecha de nacimiento y teléfono (opcional). Se le asigna un ID único y no será administrador.
* **Modificar un usuario**: Permite modificar el nombre, apellidos, clave, fecha de nacimiento y teléfono de un usuario a partir de su identificador.

### servicioProductos
El servicio `servicioProductos` ofrece la siguiente funcionalidad:
* **Alta de un producto**: Registra un producto con su título, descripción, precio, estado, ID de categoría, disponibilidad de envío e ID del vendedor. La aplicación le asigna un ID único y la fecha de publicación actual.
* **Asignar lugar de recogida**: Establece el lugar de recogida de un producto a partir de su identificador, longitud, latitud y descripción.
* **Modificar datos de un producto**: Permite cambiar el precio y/o la descripción de un producto.
* **Añadir visualización**: Incrementa en uno el contador de visualizaciones de un producto.
* **Historial del mes**: Devuelve un resumen (título, precio, fecha, categoría y visualizaciones) de los productos en venta para un mes y año concretos, ordenados por número de visualizaciones.
* **Buscar productos a la venta**: Filtra productos por categoría (incluyendo descendientes), texto en la descripción, estado (o mejor) y precio máximo. Todos los parámetros son opcionales. El orden de los estados es: `nuevo > como nuevo > buen estado > aceptable > para piezas o reparar`.

---

## Requisitos de diseño
La implementación debe seguir los siguientes principios de diseño:
* Inversión de dependencias.
* Patrón Repositorio y Repositorio AdHoc.
* Patrón Servicio.

---

## Requisitos de implementación
* Uso del API `JAXB` de XML para la carga de jerarquías de categorías. Los ficheros se encuentran en el archivo `categoriasXML.zip`.
* Todos los repositorios deben usar `JPA` para la persistencia de entidades en `MySQL`.

---

## Pruebas
Se debe incluir un programa para probar toda la funcionalidad de los servicios (`ServicioCategorias`, `ServicioUsuarios` y `ServicioProductos`). El programa debe mostrar por consola mensajes sobre el resultado de las acciones.

---

## Entrega del trabajo
* **Grupo**: El trabajo será realizado en grupos de dos estudiantes.
* **Entrega**: Se entregará a través de un repositorio Git en GitHub y en el Aula Virtual.
* **Historial Git**: Es obligatorio el uso de Git desde el inicio del desarrollo; no se aceptará un repositorio solo con el código final.
* **Gestión con Maven**:
    * El nombre del proyecto debe ser `segundumApellido1-Apellido2`.
    * Debe incluir todas las dependencias necesarias.
    * Se debe usar Java `8` u `11` y codificación `UTF-8`.


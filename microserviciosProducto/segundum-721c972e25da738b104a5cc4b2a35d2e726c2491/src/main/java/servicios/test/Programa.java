package servicios.test; // O 'dominio' si prefieres

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

// Importa las clases de tu proyecto
import dominio.Categoria;
import dominio.EstadoProducto;
import dominio.Producto;
import dominio.Usuario;
import repositorios.EntidadNoEncontrada;
import repositorios.FactoriaRepositorios;
import repositorios.RepositorioException;
import repositorios.RepositorioUsuariosJPA;
import servicios.FactoriaServicios;
import servicios.IServicioCategorias;
import servicios.IServicioProductos;
import servicios.IServicioUsuarios;
import utils.ProductoResumen;

public class Programa {


	private static IServicioUsuarios servicioUsuarios = FactoriaServicios.getServicio(IServicioUsuarios.class);
	private static IServicioCategorias servicioCategorias = FactoriaServicios.getServicio(IServicioCategorias.class);
	private static IServicioProductos servicioProductos = FactoriaServicios.getServicio(IServicioProductos.class);


	private static String idAdmin = null;
	private static String idUser = null;
	private static String idProducto1 = null;
	

	private static String idCategoriaRaiz = "8"; // "Arte y ocio"
	private static String idSubCategoria = "5709"; // "Fiestas y celebraciones"

	public static void main(String[] args) {
		
		System.out.println("--- INICIO DE PRUEBAS DE SERVICIOS SEGNDUM ---");

		try {
			probarServicioCategorias(); 
			probarServicioProductos();

		} catch (Exception e) {
			System.err.println("!! PRUEBA FALLIDA: Ha ocurrido una excepción inesperada !!");
			e.printStackTrace();
		}

		System.out.println("--- FIN DE TODAS LAS PRUEBAS ---");
	}



	private static void probarServicioCategorias() throws RepositorioException, EntidadNoEncontrada {
		System.out.println("\n--- Probando ServicioCategorias ---");
		
		if (idAdmin == null || idUser == null) {
			System.out.println("Prueba OMITIDA: Se requieren los IDs de usuario de la prueba anterior.");
			return;
		}

		// 1. Cargar TODAS las jerarquías de categorías (con admin)
		System.out.println("Cargando todas las categorías desde el directorio del usuario con ADMIN (" + idAdmin + ")...");
		

		String baseDir = "categoriasXML/";

		String[] archivosCategorias = {
		    "Arte_y_ocio.xml", "Bebes_y_peques.xml", "Bricolaje.xml",
		    "Camaras_y_opticas.xml", "Casa_y_jardin.xml", "Economia_e_industria.xml",
		    "Electronica.xml", "Elementos_religiosos_y_ceremoniales.xml",
		    "Equipamiento_deportivo.xml", "Juegos_y_juguetes.xml", "Maletas_y_bolsos_de_viaje.xml",
		    "Material_de_oficina.xml", "Mobiliario.xml", "Multimedia.xml",
		    "Productos_para_mascotas_y_animales.xml", "Ropa_y_accesorios.xml",
		    "Salud_y_belleza.xml", "Software.xml", "Vehiculos_y_recambios.xml"
		};
		
		int cargadosExito = 0;
		for (String archivo : archivosCategorias) {
		    String rutaCompleta = baseDir + archivo; 
		    System.out.println("Intentando cargar: " + archivo);
		    if (servicioCategorias.cargarCategorias(rutaCompleta, "1")) {
		        cargadosExito++;
		    }
		}
		
		System.out.println("--- Carga de categorías completada (" + cargadosExito + " nuevas) ---");

		// 2. Intentar cargar de nuevo (debe fallar)
		System.out.println("Intentando cargar de nuevo 'Arte_y_ocio.xml' (debe fallar)...");
		String rutaDuplicada = baseDir + java.io.File.separator + "Arte_y_ocio.xml";
		boolean cargadas = servicioCategorias.cargarCategorias(rutaDuplicada, idAdmin);
		if (!cargadas) {
			System.out.println("Prueba exitosa: No se cargaron categorías duplicadas.");
		} else {
			System.out.println("Prueba FALLIDA: Se cargaron categorías duplicadas.");
		}

		// 3. Modificar una categoría (con admin)
		String desc = "Descripción de prueba para Arte y Ocio";
		System.out.println("Modificando categoría " + idCategoriaRaiz + " con ADMIN...");
		boolean modificada = servicioCategorias.modificarCategoria(idCategoriaRaiz, desc, idAdmin);
		if (modificada) {
			System.out.println("Categoría MODIFICADA.");
		} else {
			System.out.println("Prueba FALLIDA: Admin no pudo modificar la categoría " + idCategoriaRaiz);
		}

		// 4. Modificar una categoría (con user normal - debe fallar)
		System.out.println("Intentando modificar categoría " + idCategoriaRaiz + " con USER (debe fallar)...");
		modificada = servicioCategorias.modificarCategoria(idCategoriaRaiz, "Intento fallido", idUser);
		if (!modificada) {
			System.out.println("Prueba exitosa: Usuario normal NO pudo modificar la categoría.");
		} else {
			System.out.println("Prueba FALLIDA: Usuario normal SÍ pudo modificar la categoría.");
		}
		
		// 5. Recuperar categorías raíz
		System.out.println("Recuperando categorías raíz...");
		List<Categoria> raices = servicioCategorias.recuperarCategoriasRaiz();
		if (raices != null && !raices.isEmpty()) {
			System.out.println("Categorías raíz RECUPERADAS (" + raices.size() + "):");
			raices.forEach(c -> System.out.println("- " + c.getNombre() + " [ID: " + c.getId() + "]"));
		} else {
			System.out.println("Prueba FALLIDA: No se encontraron categorías raíz.");
		}

		// 6. Recuperar descendientes de una categoría
		if (raices != null && !raices.isEmpty()) {
			System.out.println("Recuperando descendientes de " + idCategoriaRaiz + "...");
			List<Categoria> descendientes = servicioCategorias.recuperarCategoriasDescendentes(idCategoriaRaiz);
			
			if (descendientes != null && !descendientes.isEmpty()) {
				System.out.println("Descendientes RECUPERADOS (" + descendientes.size() + ")");
				descendientes.stream().limit(10).forEach(d -> System.out.println("- " + d.getNombre() + " [Ruta: " + d.getRuta() + "]"));
			} else {
				System.out.println("No se encontraron descendientes para " + idCategoriaRaiz + ".");
			}
		} else {
			System.out.println("OMITIDA prueba de descendientes (no se cargaron categorías raíz).");
		}
	}


	private static void probarServicioProductos() throws RepositorioException, EntidadNoEncontrada {
		System.out.println("\n--- Probando ServicioProductos ---");
		
		if (idUser == null) {
			System.out.println("Prueba OMITIDA: Se requiere el ID de usuario de la prueba anterior.");
			return;
		}

		// 1. Alta de un producto (en subcategoría)
		System.out.println("Registrando Producto 1 (en subcategoría " + idSubCategoria + ")...");
		Optional<String> idProducto1Opt = servicioProductos.registrarProducto(
			"Confeti de colores", 
			"Bolsa de 1kg de confeti para fiestas. Varios colores.", 
			15.50, 
			EstadoProducto.NUEVO, 
			idSubCategoria, // "Fiestas y celebraciones"
			"true", 
			idUser
		);
		
		if (idProducto1Opt.isPresent()) {
			idProducto1 = idProducto1Opt.get();
			System.out.println("Producto 1 CREADO con ID: " + idProducto1);
		} else {
			System.out.println("Prueba FALLIDA: No se pudo registrar el Producto 1.");
			return; 
		}
		
		// 2. Alta de Producto 2 (en categoría raíz)
		System.out.println("Registrando Producto 2 (en categoría raíz " + idCategoriaRaiz + ")...");
		Optional<String> idProducto2Opt = servicioProductos.registrarProducto(
			"Lienzo para pintar", 
			"Lienzo 50x70cm, buen estado, usado una vez.", 
			25.0, 
			EstadoProducto.BUEN_ESTADO, 
			idCategoriaRaiz, 
			"false", 
			idUser
		);
		String idProducto2 = idProducto2Opt.orElse(null);
		if (idProducto2 != null) {
			System.out.println("Producto 2 CREADO con ID: " + idProducto2);
		} else {
			System.out.println("Prueba FALLIDA: No se pudo registrar el Producto 2.");
		}

		// 3. Asignar lugar de recogida
		System.out.println("Asignando lugar de recogida a " + idProducto1 + "...");
		boolean recogidoAsignado = servicioProductos.asignarLugarRecogida(idProducto1, "Facultad de Informática, Murcia", -1, 38);
		if (recogidoAsignado) {
			System.out.println("Lugar de recogida ASIGNADO.");
		} else {
			System.out.println("Prueba FALLIDA: No se pudo asignar lugar de recogida.");
		}

		// 4. Modificar datos de un producto
		System.out.println("Modificando Producto 1 (rebaja de precio)...");
		boolean modificado = servicioProductos.modificarProducto(idProducto1, 12.0, "Bolsa de 1kg de confeti para fiestas. ¡REBAJADO!");
		if (modificado) {
			System.out.println("Producto 1 MODIFICADO.");
		} else {
			System.out.println("Prueba FALLIDA: No se pudo modificar el producto.");
		}

		// 5. Añadir visualización
		System.out.println("Añadiendo 3 visualizaciones a P1 y 1 a P2...");
		servicioProductos.addVisualizacion(idProducto1);
		servicioProductos.addVisualizacion(idProducto1);
		servicioProductos.addVisualizacion(idProducto1);
		servicioProductos.addVisualizacion(idProducto2);
		System.out.println("Visualizaciones AÑADIDAS.");

		// 6. Historial del mes (Debe salir P1 primero por tener más visitas)
		System.out.println("Obteniendo historial de ventas para el mes actual...");
		List<ProductoResumen> historial = servicioProductos.getHistorialVentas(LocalDate.now());
		if (historial != null && !historial.isEmpty() && historial.get(0).getId().equals(idProducto1)) {
			System.out.println("Historial RECUPERADO (" + historial.size() + ") y ordenado (P1 primero):");
			historial.forEach(resumen -> System.out.println("- " + resumen.getTitulo() + " (Visitas: " + resumen.getVisualizaciones() + ")"));
		} else {
			System.out.println("Prueba FALLIDA: El historial no se recuperó o no está ordenado por visualizaciones.");
		}

		// 7. Buscar productos a la venta (Pruebas de filtro)
		
		// 7a. Filtro por categoría (debe incluir descendientes)
		System.out.println("Buscando por Categoría Raíz " + idCategoriaRaiz + " (deben salir 2)...");
		List<Producto> productos = servicioProductos.getProductoByFiltros(idCategoriaRaiz, null, null, 0);
		if (productos != null && productos.size() == 2) {
			System.out.println("Prueba exitosa: Se encontraron " + productos.size() + " productos (incluyendo descendientes).");
		} else {
			System.out.println("Prueba FALLIDA: No se encontraron los 2 productos esperados.");
		}
		
		// 7b. Filtro por precio (debe salir P1)
		System.out.println("Buscando por Precio <= 20.0 (debe salir 1)...");
		productos = servicioProductos.getProductoByFiltros(null, null, null, 20.0);
		if (productos != null && productos.size() == 1 && productos.get(0).getId().equals(idProducto1)) {
			System.out.println("Prueba exitosa: Se encontró 1 producto por precio.");
		} else {
			System.out.println("Prueba FALLIDA: El filtro de precio no funcionó.");
		}

		// 7c. Filtro por estado "o mejor" (deben salir los 2)
		System.out.println("Buscando por Estado = BUEN_ESTADO o mejor (deben salir 2)...");
		productos = servicioProductos.getProductoByFiltros(null, null, EstadoProducto.BUEN_ESTADO, 0);
		if (productos != null && productos.size() == 2) {
			System.out.println("Prueba exitosa: Se encontraron 2 productos con estado 'o mejor'.");
		} else {
			System.out.println("Prueba FALLIDA: El filtro de estado 'o mejor' no funcionó.");
		}
		
		// 7d. Filtro por texto (debe salir P2)
		System.out.println("Buscando por Texto 'Lienzo' (debe salir 1)...");
		productos = servicioProductos.getProductoByFiltros(null, "Lienzo", null, 0);
		if (productos != null && productos.size() == 1 && productos.get(0).getId().equals(idProducto2)) {
			System.out.println("Prueba exitosa: Se encontró 1 producto por texto.");
		} else {
			System.out.println("Prueba FALLIDA: El filtro de texto no funcionó.");
		}
		
		// 7e. Filtro combinado (debe salir P1)
		System.out.println("Buscando por Categ=" + idSubCategoria + ", Texto='confeti', Estado=NUEVO, Precio<=15.0 (debe salir 1)...");
		productos = servicioProductos.getProductoByFiltros(idSubCategoria, "confeti", EstadoProducto.NUEVO, 15.0);
		if (productos != null && productos.size() == 1 && productos.get(0).getId().equals(idProducto1)) {
			System.out.println("Prueba exitosa: Se encontró 1 producto con filtro combinado.");
		} else {
			System.out.println("Prueba FALLIDA: El filtro combinado no funcionó como se esperaba.");
		}
	}
}
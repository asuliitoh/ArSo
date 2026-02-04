package servicios.test; // O 'dominio' si prefieres

import java.time.LocalDate;
import java.util.Optional;

// Importa las clases de tu proyecto
import dominio.Usuario;
import repositorios.EntidadNoEncontrada;
import repositorios.FactoriaRepositorios;
import repositorios.RepositorioException;
import repositorios.RepositorioUsuariosJPA;
import servicios.FactoriaServicios;
import servicios.IServicioUsuarios;
public class Programa {


	private static IServicioUsuarios servicioUsuarios = FactoriaServicios.getServicio(IServicioUsuarios.class);
	

	private static String idAdmin = null;
	private static String idUser = null;
	
	public static void main(String[] args) {
		
		System.out.println("--- INICIO DE PRUEBAS DE SERVICIOS SEGNDUM ---");

		try {
			probarServicioUsuarios();

		} catch (Exception e) {
			System.err.println("!! PRUEBA FALLIDA: Ha ocurrido una excepción inesperada !!");
			e.printStackTrace();
		}

		System.out.println("--- FIN DE TODAS LAS PRUEBAS ---");
	}


	private static void probarServicioUsuarios() throws RepositorioException, EntidadNoEncontrada {
		System.out.println("\n--- Probando ServicioUsuarios ---");


		System.out.println("Registrando usuario (Admin)...");
		Optional<String> idAdminOpt = servicioUsuarios.registrarUsuario("Admin", "SegundUM", "admin@segundum.es", "admin123", LocalDate.of(2000, 1, 1), "600111222");
		if (idAdminOpt.isPresent()) {
			idAdmin = idAdminOpt.get();
			System.out.println("Usuario Admin CREADO con ID: " + idAdmin);
		} else {
			System.out.println("Prueba FALLIDA: No se pudo registrar al admin.");
			throw new RuntimeException("No se pudo crear el admin.");
		}
		

		System.out.println("Promoviendo a ID " + idAdmin + " a Administrador (solo para pruebas)...");
		try {
			RepositorioUsuariosJPA repoUsuarios = FactoriaRepositorios.getRepositorio(Usuario.class);
			Usuario admin = repoUsuarios.getById(idAdmin);
			admin.setEsAdministrador(true); 
			repoUsuarios.update(admin); 
			System.out.println("Usuario " + idAdmin + " AHORA ES ADMIN.");
		} catch (Exception e) {
			System.err.println("Prueba FALLIDA: No se pudo hacer admin al usuario.");
			e.printStackTrace();
			throw e;
		}


		System.out.println("Registrando usuario (User)...");
		Optional<String> idUserOpt = servicioUsuarios.registrarUsuario("Usuario", "Pruebas", "user@segundum.es", "user123", LocalDate.of(1995, 5, 10), null);
		if (idUserOpt.isPresent()) {
			idUser = idUserOpt.get();
			System.out.println("Usuario User CREADO con ID: " + idUser);
		} else {
			System.out.println("Prueba FALLIDA: No se pudo registrar al user.");
			throw new RuntimeException("No se pudo crear el user.");
		}


		System.out.println("Modificando usuario " + idUser + "...");
		boolean modificado = servicioUsuarios.modificarUsuario(idUser, "Usuario Modificado", null, null, "claveNueva", null, "700800900");
		if (modificado) {
			System.out.println("Usuario MODIFICADO.");
		} else {
			System.out.println("Prueba FALLIDA: No se pudo modificar al user.");
		}


		System.out.println("Recuperando usuario " + idUser + " para verificar...");
		Usuario userRecuperado = servicioUsuarios.recuperarUsuario(idUser);
		if (userRecuperado != null && userRecuperado.getNombre().equals("Usuario Modificado") && userRecuperado.getTelefono().equals("700800900")) {
			System.out.println("Usuario RECUPERADO con éxito: " + userRecuperado.getNombre() + ", Tel: " + userRecuperado.getTelefono());
		} else {
			System.out.println("Prueba FALLIDA: Los datos recuperados no coinciden con la modificación.");
		}
	}

}
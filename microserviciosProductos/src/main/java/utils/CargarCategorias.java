package utils;

import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import dominio.Categoria;


public class CargarCategorias implements ICargarCategorias {

	public Categoria cargarCategorias(String ruta) {
		
		InputStream f = getClass().getClassLoader().getResourceAsStream(ruta); 
		if (f == null) {
			System.err.println("Error: No se pudo encontrar el fichero de categoría en el classpath: " + ruta);
			return null;
		}
		
		JAXBContext contexto;
		try {
			contexto = JAXBContext.newInstance(Categoria.class);
			Unmarshaller unmarsaller = contexto.createUnmarshaller();
			// JAXB puede leer directamente desde un InputStream
			Categoria categoriasRaices = (Categoria) unmarsaller.unmarshal(f); 
			return categoriasRaices;
		} catch (JAXBException e) {
			System.err.println("Error tratando de cargar las categorías del xml " + ruta);
			return null;
		}
		
	}
	
}

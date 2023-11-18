package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.model.Curso;
import com.service.CursoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin("*")
@RestController
@Tag(name="Servicio de cursos", description="Servicio de consulta, creación, borrado y modificacion de cursos")
public class CursoController {
	
	@Autowired
	CursoService cursoService;
	
	@Operation(summary="Busca todos los cursos", description="Busca todos los cursos existentes. No recibe parámetros")
	@GetMapping(value="cursos",produces=MediaType.APPLICATION_JSON_VALUE)
	public List<Curso> buscaAllCursos() {
		return cursoService.buscaAllCursos();
	}
	
	@Operation(summary="Busca curso por su codigo", description="Recibe un codigo y devuelve el cusro correspondiente")
	@GetMapping(value="curso/{codCurso}",produces=MediaType.APPLICATION_JSON_VALUE)
	public Curso buscaCursoPorCodigo(@Parameter(name="codCurso", description = "Código del curso a buscar") @PathVariable("codCurso") String codCurso) {
		return cursoService.buscaCursoPorCodigo(codCurso);
	}
	
	@Operation(summary="Busca cursos por precio", description="Recibe un rango de precios y devuelve los cursos cuyo precio está dentro del rango")
	@GetMapping(value = "cursos/{precioMin}/{precioMax}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Curso> buscaCursosPorRangoPrecios(@Parameter(name="precioMin", description = "Precio mínimo del rango") @PathVariable("precioMin") double precioMin,
			@Parameter(name="precioMax", description = "Precio máximo del rango") @PathVariable("precioMax") double precioMax) {
		return cursoService.buscaCursosPorRangoPrecios(precioMin, precioMax);
	}	
	
	@Operation(summary="Crea un nuevo curso", description="Recibe un curso en el body de la petición y lo da de alta")
	@PostMapping(value="curso",consumes=MediaType.APPLICATION_JSON_VALUE)
	public List<Curso> crea(@RequestBody Curso curso) {
		cursoService.creaCurso(curso);
		return cursoService.buscaAllCursos();
	}
	
	@Operation(summary="Elimina un curso", description="Recibe un codigo de curso y lo elimina")
	@DeleteMapping(value="curso/{codCurso}",produces=MediaType.APPLICATION_JSON_VALUE)
	public List<Curso> elimina(@Parameter(name="codCurso", description = "Código del curso a eliminar") @PathVariable("codCurso") String codCurso){
		cursoService.eliminaCurso(codCurso);
		return cursoService.buscaAllCursos();
	}
	
	@Operation(summary="Modifica la duración de un curso", description="Recibe un código de curso y una nueva duración, y modifica el curso")
	@PutMapping(value="curso/{codCurso}/{duracion}")
	public void modificaDuracion(@Parameter(name="codCurso", description = "Código del curso a modificar") @PathVariable("codCurso") String codCurso,
			@Parameter(name="duracion", description = "Nueva duración a establecer") @PathVariable("duracion") int duracion) {
		cursoService.modificaDuracion(codCurso, duracion);
	}

}

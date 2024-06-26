package logica;

public class Tarea {
	
	private String nombre;
	private String nombreEstudiante;
	private String fechaEntrega;
	private Double nota;
	private boolean enviado;
	
	public Tarea(String nombre, String nombreEstudiante, String fechaEntrega, double nota) {
		this.nombre = nombre;
		this.nombreEstudiante = nombreEstudiante;
		this.fechaEntrega = fechaEntrega;
		this.nota = nota == 0 ? null : nota;
	}
	public Tarea(String nombre) {
		this.nombre = nombre;
	}
	
	public boolean isEnviado() {
		return enviado;
	}
	public void setEnviado(boolean enviado) {
		this.enviado = enviado;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getNombreEstudiante() {
		return nombreEstudiante;
	}
	public void setNombreEstudiante(String nombreAlumno) {
		this.nombreEstudiante = nombreAlumno;
	}
	public String getFechaEntrega() {
		return fechaEntrega;
	}
	public void setFechaEntrega(String fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}
	public Double getNota() {
		return nota;
	}
	public void setNota(Double nota) {
		this.nota = nota;
	}

}

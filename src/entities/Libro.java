
package entities;

/**
 * @author Julian Daniel Gómez <https://github.com/juliandg1995>
 * 
 * Entidad de dominio principal que representa un Libro.
 * Contiene una referencia 1→1 unidireccional a FichaBibliografica.
 */

public class Libro{

    private Long id;
    private Boolean eliminado;
    private String titulo;
    private String autor;
    private String editorial;
    private Integer anioEdicion;
    private FichaBibliografica fichaBibliografica;

    public Libro(){
        this.eliminado = false;
    }

    public Libro(Long id, Boolean eliminado, String titulo, String autor,
                 String editorial, Integer anioEdicion, FichaBibliografica fichaBibliografica){
        this.id = id;
        this.eliminado = eliminado;
        this.titulo = titulo;
        this.autor = autor;
        this.editorial = editorial;
        this.anioEdicion = anioEdicion;
        this.fichaBibliografica = fichaBibliografica;
    }

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public Boolean getEliminado(){
        return eliminado;
    }

    public void setEliminado(Boolean eliminado){
        this.eliminado = eliminado;
    }

    public String getTitulo(){
        return titulo;
    }

    public void setTitulo(String titulo){
        this.titulo = titulo;
    }

    public String getAutor(){
        return autor;
    }

    public void setAutor(String autor){
        this.autor = autor;
    }

    public String getEditorial(){
        return editorial;
    }

    public void setEditorial(String editorial){
        this.editorial = editorial;
    }

    public Integer getAnioEdicion(){
        return anioEdicion;
    }

    public void setAnioEdicion(Integer anioEdicion){
        this.anioEdicion = anioEdicion;
    }

    public FichaBibliografica getFichaBibliografica(){
        return fichaBibliografica;
    }

    public void setFichaBibliografica(FichaBibliografica fichaBibliografica){
        this.fichaBibliografica = fichaBibliografica;
    }

    @Override
    public String toString(){
        String isbn = (fichaBibliografica != null && fichaBibliografica.getIsbn() != null)
                ? fichaBibliografica.getIsbn()
                : "SIN FICHA";
        return "Libro{" +
                "id=" + id +
                ", eliminado=" + eliminado +
                ", titulo='" + titulo + '\'' +
                ", autor='" + autor + '\'' +
                ", editorial='" + editorial + '\'' +
                ", anioEdicion=" + anioEdicion +
                ", isbnFicha=" + isbn +
                '}';
    }
}
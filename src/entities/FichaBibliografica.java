
package entities;

/**
 * @author Julian Daniel Gómez <https://github.com/juliandg1995>
 *
 * Entidad que representa la ficha bibliográfica asociada a un libro.
 * Comparte la PK con la tabla Libro (id es también FK).
 */

public class FichaBibliografica{

    private Long id;
    private Boolean eliminado;
    private String isbn;
    private String clasificacionDewey;
    private String estanteria;
    private String idioma;

    public FichaBibliografica(){
        this.eliminado = false;
    }

    public FichaBibliografica(Long id, Boolean eliminado, String isbn,
                              String clasificacionDewey, String estanteria, String idioma){
        this.id = id;
        this.eliminado = eliminado;
        this.isbn = isbn;
        this.clasificacionDewey = clasificacionDewey;
        this.estanteria = estanteria;
        this.idioma = idioma;
    }

    // Getters & Setters
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

    public String getIsbn(){
        return isbn;
    }

    public void setIsbn(String isbn){
        this.isbn = isbn;
    }

    public String getClasificacionDewey(){
        return clasificacionDewey;
    }

    public void setClasificacionDewey(String clasificacionDewey){
        this.clasificacionDewey = clasificacionDewey;
    }

    public String getEstanteria(){
        return estanteria;
    }

    public void setEstanteria(String estanteria){
        this.estanteria = estanteria;
    }

    public String getIdioma(){
        return idioma;
    }

    public void setIdioma(String idioma){
        this.idioma = idioma;
    }

    @Override
    public String toString(){
        return "FichaBibliografica{" +
                "id=" + id +
                ", eliminado=" + eliminado +
                ", isbn='" + isbn + '\'' +
                ", clasificacionDewey='" + clasificacionDewey + '\'' +
                ", estanteria='" + estanteria + '\'' +
                ", idioma='" + idioma + '\'' +
                '}';
    }
}

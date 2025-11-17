/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

/**
 *
 * @author Sandra Martinez
 */
public class FichaBibliografica extends Base {
    
    private String isbn;
    private String clasificacionDewey;
    private String estanteria;
    private String idioma;
    
    public FichaBibliografica() {
        super();
    }
    
    public FichaBibliografica(Long id, Boolean eliminado, String isbn, String clasificacionDewey, String estanteria, String idioma) {
        super(id, eliminado);
        this.isbn = isbn;
        this.clasificacionDewey = clasificacionDewey;
        this.estanteria = estanteria;
        this.idioma = idioma;
    }
    
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getClasificacionDewey() {
        return clasificacionDewey;
    }

    public void setClasificacionDewey(String clasificacionDewey) {
        this.clasificacionDewey = clasificacionDewey;
    }

    public String getEstanteria() {
        return estanteria;
    }

    public void setEstanteria(String estanteria) {
        this.estanteria = estanteria;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    @Override
    public String toString() {
        return "FichaBibliografica{" +
               "id=" + getId() + 
               ", eliminado=" + getEliminado() + 
               ", isbn='" + isbn + '\'' +
               ", clasificacionDewey='" + clasificacionDewey + '\'' +
               ", estanteria='" + estanteria + '\'' +
               ", idioma='" + idioma + '\'' +
               '}';
    }
}
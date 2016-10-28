package pt.europeia.bibloteca.models;

/**
 * Created by bruno on 21/10/2016.
 */

public class Livro {
    private int ID;
    private String titulo;
    private String autor;
    private String editora;
    private String isbn;
    private String capa;
    private int data;

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public Livro(int ID, String titulo, String autor, String editora, String isbn, String capa, int data) {
        this.ID = ID;
        this.titulo = titulo;
        this.autor = autor;
        this.editora = editora;
        this.isbn = isbn;
        this.capa = capa;
        this.data = data;

    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getCapa() {
        return capa;
    }

    public void setCapa(String capa) {
        this.capa = capa;
    }
}

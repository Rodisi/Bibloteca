package pt.europeia.bibloteca.models;

import java.util.Date;

/**
 * Created by Bruno on 11/11/2016.
 *
 * Class that extends {@link Livro}. Contains a recommended {@link Livro} so it also includes the {@link User} who recommended it.
 */

public class LivroRecomendado extends Livro {

    private User userOrigem;

    public LivroRecomendado(int ID, String titulo, String autor, String editora, String isbn, String capa, Date data, User userOrigem) {
        super(ID, titulo, autor, editora, isbn, capa, data);
        this.userOrigem = userOrigem;
    }

    public User getUserOrigem() {
        return userOrigem;
    }

    public void setUserOrigem(User userOrigem) {
        this.userOrigem = userOrigem;
    }
}

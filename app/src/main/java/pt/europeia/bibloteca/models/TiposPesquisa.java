package pt.europeia.bibloteca.models;

/**
 * Created by Bruno on 11/11/2016.
 * Enumeration for types of search
 */

public enum TiposPesquisa {
    tirulo("titulo"),
    autor("autor"),
    editora("editora");

    private final String text;

    private TiposPesquisa(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }


}

package dao;

import java.util.List;

public interface Dao<T> {
    /**
     * Renvoie la liste de tous.<br>
     *
     * @return la liste de tous les objets
     */
    List<T> getAll();

    /**
     * Ajoute un objet à l'ensemble.<br>

     * @param t objet à ajouter
     */
    void add(T t);


    /**
     * Ajoute un élément à l'ensemble.<br>

     * @param name nom de l'élément à ajouter
     * @return objet dont le nom est donné en paramètre
     */
    T findByName(String name);
}

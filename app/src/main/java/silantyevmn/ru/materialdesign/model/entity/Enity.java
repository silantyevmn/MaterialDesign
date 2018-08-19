package silantyevmn.ru.materialdesign.model.entity;

import java.io.Serializable;

/**
 * Created by silan on 18.08.2018.
 */

public class Enity implements Serializable{
    private int id;
    private String name;

    public Enity(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}

package silantyevmn.ru.materialdesign.model.entity;

import java.io.Serializable;

/**
 * Created by silan on 18.08.2018.
 */

public class Theme implements Serializable{
    private int id;
    private int color;
    private String name;

    public Theme(int id,String name,int color) {
        this.id=id;
        this.name=name;
        this.color=color;
    }

    public int getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}

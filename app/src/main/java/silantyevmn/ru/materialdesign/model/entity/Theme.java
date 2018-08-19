package silantyevmn.ru.materialdesign.model.entity;

import java.io.Serializable;

import silantyevmn.ru.materialdesign.model.entity.Enity;

/**
 * Created by silan on 18.08.2018.
 */

public class Theme extends Enity implements Serializable{
    private int color;

    public Theme(int id, String name,int color) {
        super(id, name);
        this.color=color;
    }

    public int getColor() {
        return color;
    }
}

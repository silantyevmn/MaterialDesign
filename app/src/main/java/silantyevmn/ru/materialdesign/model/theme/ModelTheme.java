package silantyevmn.ru.materialdesign.model.theme;

import java.util.List;

/**
 * Created by silan on 18.08.2018.
 */

public class ModelTheme implements IModelTheme{
    private static ModelTheme modelTheme;
    private IModelTheme iModel;

    public ModelTheme(){
        iModel=new ThemeEmmiter();
    }

    public List<Theme> getList() {
        return iModel.getList();
    }

    public static ModelTheme getInstance(){
        return modelTheme;
    }

    public static void init() {
        if(modelTheme==null){
            modelTheme=new ModelTheme();
        }
    }

}

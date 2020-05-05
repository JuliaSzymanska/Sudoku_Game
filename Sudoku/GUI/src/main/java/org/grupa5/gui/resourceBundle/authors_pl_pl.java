package org.grupa5.gui.resourceBundle;

import java.util.ListResourceBundle;

public class authors_pl_pl extends ListResourceBundle {

    private static final Object[][] contents = {
            {"Authors: ", "Autorzy: "},
            {"Julia Szymanska", "Julia Szymańska"},
            {"Przemyslaw Zdrzalik", "Przemysław Zdrzalik"}
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
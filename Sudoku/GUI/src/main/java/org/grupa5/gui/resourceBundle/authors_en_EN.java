package org.grupa5.gui.resourceBundle;

import java.util.ListResourceBundle;

public class authors_en_EN extends ListResourceBundle {

    private static final Object[][] contents = {
            {"Authors: ", "Authors: "},
            {"Julia Szymanska", "Julia Szymanska"},
            {"Przemyslaw Zdrzalik", "Przemyslaw Zdrzalik"}
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}

package org.grupa5.sudoku.exceptions;

public class GetException extends Exception {

    // TODO: 12.05.2020 niestety jeszcze internacjonalizacja ehhh

    public GetException(String s) {
        super(s);
    }

    public GetException(String s, Exception e) {
        super(s, e);
    }


    // TODO: 12.05.2020 zrobic to dla wszysktkich klas z wyjatkami
    //  https://stackoverflow.com/a/24989341
    @Override
    public String getLocalizedMessage() {
        // to jest inaczej ale tak zostawiam narazie
        return super.getLocalizedMessage();
    }
}

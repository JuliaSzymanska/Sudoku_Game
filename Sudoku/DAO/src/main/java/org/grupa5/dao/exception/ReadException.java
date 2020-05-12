package org.grupa5.dao.exception;

// TODO: 12.05.2020 niestety jeszcze internacjonalizacja ehhh
public class ReadException extends Exception {
    public ReadException(String errorMessage, Exception exception) {
        super(errorMessage, exception);
    }

    public ReadException(String errorMessage) {
        super(errorMessage);
    }

    // TODO: 12.05.2020 zrobic to dla wszysktkich klas z wyjatkami
    //  https://stackoverflow.com/a/24989341
    @Override
    public String getLocalizedMessage() {
        // to jest inaczej ale tak zostawiam narazie
        return super.getLocalizedMessage();

//        ResourceBundle labels = ResourceBundle.getBundle("loc.exc.test.message");
//
//        private static final long serialVersionUID = 1L;
//        public MyLocalizedThrowable(String messageKey) {
//            super(messageKey);
//        }
//
//        public String getLocalizedMessage() {
//            return labels.getString(getMessage());
//        }

    }
}

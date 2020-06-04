package org.grupa5.dao;

import java.sql.*;

import org.grupa5.exceptions.*;

import org.grupa5.sudoku.SudokuBoard;

class JdbcSudokuBoardDao implements Dao<SudokuBoard> {
    private Connection connection;

    private static String DB_URL = "jdbc:derby://localhost:1527/dbname;create=true";
    private static final String DB_USER = "user";
    private static final String DB_PASS = "1";
    private static final String DB_DRIVER = "org.apache.derby.jdbc.ClientDriver";
    private static final String DBRead = "DBRead";
    private static final String DBWrite = "DBWrite";
    private static final String generic = "daoGeneric";

    private final String fileName;
    private final String tableName = "boards";
    private final String boardId = "board_id";
    private final String boardFields = "fields";
    private final String boardFieldsAreEditable = "editable";

    JdbcSudokuBoardDao(String fileName) throws DaoException {
        this.fileName = "'" + fileName + "'";
        this.createConnection();
    }

    JdbcSudokuBoardDao(String fileName, String url) throws DaoException {
        DB_URL = url;
        this.fileName = "'" + fileName + "'";
        this.createConnection();
    }

    private void createConnection() throws DaoException {
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            //this.dropTable();
        } catch (SQLException e) {
            throw new DaoException(generic, e);
        }
    }

    @Override
    public SudokuBoard read() throws DaoReadException {
        String query = "SELECT * FROM " + tableName + " WHERE " + boardId + " = " + fileName;
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)
        ) {
            Class.forName(DB_DRIVER);
            SudokuBoard sudokuBoard = new SudokuBoard();
            if (isTableExist(false)) {
                String id = "";
                String fields = "";
                String isEditable = "";
                while (resultSet.next()) {
                    id = resultSet.getString(boardId);
                    fields = resultSet.getString(boardFields);
                    isEditable = resultSet.getString(boardFieldsAreEditable);
                }
                int counterX = 0;
                int counterY = 0;
                for (char c : fields.toCharArray()) {
                    sudokuBoard.set(counterX, counterY, Integer.parseInt(String.valueOf(c)));
                    if (counterY == 8) {
                        counterX += 1;
                        counterY = 0;
                    } else {
                        counterY += 1;
                    }
                }
                counterX = 0;
                counterY = 0;
                for (char c : isEditable.toCharArray()) {
                    int toBool = Integer.parseInt(String.valueOf(c));
                    if (toBool == 0) {
                        sudokuBoard.getField(counterX, counterY)
                                .setEditable(false);
                    } else {
                        sudokuBoard.getField(counterX, counterY)
                                .setEditable(true);
                    }
                    if (counterY == 8) {
                        counterX += 1;
                        counterY = 0;
                    } else {
                        counterY += 1;
                    }
                }
            } else {
                throw new DaoReadException(DBRead);
            }
            return sudokuBoard;
        } catch (ClassNotFoundException | SQLException | DaoException e) {
            throw new DaoReadException(DBRead, e);
        }
    }

    @Override
    public void write(SudokuBoard sudokuBoard) throws DaoWriteException {
        String sudoku = "'";
        String areEditable = "'";
        try (Statement statement = connection.createStatement()) {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    sudoku += Integer.toString(sudokuBoard.get(i, j));
                    areEditable +=
                            Integer.toString(sudokuBoard.getField(i, j).isEditable() ? 1 : 0);
                }
            }
            sudoku += "'";
            areEditable += "'";
            String query = "INSERT INTO " + tableName + "(" + boardId + ", "
                    + boardFields + ", " + boardFieldsAreEditable + ")"
                    + " VALUES (" + fileName + ", " + sudoku + ", " + areEditable + ")";
            Class.forName(DB_DRIVER);
            if (isTableExist(true)) {
                statement.executeUpdate(query);
            }
        } catch (GetException | ClassNotFoundException | SQLException | DaoException e) {
            throw new DaoWriteException(DBWrite, e);
        }
    }

    private void dropTable() throws SQLException {
        Statement stm = connection.createStatement();
        stm.execute("DROP TABLE " + tableName);
    }

    private boolean isTableExist(boolean type) throws SQLException, DaoException {
        if (connection != null) {
            DatabaseMetaData dbmd = connection.getMetaData();
            try (ResultSet rs = dbmd.getTables(null, null, tableName.toUpperCase(), null);
                 Statement statement = connection.createStatement()) {
                boolean rsbool = rs.next();
                if (!rsbool && type) {
                    statement.executeUpdate("CREATE TABLE " + tableName
                            + "( " + boardId + " VARCHAR(20) PRIMARY KEY, "
                            + boardFields + " CHAR(81),"
                            + boardFieldsAreEditable + " CHAR(81))");
                } else if (!rsbool && !type) {
                    return false;
                }
                return true;
            } catch (Exception e) {
                throw new DaoException(generic, e);
            }
        }
        return false;
    }

    @Override
    public void close() throws DaoException {
        try {
            connection.close();
        } catch (SQLException throwables) {
            throw new DaoException(generic, throwables);
        }
    }
}

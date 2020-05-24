package org.grupa5.dao;

import java.sql.*;

import org.grupa5.exceptions.*;

import org.grupa5.sudoku.SudokuBoard;

// TODO: 20.05.2020 dac jeden typ wyjaktu dla dao albo zrobic osobny modul
class JdbcSudokuBoardDao implements Dao<SudokuBoard> {

    //obiekt tworzący połączenie z bazą danych.
    private Connection connection;
    //obiekt pozwalający tworzyć nowe wyrażenia SQL
    private Statement statement;
    private ResultSet resultSet = null;

    private static final String DB_URL = "jdbc:derby://localhost:1527/dbname;create=true";
    //jdbc:derby://localhost/c:/temp/db";
    private static final String DB_USER = "user";
    private static final String DB_PASS = "1";
    private static final String DB_DRIVER = "org.apache.derby.jdbc.ClientDriver";

    private final String fileName;
    private final String tableName = "boards";
    private final String boardId = "board_id";
    private final String boardFields = "fields";


    JdbcSudokuBoardDao(String fileName) {
        this.fileName = "'" + fileName + "'";
    }

    @Override
    public SudokuBoard read() throws DaoReadException {
        String query = "SELECT * FROM " + tableName + " WHERE " + boardId + " = " + fileName;
        try {
            Class.forName(DB_DRIVER).newInstance();
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            // TODO: 20.05.2020 statment w try with resources, albo w finally
            statement = connection.createStatement();
            SudokuBoard sudokuBoard = new SudokuBoard();
            if (isTableExist(false)) {
                // TODO: 20.05.2020 zamknac w finally,
                resultSet = statement.executeQuery(query);
                String id = "";
                String fields = "";
                while (resultSet.next()) {
                    id = resultSet.getString(boardId);
                    fields = resultSet.getString(boardFields);
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
            } else {
                throw new DaoReadException("DBRead");
            }
            // TODO: 20.05.2020 finally tutaj koniecznie
            connection.close();
            statement.close();
            return sudokuBoard;
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException
                | SQLException | SetException e) {
            throw new DaoReadException("DBRead", e);
        }
    }

    @Override
    public void write(SudokuBoard sudokuBoard) throws DaoWriteException {
        String sudoku = "'";
        try {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    sudoku += Integer.toString(sudokuBoard.get(i, j));
                }
            }

            sudoku += "'";
            // TODO: 20.05.2020 scommitowac transakcje
            String query = "INSERT INTO " + tableName + "(" + boardId + ", " + boardFields + ")"
                    + " VALUES (" + fileName + ", " + sudoku + ")";

            Class.forName(DB_DRIVER).newInstance();
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            statement = connection.createStatement();
            if (isTableExist(true)) {
                statement.executeUpdate(query);
            }
            // TODO: 20.05.2020 finally tutaj koniecznie
            statement.close();
            connection.close();

        } catch (GetException | InstantiationException | IllegalAccessException
                | ClassNotFoundException | SQLException e) {
            throw new DaoWriteException("DBWrite", e);
        }
    }

    private boolean isTableExist(boolean type) throws SQLException {
        // dla type = false znaczy ze odczyt, jak true zapis
        if (connection != null) {
            DatabaseMetaData dbmd = connection.getMetaData();
            ResultSet rs = dbmd.getTables(null, null, tableName.toUpperCase(), null);
            boolean rsbool = rs.next();
            if (!rsbool && type) {
                statement.executeUpdate("CREATE TABLE " + tableName
                        + "( " + boardId + " VARCHAR(20) PRIMARY KEY, "
                        + boardFields + " CHAR(81))");
            } else if (!rsbool && !type) {
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public void close() throws Exception {

    }
}

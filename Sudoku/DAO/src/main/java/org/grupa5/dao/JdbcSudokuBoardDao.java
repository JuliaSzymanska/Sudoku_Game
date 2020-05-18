package org.grupa5.dao;

import java.sql.*;

import org.grupa5.dao.exception.ReadException;
import org.grupa5.dao.exception.WriteException;
import org.grupa5.sudoku.SudokuBoard;
import org.grupa5.sudoku.exceptions.GetException;
import org.grupa5.sudoku.exceptions.SetException;

class JdbcSudokuBoardDao implements Dao<SudokuBoard> {
    private String fileName;
    private static final String DBURL = "jdbc:derby://localhost:1527/dbname;create=true";
    //jdbc:derby://localhost/c:/temp/db";
    private static final String DBUSER = "user";
    private static final String DBPASS = "1";
    private static final String DBDRIVER = "org.apache.derby.jdbc.ClientDriver";
    //obiekt tworzący połączenie z bazą danych.
    private Connection connection;
    //obiekt pozwalający tworzyć nowe wyrażenia SQL
    private Statement statement;
    ResultSet resultSet = null;
    private String tableName = "boards";
    private String boardId = "board_id";
    private String boardFields = "fields";


    JdbcSudokuBoardDao(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public SudokuBoard read() throws ReadException {
        String query = "SELECT * FROM " + tableName + " WHERE " + boardId + " = " + fileName;
        try {
            Class.forName(DBDRIVER).newInstance();
            connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
            statement = connection.createStatement();
            SudokuBoard sudokuBoard = new SudokuBoard();
            if (isTableExist(false)) {
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
            }
            statement.close();
            connection.close();
            return sudokuBoard;
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException
                | SQLException | SetException e) {
            // TODO wyjatki
            e.printStackTrace();
        }
        // TODO: 18.05.2020 naprawic to zeby nie bylo null
        return null;
    }

    @Override
    public void write(SudokuBoard sudokuBoard) throws WriteException {
        String sudoku = "'";
        try {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    sudoku += Integer.toString(sudokuBoard.get(i, j));
                }
            }
        } catch (GetException e) {
            e.printStackTrace();
        }
        sudoku += "'";
        String query = "INSERT INTO " + tableName + "(" + boardId + ", " + boardFields + ")"
                + " VALUES (" + fileName + ", " + sudoku + ")";
        try {
            Class.forName(DBDRIVER).newInstance();
            connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
            statement = connection.createStatement();
            if (isTableExist(true)) {
                statement.executeUpdate(query);
            }
            statement.close();
            connection.close();
        } catch (InstantiationException | IllegalAccessException
                | ClassNotFoundException | SQLException e) {
            // TODO wyjatki
            e.printStackTrace();
        }
    }

    private void getAll() {

    }

    private boolean isTableExist(boolean type) throws SQLException {
        // TODO: 18.05.2020 zrobic porpawnie zwracanie zeby mialo sens
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

package org.grupa5.dao;

import java.sql.*;

import org.grupa5.exceptions.*;

import org.grupa5.sudoku.SudokuBoard;

class JdbcSudokuBoardDao implements Dao<SudokuBoard> {
    // TODO: 24.05.2020 nie wiem czy statment jest potrzebne
    //obiekt tworzący połączenie z bazą danych.
    private Connection connection;
    //obiekt pozwalający tworzyć nowe wyrażenia SQL
    private Statement statement;

    // TODO: 26.05.2020 Moze tworzmy w memory?
    // private static String DB_URL = "jdbc:derby://localhost:1527/dbname;create=true";
    private static String DB_URL = "jdbc:derby:memory:myDb;create=true";
    private static final String DB_USER = "user";
    private static final String DB_PASS = "1";
    private static final String DB_DRIVER = "org.apache.derby.jdbc.ClientDriver";
    private static final String DBRead = "DBRead";
    private static final String DBWrite = "DBWrite";

    private final String fileName;
    private final String tableName = "boards";
    private final String boardId = "board_id";
    private final String boardFields = "fields";

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
        } catch (SQLException e) {
            throw new DaoException(e.getLocalizedMessage());
        }
    }

    @Override
    public SudokuBoard read() throws DaoReadException {
        String query = "SELECT * FROM " + tableName + " WHERE " + boardId + " = " + fileName;
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)
        ) {
            //ladowanie klasy sterownika do pamieci
            Class.forName(DB_DRIVER);
            SudokuBoard sudokuBoard = new SudokuBoard();
            if (isTableExist(false)) {
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
        try (Statement statement = connection.createStatement()) {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    sudoku += Integer.toString(sudokuBoard.get(i, j));
                }
            }
            sudoku += "'";
            // TODO: 20.05.2020 mozna commitowac transakcje ale \
            //  wlasciwie to jak mamy jedno insert to nie trzeba
            String query = "INSERT INTO " + tableName + "(" + boardId + ", " + boardFields + ")"
                    + " VALUES (" + fileName + ", " + sudoku + ")";
            //ladowanie klasy sterownika do pamieci
            Class.forName(DB_DRIVER);
            // TODO: 24.05.2020 jesli istnieje juz w tablicy to trzeba zorbic update
            if (isTableExist(true)) {
                statement.executeUpdate(query);
            }
        } catch (GetException | ClassNotFoundException | SQLException | DaoException e) {
            throw new DaoWriteException(DBWrite, e);
        }
    }

    private boolean isTableExist(boolean type) throws SQLException, DaoException {
        if (connection != null) {
            DatabaseMetaData dbmd = connection.getMetaData();
            try (ResultSet rs = dbmd.getTables(null, null, tableName.toUpperCase(), null)) {
                boolean rsbool = rs.next();
                if (!rsbool && type) {
                    statement.executeUpdate("CREATE TABLE " + tableName
                            + "( " + boardId + " VARCHAR(20) PRIMARY KEY, "
                            + boardFields + " CHAR(81))");
                } else if (!rsbool && !type) {
                    return false;
                }
                return true;
                // TODO: 24.05.2020 null pointer exception
            } catch (Exception e) {
                throw new DaoException(e.getLocalizedMessage());
            }
        }
        return false;
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }
}

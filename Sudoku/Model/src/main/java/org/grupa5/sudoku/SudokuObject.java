package org.grupa5.sudoku;

import static org.grupa5.sudoku.SudokuBoard.SUDOKU_DIMENSIONS;

import java.io.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


/**
 * This class can represent three types of objects in sudoku:
 * Row,
 * Column,
 * Box.
 */

public class SudokuObject implements Serializable, Cloneable {

    private List<SudokuField> object;

    /**
     * Constructor
     * init the field 'object' to this param.
     */

    public SudokuObject(List<SudokuField> table) {
        if (table.size() != SUDOKU_DIMENSIONS) {
            throw new IllegalArgumentException("List size has to be equal to "
                    + Integer.toString(SUDOKU_DIMENSIONS));
        }
        this.object = Arrays.asList(new SudokuField[SUDOKU_DIMENSIONS]);
        for (int i = 0; i < SUDOKU_DIMENSIONS; i++) {
            this.object.set(i, table.get(i));
        }
    }

    /**
     * Verifies whether the structure is valid.
     *
     * @return False if the structure has duplicate numbers otherwise return True.
     */

    public boolean verify() {
        Set<Integer> setNumbers = new HashSet<Integer>();
        for (SudokuField x : object) {
            if (!setNumbers.add(x.getFieldValue()) && x.getFieldValue() != 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (!(o instanceof SudokuObject)) {
            return false;
        }
        SudokuObject that = (SudokuObject) o;
        return new EqualsBuilder().append(this.object, that.object).isEquals();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

        @Override
    public int hashCode() {
        return new HashCodeBuilder().append(object).toHashCode();
    }

    public SudokuObject clone() throws CloneNotSupportedException{
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try{
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(this);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            return (SudokuObject) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    // TODO: Usunac main i klase getFirst
//    public SudokuField getFirst(){
//        return object.get(0);
//    }

//    public static void main(String[] args) throws CloneNotSupportedException {
//        List<SudokuField> list1 = Arrays.asList(new SudokuField[9]);
//        for (int i = 0; i < 9; i++) {
//            list1.set(i, new SudokuField(i + 1));
//        }
//        SudokuObject sudoku1 = new SudokuObject(list1);
//        SudokuObject sudoku2 = sudoku1.clone();
//        System.out.print(sudoku1.toString());
//        System.out.print(sudoku2.toString());
//        System.out.print(sudoku1.getFirst() == sudoku2.getFirst());
//    }

}
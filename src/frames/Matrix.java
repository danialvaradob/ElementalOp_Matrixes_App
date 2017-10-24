/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frames;

import java.util.StringTokenizer;

/**
 *
 * @author danielalvarado
 */
public class Matrix {
    private double[][] matrix;
    private int numberOfRows; 
    private int numberOfColumns;
    
    public Matrix(int nRows,int nCols) {
        numberOfRows = nRows;
        numberOfColumns = nCols;
        this.createEmptyMatrix();
        
    }
    
    /**
     * Creates a matrix full of 0s 
     */
    public void createEmptyMatrix() {
        for (int i = 0; i < numberOfRows; i++) {
                    for (int j = 0; j < numberOfColumns; j++) {
                        matrix[i][j] = 0;              
                    }
                }
    }
    public void modifyPosition(int row,int col, float newValue) {
        this.matrix[row][col] = newValue;
    }
    
    public void tokenizeString() {
        
        StringTokenizer st1 = new StringTokenizer("2F2 + 4/3F3");
        for (int i = 1; st1.hasMoreTokens(); i++) {
               String stringPart = st1.nextToken();

               for (int j =0; j < stringPart.length(); j++ ){
                   char a_char = st1.nextToken().charAt(0);
               }
           }
    }
    
    public void multiplyRow(int _rowNumber, double _multiplier) {
        for (int i =0; i < this.numberOfColumns; i++) {
            matrix[_rowNumber-1][i] = matrix[_rowNumber-1][i] * _multiplier;
        } 
    }
    public void swapRows(int _rowN1,int _rowN2) {
        for (int i =0; i < this.numberOfColumns; i++) {
            double elementR1 = matrix[_rowN1-1][i];
            double elementR2 = matrix[_rowN2-1][i];
            matrix[_rowN1-1][i] = elementR2;
            matrix[_rowN2-1][i] = elementR1;
        } 
    }
    private void sumRows(int _rowN1,int _rowN2, double multN1, double multN2) {
        for (int i =0; i < this.numberOfColumns; i++) {
            matrix[_rowN1-1][i] = multN1*(matrix[_rowN1-1][i]) + 
                    multN2*(matrix[_rowN2-1][i]);
        }
    }
    private void substractRows(int _rowN1,int _rowN2, double multN1, double multN2) {
        for (int i =0; i < this.numberOfColumns; i++) {
            matrix[_rowN1-1][i] = multN1*(matrix[_rowN1-1][i]) - 
                    multN2*(matrix[_rowN2-1][i]);
        }
    }
    public void operateRows(int _rowN1,int _rowN2, char _op, double multN1, double multN2) {
        switch (_op) {
            case '+':
                this.sumRows(_rowN1, _rowN2,multN1,multN2);
                break;
            case '-':
                this.substractRows(_rowN1, _rowN2,multN1,multN2);
                break;
        }
    }
}

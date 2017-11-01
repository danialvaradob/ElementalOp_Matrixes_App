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
    private double[][] matrixBeforeChange;
    private double[][] matrix;
    private int numberOfRows; 
    private int numberOfColumns;
    
    public Matrix(int j,int i) {
        numberOfRows = i;
        numberOfColumns = j;
        this.createEmptyMatrix();
        
    }
    
    
    public void copyMatrix() {
        matrixBeforeChange = matrix;
    }
    
    /**
     * Method the changes a specified position of  the double[][] array
     * @param i row number
     * @param j column number 
     * @param element double, new element in that position
     */
    public void modifyPosition(int i, int j, double element) {
        matrix[i][j] = element;
    }
    
    
    public boolean rowChanged(int _row) {
        for (int i = 0; i < this.numberOfColumns; i++) {
            if (matrix[_row][i] != matrixBeforeChange[_row][i]) {
                return true;
            }
        }
        return false;
    }
    /**
     * Creates a matrix full of 0s 
     */
    public void createEmptyMatrix() {
        matrix = new double[numberOfRows][numberOfColumns];
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
    public static String fixedLengthString(String string) {
        return String.format("%1$-8s", string);
        //return String.format("%1$"+6+ "s", string);
        
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
    public double[][] getMatrix() {
        return this.matrix;
    }
    public String getMatrixString() {
        String matrixString = "";
        for (int i = 0; i < numberOfRows; i++) {
            String lineS = "";        
            for (int j = 0; j < numberOfColumns; j++) {
                    //String elementS = Double.toString(matrix[i][j]);
                    String elementS;
                    if (matrix[i][j] == 0) {
                        elementS = "0";
                    } else {
                    elementS = toFraction(matrix[i][j],10);
                    }
                    lineS += fixedLengthString(elementS);
            }
            matrixString += lineS + "\n";
        }
    return matrixString;
    }
    
    public String getRowString(int rowNumber){
        String lineS = "";  
        for (int i = 0; i < numberOfRows; i++) {
            if (i == rowNumber-1){
                for (int j = 0; j < numberOfColumns; j++) {
                    //String elementS = Double.toString(matrix[i][j]);
                    String elementS;
                    if (matrix[i][j] == 0) {
                        elementS = "0";
                    } else {
                    elementS = toFraction(matrix[i][j],10);
                    }
                    lineS += fixedLengthString(elementS);
                }
            }
        }
    return lineS;
    }
    
    public String getElementString(int _i, int _j) {
        String e = "";
        for (int i = 0; i < numberOfRows; i++) {
                    for (int j = 0; j < numberOfColumns; j++) {
                        if (j == _j && i == _i) {
                            if (matrix[i][j] == 0) {
                                e = "0";
                            } else {
                                e = toFraction(matrix[i][j],10);
                    }
                        }              
                    }
                }
        return e;
    }
    
    public static String toFraction(double d, int factor) {
        StringBuilder sb = new StringBuilder();
        if (d < 0) {
            sb.append('-');
            d = -d;
        }
        long l = (long) d;
        if (l != 0) sb.append(l);
        d -= l;
        double error = Math.abs(d);
        int bestDenominator = 1;
        for(int i=2;i<=factor;i++) {
            double error2 = Math.abs(d - (double) Math.round(d * i) / i);
            if (error2 < error) {
                error = error2;
                bestDenominator = i;
            }
        }
        if (bestDenominator > 1)
            sb.append(' ').append(Math.round(d * bestDenominator)).append('/') .append(bestDenominator);
        return sb.toString();
        }
}


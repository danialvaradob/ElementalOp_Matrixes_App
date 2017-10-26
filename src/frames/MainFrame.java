/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frames;

import static frames.Instruction.BAD_INPUT;
import static frames.Instruction.OPERATE_ROWS;
import static frames.Instruction.SWAP_ROWS;
import java.awt.GridBagConstraints;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author danielalvarado
 */
public class MainFrame extends javax.swing.JFrame {

    /**
     * Creates new form NewJFrame
     */
    
    public JTextField[][] textEditsArray;
    public Matrix matrix;
    public boolean matrixCreated = false;
    public int operationCounter = 0;
    private int nrow = 0;
    private int ncol = 0;
    
    public MainFrame() {
        initComponents();
        init2();
        //createGrid(1,5);
        
        
        
        //hideMatrix();
        String input = "1/3F2 + 100/200F1";
        String i2 = "7667F2";
        /*
        Scanner s = new Scanner(input).useDelimiter("\\s*+\\s");
        while (s.hasNext()) {
            System.out.println(s.next());
        }
        System.out.println(parse("15"));
        */
        input = input.replaceAll("\\s+","");
        //input = input.replaceAll("\\+","X");
        
        if (input.contains("+")) System.out.println("SI CONTIENE");
        String[] rat = input.split("\\+");
        //String[] rat2 = i2.split("F");
        String sss = rat[0];
        System.out.println(sss);
        //System.out.println(parse(rat2[0]));
        
    }
    
    private void init2(){
        applyBtn.setEnabled(false);
        
    }
    
    
    private void createGrid(int _rows, int _cols) {
        this.textEditsArray = new JTextField[_rows][_cols];
        for (int i =0; i < _rows; i ++ ) {
            for (int j = 0; j < _cols;j++) {
                this.textEditsArray[i][j] = new javax.swing.JTextField();
                this.textEditsArray[i][j].setText("0");
                GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = j;
                gridBagConstraints.gridy = i;
                gridBagConstraints.ipadx = 39;
                gridBagConstraints.ipady = 13;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
                gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
                jPanel1.add(this.textEditsArray[i][j], gridBagConstraints);
            }
            
        }
        jPanel1.revalidate();
        jPanel1.repaint();
        matrix = new Matrix(_cols,_rows);
    }
    
    private double parse(String ratio) {
        if (ratio.contains("/")) {
            String[] rat = ratio.split("/");
            return Double.parseDouble(rat[0]) / Double.parseDouble(rat[1]);
        } else {
            return Double.parseDouble(ratio);
        }
    }
    
    private Instruction verifyString(String _string) {
        
        int fChars = 0;
        int operators = 0;
        int punctuations = 0;
        Instruction state = BAD_INPUT;
        int rowNumber1Mult,rowNumber2Mult,rowN1,rowN2;
        
        StringTokenizer st1 = new StringTokenizer("2F2 + 4/3F3");
        for (int i = 1; st1.hasMoreTokens(); i++) {
            String stringPart = st1.nextToken();
            
            for (int j =0; j < stringPart.length(); j++ ){
                char c = stringPart.charAt(j);
                switch (c) {
                    case 'f':
                    case 'F':
                        if (fChars > 2) return BAD_INPUT;
                        fChars++;
                        if (j != stringPart.length() - 1) {
                            char c2 = stringPart.charAt(j + 1);
                            try {
                                int n = Character.getNumericValue(c2);
                                if (fChars == 1) rowN1 = n;
                                if (fChars == 2) rowN2 = n;
                            } catch  (Exception e) {
                                return BAD_INPUT;
                            } 
                            
                        }
                        break;
                    case '+':
                    case '*':
                    case '-':
                        if (operators > 1) return BAD_INPUT;
                        operators++;
                        state = OPERATE_ROWS;
                        break;
                    case ';':
                    case ',':
                        if (punctuations > 1) return BAD_INPUT;
                        punctuations++;
                        state = SWAP_ROWS;
                        break;
                    case '/':
                        if (i == 0) return BAD_INPUT;
                        
                    default:
                        break;
                }
                
                
                
                
                
            }
        }
        return state;
    }
    private void swapRowsOp(Matrix _matrix, String _string) {
        _string = _string.replaceAll("\\s+","");
        boolean first = true;
        int rowN1=1 ,rowN2 = 1;
        StringTokenizer st1 = new StringTokenizer(_string);
        for (int i = 1; st1.hasMoreTokens(); i++) {
            String stringPart = st1.nextToken();
            for (int j =0; j < stringPart.length(); j++ ){
                char c = stringPart.charAt(j);
                switch (c) {
                    case '1':
                    case '2':
                    case '3':
                    case '4':
                    case '5':
                        int n = Character.getNumericValue(c);
                        if (first) rowN1 = n;
                        else rowN2 = n;
                }
            }
        }
        _matrix.swapRows(rowN1, rowN2);
    }
    private void applyOperation(Matrix _matrix, String _string) {
        //makes every f and F
        _string = _string.replaceAll("\\s+","");
        String input = _string.replaceAll("f", "F;");
        double mult1,mult2;
        if (input.contains("+")) {
            String[] rat = _string.split("\\+");
            
            //First row op
            String firstRow = rat[0];
            String[] frs = firstRow.split("F");
            //multiplier
            try {
                mult1 = parse(frs[0]);
            }catch (Exception e) {
                mult1 = 1;
            }
            //first row number
            int rowN1 = (int)parse(frs[1]);
            //----------------------------------------
            //Second row p[
            String secondRow = rat[1];
            String[] srs = secondRow.split("F");
            //multiplier
            try {
                mult2 = parse(srs[0]);
            }catch (Exception e2) {
                mult2 = 1;
            }
            //second row number
            int rowN2 = (int)parse(srs[1]);
            _matrix.operateRows(rowN1, rowN2, '+', mult1, mult2);
            
            
        } else if (_string.contains("-")) {
            String[] rat = _string.split("-");
            
             //First row op
            String firstRow = rat[0];
            String[] frs = firstRow.split("F");
            //multiplier
            try {
                mult1 = parse(frs[0]);
            }catch (Exception e) {
                mult1 = 1;
            }
            //first row number
            int rowN1 = (int)parse(frs[1]);
            //----------------------------------------
            //Second row p[
            String secondRow = rat[1];
            String[] srs = secondRow.split("F");
            //multiplier
            try {
                mult2 = parse(srs[0]);
            }catch (Exception e2) {
                mult2 = 1;
            }
            //second row number
            int rowN2 = (int)parse(srs[1]);
            _matrix.operateRows(rowN1, rowN2, '-', mult1, mult2);
        }
           
    }
    private boolean number1to5(char c) {
        switch (c) {
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
                return true;
        }
        return false;
    }
    private void msgbox(String s){
        JOptionPane.showMessageDialog(null, s);
    }
    
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        mTextEdit = new javax.swing.JTextField();
        nTextEdit = new javax.swing.JTextField();
        createMatrixBtn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        operationTextEdit = new javax.swing.JTextField();
        applyBtn = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Operaciones Elementales en Matrices");

        jPanel1.setLayout(new java.awt.GridBagLayout());

        nTextEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nTextEditActionPerformed(evt);
            }
        });

        createMatrixBtn.setText("Crear Matriz");
        createMatrixBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                createMatrixBtnMouseClicked(evt);
            }
        });
        createMatrixBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createMatrixBtnActionPerformed(evt);
            }
        });

        jLabel1.setText("Matriz m x n");

        jLabel2.setText("x");

        applyBtn.setText("Aplicar");
        applyBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                applyBtnMouseClicked(evt);
            }
        });

        jButton2.setText("Deshacer");

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.setText("Eliminacion\n--------------------------\nLas entradas de las Matrices solo\npueden ser enteros o fracciones\npor ejemplo:\n1/3, -1/4..\n\nEjemplos de Operaciones\n--------------------------\n\n-Se modifica la 1era fila\ndigitada\n>f2,f3 o f1;2 \n> 2f2 o 2F1\n>");
        jScrollPane1.setViewportView(jTextArea1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(mTextEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(nTextEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(createMatrixBtn))))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(operationTextEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(applyBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING)))))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(mTextEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nTextEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(createMatrixBtn)
                            .addComponent(jLabel2))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addComponent(operationTextEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(applyBtn)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton2))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void createMatrixBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createMatrixBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_createMatrixBtnActionPerformed

    private void nTextEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nTextEditActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nTextEditActionPerformed

    private void createMatrixBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_createMatrixBtnMouseClicked
        boolean error_flag = false;
        StringTokenizer nToken = new StringTokenizer(nTextEdit.getText());
        for (int i = 1; nToken.hasMoreTokens(); i++) {
            String stringPart = nToken.nextToken();
            for (int j =0; j < stringPart.length(); j++ ){
                char c = stringPart.charAt(j);
                if (!number1to5(c)) {error_flag = true; break;}
                if (j==1) {error_flag = true; break;}
            }
            if (error_flag) break;
        } 
        if (!error_flag) {
            StringTokenizer mToken = new StringTokenizer(mTextEdit.getText());
            for (int i = 1; mToken.hasMoreTokens(); i++) {
            String stringPart = mToken.nextToken();
            for (int j =0; j < stringPart.length(); j++ ){
                char c = stringPart.charAt(j);
                if (!number1to5(c)) {error_flag = true; break;}
                if (j==1) {error_flag = true; break;}
                }
            if (error_flag) break;
            }
        }
        
        if (error_flag) msgbox("Por favor verifique los datos ingresados");
        else {
            int n = Integer.valueOf(nTextEdit.getText().trim());
            int m = Integer.valueOf(mTextEdit.getText().trim());
            
            //createGrid(m,n);
            //this.nrow = m;
            //this.ncol = n;
            createGrid(m,n);
            this.nrow = m;
            this.ncol = n;
            
            createMatrixBtn.setEnabled(false);
            nTextEdit.setEnabled(false);
            mTextEdit.setEnabled(false);
            applyBtn.setEnabled(true);
            
        }
    }//GEN-LAST:event_createMatrixBtnMouseClicked

    private void applyBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_applyBtnMouseClicked
        // TODO add your handling code here:
        
        //System.out.println(operationTextEdit.getText().replaceAll("\\s+",""));
        
        
        
        this.operationCounter++;
        
        String text = "Op" +this.operationCounter;
        String newLine = "\n\n\n";
        String newLineM = "\n\n\n\n\n";
        text += newLine ;
        
        String element;
        //only the first time gets the stuff that is in the matrix
        if (this.operationCounter == 1) {
            int i,j;
            for (i = 0; i < this.nrow; i++) {
                for (j=0;j < this.ncol; j++) {
                    //guessing the content is already verified ***
                    element = textEditsArray[i][j].getText();
                    this.matrix.modifyPosition(i, j, parse(element));
                }
            
            }
        }
        
        
        if (matrixCreated) {
             String txt = jTextArea1.getText();
             text += txt + newLineM;
        }
        
        //AFTER VERFYING THE TEXTEDIT!
        //applyOperation(this.matrix,this.operationTextEdit.getText());
        text += this.matrix.getMatrixString();
        jTextArea1.setText("");
        jTextArea1.setText(text);
        
    }//GEN-LAST:event_applyBtnMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton applyBtn;
    private javax.swing.JButton createMatrixBtn;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField mTextEdit;
    private javax.swing.JTextField nTextEdit;
    private javax.swing.JTextField operationTextEdit;
    // End of variables declaration//GEN-END:variables
}

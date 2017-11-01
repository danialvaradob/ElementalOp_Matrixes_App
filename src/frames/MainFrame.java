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
import java.util.StringTokenizer;
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
    private int rowModified;
    
    public MainFrame() {
        initComponents();
        init2();
        
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
    
    private void eliminateGridTextEdits() {
        for (int i = 0;i < this.nrow;i++) {
            for (int j = 0; j< this.ncol;j++) {
                this.textEditsArray[i][j].removeAll();
            }
        
        }
    }
    
    private void hideGrid() {
        for (int i = 0;i < this.nrow;i++) {
            for (int j = 0; j< this.ncol;j++) {
                this.textEditsArray[i][j].setVisible(false);
            }
        
        }
    }
    
    private void showGrid() {
        for (int i = 0;i < this.nrow;i++) {
            for (int j = 0; j< this.ncol;j++) {
                this.textEditsArray[i][j].setVisible(true);
            }
        
        }
    }
    
    private Instruction verifySwapRows(String _string) {
        Instruction type = SWAP_ROWS;
        int fcounter = 0;
        try {
            
            for (int j =0; j < _string.length(); j++ ){
                char c = _string.charAt(j);
                switch (c) {
                    case 'f':
                    case 'F':
                        fcounter++;
                        break;
                }
            }
            if (fcounter > 2 || fcounter < 1) return BAD_INPUT;
            
            _string = _string.replaceAll("\\s+","");
            
            String input = _string.replaceAll(",", ";").replaceAll("f", "F");
            
            String[] rat = input.split(";");
            //Part1 - Left Side
            String part1 = rat[0].replaceAll("F", "");
            int rowN1 = Integer.valueOf(part1);
            if (!isARow(rowN1)) {
                return BAD_INPUT;
            }
            String part2 = rat[1];
            //Part1 - Right Side
            if (fcounter == 2) {
                part2 = part2.replaceAll("F", "");
            }
            int rowN2 = Integer.valueOf(part2);
            if (!isARow(rowN2)) {
                return BAD_INPUT;
            }
            
        } catch (Exception e) {
            return BAD_INPUT;
        }
        return type;
    }
    
    private boolean isARow(int _n) {
        if (_n > nrow || _n < 1) {
            return false;
        }
        return true;
    }
    
    private Instruction verifySumSub(String _string) {
        Instruction type = OPERATE_ROWS;
        
        //makes every f and F
        try{
            _string = _string.replaceAll("\\s+","");
            String input = _string.replaceAll("f", "F");
            double mult1,mult2;
            if (input.contains("+")) {
                String[] rat = input.split("\\+");

                //First row op
                String firstRow = rat[0];
                String[] frs = firstRow.split("F");
                //multiplier
                try {
                    mult1 = parse(frs[0]);
                }catch (Exception e) {
                    if (!("".equals(frs[0]))) {
                                return BAD_INPUT;
                            }
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
                    if (!("".equals(srs[0]))) {
                                return BAD_INPUT;
                            }
                }
                //second row number
                int rowN2 = (int)parse(srs[1]);


            } else if (input.contains("-")) {
                String[] rat = input.split("-");

                 //First row op
                String firstRow = rat[0];
                String[] frs = firstRow.split("F");
                //multiplier
                try {
                    mult1 = parse(frs[0]);
                }catch (Exception e) {
                    if (!("".equals(frs[0]))) {
                                return BAD_INPUT;
                            }
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
                    if (!("".equals(srs[0]))) {
                                return BAD_INPUT;
                            }
                }
                //second row number
                int rowN2 = (int)parse(srs[1]);

                } else {
                    return BAD_INPUT;
                }
            }
        catch (Exception e) {
            return BAD_INPUT;
        
        }
        
        
        
        
        return type;
        
    }
    
    private Instruction verifyString(String _string) {
        
        if (verifySumSub(_string) == OPERATE_ROWS) {
            return OPERATE_ROWS;
        }
        if (verifySwapRows(_string) == SWAP_ROWS) {
            return SWAP_ROWS;
        }
        
        return BAD_INPUT;
        
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
        String n1 = Integer.toString(rowN1);
        String n2 = Integer.toString(rowN2);
        rowNumberModifiedTextEdit.setText(n1+" y " +n2);
        rowModifiedTextEdit.setText("Cambio de filas");
        _matrix.swapRows(rowN1, rowN2);
    }
    private void applyOperation(Matrix _matrix, String _string) {
        //makes every f and F
        _string = _string.replaceAll("\\s+","");
        String input = _string.replaceAll("f", "F");
        double mult1,mult2;
        if (input.contains("+")) {
            String[] rat = input.split("\\+");
            
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
            this.rowModified = rowN1;
            _matrix.operateRows(rowN1, rowN2, '+', mult1, mult2);
            
            
        } else if (input.contains("-")) {
            String[] rat = input.split("-");
            
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
            this.rowModified = rowN1;
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
        rowModifiedTextEdit = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        rowNumberModifiedTextEdit = new javax.swing.JTextField();

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
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.setText("Eliminacion\n--------------------------\nLas entradas de las Matrices solo\npueden ser enteros o fracciones\npor ejemplo:\n1/3, -1/4..\n\nEjemplos de Operaciones\n--------------------------\n\n-Se modifica la 1era fila\ndigitada\n>f2,f3 o f1;2 \n> 2f2 o 2F1\n>");
        jScrollPane1.setViewportView(jTextArea1);

        jLabel3.setText("Fila Modificada:");

        jLabel4.setText("#");

        rowNumberModifiedTextEdit.setText(" ");

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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE)
                            .addComponent(rowModifiedTextEdit))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel3)
                        .addGap(47, 47, 47)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rowNumberModifiedTextEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(rowNumberModifiedTextEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(rowModifiedTextEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(14, Short.MAX_VALUE))
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
        
        if ("".equals(nTextEdit.getText()) || "".equals(mTextEdit.getText()))
            msgbox("Por favor verifique el tamahno de la matriz");
        else {boolean error_flag = false;
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
       }
    }//GEN-LAST:event_createMatrixBtnMouseClicked

    private void applyBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_applyBtnMouseClicked
        // TODO add your handling code here:
        
        Instruction type = verifyString(this.operationTextEdit.getText());
        
        int i,j;
        for (i = 0; i < this.nrow; i++) {
            for (j=0;j < this.ncol; j++) {
                //guessing the content is already verified ***
                String element = textEditsArray[i][j].getText();
                try {
                    //double x = Double.valueOf(element);
                    double y = parse(element);
                } catch (Exception e) {
                    type = BAD_INPUT;
                    break;
                }

            }
            if (type == BAD_INPUT) {
                break;
            }
        }
        
        if (type == BAD_INPUT) {
            msgbox("Por favor verifique los datos ingresados");
        
        } else { 
            this.operationCounter++;



            String element;
            //only the first time gets the stuff that is in the matrix
            if (this.operationCounter == 1) {
                for (i = 0; i < this.nrow; i++) {
                    for (j=0;j < this.ncol; j++) {
                        //guessing the content is already verified ***
                        element = textEditsArray[i][j].getText();
                        this.matrix.modifyPosition(i, j, parse(element));
                    }

                }

            }

            String text = "";
            text += "Matriz Original" + "\n\n";
            text += this.matrix.getMatrixString();
            String newLine = "\n\n";
            String newLineM = "\n\n";
            text += newLine;
            if (matrixCreated) {
                 String txt = jTextArea1.getText();
                 text += txt + newLineM;
                 text += "--------------------------\n";
            }

            text += "Op" +this.operationCounter + " " + this.operationTextEdit.getText() +"\n\n";
            text += "--------------------------\n";
            //AFTER VERFYING THE TEXTEDIT!
            if (type == OPERATE_ROWS)  applyOperation(this.matrix,this.operationTextEdit.getText());
            if (type == SWAP_ROWS)  swapRowsOp(this.matrix,this.operationTextEdit.getText());
            if (type == OPERATE_ROWS) {
                rowModifiedTextEdit.setText(this.matrix.getRowString(rowModified));
                rowNumberModifiedTextEdit.setText(Integer.toString(rowModified));}
            text += this.matrix.getMatrixString();
            text += newLine;

            jTextArea1.setText("");
            jTextArea1.setText(text);
            hideGrid();
            matrixCreated = true;
            }
        
    }//GEN-LAST:event_applyBtnMouseClicked

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        // TODO add your handling code here:
        //showGrid();
        
        String helloMsg = "Eliminacion\n" +
            "--------------------------\n" +
            "Las entradas de las Matrices solo\n" +
            "pueden ser enteros o fracciones\n" +
            "por ejemplo:\n" +
            "1/3, -1/4..\n" +
            "\n" +
            "Ejemplos de Operaciones\n" +
            "--------------------------\n" +
            "\n" +
            "-Se modifica la 1era fila\n" +
            "digitada\n" +
            ">f2,f3 o f1;2 \n" +
            "> 2f2 o 2F1";
        jTextArea1.setText("");
        jTextArea1.setText(helloMsg);
        eliminateGridTextEdits();
        matrixCreated = false;
        operationCounter = 0;
        nrow = 0;
        ncol = 0;
        createMatrixBtn.setEnabled(true);
        nTextEdit.setEnabled(true);
        mTextEdit.setEnabled(true);
        applyBtn.setEnabled(false);
        rowNumberModifiedTextEdit.setText("");
        
        
        
        
        
    }//GEN-LAST:event_jButton2MouseClicked

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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField mTextEdit;
    private javax.swing.JTextField nTextEdit;
    private javax.swing.JTextField operationTextEdit;
    private javax.swing.JTextField rowModifiedTextEdit;
    private javax.swing.JTextField rowNumberModifiedTextEdit;
    // End of variables declaration//GEN-END:variables
}

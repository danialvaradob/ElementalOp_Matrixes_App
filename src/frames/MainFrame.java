/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frames;

import static frames.Instruction.BAD_INPUT;
import static frames.Instruction.OPERATE_ROWS;
import static frames.Instruction.SWAP_ROWS;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 *
 * @author danielalvarado
 */
public class MainFrame extends javax.swing.JFrame {

    /**
     * Creates new form NewJFrame
     */
    public MainFrame() {
        initComponents();
        hideMatrix();
        String input = "1/3F2+100/200F1";
        String i2 = "F2";
        /*
        Scanner s = new Scanner(input).useDelimiter("\\s*+\\s");
        while (s.hasNext()) {
            System.out.println(s.next());
        }
        System.out.println(parse("15"));
        */
        //String[] rat = input.split("+");
        String[] rat2 = i2.split("F");
        
        System.out.println(parse(rat2[0]));
        
    }
    
    
    private double parse(String ratio) {
        if (ratio.contains("/")) {
            String[] rat = ratio.split("/");
            return Double.parseDouble(rat[0]) / Double.parseDouble(rat[1]);
        } else {
            return Double.parseDouble(ratio);
        }
    }
    
    private void hideMatrix() {
         position00TextEdit.setVisible(false);
        position02TextEdit.setVisible(false);
        position01TextEdit.setVisible(false);
        position03TextEdit.setVisible(false);
        position04TextEdit.setVisible(false);
        position10TextEdit.setVisible(false);
        position14TextEdit.setVisible(false);
        position13TextEdit.setVisible(false);
        position12TextEdit.setVisible(false);
        position11TextEdit.setVisible(false);
        position20TextEdit.setVisible(false);
        position24TextEdit.setVisible(false);
        position23TextEdit.setVisible(false);
        position22TextEdit.setVisible(false);
        position21TextEdit.setVisible(false);
        position30TextEdit.setVisible(false);
        position34TextEdit.setVisible(false);
        position33TextEdit.setVisible(false);
        position32TextEdit.setVisible(false);
        position31TextEdit.setVisible(false);
        position40TextEdit.setVisible(false);
        position44TextEdit.setVisible(false);
        position43TextEdit.setVisible(false);
        position42TextEdit.setVisible(false);
        position41TextEdit.setVisible(false);
    
    }
    
    private void createMatrixMxN(int _m, int _n) {
        
        
        
        if (_m == 1) position00TextEdit.setVisible(true);
        if (_m == 2) position10TextEdit.setVisible(true);
        if (_m == 3) position20TextEdit.setVisible(true);
        if (_m == 4) position30TextEdit.setVisible(true);
        if (_m == 5) position40TextEdit.setVisible(true);
        
        if (_n == 1) {
            position00TextEdit.setVisible(true);
            position01TextEdit.setVisible(true);
            position02TextEdit.setVisible(true);
            position03TextEdit.setVisible(true);
            position04TextEdit.setVisible(true);
        }
        if (_n == 2) {
            position10TextEdit.setVisible(true);
            position11TextEdit.setVisible(true);
            position12TextEdit.setVisible(true);
            position13TextEdit.setVisible(true);
            position14TextEdit.setVisible(true);
        }
        if (_n == 3) {
            position20TextEdit.setVisible(true);
            position21TextEdit.setVisible(true);
            position22TextEdit.setVisible(true);
            position23TextEdit.setVisible(true);
            position24TextEdit.setVisible(true);
        }
        if (_n == 4) {
            position30TextEdit.setVisible(true);
            position31TextEdit.setVisible(true);
            position32TextEdit.setVisible(true);
            position33TextEdit.setVisible(true);
            position34TextEdit.setVisible(true);
        }
        if (_n == 5) {
            position40TextEdit.setVisible(true);
            position41TextEdit.setVisible(true);
            position42TextEdit.setVisible(true);
            position43TextEdit.setVisible(true);
            position44TextEdit.setVisible(true);
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
        String input = _string.replaceAll("f", "F;");
        double mult1,mult2;
        if (input.contains("+")) {
            String[] rat = _string.split("+");
            
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
    
    
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        position00TextEdit = new javax.swing.JTextField();
        position02TextEdit = new javax.swing.JTextField();
        position01TextEdit = new javax.swing.JTextField();
        position03TextEdit = new javax.swing.JTextField();
        position04TextEdit = new javax.swing.JTextField();
        position10TextEdit = new javax.swing.JTextField();
        position14TextEdit = new javax.swing.JTextField();
        position13TextEdit = new javax.swing.JTextField();
        position12TextEdit = new javax.swing.JTextField();
        position11TextEdit = new javax.swing.JTextField();
        position20TextEdit = new javax.swing.JTextField();
        position24TextEdit = new javax.swing.JTextField();
        position23TextEdit = new javax.swing.JTextField();
        position22TextEdit = new javax.swing.JTextField();
        position21TextEdit = new javax.swing.JTextField();
        position30TextEdit = new javax.swing.JTextField();
        position34TextEdit = new javax.swing.JTextField();
        position33TextEdit = new javax.swing.JTextField();
        position32TextEdit = new javax.swing.JTextField();
        position31TextEdit = new javax.swing.JTextField();
        position40TextEdit = new javax.swing.JTextField();
        position44TextEdit = new javax.swing.JTextField();
        position43TextEdit = new javax.swing.JTextField();
        position42TextEdit = new javax.swing.JTextField();
        position41TextEdit = new javax.swing.JTextField();
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

        jPanel1.setLayout(new java.awt.GridBagLayout());

        position00TextEdit.setText("0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 39;
        gridBagConstraints.ipady = 13;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        jPanel1.add(position00TextEdit, gridBagConstraints);

        position02TextEdit.setText("0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 39;
        gridBagConstraints.ipady = 13;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        jPanel1.add(position02TextEdit, gridBagConstraints);

        position01TextEdit.setText("0");
        position01TextEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                position01TextEditActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 39;
        gridBagConstraints.ipady = 13;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        jPanel1.add(position01TextEdit, gridBagConstraints);

        position03TextEdit.setText("0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 39;
        gridBagConstraints.ipady = 13;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        jPanel1.add(position03TextEdit, gridBagConstraints);

        position04TextEdit.setText("0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 39;
        gridBagConstraints.ipady = 13;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 6);
        jPanel1.add(position04TextEdit, gridBagConstraints);

        position10TextEdit.setText("0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 39;
        gridBagConstraints.ipady = 13;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        jPanel1.add(position10TextEdit, gridBagConstraints);

        position14TextEdit.setText("0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 39;
        gridBagConstraints.ipady = 13;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 6);
        jPanel1.add(position14TextEdit, gridBagConstraints);

        position13TextEdit.setText("0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 39;
        gridBagConstraints.ipady = 13;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        jPanel1.add(position13TextEdit, gridBagConstraints);

        position12TextEdit.setText("0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 39;
        gridBagConstraints.ipady = 13;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        jPanel1.add(position12TextEdit, gridBagConstraints);

        position11TextEdit.setText("0");
        position11TextEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                position11TextEditActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 39;
        gridBagConstraints.ipady = 13;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        jPanel1.add(position11TextEdit, gridBagConstraints);

        position20TextEdit.setText("0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 39;
        gridBagConstraints.ipady = 13;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        jPanel1.add(position20TextEdit, gridBagConstraints);

        position24TextEdit.setText("0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 39;
        gridBagConstraints.ipady = 13;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 6);
        jPanel1.add(position24TextEdit, gridBagConstraints);

        position23TextEdit.setText("0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 39;
        gridBagConstraints.ipady = 13;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        jPanel1.add(position23TextEdit, gridBagConstraints);

        position22TextEdit.setText("0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 39;
        gridBagConstraints.ipady = 13;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        jPanel1.add(position22TextEdit, gridBagConstraints);

        position21TextEdit.setText("0");
        position21TextEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                position21TextEditActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 39;
        gridBagConstraints.ipady = 13;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        jPanel1.add(position21TextEdit, gridBagConstraints);

        position30TextEdit.setText("0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.ipadx = 39;
        gridBagConstraints.ipady = 13;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        jPanel1.add(position30TextEdit, gridBagConstraints);

        position34TextEdit.setText("0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.ipadx = 39;
        gridBagConstraints.ipady = 13;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 6);
        jPanel1.add(position34TextEdit, gridBagConstraints);

        position33TextEdit.setText("0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.ipadx = 39;
        gridBagConstraints.ipady = 13;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        jPanel1.add(position33TextEdit, gridBagConstraints);

        position32TextEdit.setText("0");
        position32TextEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                position32TextEditActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.ipadx = 39;
        gridBagConstraints.ipady = 13;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        jPanel1.add(position32TextEdit, gridBagConstraints);

        position31TextEdit.setText("0");
        position31TextEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                position31TextEditActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.ipadx = 39;
        gridBagConstraints.ipady = 13;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        jPanel1.add(position31TextEdit, gridBagConstraints);

        position40TextEdit.setText("0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.ipadx = 39;
        gridBagConstraints.ipady = 13;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 15, 0);
        jPanel1.add(position40TextEdit, gridBagConstraints);

        position44TextEdit.setText("0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.ipadx = 39;
        gridBagConstraints.ipady = 13;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 15, 6);
        jPanel1.add(position44TextEdit, gridBagConstraints);

        position43TextEdit.setText("0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.ipadx = 39;
        gridBagConstraints.ipady = 13;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 15, 0);
        jPanel1.add(position43TextEdit, gridBagConstraints);

        position42TextEdit.setText("0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.ipadx = 39;
        gridBagConstraints.ipady = 13;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 15, 0);
        jPanel1.add(position42TextEdit, gridBagConstraints);

        position41TextEdit.setText("0");
        position41TextEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                position41TextEditActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.ipadx = 39;
        gridBagConstraints.ipady = 13;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 15, 0);
        jPanel1.add(position41TextEdit, gridBagConstraints);

        nTextEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nTextEditActionPerformed(evt);
            }
        });

        createMatrixBtn.setText("Crear Matriz");
        createMatrixBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createMatrixBtnActionPerformed(evt);
            }
        });

        jLabel1.setText("Matriz m x n");

        jLabel2.setText("x");

        applyBtn.setText("Aplicar");

        jButton2.setText("Deshacer");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.setText("Las entradas de las Matrices solo pueden ");
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
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(applyBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(operationTextEdit))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(82, 82, 82)
                        .addComponent(operationTextEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(applyBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(mTextEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(nTextEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(createMatrixBtn)
                                    .addComponent(jLabel2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void position01TextEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_position01TextEditActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_position01TextEditActionPerformed

    private void position11TextEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_position11TextEditActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_position11TextEditActionPerformed

    private void position21TextEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_position21TextEditActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_position21TextEditActionPerformed

    private void position31TextEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_position31TextEditActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_position31TextEditActionPerformed

    private void position41TextEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_position41TextEditActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_position41TextEditActionPerformed

    private void createMatrixBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createMatrixBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_createMatrixBtnActionPerformed

    private void nTextEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nTextEditActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nTextEditActionPerformed

    private void position32TextEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_position32TextEditActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_position32TextEditActionPerformed

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
    private javax.swing.JTextField position00TextEdit;
    private javax.swing.JTextField position01TextEdit;
    private javax.swing.JTextField position02TextEdit;
    private javax.swing.JTextField position03TextEdit;
    private javax.swing.JTextField position04TextEdit;
    private javax.swing.JTextField position10TextEdit;
    private javax.swing.JTextField position11TextEdit;
    private javax.swing.JTextField position12TextEdit;
    private javax.swing.JTextField position13TextEdit;
    private javax.swing.JTextField position14TextEdit;
    private javax.swing.JTextField position20TextEdit;
    private javax.swing.JTextField position21TextEdit;
    private javax.swing.JTextField position22TextEdit;
    private javax.swing.JTextField position23TextEdit;
    private javax.swing.JTextField position24TextEdit;
    private javax.swing.JTextField position30TextEdit;
    private javax.swing.JTextField position31TextEdit;
    private javax.swing.JTextField position32TextEdit;
    private javax.swing.JTextField position33TextEdit;
    private javax.swing.JTextField position34TextEdit;
    private javax.swing.JTextField position40TextEdit;
    private javax.swing.JTextField position41TextEdit;
    private javax.swing.JTextField position42TextEdit;
    private javax.swing.JTextField position43TextEdit;
    private javax.swing.JTextField position44TextEdit;
    // End of variables declaration//GEN-END:variables
}

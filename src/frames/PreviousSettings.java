/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frames;

/**
 *
 * @author danielalvarado
 */
public class PreviousSettings {
    public Matrix previousMatrix; 
    public String previousMatrixString;
    public String operationText;
    public int previousOperationCounter;
    public String previousRowNumberModified;
    public String previousRowModified;
    public int previousRowNumber;
    
    public PreviousSettings(Matrix _preM, String _prePrintedText, String _preOperationText,
                            int _preOperationCounter, String _preRowNumberMod,
                            String _preRowrMod, int _preRowN) {
        this.previousMatrix = _preM;
        this.previousMatrixString = _prePrintedText;
        this.operationText = _preOperationText;
        this.previousOperationCounter = _preOperationCounter;
        this.previousRowNumberModified = _preRowNumberMod;
        this.previousRowModified = _preRowrMod;
        this.previousRowNumber = _preRowN;
        
        
    }
}

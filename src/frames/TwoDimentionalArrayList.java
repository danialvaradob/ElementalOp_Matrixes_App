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
import java.util.ArrayList;
import javax.swing.JTextField;

public class TwoDimentionalArrayList<JTextField> extends ArrayList<ArrayList<JTextField>> {
    public void addToInnerArray(int index, JTextField element) {
        while (index >= this.size()) {
            this.add(new ArrayList<JTextField>());
        }
        this.get(index).add(element);
    }

    public void addToInnerArray(int index, int index2, JTextField element) {
        while (index >= this.size()) {
            this.add(new ArrayList<JTextField>());
        }

        ArrayList<JTextField> inner = this.get(index);
        while (index2 >= inner.size()) {
            inner.add(null);
        }

        inner.set(index2, element);
    }
}

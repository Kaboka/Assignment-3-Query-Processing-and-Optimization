/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simpledb.query;

/**
 *
 * @author Kasper
 */
public class BoolConstant implements Constant{
    
    private Boolean val;

    public BoolConstant(boolean f) {
        val = new Boolean(f);
    }

    @Override
    public Object asJavaVal() {
        return val;
    }

    @Override
    public int compareTo(Constant t) {
        BoolConstant bc = (BoolConstant) t;
        return val.compareTo(bc.val);
    }

    public boolean equals(Object obj) {
        BoolConstant bc = (BoolConstant) obj;
        return bc != null && val.equals(bc.val);
    }

    public int hashCode() {
        return val.hashCode();
    }

    public String toString() {
        return val.toString();
    }

}

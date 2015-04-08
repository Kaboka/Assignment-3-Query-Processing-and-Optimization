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
public class FloatConstant implements Constant {

    private Float val;

    public FloatConstant(float f) {
        val = new Float(f);
    }

    @Override
    public Object asJavaVal() {
        return val;
    }

    @Override
    public int compareTo(Constant t) {
        FloatConstant fc = (FloatConstant) t;
        return val.compareTo(fc.val);
    }

    public boolean equals(Object obj) {
        FloatConstant fc = (FloatConstant) obj;
        return fc != null && val.equals(fc.val);
    }

    public int hashCode() {
        return val.hashCode();
    }

    public String toString() {
        return val.toString();
    }

}

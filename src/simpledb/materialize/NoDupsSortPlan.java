/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpledb.materialize;

import java.util.Collection;
import simpledb.query.Plan;
import simpledb.query.Scan;
import simpledb.record.Schema;
import simpledb.tx.Transaction;

/**
 *
 * @author Kasper
 */
public class NoDupsSortPlan implements Plan {

    private Plan p;
    private Collection<String> distinctfields;
    private Schema sch;

    public NoDupsSortPlan(Plan p, Collection<String> distinctFields, Transaction tx) {
        this.p = p;
        this.distinctfields = distinctFields;
        for (String fldname : distinctFields) {
            sch.add(fldname, p.schema());
        }

    }

    @Override
    public Scan open() {
       Scan s = p.open();
       return new NoDupsSortScan(s, distinctfields);
    }

    @Override
    public int blocksAccessed() {
        return p.blocksAccessed();
    }

    @Override
    public int recordsOutput() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int distinctValues(String fldname) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Schema schema() {
        return sch;
    }

}

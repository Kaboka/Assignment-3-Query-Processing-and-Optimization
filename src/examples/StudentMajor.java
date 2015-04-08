/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examples;

import simpledb.query.Plan;
import simpledb.query.Scan;
import simpledb.server.SimpleDB;
import simpledb.tx.Transaction;

/**
 *
 * @author bulskov
 */
public class StudentMajor
{

    public static void main(String[] args)
    {
        try
        {
            SimpleDB.init("studentdb");

            Transaction tx = new Transaction();

            String qry = "select SName, DName "
                    + "from DEPT, STUDENT "
                    + "where MajorId = DId";
            Plan p = SimpleDB.planner().createQueryPlan(qry, tx);

            Scan s = p.open();

            System.out.println("Name\tMajor");
            while (s.next())
            {
                String sname = s.getString("sname"); //SimpleDB stores field names
                String dname = s.getString("dname"); //in lower case
                System.out.println(sname + "\t" + dname);
            }
            s.close();
            tx.commit();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}

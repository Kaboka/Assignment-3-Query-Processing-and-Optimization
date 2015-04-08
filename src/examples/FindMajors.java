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
public class FindMajors
{

    public static void main(String[] args)
    {
        try
        {
            SimpleDB.init("studentdb");

            Transaction tx = new Transaction();

            String major = "drama";//args[0];
            System.out.println("Here are the " + major + " majors");
            System.out.println("Name\tGradYear");

            String qry = "select sname, gradyear "
                    + "from student, dept "
                    + "where did = majorid "
                    + "and dname = '" + major + "'";
            Plan p = SimpleDB.planner().createQueryPlan(qry, tx);

            Scan s = p.open();

            // Step 3: loop through the result set
            while (s.next())
            {
                String sname = s.getString("sname");
                int gradyear = s.getInt("gradyear");
                System.out.println(sname + "\t" + gradyear);
            }
            s.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}

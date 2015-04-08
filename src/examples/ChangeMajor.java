/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examples;

import simpledb.server.SimpleDB;
import simpledb.tx.Transaction;

/**
 *
 * @author bulskov
 */
public class ChangeMajor
{

    public static void main(String[] args)
    {
        try
        {
            SimpleDB.init("studentdb");

            Transaction tx = new Transaction();

            String cmd = "update STUDENT set MajorId=30 "
                    + "where SName = 'amy'";
            SimpleDB.planner().executeUpdate(cmd, tx);
            System.out.println("Amy is now a drama major.");

            tx.commit();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}

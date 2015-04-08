/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examples;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.rmi.RemoteException;
import simpledb.query.Plan;
import simpledb.query.Scan;
import simpledb.server.SimpleDB;
import simpledb.tx.Transaction;
import static java.sql.Types.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author bulskov
 */
public class SQLInterpreter
{

    public static void main(String[] args)
    {
        try
        {
            SimpleDB.init("studentdb");

            Reader rdr = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(rdr);

            while (true)
            {
                // process one line of input
                System.out.print("\nSQL> ");
                String cmd = br.readLine().trim();
                System.out.println();
                if (cmd.startsWith("exit"))
                {
                    break;
                } else if (cmd.startsWith("select"))
                {
                    doQuery(cmd);
                } else
                {
                    doUpdate(cmd);
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private static void doQuery(String cmd)
    {
        try
        {
            Transaction tx = new Transaction();
            
            Plan p = SimpleDB.planner().createQueryPlan(cmd, tx);
            Scan s = p.open();
            
            int totalwidth = 0;

            // print header
            for (String name : p.schema().fields())
            {
                int width = getDisplayLength(p, name);
                totalwidth += width;
                String fmt = "%" + width + "s";
                System.out.format(fmt, name);
            }
            System.out.println();
            for (int i = 0; i <= totalwidth; i++)
            {
                System.out.print("-");
            }
            System.out.println();

            // print records
            while (s.next())
            {
                for (String fldname : p.schema().fields())
                {
                    int fldtype = p.schema().type(fldname);
                    String fmt = "%" + getDisplayLength(p, fldname);
                    if (fldtype == INTEGER)
                    {
                        System.out.format(fmt + "d", s.getInt(fldname));
                    } else
                    {
                        System.out.format(fmt + "s", s.getString(fldname));
                    }
                    System.out.print(" ");
                }
                System.out.println();
            }
            s.close();
        } catch (Exception e)
        {
            System.out.println("Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void doUpdate(String cmd)
    {
        try
        {
            Transaction tx = new Transaction();
            int howmany = SimpleDB.planner().executeUpdate(cmd, tx);
            System.out.println(howmany + " records processed");
            tx.commit();
        } catch (Exception e)
        {
            System.out.println("Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static int getDisplayLength(Plan plan, String fieldName)
    {
        int length = plan.schema().length(fieldName);
        // if length is 0 then return 6 -- for integers
        return length == 0 ? 6 : length;
    }
    
}

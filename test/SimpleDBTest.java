/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import simpledb.materialize.SortPlan;
import simpledb.metadata.TableMgr;
import simpledb.query.Plan;
import simpledb.query.Scan;
import simpledb.query.TablePlan;
import simpledb.record.RecordFile;
import simpledb.record.Schema;
import simpledb.record.TableInfo;
import simpledb.server.SimpleDB;
import simpledb.tx.Transaction;

/**
 *
 * @author Kasper
 */
public class SimpleDBTest {

    public SimpleDBTest() {
    }

    private static Transaction tx;
//    MyOperation op;
    static final String tableName = "mytable";
    static final String dbName = "mytestdb";

    @BeforeClass
    public static void setupClass() throws IOException {
        SimpleDB.dropDatabase(dbName);
        SimpleDB.init(dbName);
        tx = new Transaction();

        Schema schema = new Schema();
        schema.addStringField("name", TableMgr.MAX_NAME);
        schema.addIntField("age");
        schema.addBoolField("male");
        schema.addFloatField("income");
        SimpleDB.mdMgr().createTable(tableName, schema, tx);
        TableInfo tableInfo = SimpleDB.mdMgr().getTableInfo(tableName, tx);
        RecordFile file = new RecordFile(tableInfo, tx);

        file.insert();
        file.setString("name", "Peter");
        file.setInt("age", 23);
        file.setBool("male", true);
        file.setFloat("income", 999999.99f);

        file.insert();
        file.setString("name", "Ellen");
        file.setInt("age", 25);
        file.setBool("male", true);
        file.setFloat("income", 999.99f);

        file.insert();
        file.setString("name", "John");
        file.setInt("age", 23);
        file.setBool("male", false);
        file.setFloat("income", 424242.99f);
        
                file.insert();
        file.setString("name", "John");
        file.setInt("age", 23);
        file.setBool("male", false);
        file.setFloat("income", 424242.99f);
        tx.commit();
    }

    @AfterClass
    public static void tearDownClass() throws IOException {
        SimpleDB.dropDatabase(dbName);
    }

    @After
    public void tearDown() {
        tx.commit();
    }

//    @Test
    public void myTest() {
        SimpleDB.init(dbName);

        Transaction tx2 = new Transaction();
        TableInfo info = SimpleDB.mdMgr().getTableInfo(tableName, tx2);
        System.out.println(info.schema().fields());

        String qry = "select name, age, male, income"
                + " from " + tableName;
        Plan p = SimpleDB.planner().createQueryPlan(qry, tx2);
        Scan s = p.open();

        while (s.next()) {
            String name = s.getString("name");
            int age = s.getInt("age");
            boolean male = s.getBool("male");
            float income = s.getFloat("income");
            System.out.println(name + "\t" + age + "\t" + male + "\t" + income);
        }
        s.close();
    }

    @Test
    public void sortTest() {
        SimpleDB.init(dbName);

        Transaction tx2 = new Transaction();
        TableInfo info = SimpleDB.mdMgr().getTableInfo(tableName, tx2);
        System.out.println(info.schema().fields());
        List<String> sortFields = new ArrayList<>();
        sortFields.add("age");
        Plan p = new SortPlan(new TablePlan(tableName, tx), sortFields, tx);
        Scan s = p.open();

        while (s.next()) {
            String name = s.getString("name");
            int age = s.getInt("age");
            boolean male = s.getBool("male");
            float income = s.getFloat("income");
            System.out.println(name + "\t" + age + "\t" + male + "\t" + income);
        }
        s.close();
    }
}

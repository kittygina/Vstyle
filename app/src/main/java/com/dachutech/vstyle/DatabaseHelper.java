package com.dachutech.vstyle;

//Add required imports
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.List;
/**
 * Created by Test on 21/06/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper
{

    //The Android's default system path of your application database.
    private static String DB_PATH = "data/data/ng.edu.baze.bazeurocks.dbstatuscheckercom313/databases/";

    private static String DB_NAME = "theDuchess";
    private SQLiteDatabase myDataBase;
    private Context callerContext = null;
    public static String KEY_ROWID = null;
    //
    private  String currentDatabaseName;
    private  String activePackageName;
    private Boolean cloneFromExport;
    private String exportedFileName;
    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     * @param context
     */
    public DatabaseHelper(Context context)
    {
        super(context, DB_NAME, null, 1);
        this.callerContext = context;
        this.cloneFromExport = false;
        this.exportedFileName = "";       //

    }
    //
    public void overridePackageNameAndDatabaseName()
    {
        if (this.currentDatabaseName.length() > 0 && this.activePackageName.length() > 0)
        {
            //Override coded db_path name
            DB_PATH = "data/data/" + this.activePackageName + "/databases/";
            DB_NAME = this.currentDatabaseName;
        }
    }
    //
    public void createDataBase() throws IOException
    {

        boolean dbExist = checkDataBase();

        if(dbExist)
        {
            //do nothing - database already exist
        }
        else
        {

            //By calling this method an empty database will be created into the default system path
            //of your application so we can   that database with our database or create
            // a new database schema using an exported DDL from SQLite Browser
            this.getReadableDatabase();
            try
            {
                copyDataBase();
            }
            catch (IOException e)
            {
                throw new Error("Error copying database or executing schema DDL");
            }
        }
        //Check if copied or generated database is accessibe
        dbExist = checkDataBase();
        if (dbExist){ return;}

        throw new Error("Error copying database or executing schema DDL");
    }
    public boolean checkDataBase()
    {

        try
        {
            String myPath = DB_PATH + DB_NAME;
            myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
            this.close();
            return  true;
        }
        catch(SQLiteException e)
        {
            e.printStackTrace();
            System.out.print("DB not found");
            //database doesn't exist yet.
        }
        //
        return  false;
    }
    //
    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
    public void copyDataBase() throws IOException
    {
        Boolean dbLoadOkay = false;
        if (cloneFromExport &&(exportedFileName.length() > 0))
        {
            dbLoadOkay  = cloneDatabaseFromExport(exportedFileName);
            return;
        }
        InputStream myInput=null;
        //Open your local db as the input stream
        // String myasset[]=callerContext.getResources().getAssets().list("/");

        //  for(String s: myasset){
        //     Log.e("Asset", s);
        //  }
        // to open the database that is in the assets folder
        myInput = callerContext.getAssets().open(DB_NAME);

        Log.e("Execution", "It went pass the previous line");

        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }
        Log.e("Execution", "Database has been copied");
        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    private Boolean cloneDatabaseFromExport(String exportedFileName) throws IOException
    {
        BufferedReader textReader = null;
        InputStream myInput=  callerContext.getAssets().open(exportedFileName);
        textReader = new BufferedReader(
                new InputStreamReader(callerContext.getAssets().open("filename.txt")));
        //empty file into stringBuilder
        StringBuilder exportedString = new StringBuilder();
        String inputLine;
        while ((inputLine = textReader.readLine()) != null)
        {
            exportedString.append(inputLine) ;

        }
        //Now parse content of string builder to create executable DDL
        SQLiteDdlParser  ddlParserObject = new SQLiteDdlParser();
        List<String> executableDDL = null;
        try
        {
            executableDDL  = ddlParserObject.buildExecutableDdl(exportedString);
        } catch (Exception e) {
            e.printStackTrace();
            return  false;
        }
        //now execute the DDL to create the database schema
        for (int loopCounter = 0; loopCounter < executableDDL.size();loopCounter++)
        {
            myDataBase.execSQL(executableDDL.get(loopCounter));
        }
        //
        return true;
    }
    //I/O methods sample
    public String getData(String ColumnName)
    {
        //This is just raw sample, best thing is to pass it the query string
        //and then just execute
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath,null,SQLiteDatabase.OPEN_READONLY);
        Cursor c = myDataBase.rawQuery("select * from HealthMonitor", null);
        String out;
        if(c != null)
        {

            c.moveToFirst();
            out = c.getString(4);
            myDataBase.close();
        }
        else
        {
            out = "000";
        }
        return out;
    }
    //to insert values into a database
    public Cursor insertIntoDatabase(String nameOfTableInDatabase, List<String> listOfColumnsInTable, List<String> listOfValuesToInsert){

        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath,null,SQLiteDatabase.OPEN_READWRITE);
        String tableName = nameOfTableInDatabase;
        List<String> columnName = listOfColumnsInTable;
        List<String> values = listOfValuesToInsert;

        String query = "INSERT INTO "+ tableName +"("+ columnName +")"+" VALUES ("+ values +")";

        Cursor c =   myDataBase.rawQuery(query, null);
        myDataBase.close();

        return c;

    }
    //I/O sample end

    public void openDataBase() throws SQLException{

        //Open the database
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);

    }

    @Override
    public synchronized void close() {

        if(myDataBase != null)
            myDataBase.close();

        super.close();

    }

    //Required boiler plate overrides
    @Override
    public void onCreate(SQLiteDatabase db)
    {

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public String getCurrentDatabaseName() {
        return currentDatabaseName;
    }

    public void setCurrentDatabaseName(String currentDatabaseName) {
        this.currentDatabaseName = currentDatabaseName;
    }

    public String getActivePackageName() {
        return activePackageName;
    }

    public void setActivePackageName(String activePackageName) {
        this.activePackageName = activePackageName;
    }

    public Boolean getCloneFromExport() {
        return cloneFromExport;
    }

    public void setCloneFromExport(Boolean cloneFromExport) {
        this.cloneFromExport = cloneFromExport;
    }

    public String getExportedFileName() {
        return exportedFileName;
    }

    public void setExportedFileName(String exportedFileName) {
        this.exportedFileName = exportedFileName;
    }

    public class SQLException extends Exception
    {
    }

    public void execSQL(String insertQueryUser)
    {

    }
}

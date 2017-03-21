package com.dachutech.vstyle;

import android.app.Activity;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class SQLiteDdlParser extends Activity
{

    public SQLiteDdlParser()
    {

    }
    public List<String> buildExecutableDdl(StringBuilder ddlFileInString)
    {
        List<String> executableDdlAsListOfString  =  new ArrayList<String>();
        String ddlFileToParse = ddlFileInString.toString();
        String tempDDL   = ConvertImageToBlob(ddlFileToParse);
        tempDDL   = ConvertDateToVarchar18(tempDDL);
        tempDDL   = ConvertDecimalToVarchar18(tempDDL);
        executableDdlAsListOfString  = generateExecutableDdlInList(tempDDL);
        return executableDdlAsListOfString;
    }

    private List<String> generateExecutableDdlInList(String ddlFileToParse)
    {
        List<String> returnList  =  new ArrayList<String>();
        String semiColonMarker  = ";";
        Integer semiColonLocation = 0;
        Integer nextStart = 0;
        while ((semiColonLocation = ddlFileToParse.indexOf(semiColonMarker, nextStart))> 0)
        {
            String execDDL  = ddlFileToParse.substring(nextStart, semiColonLocation+1);
            returnList.add(execDDL);
            nextStart = semiColonLocation + 1;
            //
            if (nextStart < ddlFileToParse.length()) {}
            else
            {
                break;
            }
        }
        //
        return returnList;
    }

    private String ConvertDecimalToVarchar18(String ddlFileToParse)
    {
        String returnString = null;
        String DecimalAsVarchar   = " varchar(18)";
        String decimalFinder1 = " Decimal(";
        String DecimalFinder2 = " decimal(";
        //
        String tempString  = ddlFileToParse.replaceAll(" Decimal",DecimalFinder2);
        Integer decimalLocation  = 0;
        Integer nextStart = 0;
        while ((decimalLocation = tempString.indexOf(DecimalFinder2, nextStart))> 0)
        {
            Integer closeParen  = tempString.indexOf(")", decimalLocation+1);
            String stringPart1  = tempString.substring(0, decimalLocation);
            String stringPart2 = tempString.substring(closeParen+1);
            String appendedDataString  = " " + DecimalAsVarchar + " ";
            tempString = stringPart1 + appendedDataString +
                    stringPart2;
            nextStart = stringPart1.length() + appendedDataString.length() + 1;
            if (nextStart < tempString.length()) {}
            else
            {
                break;
            }
        }
        //
        returnString = tempString;
        return returnString;
    }

    private String ConvertDateToVarchar18(String ddlFileToParse)
    {
        String returnString = null;
        String dateAsVarchar   = " varchar(18)";
        returnString = ddlFileToParse.replaceAll(" datetime", dateAsVarchar);
        returnString = returnString.replaceAll(" Datetime", dateAsVarchar);
        return returnString;
    }

    private String ConvertImageToBlob(String ddlFileToParse)
    {
        String returnString = null;
        //
        String imageAsBlob  = " blob";
        returnString = ddlFileToParse.replaceAll(" Image", imageAsBlob);
        returnString = returnString.replaceAll(" image", imageAsBlob);
        //
        return returnString;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }
}

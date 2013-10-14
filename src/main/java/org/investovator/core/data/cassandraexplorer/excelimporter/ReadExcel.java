package org.investovator.core.data.cassandraexplorer.excelimporter;


import jxl.*;
import jxl.read.biff.BiffException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: hasala
 * Date: 8/8/13
 * Time: 4:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class ReadExcel {

        private String inputFile;
        private ConfigParser xmlParser;
        private ArrayList<HistoryData> historyData;

        public void setInputFile(String inputFile, ConfigParser xmlParser) {
            this.inputFile = inputFile;
            this.xmlParser = xmlParser;
            this.historyData = new ArrayList<HistoryData>();
        }


        public void read() throws IOException  {
            File inputWorkbook = new File(inputFile);
            ArrayList<String> fields = xmlParser.getInputFields();
            Workbook workbook;

            try {
                workbook = Workbook.getWorkbook(inputWorkbook);
                Sheet sheet = workbook.getSheet(0);

                int columnCount = fields.size();
                int rowCount = sheet.getRows();

                //sharePriceData = new double[rowCount][columnCount];

                for (int i = 0; i < rowCount - 1; i++) {
                    HistoryData data = new HistoryData();

                    for (int j = 0; j < columnCount; j++) {

                        String currentField = fields.get(j);
                        Cell cellTest = sheet.findCell(currentField);            //find required cells
                        Cell cell = sheet.getCell(cellTest.getColumn(), i + 1);

                        if(cell.getContents() == "")
                            continue;

                        if(currentField.equals("Day"))
                        {
                             data.setDate(cell.getContents());
                        }
                        else if(currentField.equals("High"))
                        {
                             data.setHighPrice(Double.parseDouble(cell.getContents()));
                        }
                        else if(currentField.equals("Low"))
                        {
                             data.setLowPrice(Double.parseDouble(cell.getContents()));
                        }
                        else if(currentField.equals("Closing"))
                        {
                             data.setClosingPrice(Double.parseDouble(cell.getContents()));
                        }
                        else if(currentField.equals("No. of Trades"))
                        {
                             data.setNoOfShares(Double.parseDouble(cell.getContents()));
                        }
                        else if(currentField.equals("No. of Shares"))
                        {
                             data.setNoOfTrades(Double.parseDouble(cell.getContents()));
                        }
                        else if(currentField.equals("Turnover"))
                        {
                             data.setTurnover(Double.parseDouble(cell.getContents()));
                        }
                        System.out.println(cell.getContents());

                    }
                    historyData.add(data);

                }
            } catch (BiffException e) {
                e.printStackTrace();
            }
        }

        public ArrayList<HistoryData> getSharePriceData(){
            return historyData;
        }


}

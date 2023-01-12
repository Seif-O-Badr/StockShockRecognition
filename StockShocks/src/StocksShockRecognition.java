import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.StandardSocketOptions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class StocksShockRecognition {
    public static void main(String[] args) throws IOException {
        //HistoricalPrices.csv
        String path = "C:\\Users\\pc j\\IdeaProjects\\StockShocks\\HistoricalPrices.csv";
        String line = "";
        BufferedReader br = new BufferedReader(new FileReader(path));
        String[] excel;
        Double[] values = new Double[5605];
        String[] dates = new String[5605];
        int x = 0;

        br.readLine(); // this will read the first line
        while ((line = br.readLine()) != null) {
            excel = line.split(",");
            //excel at 0 get date , excel at 1 get values
            //System.out.println("Date : " + excel[0] + " Values : " + excel[1]);
            Double temp = Double.parseDouble(excel[1]);
            String temp2 = excel[0];
                values[x] = temp;
                dates[x] = temp2;
                x++;

        }
        // Our Array of values
        // System.out.println("ARRAY OF PRICES : " +  Arrays.toString(values));
        // System.out.println("ARRAY OF DATES : " +  Arrays.toString(dates));


         // Price Array Reversal
        for(int i = 0; i < values.length / 2; i++)
        {
            Double temp = values[i];
            values[i] = values[values.length - i - 1];
            values[values.length - i - 1] = temp;
        }
        // Date Array Reversal
        for(int i = 0; i < dates.length / 2; i++)
        {
            String temp = dates[i];
            dates[i] = dates[values.length - i - 1];
            dates[values.length - i - 1] = temp;
        }
        // System.out.println("ARRAY OF PRICES AFTER REVERSE : " +  Arrays.toString(values));
        // System.out.println("ARRAY OF DATES AFTER REVERSE : " +  Arrays.toString(dates));

        int counter = 1;
        double startLastShockValue = 0;
        double endLastShockValue = 0;
        double beforeLastShockValue = 0;
        double consecutiveShockValue = 0;
        String startLastShockDate = "";
        String endLastShockDate = "";
        String beforeLastShockDate = "";
        String consecutiveShockDate = "";
        boolean twoShocksOccured = false;

        for (int i = 0 ; i < values.length ; i++) {
            //System.out.println("outer loop i : " + i);
            try {
                if (values[i + 1] < values[i]) {
                    for (int j = i; j < values.length; j++) {
                        if (values[i + counter] >= values[i]) {
                            System.out.println("Shock From Value " + values[i] + " At date : " + dates[i]
                                    + " |||  Until Value " + values[i + counter] + " At date : " + dates[i + counter] );


                            // BONUS - Storing all shock values of 2 shocks, to test whether they are in a row or not
                            startLastShockValue = values[i];
                            startLastShockDate = dates[i];
                            endLastShockValue = values[i+counter];
                            endLastShockDate = dates[i+counter];
                            //System.out.println("START LAST SHOCK VALUE : " + startLastShockValue);
                            //System.out.println("END LAST SHOCK VALUE : " + endLastShockValue);
                            //System.out.println("BEFORE LAST SHOCK VALUE : " + beforeLastShockValue);

                            if (twoShocksOccured) {
                                System.out.println("INCREASE After 2 Consecutive Shocks was From Value " + consecutiveShockValue +
                                        " Date : " + consecutiveShockDate +
                                        " Until Value " + startLastShockValue + " Date : " + startLastShockDate);
                                twoShocksOccured = false;
                            }

                            if (beforeLastShockValue == startLastShockValue) {
                                System.out.println("2 CONSECUTIVE SHOCKS OCCURRED");
                                consecutiveShockValue = endLastShockValue;
                                consecutiveShockDate = endLastShockDate;
                                twoShocksOccured = true;

                            }

                            // counter increment and set i to new location
                            i = i + counter - 1;
                            counter = 1;

                            break;
                        }

                        else {
                            counter++;
                        }
                    }
                }
                beforeLastShockValue = endLastShockValue;
                beforeLastShockDate = endLastShockDate;
            }
            catch (ArrayIndexOutOfBoundsException e){
                System.out.println("Array Out of Bounds !! " + "i = " +  i + " counter = " + counter );
                break;
            }
        }



    }
}
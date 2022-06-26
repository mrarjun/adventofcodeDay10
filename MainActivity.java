package com.test.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    int partOneAns = 0;
    long partTwoAns = 0;
    List<Long> partTwoData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Method used to execute the navigation data error.
        calculateFirstPart();

        //Answer Print
        Log.e(" --- Part One --- : ", String.valueOf(partOneAns));
        //Sorting the sum of the valid sum.
        Collections.sort(partTwoData);
        //Finding the middle value
        partTwoAns = partTwoData.get((partTwoData.size() / 2));
        Log.e(" --- Part Two --- : ", String.valueOf(partTwoAns));
    }

    /**
     * Method for handling the navigation data error part 1.
     */
    private void calculateFirstPart() {
        try {
            //Reads input symbols from file.
            BufferedReader br = new BufferedReader(new InputStreamReader(getResources()
                    .getAssets().open("sample.txt"), StandardCharsets.UTF_8));

            String line;
            //Reads line
            while ((line = br.readLine()) != null) {
                String[] chunkCharArr = line.trim().split("");

                boolean isChunkIncomplete = true;
                ArrayList<String> chunkArray = new ArrayList<>();
                String arrayLastValue = "";
                int arrayLastValuePosition = 0;

                //Looping the sequence
                forLoopScope:
                for (String value : chunkCharArr) {
                    if (!chunkArray.isEmpty()) {
                        arrayLastValuePosition = chunkArray.size() - 1;
                        arrayLastValue = chunkArray.get(arrayLastValuePosition);
                    }
                    switch (value) {
                        //Adding opening chunks to array
                        case "(":
                        case "[":
                        case "{":
                        case "<":
                            chunkArray.add(value);
                            break;

                        //Checks for the opening character and if not found then
                        //Mark it as not valid and exit the loop else consider it as a valid item.
                        case ")":
                            if ("(".equals(arrayLastValue)) {
                                chunkArray.remove(arrayLastValuePosition);
                                break;
                            }
                            partOneAns += 3;
                            isChunkIncomplete = false;
                            break forLoopScope;
                        case "]":
                            if ("[".equals(arrayLastValue)) {
                                chunkArray.remove(arrayLastValuePosition);
                                break;
                            }
                            partOneAns += 57;
                            isChunkIncomplete = false;
                            break forLoopScope;
                        case "}":
                            if ("{".equals(arrayLastValue)) {
                                chunkArray.remove(arrayLastValuePosition);
                                break;
                            }
                            partOneAns += 1197;
                            isChunkIncomplete = false;
                            break forLoopScope;
                        case ">":
                            if ("<".equals(arrayLastValue)) {
                                chunkArray.remove(arrayLastValuePosition);
                                break;
                            }
                            partOneAns += 25137;
                            isChunkIncomplete = false;
                            break forLoopScope;
                    }
                }
                //Part 2 starts here!!!
                calculateSecondPart(chunkArray, isChunkIncomplete);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method for handling the navigation data error part 2.
     */
    private void calculateSecondPart(ArrayList<String> chunkArray, boolean isChunkIncomplete) {
        //Calculating the score for incompleted chunk
        if (isChunkIncomplete) {
            long count = 0;
            while (chunkArray.size() > 0) {
                String chunk = chunkArray.remove(chunkArray.size() - 1);
                count = count * 5;
                switch (chunk) {
                    case "(":
                        count += 1;
                        break;
                    case "[":
                        count += 2;
                        break;
                    case "{":
                        count += 3;
                        break;
                    case "<":
                        count += 4;
                        break;
                }
            }
            partTwoData.add(count);
        }
    }
}
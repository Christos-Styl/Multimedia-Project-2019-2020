package gr.ntua.multimediaproject.airport;

import gr.ntua.multimediaproject.dockingstations.DockingStation;
import gr.ntua.multimediaproject.dockingstations.DockingStationBuilder;
import gr.ntua.multimediaproject.dockingstations.DockingStationType;
import gr.ntua.multimediaproject.dockingstations.exceptions.DockingStationException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class AirportSingleton {
    private static AirportSingleton airport;

    private List<DockingStation> dockingStationList;

    public static AirportSingleton getInstance(){
        if(airport == null){
            airport = new AirportSingleton();
        }
        return airport;
    }

    private AirportSingleton(){
        dockingStationList = new ArrayList<>();
    }

    public List<DockingStation> getDockingStationList() {
        return dockingStationList;
    }

    public void setDockingStationList(List<DockingStation> dockingStationList) {
        this.dockingStationList = dockingStationList;
    }


    public void initializeAirportContentFromFiles(){
        String setupFileName = "Multimedia-Airport/src/main/java/gr/ntua/multimediaproject/medialab/airport_default.txt";
        initializeDockingStationListFromFile(setupFileName);
    }


    public void initializeDockingStationListFromFile(String fileName){
        List<String[]> lines = readFileLinesIntoListOfStringArrays(fileName);
        try {
            for (String[] line : lines) {
                DockingStation dockingStation = createNewDockingStationsFromFileLine(line);
                dockingStationList.add(dockingStation);
            }
        }
        catch(DockingStationException ex){
            System.out.println(ex + ": "+ ex.getMessage() + " " + ex.getCause());
        }
    }


    private List<String[]> readFileLinesIntoListOfStringArrays(String fileName){
        List<String[]> lines = new ArrayList<>();
        try {
            BufferedReader in = new BufferedReader(new FileReader(fileName));
            try {
                String line;
                while ((line = in.readLine()) != null) {
                    String[] splitLine = line.split(",");
                    if(splitLine.length != 4){
                        throw new IOException("The line \"" + Arrays.toString(splitLine) + "\" did not have length 4. All lines should" +
                                " have 4 arguments.");
                    }
                    lines.add(splitLine);
                }
            }
            catch (IOException ex) {
                System.out.println(ex + ": "+ ex.getMessage() + " " + ex.getCause());
            }
        }
        catch (FileNotFoundException ex) {
            System.out.println(ex + ": "+ ex.getMessage() + " " + ex.getCause());
        }
        return lines;
    }


    private DockingStation createNewDockingStationsFromFileLine(String[] line) throws DockingStationException{
        try {
            DockingStationType dockingStationType = DockingStationType.valueOf(Integer.parseInt(line[0]));
            int numberOfLandingSpaces = Integer.parseInt(line[1]);
            double cost = Double.parseDouble(line[2]);
            String idPrefix = line[3];
            return DockingStationBuilder.create().ofDockingStationType(dockingStationType).ofCost(cost).
                    ofIdPrefixAndNumberOfSpaces(idPrefix, numberOfLandingSpaces).build();
        }
        catch(DockingStationException ex){
            System.out.println(ex + ": "+ ex.getMessage() + " " + ex.getCause());
        }
        throw new DockingStationException("createNewDockingStationsFromFileLine failed.");

    }

    @Override
    public String toString() {
        String dockingStationListToString = "";
        for(DockingStation dockingStation : dockingStationList){
            dockingStationListToString = dockingStationListToString.concat(dockingStation.toString() + "\n");
        }
        return "AirportSingleton{\n" +
                "dockingStationList=\n" + dockingStationListToString +
                '}';
    }
}

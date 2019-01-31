package com.workday.transactions.rest.runtime;

import java.io.File;
import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * TODO: add description
 *
 * @author ludim.castillo
 * @since Dec-2018
 */
public class Main {

    private static ArrayList<Record> records = new ArrayList<>();
    private static List<Record> sortedRecordsList = new ArrayList<>();
    private static String[][] notes;
    private static HashMap<String, Integer> sleepMap = new HashMap<>();
    private static HashMap<String, HashMap<Integer, Integer>> minuteCountSleepMap = new HashMap<>();

    // Java Program to illustrate reading from Text File
    // using Scanner Class
    public static void main(String[] args)
	throws Exception {
        File file = new File("/Users/ludim.castillo/Downloads/input.txt");
        Scanner sc = new Scanner(file);

        while (sc.hasNextLine()) {
            String record = sc.nextLine();

            parseRecords(record);
        }

        sortedRecordsList = records.stream().sorted(Comparator.comparing(Record::getDate)).collect(Collectors.toList());

        notes = new String[60][sortedRecordsList.size() + 1];

        String id = "";
        for (Record r : sortedRecordsList) {
            if (!r.getId().isEmpty()) {
		id = r.getId();
            }
            r.setId(id);
            System.out.println(r.getDate() + "  " + r.getId() + " Type: " + r.getType());
        }

        initTimeline();
        analyze();
        printTimeline();
        System.out.println(sortedRecordsList.size());
        String guard = getGuardWithMostSleep();
        System.out.println(guard);
        //printSleepResults();
        int minute = getMinuteWithMostSleepForGuard(guard);
        System.out.println(minute);
        //printMostSleepMinuteFor("#99");
        System.out.println("Result To Part 1 => " + Integer.valueOf(guard.substring(1)) * minute);
        System.out.println("======================================================================");
        String guard2 = getGuardWithMaxSleepOnSameMinute();
        System.out.println(guard2);
        int minute2 = getMinuteWithMostSleepForGuard(guard2);
        System.out.println(minute2);
        System.out.println("Result To Part 2 => " + Integer.valueOf(guard2.substring(1)) * minute2);
    }

    private static void analyze() {
        String currID = sortedRecordsList.get(0).getId();
        int row = 0;
        for (int i = 0; i < sortedRecordsList.size(); i++) {
            if (i != 0 && sortedRecordsList.get(i).getType().equals("start") && currID.equals(sortedRecordsList.get(i).getId())) {
                row++;
            }
            else if (currID.equals(sortedRecordsList.get(i).getId()) && sortedRecordsList.get(i).getType().equals("falls") && sortedRecordsList.get(i+1).getType().equals("wakes")) {
                for (int j = sortedRecordsList.get(i).getDate().getMinute(); j < sortedRecordsList.get(i + 1).getDate().getMinute(); j++) {
                    notes[j][row] = "#";
                    if(sleepMap.containsKey(currID)) {
                        sleepMap.put(currID, sleepMap.get(currID) + 1);
                        if (minuteCountSleepMap.containsKey(currID)) {
                            if (minuteCountSleepMap.get(currID).containsKey(j)) {
                                minuteCountSleepMap.get(currID).put(j, minuteCountSleepMap.get(currID).get(j) + 1);
                            }
                            else {
                                minuteCountSleepMap.get(currID).put(j, 1);
                            }
                        }
                        else {
                            HashMap<Integer, Integer> nuevo = new HashMap<>();
                            nuevo.put(j, 1);
                            minuteCountSleepMap.put(currID, nuevo);
                        }
                    }
                    else {
                        sleepMap.put(currID, 1);

                        HashMap<Integer, Integer> nuevo = new HashMap<>();
                        nuevo.put(j, 1);
                        minuteCountSleepMap.put(currID, nuevo);
                    }
                }
                i++;
            }
            else if (!sortedRecordsList.get(i).getType().equals("falls")) {

            }
            else {
                row++;
                currID = sortedRecordsList.get(i).getId();
                i--;
            }
        }
    }

    private static void initTimeline() {
        for (int i = 0; i < sortedRecordsList.size(); i++) {
            for (int j = 0; j < 60; j++) {
                notes[j][i] = ".";
            }
        }
    }

    private static void printTimeline() {
        for (int i = 0; i < sortedRecordsList.size(); i++) {
            for (int j = 0; j < 60; j++) {
                System.out.print(notes[j][i]);
            }
            System.out.println();
        }
    }

    private static void printSleepResults() {
        Iterator it = sleepMap.entrySet().iterator();
        while(it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            System.out.println(pair.getKey() + " = " + pair.getValue());
        }
    }

    private static String getGuardWithMostSleep() {
        String guardId = "";
        int maxSleep = 0;
        Iterator it = sleepMap.entrySet().iterator();
        while(it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            if (Integer.valueOf(pair.getValue().toString()) > maxSleep) {
                maxSleep = Integer.valueOf(pair.getValue().toString());
                guardId = pair.getKey().toString();
            }
        }
        return guardId;
    }

    private static void printMostSleepMinuteFor(String guardId) {
        Iterator it = minuteCountSleepMap.get(guardId).entrySet().iterator();
        while(it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            System.out.println(pair.getKey() + " = " + pair.getValue());
        }
    }

    private static int getMinuteWithMostSleepForGuard(String guardId) {
        int maxMinuteCollisions = 0;
        int maxMinuteSlept = 0;
        Iterator it = minuteCountSleepMap.get(guardId).entrySet().iterator();
        while(it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            int pariVal = Integer.valueOf(pair.getValue().toString());
            if (pariVal > maxMinuteCollisions) {
                maxMinuteCollisions = pariVal;
                maxMinuteSlept = Integer.valueOf(pair.getKey().toString());
            }
        }
        return maxMinuteSlept;
    }

    /**
     * For Part 2 of Day 4
     * @param
     * @return
     */
    private static String getGuardWithMaxSleepOnSameMinute() {
        String guard = "";
        int maxMinuteCollisions = 0;
        Iterator it1 = minuteCountSleepMap.entrySet().iterator();
        while(it1.hasNext()) {
            Map.Entry pair1 = (Map.Entry) it1.next();
            int minute = getMinuteWithMostSleepForGuard(pair1.getKey().toString());
            int frequencyOnThatMinute = minuteCountSleepMap.get(pair1.getKey().toString()).get(minute);
            if(frequencyOnThatMinute > maxMinuteCollisions) {
                maxMinuteCollisions = frequencyOnThatMinute;
                guard = pair1.getKey().toString();
            }
        }
        System.out.println(guard + " / " + maxMinuteCollisions);
        return guard;
    }

    private static void parseRecords(String record) {
        //claim = record.replaceAll("\\s+", "");
        String[] recordlist = record.split("]");
        //System.out.println("1st: " + recordlist[0] + " 2nd: " + recordlist[1]);
        String timeStamp = record.substring(record.indexOf("[") + 1,  record.indexOf("]"));

        String[] timeStampList = timeStamp.split("\\s+");
        String dateTimeInString = timeStampList[0] + "T" + timeStampList[1] + ":00Z";
        //System.out.println(dateTimeInString);
        //System.out.println("LocalDate: " + timeStampList[0] + " time: " + timeStampList[1]);

        Instant instant = Instant.parse(dateTimeInString);
        //get date time only
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.of(ZoneOffset.UTC.getId()));

        Record newRecord;
        if (recordlist[1].contains("falls")) {
            newRecord = new Record("", "falls", localDateTime);
        }
        else if (recordlist[1].contains("wakes")) {
            newRecord = new Record("", "wakes", localDateTime);
        }
        else {
            String idReplaced = recordlist[1].replaceAll("\\s+", "");
            String id = idReplaced.substring(idReplaced.indexOf("#"), idReplaced.indexOf("b"));
            newRecord = new Record(id, "start", localDateTime);
        }

        records.add(newRecord);
    }

    static class Record {
        private String id;
        private String type;
        private LocalDateTime date;

        Record(String id, String type, LocalDateTime date) {
            this.id = id;
            this.type = type;
            this.date = date;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public LocalDateTime getDate() {
            return date;
        }

        public void setDate(LocalDateTime date) {
            this.date = date;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}

package com.company;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.io.File;

public class Processor {

    Set<WorkOrder> initialSet = new HashSet<>();
    Set<WorkOrder> assignedSet = new HashSet<>();
    Set<WorkOrder> in_progressSet = new HashSet<>();
    Set<WorkOrder> doneSet = new HashSet<>();

    public void processWorkOrders() {
        while(true){
            readIt();
            moveIt();
        }
    }

    private void moveIt() {
        // move work orders in map from one state to another
        HashMap<Status, HashSet> mapOrders = new HashMap<>();
        mapOrders.put(Status.INITIAL, (HashSet) initialSet);
        mapOrders.put(Status.ASSIGNED, (HashSet) assignedSet);
        mapOrders.put(Status.IN_PROGRESS, (HashSet) in_progressSet);
        mapOrders.put(Status.DONE, (HashSet) doneSet);

        if(!(mapOrders.get(Status.INITIAL).size() == 0)){
            System.out.println("-----------------------");
            System.out.println("initial work order" + mapOrders.get(Status.INITIAL));
            System.out.println(".......................");
        }
        if(!(mapOrders.get(Status.ASSIGNED).size() == 0)){
            System.out.println("-----------------------");
            System.out.println("assigned work order" + mapOrders.get(Status.ASSIGNED));
            System.out.println(".......................");
        }
        if(!(mapOrders.get(Status.IN_PROGRESS).size() == 0)){
            System.out.println("-----------------------");
            System.out.println("in progress work order" + mapOrders.get(Status.IN_PROGRESS));
            System.out.println(".......................");
        }
        if(!(mapOrders.get(Status.DONE).size() == 0)){
            System.out.println("-----------------------");
            System.out.println("finished work order" + mapOrders.get(Status.DONE));
            System.out.println(".......................");
        }
        if(!(mapOrders.get(Status.INITIAL).size() == 0)|| !(mapOrders.get(Status.ASSIGNED).size() == 0) || !(mapOrders.get(Status.IN_PROGRESS).size() == 0) || !(mapOrders.get(Status.DONE).size() == 0)){
            System.out.println("work order map:");
            System.out.println(mapOrders.entrySet());
            System.out.println(" ");
        }
    }

    private void readIt() {
        // read the json files into WorkOrders and put in map
        
    }

    public void rewrite(WorkOrder wo, Status status){
        WorkOrder temp = new WorkOrder();
        temp.setSenderName( wo.getSenderName());
        temp.setId(wo.getId());
        temp.setDescription(wo.getDescription());
        temp.setStatus(status);
        String jsonOrder;
        try{
            ObjectMapper mapper = new ObjectMapper();
            jsonOrder = mapper.writeValueAsString(temp);
            File jsonFile = new File(temp.getId() + ".json");
            FileWriter newFIle = new FileWriter(jsonFile);
            newFIle.write(jsonOrder);
            newFIle.close();

        }
        catch(IOException ex){
            ex.printStackTrace();
        }
    }

    public static void main(String args[]) {
        Processor processor = new Processor();
        processor.processWorkOrders();
    }
}


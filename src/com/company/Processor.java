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
            try{
            readIt();
            moveIt();
            clearIt();
            Thread.sleep(5000l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    private void clearIt(){
        initialSet.removeAll(initialSet);
        assignedSet.removeAll(assignedSet);
        in_progressSet.removeAll(in_progressSet);
        doneSet.removeAll(doneSet);
    }

    private void moveIt() {

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
        File directory = new File(".");
        File files[] = directory.listFiles();
        for(File f : files){
            if(f.getName().endsWith(".json")){
                try{
                   ObjectMapper mapper = new ObjectMapper();
                   WorkOrder order = mapper.readValue(f, WorkOrder.class);
                   if(order.getStatus().equals(Status.INITIAL)){
                       System.out.println("-----new work order-----");
                       System.out.println("work order: " + order.getId() + " description: "  + order.getDescription() + " submitted by: " + order.getSenderName() + " status" + order.getStatus());
                       initialSet.add(order);
                       rewrite(order, Status.ASSIGNED);
                   }
                   else if( order.getStatus().equals(Status.ASSIGNED)){
                   assignedSet.add(order);
                   rewrite(order, Status.IN_PROGRESS);
                    }
                    else if(order.getStatus().equals(Status.IN_PROGRESS)){
                       in_progressSet.add(order);
                       rewrite(order, Status.DONE);
                   }
                   else if(order.getStatus().equals(Status.DONE)){
                        doneSet.add(order);
                       System.out.println("---------completed work order----------");
                       System.out.println("work order: " + order.getId() + " description: "  + order.getDescription() + " submitted by: " + order.getSenderName() + " status" + order.getStatus());

                       f.delete();
                   }
                }catch(IOException ex){
                    ex.printStackTrace();
                }
            }
        }
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


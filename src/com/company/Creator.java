package com.company;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Creator {
    public void createWorkOrders() {
        Scanner scanner =  new Scanner(System.in);

        System.out.println("Enter your name");
        String workName = scanner.nextLine();
        System.out.println("Enter work description");
        String workDes = scanner.nextLine();
        System.out.println("Enter a new 5 digit work order number");
        int workId = scanner.nextInt();

        WorkOrder createOrder = new WorkOrder();
        createOrder.setDescription(workDes);
        createOrder.setId(workId);
        createOrder.setSenderName(workName);
        createOrder.setStatus(Status.INITIAL);

        String jsonOrder= "";
        try{
            ObjectMapper mapper = new ObjectMapper();
            jsonOrder = mapper.writeValueAsString(createOrder);
        }catch (IOException ex){
            ex.printStackTrace();
        }
        try {
            File jsonFile = new File(createOrder.getId() + ".json");
            FileWriter newFile = new FileWriter(jsonFile);
            newFile.write(jsonOrder);
            newFile.close();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }


        public static void main (String args[]){
            while (true) {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Enter 'N' to create a new work order");
                String newWork = scanner.nextLine().toUpperCase();
                if (newWork.contentEquals("N")) {
                    Creator creator = new Creator();
                    creator.createWorkOrders();
                }
            }
        }

    }
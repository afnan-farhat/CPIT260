package cpit260;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class CPIT260 {

    public static ArrayList<MemoryBlock> blocks = new ArrayList<>();

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        MemoryManager manager = new MemoryManager(1024); // 1024 KB of total memory

        int count = 0; // count of process like P1 , P2, P3
        int memorySizeFree = 10;
        boolean isFree = true;
        int processNo = 1;
        int startAddress = 0;
        int size = 0;
        String processName = " ";
        int countOfEnterLoop = 0;

        // Full to memory 
        while (memorySizeFree != 0) {
            ++countOfEnterLoop;

            if (countOfEnterLoop == 1) {
                isFree = false;
                processName = "OS";
                //size of OS
                size = 3;
            } else {
                isFree = false;

                // Add process name in linked list 
                processName = "P" + processNo;
                ++processNo;

                // Add randome size to process between 1 to 5
                Random randomNum = new Random();
                size = randomNum.nextInt(5);
            }

            MemoryBlock b = new MemoryBlock(processName, startAddress, size, isFree);
            blocks.add(b);

            // Calculate to startAddress
            startAddress += size;

            memorySizeFree -= size;
        }

        // Display all process 
        System.out.println("Processes in memory: ");
        for (MemoryBlock precess : blocks) {
            System.out.print(precess.getProcessName() + ", ");
        }
        System.out.println();

        // Deallocate 
        System.out.println("Enter process name to deallocate:");
        processName = scanner.next();
        scanner.nextLine(); // consume newline
        //Display information that process that you deallocate it
        for (MemoryBlock process : blocks) {
            if (process.getProcessName().equals(processName)) {
                System.out.println("Process information");
                System.out.println(process.toString());
            }
        }
        manager.deallocate(processName);
        
        // Display all process after deallocate
        System.out.println("Processes in memory: ");
        for (MemoryBlock precess : blocks) {
            System.out.print(precess.getProcessName() + ", ");
        }
        System.out.println();

//        // Algorithim 'first fit' or 'best fit'
//        while (true) {
//            System.out.println("\nEnter 'allocate' or 'deallocate':");
//            String command = scanner.nextLine();
//
//            if (command.equalsIgnoreCase("allocate")) {
//                System.out.println("Enter algorithm ('first fit' or 'best fit'):");
//
//                String algorithm = scanner.next();
//                scanner.nextLine(); // consume newline
//                manager.allocate(processName, size, algorithm);
//            } else if (command.equalsIgnoreCase("deallocate")) {
//                System.out.println("Enter process name to deallocate:");
//                processName = scanner.next();
//                scanner.nextLine(); // consume newline
//                manager.deallocate(processName);
//            } else {
//                System.out.println("Invalid command.");
//                continue;
//            }
//
//            manager.displayMemory();
//        }
    }
}

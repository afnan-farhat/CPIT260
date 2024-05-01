package javaapplication8;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;
import java.util.InputMismatchException;

public class CPIT2600 {

    public static ArrayList<MemoryBlock> blocks = new ArrayList<>();

    public static void main(String[] args) {

        int memorySize = 10;
        int osSize = 3;

        // Add Operating System to the blocks list
        MemoryBlock os = new MemoryBlock("OS", 0, osSize, false);
        blocks.add(os);
        int processNum = 0;
        // Add random-sized processes to fill up the remaining memory
        int startAddress = osSize; //اول بروسز بعد الاوبريتنق تبدا من اندكس 3 
        Random randomNum = new Random();
        while (startAddress < memorySize) { // لوب عشان نملي الميموري 
            String processName = "P" + (processNum); // P1, P2, P3, ...
            int CurrentSize = memorySize - startAddress; //10-3=7 مساحةالميموري بعد كل بروسز تنضاف
            int size = randomNum.nextInt(CurrentSize) + 1; // راندوم نمبرز بين واحد والحجم الجديد للميموري
            MemoryBlock process = new MemoryBlock(processName, startAddress, size, false);
            blocks.add(process);
            startAddress += size;// ex: 3+4=7 
            processNum++;
        }
        /* while (true) {
            // Display all processes in memory
            System.out.println("Processes in memory:");
            for (MemoryBlock block : blocks) {
                System.out.println(block);
            }
Scanner scanner=new Scanner(System.in);
            // Ask user to choose allocate or deallocate process
            System.out.print("Choose action (1 - Allocate, 2 - Deallocate, 0 - Exit): ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            if (choice == 0) {
                break;
            }

            if (choice == 1) {
                System.out.print("Enter process name: ");
                String processName = scanner.nextLine();

                // Ask user to choose allocation algorithm
                System.out.print("Choose allocation algorithm (1 - First Fit, 2 - Best Fit): ");
                int algorithmChoice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                if (algorithmChoice == 1) {
                    // Allocate using First Fit algorithm
                    allocateFirstFit(blocks, processName);
                } else if (algorithmChoice == 2) {
                    // Allocate using Best Fit algorithm
                    // Implement Best Fit allocation method
                    // allocateBestFit(blocks, processName);
                } else {
                    System.out.println("Invalid choice. Please choose a valid algorithm.");
                }
            } else if (choice == 2) {
                // Deallocate a process
                System.out.print("Enter the process name to deallocate: ");
                String processToDeallocate = scanner.nextLine();

                MemoryBlock deallocatedBlock = deallocateProcess(blocks, processToDeallocate);

                if (deallocatedBlock != null) {
                    System.out.println("Process deallocated successfully:");
                    System.out.println("Deallocated Process Info: " + deallocatedBlock);
                } else {
                    System.out.println("Process not found in memory.");
                }
            } else {
                System.out.println("Invalid choice. Please choose a valid action.");
            }
            
        }
         */
        while (true) {// لوب مانخرج منها لين اليوزر يسوي (exit)

            Scanner scanner = new Scanner(System.in);
            // Display all processes in memory
            System.out.println("Processes in memory:");
            for (MemoryBlock block : blocks) { // loop عشان نطبع كل البروسز الي بالميموري
                System.out.println(block);
            }

            // Ask user to choose allocate or deallocate process
            System.out.print("Choose action (1 - Allocate, 2 - Deallocate, 0 - Exit): ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (choice == 0) {
                break;
            }

            if (choice == 1) {

                System.out.print("Enter process name: ");
                String processName = scanner.nextLine();

                // Ask user to choose algorithm
                System.out.print("Choose allocation algorithm ( (1) for First Fit, (2) for Best Fit): ");
                int algorithmChoice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                Random random = new Random();

                int processSize = random.nextInt(7) + 1; // Random size between 1 and maxSize 

                System.out.println("Process size: " + processSize);

                if (algorithmChoice == 1) {
                    // Allocate using First Fit algorithm
                    allocateFirstFit(blocks, processName, processSize);

                } else if (algorithmChoice == 2) {
                    // Allocate using Best Fit algorithm
                    allocateBestFit(blocks, processName, processSize);

                } else {
                    System.out.println("Invalid choice. Please choose a valid algorithm.");
                }

            } else if (choice == 2) {
                // Deallocate a process
                System.out.print("Enter the process name to deallocate: ");
                String processToDeallocate = scanner.nextLine();

                MemoryBlock deallocatedBlock = deallocateProcess(blocks, processToDeallocate);

                if (deallocatedBlock != null) {
                    System.out.println("Process deallocated successfully:");
                    System.out.println("Deallocated Process Info: " + deallocatedBlock);
                } else {
                    System.out.println("Process not found in memory.");
                }
            } else {
                System.out.println("Invalid choice. Please choose a valid action.");
            }
        }
    }

//ميثود الحذف
    public static MemoryBlock deallocateProcess(ArrayList<MemoryBlock> blocks, String processName) {
        for (MemoryBlock block : blocks) {
            if (block != null && block.processName.equals(processName)) {//يشيك على اسم البروسز وانها ماتساوي نل
                MemoryBlock deallocatedBlock = block; //سوينا اوبجكت عشان نتعامل مع بيانات البروسز 
                deallocatedBlock.processName = "HOLE";
                deallocatedBlock.setFree(true);

                // Merge adjacent "HOLE" blocks
                mergeAdjacentHoles(blocks);

                return deallocatedBlock;
            }

        }

        return null; // Process not found
    }

    public static MemoryBlock allocateFirstFit(ArrayList<MemoryBlock> blocks, String processName, int processSize) {
        int totalOccupiedSize = getTotalOccupiedSize(blocks);

        for (MemoryBlock block : blocks) {
            if (block.processName.equals("HOLE") && block.size >= processSize && block.isFree == true) {
                int newTotalOccupiedSize = totalOccupiedSize + processSize;//############### 

                if (newTotalOccupiedSize <= 10) {
                    int NewSize = X(blocks, processSize);

                    if (NewSize == block.size) {
                        block.processName = processName;
                        block.setFree(false);
                        block.setSize(NewSize);

                    } else if (NewSize == -1) {
                        break;
                    } else {
                        String oldProcess = block.processName; //oldHole
                        int oldSAdress = block.startAddress;
                        int oldSize = block.size;
                        Boolean oldState = block.isFree;

                        blocks.remove(block);
                        MemoryBlock newProcess = new MemoryBlock(processName, oldSAdress, NewSize, false);
                        MemoryBlock newHole = new MemoryBlock("HOLE", NewSize + oldSAdress, oldSize - NewSize, true); //startAddress += size

                        blocks.add(newProcess);
                        blocks.add(newHole);

                        Collections.sort(blocks, Comparator.comparingInt(MemoryBlock::getStartAddress));

                    }

                    return block;
                }
            }
        }

        System.out.println("Can't add the process because no place");
        return null;
    }

    private static int getTotalOccupiedSize(ArrayList<MemoryBlock> blocks) {
        int totalOccupiedSize = 0;
        for (MemoryBlock block : blocks) {
            if (!(block.isFree())) {// نشيك اذا مكان البروسز فري ولالا 
                totalOccupiedSize += block.size; // اذامو فري نجمع السايز 
            }
        }
        return totalOccupiedSize;
    }

    private static void mergeAdjacentHoles(ArrayList<MemoryBlock> blocks) {
        for (int i = 0; i < blocks.size() - 1; i++) {
            MemoryBlock currentBlock = blocks.get(i);
            MemoryBlock nextBlock = blocks.get(i + 1);

            if (currentBlock.processName.equals("HOLE") && nextBlock.processName.equals("HOLE")) {
                // Merge adjacent "HOLE" blocks
                int mergedSize = currentBlock.size + nextBlock.size;
                currentBlock.size = mergedSize;
                blocks.remove(nextBlock);
                i--; // Decrement i to avoid skipping the next block after removal
            }
        }

    }

    private static int X(ArrayList<MemoryBlock> blocks, int processSize) {
        int newHole = 0;
        for (MemoryBlock block : blocks) {

            if (block.isFree()) {
                if (processSize <= block.size) {
                    break;

                } else {
                    return -1;

                }
            }

        }
        return processSize;
    }

    //---------------------------------------- bestFit
    public static MemoryBlock allocateBestFit(ArrayList<MemoryBlock> blocks, String processName, int processSize) {

        Collections.sort(blocks, Comparator.comparingInt(MemoryBlock::getSize));
        int totalOccupiedSize = getTotalOccupiedSize(blocks);

        for (MemoryBlock block : blocks) {
            if (block.processName.equals("HOLE") && block.size >= processSize && block.isFree == true) {
                int newTotalOccupiedSize = totalOccupiedSize + processSize;

                if (newTotalOccupiedSize <= 10) {
                    int NewSize = X(blocks, processSize);

                    if (NewSize == block.size) {
                        block.processName = processName;
                        block.setFree(false);
                        block.setSize(NewSize);

                    } else if (NewSize == -1) {
                        break;
                    } else {
                        String oldProcess = block.processName; //oldHole
                        int oldSAdress = block.startAddress;
                        int oldSize = block.size;
                        Boolean oldState = block.isFree;

                        blocks.remove(block);
                        MemoryBlock newProcess = new MemoryBlock(processName, oldSAdress, NewSize, false);
                        MemoryBlock newHole = new MemoryBlock("HOLE", NewSize + oldSAdress, oldSize - NewSize, true); //startAddress += size

                        blocks.add(newProcess);
                        blocks.add(newHole);

                    }
                    Collections.sort(blocks, Comparator.comparingInt(MemoryBlock::getStartAddress));
                    return block;
                }

            }

        }
        Collections.sort(blocks, Comparator.comparingInt(MemoryBlock::getStartAddress));
        System.out.println("Can't add the process because no place");
        return null;

    }

}

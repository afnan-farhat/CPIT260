package javaapplication8;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class CPIT2600 {

    public static ArrayList<MemoryBlock> blocks = new ArrayList<>();

    public static void main(String[] args) {

        int memorySize = 10;
        int osSize = 3;

        // Add Operating System to the blocks list
        MemoryBlock os = new MemoryBlock("OS", 0, osSize, false);
        blocks.add(os);

        // Add random-sized processes to fill up the remaining memory
        int startAddress = osSize;
        Random randomNum = new Random();
        while (startAddress < memorySize) {
            String processName = "P" + (blocks.size() - 1); // P1, P2, P3, ...
            int maxSize = memorySize - startAddress;
            int size = randomNum.nextInt(maxSize) + 1; // Generate random size between 1 and maxSize
            MemoryBlock process = new MemoryBlock(processName, startAddress, size, false);
            blocks.add(process);
            startAddress += size;
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
        while (true) {
             Scanner scanner = new Scanner(System.in);
            // Display all processes in memory
            System.out.println("Processes in memory:");
            for (MemoryBlock block : blocks) {
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

                // Generate a random size for the process
                Random random = new Random();
                int maxSize = 10 - getTotalOccupiedSize(blocks); // Remaining available memory
                int processSize = random.nextInt(maxSize) + 1; // Random size between 1 and maxSize

                System.out.println("Process size: " + processSize);

                // Allocate using First Fit algorithm
                allocateFirstFit(blocks, processName, processSize);
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

  public static MemoryBlock deallocateProcess(ArrayList<MemoryBlock> blocks, String processName) {
    for (MemoryBlock block : blocks) {
        if (block != null && block.processName.equals(processName)) {
            MemoryBlock deallocatedBlock = block;
            deallocatedBlock.processName = "HOLE";
            deallocatedBlock.occupied = false;

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
        if (block.processName.equals("HOLE") && block.size >= processSize) {
            int newTotalOccupiedSize = totalOccupiedSize - block.size + processSize;

            if (newTotalOccupiedSize <= 10) {
                if (block.size == processSize) {
                    block.processName = processName;
                    block.occupied = true;
                } else {
                    block.processName = processName;
                    block.occupied = true;

                    MemoryBlock newHole = new MemoryBlock("HOLE", block.startAddress + processSize, block.size - processSize, false);
                    blocks.add(blocks.indexOf(block) + 1, newHole);

                    block.size = processSize;
                }

                mergeAdjacentHoles(blocks);

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
            if (block != null && block.occupied) {
                totalOccupiedSize += block.size;
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
}



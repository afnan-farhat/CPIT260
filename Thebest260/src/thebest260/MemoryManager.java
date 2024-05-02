package thebest260;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class MemoryManager {

// List to store memory blocks
    private final ArrayList<MemoryBlock> blocks = new ArrayList<>();
// Total memory size
    private final int memorySize = 10;
// Size reserved for the operating system
    private final int osSize = 3;
    private int processNum = 0;

    // Constructor for the MemoryManager class
    public MemoryManager() {
        // Creating a memory block for the operating system
        MemoryBlock os = new MemoryBlock("OS", 0, osSize, false);
        // Adding the OS memory block to the list of blocks
        blocks.add(os);
        // Filling the remaining memory with available blocks
        fillRemainingMemory();
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {

            displayMemory();
            System.out.println("_________________________________________________________________");
            System.out.print("Choose action (1 - Allocate, 2 - Deallocate, 0 - Exit): ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine();

                if (choice == 0) {
                    // Exit the loop if the user chooses to exit
                    break;
                }

                if (choice == 1) {
                    // Allocate memory if the user chooses allocation
                    Print_allocateProcess(scanner);

                } else if (choice == 2) {
                    // Deallocate memory if the user chooses deallocation
                    Print_deallocateProcess(scanner);

                } else {
                    // Display error message for invalid choice
                    System.out.println("\n **** Invalid choice. Please choose a valid action. ****  \n");
                }
            } catch (InputMismatchException e) {
                // Display error message for invalid input
                System.out.println("\n **** Invalid input. Please choose a number ****  \n");
                scanner.nextLine();
            }
        }

        scanner.close();
    }

    // Method to fill the remaining memory with process blocks
    private void fillRemainingMemory() {
        // Start address for filling memory after the OS
        int base = osSize;
        // Random number generator for process sizes
        Random randomNum = new Random();
        // Loop until the remaining memory is filled
        while (base < memorySize) {
            // Generate a process name
            String processName = "P" + processNum;
            // Calculate the current available size of memory
            int currentSize = memorySize - base;
            // Generate a random size for the process
            int size = randomNum.nextInt(currentSize) + 1;
            // Create object of a memory block for the process
            MemoryBlock process = new MemoryBlock(processName, base, size, false);
            // Add the object process block to the list of blocks
            blocks.add(process);
            // Update the start address for the next process
            base += size;
            // Increment the process number for the next process
            processNum++;
        }
    }

    // Method to display the all process in memory 
    private void displayMemory() {
        System.out.println("_________________________________________________________________");
        System.out.println("Processes in memory:");
        for (MemoryBlock block : blocks) {
            System.out.println(block);
        }
    }

    // Method to allocate memory for a process
    private void allocateProcess(String processName, int processSize, Comparator<MemoryBlock> comparator) {
        // Calculate the total occupied size of memory
        int totalOccupiedSize = getTotalOccupiedSize();
        // loop for memory blocks to find a hole for allocation
        for (MemoryBlock block : blocks) {
            if (block.getProcessName().equals("HOLE") && block.getSize() >= processSize && block.getIsFree()) {
                // Calculate the new total occupied size if the process is allocated
                int newTotalOccupiedSize = totalOccupiedSize + processSize;

                // Check if the new total occupied size is within limits
                if (newTotalOccupiedSize <= 10) {
                    // Allocate the process

                    // If process size matches block size
                    if (processSize == block.getSize()) {
                        block.setProcessName(processName);
                        block.setIsFree(false);
                        block.setSize(processSize);

                        // If process size is greater than block size
                    } else if (processSize > block.getSize()) {
                        break;
                    } else { // If process size is less than block size

                        // Remove the current block
                        blocks.remove(block);
                        // Create new memory blocks for process and hole **the process takes only the space it needs and leaves the rest empty** 
                        MemoryBlock newProcess = new MemoryBlock(processName, block.getBase(), processSize, false);
                        MemoryBlock newHole = new MemoryBlock("HOLE", processSize + block.getBase(), block.getSize() - processSize, true);
                        // Add the new blocks to the list
                        blocks.add(newProcess);
                        blocks.add(newHole);

                    }
                    // Sort the blocks based on base address
                    Collections.sort(blocks, Comparator.comparingInt(MemoryBlock::getBase));
                    // Return, as allocation is done
                    return;
                }
            }
        }
        // If no suitable hole is found, print a message
        System.out.println("\n **** Can't add the process because no place ****\n Required size:" + processSize);
        Collections.sort(blocks, Comparator.comparingInt(MemoryBlock::getBase));
    }

    // First Fit Allocation method
    private void allocateFirstFit(String processName, int processSize) {
        allocateProcess(processName, processSize, Comparator.comparingInt(MemoryBlock::getBase));
    }

    // Best Fit Allocation method
    private void allocateBestFit(String processName, int processSize) {
        Collections.sort(blocks, Comparator.comparingInt(MemoryBlock::getSize)); // Sort by size
        allocateProcess(processName, processSize, Comparator.comparingInt(MemoryBlock::getBase));
    }

    // Deallocation method
    private MemoryBlock deallocateProcess(String processName) {

        for (MemoryBlock block : blocks) {
            // Check if the process name is "OS"
            if (processName.equalsIgnoreCase("OS")) {
                // Print a message indicating OS cannot be deleted
                System.out.println("\n !!! YOU CAN'T DELETE THE OPERATING SYSTEM !!! \n");

                return null;

            } else if (!(processName.equalsIgnoreCase("HOLE"))) {  // Check if the block is not a hole and matches the given process name
                if (block != null && block.getProcessName().equalsIgnoreCase(processName)) {

                    // Deallocate the block
                    MemoryBlock deallocatedBlock = block;
                    deallocatedBlock.setProcessName("HOLE");
                    deallocatedBlock.setIsFree(true);
                    mergeAdjacentHoles();  // Merge adjacent holes after deallocation
                    return deallocatedBlock;
                }

            } else {   // Check if the block is a hole

                // Print a message indicating it's a hole
                System.out.println("\n **** Its Hole **** \n ");
                return null;
            }

        }

        // If the process is not found in memory, print a message
        System.out.println("\n Process not found in memory.\n");

        return null;
    }

    // Method to print info of allocate a process
    private void Print_allocateProcess(Scanner scanner) {
        System.out.print("\n Enter process number:");
        int processNum = scanner.nextInt();

        // Create process name based on process number
        String processName = "P" + processNum;
        for (MemoryBlock block : blocks) {
            if (processName.equals(block.getProcessName())) {
                System.out.println("\n **** This process already in memory **** \n");
                return;
            }
        }

        System.out.print("\nChoose allocation algorithm (1 - First Fit, 2 - Best Fit): ");

        try {
            int algorithmChoice = scanner.nextInt();
            scanner.nextLine();

            // Generate random process size
            int processSize = new Random().nextInt(7) + 1;// (from 1 to 7) because the OS get 3 size from the memory so the remaining memory space is 7
            System.out.println("Process Name: P" + processNum + " | Process Size: " + processSize);

            // Allocate process based on algorithm choice
            if (algorithmChoice == 1) {
                allocateFirstFit(processName, processSize);
            } else if (algorithmChoice == 2) {
                allocateBestFit(processName, processSize);
            } else {
                //if enter the other numbers
                System.out.println("\n **** Invalid choice. Please choose a valid algorithm. ****  \n");
            }
        } catch (InputMismatchException e) {
            //if enter the letters 
            System.out.println("\n **** Invalid input. Please choose a number **** \n");
            scanner.nextLine();
        }
    }

    private void Print_deallocateProcess(Scanner scanner) {
        System.out.print("\n Enter the process name to deallocate: ");
        String processToDeallocate = scanner.nextLine();
        MemoryBlock deallocatedBlock = deallocateProcess(processToDeallocate);

        // If deallocated block is not null, print success message and hole info
        if (deallocatedBlock != null) {
            System.out.println("\n_________________________________________________________________\n");
            System.out.println("Process deallocated successfully:     ");
            System.out.println("\n" + "Hole Info: " + deallocatedBlock + "     ");

        }
    }

    // Helper method to get the total occupied size
    private int getTotalOccupiedSize() {
        int totalOccupiedSize = 0;
        for (MemoryBlock block : blocks) {

            // Check if the block is not free
            if (!block.getIsFree()) {

                // Add the size of the block to the total occupied size
                totalOccupiedSize += block.getSize();
            }
        }
        return totalOccupiedSize;
    }

    // Method to merge adjacent "HOLE" blocks
    private void mergeAdjacentHoles() {
        for (int i = 0; i < blocks.size() - 1; i++) {
            MemoryBlock currentBlock = blocks.get(i);
            MemoryBlock nextBlock = blocks.get(i + 1);

            // Check if both current and next blocks are holes
            if (currentBlock.getProcessName().equals("HOLE") && nextBlock.getProcessName().equals("HOLE")) {

                // Calculate the merged size of the two holes
                int mergedSize = currentBlock.getSize() + nextBlock.getSize();

                // Set the size of the current block to the merged size
                currentBlock.setSize(mergedSize);

                // Remove the next block
                blocks.remove(nextBlock);
                i--; // Decrement i to avoid skipping the next block after removal
            }
        }
    }

}

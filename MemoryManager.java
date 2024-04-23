package cpit260;
//
//import java.util.LinkedList;
//
//public class MemoryManager {
//
//    private LinkedList<MemoryBlock> blocks;
//    private int totalMemorySize;
//
//    public MemoryManager(int totalMemorySize) {
//        this.totalMemorySize = totalMemorySize;
//        blocks = new LinkedList<>();
//        blocks.add(new MemoryBlock(0, totalMemorySize, true)); // Start with one free block
//    }
//
//    public void allocate(String processName, int size, String algorithm) {
//        if (algorithm.equalsIgnoreCase("First fit")) {
//        } else if (algorithm.equalsIgnoreCase("Best fit")) {
//        } else {
//        }
//    }
//
//    public void deallocate(MemoryBlock processName) {
//        // Implement deallocation logic here
//        if (blocks.contains(processName)) {
//            blocks.remove(processName);
//        } else {
//            System.out.println("Error msg");
//        }
//
//    }
//
//    // Additional methods to merge free blocks, etc.
//}

import static cpit260.CPIT260.blocks;
import java.util.ArrayList;
import java.util.ListIterator;

public class MemoryManager {

    private int totalMemorySize;

    public MemoryManager(int totalMemorySize) {
        this.totalMemorySize = totalMemorySize;
        blocks = new ArrayList<>();
        blocks.add(new MemoryBlock("", 0, totalMemorySize, true)); // Start with one large free block
    }

    public void allocate(String processName, int size, String algorithm) {
        switch (algorithm.toLowerCase()) {
            case "first fit":
                allocateFirstFit(processName, size);
                break;
            case "best fit":
                allocateBestFit(processName, size);
                break;
            default:
                System.out.println("Invalid allocation algorithm. Please use 'first fit' or 'best fit'.");
        }
    }

    private void allocateFirstFit(String processName, int size) {
        ListIterator<MemoryBlock> iterator = blocks.listIterator();

        while (iterator.hasNext()) {
            MemoryBlock block = iterator.next();
            if (block.isFree() && block.getSize() >= size) {
                iterator.remove();
                MemoryBlock newBlock = new MemoryBlock(processName, block.getStartAddress(), size, false);
                newBlock.setProcessName(processName);
                iterator.add(newBlock);

                if (block.getSize() > size) {
                    MemoryBlock remainingBlock = new MemoryBlock(processName, block.getStartAddress() + size, block.getSize() - size, true);
                    iterator.add(remainingBlock);
                }
                System.out.println("Allocated using First Fit: " + newBlock);
                return;
            }
        }
        System.out.println("Allocation failed. No suitable block found.");
    }

    private void allocateBestFit(String processName, int size) {
        MemoryBlock bestBlock = null;
        int bestBlockSize = Integer.MAX_VALUE;
        ListIterator<MemoryBlock> iterator = blocks.listIterator();
        int index = -1, bestIndex = -1;

        while (iterator.hasNext()) {
            index++;
            MemoryBlock block = iterator.next();
            if (block.isFree() && block.getSize() >= size && block.getSize() < bestBlockSize) {
                bestBlock = block;
                bestBlockSize = block.getSize();
                bestIndex = index;
            }
        }

        if (bestBlock != null) {
            iterator = blocks.listIterator(bestIndex);
            bestBlock = iterator.next();
            iterator.remove();
            MemoryBlock newBlock = new MemoryBlock(processName,bestBlock.getStartAddress(), size, false);
            newBlock.setProcessName(processName);
            iterator.add(newBlock);

            if (bestBlock.getSize() > size) {
                MemoryBlock remainingBlock = new MemoryBlock(processName,bestBlock.getStartAddress() + size, bestBlock.getSize() - size, true);
                iterator.add(remainingBlock);
            }
            System.out.println("Allocated using Best Fit: " + newBlock);
        } else {
            System.out.println("Allocation failed. No suitable block found.");
        }
    }

    public void deallocate(String processName) {
        ListIterator<MemoryBlock> iterator = blocks.listIterator();
        boolean found = false;

        while (iterator.hasNext()) {
            MemoryBlock block = iterator.next();
            if (!block.isFree() && block.getProcessName().equals(processName)) {
                block.setFree(true);
                block.setProcessName(null);
                found = true;
                System.out.println("Deallocated succesfully");
                break;
            }
        }

        if (!found) {
            System.out.println("Deallocate failed. No process found with name: " + processName);
        }
        mergeFreeBlocks();
    }

    private void mergeFreeBlocks() {
        if (blocks.size() > 1) {
            ListIterator<MemoryBlock> iterator = blocks.listIterator();
            MemoryBlock current = iterator.
                    next();

            while (iterator.hasNext()) {
                MemoryBlock next = iterator.next();
                if (current.isFree() && next.isFree()) {
                    current = new MemoryBlock("",current.getStartAddress(), current.getSize() + next.getSize(), true);
                    iterator.remove();  // Remove next
                    iterator.previous(); // Move back to current
                    iterator.set(current); // Replace current with merged
                } else {
                    current = next;
                }
            }
        }
    }

    public void displayMemory() {
        System.out.println("Total memory: " + totalMemorySize + "KB");
        for (MemoryBlock block : blocks) {
            System.out.println(block);
        }
    }
}

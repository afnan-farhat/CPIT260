package cpit260;
//
//
//public class MemoryBlock {
//
//    private MemoryBlock processName; // Null if block is free
//    private int startAddress;
//    private int size;
//    private boolean isFree;
//
//    public MemoryBlock(int startAddress, int size, boolean isFree) {
//        this.startAddress = startAddress;
//        this.size = size;
//        this.isFree = isFree;
//    }
//
//    // Getters and setters
//}

public class MemoryBlock {

    private String processName;
    private int startAddress;
    private int size;
    private boolean isFree;

    public MemoryBlock(String processName, int startAddress, int size, boolean isFree) {
        this.startAddress = startAddress;
        this.size = size;
        this.isFree = isFree;
        this.processName = processName;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public int getStartAddress() {
        return startAddress;
    }

    public int getSize() {
        return size;
    }

    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean free) {
        isFree = free;
    }

    public String toString() {
        //return (isFree ? "Free " : "Used by " + processName) + " Block at " + startAddress + " of size " + size;
        return "process name: " + processName + " \tstartAddress: " + startAddress + " \tsize: " + size + "\n";
    }
}

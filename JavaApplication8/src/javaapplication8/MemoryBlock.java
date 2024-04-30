package javaapplication8;

class MemoryBlock {

    String processName;
    int startAddress;
    int size;
    boolean isFree=true;
    boolean occupied;
    int remainingSize;
    int i;
    boolean b;
    String string;

    public MemoryBlock(String processName, int startAddress, int size, boolean isFree) {
        this.processName = processName;
        this.startAddress = startAddress;
        this.size = size;
        this.isFree = isFree;
    }

    MemoryBlock(int remainingSize, int i, boolean b, String string) {
        this.remainingSize = remainingSize;
        this.i = i;
        this.b = b;
        this.string = string;
    }


@Override
        public String toString() {
        return "Process Name: " + processName + " | Start Address: " + startAddress + " | Size: " + size + " | Free: " + isFree;
    }

    public boolean isFree() {
        return isFree;
    }

    public int getSize() {
        return size;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

 

    public void setSize(int size) {
        this.size = size;
    }
    
    public void setFree(Boolean isFree){
        this.isFree=isFree;
    }

    int getStartAddress() {
       return startAddress;
    }
}

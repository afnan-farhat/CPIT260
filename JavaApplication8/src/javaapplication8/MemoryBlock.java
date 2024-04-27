package javaapplication8;


class MemoryBlock {

    String processName;
    int startAddress;
    int size;
    private boolean isFree;
 boolean occupied;
 
    public MemoryBlock(String processName, int startAddress, int size, boolean isFree) {
        this.processName = processName;
        this.startAddress = startAddress;
        this.size = size;
        this.isFree = isFree;
    }

    MemoryBlock(int remainingSize, int i, boolean b, String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String toString() {
        return "Process Name: " + processName + " | Start Address: " + startAddress + " | Size: " + size + " | Free: " + isFree;
    }
public boolean isOccupied() {
        return occupied;
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

    int getAddress() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   public void setSize(int size) {
    this.size = size;
}
}
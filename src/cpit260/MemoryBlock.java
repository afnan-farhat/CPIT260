package cpit260;

class MemoryBlock {

    private String ProcessName;
    private int getBase;
    private int size;
    private boolean isFree = true;

    public MemoryBlock(String processName, int getBase, int size, boolean isFree) {
        this.ProcessName = processName;
        this.getBase = getBase;
        this.size = size;
        this.isFree = isFree;
    }

    public String getProcessName() {
        return ProcessName;
    }

    public void setProcessName(String ProcessName) {
        this.ProcessName = ProcessName;
    }

    public int getBase() {
        return getBase;
    }

    public void setGetBase(int getBase) {
        this.getBase = getBase;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean getIsFree() {
        return isFree;
    }

    public void setIsFree(boolean isFree) {
        this.isFree = isFree;
    }

    @Override
    public String toString() {
        if(isFree==false){
            
            String state="allocated";
            return "Process Name: " + ProcessName + " | Base: " + getBase + " | Size: " + size + " | State: " + state;
        }
        
        String state="deallocated";
        return "Process Name: " + ProcessName + " | Base: " + getBase + " | Size: " + size + " | State: " + state;
    }
}
  
package net.ninebolt;

public abstract class Provider {    
    public abstract String getName();

    public abstract int calculateBill(int gasUsage, int electricityUsage);

    public int calculateGasStage(int usage, int[] stageTable) {
        if(usage == 0) {
            return -1;
        }
        for(int i=0; i<stageTable.length-1; i++) {
            if(stageTable[i] < usage && usage <= stageTable[i+1]) {
                return i;
            }
        }
        return -1;
    }
}

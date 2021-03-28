package net.ninebolt.provider;

import net.ninebolt.Provider;

public class NURO extends Provider {

    private final double[] gasBasicFeeTable = {935.46, 1222.38, 1393.39, 2033.02, 6300.61, 12265.05};
    private final double[] gasUnitFeeTable = {140.76, 126.42, 124.28, 121.08, 112.54, 105.09};
    private final int[] gasStageTable = {0, 20, 80, 200, 500, 800, Integer.MAX_VALUE};

    private final double[] elecBasicFeeTable = {767.66, 901.49, 1035.32, 1302.98, 1570.64, 1838.30, 2105.96};
    private final double[] elecUnitFeeTable = {19.78, 25.79, 30.57};
    private final int[] elecStageTable = {0, 120, 400, Integer.MAX_VALUE};
    private final double renewableEnergyFee = 2.98;

    private String name = "NURO光";

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int calculateBill(int gasUsage, int electricityUsage) {
        int gasBill = calculateGasBill(gasUsage) - 200;
        int elecBill = calculateElectricityBill(electricityUsage) - 501;
        System.out.println("ガス料金 " + gasBill + ", 電気料金 " + elecBill);

        return gasBill + elecBill;
    }

    public int calculateGasBill(int gasUsage) {
        double gasBill = 0.0;
        int gasStage = calculateGasStage(gasUsage, gasStageTable);
        System.out.println("ガス: ステージ" + gasStage + ", 基本料金" + gasBasicFeeTable[gasStage] + ", 基準単位料金" + gasUnitFeeTable[gasStage]);
        gasBill += gasBasicFeeTable[gasStage];
        gasBill += gasUnitFeeTable[gasStage] * gasUsage;
        // 電気セット割引
        gasBill -= Math.floor(gasBill*0.05);

        return (int) Math.floor(gasBill);
    }

    public int calculateElectricityBill(int electricityUsage) {
        int elecStage = 4; // 40A
        int elecUsage = 0;

        double elecBill = 0.0;
        int index = 0;
        System.out.println("電気: 基本料金" + elecBasicFeeTable[elecStage]);

        while(elecUsage <= electricityUsage) {
            elecUsage++;
            elecBill += elecUnitFeeTable[index];
            if(elecUsage >= elecStageTable[index+1]) {
                index++;
            }
        }
        
        //再生可能エネルギー発電促進賦課金
        int renewableFee = (int) Math.floor(renewableEnergyFee * (double)electricityUsage);
        System.out.println("再生可能エネルギー発電促進賦課金 " + renewableFee);
        elecBill += renewableFee;

        // ガスセット割引
        elecBill -= Math.floor(elecBill*0.005);
        
        return (int) Math.floor(elecBill);
    }
}

package net.ninebolt.provider;

import net.ninebolt.Provider;

public class Sanix extends Provider{
    private final double[] gasBasicFeeTable = {759.0, 1056.0, 1232.0, 1892.0, 6292.0, 12452.0};
    private final double[] gasUnitFeeTable = {145.31, 130.46, 128.26, 124.96, 116.16, 108.46};
    private final int[] gasStageTable = {0, 20, 80, 200, 500, 800, Integer.MAX_VALUE};

    private final double[] elecBasicFeeTable = {743.6, 929.5, 1115.4};
    private final double[] elecUnitFeeTable = {23.22};
    private final int[] elecStageTable = {0, Integer.MAX_VALUE};
    private final double renewableEnergyFee = 2.98;

    private final String name = "サニックスでんき";

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int calculateBill(int gasUsage, int electricityUsage) {
        int gasBill = calculateGasBill(gasUsage);
        int elecBill = calculateElectricityBill(electricityUsage);
        System.out.println("ガス料金 " + gasBill + ", 電気料金 " + elecBill);

        return gasBill + elecBill;
    }

    public int calculateGasBill(int gasUsage) {
        double gasBill = 0.0;
        int gasStage = calculateGasStage(gasUsage, gasStageTable);
        gasBill += gasBasicFeeTable[gasStage];
        gasBill += gasUnitFeeTable[gasStage] * (double)gasUsage;
        System.out.println("ガス: ステージ" + gasStage + ", 基本料金" + gasBasicFeeTable[gasStage] + ", 基準単位料金" + gasUnitFeeTable[gasStage]);
        gasBill -= Math.floor(gasBill*0.03);
        return (int) Math.floor(gasBill);
    }

    public int calculateElectricityBill(int electricityUsage) {
        int elecStage = 0; // 40A
        int usageCounter = 0;

        double elecBill = 0.0;
        double elecBasicFee = elecBasicFeeTable[elecStage];
        elecBill += elecBasicFee;
        System.out.println("電気: 基本料金" + elecBasicFee);
        
        int index = 0;
        while(usageCounter <= electricityUsage) {
            usageCounter++;
            elecBill += elecUnitFeeTable[index];
            if(usageCounter >= elecStageTable[index+1]) {
                index++;
            }
        }

        //再生可能エネルギー発電促進賦課金
        int renewableFee = (int) Math.floor(renewableEnergyFee * (double)electricityUsage);
        System.out.println("再生可能エネルギー発電促進賦課金 " + renewableFee);
        elecBill += renewableFee;
        
        return (int) Math.floor(elecBill);
    }
}
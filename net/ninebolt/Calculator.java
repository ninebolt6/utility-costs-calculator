package net.ninebolt;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import net.ninebolt.provider.NURO;
import net.ninebolt.provider.Sanix;
import net.ninebolt.provider.TokyoGas;

public class Calculator {
    private static List<Provider> list;
    public static void main(String[] args) {
        list = new ArrayList<Provider>();
        list.add(new NURO());
        list.add(new TokyoGas());
        list.add(new Sanix());

        Scanner sc = new Scanner(System.in);

        System.out.println("ひと月のガスの使用量(m^3)を入力してください");
        int gasUsage = sc.nextInt(); // m^3
        System.out.println("ひと月の電気の使用量(kWh)を入力してください");
        int elecUsage = sc.nextInt(); // kWh
        sc.close();
        System.out.println("----------");

        for(Provider p : list) {
            System.out.println(p.getName());
            System.out.println("合計 " + p.calculateBill(gasUsage, elecUsage) + "円\n");
        }
    }
}

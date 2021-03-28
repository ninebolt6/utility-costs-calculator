package net.ninebolt;

import java.util.ArrayList;
import java.util.List;

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

        for(Provider p : list) {
            System.out.println(p.getName() + ": " + p.calculateBill(37, 600) + "円\n");
        }
    }
}

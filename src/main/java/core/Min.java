package core;

import java.util.Arrays;

public class Min {
    public static void main(String[] args) {
        int[] numbers = {5, 2, 9, 1, 7, 4, 6, 3, 8, 10};
        String[] names = {"Борис", "Игорь", "Витек", "Потный Гарри", "Снежана", "Анжела"};
        var u = Arrays.stream(names).filter(x -> x.contains("Б")).findFirst().get();
        System.out.println(u);
    }


}

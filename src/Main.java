import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);

        String[] product = {"Хлеб", "Молоко", "Соль", "Масло подсолнечное",
                "Крупа гречневая", "Кефир", "Яйца", "Квас"};
        int[] prices = {100, 120, 60, 120, 90, 80, 90, 160};
        Basket basket = new Basket(product, prices);
        File textFile = new File("basket.bin");

        if (textFile.exists()) {
            basket = Basket.loadFromBinFile(textFile);
            System.out.println("Существует покупательская корзина:");
            basket.printCart();
        } else {
            System.out.println("Создайте покупательскую корзину:");
        }
        System.out.println("\nСписок возможных товаров для покупки:");
        for (int i = 0; i < product.length; i++) {
            System.out.println((i + 1) + "." + product[i] + " - " + prices[i] + " руб./шт.");
        }
        int productNum;
        int amount;

        while (true) {
            System.out.println("Выберите товар и количество или введите 'end':");
            String input = scanner.nextLine();
            if ("end".equals(input)) {
                break;
            } else {
                String[] parts = input.split(" ");
                productNum = Integer.parseInt(parts[0]) - 1;
                amount = Integer.parseInt(parts[1]);
                basket.addToCart(productNum, amount);
            }
        }
        basket.printCart();
        basket.saveBin(textFile);
    }
}
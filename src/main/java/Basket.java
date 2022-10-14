import com.google.gson.Gson;

import java.io.*;
import java.util.Scanner;

public class Basket {
    protected int[] basket;
    protected String[] product;
    protected int[] prices;

    public Basket(String[] product, int[] prices) {
        this.product = product;
        this.prices = prices;
        this.basket = new int[product.length];
    }

    public void addToCart(int productNum, int amount) {
        basket[productNum] += amount;
    }

    public void printCart() {
        int sumProduct = 0;
        System.out.println("Ваша корзина:");
        for (int i = 0; i < product.length; i++) {
            if (basket[i] != 0) {
                sumProduct += basket[i] * prices[i];
                System.out.println(product[i] + " " + basket[i] + " шт. " +
                        prices[i] + " руб./шт.  " + (basket[i] * prices[i]) + " рублей в сумме");
            }
        }
        System.out.println("Итого: " + sumProduct + " рублей.");
    }

    public void saveTxt(File textFile) {
        try (PrintWriter out = new PrintWriter(new FileWriter(textFile))) {
            for (String products : product) {
                out.write(products + ", ");
            }
            out.write("\n");
            for (int price : prices) {
                out.write(price + ", ");
            }
            out.write("\n");
            for (int baskets : basket) {
                out.print(baskets + ", ");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static Basket loadFromTxtFile(File textFile) {
        try (BufferedReader in = new BufferedReader(new FileReader(textFile))) {
            String[] product = in.readLine().split(", ");
            String[] priceString = in.readLine().split(", ");
            int[] prices = new int[priceString.length];
            for (int i = 0; i < prices.length; i++) {
                prices[i] = Integer.parseInt(priceString[i]);
            }
            Basket basket = new Basket(product, prices);
            String[] basketString = in.readLine().split(", ");
            for (int i = 0; i < basketString.length; i++) {
                basket.basket[i] = Integer.parseInt(basketString[i]);
            }
            return basket;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    //JSON-файл:
    public void saveJson(File jsonFile) throws IOException {
        try (PrintWriter out = new PrintWriter(new FileWriter(jsonFile))) {
            Gson gson = new Gson();
            String json = gson.toJson(this);
            out.println(json);
        }
    }

    public static Basket loadJson(File jsonFile) throws IOException {
        try (Scanner scanner = new Scanner(jsonFile)) {
            String json = scanner.nextLine();
            Gson gson = new Gson();
            return gson.fromJson(json, Basket.class);
        }
    }
}

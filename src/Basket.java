import java.io.*;

public class Basket implements Serializable {
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

    public void saveBin(File textFile) throws IOException {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(textFile))) {
            outputStream.writeObject(this);
        }
    }

    public static Basket loadFromBinFile(File textFile) throws IOException, ClassNotFoundException {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(textFile))) {
            Basket basket = (Basket) inputStream.readObject();
            return basket;
        }
    }
}
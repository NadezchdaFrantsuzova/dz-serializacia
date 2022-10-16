import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, SAXException,
            ParserConfigurationException, XPathExpressionException {
        Scanner scanner = new Scanner(System.in);

        String[] product = {"Хлеб", "Молоко", "Соль", "Масло подсолнечное",
                "Крупа гречневая", "Кефир", "Яйца", "Квас"};
        int[] prices = {100, 120, 60, 120, 90, 80, 90, 160};

        //shop.xml:
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse("chop.xml");
        XPath xPath = XPathFactory.newInstance().newXPath();
        String loadFileName = xPath
                .compile("/config/load/fileName")
                .evaluate(document); //basket.json
        boolean loadEnabled = Boolean.parseBoolean(xPath
                .compile("/config/load/enabled")
                .evaluate(document)); //false
        String loadFormat = xPath
                .compile("/config/load/format")
                .evaluate(document); //json
        String saveFileName = xPath
                .compile("/config/save/fileName")
                .evaluate(document); //basket.json
        boolean saveEnabled = Boolean.parseBoolean(xPath
                .compile("/config/save/enabled")
                .evaluate(document)); //true
        String saveFormat = xPath
                .compile("/config/save/format")
                .evaluate(document);
        String logFileName = xPath
                .compile("/config/log/fileName")
                .evaluate(document); //client.csv
        boolean logEnabled = Boolean.parseBoolean(xPath
                .compile("/config/log/enabled")
                .evaluate(document)); //true


        Basket basket = new Basket(product, prices);
        File textFile = new File("basket.txt");
        ClientLog clientLog = new ClientLog();
//        File txtFile = new File("log.scv");
//        File jsonFile = new File("basket.json");
        File txtFile = new File(logFileName);
        File jsonFile = new File(saveFileName);

        if (loadEnabled) {
            if (loadFormat.equals("json")) {
                if (jsonFile.exists()) {
                    basket = Basket.loadJson(jsonFile);
                    System.out.println("Существует покупательская корзина:");
                    basket.printCart();
                }
            } else if (loadFormat.equals("txt")) {
                if (textFile.exists()) {
                    basket = Basket.loadFromTxtFile(textFile);
                    System.out.println("Существует покупательская корзина:");
                    basket.printCart();
                }
            }
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

                clientLog.log(productNum, amount);
                basket.addToCart(productNum, amount);
            }
        }

        if (saveEnabled) {
            if (saveFormat.equals("json")) {
                basket.saveJson(jsonFile);
            } else {
                basket.saveTxt(textFile);
            }
        }
        if (logEnabled) {
            clientLog.exportAsCSV(txtFile);
        }

        basket.printCart();
    }
}
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class MenuItem {
    private String name;
    private double price;

    public MenuItem(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

class Menu {
    private List<MenuItem> menuItems;

    public Menu() {
        menuItems = new ArrayList<>();
        menuItems.add(new MenuItem("Coffee", 3.00));
        menuItems.add(new MenuItem("Tea", 2.50));
        menuItems.add(new MenuItem("Juice", 2.00));
        menuItems.add(new MenuItem("Pasta", 7.50));
        menuItems.add(new MenuItem("Burger", 6.50));
        menuItems.add(new MenuItem("Porridge", 5.50));
        menuItems.add(new MenuItem("Cake", 4.50));
        menuItems.add(new MenuItem("Pie", 4.00));
        menuItems.add(new MenuItem("Pudding", 3.50));
    }

    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }
}

public class BillingSystem {
    /*
    Print menu
    tax 0.0675
    tip >=0.1675
    Display ordered, tax, tip & total
     */

    private static double taxRate = 0.0675;
    private static double tipRate = 0.10675;

    private double beforeTax = 0.0;
    private double taxAmt = 0.0;
    private double tipAmt = 0.0;
    private double total = 0.0;
    private Menu menu;

    public static double getTaxRate() {
        return taxRate;
    }

    public static double getTipRate() {
        return tipRate;
    }

    public double getBeforeTax() {
        return beforeTax;
    }

    public double getTaxAmt() {
        return taxAmt;
    }

    public double getTipAmt() {
        return tipAmt;
    }

    public double getTotal() {
        return total;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setTaxRate(double taxRate) {
        BillingSystem.taxRate = taxRate;
    }

    public void setTipRate(double tipRate) {
        BillingSystem.tipRate = tipRate;
    }

    public void setBeforeTax(double beforeTax) {
        this.beforeTax = beforeTax;
    }

    public void setTaxAmt(double taxAmt) {
        this.taxAmt = taxAmt;
    }

    public void setTipAmt(double tipAmt) {
        this.tipAmt = tipAmt;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public void start() {
        printMenu();
        boolean orderComplete;
        do {
            orderComplete = collectOrder();
        } while(!orderComplete);
        processOrder();
    }

    private boolean collectOrder() {
        Scanner sc = new Scanner(System.in);
        int choice, quantity;
        do {
            System.out.println("Please select an item number. Press 0 to exit selection.");
            choice = sc.nextInt();
        } while (choice < 0 || choice > menu.getMenuItems().size());
        // done with order
        if (choice == 0) {
            return true;
        }
        // selected something
        else {
            do {
                System.out.println("You have selected item " + choice + " (" + menu.getMenuItems().get(choice - 1).getName()
                        + ").\nPlease enter the quantity you would like to purchase.");
                quantity = sc.nextInt();
            } while (quantity < 0);
            beforeTax += quantity * menu.getMenuItems().get(choice - 1).getPrice();
            return false;
        }

    }

    private void printMenu() {
        System.out.println("No. " + "Item     " + "Price");
        List<MenuItem> menuItems = menu.getMenuItems();
        int menuSize = menuItems.size();
        int pricePosition = 13;
        for (int i = 0; i < menuSize; i++) {
            String currentMenuItem = menuItems.get(i).getName();
            System.out.printf("%2d. %s", i+1, currentMenuItem);
            int padding = pricePosition - (currentMenuItem.length() + 4);
            for (int j = 0; j < padding; j++) {
                System.out.print(" ");
            }
            System.out.printf("$%.2f\n", menuItems.get(i).getPrice());
        }
    }

    private void processOrder() {
        if (beforeTax == 0) {
            System.out.println("Please come back once you have decided on your order.");
        }
        else {
            taxAmt = BillingSystem.taxRate * beforeTax;
            tipAmt = BillingSystem.tipRate * beforeTax;
            total = beforeTax + taxAmt + tipAmt;
            System.out.println("Thank you for your order. Here is the breakdown of your order:");
            System.out.println("");
            System.out.printf("Before taxes: $%.2f\n", beforeTax);
            System.out.printf("%.2f%% tax: $%.2f\n", taxRate*100, taxAmt);
            System.out.printf("Tips (%.0f%% of bill after taxes): $%.2f\n", -(tipRate*-1+taxRate)*100, tipAmt);
            System.out.println("Any additional tips will be greatly appreciated.");
            System.out.printf("Total bill: $%.2f", total);
        }
    }

    public static void main(String[] args) {
        BillingSystem billingSystem = new BillingSystem();
        Menu menu = new Menu();
        billingSystem.setMenu(menu);
        billingSystem.start();
    }
}

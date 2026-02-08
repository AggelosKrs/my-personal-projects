
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // 1. Δημιουργία του αντικειμένου repo
        ProductRepository repo = new ProductRepository();

        System.out.println("--- Smart Inventory System ---");
        System.out.println("Σύνδεση με τη βάση και ανάκτηση δεδομένων...");

        try {
            List<Product> products = repo.getAllProducts();
            
            if (products.isEmpty()) {
                System.out.println("⚠️ Η βάση είναι άδεια. Πρόσθεσε δεδομένα στον πίνακα 'product'.");
            } else {
                System.out.println("Βρέθηκαν " + products.size() + " προϊόντα:");
                System.out.println("--------------------------------------------------");
                for (Product p : products) {
                    // Χρησιμοποιούμε τα getters που έφτιαξες
                    System.out.printf("ID: %d | Προϊόν: %-15s | Τιμή: %.2f€ | Κατηγορία: %s%n", 
                                      p.getId(), p.getName(), p.getPrice(), p.getCategory());
                }
                System.out.println("--------------------------------------------------");
            }
        } catch (Exception e) {
            // Αν κάτι πάει στραβά (π.χ. λάθος κωδικός στη βάση), το πρόγραμμα δεν θα κλείσει απότομα
            System.err.println("❌ Σφάλμα κατά την επικοινωνία με τη βάση: " + e.getMessage());
        }
    }
}

public class Storage {

        private String productCategory;
        private int productPriority = 0;

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public Storage(String productCategory) {
        this.productCategory = productCategory;
    }

    // Kullanıcının girdiği ürün kategorisine göre kendimizin belirlediği öncelikler atanıyor.
    public int productCategoryControl(){
        if(productCategory.equalsIgnoreCase("Other")){
            return productPriority += 8;
        }
        else if(productCategory.equalsIgnoreCase("Textile")){
            return productPriority += 7;
        }
        else if(productCategory.equalsIgnoreCase("Furniture")){
            return productPriority += 6;
        }
        else if(productCategory.equalsIgnoreCase("Industrial")){
            return productPriority += 5;
        }
        else if(productCategory.equalsIgnoreCase("Electronics")){
            return productPriority += 4;
        }
        else if(productCategory.equalsIgnoreCase("Valuable")){
            return productPriority += 3;
        }
        else if(productCategory.equalsIgnoreCase("Foods")) {
            return productPriority += 2;
        }
        else if(productCategory.equalsIgnoreCase("Medicine")){
            return productPriority += 1;
        }
        else{
            System.out.println("Invalid product category");
            return 0;
        }
    }

}
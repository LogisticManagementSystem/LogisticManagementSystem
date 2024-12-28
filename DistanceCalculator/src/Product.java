class Product {
    private String category;
    private int priority;

    public Product(String category, int priority) {
        this.category = category;
        this.priority = priority;
    }

    public String getCategory() {
        return category;
    }

    public int getPriority() {
        return priority;
    }
}

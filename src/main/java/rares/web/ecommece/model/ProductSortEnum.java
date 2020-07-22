package rares.web.ecommece.model;

public enum ProductSortEnum {
    ID_ASC("ID Ascending", "id", "asc"),
    ID_DESC("ID Descending", "id", "desc"),
    NAME_ASC("Name Ascending", "name", "asc"),
    NAME_DESC("Name Descending", "name", "desc"),
    PRICE_ASC("Price Ascending", "price", "asc"),
    PRICE_DESC("Price Descending", "price", "desc");

    private final String displayValue;
    private final String productAttrName;
    private final String sortDirection;

    private ProductSortEnum(String displayValue, String productAttrName, String sortDirection) {
        this.displayValue = displayValue;
        this.productAttrName = productAttrName;
        this.sortDirection = sortDirection;
    }

    public String getDisplayValue() {
        return displayValue;
    }

    public String getProductAttrName() {
        return productAttrName;
    }

    public String getSortDirection() {
        return sortDirection;
    }
}

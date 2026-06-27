package attribute;

public class AttributeModifier {

    private final String source;
    private final Attribute attribute;
    private final ModifierOperation operation;
    private final double amount;

    public AttributeModifier(String source, Attribute attribute, ModifierOperation operation, double amount) {
        this.source = source;
        this.attribute = attribute;
        this.operation = operation;
        this.amount = amount;
    }

    public String getSource() {
        return source;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public ModifierOperation getOperation() {
        return operation;
    }

    public double getAmount() {
        return amount;
    }
}
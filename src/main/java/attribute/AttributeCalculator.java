package attribute;

import java.util.List;

public class AttributeCalculator {

    public double calculate(double baseValue, List<AttributeModifier> modifiers) {
        double flat = baseValue;
        double percent = 0;
        double multiplier = 1;

        for (AttributeModifier modifier : modifiers) {
            switch (modifier.getOperation()) {
                case ADD_FLAT:
                    flat += modifier.getAmount();
                    break;

                case ADD_PERCENT:
                    percent += modifier.getAmount();
                    break;

                case MULTIPLY:
                    multiplier *= modifier.getAmount();
                    break;
            }
        }

        return flat * (1 + percent / 100.0) * multiplier;
    }
}
package guru.qa.niffler.jupiter.extension;

import guru.qa.niffler.api.SpendApiClient;
import guru.qa.niffler.model.SpendJson;

import java.util.Objects;

public class SpendHttpExtension extends AbstractSpendExtension {

    private final SpendApiClient spendApiClient = new SpendApiClient();

    @Override
    protected SpendJson createSpend(SpendJson spend) {
        SpendJson newSpend;
        try {
            SpendJson result = spendApiClient.createSpend(spend);
            newSpend = new SpendJson(
                    Objects.requireNonNull(result).id(),
                    result.spendDate(),
                    spend.categoryEntity(),
                    spend.currency(),
                    spend.amount(),
                    spend.description(),
                    spend.categoryEntity().getUsername(),
                    spend.categoryEntity().getCategory()
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return newSpend;
    }

    @Override
    protected void removeSpend(SpendJson spend) {
        return;
    }
}

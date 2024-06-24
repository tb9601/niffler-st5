package guru.qa.niffler.jupiter.extension;


import guru.qa.niffler.api.SpendApiClient;
import guru.qa.niffler.model.CategoryJson;

public class CategoryHttpExtension extends AbstractCategoryExtention{
    private final SpendApiClient spendApiClient = new SpendApiClient();


    @Override
    protected CategoryJson createCategory(CategoryJson category) {
        try {
            return spendApiClient.addCategory(category);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void removeCategory(CategoryJson category) {
        return;
    }
}

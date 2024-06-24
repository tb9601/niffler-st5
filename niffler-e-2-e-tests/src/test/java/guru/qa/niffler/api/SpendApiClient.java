package guru.qa.niffler.api;

import guru.qa.niffler.model.CategoryJson;
import guru.qa.niffler.model.SpendJson;

public class SpendApiClient extends ApiClient {

    private final SpendApi spendApi;
    private final CategoryApi categoryApi;

    public SpendApiClient() {
        super(CFG.spendUrl());
        this.categoryApi = retrofit.create(CategoryApi.class);
        this.spendApi = retrofit.create(SpendApi.class);
    }

    public SpendJson createSpend(SpendJson spendJson) throws Exception {
        return spendApi.createSpend(spendJson)
                .execute()
                .body();
    }

    public CategoryJson addCategory(CategoryJson categoryJson) throws Exception {
        return categoryApi.createCategory(categoryJson)
                .execute()
                .body();
    }
}
package guru.qa.niffler.api;

import guru.qa.niffler.model.CategoryJson;

import java.util.List;

public class GatewayApiClient extends ApiClient {
    private final GatewayApi gatewayApi;

    public GatewayApiClient() {
        super(CFG.frontUrl());
        this.gatewayApi = retrofit.create(GatewayApi.class);
    }

    public List<CategoryJson> getCategories(String bearerToken) throws Exception {
        return gatewayApi.getCategories(bearerToken).execute().body();
    }

    public CategoryJson addCategory(String bearerToken, CategoryJson category) throws Exception {
        return gatewayApi.addCategory(bearerToken, category).execute().body();
    }

    public void deleteSpends(String bearerToken, List<String> ids) throws Exception {
        gatewayApi.deleteSpends(bearerToken, ids).execute();
    }
}

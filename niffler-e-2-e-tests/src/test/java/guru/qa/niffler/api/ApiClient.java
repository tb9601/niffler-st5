package guru.qa.niffler.api;

import guru.qa.niffler.config.Config;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import static okhttp3.logging.HttpLoggingInterceptor.Level;
import static okhttp3.logging.HttpLoggingInterceptor.Level.BODY;

public class ApiClient {

    protected static final Config CFG = Config.getInstance();

    protected final OkHttpClient okHttpClient;

    protected final Retrofit retrofit;

    public ApiClient(String baseUrl) {
        this(baseUrl, false, JacksonConverterFactory.create(), BODY);
    }

    public ApiClient(String baseUrl, Level loggingLevel) {
        this(baseUrl, false, JacksonConverterFactory.create(), loggingLevel);
    }

    public ApiClient(String baseUrl, boolean followRedirect) {
        this(baseUrl, followRedirect, JacksonConverterFactory.create(), BODY);
    }

    public ApiClient(String baseUrl, boolean followRedirect, Level loggingLevel) {
        this(baseUrl, followRedirect, JacksonConverterFactory.create(), loggingLevel);
    }

    public ApiClient(String baseUrl, Converter.Factory factory, Level loggingLevel) {
        this(baseUrl, false, factory, loggingLevel);
    }

    public ApiClient(String baseUrl, Converter.Factory factory) {
        this(baseUrl, false, factory, BODY);
    }

    public ApiClient(String baseUrl, boolean followRedirect, Converter.Factory factory) {
        this(baseUrl, followRedirect, factory, BODY);
    }

    public ApiClient(String baseUrl,
                     boolean followRedirect,
                     Converter.Factory factory,
                     Level loggingLevel,
                     Interceptor... interceptors) {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();

        if (okHttpClientBuilder != null) {
            for (Interceptor interceptor : interceptors) {
                okHttpClientBuilder.addNetworkInterceptor(interceptor);
            }
        }
        this.okHttpClient = okHttpClientBuilder.addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(loggingLevel))
                .followRedirects(followRedirect).build();

        this.retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(factory)
                .client(this.okHttpClient)
                .build();
    }
}
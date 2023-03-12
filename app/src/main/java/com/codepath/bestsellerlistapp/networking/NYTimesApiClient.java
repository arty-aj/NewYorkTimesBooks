package com.codepath.bestsellerlistapp.networking;

import com.codepath.bestsellerlistapp.models.BestSellerBook;
import com.codepath.bestsellerlistapp.models.NYTimesAPIResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NYTimesApiClient {

    private static final String API_KEY = "gftowjhmWCMC0SduDcodslJVC799tsc3";
    private NYTimesService nyTimesService;

    public NYTimesApiClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.nytimes.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        nyTimesService = retrofit.create(NYTimesService.class);
    }

    public void getBestSellersList(final CallbackResponse<List<BestSellerBook>> booksListResponse) {

        // this hard codes to only the current date's hardcover fiction best selling books
        // see https://developer.nytimes.com/docs/books-product/1/overview for more information on API documentation
        Call<NYTimesAPIResponse> current = nyTimesService.getBestSellingBooks("current", "hardcover-fiction", API_KEY);
        current.enqueue(new Callback<NYTimesAPIResponse>() {
            @Override
            public void onResponse(Call<NYTimesAPIResponse> call, Response<NYTimesAPIResponse> response) {
                NYTimesAPIResponse model = response.body();
                if (response.isSuccessful() && model != null) {
                    booksListResponse.onSuccess(model.results.books);
                } else {
                    booksListResponse.onFailure(new Throwable("error with response code " + response.code() + " " + response.message()));
                }
            }

            @Override
            public void onFailure(Call<NYTimesAPIResponse> call, Throwable t) {
                booksListResponse.onFailure(t);
            }
        });
    }
}

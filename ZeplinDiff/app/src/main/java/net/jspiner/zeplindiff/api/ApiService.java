package net.jspiner.zeplindiff.api;

import net.jspiner.zeplindiff.model.Project;
import net.jspiner.zeplindiff.model.ProjectModel;
import net.jspiner.zeplindiff.model.ScreenModel;
import net.jspiner.zeplindiff.model.User;

import java.util.ArrayList;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    @FormUrlEncoded
    @POST("/users/login")
    Single<Response<User>> login(
            @Field("handle") String handle,
            @Field("password") String password
    );

    @GET("/v2/projects")
    Single<ProjectModel> getProjectList(
            @Header("zeplin-token") String zeplinToken
    );

    @GET("/v2/projects/{hash_id}")
    Single<ScreenModel> getScreenList(
            @Header("zeplin-token") String zeplinToken,
            @Path("hash_id") String hash_id
    );

}

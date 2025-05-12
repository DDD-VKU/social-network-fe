package vku.ddd.social_network_fe.data.api

import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Body
import retrofit2.http.Query
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.PUT
import retrofit2.http.Part
import vku.ddd.social_network_be.dto.request.AccountCreateRequest
import vku.ddd.social_network_be.dto.request.AccountUpdateRequest
import vku.ddd.social_network_be.dto.request.AuthenticateRequest
import vku.ddd.social_network_be.dto.request.CommentCreateRequest
import vku.ddd.social_network_be.dto.request.CommentUpdateRequest
import vku.ddd.social_network_be.dto.request.PostCreateRequest
import vku.ddd.social_network_be.dto.request.PostShareRequest
import vku.ddd.social_network_be.dto.request.ReactToPostRequest
import vku.ddd.social_network_fe.data.model.Account
import vku.ddd.social_network_fe.data.model.Comment
import vku.ddd.social_network_fe.data.model.Notification
import vku.ddd.social_network_fe.data.model.Post
import vku.ddd.social_network_fe.data.model.User
import vku.ddd.social_network_fe.data.model.response.ApiResponse
import vku.ddd.social_network_fe.data.model.response.AuthenticateResponse

interface ApiService {
    // User
    @GET("api/users/me")
    suspend fun getMyAccountInfo(@Header("Authorization") token: String): Response<ApiResponse<Account>>

    @GET("api/users/{id}")
    suspend fun getAccountInfo(@Path("id") id: Long): Response<ApiResponse<Account>>

    @POST("api/users")
    suspend fun createAccount(@Body request: AccountCreateRequest): Response<ApiResponse<Account>>

    @Multipart
    @PUT("api/users/me")
    suspend fun updateAccount(
        @Header("Authorization") token: String,
        @Part("request") request: RequestBody,
        @Part avatar: MultipartBody.Part?
    ): Response<ApiResponse<Account>>

    @DELETE("api/users/search")
    suspend fun findUsersByName(@Query("name") name: String): Response<ApiResponse<List<User>>>

    @GET("api/users/{id}/followers")
    suspend fun getFollowers(@Path("id") id: Long): Response<ApiResponse<List<User>>>

    @GET("api/users/{id}/following")
    suspend fun getFollowing(@Path("id") id: Long): Response<ApiResponse<List<User>>>

    @GET("api/users/")
    suspend fun getUsers(): Response<ApiResponse<List<User>>>

    @GET("api/users/search")
    suspend fun searchUsers(
        @Query("query") query: String
    ): Response<ApiResponse<List<User>>>

    // Post
    @GET("api/posts")
    suspend fun getPosts(): Response<ApiResponse<List<Post>>>

    @GET("api/posts/{id}")
    suspend fun getPostById(@Path("id") id: Long): Response<ApiResponse<Post>>

    @Multipart
    @POST("api/posts")
    suspend fun createPost(
        @Part("request") request: RequestBody,
        @Part imageFiles: List<MultipartBody.Part?>
    ): Response<ApiResponse<Post>>

    @Multipart
    @PUT("api/posts/{id}")
    suspend fun updatePost(
        @Path("id") id: Long,
        @Part("request") request: RequestBody,
        @Part imageFiles: List<MultipartBody.Part?>
    ): Response<ApiResponse<Post>>

    @DELETE("api/posts/{id}")
    suspend fun deletePost(@Path("id") id: Long): Response<ApiResponse<Unit>>

    @POST("api/posts/share")
    suspend fun sharePost(@Body request: PostShareRequest): Response<ApiResponse<Post>>

    @POST("api/posts/react/{id}")
    suspend fun reactPost(@Path("id") id: Long, @Body request: ReactToPostRequest): Response<ApiResponse<Post>>

    @GET("api/posts/creator/{id}")
    suspend fun getPostByCreatorId(@Path("id") id: Long): Response<ApiResponse<List<Post>>>

    @GET("api/posts/search")
    suspend fun searchPosts(@Query("query") query: String): Response<ApiResponse<List<Post>>>

    // Comment
    @POST("api/comments")
    suspend fun createComment(@Body request: CommentCreateRequest): Response<ApiResponse<Comment>>

    @PUT("api/comments/{id}")
    suspend fun updateComment(@Path("id") id: Long, @Body request: CommentUpdateRequest): Response<ApiResponse<Comment>>

    @DELETE("api/comments/{id}")
    suspend fun deleteComment(@Path("id") id: Long): Response<ApiResponse<Unit>>

    @GET("api/comments/post/{postId}")
    suspend fun getCommentsByPostId(@Path("postId") postId: Long): Response<ApiResponse<List<Comment>>>

    // Authenticate
    @POST("api/auth/authenticate")
    suspend fun authenticate(@Body request: AuthenticateRequest) : Response<ApiResponse<AuthenticateResponse>>

    @POST("api/auth/logout")
    suspend fun logout(@Header("Authorization") token: String) : Response<ApiResponse<Unit>>

    @POST("api/auth/register")
    suspend fun register(@Body request: AccountCreateRequest) : Response<ApiResponse<AuthenticateResponse>>

    @GET("api/uploads/image/{id}")
    suspend fun getImage(@Path("id") id: Long): Response<ResponseBody>

    //Notification
    suspend fun getAllNotifications() : Response<ApiResponse<List<Notification>>>
}
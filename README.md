# Welcome to Basemaster

**Only import this lib, you can use retrofit2, all custom views, utils, animations, ads...
Download this app demo:**
https://play.google.com/store/apps/details?id=loitp.basemaster

### Importing the Library
**Step 1. Add the JitPack repository to your build file**  

    allprojects {  
          repositories {  
             ...  
             maven { url 'https://jitpack.io' }  
          }   }
**Step 2. Add the dependency**  

    defaultConfig {  
      ...  
      multiDexEnabled  true  
    }
    ...
    dependencies {  
      compile 'com.github.tplloi:basemaster:1.0.3'  
      compile 'com.android.support:multidex:1.0.3'  
    }
    
**Step 3. create class LSApplication**  

    public class LSApplication extends MultiDexApplication {  
        private static LSApplication instance;  
        private Gson gson;  
      
        @Override  
        public void onCreate() {  
            super.onCreate();  
            instance = this;  
            if (gson == null) {  
                gson = new Gson();  
            }  
        }  
      
        public Gson getGson() {  
            return gson;  
        }  
      
        public static LSApplication getInstance() {  
            return instance;  
        }  
      
        public static Context getContext() {  
            return instance.getApplicationContext();  
        }  
    }
**Step 4.  Edit manifest** 
Open manifest, put this code in Application tag

    <application  
      android:name=".app.LSApplication"  
      ...>  
        <activity ,,,
        </activity>  
    </application>

# Call API example:
**Create interface**  

    public interface APIServices {  
        @GET("/2.2/questions?order=desc&sort=votes&site=stackoverflow&tagged=android&filter=withbody")  
        Observable<Object> test(); 
    }
**Function** 

    private void testAPI() {  
        RestClient.init("https://api.stackexchange.com");  
        //RestClient.init("https://api.stackexchange.com", "token");  
        APIServices service = RestClient.createService(APIServices.class);  
        subscribe(service.test(), new ApiSubscriber<Object>() {  
            @Override  
            public void onSuccess(Object result) {  
                LLog.d(TAG, "testAPI onSuccess " \+ LSApplication.getInstance().getGson().toJson(result));  
            }  
      
            @Override  
            public void onFail(Throwable e) {  
                handleException(e);  
            }  
        });  
    }
**Expand interface**

    public interface APIServices {  
        @GET("/2.2/questions?order=desc&sort=votes&site=stackoverflow&tagged=android&filter=withbody")  
        Observable<Object> test();  
      
        @FormUrlEncoded  
	    @POST("/api/public/v1/auth/credentical")  
        Observable<Object> auth(@Field("accessKeyId") String accessKeyId, @Field("secretKeyId") String secretKeyId);  
      
        @POST("/api/data/v1/metadata/list")  
        Observable<Object> getMetadatList();  
      
        @GET("v1/app/poster")  
        Observable<GetPoster\[\]>  
        getPoster(@Query("number") int number);  
      
        @FormUrlEncoded  
        @PUT("v1/room/follow")  
        Observable<FollowIdol> followIdol(@Field("roomId") String roomId);  
      
        @DELETE("v1/room/schedule")  
        Observable<Void> deleteSchedule(@Query("scheduleId") String id);  
      
        @Multipart  
        @POST("v1/room/banner")  
        Observable<Room> updateBanner(@Part MultipartBody.Part file);  
    }

# Activity transition:

    ActivityData.getInstance().setType(Constants.TYPE_ACTIVITY_TRANSITION_SLIDEUP);
put above code after startActivity(); or onBackPress();

There are some types:

 - TYPE\_ACTIVITY\_TRANSITION\_NO\_ANIM
 - TYPE\_ACTIVITY\_TRANSITION\_SYSTEM\_DEFAULT
 - TYPE\_ACTIVITY\_TRANSITION_SLIDELEFT
 - TYPE\_ACTIVITY\_TRANSITION_SLIDERIGHT
 - TYPE\_ACTIVITY\_TRANSITION_SLIDEDOWN
 - TYPE\_ACTIVITY\_TRANSITION_SLIDEUP
 - TYPE\_ACTIVITY\_TRANSITION_FADE
 - TYPE\_ACTIVITY\_TRANSITION_ZOOM
 - TYPE\_ACTIVITY\_TRANSITION_WINDMILL
 - TYPE\_ACTIVITY\_TRANSITION_DIAGONAL
 - TYPE\_ACTIVITY\_TRANSITION_SPIN
 
![alt text](https://lh3.googleusercontent.com/OvDbWUeUC_vc12SxJwK2nMm6ke8ebt0PlAMcjKzuOznYkejFw7Gft2BY0YEB2xrH4DdTo_Uk76s=w248-h437-no)


# Animation views:

    LAnimationUtil.play(view, listAnim.get(position));

Ex:

    LAnimationUtil.play(tvAnim, Techniques.Pulse);
    
![alt text](https://lh3.googleusercontent.com/CWVngpE8Cm8xksdIU6zVaT3jiWIEGfwAM6U5KsXtSIFj4wMSjbE0hPc_DfBiZ1MwKpDstSP3DapDbS45xlSYe_ndYBlj-DNol6LHy98BmSko_P02Wtn8ktX21dsBgE4SAELP98kHEzymze4Tuu9T36Ecw55lxf4RkSH6t4M-99bIuiyvDlc9qsShDI4KiMRCpHLWXckgBzvZ2lqERS_3L02V93dUeW0OIHGUmyjJbuu6mWXTPR8hhN5mGdo4gziGVOcC2ZF9yxmMhvXA3kcl2Kpz_Jl_DOloEYsggKDFgCzBEmMRZAoZsBucVP4h9ttu3Wap1IsUlubaTc5wHTvbVkraz_lz1yS6YhmG0ZeNoKbrp_2w20ztL1wFssMUasQ8K0TQ4cEpAgZXzpM1qfrMJ840N-XSAvqbR6rhNkU74-sFtXfMtPgwOOPxujPjz6mc5TqTRpnazvJQE4THoQ_juriO6MH4rZta31T6yxEpd_m9oI6InEM8kz9N3kxzkNpXesbTHjcckLkfE5ni4F8ySsIXuXg5ZyJkCaZFdUyZB2HkPwbJr2Lw4gS06vEYNvuN9d3ikc7oGud4cadUHyJsUXVP0-gxy_VpoDKIE3L9=w452-h795-no)


![alt text](https://lh3.googleusercontent.com/OvDbWUeUC_vc12SxJwK2nMm6ke8ebt0PlAMcjKzuOznYkejFw7Gft2BY0YEB2xrH4DdTo_Uk76s=w248-h437-no)
...Update...
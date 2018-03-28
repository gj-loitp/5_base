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
![alt text](https://lh3.googleusercontent.com/ELILEx8EzS_IRMxnPCLD8dcW2vr4jgtHEN61Jzmor1w5rZz83toJRdGpkPyX7RgVn7nXUDPHzLS0foZN4hUWdfpw__IqJnI6lK7NDj4zPxijSXwnoSNDjNA3Eyxw0ywMh8iA4Ydei08MchrqLaDFu3HzKlT45q5hfukjTPDHMZCqmWIA6zHPXz0xJgQRV4-oab6K8PsqMMpPpsuTIBSm3dBSiCMVYpmRQKnCc5KlCqD2gIVN-nT2P_LwzFWM9oTxOitkSh7zW1LWONaWbn6vkUdrofs7B8kO26vmTjDvfRx2rZbQJyoxADMefm-JSkQIfkSWs3VHm84bzRbik4Y5cCGiYE-FB4bZAP_CyX8muJVdZTwIF5Vd62RTyIJ5CANO9K5UWCT-9LISo0J_nnNaI12Gp4neoxb7B2u2sfejDjulTrgdo7TtrrZY3EMYwXJ6-jfdSA3ZtkRJCnIjt2Zs2_D0KaTE0Z9wZVUREfwxRraCRnxGhJMB6oeWDWUTbYn5XUAR0iov0FAVmiYQish8-WLBrJ-oYi7gvj44vJpgDPtiOVhdIgA0u-hNTLmWuPSHT3j3v9nDYbXEdsfCrfyIe8NfuPSHih17WjBgUOpq=w452-h795-no)
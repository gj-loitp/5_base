# Welcome to Basemaster
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

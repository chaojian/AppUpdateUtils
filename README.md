# AppUpdateUtils
APP检测更新
## How to
To get a Git project into your build:<br>
## gradle
### Step 1. Add the JitPack repository to your build file<br>
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  ### Step 2. Add the dependency
	dependencies {
	        implementation 'com.github.freakcsh:AppUpdateUtils:V1.2'
	}
  

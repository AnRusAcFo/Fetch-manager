pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        jcenter()
        maven {
            url = uri("https://www.jitpack.io" )
        }
        maven {
            url = uri ("https://maven.aliyun.com/repository/jcenter")
        }
    }
}

rootProject.name = "AppBanHang"
include(":app")
 
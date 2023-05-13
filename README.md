# monster.makeover republishing steps
1)  build.gradle(Module): replace com.example.createmonster with mix.monsters.makeover.monster
2)  build.gradle(Module) and directories: change package name to com.monster.makeover
3)  kotlin files: replace com.example.createmonster with com.monster.makeover
4)  settings.gradle: rootProject.name = "monster makeover"
5)  strings.xml: app_name is Monster Makeover
6)  unityAdsManager.kt: unityAppId = "5274719"
7)  after test -> unityAdsManager.kt: testMode = false

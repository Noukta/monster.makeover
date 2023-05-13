# monster.makeover republishing steps
1)  build.gradle(Module): replace com.example.createmonster with mix.monsters.makeover.monster
2)  build.gradle(Module) and directories: change package name to com.monster.makeover
3)  app: replace com.example.createmonster with com.monster.makeover
4)  settings.gradle: rootProject.name = "monster.makeover"
5)  strings.xml: app_name is Monster Makeover
6)  unityAdsManager.kt: unityAppId = "5274719"
7)  after test -> unityAdsManager.kt: testMode = false

# monster.makeover republishing steps
1)  build.gradle(Module): replace com.example.createmonster with monster.makeover.mix.monsters
2)  build.gradle(Module) and directories: change package name to com.mix.monster
3)  app: replace com.example.createmonster with com.mix.monster
4)  settings.gradle: rootProject.name = "mix.monster"
5)  strings.xml: app_name is Mix Monster
6)  after test -> unityAdsManager.kt: testMode = false

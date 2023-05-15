# monster.makeover republishing steps
1)  build.gradle(Module): replace com.example.createmonster with mix.monsters.makeover.monster
2)  build.gradle(Module) and directories: change package name to com.monster.makeover
3)  app: replace com.example.createmonster with com.monster.makeover
4)  app: replace com.example with com.monster
5)  settings.gradle: rootProject.name = "monster.makeover"
6)  strings.xml: app_name is Monster Makeover
7)  unityAdsManager.kt: unityAppId = "5274719"
8)  after test -> unityAdsManager.kt: testMode = false

# mix.monster republishing steps
1) build.gradle(Module): replace com.example.createmonster with monster.makeover.mix.monsters
2) build.gradle(Module) and directories: change package name to com.mix.monster
3) app: replace com.example.createmonster with com.mix.monster
4) app: replace com.example with com.monster
5) settings.gradle: rootProject.name = "mix.monster"
6) strings.xml: app_name is Mix Monster
7) change ic_launcher
8) delete .gradle and build folders
9) unityAdsManager.kt: unityAppId = "5273632"
10) after test -> unityAdsManager.kt: testMode = false
package com.example.createmonster

import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.FileProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.createmonster.ui.CreateMonsterViewModel
import com.example.createmonster.ui.CreateScreen
import com.example.createmonster.ui.EndScreen
import com.example.createmonster.ui.StartScreen
import com.smarttoolfactory.screenshot.rememberScreenshotState
import java.io.File
import java.io.FileOutputStream

enum class CreateMonsterScreen {
    Start,
    Create,
    End
}

/*@Composable
fun CreateMonsterAppBar(
    currentScreen: CreateMonsterScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(currentScreen.title)) },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}*/

@Composable
fun CreateMonsterApp(modifier: Modifier = Modifier, viewModel: CreateMonsterViewModel = viewModel()){
    val navController = rememberNavController()

    /*val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = CreateMonsterScreen.valueOf(
        backStackEntry?.destination?.route ?: CreateMonsterScreen.Start.name
    )*/

    Scaffold(
        /*topBar = {
            CreateMonsterAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }*/
    ) { innerPadding ->
        val uiState by viewModel.uiState.collectAsState()
        NavHost(
            navController = navController,
            startDestination = CreateMonsterScreen.Start.name,
            modifier = modifier.padding(innerPadding)
        ){
            composable(route = CreateMonsterScreen.Start.name) {
                StartScreen(
                    onNextButtonClicked = {navController.navigate(CreateMonsterScreen.Create.name)}
                )
            }
            composable(route = CreateMonsterScreen.Create.name) {
                CreateScreen(
                    monsterUiState = uiState,
                    onMonsterHeadChanged = {id -> viewModel.updateMonsterHead(id)},
                    onMonsterEyeChanged = {id -> viewModel.updateMonsterEye(id)},
                    onMonsterMouthChanged = {id -> viewModel.updateMonsterMouth(id)},
                    onMonsterAccChanged = {id -> viewModel.updateMonsterAcc(id)},
                    onMonsterBodyChanged = {id -> viewModel.updateMonsterBody(id)},
                    onEyePositionChanged = {viewModel.updateEyePosition(it)},
                    onMouthPositionChanged = {viewModel.updateMouthPosition(it)},
                    onAccPositionChanged = {viewModel.updateAccPosition(it)},
                    onDoneButtonClicked = {navController.navigate(CreateMonsterScreen.End.name) }
                    )

            }
            composable(route = CreateMonsterScreen.End.name) {
                val screenshotState = rememberScreenshotState()
                val context = LocalContext.current

                var showDialog by remember { mutableStateOf(false) }
                // Show dialog only when ImageResult is success or error
                LaunchedEffect(key1 = screenshotState.bitmap){
                    screenshotState.bitmap?.let { showDialog = true }
                }

                EndScreen(
                    monsterUiState = uiState,
                    screenshotState = screenshotState,
                    onRemakeButtonClicked = {remakeNewMonster(viewModel, navController)}
                ) {
                    screenshotState.capture()
                    if(showDialog) {
                        screenshotState.bitmap?.let {
                            val uri = it.saveToInternalStorage(context, "monster.png")
                            val message =
                                "did you like my monster? create yours: https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}"
                            shareImageUri(context, uri, message)
                        }
                    }
                }
            }
        }
    }
}

private fun Bitmap.saveToInternalStorage(context: Context, fileName: String): Uri {
    val imagesDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    val imageFile = File(imagesDir, fileName)
    val outputStream = FileOutputStream(imageFile)
    this.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
    outputStream.flush()
    outputStream.close()
    return FileProvider.getUriForFile(
        context,
        "${BuildConfig.APPLICATION_ID}.provider",
        imageFile
    )
}

private fun shareImageUri(context: Context, uri: Uri, message: String) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        clipData = ClipData.newRawUri(null, uri)
        putExtra(Intent.EXTRA_STREAM, uri)
        putExtra(Intent.EXTRA_TEXT, message)
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        type = "image/png"
    }
    context.startActivity(Intent.createChooser(intent, "Share Monster"))
}

private fun remakeNewMonster(
    viewModel: CreateMonsterViewModel,
    navController: NavHostController
) {
    viewModel.resetMonster()
    navController.popBackStack(CreateMonsterScreen.Start.name, inclusive = false)
}
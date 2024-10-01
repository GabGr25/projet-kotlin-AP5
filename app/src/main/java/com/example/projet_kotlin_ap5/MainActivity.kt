package com.example.projet_kotlin_ap5

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.FractionRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.res.ResourcesCompat
import com.example.projet_kotlin_ap5.ui.theme.ProjetkotlinAP5Theme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProjetkotlinAP5Theme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = Color(27,27,27),
                    bottomBar = {
                        Navbar(
                            pathAccueil = "Android",
                            pathMySong = "Song",
                            paddingBottom = PaddingValues(),
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .height(74.dp)
                    )}
                ) { innerPadding ->

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        Home(
                            modifier = Modifier
                        )
                    }
                }
            }
        }
    }
}
//Police
val lexendFontFamily = FontFamily(
    Font(R.font.lexend_deca_semi_bold, FontWeight.SemiBold) // Charger la police depuis res/font
)

@Composable
fun Home(modifier: Modifier){
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        TitleText("Écoutés récemment", 0.66f)
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(14.dp, 25.dp)
        ){
            ClickableImage("img1", 120.dp)
            Spacer(modifier = Modifier.width(14.dp))
            ClickableImage("img2", 120.dp)
            Spacer(modifier = Modifier.width(14.dp))
            ClickableImage("img3", 120.dp)
        }
        Spacer(modifier = Modifier.height(24.dp)) // Espacement entre les deux titres
        TitleText("Playlists du moment", 0.68f)
        Row (
            modifier = modifier
                .fillMaxWidth()
                .padding(14.dp, 25.dp)
        ){
            Column {
                ClickableImage("img1", 170.dp)
                Spacer(modifier = Modifier.height(30.dp))
                ClickableImage("img1", 170.dp)
            }
            Spacer(modifier = Modifier.width(30.dp))
            Column {
                ClickableImage("img1", 170.dp)
                Spacer(modifier = Modifier.height(30.dp))
                ClickableImage("img1", 170.dp)
            }
        }
    }
}

@Composable
fun Navbar(pathAccueil: String, pathMySong: String, paddingBottom: PaddingValues, modifier: Modifier) {
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()  // Prend toute la largeur
            .wrapContentHeight()  // Seulement la hauteur nécessaire
            .border(1.dp, Color(16,16,16))
            .background(Color(16,16,16))
            .padding(bottom = 30.dp)
            .padding(top = 10.dp)
    ) {
        Button(
            onClick = { /* TODO: Action pour le bouton Home */ },
            colors = ButtonDefaults.buttonColors(
                Color(16,16,16),
                Color.Yellow
            )
        ) {
            ButtonText("Home"
            )
        }
        VerticalDivider(Modifier.height(20.dp))
        Button(
            onClick = { /* TODO: Action pour le bouton Favorite */ },
            colors = ButtonDefaults.buttonColors(
                Color(16,16,16),
                Color.Yellow
            )
        ) {
            ButtonText("Album")
        }
        VerticalDivider(Modifier.height(20.dp))
        Button(
            onClick = { /* TODO: Action pour le bouton Settings */ },
            colors = ButtonDefaults.buttonColors(
                Color(16,16,16),
                Color.Yellow
            )
        ) {
            ButtonText("Artiste")
        }
    }
}

@Composable
fun ButtonText(text: String){
    Text(
        text = text,
        fontSize = 18.sp,
        fontFamily = lexendFontFamily,
        color = Color(255,255,100),
        fontWeight = FontWeight.SemiBold
    )
}

@Composable
fun TitleText(text: String, sizeLigne: Float) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = text,
            fontSize = 24.sp,
            color = Color.White,
            fontWeight = FontWeight.W700,
            fontFamily = lexendFontFamily,
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .padding(top = 24.dp)

        )
        Spacer(modifier = Modifier.height(4.dp)) // Espacement entre le texte et la ligne
        Box(
            modifier = Modifier
                .height(3.dp) // Hauteur de la ligne
                .padding(horizontal = 20.dp)
                .fillMaxWidth(sizeLigne) // Largeur de la ligne
                .background(Color(255,255,100)) // Couleur de la ligne
        )
    }
}

fun getImageResourceId(context: Context, nameImage: String): Int? {
    val resourceId = context.resources.getIdentifier(nameImage, "drawable", context.packageName)
    return if (resourceId != 0) resourceId else null
}

@Composable
fun ClickableImage(nameImage: String, sizeImage: Dp) {
    val context = LocalContext.current
    // Obtenir l'identifiant de l'image dynamiquement à partir du nom
    val imageId = getImageResourceId(context, nameImage)

    // Si l'image existe, l'afficher
    imageId?.let {
        Image(
            painter = painterResource(id = it),
            contentDescription = "Clickable Image",
            modifier = Modifier
                .size(sizeImage)
                .clickable {
                    // Action lors du clic
                },
            contentScale = ContentScale.Crop // Ajustement de l'image
        )
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ProjetkotlinAP5Theme {
        Greeting("Android")
    }
}

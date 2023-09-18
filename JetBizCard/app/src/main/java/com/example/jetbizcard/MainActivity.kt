package com.example.jetbizcard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetbizcard.ui.theme.JetBizCardTheme

/*
    ** Topics Covered **
    * Surface, Card, Column, Row, Image, Box, Button+Remember, LazyColumn (RecyclerView)
    * Modifiers, Shapes, Vertical+Horizontal Arrangement, Basic Layout
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetBizCardTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    CreateBizCard()
                }
            }
        }
    }
}

@Composable
fun CreateBizCard() {

    val buttonClickedState = remember {
        // initialize to false each time
        mutableStateOf(false)
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(12.dp)
    ) {
        Card(
            modifier = Modifier
                .width(200.dp)
                .height(390.dp),
            elevation = 4.dp,
            shape = RoundedCornerShape(corner = CornerSize(15.dp)),
            backgroundColor = Color.White
        ) {
            // This is one of our key formatting tools
            Column(
                modifier = Modifier.height(300.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CreateImageProfile(
                    imageResourceId = R.drawable.krombopulos,
                    contentDescription = "krombopulos"
                )
                Divider()
                CreatePersonalInfo()
                Button(
                    onClick = {
                        // toggling value t/f here
                        buttonClickedState.value = !buttonClickedState.value
                    }) {
                    Text(
                        text = "Show Killings",
                        style = MaterialTheme.typography.button
                    )
                }
                // Show portfolio content OR blank Box
                if (buttonClickedState.value) {
                    PortfolioContent()
                } else {
                    // This is how you show "Nothing"... for now...
                    Box() {
                    }
                }

            }
        }
    }
}

@Composable
private fun CreatePersonalInfo() {
    // This column isn't 100% necessary (as it will live in a column), but it is good to consider writing composables as entities of their own.
    Column(modifier = Modifier.padding(5.dp)) {
        Text(
            text = "Krombopolus, M",
            style = MaterialTheme.typography.h4,
            color = MaterialTheme.colors.primaryVariant
        )
        Text(
            text = "Galactic President of Killin' Stuff",
            modifier = Modifier.padding(3.dp)
        )
        Text(
            text = "@thekrombopulizer",
            modifier = Modifier.padding(3.dp),
            style = MaterialTheme.typography.subtitle1
        )
    }
}

// Extracted out by highlighting -> Right Click -> Refactor
// Note on Modifiers: We are passing an optional modifier here, but declaring default values as well. A passed modifier will override all.
@Composable
private fun CreateImageProfile(
    modifier: Modifier = Modifier,
    imageResourceId: Int,
    contentDescription: String = "no description"
) {
    Surface(
        modifier = modifier
            .size(150.dp)
            .padding(5.dp),
        shape = CircleShape,
        border = BorderStroke(0.5.dp, Color.LightGray),
        elevation = 4.dp,
        color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f)
    ) {
        Image(
            painter = painterResource(imageResourceId),
            contentDescription = contentDescription,
            modifier = modifier.size(135.dp)
        )
    }
}


@Preview
@Composable
private fun PortfolioContent() {
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        Surface(
            modifier = Modifier
                .padding(3.dp)
                .fillMaxWidth()
                .fillMaxHeight(),
            shape = RoundedCornerShape(5.dp),
            border = BorderStroke(width = 2.dp, color = Color.LightGray)
        ) {
            Portfolio(data = getKillList())
        }
    }
}

private fun getKillList(): List<DeadSpaceThing> {
    val newList: ArrayList<DeadSpaceThing> = ArrayList()

    newList.add(DeadSpaceThing("Squanchy...", "Found in hotel room", R.drawable.squanchy))
    newList.add(DeadSpaceThing("Rick Sanchez", "Murdered in spacecraft ventilation shaft", R.drawable.rick))
    newList.add(DeadSpaceThing("Morty Smith", "Imploded at Blitz n' Chitz", R.drawable.morty))
    newList.add(DeadSpaceThing("Summer Smith", "Vaporized or something", R.drawable.summer))

    return newList
}

@Composable
private fun Portfolio(data: List<DeadSpaceThing>) {
    // lazy column gives us the ability to scroll and recycle (Recycler View)
    LazyColumn {
        items(data) { item ->
            //Text(text = item)
            Card(
                modifier = Modifier
                    .padding(13.dp)
                    .fillMaxWidth(),
                shape = RectangleShape
            ) {
                Row(
                    modifier = Modifier
                        .padding(8.dp)
                        .background(MaterialTheme.colors.surface)
                        .padding(16.dp)
                ) {
                    // reusing image profile composable here, changing function parameters
                    CreateImageProfile(
                        modifier = Modifier.size(100.dp),
                        imageResourceId = item.resourceId,
                        contentDescription = item.name
                    )
                    Column(
                        modifier = Modifier
                            .padding(8.dp)
                            .align(alignment = Alignment.CenterVertically)
                    ) {
                        Text(text = item.name, fontWeight = FontWeight.Bold)
                        Text(text = item.description, style = MaterialTheme.typography.body2)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JetBizCardTheme {
        CreateBizCard()
    }
}

data class DeadSpaceThing(val name: String, val description: String, val resourceId: Int)
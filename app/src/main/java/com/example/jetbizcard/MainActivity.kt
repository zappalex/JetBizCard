package com.example.jetbizcard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetbizcard.ui.theme.JetBizCardTheme

/*
    ** Topics Covered **
    * Surface, Card, Column, Image, Box, Button+Remember, LazyColumn (RecyclerView)
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
                CreateImageProfile()
                Divider()
                CreatePersonalInfo()
                Button(
                    onClick = {
                        // toggling value t/f here
                        buttonClickedState.value = !buttonClickedState.value
                    }) {
                    Text(
                        text = "Show Portfolio",
                        style = MaterialTheme.typography.button
                    )
                }

                // Show portfolio content OR blank Box
                if(buttonClickedState.value) {
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
@Composable
private fun CreateImageProfile(modifier: Modifier = Modifier) {
    Surface(
        modifier = Modifier
            .size(150.dp)
            .padding(5.dp),
        shape = CircleShape,
        border = BorderStroke(0.5.dp, Color.LightGray),
        elevation = 4.dp,
        color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f)
    ) {
        Image(
            painter = painterResource(id = R.drawable.krombopulos),
            contentDescription = "krombopulos mf'n michael",
            modifier = Modifier.size(135.dp)
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
            Portfolio(data = getProjectList())
        }
    }
}

private fun getProjectList(): List<String> {
    val newList: ArrayList<String> = ArrayList()
    for (i in 1..45) {
        newList.add("Project $i")
    }
    return newList
}

@Composable
private fun Portfolio(data: List<String>) {
    // lazy column gives us the ability to scroll and recycle (Recycler View)
    LazyColumn {
        items(data) { item -> 
            Text(text = item)
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
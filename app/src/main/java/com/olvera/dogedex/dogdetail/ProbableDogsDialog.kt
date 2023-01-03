package com.olvera.dogedex.dogdetail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import com.olvera.dogedex.R
import com.olvera.dogedex.doglist.ui.theme.DogedexTheme
import com.olvera.dogedex.model.Dog

@ExperimentalCoilApi
@Composable
fun MostProbableDogsDialog(
    mostProbableDogs: MutableList<Dog>,
    onShowMostProbableDogsDialogDismiss: () -> Unit,
    onItemClick: (Dog) -> Unit
) {

    AlertDialog(
        onDismissRequest = { onShowMostProbableDogsDialogDismiss() },
        title = {
            Text(
                text = stringResource(id = R.string.other_probable_dogs),
                color = colorResource(id = R.color.text_black),
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
            )
        },
        text = {
            MostProbableDogsList(mostProbableDogs) {
                onItemClick(it)
                onShowMostProbableDogsDialogDismiss()
            }
        },
        buttons = {
            Row(
                modifier = Modifier.padding(all = 8.dp),
                horizontalArrangement = Arrangement.Center
            ) {

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { onShowMostProbableDogsDialogDismiss() }) {

                    Text(text = stringResource(id = R.string.dismiss))
                }

            }
        }
    )
}

@ExperimentalCoilApi
@Composable
fun MostProbableDogsList(dogs: MutableList<Dog>, onItemClick: (Dog) -> Unit) {
    Box(modifier = Modifier.height(250.dp)) {
        LazyColumn(content = {
            items(dogs) {
                MostProbableItem(dog = it, onItemClick)
            }
        })
    }
}

@ExperimentalCoilApi
@Composable
fun MostProbableItem(dog: Dog, onItemClick: (Dog) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable(enabled = true, onClick = { onItemClick(dog) })
    ) {
        Text(
            text = dog.name,
            modifier = Modifier.padding(8.dp),
            color = colorResource(id = R.color.text_black)
        )
    }
}

@ExperimentalCoilApi
@Composable
@Preview(showBackground = true)
fun MostProbableDogsDialogPreview() {
    DogedexTheme {
        Surface {
            MostProbableDogsDialog(getFakeDogs(), {}) {}
        }
    }
}

fun getFakeDogs(): MutableList<Dog> {
    val dogList = mutableListOf<Dog>()

    dogList.add(
        Dog(
            1,
            1,
            "Chihuahua",
            "Chihuahua",
            "Toy",
            "19",
            "Brave",
            "12 - 15",
            "2.0",
            "2.5",
            "12.0",
            false
        )
    )

    dogList.add(
        Dog(
            2,
            2,
            "Pug",
            "Pug",
            "Toy",
            "12",
            "Friendly",
            "www.pug.com",
            "10 - 12",
            "4.5",
            "12.0",
            false
        )
    )

    dogList.add(
        Dog(
            3,
            3,
            "Husky",
            "Husky",
            "Sporting",
            "15",
            "www.husky.com",
            "8 - 12",
            "5.0",
            "2.5",
            "12.0",
            false
        )
    )


    return dogList
}

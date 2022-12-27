package com.olvera.dogedex.composables

import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun AuthField(
    label: String,
    email: String, onTextChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    OutlinedTextField(
        label = { Text(text = label) },
        modifier = modifier,
        value = email, onValueChange = { onTextChanged(it) },
        visualTransformation = visualTransformation
    )

}
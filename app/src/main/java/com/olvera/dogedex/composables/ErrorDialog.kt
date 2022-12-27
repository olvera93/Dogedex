package com.olvera.dogedex.composables

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.olvera.dogedex.R
import com.olvera.dogedex.api.ApiResponseStatus

@Composable
fun ErrorDialog(messageId: Int, onDialogDismiss: () -> Unit) {
    AlertDialog(onDismissRequest = { },
        title = {
            Text(text = stringResource(id = R.string.error_dialog_title))
        },
        text = {
            Text(text = stringResource(id = messageId))
        },
        confirmButton = {
            Button(onClick = { onDialogDismiss() }) {
                Text(text = stringResource(id = R.string.try_again))
            }
        }
    )
}

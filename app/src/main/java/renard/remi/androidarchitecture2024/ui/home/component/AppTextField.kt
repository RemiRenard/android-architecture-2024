package renard.remi.androidarchitecture2024.ui.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun AppTextField(
    modifier: Modifier,
    value: String,
    label: String,
    isError: Boolean,
    isAllCaps: Boolean = false,
    errorMessage: String?,
    minLines: Int = 1,
    maxCharacters: Int? = null,
    onValueChange: (String) -> Unit,
    onDone: ((KeyboardActionScope) -> Unit)? = null
) {
    Column(modifier = modifier) {
        OutlinedTextField(
            value = value,
            onValueChange = {
                if (maxCharacters == null || it.length <= maxCharacters) {
                    onValueChange.invoke(it)
                }
            },
            isError = isError,
            modifier = modifier.fillMaxWidth(),
            label = {
                Text(
                    text = label,
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = if (onDone != null) {
                    ImeAction.Done
                } else {
                    ImeAction.Default
                },
                capitalization = if (isAllCaps) {
                    KeyboardCapitalization.Characters
                } else {
                    KeyboardCapitalization.None
                }
            ),
            keyboardActions = KeyboardActions(onDone = onDone),
            minLines = minLines,
            colors = OutlinedTextFieldDefaults.colors(
                cursorColor = MaterialTheme.colorScheme.onSurface,
                focusedBorderColor = MaterialTheme.colorScheme.onSurface,
                focusedLabelColor = MaterialTheme.colorScheme.onSurface,
                focusedPlaceholderColor = MaterialTheme.colorScheme.onSurface,
                focusedLeadingIconColor = MaterialTheme.colorScheme.onSurface,
                focusedTrailingIconColor = MaterialTheme.colorScheme.onSurface,
                focusedPrefixColor = MaterialTheme.colorScheme.onSurface,
                focusedSuffixColor = MaterialTheme.colorScheme.onSurface,
            )
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 2.dp, top = 1.dp),
            horizontalArrangement = if (isError) Arrangement.SpaceBetween else Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isError) {
                Text(
                    text = errorMessage ?: "",
                    color = MaterialTheme.colorScheme.error,
                )
            }
            if (maxCharacters != null) {
                Text(
                    text = "${value.length} / $maxCharacters",
                    textAlign = TextAlign.End,
                    style = MaterialTheme.typography.labelMedium,
                )
            }
        }
    }
}
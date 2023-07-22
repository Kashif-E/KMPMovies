package com.kashif.common.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchAppBar(placeHolder: String, onTextChange: (query: String) -> Unit) {
    var isExpanded by remember { mutableStateOf(false) }
    var query by remember { mutableStateOf(("")) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    val keyboardActions =
        KeyboardActions(
            onSearch = {
                focusManager.clearFocus()
                keyboardController?.hide()
            })

    AnimatedVisibility(
        visible = isExpanded,
        enter = fadeIn() + slideIn { fullSize -> IntOffset(fullSize.width + 100, 0) },
        exit = slideOut { fullSize -> IntOffset(fullSize.width + 100, 0) },
        content = {
            Row(
                modifier =
                    Modifier.fillMaxWidth()
                        .padding(12.dp)
                        .background(
                            color = Color.LightGray.copy(alpha = 0.95f),
                            shape = RoundedCornerShape(50.dp)),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically) {
                    TextField(
                        value = query,
                        onValueChange = { text ->
                            query = text
                            onTextChange(query)
                        },
                        placeholder = {
                            Text(
                                placeHolder,
                                color = MaterialTheme.colors.background,
                                style = MaterialTheme.typography.h6)
                        },
                        modifier = Modifier.padding(horizontal = 8.dp).weight(9f),
                        keyboardOptions =
                            KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
                        keyboardActions = keyboardActions,
                        singleLine = true,
                        colors =
                            TextFieldDefaults.textFieldColors(
                                textColor = Color.Black,
                                disabledTextColor = MaterialTheme.colors.background,
                                disabledIndicatorColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                errorIndicatorColor = Color.Transparent),
                    )
                    IconButton(
                        onClick = {
                            isExpanded = false
                            query = ""
                        },
                        modifier = Modifier.weight(1f)) {
                            Icon(Icons.Rounded.Close, "", tint = Color.White)
                        }
                }
        })

    AnimatedVisibility(visible = !isExpanded) {
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopEnd) {
            TransparentIconHolder { isExpanded = true }
        }
    }

}

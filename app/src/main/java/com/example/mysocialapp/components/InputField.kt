package com.example.mysocialapp.components


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputField(
    modifier: Modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 10.dp),
    valueState: MutableState<String>,
    labelId: String,
    enabled: Boolean,
    isSingleLine: Boolean,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default,
    onValueChange:(text:String)->Unit
) {
    OutlinedTextField(
        value = valueState.value,
        onValueChange = { valueState.value = it
                        onValueChange(it)
                        },
        label = { Text(text = labelId , color = Color(0xff15420F )) },
        leadingIcon = {
            Icon(
                imageVector = Icons.Rounded.Search, contentDescription = "text field icon", tint = Color(
                    0xFFD6D3D3
                )
            )
        }, singleLine = isSingleLine,
        modifier = modifier.padding(bottom = 10.dp, start = 10.dp, end = 10.dp).fillMaxWidth(),
        textStyle = TextStyle(
            fontSize = 18.sp,
            color = Color.Black
        ),
        enabled = enabled,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        keyboardActions = onAction,
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.LightGray, // Change the text color
            containerColor = Color.White, // Change the background color
            cursorColor = Color(0xff15420F ), // Change the cursor color
            focusedIndicatorColor =Color(0xff15420F ), // Change the focused indicator color
            unfocusedIndicatorColor = Color.LightGray, // Change the unfocused indicator color
        )

    )


}
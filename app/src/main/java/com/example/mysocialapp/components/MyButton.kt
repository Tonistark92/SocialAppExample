package com.ainshams.graduation_booking_halls.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp


@Composable
fun HallsButton(
    modifer: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    enabled: Boolean
) {
    Button(
        onClick = { onClick.invoke() },
        shape = RoundedCornerShape(topEnd = 33.dp, bottomStart = 33.dp),
        enabled = enabled,
        modifier = modifer.width(90.dp).height(50.dp)
    ) {
        Text(text)

    }

}
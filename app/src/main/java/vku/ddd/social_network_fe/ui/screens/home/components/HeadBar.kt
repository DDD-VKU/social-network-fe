package vku.ddd.social_network_fe.ui.screens.home.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HeadBar() {
    Row {
        Button(onClick = {}, modifier = Modifier.height(20.dp).width(50.dp)) {
            Icon (
                imageVector = Icons.Default.AddCircle,
                contentDescription = "Add"
            )
            Text(text = "Create new post")
        }
    }
}
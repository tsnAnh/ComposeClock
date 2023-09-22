package dev.tsnanh.compose.clock

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.tsnanh.compose.clock.ui.theme.ClockTheme
import kotlinx.coroutines.delay
import java.util.Calendar
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ClockTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var hours by remember {
                        mutableStateOf(0)
                    }
                    var minutes by remember {
                        mutableStateOf(0)
                    }
                    var seconds by remember {
                        mutableStateOf(0)
                    }
                    LaunchedEffect(Unit) {
                        while (true) {
                            val calendar = Calendar.getInstance()
                            hours = calendar[Calendar.HOUR]
                            minutes = calendar[Calendar.MINUTE]
                            seconds = calendar[Calendar.SECOND]
                            delay(1000)
                        }
                    }
                    Clock(hours, minutes, seconds)
                }
            }
        }
    }
}

@Composable
private fun Clock(hour: Int, minutes: Int, seconds: Int) {
    val radius = 400F
    Canvas(modifier = Modifier.fillMaxSize()) {
        drawCircle(Color.Black, radius)
        drawCircle(Color.Green, 6F)

        for (i in 0..360 step 6) {
            val lineHeight: Float
            val strokeWidth: Float
            if (i % 30 == 0) {
                lineHeight = 40F
                strokeWidth = 2.dp.toPx()
            } else {
                lineHeight = 20F
                strokeWidth = .5.dp.toPx()
            }
            val angle = i.toFloat() * (PI / 180)
            val startX = center.x + radius * cos(angle)
            val startY = center.y + radius * sin(angle)
            val endX = center.x + (radius - lineHeight) * cos(angle)
            val endY = center.y + (radius - lineHeight) * sin(angle)

            drawLine(
                color = Color.Green,
                start = Offset(startX.toFloat(), startY.toFloat()),
                end = Offset(endX.toFloat(), endY.toFloat()),
                strokeWidth = strokeWidth
            )
            drawText(
                text = "Hello, world!",
                textMeasurer = TextMeasurer(),
                x = 10.dp.toPx(),
                y = 10.dp.toPx(),
            )
        }

        val hourTiltPercent = minutes * 6 / 360F * 30F
        val minuteTiltPercent = seconds / 60F * 6F

        val hourAngle = ((hour * 30F) - 90F + hourTiltPercent) * (PI / 180)
        val hourEndX = center.x + (radius - 150) * cos(hourAngle).toFloat()
        val hourEndY = center.y + (radius - 150) * sin(hourAngle).toFloat()
        drawLine(
            Color.Green,
            start = Offset(center.x, center.y),
            end = Offset(hourEndX, hourEndY),
            strokeWidth = 5F
        )

        val minuteAngle = ((minutes * 6F) - 90F + minuteTiltPercent) * (PI / 180)
        val minuteEndX = center.x + (radius - 100) * cos(minuteAngle).toFloat()
        val minuteEndY = center.y + (radius - 100) * sin(minuteAngle).toFloat()
        drawLine(
            Color.Green,
            start = Offset(center.x, center.y),
            end = Offset(minuteEndX, minuteEndY),
            strokeWidth = 3F
        )

        val secondAngle = ((seconds * 6F) - 90F) * (PI / 180)
        val secondEndX = center.x + (radius - 50) * cos(secondAngle).toFloat()
        val secondEndY = center.y + (radius - 50) * sin(secondAngle).toFloat()
        drawLine(
            Color.Green,
            start = Offset(center.x, center.y),
            end = Offset(secondEndX, secondEndY),
            strokeWidth = 2F
        )
    }
}

@Preview
@Composable
fun ClockPreview() {
    Clock(5, 45, 30)
}

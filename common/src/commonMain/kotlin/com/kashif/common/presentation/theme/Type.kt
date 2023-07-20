package com.kashif.common.presentation.theme

import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.kashif.common.font

@Composable
fun getTypography(): Typography {

    val nunitoRegular = FontFamily(
        font(
            "Nunito", "nunito_regular", FontWeight.Normal, FontStyle.Normal
        )
    )

    val nunitoSemiBold = FontFamily(
        font(
            "Nunito", "nunito_semibold", FontWeight.Normal, FontStyle.Normal
        )
    )
    val nunitoBold = FontFamily(
        font(
            "Nunito", "nunito_bold", FontWeight.Normal, FontStyle.Normal
        )
    )
   return Typography(
        h1 = TextStyle(
            fontFamily = nunitoBold,
            fontWeight = FontWeight.Bold,
            fontSize = 52.sp,
        ),
        h2 = TextStyle(fontFamily = nunitoBold, fontWeight = FontWeight.Bold, fontSize = 24.sp),
        h3 = TextStyle(
            fontFamily = nunitoBold,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
        ),
        h4 = TextStyle(
            fontFamily = nunitoBold,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
        ),
        h5 = TextStyle(fontFamily = nunitoBold, fontWeight = FontWeight.Bold, fontSize = 14.sp),
        h6 = TextStyle(
            fontFamily = nunitoSemiBold,
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.sp,
        ),
        subtitle1 = TextStyle(
            fontFamily = nunitoSemiBold,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
        ),
        subtitle2 = TextStyle(
            fontFamily = nunitoRegular,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
        ),
        body1 = TextStyle(
            fontFamily = nunitoRegular, fontWeight = FontWeight.Normal, fontSize = 14.sp
        ),
        body2 = TextStyle(fontFamily = nunitoRegular, fontSize = 10.sp),
        button = TextStyle(
            fontFamily = nunitoRegular,
            fontWeight = FontWeight.Normal,
            fontSize = 15.sp,
            color = OnPrimary
        ),
        caption = TextStyle(
            fontFamily = nunitoRegular, fontWeight = FontWeight.Normal, fontSize = 8.sp
        ),
        overline = TextStyle(
            fontFamily = nunitoRegular, fontWeight = FontWeight.Normal, fontSize = 12.sp
        )
    )
}
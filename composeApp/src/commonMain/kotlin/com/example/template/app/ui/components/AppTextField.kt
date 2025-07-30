package com.example.template.app.ui.components


import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit


@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textColor: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    textStyle: TextStyle = LocalTextStyle.current,

    // Label
    labelText: String? = null,
    labelTextStyle: TextStyle? = null, // Optional: To style the label

    // Placeholder
    placeholderText: String? = null,
    placeholderTextStyle: TextStyle? = null, // Optional: To style the placeholder

    // Supporting Text
    supportingTextString: String? = null,
    supportingTextStyle: TextStyle? = null, // Optional: To style the supporting text

    // Prefix & Suffix
    prefixText: String? = null,
    prefixTextStyle: TextStyle? = null,
    suffixText: String? = null,
    suffixTextStyle: TextStyle? = null,

    // Icons
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,

    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = OutlinedTextFieldDefaults.shape,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors()
) {
    // Merge the base textStyle with individual styling parameters for the input text
    val mergedInputTextStyle = textStyle.merge(
        textAlign?.let {
            TextStyle(
                color = textColor,
                fontSize = fontSize,
                fontStyle = fontStyle,
                fontWeight = fontWeight,
                fontFamily = fontFamily,
                letterSpacing = letterSpacing,
                textDecoration = textDecoration,
                textAlign = it,
                lineHeight = lineHeight
            )
        }
    )

    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        enabled = enabled,
        readOnly = readOnly,
        textStyle = mergedInputTextStyle, // Apply the merged style to the input text
        label = labelText?.let {
            {
                Text(
                    text = it,
                    style = labelTextStyle ?: LocalTextStyle.current
                )
            }
        },
        placeholder = placeholderText?.let {
            {
                Text(
                    text = it,
                    style = placeholderTextStyle ?: LocalTextStyle.current
                )
            }
        },
        supportingText = supportingTextString?.let {
            {
                Text(
                    text = it,
                    style = supportingTextStyle ?: LocalTextStyle.current
                )
            }
        },
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        prefix = prefixText?.let {
            {
                Text(
                    text = it,
                    style = prefixTextStyle ?: LocalTextStyle.current
                )
            }
        },
        suffix = suffixText?.let {
            {
                Text(
                    text = it,
                    style = suffixTextStyle ?: LocalTextStyle.current
                )
            }
        },
        isError = isError,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
        interactionSource = interactionSource,
        shape = shape,
        colors = colors
    )
}
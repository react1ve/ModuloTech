package com.example.core.type

/**
 * #Boolean #Transformation
 *
 * @return Int value: 1 - if true, 0 - if false
 */
fun Boolean.toInt(): Int = if (this) 1 else 0

/**
 * #Boolean #Transformation
 *
 * @return String value: "Yes" - if true, "No" - if false
 */
fun Boolean.asYesNo(): String = if (this) "Yes" else "No"

/**
 * #Boolean #Transformation
 *
 * @return String value: "True" - if true, "False" - if false
 */
fun Boolean.asTrueFalse(): String = if (this) "True" else "False"

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

object AppIcons {


    // CORRECTED NAVIGATION ICON
    val ArrowBack: ImageVector by lazy {
        ImageVector.Builder(
            name = "ArrowBack",
            defaultWidth = 24.0.dp,
            defaultHeight = 24.0.dp,
            viewportWidth = 24.0f,
            viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)),
                stroke = null,
                strokeLineWidth = 0.0f,
                strokeLineCap = Butt,
                strokeLineJoin = Miter,
                strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(20.0f, 11.0f)
                horizontalLineTo(7.83f)
                lineToRelative(5.59f, -5.59f)
                lineTo(12.0f, 4.0f)
                lineToRelative(-8.0f, 8.0f)
                lineToRelative(8.0f, 8.0f)
                lineToRelative(1.41f, -1.41f)
                lineTo(7.83f, 13.0f)
                horizontalLineTo(20.0f)
                verticalLineToRelative(-2.0f)
                close()
            }
        }.build()
    }
    // Game Controller Icon
    val Game: ImageVector by lazy {
        ImageVector.Builder(
            name = "Game",
            defaultWidth = 24.0.dp,
            defaultHeight = 24.0.dp,
            viewportWidth = 24.0f,
            viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)),
                stroke = null,
                strokeLineWidth = 0.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(21.58f, 16.09f)
                lineToRelative(-1.09f, -7.66f)
                curveTo(20.21f, 6.46f, 18.52f, 5.0f, 16.53f, 5.0f)
                horizontalLineTo(7.47f)
                curveTo(5.48f, 5.0f, 3.79f, 6.46f, 3.51f, 8.43f)
                lineToRelative(-1.09f, 7.66f)
                curveTo(2.2f, 17.63f, 3.39f, 19.0f, 4.94f, 19.0f)
                curveToRelative(0.68f, 0.0f, 1.32f, -0.27f, 1.8f, -0.75f)
                lineTo(9.0f, 16.0f)
                horizontalLineToRelative(6.0f)
                lineToRelative(2.25f, 2.25f)
                curveToRelative(0.48f, 0.48f, 1.13f, 0.75f, 1.8f, 0.75f)
                curveToRelative(1.56f, 0.0f, 2.75f, -1.37f, 2.53f, -2.91f)
                close()
                moveTo(11.0f, 11.0f)
                horizontalLineTo(9.0f)
                verticalLineToRelative(2.0f)
                horizontalLineTo(8.0f)
                verticalLineToRelative(-2.0f)
                horizontalLineTo(6.0f)
                verticalLineToRelative(-1.0f)
                horizontalLineToRelative(2.0f)
                verticalLineTo(8.0f)
                horizontalLineToRelative(1.0f)
                verticalLineToRelative(2.0f)
                horizontalLineToRelative(2.0f)
                verticalLineToRelative(1.0f)
                close()
                moveTo(15.5f, 12.0f)
                curveToRelative(-0.55f, 0.0f, -1.0f, -0.45f, -1.0f, -1.0f)
                reflectiveCurveToRelative(0.45f, -1.0f, 1.0f, -1.0f)
                reflectiveCurveToRelative(1.0f, 0.45f, 1.0f, 1.0f)
                reflectiveCurveToRelative(-0.45f, 1.0f, -1.0f, 1.0f)
                close()
                moveTo(17.5f, 10.0f)
                curveToRelative(-0.55f, 0.0f, -1.0f, -0.45f, -1.0f, -1.0f)
                reflectiveCurveToRelative(0.45f, -1.0f, 1.0f, -1.0f)
                reflectiveCurveToRelative(1.0f, 0.45f, 1.0f, 1.0f)
                reflectiveCurveToRelative(-0.45f, 1.0f, -1.0f, 1.0f)
                close()
            }
        }.build()
    }

    // Referral/Email Icon
    val Referral: ImageVector by lazy {
        ImageVector.Builder(
            name = "Referral",
            defaultWidth = 24.0.dp,
            defaultHeight = 24.0.dp,
            viewportWidth = 24.0f,
            viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)),
                stroke = null,
                strokeLineWidth = 0.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(20.0f, 4.0f)
                horizontalLineTo(4.0f)
                curveToRelative(-1.1f, 0.0f, -1.99f, 0.9f, -1.99f, 2.0f)
                lineTo(2.0f, 18.0f)
                curveToRelative(0.0f, 1.1f, 0.9f, 2.0f, 2.0f, 2.0f)
                horizontalLineToRelative(16.0f)
                curveToRelative(1.1f, 0.0f, 2.0f, -0.9f, 2.0f, -2.0f)
                verticalLineTo(6.0f)
                curveToRelative(0.0f, -1.1f, -0.9f, -2.0f, -2.0f, -2.0f)
                close()
                moveTo(20.0f, 8.0f)
                lineToRelative(-8.0f, 5.0f)
                lineToRelative(-8.0f, -5.0f)
                verticalLineTo(6.0f)
                lineToRelative(8.0f, 5.0f)
                lineToRelative(8.0f, -5.0f)
                verticalLineToRelative(2.0f)
                close()
            }
        }.build()
    }

    val ArrowDropDown: ImageVector by lazy {
        ImageVector.Builder(
            name = "ArrowDropDown",
            defaultWidth = 24.0.dp,
            defaultHeight = 24.0.dp,
            viewportWidth = 24.0f,
            viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)),
                stroke = null,
                strokeLineWidth = 0.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(7.0f, 10.0f)
                lineToRelative(5.0f, 5.0f)
                lineToRelative(5.0f, -5.0f)
                close()
            }
        }.build()
    }

    val ArrowDropUp: ImageVector by lazy {
        ImageVector.Builder(
            name = "ArrowDropUp",
            defaultWidth = 24.0.dp,
            defaultHeight = 24.0.dp,
            viewportWidth = 24.0f,
            viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)),
                stroke = null,
                strokeLineWidth = 0.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(7.0f, 14.0f)
                lineToRelative(5.0f, -5.0f)
                lineToRelative(5.0f, 5.0f)
                close()
            }
        }.build()
    }





    val LocationOn: ImageVector by lazy {
        ImageVector.Builder(
            name = "LocationOn",
            defaultWidth = 24.0.dp,
            defaultHeight = 24.0.dp,
            viewportWidth = 24.0f,
            viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)),
                stroke = null,
                strokeLineWidth = 0.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(12.0f, 2.0f)
                curveTo(8.13f, 2.0f, 5.0f, 5.13f, 5.0f, 9.0f)
                curveToRelative(0.0f, 5.25f, 7.0f, 13.0f, 7.0f, 13.0f)
                reflectiveCurveToRelative(7.0f, -7.75f, 7.0f, -13.0f)
                curveToRelative(0.0f, -3.87f, -3.13f, -7.0f, -7.0f, -7.0f)
                close()
                moveTo(12.0f, 11.5f)
                curveToRelative(-1.38f, 0.0f, -2.5f, -1.12f, -2.5f, -2.5f)
                reflectiveCurveToRelative(1.12f, -2.5f, 2.5f, -2.5f)
                reflectiveCurveToRelative(2.5f, 1.12f, 2.5f, 2.5f)
                reflectiveCurveToRelative(-1.12f, 2.5f, -2.5f, 2.5f)
                close()
            }
        }.build()
    }



    // News/Newspaper Icon
    val News: ImageVector by lazy {
        ImageVector.Builder(
            name = "News",
            defaultWidth = 24.0.dp,
            defaultHeight = 24.0.dp,
            viewportWidth = 24.0f,
            viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)),
                stroke = null,
                strokeLineWidth = 0.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(19.0f, 3.0f)
                horizontalLineTo(5.0f)
                curveToRelative(-1.1f, 0.0f, -2.0f, 0.9f, -2.0f, 2.0f)
                verticalLineToRelative(14.0f)
                curveToRelative(0.0f, 1.1f, 0.9f, 2.0f, 2.0f, 2.0f)
                horizontalLineToRelative(14.0f)
                curveToRelative(1.1f, 0.0f, 2.0f, -0.9f, 2.0f, -2.0f)
                verticalLineTo(5.0f)
                curveToRelative(0.0f, -1.1f, -0.9f, -2.0f, -2.0f, -2.0f)
                close()
                moveTo(19.0f, 19.0f)
                horizontalLineTo(5.0f)
                verticalLineTo(5.0f)
                horizontalLineToRelative(14.0f)
                verticalLineToRelative(14.0f)
                close()
                moveTo(7.0f, 10.0f)
                horizontalLineToRelative(10.0f)
                verticalLineToRelative(2.0f)
                horizontalLineTo(7.0f)
                close()
                moveTo(7.0f, 14.0f)
                horizontalLineToRelative(7.0f)
                verticalLineToRelative(2.0f)
                horizontalLineTo(7.0f)
                close()
                moveTo(7.0f, 6.0f)
                horizontalLineToRelative(10.0f)
                verticalLineToRelative(2.0f)
                horizontalLineTo(7.0f)
                close()
            }
        }.build()
    }

    // Vouchers/Gift Icon
    val Vouchers: ImageVector by lazy {
        ImageVector.Builder(
            name = "Vouchers",
            defaultWidth = 24.0.dp,
            defaultHeight = 24.0.dp,
            viewportWidth = 24.0f,
            viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)),
                stroke = null,
                strokeLineWidth = 0.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(20.0f, 6.0f)
                horizontalLineToRelative(-2.18f)
                curveToRelative(0.11f, -0.31f, 0.18f, -0.65f, 0.18f, -1.0f)
                curveToRelative(0.0f, -1.66f, -1.34f, -3.0f, -3.0f, -3.0f)
                curveToRelative(-1.05f, 0.0f, -1.96f, 0.54f, -2.5f, 1.35f)
                lineToRelative(-0.5f, 0.67f)
                lineToRelative(-0.5f, -0.68f)
                curveTo(10.96f, 2.54f, 10.05f, 2.0f, 9.0f, 2.0f)
                curveTo(7.34f, 2.0f, 6.0f, 3.34f, 6.0f, 5.0f)
                curveToRelative(0.0f, 0.35f, 0.07f, 0.69f, 0.18f, 1.0f)
                horizontalLineTo(4.0f)
                curveToRelative(-1.11f, 0.0f, -1.99f, 0.89f, -1.99f, 2.0f)
                lineTo(2.0f, 19.0f)
                curveToRelative(0.0f, 1.11f, 0.89f, 2.0f, 2.0f, 2.0f)
                horizontalLineToRelative(16.0f)
                curveToRelative(1.11f, 0.0f, 2.0f, -0.89f, 2.0f, -2.0f)
                verticalLineTo(8.0f)
                curveToRelative(0.0f, -1.11f, -0.89f, -2.0f, -2.0f, -2.0f)
                close()
                moveTo(15.0f, 4.0f)
                curveToRelative(0.55f, 0.0f, 1.0f, 0.45f, 1.0f, 1.0f)
                reflectiveCurveToRelative(-0.45f, 1.0f, -1.0f, 1.0f)
                reflectiveCurveToRelative(-1.0f, -0.45f, -1.0f, -1.0f)
                reflectiveCurveToRelative(0.45f, -1.0f, 1.0f, -1.0f)
                close()
                moveTo(9.0f, 4.0f)
                curveToRelative(0.55f, 0.0f, 1.0f, 0.45f, 1.0f, 1.0f)
                reflectiveCurveToRelative(-0.45f, 1.0f, -1.0f, 1.0f)
                reflectiveCurveToRelative(-1.0f, -0.45f, -1.0f, -1.0f)
                reflectiveCurveToRelative(0.45f, -1.0f, 1.0f, -1.0f)
                close()
                moveTo(20.0f, 19.0f)
                horizontalLineTo(4.0f)
                verticalLineToRelative(-2.0f)
                horizontalLineToRelative(16.0f)
                verticalLineToRelative(2.0f)
                close()
                moveTo(20.0f, 14.0f)
                horizontalLineTo(4.0f)
                verticalLineTo(8.0f)
                horizontalLineToRelative(5.08f)
                lineTo(7.0f, 10.83f)
                lineTo(8.62f, 12.0f)
                lineTo(12.0f, 7.4f)
                lineToRelative(3.38f, 4.6f)
                lineTo(17.0f, 10.83f)
                lineTo(14.92f, 8.0f)
                horizontalLineTo(20.0f)
                verticalLineToRelative(6.0f)
                close()
            }
        }.build()
    }

    // QR Code Icon (for bottom navigation)
    val QRCode: ImageVector by lazy {
        ImageVector.Builder(
            name = "QRCode",
            defaultWidth = 24.0.dp,
            defaultHeight = 24.0.dp,
            viewportWidth = 24.0f,
            viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)),
                stroke = null,
                strokeLineWidth = 0.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(3.0f, 11.0f)
                horizontalLineToRelative(8.0f)
                verticalLineTo(3.0f)
                horizontalLineTo(3.0f)
                verticalLineToRelative(8.0f)
                close()
                moveTo(5.0f, 5.0f)
                horizontalLineToRelative(4.0f)
                verticalLineToRelative(4.0f)
                horizontalLineTo(5.0f)
                verticalLineTo(5.0f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFF000000)),
                stroke = null,
                strokeLineWidth = 0.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(3.0f, 21.0f)
                horizontalLineToRelative(8.0f)
                verticalLineToRelative(-8.0f)
                horizontalLineTo(3.0f)
                verticalLineToRelative(8.0f)
                close()
                moveTo(5.0f, 15.0f)
                horizontalLineToRelative(4.0f)
                verticalLineToRelative(4.0f)
                horizontalLineTo(5.0f)
                verticalLineToRelative(-4.0f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFF000000)),
                stroke = null,
                strokeLineWidth = 0.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(13.0f, 3.0f)
                verticalLineToRelative(8.0f)
                horizontalLineToRelative(8.0f)
                verticalLineTo(3.0f)
                horizontalLineToRelative(-8.0f)
                close()
                moveTo(19.0f, 9.0f)
                horizontalLineToRelative(-4.0f)
                verticalLineTo(5.0f)
                horizontalLineToRelative(4.0f)
                verticalLineToRelative(4.0f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFF000000)),
                stroke = null,
                strokeLineWidth = 0.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(19.0f, 19.0f)
                horizontalLineToRelative(2.0f)
                verticalLineToRelative(2.0f)
                horizontalLineToRelative(-2.0f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFF000000)),
                stroke = null,
                strokeLineWidth = 0.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(13.0f, 13.0f)
                horizontalLineToRelative(2.0f)
                verticalLineToRelative(2.0f)
                horizontalLineToRelative(-2.0f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFF000000)),
                stroke = null,
                strokeLineWidth = 0.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(15.0f, 15.0f)
                horizontalLineToRelative(2.0f)
                verticalLineToRelative(2.0f)
                horizontalLineToRelative(-2.0f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFF000000)),
                stroke = null,
                strokeLineWidth = 0.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(13.0f, 17.0f)
                horizontalLineToRelative(2.0f)
                verticalLineToRelative(2.0f)
                horizontalLineToRelative(-2.0f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFF000000)),
                stroke = null,
                strokeLineWidth = 0.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(15.0f, 19.0f)
                horizontalLineToRelative(2.0f)
                verticalLineToRelative(2.0f)
                horizontalLineToRelative(-2.0f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFF000000)),
                stroke = null,
                strokeLineWidth = 0.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(17.0f, 17.0f)
                horizontalLineToRelative(2.0f)
                verticalLineToRelative(2.0f)
                horizontalLineToRelative(-2.0f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFF000000)),
                stroke = null,
                strokeLineWidth = 0.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(19.0f, 15.0f)
                horizontalLineToRelative(2.0f)
                verticalLineToRelative(2.0f)
                horizontalLineToRelative(-2.0f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFF000000)),
                stroke = null,
                strokeLineWidth = 0.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(17.0f, 13.0f)
                horizontalLineToRelative(2.0f)
                verticalLineToRelative(2.0f)
                horizontalLineToRelative(-2.0f)
                close()
            }
        }.build()
    }

    // Home Icon
    val Home2: ImageVector by lazy {
        ImageVector.Builder(
            name = "Home",
            defaultWidth = 24.0.dp,
            defaultHeight = 24.0.dp,
            viewportWidth = 24.0f,
            viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)),
                stroke = null,
                strokeLineWidth = 0.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(10.0f, 20.0f)
                verticalLineToRelative(-6.0f)
                horizontalLineToRelative(4.0f)
                verticalLineToRelative(6.0f)
                horizontalLineToRelative(5.0f)
                verticalLineToRelative(-8.0f)
                horizontalLineToRelative(3.0f)
                lineTo(12.0f, 3.0f)
                lineTo(2.0f, 12.0f)
                horizontalLineToRelative(3.0f)
                verticalLineToRelative(8.0f)
                close()
            }
        }.build()
    }

    // Store/Shop Icon
    val Store2: ImageVector by lazy {
        ImageVector.Builder(
            name = "Store",
            defaultWidth = 24.0.dp,
            defaultHeight = 24.0.dp,
            viewportWidth = 24.0f,
            viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)),
                stroke = null,
                strokeLineWidth = 0.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(20.0f, 4.0f)
                horizontalLineTo(4.0f)
                verticalLineToRelative(2.0f)
                horizontalLineToRelative(16.0f)
                verticalLineTo(4.0f)
                close()
                moveTo(21.0f, 14.0f)
                verticalLineToRelative(-2.0f)
                lineToRelative(-1.0f, -5.0f)
                horizontalLineTo(4.0f)
                lineToRelative(-1.0f, 5.0f)
                verticalLineToRelative(2.0f)
                horizontalLineToRelative(1.0f)
                verticalLineToRelative(6.0f)
                horizontalLineToRelative(10.0f)
                verticalLineToRelative(-6.0f)
                horizontalLineToRelative(4.0f)
                verticalLineToRelative(6.0f)
                horizontalLineToRelative(2.0f)
                verticalLineToRelative(-6.0f)
                horizontalLineToRelative(1.0f)
                close()
                moveTo(12.0f, 18.0f)
                horizontalLineTo(6.0f)
                verticalLineToRelative(-4.0f)
                horizontalLineToRelative(6.0f)
                verticalLineToRelative(4.0f)
                close()
            }
        }.build()
    }

    // Reward/Gift Card Icon
    val Reward: ImageVector by lazy {
        ImageVector.Builder(
            name = "Reward",
            defaultWidth = 24.0.dp,
            defaultHeight = 24.0.dp,
            viewportWidth = 24.0f,
            viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)),
                stroke = null,
                strokeLineWidth = 0.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(21.41f, 11.58f)
                lineToRelative(-9.0f, -9.0f)
                curveTo(12.05f, 2.22f, 11.55f, 2.0f, 11.0f, 2.0f)
                horizontalLineTo(4.0f)
                curveToRelative(-1.1f, 0.0f, -2.0f, 0.9f, -2.0f, 2.0f)
                verticalLineToRelative(7.0f)
                curveToRelative(0.0f, 0.55f, 0.22f, 1.05f, 0.59f, 1.42f)
                lineToRelative(9.0f, 9.0f)
                curveToRelative(0.36f, 0.36f, 0.86f, 0.58f, 1.41f, 0.58f)
                reflectiveCurveToRelative(1.05f, -0.22f, 1.41f, -0.59f)
                lineToRelative(7.0f, -7.0f)
                curveToRelative(0.37f, -0.36f, 0.59f, -0.86f, 0.59f, -1.41f)
                reflectiveCurveToRelative(-0.23f, -1.06f, -0.59f, -1.42f)
                close()
                moveTo(5.5f, 7.0f)
                curveTo(4.67f, 7.0f, 4.0f, 6.33f, 4.0f, 5.5f)
                reflectiveCurveTo(4.67f, 4.0f, 5.5f, 4.0f)
                reflectiveCurveTo(7.0f, 4.67f, 7.0f, 5.5f)
                reflectiveCurveTo(6.33f, 7.0f, 5.5f, 7.0f)
                close()
            }
        }.build()
    }

    // Profile/Person Icon
    val Profile: ImageVector by lazy {
        ImageVector.Builder(
            name = "Profile",
            defaultWidth = 24.0.dp,
            defaultHeight = 24.0.dp,
            viewportWidth = 24.0f,
            viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)),
                stroke = null,
                strokeLineWidth = 0.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(12.0f, 12.0f)
                curveToRelative(2.21f, 0.0f, 4.0f, -1.79f, 4.0f, -4.0f)
                reflectiveCurveToRelative(-1.79f, -4.0f, -4.0f, -4.0f)
                reflectiveCurveToRelative(-4.0f, 1.79f, -4.0f, 4.0f)
                reflectiveCurveToRelative(1.79f, 4.0f, 4.0f, 4.0f)
                close()
                moveTo(12.0f, 14.0f)
                curveToRelative(-2.67f, 0.0f, -8.0f, 1.34f, -8.0f, 4.0f)
                verticalLineToRelative(2.0f)
                horizontalLineToRelative(16.0f)
                verticalLineToRelative(-2.0f)
                curveToRelative(0.0f, -2.66f, -5.33f, -4.0f, -8.0f, -4.0f)
                close()
            }
        }.build()
    }

    // Arrow Back Icon (included for completeness)
    val ArrowBack2: ImageVector by lazy {
        ImageVector.Builder(
            name = "ArrowBack",
            defaultWidth = 24.0.dp,
            defaultHeight = 24.0.dp,
            viewportWidth = 24.0f,
            viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)),
                stroke = null,
                strokeLineWidth = 0.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(20.0f, 11.0f)
                horizontalLineTo(7.83f)
                lineToRelative(5.59f, -5.59f)
                lineTo(12.0f, 4.0f)
                lineToRelative(-8.0f, 8.0f)
                lineToRelative(8.0f, 8.0f)
                lineToRelative(1.41f, -1.41f)
                lineTo(7.83f, 13.0f)
                horizontalLineTo(20.0f)
                verticalLineToRelative(-2.0f)
                close()
            }
        }.build()
    }

    // Discount/Percent Icon
    val Discount: ImageVector by lazy {
        ImageVector.Builder(
            name = "Discount",
            defaultWidth = 24.0.dp,
            defaultHeight = 24.0.dp,
            viewportWidth = 24.0f,
            viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)),
                stroke = null,
                strokeLineWidth = 0.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 4.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(12.79f, 21.0f)
                lineTo(20.13f, 3.0f)
                horizontalLineTo(22.0f)
                lineToRelative(-7.34f, 18.0f)
                horizontalLineTo(12.79f)
                close()
                moveTo(5.5f, 10.0f)
                curveTo(6.88f, 10.0f, 8.0f, 8.88f, 8.0f, 7.5f)
                reflectiveCurveTo(6.88f, 5.0f, 5.5f, 5.0f)
                reflectiveCurveTo(3.0f, 6.12f, 3.0f, 7.5f)
                reflectiveCurveTo(4.12f, 10.0f, 5.5f, 10.0f)
                close()
                moveTo(5.5f, 6.0f)
                curveTo(6.33f, 6.0f, 7.0f, 6.67f, 7.0f, 7.5f)
                reflectiveCurveTo(6.33f, 9.0f, 5.5f, 9.0f)
                reflectiveCurveTo(4.0f, 8.33f, 4.0f, 7.5f)
                reflectiveCurveTo(4.67f, 6.0f, 5.5f, 6.0f)
                close()
                moveTo(18.5f, 19.0f)
                curveToRelative(-1.38f, 0.0f, -2.5f, -1.12f, -2.5f, -2.5f)
                reflectiveCurveToRelative(1.12f, -2.5f, 2.5f, -2.5f)
                reflectiveCurveToRelative(2.5f, 1.12f, 2.5f, 2.5f)
                reflectiveCurveToRelative(-1.12f, 2.5f, -2.5f, 2.5f)
                close()
                moveTo(18.5f, 15.0f)
                curveToRelative(-0.83f, 0.0f, -1.5f, 0.67f, -1.5f, 1.5f)
                reflectiveCurveToRelative(0.67f, 1.5f, 1.5f, 1.5f)
                reflectiveCurveToRelative(1.5f, -0.67f, 1.5f, -1.5f)
                reflectiveCurveToRelative(-0.67f, -1.5f, -1.5f, -1.5f)
                close()
            }
        }.build()
    }

    val Delete: ImageVector by lazy {
        ImageVector.Builder(
            name = "Delete",
            defaultWidth = 24.0.dp,
            defaultHeight = 24.0.dp,
            viewportWidth = 24.0f,
            viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)),
                stroke = null,
                strokeLineWidth = 0.0f,
                strokeLineCap = Butt,
                strokeLineJoin = Miter,
                strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(6.0f, 19.0f)
                curveToRelative(0.0f, 1.1f, 0.9f, 2.0f, 2.0f, 2.0f)
                horizontalLineToRelative(8.0f)
                curveToRelative(1.1f, 0.0f, 2.0f, -0.9f, 2.0f, -2.0f)
                verticalLineTo(7.0f)
                horizontalLineTo(6.0f)
                verticalLineToRelative(12.0f)
                close()
                moveTo(19.0f, 4.0f)
                horizontalLineToRelative(-3.5f)
                lineToRelative(-1.0f, -1.0f)
                horizontalLineToRelative(-5.0f)
                lineToRelative(-1.0f, 1.0f)
                horizontalLineTo(5.0f)
                verticalLineToRelative(2.0f)
                horizontalLineToRelative(14.0f)
                verticalLineTo(4.0f)
                close()
            }
        }.build()
    }

    val Refresh: ImageVector by lazy {
        ImageVector.Builder(
            name = "Refresh",
            defaultWidth = 24.0.dp,
            defaultHeight = 24.0.dp,
            viewportWidth = 24.0f,
            viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)),
                stroke = null,
                strokeLineWidth = 0.0f,
                strokeLineCap = Butt,
                strokeLineJoin = Miter,
                strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(17.65f, 6.35f)
                curveTo(16.2f, 4.9f, 14.21f, 4.0f, 12.0f, 4.0f)
                curveToRelative(-4.42f, 0.0f, -7.99f, 3.58f, -7.99f, 8.0f)
                reflectiveCurveToRelative(3.57f, 8.0f, 7.99f, 8.0f)
                curveToRelative(3.73f, 0.0f, 6.84f, -2.55f, 7.73f, -6.0f)
                horizontalLineToRelative(-2.08f)
                curveToRelative(-0.82f, 2.33f, -3.04f, 4.0f, -5.65f, 4.0f)
                curveToRelative(-3.31f, 0.0f, -6.0f, -2.69f, -6.0f, -6.0f)
                reflectiveCurveToRelative(2.69f, -6.0f, 6.0f, -6.0f)
                curveToRelative(1.66f, 0.0f, 3.14f, 0.69f, 4.22f, 1.78f)
                lineTo(13.0f, 11.0f)
                horizontalLineToRelative(7.0f)
                verticalLineTo(4.0f)
                lineToRelative(-2.35f, 2.35f)
                close()
            }
        }.build()
    }

    // THEME ICONS
    val DarkMode: ImageVector by lazy {
        ImageVector.Builder(
            name = "DarkMode",
            defaultWidth = 24.0.dp,
            defaultHeight = 24.0.dp,
            viewportWidth = 24.0f,
            viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)),
                stroke = null,
                strokeLineWidth = 0.0f,
                strokeLineCap = Butt,
                strokeLineJoin = Miter,
                strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(12.0f, 3.0f)
                arcToRelative(9.0f, 9.0f, 0.0f, true, false, 9.0f, 9.0f)
                curveToRelative(0.0f, -0.46f, -0.04f, -0.92f, -0.1f, -1.36f)
                arcToRelative(5.389f, 5.389f, 0.0f, false, true, -4.4f, 2.26f)
                arcToRelative(5.403f, 5.403f, 0.0f, false, true, -3.14f, -9.8f)
                curveToRelative(-0.44f, -0.06f, -0.9f, -0.1f, -1.36f, -0.1f)
                close()
            }
        }.build()
    }

    val LightMode: ImageVector by lazy {
        ImageVector.Builder(
            name = "LightMode",
            defaultWidth = 24.0.dp,
            defaultHeight = 24.0.dp,
            viewportWidth = 24.0f,
            viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)),
                stroke = null,
                strokeLineWidth = 0.0f,
                strokeLineCap = Butt,
                strokeLineJoin = Miter,
                strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(12.0f, 9.0f)
                curveToRelative(1.66f, 0.0f, 3.0f, 1.34f, 3.0f, 3.0f)
                reflectiveCurveToRelative(-1.34f, 3.0f, -3.0f, 3.0f)
                reflectiveCurveToRelative(-3.0f, -1.34f, -3.0f, -3.0f)
                reflectiveCurveToRelative(1.34f, -3.0f, 3.0f, -3.0f)
                close()
                moveTo(12.0f, 17.0f)
                curveToRelative(2.76f, 0.0f, 5.0f, -2.24f, 5.0f, -5.0f)
                reflectiveCurveToRelative(-2.24f, -5.0f, -5.0f, -5.0f)
                reflectiveCurveToRelative(-5.0f, 2.24f, -5.0f, 5.0f)
                reflectiveCurveToRelative(2.24f, 5.0f, 5.0f, 5.0f)
                close()
                moveTo(12.0f, 1.0f)
                lineToRelative(0.0f, 2.0f)
                verticalLineToRelative(2.0f)
                lineToRelative(0.0f, -2.0f)
                verticalLineTo(1.0f)
                close()
                moveTo(12.0f, 19.0f)
                verticalLineToRelative(2.0f)
                lineToRelative(0.0f, 2.0f)
                verticalLineToRelative(-2.0f)
                verticalLineToRelative(-2.0f)
                close()
                moveTo(4.22f, 5.64f)
                lineToRelative(1.41f, -1.41f)
                lineToRelative(1.42f, 1.42f)
                lineToRelative(-1.42f, -1.42f)
                lineToRelative(-1.41f, 1.41f)
                close()
                moveTo(17.36f, 18.78f)
                lineToRelative(1.41f, -1.41f)
                lineToRelative(1.42f, 1.42f)
                lineToRelative(-1.42f, -1.42f)
                lineToRelative(-1.41f, 1.41f)
                close()
                moveTo(1.0f, 13.0f)
                horizontalLineToRelative(2.0f)
                verticalLineToRelative(-2.0f)
                horizontalLineTo(1.0f)
                verticalLineToRelative(2.0f)
                close()
                moveTo(21.0f, 13.0f)
                horizontalLineToRelative(2.0f)
                verticalLineToRelative(-2.0f)
                horizontalLineToRelative(-2.0f)
                verticalLineToRelative(2.0f)
                close()
                moveTo(4.22f, 18.36f)
                lineToRelative(1.41f, 1.41f)
                lineToRelative(1.42f, -1.42f)
                lineToRelative(-1.42f, 1.42f)
                lineToRelative(-1.41f, -1.41f)
                close()
                moveTo(18.36f, 5.64f)
                lineToRelative(1.41f, 1.41f)
                lineToRelative(1.42f, -1.42f)
                lineToRelative(-1.42f, 1.42f)
                lineToRelative(-1.41f, -1.41f)
                close()
            }
        }.build()
    }

    // LOGOUT ICON
    val Logout: ImageVector by lazy {
        ImageVector.Builder(
            name = "Logout",
            defaultWidth = 24.0.dp,
            defaultHeight = 24.0.dp,
            viewportWidth = 24.0f,
            viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)),
                stroke = null,
                strokeLineWidth = 0.0f,
                strokeLineCap = Butt,
                strokeLineJoin = Miter,
                strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(17.0f, 7.0f)
                lineToRelative(-1.41f, 1.41f)
                lineTo(18.17f, 11.0f)
                horizontalLineTo(8.0f)
                verticalLineToRelative(2.0f)
                horizontalLineToRelative(10.17f)
                lineToRelative(-2.58f, 2.58f)
                lineTo(17.0f, 17.0f)
                lineToRelative(5.0f, -5.0f)
                lineToRelative(-5.0f, -5.0f)
                close()
                moveTo(4.0f, 5.0f)
                horizontalLineToRelative(8.0f)
                verticalLineTo(3.0f)
                horizontalLineTo(4.0f)
                curveToRelative(-1.1f, 0.0f, -2.0f, 0.9f, -2.0f, 2.0f)
                verticalLineToRelative(14.0f)
                curveToRelative(0.0f, 1.1f, 0.9f, 2.0f, 2.0f, 2.0f)
                horizontalLineToRelative(8.0f)
                verticalLineToRelative(-2.0f)
                horizontalLineTo(4.0f)
                verticalLineTo(5.0f)
                close()
            }
        }.build()
    }

    // LOCATION ICON
    val Location: ImageVector by lazy {
        ImageVector.Builder(
            name = "Location",
            defaultWidth = 24.0.dp,
            defaultHeight = 24.0.dp,
            viewportWidth = 24.0f,
            viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)),
                stroke = null,
                strokeLineWidth = 0.0f,
                strokeLineCap = Butt,
                strokeLineJoin = Miter,
                strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(12.0f, 2.0f)
                curveTo(8.13f, 2.0f, 5.0f, 5.13f, 5.0f, 9.0f)
                curveToRelative(0.0f, 5.25f, 7.0f, 13.0f, 7.0f, 13.0f)
                reflectiveCurveToRelative(7.0f, -7.75f, 7.0f, -13.0f)
                curveToRelative(0.0f, -3.87f, -3.13f, -7.0f, -7.0f, -7.0f)
                close()
                moveTo(12.0f, 11.5f)
                curveToRelative(-1.38f, 0.0f, -2.5f, -1.12f, -2.5f, -2.5f)
                reflectiveCurveToRelative(1.12f, -2.5f, 2.5f, -2.5f)
                reflectiveCurveToRelative(2.5f, 1.12f, 2.5f, 2.5f)
                reflectiveCurveToRelative(-1.12f, 2.5f, -2.5f, 2.5f)
                close()
            }
        }.build()
    }

    // MAP ICON
    val Map: ImageVector by lazy {
        ImageVector.Builder(
            name = "Map",
            defaultWidth = 24.0.dp,
            defaultHeight = 24.0.dp,
            viewportWidth = 24.0f,
            viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)),
                stroke = null,
                strokeLineWidth = 0.0f,
                strokeLineCap = Butt,
                strokeLineJoin = Miter,
                strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(20.5f, 3.0f)
                lineToRelative(-0.16f, 0.03f)
                lineTo(15.0f, 5.1f)
                lineTo(9.0f, 3.0f)
                lineToRelative(-0.34f, 0.03f)
                lineTo(3.5f, 4.9f)
                curveToRelative(-0.3f, 0.15f, -0.5f, 0.46f, -0.5f, 0.8f)
                verticalLineToRelative(14.4f)
                curveToRelative(0.0f, 0.83f, 0.67f, 1.5f, 1.5f, 1.5f)
                curveToRelative(0.17f, 0.0f, 0.33f, -0.03f, 0.5f, -0.1f)
                lineTo(9.0f, 19.9f)
                lineToRelative(6.0f, 2.1f)
                lineToRelative(5.16f, -1.9f)
                curveToRelative(0.3f, -0.15f, 0.5f, -0.46f, 0.5f, -0.8f)
                verticalLineTo(4.9f)
                curveToRelative(0.0f, -0.83f, -0.67f, -1.5f, -1.5f, -1.5f)
                curveToRelative(-0.17f, 0.0f, -0.33f, 0.03f, -0.5f, 0.1f)
                lineToRelative(-0.16f, 0.5f)
                close()
                moveTo(15.0f, 19.0f)
                lineToRelative(-6.0f, -2.11f)
                verticalLineTo(5.11f)
                lineTo(15.0f, 7.0f)
                verticalLineTo(19.0f)
                close()
            }
        }.build()
    }

    // CHECK ICON
    val Check: ImageVector by lazy {
        ImageVector.Builder(
            name = "Check",
            defaultWidth = 24.0.dp,
            defaultHeight = 24.0.dp,
            viewportWidth = 24.0f,
            viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)),
                stroke = null,
                strokeLineWidth = 0.0f,
                strokeLineCap = Butt,
                strokeLineJoin = Miter,
                strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(9.0f, 16.17f)
                lineTo(4.83f, 12.0f)
                lineToRelative(-1.42f, 1.41f)
                lineTo(9.0f, 19.0f)
                lineTo(21.0f, 7.0f)
                lineToRelative(-1.41f, -1.41f)
                close()
            }
        }.build()
    }

    // CLOSE ICON
    val Close: ImageVector by lazy {
        ImageVector.Builder(
            name = "Close",
            defaultWidth = 24.0.dp,
            defaultHeight = 24.0.dp,
            viewportWidth = 24.0f,
            viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)),
                stroke = null,
                strokeLineWidth = 0.0f,
                strokeLineCap = Butt,
                strokeLineJoin = Miter,
                strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(19.0f, 6.41f)
                lineTo(17.59f, 5.0f)
                lineTo(12.0f, 10.59f)
                lineTo(6.41f, 5.0f)
                lineTo(5.0f, 6.41f)
                lineTo(10.59f, 12.0f)
                lineTo(5.0f, 17.59f)
                lineTo(6.41f, 19.0f)
                lineTo(12.0f, 13.41f)
                lineTo(17.59f, 19.0f)
                lineTo(19.0f, 17.59f)
                lineTo(13.41f, 12.0f)
                close()
            }
        }.build()
    }

    // MORE OPTIONS ICON
    val MoreVert: ImageVector by lazy {
        ImageVector.Builder(
            name = "MoreVert",
            defaultWidth = 24.0.dp,
            defaultHeight = 24.0.dp,
            viewportWidth = 24.0f,
            viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)),
                stroke = null,
                strokeLineWidth = 0.0f,
                strokeLineCap = Butt,
                strokeLineJoin = Miter,
                strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(12.0f, 8.0f)
                curveToRelative(1.1f, 0.0f, 2.0f, -0.9f, 2.0f, -2.0f)
                reflectiveCurveToRelative(-0.9f, -2.0f, -2.0f, -2.0f)
                reflectiveCurveToRelative(-2.0f, 0.9f, -2.0f, 2.0f)
                reflectiveCurveToRelative(0.9f, 2.0f, 2.0f, 2.0f)
                close()
                moveTo(12.0f, 10.0f)
                curveToRelative(-1.1f, 0.0f, -2.0f, 0.9f, -2.0f, 2.0f)
                reflectiveCurveToRelative(0.9f, 2.0f, 2.0f, 2.0f)
                reflectiveCurveToRelative(2.0f, -0.9f, 2.0f, -2.0f)
                reflectiveCurveToRelative(-0.9f, -2.0f, -2.0f, -2.0f)
                close()
                moveTo(12.0f, 16.0f)
                curveToRelative(-1.1f, 0.0f, -2.0f, 0.9f, -2.0f, 2.0f)
                reflectiveCurveToRelative(0.9f, 2.0f, 2.0f, 2.0f)
                reflectiveCurveToRelative(2.0f, -0.9f, 2.0f, -2.0f)
                reflectiveCurveToRelative(-0.9f, -2.0f, -2.0f, -2.0f)
                close()
            }
        }.build()
    }

val ArrowForward: ImageVector by lazy {
    ImageVector.Builder(
        name = "ArrowForward",
        defaultWidth = 24.0.dp,
        defaultHeight = 24.0.dp,
        viewportWidth = 24.0f,
        viewportHeight = 24.0f
    ).apply {
        path(
            fill = SolidColor(Color(0xFF000000)),
            stroke = null,
            strokeLineWidth = 0.0f,
            strokeLineCap = Butt,
            strokeLineJoin = Miter,
            strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(12.0f, 4.0f)
            lineToRelative(-1.41f, 1.41f)
            lineTo(16.17f, 11.0f)
            horizontalLineTo(4.0f)
            verticalLineToRelative(2.0f)
            horizontalLineToRelative(12.17f)
            lineToRelative(-5.58f, 5.59f)
            lineTo(12.0f, 20.0f)
            lineToRelative(8.0f, -8.0f)
            lineToRelative(-8.0f, -8.0f)
            close()
        }
    }.build()
}

// CORE APP ICONS
val Home: ImageVector by lazy {
    ImageVector.Builder(
        name = "Home",
        defaultWidth = 24.0.dp,
        defaultHeight = 24.0.dp,
        viewportWidth = 24.0f,
        viewportHeight = 24.0f
    ).apply {
        path(
            fill = SolidColor(Color(0xFF000000)),
            stroke = null,
            strokeLineWidth = 0.0f,
            strokeLineCap = Butt,
            strokeLineJoin = Miter,
            strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(10.0f, 20.0f)
            verticalLineToRelative(-6.0f)
            horizontalLineToRelative(4.0f)
            verticalLineToRelative(6.0f)
            horizontalLineToRelative(5.0f)
            verticalLineToRelative(-8.0f)
            horizontalLineToRelative(3.0f)
            lineTo(12.0f, 3.0f)
            lineTo(2.0f, 12.0f)
            horizontalLineToRelative(3.0f)
            verticalLineToRelative(8.0f)
            close()
        }
    }.build()
}

val Person: ImageVector by lazy {
    ImageVector.Builder(
        name = "Person",
        defaultWidth = 24.0.dp,
        defaultHeight = 24.0.dp,
        viewportWidth = 24.0f,
        viewportHeight = 24.0f
    ).apply {
        path(
            fill = SolidColor(Color(0xFF000000)),
            stroke = null,
            strokeLineWidth = 0.0f,
            strokeLineCap = Butt,
            strokeLineJoin = Miter,
            strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(12.0f, 4.0f)
            curveToRelative(2.2f, 0.0f, 4.0f, 1.8f, 4.0f, 4.0f)
            reflectiveCurveToRelative(-1.8f, 4.0f, -4.0f, 4.0f)
            reflectiveCurveToRelative(-4.0f, -1.8f, -4.0f, -4.0f)
            reflectiveCurveToRelative(1.8f, -4.0f, 4.0f, -4.0f)
            close()
            moveTo(6.0f, 14.0f)
            curveToRelative(0.0f, -2.0f, 2.7f, -3.0f, 6.0f, -3.0f)
            reflectiveCurveToRelative(6.0f, 1.0f, 6.0f, 3.0f)
            verticalLineToRelative(6.0f)
            horizontalLineTo(6.0f)
            verticalLineToRelative(-6.0f)
            close()
        }
    }.build()
}
    val Outlet: ImageVector by lazy {
        ImageVector.Builder(
            name = "Outlet",
            defaultWidth = 24.0.dp,
            defaultHeight = 24.0.dp,
            viewportWidth = 24.0f,
            viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)),
                stroke = null,
                strokeLineWidth = 0.0f,
                strokeLineCap = Butt,
                strokeLineJoin = Miter,
                strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                // Outer pin shape (like a location marker)
                moveTo(12.0f, 2.0f)
                curveToRelative(-3.3f, 0.0f, -6.0f, 2.7f, -6.0f, 6.0f)
                curveToRelative(0.0f, 4.5f, 6.0f, 12.0f, 6.0f, 12.0f)
                reflectiveCurveToRelative(6.0f, -7.5f, 6.0f, -12.0f)
                curveToRelative(0.0f, -3.3f, -2.7f, -6.0f, -6.0f, -6.0f)
                close()

                // Inner circle (outlet center)
                moveTo(12.0f, 9.0f)
                curveToRelative(-1.1f, 0.0f, -2.0f, -0.9f, -2.0f, -2.0f)
                reflectiveCurveToRelative(0.9f, -2.0f, 2.0f, -2.0f)
                reflectiveCurveToRelative(2.0f, 0.9f, 2.0f, 2.0f)
                reflectiveCurveToRelative(-0.9f, 2.0f, -2.0f, 2.0f)
                close()
            }
        }.build()
    }

val Settings: ImageVector by lazy {
    ImageVector.Builder(
        name = "Settings",
        defaultWidth = 24.0.dp,
        defaultHeight = 24.0.dp,
        viewportWidth = 24.0f,
        viewportHeight = 24.0f
    ).apply {
        path(
            fill = SolidColor(Color(0xFF000000)),
            stroke = null,
            strokeLineWidth = 0.0f,
            strokeLineCap = Butt,
            strokeLineJoin = Miter,
            strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(19.14f, 12.94f)
            curveToRelative(0.04f, -0.3f, 0.06f, -0.61f, 0.06f, -0.94f)
            curveToRelative(0.0f, -0.32f, -0.02f, -0.64f, -0.07f, -0.94f)
            lineToRelative(2.03f, -1.58f)
            curveToRelative(0.18f, -0.14f, 0.23f, -0.41f, 0.12f, -0.61f)
            lineToRelative(-1.92f, -3.32f)
            curveToRelative(-0.12f, -0.22f, -0.37f, -0.29f, -0.59f, -0.22f)
            lineToRelative(-2.39f, 0.96f)
            curveToRelative(-0.5f, -0.38f, -1.03f, -0.7f, -1.62f, -0.94f)
            lineTo(14.4f, 2.81f)
            curveToRelative(-0.04f, -0.24f, -0.24f, -0.41f, -0.48f, -0.41f)
            horizontalLineToRelative(-3.84f)
            curveToRelative(-0.24f, 0.0f, -0.43f, 0.17f, -0.47f, 0.41f)
            lineTo(9.25f, 5.35f)
            curveTo(8.66f, 5.59f, 8.12f, 5.92f, 7.63f, 6.29f)
            lineTo(5.24f, 5.33f)
            curveToRelative(-0.22f, -0.08f, -0.47f, 0.0f, -0.59f, 0.22f)
            lineTo(2.74f, 8.87f)
            curveTo(2.62f, 9.08f, 2.66f, 9.34f, 2.86f, 9.48f)
            lineToRelative(2.03f, 1.58f)
            curveTo(4.84f, 11.36f, 4.8f, 11.69f, 4.8f, 12.0f)
            reflectiveCurveToRelative(0.02f, 0.64f, 0.07f, 0.94f)
            lineToRelative(-2.03f, 1.58f)
            curveToRelative(-0.18f, 0.14f, -0.23f, 0.41f, -0.12f, 0.61f)
            lineToRelative(1.92f, 3.32f)
            curveToRelative(0.12f, 0.22f, 0.37f, 0.29f, 0.59f, 0.22f)
            lineToRelative(2.39f, -0.96f)
            curveToRelative(0.5f, 0.38f, 1.03f, 0.7f, 1.62f, 0.94f)
            lineToRelative(0.36f, 2.54f)
            curveToRelative(0.05f, 0.24f, 0.24f, 0.41f, 0.48f, 0.41f)
            horizontalLineToRelative(3.84f)
            curveToRelative(0.24f, 0.0f, 0.44f, -0.17f, 0.47f, -0.41f)
            lineToRelative(0.36f, -2.54f)
            curveToRelative(0.59f, -0.24f, 1.13f, -0.56f, 1.62f, -0.94f)
            lineToRelative(2.39f, 0.96f)
            curveToRelative(0.22f, 0.08f, 0.47f, 0.0f, 0.59f, -0.22f)
            lineToRelative(1.92f, -3.32f)
            curveToRelative(0.12f, -0.22f, 0.07f, -0.47f, -0.12f, -0.61f)
            lineTo(19.14f, 12.94f)
            close()
            moveTo(12.0f, 15.6f)
            curveToRelative(-1.98f, 0.0f, -3.6f, -1.62f, -3.6f, -3.6f)
            reflectiveCurveToRelative(1.62f, -3.6f, 3.6f, -3.6f)
            reflectiveCurveToRelative(3.6f, 1.62f, 3.6f, 3.6f)
            reflectiveCurveTo(13.98f, 15.6f, 12.0f, 15.6f)
            close()
        }
    }.build()
}

val Add: ImageVector by lazy {
    ImageVector.Builder(
        name = "Add",
        defaultWidth = 24.0.dp,
        defaultHeight = 24.0.dp,
        viewportWidth = 24.0f,
        viewportHeight = 24.0f
    ).apply {
        path(
            fill = SolidColor(Color(0xFF000000)),
            stroke = null,
            strokeLineWidth = 0.0f,
            strokeLineCap = Butt,
            strokeLineJoin = Miter,
            strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(11.0f, 11.0f)
            verticalLineTo(5.0f)
            horizontalLineToRelative(2.0f)
            verticalLineToRelative(6.0f)
            horizontalLineToRelative(6.0f)
            verticalLineToRelative(2.0f)
            horizontalLineToRelative(-6.0f)
            verticalLineToRelative(6.0f)
            horizontalLineToRelative(-2.0f)
            verticalLineToRelative(-6.0f)
            horizontalLineTo(5.0f)
            verticalLineToRelative(-2.0f)
            horizontalLineToRelative(6.0f)
            close()
        }
    }.build()
}

// QR CODE ICONS
val QrCode: ImageVector by lazy {
    ImageVector.Builder(
        name = "QrCode",
        defaultWidth = 24.0.dp,
        defaultHeight = 24.0.dp,
        viewportWidth = 24.0f,
        viewportHeight = 24.0f
    ).apply {
        path(
            fill = SolidColor(Color(0xFF000000)),
            stroke = null,
            strokeLineWidth = 0.0f,
            strokeLineCap = Butt,
            strokeLineJoin = Miter,
            strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            // QR code squares
            moveTo(3.0f, 3.0f)
            horizontalLineToRelative(8.0f)
            verticalLineToRelative(8.0f)
            horizontalLineTo(3.0f)
            verticalLineTo(3.0f)
            close()
            moveTo(5.0f, 5.0f)
            verticalLineToRelative(4.0f)
            horizontalLineToRelative(4.0f)
            verticalLineTo(5.0f)
            horizontalLineTo(5.0f)
            close()

            moveTo(13.0f, 3.0f)
            horizontalLineToRelative(8.0f)
            verticalLineToRelative(8.0f)
            horizontalLineToRelative(-8.0f)
            verticalLineTo(3.0f)
            close()
            moveTo(15.0f, 5.0f)
            verticalLineToRelative(4.0f)
            horizontalLineToRelative(4.0f)
            verticalLineTo(5.0f)
            horizontalLineToRelative(-4.0f)
            close()

            moveTo(3.0f, 13.0f)
            horizontalLineToRelative(8.0f)
            verticalLineToRelative(8.0f)
            horizontalLineTo(3.0f)
            verticalLineToRelative(-8.0f)
            close()
            moveTo(5.0f, 15.0f)
            verticalLineToRelative(4.0f)
            horizontalLineToRelative(4.0f)
            verticalLineToRelative(-4.0f)
            horizontalLineTo(5.0f)
            close()

            // Small squares
            moveTo(13.0f, 13.0f)
            horizontalLineToRelative(2.0f)
            verticalLineToRelative(2.0f)
            horizontalLineToRelative(-2.0f)
            close()
            moveTo(17.0f, 13.0f)
            horizontalLineToRelative(4.0f)
            verticalLineToRelative(2.0f)
            horizontalLineToRelative(-4.0f)
            close()
            moveTo(15.0f, 17.0f)
            horizontalLineToRelative(2.0f)
            verticalLineToRelative(4.0f)
            horizontalLineToRelative(-2.0f)
            close()
            moveTo(19.0f, 17.0f)
            horizontalLineToRelative(2.0f)
            verticalLineToRelative(4.0f)
            horizontalLineToRelative(-2.0f)
            close()
        }
    }.build()
}

val QrScan: ImageVector by lazy {
    ImageVector.Builder(
        name = "QrScan",
        defaultWidth = 24.0.dp,
        defaultHeight = 24.0.dp,
        viewportWidth = 24.0f,
        viewportHeight = 24.0f
    ).apply {
        path(
            fill = SolidColor(Color(0xFF000000)),
            stroke = null,
            strokeLineWidth = 0.0f,
            strokeLineCap = Butt,
            strokeLineJoin = Miter,
            strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            // Scan frame corners
            moveTo(2.0f, 2.0f)
            horizontalLineToRelative(5.0f)
            verticalLineToRelative(2.0f)
            horizontalLineTo(4.0f)
            verticalLineToRelative(3.0f)
            horizontalLineTo(2.0f)
            verticalLineTo(2.0f)
            close()

            moveTo(17.0f, 2.0f)
            horizontalLineToRelative(5.0f)
            verticalLineToRelative(5.0f)
            horizontalLineToRelative(-2.0f)
            verticalLineTo(4.0f)
            horizontalLineToRelative(-3.0f)
            verticalLineTo(2.0f)
            close()

            moveTo(2.0f, 17.0f)
            verticalLineToRelative(5.0f)
            horizontalLineToRelative(5.0f)
            verticalLineToRelative(-2.0f)
            horizontalLineTo(4.0f)
            verticalLineToRelative(-3.0f)
            horizontalLineTo(2.0f)
            close()

            moveTo(20.0f, 17.0f)
            verticalLineToRelative(3.0f)
            horizontalLineToRelative(-3.0f)
            verticalLineToRelative(2.0f)
            horizontalLineToRelative(5.0f)
            verticalLineToRelative(-5.0f)
            horizontalLineTo(20.0f)
            close()

            // Scan line
            moveTo(6.0f, 11.0f)
            horizontalLineToRelative(12.0f)
            verticalLineToRelative(2.0f)
            horizontalLineTo(6.0f)
            verticalLineTo(11.0f)
            close()
        }
    }.build()
}

// LOYALTY SPECIFIC ICONS
val Coupon: ImageVector by lazy {
    ImageVector.Builder(
        name = "Coupon",
        defaultWidth = 24.0.dp,
        defaultHeight = 24.0.dp,
        viewportWidth = 24.0f,
        viewportHeight = 24.0f
    ).apply {
        path(
            fill = SolidColor(Color(0xFF000000)),
            stroke = null,
            strokeLineWidth = 0.0f,
            strokeLineCap = Butt,
            strokeLineJoin = Miter,
            strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(20.0f, 4.0f)
            horizontalLineTo(4.0f)
            curveTo(2.9f, 4.0f, 2.0f, 4.9f, 2.0f, 6.0f)
            verticalLineToRelative(3.0f)
            curveToRelative(1.1f, 0.0f, 2.0f, 0.9f, 2.0f, 2.0f)
            reflectiveCurveToRelative(-0.9f, 2.0f, -2.0f, 2.0f)
            verticalLineToRelative(3.0f)
            curveToRelative(0.0f, 1.1f, 0.9f, 2.0f, 2.0f, 2.0f)
            horizontalLineToRelative(16.0f)
            curveToRelative(1.1f, 0.0f, 2.0f, -0.9f, 2.0f, -2.0f)
            verticalLineToRelative(-3.0f)
            curveToRelative(-1.1f, 0.0f, -2.0f, -0.9f, -2.0f, -2.0f)
            reflectiveCurveToRelative(0.9f, -2.0f, 2.0f, -2.0f)
            verticalLineTo(6.0f)
            curveTo(22.0f, 4.9f, 21.1f, 4.0f, 20.0f, 4.0f)
            close()
            moveTo(11.0f, 15.0f)
            horizontalLineTo(6.0f)
            verticalLineToRelative(-2.0f)
            horizontalLineToRelative(5.0f)
            verticalLineTo(15.0f)
            close()
            moveTo(18.0f, 11.0f)
            horizontalLineTo(6.0f)
            verticalLineTo(9.0f)
            horizontalLineToRelative(12.0f)
            verticalLineTo(11.0f)
            close()
        }
    }.build()
}

val Points: ImageVector by lazy {
    ImageVector.Builder(
        name = "Points",
        defaultWidth = 24.0.dp,
        defaultHeight = 24.0.dp,
        viewportWidth = 24.0f,
        viewportHeight = 24.0f
    ).apply {
        path(
            fill = SolidColor(Color(0xFF000000)),
            stroke = null,
            strokeLineWidth = 0.0f,
            strokeLineCap = Butt,
            strokeLineJoin = Miter,
            strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            // Star shape for points
            moveTo(12.0f, 2.0f)
            lineToRelative(3.09f, 6.26f)
            lineTo(22.0f, 9.27f)
            lineToRelative(-5.0f, 4.87f)
            lineTo(18.18f, 21.0f)
            lineTo(12.0f, 17.77f)
            lineTo(5.82f, 21.0f)
            lineTo(7.0f, 14.14f)
            lineTo(2.0f, 9.27f)
            lineToRelative(6.91f, -1.01f)
            close()
        }
    }.build()
}

val Gift: ImageVector by lazy {
    ImageVector.Builder(
        name = "Gift",
        defaultWidth = 24.0.dp,
        defaultHeight = 24.0.dp,
        viewportWidth = 24.0f,
        viewportHeight = 24.0f
    ).apply {
        path(
            fill = SolidColor(Color(0xFF000000)),
            stroke = null,
            strokeLineWidth = 0.0f,
            strokeLineCap = Butt,
            strokeLineJoin = Miter,
            strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(20.0f, 6.0f)
            horizontalLineToRelative(-2.0f)
            curveToRelative(0.0f, -2.2f, -1.8f, -4.0f, -4.0f, -4.0f)
            reflectiveCurveToRelative(-4.0f, 1.8f, -4.0f, 4.0f)
            horizontalLineTo(4.0f)
            verticalLineToRelative(5.0f)
            horizontalLineToRelative(16.0f)
            verticalLineTo(6.0f)
            close()
            moveTo(12.0f, 4.0f)
            curveToRelative(1.1f, 0.0f, 2.0f, 0.9f, 2.0f, 2.0f)
            horizontalLineToRelative(-4.0f)
            curveTo(10.0f, 4.9f, 10.9f, 4.0f, 12.0f, 4.0f)
            close()
            moveTo(4.0f, 13.0f)
            verticalLineToRelative(7.0f)
            horizontalLineToRelative(7.0f)
            verticalLineToRelative(-7.0f)
            horizontalLineTo(4.0f)
            close()
            moveTo(13.0f, 13.0f)
            verticalLineToRelative(7.0f)
            horizontalLineToRelative(7.0f)
            verticalLineToRelative(-7.0f)
            horizontalLineTo(13.0f)
            close()
        }
    }.build()
}

// BUSINESS ICONS
val Store: ImageVector by lazy {
    ImageVector.Builder(
        name = "Store",
        defaultWidth = 24.0.dp,
        defaultHeight = 24.0.dp,
        viewportWidth = 24.0f,
        viewportHeight = 24.0f
    ).apply {
        path(
            fill = SolidColor(Color(0xFF000000)),
            stroke = null,
            strokeLineWidth = 0.0f,
            strokeLineCap = Butt,
            strokeLineJoin = Miter,
            strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(20.0f, 4.0f)
            horizontalLineTo(4.0f)
            verticalLineToRelative(2.0f)
            horizontalLineToRelative(16.0f)
            verticalLineTo(4.0f)
            close()
            moveTo(21.0f, 14.0f)
            verticalLineToRelative(-2.0f)
            lineToRelative(-1.0f, -5.0f)
            horizontalLineTo(4.0f)
            lineToRelative(-1.0f, 5.0f)
            verticalLineToRelative(2.0f)
            horizontalLineToRelative(1.0f)
            verticalLineToRelative(6.0f)
            horizontalLineToRelative(10.0f)
            verticalLineToRelative(-6.0f)
            horizontalLineToRelative(4.0f)
            verticalLineToRelative(6.0f)
            horizontalLineToRelative(2.0f)
            verticalLineToRelative(-6.0f)
            horizontalLineTo(21.0f)
            close()
            moveTo(12.0f, 18.0f)
            horizontalLineTo(6.0f)
            verticalLineToRelative(-4.0f)
            horizontalLineToRelative(6.0f)
            verticalLineTo(18.0f)
            close()
        }
    }.build()
}

val Receipt: ImageVector by lazy {
    ImageVector.Builder(
        name = "Receipt",
        defaultWidth = 24.0.dp,
        defaultHeight = 24.0.dp,
        viewportWidth = 24.0f,
        viewportHeight = 24.0f
    ).apply {
        path(
            fill = SolidColor(Color(0xFF000000)),
            stroke = null,
            strokeLineWidth = 0.0f,
            strokeLineCap = Butt,
            strokeLineJoin = Miter,
            strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(18.0f, 17.0f)
            horizontalLineTo(6.0f)
            verticalLineToRelative(-2.0f)
            horizontalLineToRelative(12.0f)
            verticalLineTo(17.0f)
            close()
            moveTo(18.0f, 13.0f)
            horizontalLineTo(6.0f)
            verticalLineToRelative(-2.0f)
            horizontalLineToRelative(12.0f)
            verticalLineTo(13.0f)
            close()
            moveTo(18.0f, 9.0f)
            horizontalLineTo(6.0f)
            verticalLineTo(7.0f)
            horizontalLineToRelative(12.0f)
            verticalLineTo(9.0f)
            close()
            moveTo(3.0f, 22.0f)
            lineToRelative(1.5f, -1.5f)
            lineTo(6.0f, 22.0f)
            lineToRelative(1.5f, -1.5f)
            lineTo(9.0f, 22.0f)
            lineToRelative(1.5f, -1.5f)
            lineTo(12.0f, 22.0f)
            lineToRelative(1.5f, -1.5f)
            lineTo(15.0f, 22.0f)
            lineToRelative(1.5f, -1.5f)
            lineTo(18.0f, 22.0f)
            lineToRelative(1.5f, -1.5f)
            lineTo(21.0f, 22.0f)
            verticalLineTo(2.0f)
            lineToRelative(-1.5f, 1.5f)
            lineTo(18.0f, 2.0f)
            lineToRelative(-1.5f, 1.5f)
            lineTo(15.0f, 2.0f)
            lineToRelative(-1.5f, 1.5f)
            lineTo(12.0f, 2.0f)
            lineToRelative(-1.5f, 1.5f)
            lineTo(9.0f, 2.0f)
            lineToRelative(-1.5f, 1.5f)
            lineTo(6.0f, 2.0f)
            lineToRelative(-1.5f, 1.5f)
            lineTo(3.0f, 2.0f)
            verticalLineTo(22.0f)
            close()
        }
    }.build()
}

// COMMUNICATION ICONS
val Email: ImageVector by lazy {
    ImageVector.Builder(
        name = "Email",
        defaultWidth = 24.0.dp,
        defaultHeight = 24.0.dp,
        viewportWidth = 24.0f,
        viewportHeight = 24.0f
    ).apply {
        path(
            fill = SolidColor(Color(0xFF000000)),
            stroke = null,
            strokeLineWidth = 0.0f,
            strokeLineCap = Butt,
            strokeLineJoin = Miter,
            strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(20.0f, 4.0f)
            horizontalLineTo(4.0f)
            curveToRelative(-1.1f, 0.0f, -1.99f, 0.9f, -1.99f, 2.0f)
            lineTo(2.0f, 18.0f)
            curveToRelative(0.0f, 1.1f, 0.9f, 2.0f, 2.0f, 2.0f)
            horizontalLineToRelative(16.0f)
            curveToRelative(1.1f, 0.0f, 2.0f, -0.9f, 2.0f, -2.0f)
            verticalLineTo(6.0f)
            curveToRelative(0.0f, -1.1f, -0.9f, -2.0f, -2.0f, -2.0f)
            close()
            moveTo(20.0f, 8.0f)
            lineToRelative(-8.0f, 5.0f)
            lineToRelative(-8.0f, -5.0f)
            verticalLineTo(6.0f)
            lineToRelative(8.0f, 5.0f)
            lineToRelative(8.0f, -5.0f)
            verticalLineToRelative(2.0f)
            close()
        }
    }.build()
}

val Phone: ImageVector by lazy {
    ImageVector.Builder(
        name = "Phone",
        defaultWidth = 24.0.dp,
        defaultHeight = 24.0.dp,
        viewportWidth = 24.0f,
        viewportHeight = 24.0f
    ).apply {
        path(
            fill = SolidColor(Color(0xFF000000)),
            stroke = null,
            strokeLineWidth = 0.0f,
            strokeLineCap = Butt,
            strokeLineJoin = Miter,
            strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(6.62f, 10.79f)
            curveToRelative(1.44f, 2.83f, 3.76f, 5.14f, 6.59f, 6.59f)
            lineToRelative(2.2f, -2.2f)
            curveToRelative(0.27f, -0.27f, 0.67f, -0.36f, 1.02f, -0.24f)
            curveToRelative(1.12f, 0.37f, 2.33f, 0.57f, 3.57f, 0.57f)
            curveToRelative(0.55f, 0.0f, 1.0f, 0.45f, 1.0f, 1.0f)
            verticalLineTo(20.0f)
            curveToRelative(0.0f, 0.55f, -0.45f, 1.0f, -1.0f, 1.0f)
            curveToRelative(-9.39f, 0.0f, -17.0f, -7.61f, -17.0f, -17.0f)
            curveToRelative(0.0f, -0.55f, 0.45f, -1.0f, 1.0f, -1.0f)
            horizontalLineToRelative(3.5f)
            curveToRelative(0.55f, 0.0f, 1.0f, 0.45f, 1.0f, 1.0f)
            curveToRelative(0.0f, 1.25f, 0.2f, 2.45f, 0.57f, 3.57f)
            curveToRelative(0.11f, 0.35f, 0.03f, 0.74f, -0.25f, 1.02f)
            lineToRelative(-2.2f, 2.2f)
            close()
        }
    }.build()
}

val Notifications: ImageVector by lazy {
    ImageVector.Builder(
        name = "Notifications",
        defaultWidth = 24.0.dp,
        defaultHeight = 24.0.dp,
        viewportWidth = 24.0f,
        viewportHeight = 24.0f
    ).apply {
        path(
            fill = SolidColor(Color(0xFF000000)),
            stroke = null,
            strokeLineWidth = 0.0f,
            strokeLineCap = Butt,
            strokeLineJoin = Miter,
            strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(12.0f, 22.0f)
            curveToRelative(1.1f, 0.0f, 2.0f, -0.9f, 2.0f, -2.0f)
            horizontalLineToRelative(-4.0f)
            curveToRelative(0.0f, 1.1f, 0.9f, 2.0f, 2.0f, 2.0f)
            close()
            moveTo(18.0f, 16.0f)
            verticalLineToRelative(-5.0f)
            curveToRelative(0.0f, -3.07f, -1.64f, -5.64f, -4.5f, -6.32f)
            verticalLineTo(4.0f)
            curveToRelative(0.0f, -0.83f, -0.67f, -1.5f, -1.5f, -1.5f)
            reflectiveCurveToRelative(-1.5f, 0.67f, -1.5f, 1.5f)
            verticalLineToRelative(0.68f)
            curveTo(7.63f, 5.36f, 6.0f, 7.92f, 6.0f, 11.0f)
            verticalLineToRelative(5.0f)
            lineToRelative(-2.0f, 2.0f)
            verticalLineToRelative(1.0f)
            horizontalLineToRelative(16.0f)
            verticalLineToRelative(-1.0f)
            lineToRelative(-2.0f, -2.0f)
            close()
        }
    }.build()
}

// SECURITY ICONS
val Lock: ImageVector by lazy {
    ImageVector.Builder(
        name = "Lock",
        defaultWidth = 24.0.dp,
        defaultHeight = 24.0.dp,
        viewportWidth = 24.0f,
        viewportHeight = 24.0f
    ).apply {
        path(
            fill = SolidColor(Color(0xFF000000)),
            stroke = null,
            strokeLineWidth = 0.0f,
            strokeLineCap = Butt,
            strokeLineJoin = Miter,
            strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(18.0f, 8.0f)
            horizontalLineToRelative(-1.0f)
            verticalLineTo(6.0f)
            curveToRelative(0.0f, -2.76f, -2.24f, -5.0f, -5.0f, -5.0f)
            reflectiveCurveToRelative(-5.0f, 2.24f, -5.0f, 5.0f)
            verticalLineToRelative(2.0f)
            horizontalLineTo(6.0f)
            curveToRelative(-1.1f, 0.0f, -2.0f, 0.9f, -2.0f, 2.0f)
            verticalLineToRelative(10.0f)
            curveToRelative(0.0f, 1.1f, 0.9f, 2.0f, 2.0f, 2.0f)
            horizontalLineToRelative(12.0f)
            curveToRelative(1.1f, 0.0f, 2.0f, -0.9f, 2.0f, -2.0f)
            verticalLineTo(10.0f)
            curveToRelative(0.0f, -1.1f, -0.9f, -2.0f, -2.0f, -2.0f)
            close()
            moveTo(15.0f, 8.0f)
            horizontalLineTo(9.0f)
            verticalLineTo(6.0f)
            curveToRelative(0.0f, -1.66f, 1.34f, -3.0f, 3.0f, -3.0f)
            reflectiveCurveToRelative(3.0f, 1.34f, 3.0f, 3.0f)
            verticalLineToRelative(2.0f)
            close()
        }
    }.build()
}

val Visibility: ImageVector by lazy {
    ImageVector.Builder(
        name = "Visibility",
        defaultWidth = 24.0.dp,
        defaultHeight = 24.0.dp,
        viewportWidth = 24.0f,
        viewportHeight = 24.0f
    ).apply {
        path(
            fill = SolidColor(Color(0xFF000000)),
            stroke = null,
            strokeLineWidth = 0.0f,
            strokeLineCap = Butt,
            strokeLineJoin = Miter,
            strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(12.0f, 4.5f)
            curveTo(7.0f, 4.5f, 2.73f, 7.61f, 1.0f, 12.0f)
            curveToRelative(1.73f, 4.39f, 6.0f, 7.5f, 11.0f, 7.5f)
            reflectiveCurveToRelative(9.27f, -3.11f, 11.0f, -7.5f)
            curveTo(21.27f, 7.61f, 17.0f, 4.5f, 12.0f, 4.5f)
            close()
            moveTo(12.0f, 17.0f)
            curveToRelative(-2.76f, 0.0f, -5.0f, -2.24f, -5.0f, -5.0f)
            reflectiveCurveToRelative(2.24f, -5.0f, 5.0f, -5.0f)
            reflectiveCurveToRelative(5.0f, 2.24f, 5.0f, 5.0f)
            reflectiveCurveToRelative(-2.24f, 5.0f, -5.0f, 5.0f)
            close()
            moveTo(12.0f, 9.0f)
            curveToRelative(-1.66f, 0.0f, -3.0f, 1.34f, -3.0f, 3.0f)
            reflectiveCurveToRelative(1.34f, 3.0f, 3.0f, 3.0f)
            reflectiveCurveToRelative(3.0f, -1.34f, 3.0f, -3.0f)
            reflectiveCurveToRelative(-1.34f, -3.0f, -3.0f, -3.0f)
            close()
        }
    }.build()
}

val VisibilityOff: ImageVector by lazy {
    ImageVector.Builder(
        name = "VisibilityOff",
        defaultWidth = 24.0.dp,
        defaultHeight = 24.0.dp,
        viewportWidth = 24.0f,
        viewportHeight = 24.0f
    ).apply {
        path(
            fill = SolidColor(Color(0xFF000000)),
            stroke = null,
            strokeLineWidth = 0.0f,
            strokeLineCap = Butt,
            strokeLineJoin = Miter,
            strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(12.0f, 7.0f)
            curveToRelative(2.76f, 0.0f, 5.0f, 2.24f, 5.0f, 5.0f)
            curveToRelative(0.0f, 0.65f, -0.13f, 1.26f, -0.36f, 1.83f)
            lineToRelative(2.92f, 2.92f)
            curveToRelative(1.51f, -1.26f, 2.7f, -2.89f, 3.43f, -4.75f)
            curveToRelative(-1.73f, -4.39f, -6.0f, -7.5f, -11.0f, -7.5f)
            curveToRelative(-1.4f, 0.0f, -2.74f, 0.25f, -3.98f, 0.7f)
            lineToRelative(2.16f, 2.16f)
            curveTo(10.74f, 7.13f, 11.35f, 7.0f, 12.0f, 7.0f)
            close()
            moveTo(2.0f, 4.27f)
            lineToRelative(2.28f, 2.28f)
            lineToRelative(0.46f, 0.46f)
            curveTo(3.08f, 8.3f, 1.78f, 10.02f, 1.0f, 12.0f)
            curveToRelative(1.73f, 4.39f, 6.0f, 7.5f, 11.0f, 7.5f)
            curveToRelative(1.55f, 0.0f, 3.03f, -0.3f, 4.38f, -0.84f)
            lineToRelative(0.42f, 0.42f)
            lineTo(19.73f, 22.0f)
            lineTo(21.0f, 20.73f)
            lineTo(3.27f, 3.0f)
            lineTo(2.0f, 4.27f)
            close()
            moveTo(7.53f, 9.8f)
            lineToRelative(1.55f, 1.55f)
            curveToRelative(-0.05f, 0.21f, -0.08f, 0.43f, -0.08f, 0.65f)
            curveToRelative(0.0f, 1.66f, 1.34f, 3.0f, 3.0f, 3.0f)
            curveToRelative(0.22f, 0.0f, 0.44f, -0.03f, 0.65f, -0.08f)
            lineToRelative(1.55f, 1.55f)
            curveToRelative(-0.67f, 0.33f, -1.41f, 0.53f, -2.2f, 0.53f)
            curveToRelative(-2.76f, 0.0f, -5.0f, -2.24f, -5.0f, -5.0f)
            curveToRelative(0.0f, -0.79f, 0.2f, -1.53f, 0.53f, -2.2f)
            close()
            moveTo(11.84f, 9.02f)
            lineToRelative(3.15f, 3.15f)
            lineToRelative(0.02f, -0.16f)
            curveToRelative(0.0f, -1.66f, -1.34f, -3.0f, -3.0f, -3.0f)
            lineToRelative(-0.17f, 0.01f)
            close()
        }
    }.build()
}

// INTERFACE ICONS
val Info: ImageVector by lazy {
    ImageVector.Builder(
        name = "Info",
        defaultWidth = 24.0.dp,
        defaultHeight = 24.0.dp,
        viewportWidth = 24.0f,
        viewportHeight = 24.0f
    ).apply {
        path(
            fill = SolidColor(Color(0xFF000000)),
            stroke = null,
            strokeLineWidth = 0.0f,
            strokeLineCap = Butt,
            strokeLineJoin = Miter,
            strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(12.0f, 2.0f)
            curveTo(6.48f, 2.0f, 2.0f, 6.48f, 2.0f, 12.0f)
            reflectiveCurveToRelative(4.48f, 10.0f, 10.0f, 10.0f)
            reflectiveCurveToRelative(10.0f, -4.48f, 10.0f, -10.0f)
            reflectiveCurveTo(17.52f, 2.0f, 12.0f, 2.0f)
            close()
            moveTo(13.0f, 17.0f)
            horizontalLineToRelative(-2.0f)
            verticalLineToRelative(-6.0f)
            horizontalLineToRelative(2.0f)
            verticalLineToRelative(6.0f)
            close()
            moveTo(13.0f, 9.0f)
            horizontalLineToRelative(-2.0f)
            verticalLineTo(7.0f)
            horizontalLineToRelative(2.0f)
            verticalLineToRelative(2.0f)
            close()
        }
    }.build()
}

val Search: ImageVector by lazy {
    ImageVector.Builder(
        name = "Search",
        defaultWidth = 24.0.dp,
        defaultHeight = 24.0.dp,
        viewportWidth = 24.0f,
        viewportHeight = 24.0f
    ).apply {
        path(
            fill = SolidColor(Color(0xFF000000)),
            stroke = null,
            strokeLineWidth = 0.0f,
            strokeLineCap = Butt,
            strokeLineJoin = Miter,
            strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(15.5f, 14.0f)
            horizontalLineToRelative(-0.79f)
            lineToRelative(-0.28f, -0.27f)
            curveTo(15.41f, 12.59f, 16.0f, 11.11f, 16.0f, 9.5f)
            curveTo(16.0f, 5.91f, 13.09f, 3.0f, 9.5f, 3.0f)
            reflectiveCurveTo(3.0f, 5.91f, 3.0f, 9.5f)
            reflectiveCurveTo(5.91f, 16.0f, 9.5f, 16.0f)
            curveToRelative(1.61f, 0.0f, 3.09f, -0.59f, 4.23f, -1.57f)
            lineToRelative(0.27f, 0.28f)
            verticalLineToRelative(0.79f)
            lineToRelative(5.0f, 4.99f)
            lineTo(20.49f, 19.0f)
            lineToRelative(-4.99f, -5.0f)
            close()
            moveTo(9.5f, 14.0f)
            curveTo(7.01f, 14.0f, 5.0f, 11.99f, 5.0f, 9.5f)
            reflectiveCurveTo(7.01f, 5.0f, 9.5f, 5.0f)
            reflectiveCurveTo(14.0f, 7.01f, 14.0f, 9.5f)
            reflectiveCurveTo(11.99f, 14.0f, 9.5f, 14.0f)
            close()
        }
    }.build()
}

val Filter: ImageVector by lazy {
    ImageVector.Builder(
        name = "Filter",
        defaultWidth = 24.0.dp,
        defaultHeight = 24.0.dp,
        viewportWidth = 24.0f,
        viewportHeight = 24.0f
    ).apply {
        path(
            fill = SolidColor(Color(0xFF000000)),
            stroke = null,
            strokeLineWidth = 0.0f,
            strokeLineCap = Butt,
            strokeLineJoin = Miter,
            strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(10.0f, 18.0f)
            horizontalLineToRelative(4.0f)
            verticalLineToRelative(-2.0f)
            horizontalLineToRelative(-4.0f)
            verticalLineToRelative(2.0f)
            close()
            moveTo(3.0f, 6.0f)
            verticalLineToRelative(2.0f)
            horizontalLineToRelative(18.0f)
            verticalLineTo(6.0f)
            horizontalLineTo(3.0f)
            close()
            moveTo(6.0f, 13.0f)
            horizontalLineToRelative(12.0f)
            verticalLineToRelative(-2.0f)
            horizontalLineTo(6.0f)
            verticalLineToRelative(2.0f)
            close()
        }
    }.build()
}

val Calendar: ImageVector by lazy {
    ImageVector.Builder(
        name = "Calendar",
        defaultWidth = 24.0.dp,
        defaultHeight = 24.0.dp,
        viewportWidth = 24.0f,
        viewportHeight = 24.0f
    ).apply {
        path(
            fill = SolidColor(Color(0xFF000000)),
            stroke = null,
            strokeLineWidth = 0.0f,
            strokeLineCap = Butt,
            strokeLineJoin = Miter,
            strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(19.0f, 3.0f)
            horizontalLineToRelative(-1.0f)
            verticalLineTo(1.0f)
            horizontalLineToRelative(-2.0f)
            verticalLineToRelative(2.0f)
            horizontalLineTo(8.0f)
            verticalLineTo(1.0f)
            horizontalLineTo(6.0f)
            verticalLineToRelative(2.0f)
            horizontalLineTo(5.0f)
            curveToRelative(-1.11f, 0.0f, -1.99f, 0.9f, -1.99f, 2.0f)
            lineTo(3.0f, 19.0f)
            curveToRelative(0.0f, 1.1f, 0.89f, 2.0f, 2.0f, 2.0f)
            horizontalLineToRelative(14.0f)
            curveToRelative(1.1f, 0.0f, 2.0f, -0.9f, 2.0f, -2.0f)
            verticalLineTo(5.0f)
            curveToRelative(0.0f, -1.1f, -0.9f, -2.0f, -2.0f, -2.0f)
            close()
            moveTo(19.0f, 19.0f)
            horizontalLineTo(5.0f)
            verticalLineTo(8.0f)
            horizontalLineToRelative(14.0f)
            verticalLineToRelative(11.0f)
            close()
            moveTo(7.0f, 10.0f)
            horizontalLineToRelative(5.0f)
            verticalLineToRelative(5.0f)
            horizontalLineTo(7.0f)
            close()
        }
    }.build()
}

val Share: ImageVector by lazy {
    ImageVector.Builder(
        name = "Share",
        defaultWidth = 24.0.dp,
        defaultHeight = 24.0.dp,
        viewportWidth = 24.0f,
        viewportHeight = 24.0f
    ).apply {
        path(
            fill = SolidColor(Color(0xFF000000)),
            stroke = null,
            strokeLineWidth = 0.0f,
            strokeLineCap = Butt,
            strokeLineJoin = Miter,
            strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(18.0f, 16.08f)
            curveToRelative(-0.76f, 0.0f, -1.44f, 0.3f, -1.96f, 0.77f)
            lineTo(8.91f, 12.7f)
            curveToRelative(0.05f, -0.23f, 0.09f, -0.46f, 0.09f, -0.7f)
            reflectiveCurveToRelative(-0.04f, -0.47f, -0.09f, -0.7f)
            lineToRelative(7.05f, -4.11f)
            curveToRelative(0.54f, 0.5f, 1.25f, 0.81f, 2.04f, 0.81f)
            curveToRelative(1.66f, 0.0f, 3.0f, -1.34f, 3.0f, -3.0f)
            reflectiveCurveToRelative(-1.34f, -3.0f, -3.0f, -3.0f)
            reflectiveCurveToRelative(-3.0f, 1.34f, -3.0f, 3.0f)
            curveToRelative(0.0f, 0.24f, 0.04f, 0.47f, 0.09f, 0.7f)
            lineTo(8.04f, 9.81f)
            curveTo(7.5f, 9.31f, 6.79f, 9.0f, 6.0f, 9.0f)
            curveToRelative(-1.66f, 0.0f, -3.0f, 1.34f, -3.0f, 3.0f)
            reflectiveCurveToRelative(1.34f, 3.0f, 3.0f, 3.0f)
            curveToRelative(0.79f, 0.0f, 1.5f, -0.31f, 2.04f, -0.81f)
            lineToRelative(7.12f, 4.16f)
            curveToRelative(-0.05f, 0.21f, -0.08f, 0.43f, -0.08f, 0.65f)
            curveToRelative(0.0f, 1.61f, 1.31f, 2.92f, 2.92f, 2.92f)
            reflectiveCurveToRelative(2.92f, -1.31f, 2.92f, -2.92f)
            reflectiveCurveToRelative(-1.31f, -2.92f, -2.92f, -2.92f)
            close()
        }
    }.build()
}

val Download: ImageVector by lazy {
    ImageVector.Builder(
        name = "Download",
        defaultWidth = 24.0.dp,
        defaultHeight = 24.0.dp,
        viewportWidth = 24.0f,
        viewportHeight = 24.0f
    ).apply {
        path(
            fill = SolidColor(Color(0xFF000000)),
            stroke = null,
            strokeLineWidth = 0.0f,
            strokeLineCap = Butt,
            strokeLineJoin = Miter,
            strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(19.0f, 9.0f)
            horizontalLineToRelative(-4.0f)
            verticalLineTo(3.0f)
            horizontalLineTo(9.0f)
            verticalLineToRelative(6.0f)
            horizontalLineTo(5.0f)
            lineToRelative(7.0f, 7.0f)
            lineToRelative(7.0f, -7.0f)
            close()
            moveTo(5.0f, 18.0f)
            verticalLineToRelative(2.0f)
            horizontalLineToRelative(14.0f)
            verticalLineToRelative(-2.0f)
            horizontalLineTo(5.0f)
            close()
        }
    }.build()
}

val Edit: ImageVector by lazy {
    ImageVector.Builder(
        name = "Edit",
        defaultWidth = 24.0.dp,
        defaultHeight = 24.0.dp,
        viewportWidth = 24.0f,
        viewportHeight = 24.0f
    ).apply {
        path(
            fill = SolidColor(Color(0xFF000000)),
            stroke = null,
            strokeLineWidth = 0.0f,
            strokeLineCap = Butt,
            strokeLineJoin = Miter,
            strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(20.0f, 11.0f)
            horizontalLineTo(7.83f)
            lineToRelative(5.59f, -5.59f)
            lineTo(12.0f, 4.0f)
            lineToRelative(-8.0f, 8.0f)
            lineToRelative(8.0f, 8.0f)
            lineToRelative(1.41f, -1.41f)
            lineTo(7.83f, 13.0f)
            horizontalLineTo(20.0f)
            verticalLineToRelative(-2.0f)
            close()
        }
    }.build()
}
// PASSWORD ICONS - Add these to your AppIcons object

    val Password: ImageVector by lazy {
        ImageVector.Builder(
            name = "Password",
            defaultWidth = 24.0.dp,
            defaultHeight = 24.0.dp,
            viewportWidth = 24.0f,
            viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)),
                stroke = null,
                strokeLineWidth = 0.0f,
                strokeLineCap = Butt,
                strokeLineJoin = Miter,
                strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(2.0f, 17.0f)
                horizontalLineToRelative(20.0f)
                verticalLineToRelative(2.0f)
                horizontalLineTo(2.0f)
                verticalLineToRelative(-2.0f)
                close()
                moveTo(3.15f, 12.95f)
                lineTo(4.0f, 11.47f)
                lineToRelative(0.85f, 1.48f)
                lineToRelative(-0.3f, 0.52f)
                horizontalLineToRelative(-0.1f)
                curveToRelative(-0.25f, 0.0f, -0.5f, -0.13f, -0.63f, -0.37f)
                curveTo(3.69f, 12.85f, 3.69f, 12.6f, 3.82f, 12.35f)
                lineToRelative(0.3f, -0.52f)
                close()
                moveTo(6.53f, 12.95f)
                lineToRelative(0.3f, -0.52f)
                lineToRelative(0.85f, 1.48f)
                lineToRelative(-0.3f, 0.52f)
                curveToRelative(-0.13f, 0.24f, -0.38f, 0.37f, -0.63f, 0.37f)
                horizontalLineToRelative(-0.1f)
                lineToRelative(-0.85f, -1.48f)
                close()
                moveTo(9.91f, 12.95f)
                lineToRelative(0.3f, -0.52f)
                lineToRelative(0.85f, 1.48f)
                lineToRelative(-0.3f, 0.52f)
                curveToRelative(-0.13f, 0.24f, -0.38f, 0.37f, -0.63f, 0.37f)
                horizontalLineToRelative(-0.1f)
                lineToRelative(-0.85f, -1.48f)
                close()
                moveTo(13.29f, 12.95f)
                lineToRelative(0.3f, -0.52f)
                lineToRelative(0.85f, 1.48f)
                lineToRelative(-0.3f, 0.52f)
                curveToRelative(-0.13f, 0.24f, -0.38f, 0.37f, -0.63f, 0.37f)
                horizontalLineToRelative(-0.1f)
                lineToRelative(-0.85f, -1.48f)
                close()
                moveTo(16.67f, 12.95f)
                lineToRelative(0.3f, -0.52f)
                lineToRelative(0.85f, 1.48f)
                lineToRelative(-0.3f, 0.52f)
                curveToRelative(-0.13f, 0.24f, -0.38f, 0.37f, -0.63f, 0.37f)
                horizontalLineToRelative(-0.1f)
                lineToRelative(-0.85f, -1.48f)
                close()
                moveTo(20.05f, 12.95f)
                lineToRelative(0.3f, -0.52f)
                lineToRelative(0.85f, 1.48f)
                lineToRelative(-0.3f, 0.52f)
                curveToRelative(-0.13f, 0.24f, -0.38f, 0.37f, -0.63f, 0.37f)
                horizontalLineToRelative(-0.1f)
                lineToRelative(-0.85f, -1.48f)
                close()
            }
        }.build()
    }

    val PasswordShow: ImageVector by lazy {
        ImageVector.Builder(
            name = "PasswordShow",
            defaultWidth = 24.0.dp,
            defaultHeight = 24.0.dp,
            viewportWidth = 24.0f,
            viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)),
                stroke = null,
                strokeLineWidth = 0.0f,
                strokeLineCap = Butt,
                strokeLineJoin = Miter,
                strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(12.0f, 4.5f)
                curveTo(7.0f, 4.5f, 2.73f, 7.61f, 1.0f, 12.0f)
                curveToRelative(1.73f, 4.39f, 6.0f, 7.5f, 11.0f, 7.5f)
                reflectiveCurveToRelative(9.27f, -3.11f, 11.0f, -7.5f)
                curveTo(21.27f, 7.61f, 17.0f, 4.5f, 12.0f, 4.5f)
                close()
                moveTo(12.0f, 17.0f)
                curveToRelative(-2.76f, 0.0f, -5.0f, -2.24f, -5.0f, -5.0f)
                reflectiveCurveToRelative(2.24f, -5.0f, 5.0f, -5.0f)
                reflectiveCurveToRelative(5.0f, 2.24f, 5.0f, 5.0f)
                reflectiveCurveToRelative(-2.24f, 5.0f, -5.0f, 5.0f)
                close()
                moveTo(12.0f, 9.0f)
                curveToRelative(-1.66f, 0.0f, -3.0f, 1.34f, -3.0f, 3.0f)
                reflectiveCurveToRelative(1.34f, 3.0f, 3.0f, 3.0f)
                reflectiveCurveToRelative(3.0f, -1.34f, 3.0f, -3.0f)
                reflectiveCurveToRelative(-1.34f, -3.0f, -3.0f, -3.0f)
                close()
            }
        }.build()
    }

    val PasswordHide: ImageVector by lazy {
        ImageVector.Builder(
            name = "PasswordHide",
            defaultWidth = 24.0.dp,
            defaultHeight = 24.0.dp,
            viewportWidth = 24.0f,
            viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)),
                stroke = null,
                strokeLineWidth = 0.0f,
                strokeLineCap = Butt,
                strokeLineJoin = Miter,
                strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(12.0f, 7.0f)
                curveToRelative(2.76f, 0.0f, 5.0f, 2.24f, 5.0f, 5.0f)
                curveToRelative(0.0f, 0.65f, -0.13f, 1.26f, -0.36f, 1.83f)
                lineToRelative(2.92f, 2.92f)
                curveToRelative(1.51f, -1.26f, 2.7f, -2.89f, 3.43f, -4.75f)
                curveToRelative(-1.73f, -4.39f, -6.0f, -7.5f, -11.0f, -7.5f)
                curveToRelative(-1.4f, 0.0f, -2.74f, 0.25f, -3.98f, 0.7f)
                lineToRelative(2.16f, 2.16f)
                curveTo(10.74f, 7.13f, 11.35f, 7.0f, 12.0f, 7.0f)
                close()
                moveTo(2.0f, 4.27f)
                lineToRelative(2.28f, 2.28f)
                lineToRelative(0.46f, 0.46f)
                curveTo(3.08f, 8.3f, 1.78f, 10.02f, 1.0f, 12.0f)
                curveToRelative(1.73f, 4.39f, 6.0f, 7.5f, 11.0f, 7.5f)
                curveToRelative(1.55f, 0.0f, 3.03f, -0.3f, 4.38f, -0.84f)
                lineToRelative(0.42f, 0.42f)
                lineTo(19.73f, 22.0f)
                lineTo(21.0f, 20.73f)
                lineTo(3.27f, 3.0f)
                lineTo(2.0f, 4.27f)
                close()
                moveTo(7.53f, 9.8f)
                lineToRelative(1.55f, 1.55f)
                curveToRelative(-0.05f, 0.21f, -0.08f, 0.43f, -0.08f, 0.65f)
                curveToRelative(0.0f, 1.66f, 1.34f, 3.0f, 3.0f, 3.0f)
                curveToRelative(0.22f, 0.0f, 0.44f, -0.03f, 0.65f, -0.08f)
                lineToRelative(1.55f, 1.55f)
                curveToRelative(-0.67f, 0.33f, -1.41f, 0.53f, -2.2f, 0.53f)
                curveToRelative(-2.76f, 0.0f, -5.0f, -2.24f, -5.0f, -5.0f)
                curveToRelative(0.0f, -0.79f, 0.2f, -1.53f, 0.53f, -2.2f)
                close()
                moveTo(11.84f, 9.02f)
                lineToRelative(3.15f, 3.15f)
                lineToRelative(0.02f, -0.16f)
                curveToRelative(0.0f, -1.66f, -1.34f, -3.0f, -3.0f, -3.0f)
                lineToRelative(-0.17f, 0.01f)
                close()
            }
        }.build()
    }
}
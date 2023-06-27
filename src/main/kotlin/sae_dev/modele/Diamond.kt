package sae_dev.modele
import javafx.scene.paint.Color
import javafx.scene.shape.Polygon

class Diamond(fill: Color, width: Double, height: Double, stroke: Color = Color.WHITE) : Polygon() {
    init {
        //Application des couleurs et bordure
        this.fill = fill
        this.stroke = stroke
        this.strokeWidth = 3.0

        // Définir les points du polygone pour représenter un diamant
        val halfWidth = width / 2
        val halfHeight = height / 2

        val diamondPoints = doubleArrayOf(
            0.0, halfHeight,
            halfWidth, 0.0,
            width, halfHeight,
            halfWidth, height
        )

        // Ajouter les points au polygone
        for (i in 0 until diamondPoints.size step 2) {
            val x = diamondPoints[i]
            val y = diamondPoints[i + 1]
            points.addAll(x, y)
        }
    }
}

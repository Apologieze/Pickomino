package sae_dev.modele

import javafx.scene.control.Button
import javafx.scene.image.Image
import javafx.scene.image.ImageView

class PickominoBouton(img: Image, val valeur: Int): Button("", ImageView(img)){
    var isPickable = false
        private set

    init {
        this.style = "-fx-padding: 0 0 0 0; -fx-background-color: #292929;"
    }

    fun pickable(value: Boolean){
        if (value) {
            isPickable = true
            this.isDisable = false
        }else{
            isPickable = true
            this.isDisable = true
        }
    }

}
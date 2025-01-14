package net.perfectdreams.spicymorenitta.views

import kotlinx.html.div
import kotlinx.html.dom.append
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JSON
import kotlinx.serialization.parse
import kotlinx.serialization.parseList
import net.perfectdreams.spicymorenitta.utils.SaveUtils
import net.perfectdreams.spicymorenitta.utils.loriUrl
import net.perfectdreams.spicymorenitta.utils.page
import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.HTMLInputElement
import kotlin.browser.document
import kotlin.browser.window
import kotlin.dom.addClass

@ImplicitReflectionSerializer
object ShipEffectsView {
    @JsName("start")
    fun start() {
        document.addEventListener("DOMContentLoaded", {
            val premiumAsJson = document.getElementById("ship-effects-json")?.innerHTML!!

            val shipEffects = JSON.nonstrict.parseList<ShipEffect>(premiumAsJson)
            val profileAsJson = document.getElementById("profile-json")?.innerHTML!!

            val profile = JSON.nonstrict.parse<ProfileListView.Profile>(profileAsJson)

            val buyButton = page.getElementById("buy-button") as HTMLButtonElement
            if (3000 > profile.money) {
                buyButton.addClass("button-discord-disabled")
            } else {
                buyButton.addClass("button-discord-success")
            }

            if (profile.money > 3000) {
                buyButton.onclick = {
                    prepareSave()
                }
            }

            val el = page.getElementById("ship-active-effects")

            println(shipEffects.size)
            shipEffects.forEach { shipEffect ->
                el.append {
                    div {
                        + (shipEffect.user1Id + " + " + shipEffect.user2Id + " com ${shipEffect.editedShipValue}%")
                    }
                }
            }
        })
    }

    @JsName("buy")
    fun prepareSave() {
        SaveUtils.prepareSave("ship_effect", endpoint = "${loriUrl}api/v1/users/self-profile", extras = {
            it["buyItem"] = "ship_effect"
            it["editedValue"] = (page.getElementById("newShipValue") as HTMLInputElement).value
            it["user2NamePlusDiscriminator"] = (page.getElementById("userName") as HTMLInputElement).value
        }, onFinish = {
            if (it.statusCode in 200 .. 299) {
                window.location.href = window.location.href + "?bought"
            } else {

            }
        })
    }

    @Serializable
    class ShipEffect(
            val buyerId: String,
            val user1Id: String,
            val user2Id: String,
            val editedShipValue: Int,
            val expiresAt: Long
    )
}
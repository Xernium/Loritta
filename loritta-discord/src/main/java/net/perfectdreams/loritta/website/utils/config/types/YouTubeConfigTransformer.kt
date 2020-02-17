package net.perfectdreams.loritta.website.utils.config.types

import com.github.salomonbrys.kotson.*
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.mrpowergamerbr.loritta.dao.ServerConfig
import com.mrpowergamerbr.loritta.network.Databases
import net.dv8tion.jda.api.entities.Guild
import net.perfectdreams.loritta.tables.TrackedYouTubeAccounts
import net.perfectdreams.loritta.website.session.LorittaJsonWebSession
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

object YouTubeConfigTransformer : ConfigTransformer {
    override val payloadType: String = "youtube"
    override val configKey: String = "trackedYouTubeChannels"

    override fun fromJson(guild: Guild, serverConfig: ServerConfig, payload: JsonObject) {
        transaction(Databases.loritta) {
            TrackedYouTubeAccounts.deleteWhere {
                TrackedYouTubeAccounts.guildId eq guild.idLong
            }

            val accounts = payload["accounts"].array

            for (account in accounts) {
                TrackedYouTubeAccounts.insert {
                    it[guildId] = guild.idLong
                    it[channelId] = account["channel"].long
                    it[youTubeChannelId] = account["youTubeChannelId"].string
                    it[message] = account["message"].string
                }
            }
        }
    }

    override fun toJson(guild: Guild, serverConfig: ServerConfig): JsonElement {
        return transaction(Databases.loritta) {
            val array = JsonArray()

            TrackedYouTubeAccounts.select {
                TrackedYouTubeAccounts.guildId eq guild.idLong
            }.forEach {
                array.add(
                        jsonObject(
                                "channelId" to it[TrackedYouTubeAccounts.channelId],
                                "youTubeChannelId" to it[TrackedYouTubeAccounts.youTubeChannelId],
                                "message" to it[TrackedYouTubeAccounts.message],
                                "webhookUrl" to it[TrackedYouTubeAccounts.webhookUrl]
                        )
                )
            }

            array
        }
    }
}
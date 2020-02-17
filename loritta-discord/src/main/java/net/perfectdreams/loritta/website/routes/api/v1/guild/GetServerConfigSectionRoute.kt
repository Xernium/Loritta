package net.perfectdreams.loritta.website.routes.api.v1.guild

import com.github.salomonbrys.kotson.jsonObject
import com.github.salomonbrys.kotson.set
import com.mrpowergamerbr.loritta.dao.ServerConfig
import com.mrpowergamerbr.loritta.userdata.MongoServerConfig
import io.ktor.application.ApplicationCall
import net.dv8tion.jda.api.entities.Guild
import net.perfectdreams.loritta.platform.discord.LorittaDiscord
import net.perfectdreams.loritta.website.routes.api.v1.RequiresAPIGuildAuthRoute
import net.perfectdreams.loritta.website.session.LorittaJsonWebSession
import net.perfectdreams.loritta.website.utils.WebsiteUtils
import net.perfectdreams.loritta.website.utils.config.types.YouTubeConfigTransformer
import net.perfectdreams.loritta.website.utils.extensions.respondJson
import net.perfectdreams.temmiediscordauth.TemmieDiscordAuth

class GetServerConfigSectionRoute(loritta: LorittaDiscord) : RequiresAPIGuildAuthRoute(loritta, "/config/{sections}") {
	override suspend fun onGuildAuthenticatedRequest(call: ApplicationCall, discordAuth: TemmieDiscordAuth, userIdentification: LorittaJsonWebSession.UserIdentification, guild: Guild, serverConfig: ServerConfig, legacyServerConfig: MongoServerConfig) {
		val sections = call.parameters["sections"]!!.split(",").toSet()

		val payload = jsonObject()

		val configTransformers = listOf(
				YouTubeConfigTransformer
		)

		for (section in sections) {
			val transformer = configTransformers.firstOrNull { it.configKey == section }

			if (transformer != null) {
				payload[transformer.payloadType] = transformer.toJson(guild, serverConfig)
			}
		}

		call.respondJson(payload)
	}
}
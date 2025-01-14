package net.perfectdreams.loritta.plugin.stafflorittaban.modules

import com.mrpowergamerbr.loritta.dao.Profile
import com.mrpowergamerbr.loritta.dao.ServerConfig
import com.mrpowergamerbr.loritta.events.LorittaMessageEvent
import com.mrpowergamerbr.loritta.modules.MessageReceivedModule
import com.mrpowergamerbr.loritta.userdata.MongoServerConfig
import com.mrpowergamerbr.loritta.utils.LorittaUser
import com.mrpowergamerbr.loritta.utils.locale.LegacyBaseLocale
import com.mrpowergamerbr.loritta.utils.loritta
import net.perfectdreams.loritta.plugin.stafflorittaban.StaffLorittaBanConfig

class AddReactionForStaffLoriBanModule(val config: StaffLorittaBanConfig) : MessageReceivedModule {
	override fun matches(event: LorittaMessageEvent, lorittaUser: LorittaUser, lorittaProfile: Profile, serverConfig: ServerConfig, legacyServerConfig: MongoServerConfig, locale: LegacyBaseLocale): Boolean {
		return config.enabled && event.channel.idLong in config.channels
	}

	override suspend fun handle(event: LorittaMessageEvent, lorittaUser: LorittaUser, lorittaProfile: Profile, serverConfig: ServerConfig, legacyServerConfig: MongoServerConfig, locale: LegacyBaseLocale): Boolean {
		val message = event.message

		val split = message.contentRaw.split(" ")
		val toBeBannedUserId = split[0]
		val args = split.toMutableList().apply { this.removeAt(0) }
		val reason = args.joinToString(" ")

		val profile = loritta.getLorittaProfile(toBeBannedUserId) ?: return false

		if (profile.isBanned) {
			event.channel.sendMessage("O usuário $toBeBannedUserId já está banido, bobinho! Ele foi banido pelo motivo `${profile.id.value}`")
					.queue()
			return false
		}

		if (reason.isBlank()) {
			event.channel.sendMessage("Você esqueceu de colocar o motivo! Coloque um motivo top top para o ban! ╰(\\*°▽°\\*)╯")
					.queue()
			return false
		}

		if (reason.startsWith("http://") || reason.startsWith("https://")) {
			event.channel.sendMessage("Coloque um motivo mais \"wow\", sabe? Sempre é bom explicar o motivo da pessoa ter sido banida (Como `Provocar a equipe de suporte da Loritta`) em vez de colocar apenas uma imagem, provas são boas mas não devemos apenas depender que a pessoa descubra o motivo do ban apenas baseando em uma imagem ^-^")
					.queue()
			return false
		}

		event.message.addReaction("gato_joinha:593161404937404416").queue()
		return false
	}
}
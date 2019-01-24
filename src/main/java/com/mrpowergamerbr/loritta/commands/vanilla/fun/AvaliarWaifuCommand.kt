package com.mrpowergamerbr.loritta.commands.vanilla.`fun`

import com.mrpowergamerbr.loritta.commands.AbstractCommand
import net.perfectdreams.loritta.api.commands.CommandCategory
import com.mrpowergamerbr.loritta.commands.CommandContext
import com.mrpowergamerbr.loritta.modules.InviteLinkModule
import com.mrpowergamerbr.loritta.utils.LoriReply
import com.mrpowergamerbr.loritta.utils.LorittaPermission
import com.mrpowergamerbr.loritta.utils.MiscUtils
import com.mrpowergamerbr.loritta.utils.locale.LegacyBaseLocale
import java.util.*

class AvaliarWaifuCommand : AbstractCommand("ratewaifu", listOf("avaliarwaifu", "ratemywaifu", "ratewaifu", "avaliarminhawaifu", "notawaifu"), CommandCategory.FUN) {
	override fun getDescription(locale: LegacyBaseLocale): String {
		return locale["RATEWAIFU_DESCRIPTION"]
	}

	override fun getExamples(): List<String> {
		return listOf("Loritta")
	}

	override fun getUsage(): String {
		return "<usuário 1>"
	}

	override suspend fun run(context: CommandContext,locale: LegacyBaseLocale) {
		if (context.args.isNotEmpty()) {
			var waifu = context.args.joinToString(separator = " ") // Vamos juntar tudo em uma string
			val inviteBlockerConfig = context.config.inviteBlockerConfig
			val checkInviteLinks = inviteBlockerConfig.isEnabled && !inviteBlockerConfig.whitelistedChannels.contains(context.event.channel.id) && !context.lorittaUser.hasPermission(LorittaPermission.ALLOW_INVITES)

			if (checkInviteLinks) {
				val whitelisted = mutableListOf<String>()
				whitelisted.addAll(context.config.inviteBlockerConfig.whitelistedIds)

				InviteLinkModule.cachedInviteLinks[context.guild.id]?.forEach {
					whitelisted.add(it)
				}

				if (MiscUtils.hasInvite(waifu, whitelisted)) {
					return
				}
			}

			val user = context.getUserAt(0)
			if (user != null) {
				waifu = user.name
			}

			val waifuLowerCase = waifu.toLowerCase()
			var random = SplittableRandom(Calendar.getInstance().get(Calendar.DAY_OF_YEAR) + waifuLowerCase.hashCode().toLong()) // Usar um RANDOM sempre com a mesma seed
			var nota = random.nextInt(0, 11)

			var reason = locale["RATEWAIFU_10"]

			if (nota == 9) {
				reason = "${locale["RATEWAIFU_9"]} <:osama:325332212255948802>"
			}
			if (nota == 8) {
				reason = locale["RATEWAIFU_8"]
			}
			if (nota == 7) {
				reason = "${locale["RATEWAIFU_7"]} 😊"
			}
			if (nota == 6) {
				reason = locale["RATEWAIFU_6"]
			}
			if (nota == 5) {
				reason = locale["RATEWAIFU_5"]
			}
			if (nota == 4) {
				reason = locale["RATEWAIFU_4"]
			}
			if (nota == 3) {
				reason = locale["RATEWAIFU_3"]
			}
			if (nota == 2) {
				reason = locale["RATEWAIFU_2"]
			}
			if (nota == 1) {
				reason = locale["RATEWAIFU_1"]
			}
			if (nota == 0) {
				reason = "🤦 ${locale["RATEWAIFU_0"]}"
			}
			var strNota = nota.toString()
			if (waifuLowerCase == "loritta") {
				strNota = "∞"
				reason = "${locale["RATEWAIFU_IM_PERFECT"]} <:loritta_quebrada:338679008210190336>"
			}
			if (waifuLowerCase == "pollux") {
				strNota = "10"
				reason = "${locale["RATEWAIFU_Pollux"]} <:polluxthonk:391375859937837076>"
			}
			if (waifuLowerCase == "pantufa") {
				strNota = "10"
				reason = "${locale["RATEWAIFU_Pantufa"]} \uD83D\uDE0A"
			}
			if (waifuLowerCase == "tyrone") {
				strNota = "10"
				reason = locale["RATEWAIFU_Tyrone"]
			}
			if (waifuLowerCase == "tatsumaki") {
				strNota = "10"
				reason = locale["RATEWAIFU_Tatsumaki"]
			}
			if (waifuLowerCase == "mantaro") {
				strNota = "8"
				reason = locale["RATEWAIFU_Mantaro"]
			}
			if (waifuLowerCase == "kawaiibot") {
				strNota = "8"
				reason = "ʕ•ᴥ•ʔ"
			}
			if (waifuLowerCase == "yggdrasil") {
				strNota = "8"
				reason = locale["RATEWAIFU_Yggdrasil"]
			}
			if (waifuLowerCase == "mee6") {
				strNota = "8"
				reason = locale["RATEWAIFU_Mee6"]
			}
			if (waifuLowerCase == "dyno") {
				strNota = "8"
				reason = locale["RATEWAIFU_Dyno"]
			}
			if (waifuLowerCase == "notsobot") {
				strNota = "6"
				reason = locale["RATEWAIFU_NotSoBot"]
			}
			if (waifuLowerCase == "lorita" || waifuLowerCase == "lorrita") {
				strNota = "-∞"
				reason = locale["RATEWAIFU_Lorrita"]
			}
			context.reply(
					LoriReply(
							message = locale["RATEWAIFU_RESULT", strNota, waifu, reason],
							prefix = "\uD83E\uDD14"
					)
			)
		} else {
			this.explain(context)
		}
	}
}
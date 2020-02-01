package net.perfectdreams.spicymorenitta

import ConfigureAutoroleView
import ConfigureEconomyView
import ConfigureMemberCounterView
import ConfigureMiscellaneousConfig
import ConfigureModerationView
import ConfigureProfileView
import ConfigureWelcomerView
import kotlinx.serialization.ImplicitReflectionSerializer
import net.perfectdreams.spicymorenitta.views.*

object DoNotRemoveDeadCodeWorkaround {
	@ImplicitReflectionSerializer
	val methodRefs = mutableListOf(
			ConfigureAutoroleView::start,
			ConfigureAutoroleView::prepareSave,
			ConfigureAutoroleView::addRoleFromSelection,
			ConfigureEconomyView::start,
			ConfigureEconomyView::prepareSave,
			ConfigureMemberCounterView::start,
			ConfigureMemberCounterView::prepareSave,
			ConfigureMiscellaneousConfig::start,
			ConfigureMiscellaneousConfig::prepareSave,
			ConfigureModerationView::start,
			ConfigureModerationView::prepareSave,
			ConfigureProfileView::start,
			ConfigureProfileView::prepareSave,
			ConfigureWelcomerView::start,
			ConfigureWelcomerView::prepareSave,
			DailyView::start,
			DailyView::recaptchaCallback,
			DonateView::start,
			ProfileListView::start,
			ReputationView::recaptchaCallback,
			ShipEffectsView::start,
			ShipEffectsView::prepareSave
	)
}
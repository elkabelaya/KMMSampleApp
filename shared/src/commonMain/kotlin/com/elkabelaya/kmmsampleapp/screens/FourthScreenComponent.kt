import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.elkabelaya.kmmsampleapp.screens.DefaultFirstScreenComponent
import com.elkabelaya.kmmsampleapp.screens.FirstScreenComponent
import com.elkabelaya.kmmsampleapp.utils.screenutils.ChildType
import com.elkabelaya.kmmsampleapp.utils.screenutils.TypedChild

data class FourthScreenState(
    val value: String
)
private sealed interface ForthScreenChildConfig : Parcelable {
    @Parcelize
    object FirstScreen : ForthScreenChildConfig
}

interface FourthScreenComponent {
    val state: Value<FourthScreenState>
    val childSlot: Value<ChildSlot<*, Child>>
    fun onBackClick()
    fun onNextClick()

    fun onCloseClick()
    sealed class Child(override val type: ChildType): TypedChild {
        class FirstScreen(val component: FirstScreenComponent) : Child(type = ChildType.SCREEN)
    }
}

class DefaultFourthScreenComponent(
    componentContext: ComponentContext,
    initialValue: String,
    val dismiss: () -> Unit
) : ComponentContext by componentContext, FourthScreenComponent {
    override val state = MutableValue(FourthScreenState(initialValue))
    private val slotNavigation = SlotNavigation<ForthScreenChildConfig>()

    private val _childSlot =
        childSlot(source = slotNavigation,
            handleBackButton = true,
            childFactory = ::createChild)
    override val childSlot: Value<ChildSlot<*, FourthScreenComponent.Child>> = _childSlot

    override fun onBackClick()  {
        slotNavigation.dismiss()
    }

    override fun onCloseClick() = dismiss()
    override fun onNextClick() {
        slotNavigation.activate(ForthScreenChildConfig.FirstScreen)
    }

    private fun createChild(
        config: ForthScreenChildConfig,
        componentContext: ComponentContext
    ): FourthScreenComponent.Child = when (config) {
        is ForthScreenChildConfig.FirstScreen -> {
            FourthScreenComponent.Child.FirstScreen(
                DefaultFirstScreenComponent(
                    componentContext,
                    dismiss = slotNavigation::dismiss
                )
            )
        }
    }
}

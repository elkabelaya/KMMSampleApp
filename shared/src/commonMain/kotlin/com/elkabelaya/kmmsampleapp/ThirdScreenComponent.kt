import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value

data class ThirdScreenState(
    val value: String
)

interface ThirdScreenComponent {
    val state: Value<ThirdScreenState>
    fun onBackClick()
}

class DefaultThirdScreenComponent(
    componentContext: ComponentContext,
    initialValue: String,
    val dismiss: () -> Unit
) : ComponentContext by componentContext, ThirdScreenComponent {
    override val state = MutableValue(ThirdScreenState(initialValue))
    override fun onBackClick()  = dismiss()
}
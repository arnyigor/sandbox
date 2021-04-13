package delegation

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class StringTrimDelegate : ReadWriteProperty<Any?, String> {

    private var value: String = ""

    override fun getValue(thisRef: Any?, property: KProperty<*>): String = value

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        this.value = value.trim()
    }

}
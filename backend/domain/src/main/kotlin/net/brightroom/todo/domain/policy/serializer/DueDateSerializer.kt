package net.brightroom.todo.domain.policy.serializer

import kotlinx.datetime.LocalDate
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import net.brightroom.todo.domain.model.planning.due.DueDate
import net.brightroom.todo.domain.model.planning.due.DueDateFactory

object DueDateSerializer : KSerializer<DueDate> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("net.brightroom.todo.domain.model.planning.due.DueDate", PrimitiveKind.STRING)

    @OptIn(ExperimentalSerializationApi::class)
    override fun serialize(
        encoder: Encoder,
        value: DueDate,
    ) {
        if (value.is期限日がセット済み()) {
            encoder.encodeString(value().toString())
        } else {
            encoder.encodeNull()
        }
    }

    @OptIn(ExperimentalSerializationApi::class)
    override fun deserialize(decoder: Decoder): DueDate {
        val str =
            decoder
                .decodeNullableSerializableValue(String.serializer())
                ?: return DueDateFactory.create(null)

        if (str.isBlank()) return DueDateFactory.create(null)

        return DueDateFactory.create(LocalDate.parse(str))
    }
}

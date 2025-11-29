package net.brightroom.todo.domain.validator.mailaddress

class MailAddressValidator(mailAddress: String) {
    private val localPart: LocalPart
    private val domainPart: DomainPart

    init {
        require(mailAddress.isNotBlank()) { "mail address can not be blank" }
        require(mailAddress.contains("@")) { "mail address must contain @" }
        require(mailAddress.count { it == '@' } == 1) { "mail address can only contain one '@'" }

        val l = mailAddress.split("@")
        localPart = LocalPart(l[0])
        domainPart = DomainPart(l[1])
    }

    fun validate() {
        localPart.validate()
        domainPart.validate()
    }
}

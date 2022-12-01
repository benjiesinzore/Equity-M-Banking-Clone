package com.benjaminsinzore.equitym_bankingclone.data

data class Email(

    val id: Long,
    val sender: com.benjaminsinzore.equitym_bankingclone.data.Account,
    val recipients: List<com.benjaminsinzore.equitym_bankingclone.data.Account> = emptyList(),
    val subject: String,
    val body: String,
    val attachments: List<EmailAttachment> = emptyList(),
    var isImportant: Boolean = false,
    var isStarred: Boolean = false,
    var mailbox: MailboxType = MailboxType.INBOX,
    val createdAt: String,
    val threads: List<Email> = emptyList()
)

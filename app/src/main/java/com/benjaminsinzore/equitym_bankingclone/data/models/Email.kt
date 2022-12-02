package com.benjaminsinzore.equitym_bankingclone.data.models

import com.benjaminsinzore.equitym_bankingclone.data.EmailAttachment
import com.benjaminsinzore.equitym_bankingclone.data.MailboxType

data class Email(

    val id: Long,
    val sender: Account,
    val recipients: List<Account> = emptyList(),
    val subject: String,
    val body: String,
    val attachments: List<EmailAttachment> = emptyList(),
    var isImportant: Boolean = false,
    var isStarred: Boolean = false,
    var mailbox: MailboxType = MailboxType.INBOX,
    val createdAt: String,
    val threads: List<Email> = emptyList()
)

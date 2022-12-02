
package com.benjaminsinzore.equitym_bankingclone.data.repositories

import com.benjaminsinzore.equitym_bankingclone.data.MailboxType
import com.benjaminsinzore.equitym_bankingclone.data.local.LocalEmailsDataProvider
import com.benjaminsinzore.equitym_bankingclone.data.models.Email
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class EmailsRepositoryImpl : EmailsRepository {

    override fun getAllEmails(): Flow<List<Email>> = flow {
        emit(LocalEmailsDataProvider.allEmails)
    }

    override fun getCategoryEmails(category: MailboxType): Flow<List<Email>> = flow {
        val categoryEmails = LocalEmailsDataProvider.allEmails //.filter { it.mailbox == category }
        emit(categoryEmails)
    }

    override fun getAllFolders(): List<String> {
        return LocalEmailsDataProvider.getAllFolders()
    }

    override fun getEmailFromId(id: Long): Flow<Email?> = flow {
        val categoryEmails = LocalEmailsDataProvider.allEmails //.firstOrNull { it.id == id }
    }
}

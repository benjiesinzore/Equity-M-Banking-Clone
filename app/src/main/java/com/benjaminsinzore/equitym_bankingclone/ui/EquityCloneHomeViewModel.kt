package com.benjaminsinzore.equitym_bankingclone.ui.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.benjaminsinzore.equitym_bankingclone.data.Email
import com.benjaminsinzore.equitym_bankingclone.data.EmailsRepository
import com.benjaminsinzore.equitym_bankingclone.data.EmailsRepositoryImpl
import com.benjaminsinzore.equitym_bankingclone.ui.theme.utils.EquityCloneContentType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class EquityCloneHomeViewModel(private val emailsRepository: EmailsRepository = EmailsRepositoryImpl()) :
    ViewModel() {

    // UI state exposed to the UI
    private val _uiState = MutableStateFlow(EquityCloneHomeUIState(loading = true))
    val uiState: StateFlow<EquityCloneHomeUIState> = _uiState

    init {
        observeEmails()
    }

    private fun observeEmails() {
        viewModelScope.launch {
            emailsRepository.getAllEmails()
                .catch { ex ->
                    _uiState.value = EquityCloneHomeUIState(error = ex.message)
                }
                .collect { emails ->
                    /**
                     * We set first email selected by default for first App launch in large-screens
                     */
                    _uiState.value = EquityCloneHomeUIState(
                        emails = emails,
                        selectedEmail = emails.first()
                    )
                }
        }
    }

    fun setSelectedEmail(emailId: Long, contentType: EquityCloneContentType) {
        /**
         * We only set isDetailOnlyOpen to true when it's only single pane layout
         */
        val email = uiState.value.emails.find { it.id == emailId }
        _uiState.value = _uiState.value.copy(
            selectedEmail = email,
            isDetailOnlyOpen = contentType == EquityCloneContentType.SINGLE_PANE
        )
    }

    fun closeDetailScreen() {
        _uiState.value = _uiState
            .value.copy(
                isDetailOnlyOpen = false,
                selectedEmail = _uiState.value.emails.first()
            )
    }
}

data class EquityCloneHomeUIState(
    val emails: List<Email> = emptyList(),
    val selectedEmail: Email? = null,
    val isDetailOnlyOpen: Boolean = false,
    val loading: Boolean = false,
    val error: String? = null
)
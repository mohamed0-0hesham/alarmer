package com.hesham0_0.alarmer.ui.medicine

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hesham0_0.alarmer.domain.model.MedicineReminder
import com.hesham0_0.alarmer.domain.usecase.medicine.GetMedicineRemindersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MedicineViewModel @Inject constructor(
    getMedicineRemindersUseCase: GetMedicineRemindersUseCase
) : ViewModel() {

    val medicineReminders: StateFlow<List<MedicineReminder>> = getMedicineRemindersUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
}

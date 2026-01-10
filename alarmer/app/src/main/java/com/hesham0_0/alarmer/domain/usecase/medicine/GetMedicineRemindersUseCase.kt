package com.hesham0_0.alarmer.domain.usecase.medicine

import com.hesham0_0.alarmer.domain.model.MedicineReminder
import com.hesham0_0.alarmer.domain.repository.MedicineRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMedicineRemindersUseCase @Inject constructor(
    private val repository: MedicineRepository
) {
    operator fun invoke(): Flow<List<MedicineReminder>> = repository.getMedicineReminders()
}

package com.oxymium.si2gassistant.features.academies

import com.oxymium.si2gassistant.model.Academy

// ---------------
// AcademyListener
// ---------------

class AcademyListener(val onClickAcademyListener: (academy: Academy) -> Unit) {

    fun onClickAcademy(academy: Academy) = onClickAcademyListener(academy)

}
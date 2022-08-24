package com.oxymium.si2gassistant.features.modules

import com.oxymium.si2gassistant.model.Module

// --------------
// ModuleListener
// --------------

class ModuleListener(val onClickModuleListener: (module: Module) -> Unit) {

    fun onClickModule(module: Module) = onClickModuleListener(module)

}
package com.oxymium.si2gassistant.features.actors

import com.oxymium.si2gassistant.model.Actor

// -------------
// ActorListener
// -------------

class ActorListener(val onClickActorListener: (actor: Actor) -> Unit) {

    fun onClickActor(actor: Actor) = onClickActorListener(actor)

}
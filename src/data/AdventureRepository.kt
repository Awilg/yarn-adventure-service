package com.yarn.services.data

import com.yarn.services.core.data.BaseDaoAsync
import org.litote.kmongo.coroutine.CoroutineCollection

class AdventureRepository(collection: CoroutineCollection<Adventure>) : BaseDaoAsync<Adventure>(collection) {

}

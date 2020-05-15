package p.vasylprokudin.israelit.domain.interactor.base

import io.reactivex.observers.DisposableSingleObserver

interface ExecutableUseCase {

    interface Single<Results, in Params> {
        fun execute(
            observer: DisposableSingleObserver<Results> = EmptySingleObserver(),
            params: Params? = null
        )
    }
}

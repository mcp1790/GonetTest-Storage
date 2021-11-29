package mx.test.android.gonet.storagelib.implement

import io.reactivex.Observable

interface IDao<T> {
    fun getRx(id: Int): Observable<T>
    fun saveRx(model: T?): Observable<Boolean>
    fun updateRx(model: T): Observable<Boolean>
    fun deleteRx(id: Int): Observable<Boolean>
}
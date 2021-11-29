package mx.test.android.gonet.storagelib.implement

import android.annotation.SuppressLint
import android.content.Context
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.realm.RealmList
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import mx.test.android.gonet.domainlib.models.ListTvShowsModel
import mx.test.android.gonet.storagelib.converter.*
import mx.test.android.gonet.storagelib.entity.ListMoviesRealmEntity
import mx.test.android.gonet.storagelib.entity.ListTvShowsRealmEntity
import mx.test.android.gonet.storagelib.entity.MovieRawRealmEntity
import mx.test.android.gonet.storagelib.realmConfig.RealmCore

@SuppressLint("CheckResult")
class TVShowListStorage(val context: Context) : IDao<ListTvShowsModel> {
    override fun getRx(id: Int): Observable<ListTvShowsModel> {
        return Observable.create { emitter ->
            val realm = RealmCore.getRxInstance(context = context)

            realm.executeTransaction { rlm ->
                val realmEntity =
                    rlm.where<ListTvShowsRealmEntity>().equalTo("page", id).findFirst()

                realmEntity?.let { list ->
                    emitter.onNext(ListTvShowStorageConverter.entityToModel(list))
                } ?: emitter.onError(Throwable("TVShowListStorage: Element is null"))
            }
            realm.close()
        }
    }

    override fun saveRx(model: ListTvShowsModel?): Observable<Boolean> {
        return Observable.create { emitter ->

            model?.let { tvModel ->
                this.getRx(tvModel.page)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .subscribe(
                        {
                            this.updateRx(tvModel)
                                .subscribeOn(Schedulers.io())
                                .observeOn(Schedulers.io())
                                .subscribe(
                                    {
                                        emitter.onNext(true)
                                    },
                                    { error ->
                                        emitter.onError(error)
                                    }
                                )
                        },
                        {
                            val realm = RealmCore.getRxInstance(context)

                            realm.executeTransaction { rlm ->
                                rlm.createObject<ListMoviesRealmEntity>(primaryKeyValue = tvModel.page)
                                    .apply {
                                        total_pages = tvModel.totalPages
                                        total_results = tvModel.totalResults
                                        val movieRealmList: RealmList<MovieRawRealmEntity> =
                                            RealmList()
                                        tvModel.results.forEach {
                                            movieRealmList.add(
                                                rlm.createObject(TvShowRawStorageConverter.modelToEntity(it))
                                            )
                                        }
                                        results =
                                            rlm.copyToRealm(movieRealmList) as RealmList<MovieRawRealmEntity>
                                    }
                            }

                            realm.close()
                            emitter.onNext(true)
                        }
                    )
            }
        }
    }

    override fun updateRx(model: ListTvShowsModel): Observable<Boolean> {
        return Observable.create { emitter ->
            val realm = RealmCore.getRxInstance(context)
            realm.executeTransaction { rlm ->
                rlm.copyToRealmOrUpdate(ListTvShowStorageConverter.modelToEntity(model))
            }

            realm.close()
            emitter.onNext(true)
        }
    }

    override fun deleteRx(id: Int): Observable<Boolean> {
        return Observable.create { emitter ->
            val realm = RealmCore.getRxInstance(context)
            realm.executeTransaction { rlm ->
                val realmEntity =
                    rlm.where<ListTvShowsRealmEntity>().equalTo("page", id).findFirst()
                realmEntity?.deleteFromRealm()
            }

            realm.close()
            emitter.onNext(true)
        }
    }
}
package mx.test.android.gonet.storagelib.implement

import android.annotation.SuppressLint
import android.content.Context
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.realm.RealmList
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import mx.test.android.gonet.domainlib.models.ListMoviesModel
import mx.test.android.gonet.storagelib.converter.DatesStorageConverter
import mx.test.android.gonet.storagelib.converter.ListMoviesStorageConverter
import mx.test.android.gonet.storagelib.converter.MovieRawStorageConverter
import mx.test.android.gonet.storagelib.entity.ListMoviesRealmEntity
import mx.test.android.gonet.storagelib.entity.MovieRawRealmEntity
import mx.test.android.gonet.storagelib.realmConfig.RealmCore

@SuppressLint("CheckResult")
class MoviesListStorage(val context: Context) : IDao<ListMoviesModel> {
    override fun getRx(id: Int): Observable<ListMoviesModel> {
        return Observable.create { emitter ->
            val realm = RealmCore.getRxInstance(context = context)

            realm.executeTransaction { rlm ->
                val realmEntity =
                    rlm.where<ListMoviesRealmEntity>().equalTo("page", id).findFirst()

                realmEntity?.let { list ->
                    emitter.onNext(ListMoviesStorageConverter.entityToModel(list))
                } ?: emitter.onError(Throwable("MoviesListStorage: Element is null"))
            }
            realm.close()
        }
    }

    override fun saveRx(model: ListMoviesModel?): Observable<Boolean> {
        return Observable.create { emitter ->

            model?.let { movieModel ->
                this.getRx(movieModel.page)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .subscribe(
                        {
                            this.updateRx(movieModel)
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
                                rlm.createObject<ListMoviesRealmEntity>(primaryKeyValue = movieModel.page)
                                    .apply {
                                        total_pages = movieModel.totalPages
                                        total_results = movieModel.totalResults
                                        val movieRealmList: RealmList<MovieRawRealmEntity> =
                                            RealmList()
                                        movieModel.results.forEach {
                                            movieRealmList.add(
                                                rlm.createObject(MovieRawStorageConverter.modelToEntity(it))
                                            )
                                        }
                                        results =
                                            rlm.copyToRealm(movieRealmList) as RealmList<MovieRawRealmEntity>
                                        dates =
                                            rlm.copyToRealm(DatesStorageConverter.modelToEntity(movieModel.dates))
                                    }
                            }

                            realm.close()
                            emitter.onNext(true)
                        }
                    )
            }
        }
    }

    override fun updateRx(model: ListMoviesModel): Observable<Boolean> {
        return Observable.create { emitter ->
            val realm = RealmCore.getRxInstance(context)
            realm.executeTransaction { rlm ->
                rlm.copyToRealmOrUpdate(ListMoviesStorageConverter.modelToEntity(model))
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
                    rlm.where<ListMoviesRealmEntity>().equalTo("page", id).findFirst()
                realmEntity?.deleteFromRealm()
            }

            realm.close()
            emitter.onNext(true)
        }
    }
}
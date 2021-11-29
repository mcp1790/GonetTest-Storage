package mx.test.android.gonet.storagelib.implement

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.realm.RealmList
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import mx.test.android.gonet.domainlib.models.ListMoviesModel
import mx.test.android.gonet.domainlib.models.MovieRawModel
import mx.test.android.gonet.storagelib.converter.DatesConverter
import mx.test.android.gonet.storagelib.converter.ListMoviesConverter
import mx.test.android.gonet.storagelib.converter.MovieRawConverter
import mx.test.android.gonet.storagelib.entity.ListMoviesRealmEntity
import mx.test.android.gonet.storagelib.entity.MovieRawRealmEntity
import mx.test.android.gonet.storagelib.realmConfig.RealmCore
import java.lang.Exception

@SuppressLint("CheckResult")
class MoviesListStorage(val context: Context) : IDao<ListMoviesModel> {
    override fun getRx(id: Int): Observable<ListMoviesModel?> {
        return Observable.create { emitter ->
            val realm = RealmCore.getRxInstance(context = context)

            realm.executeTransaction { rlm ->
                val realmEntity =
                    rlm.where<ListMoviesRealmEntity>().equalTo("page", id).findFirst()

                realmEntity?.let { list ->
                    emitter.onNext(ListMoviesConverter.entityToModel(list))
                } ?: emitter.onError(Throwable("GWHomeCarouselsStorage: Element is null"))
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
                                                rlm.createObject(MovieRawConverter.modelToEntity(it))
                                            )
                                        }
                                        results =
                                            rlm.copyToRealm(movieRealmList) as RealmList<MovieRawRealmEntity>
                                        dates =
                                            rlm.copyToRealm(DatesConverter.modelToEntity(movieModel.dates))
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
                rlm.copyToRealmOrUpdate(ListMoviesConverter.modelToEntity(model))
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
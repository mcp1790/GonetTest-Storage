package mx.test.android.gonet.storagelib.implement

import android.annotation.SuppressLint
import android.content.Context
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.realm.RealmList
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import mx.test.android.gonet.domainlib.models.TvShowRawModel
import mx.test.android.gonet.storagelib.converter.*
import mx.test.android.gonet.storagelib.entity.ListMoviesRealmEntity
import mx.test.android.gonet.storagelib.entity.TvShowRawRealmEntity
import mx.test.android.gonet.storagelib.entity.child.GenreRealmEntity
import mx.test.android.gonet.storagelib.realmConfig.RealmCore

@SuppressLint("CheckResult")
class TvShowRawStorage(val context: Context) : IDao<TvShowRawModel> {
    override fun getRx(id: Int): Observable<TvShowRawModel> {
        return Observable.create { emitter ->
            val realm = RealmCore.getRxInstance(context = context)

            realm.executeTransaction { rlm ->
                val realmEntity =
                    rlm.where<TvShowRawRealmEntity>().equalTo("page", id).findFirst()

                realmEntity?.let { list ->
                    emitter.onNext(TvShowRawStorageConverter.entityToModel(list))
                } ?: emitter.onError(Throwable("GWHomeCarouselsStorage: Element is null"))
            }
            realm.close()
        }
    }

    override fun saveRx(model: TvShowRawModel?): Observable<Boolean> {
        return Observable.create { emitter ->

            model?.let { movieModel ->
                this.getRx(movieModel.id)
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
                                rlm.createObject<TvShowRawRealmEntity>(primaryKeyValue = movieModel.id)
                                    .apply {
                                        backdrop_path = movieModel.backdropPath
                                        first_air_date = movieModel.firstAirDate
                                        val genreIdRealmList: RealmList<Int> = RealmList()
                                        movieModel.genres.forEach {
                                            genreIdRealmList.add(
                                                rlm.createObject(it.id)
                                            )
                                        }
                                        genre_ids = genreIdRealmList
                                        val genreRealmList: RealmList<GenreRealmEntity> = RealmList()
                                        movieModel.genres.forEach {
                                            genreRealmList.add(
                                                rlm.createObject(GenreStorageConverter.modelToEntity(it))
                                            )
                                        }
                                        genres = genreRealmList
                                        id = movieModel.id
                                        name = movieModel.name
                                        val countryRealmList: RealmList<String> = RealmList()
                                        movieModel.originCountry.forEach {
                                            countryRealmList.add(
                                                rlm.createObject(it)
                                            )
                                        }
                                        origin_country = countryRealmList
                                        original_language = movieModel.originalLanguage
                                        original_name = movieModel.originalName
                                        overview = movieModel.overview
                                        popularity = movieModel.popularity
                                        poster_path = movieModel.posterPath
                                        vote_average = movieModel.voteAverage
                                        vote_count = movieModel.voteCount
                                    }
                            }

                            realm.close()
                            emitter.onNext(true)
                        }
                    )
            }
        }
    }

    override fun updateRx(model: TvShowRawModel): Observable<Boolean> {
        return Observable.create { emitter ->
            val realm = RealmCore.getRxInstance(context)
            realm.executeTransaction { rlm ->
                rlm.copyToRealmOrUpdate(TvShowRawStorageConverter.modelToEntity(model))
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
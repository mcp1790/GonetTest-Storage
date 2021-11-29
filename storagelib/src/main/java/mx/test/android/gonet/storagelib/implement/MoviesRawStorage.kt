package mx.test.android.gonet.storagelib.implement

import android.annotation.SuppressLint
import android.content.Context
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.realm.RealmList
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import mx.test.android.gonet.domainlib.models.MovieRawModel
import mx.test.android.gonet.storagelib.converter.*
import mx.test.android.gonet.storagelib.entity.ListMoviesRealmEntity
import mx.test.android.gonet.storagelib.entity.MovieRawRealmEntity
import mx.test.android.gonet.storagelib.entity.child.GenreRealmEntity
import mx.test.android.gonet.storagelib.entity.child.ProductionCompanyRealmEntity
import mx.test.android.gonet.storagelib.entity.child.ProductionCountryRealmEntity
import mx.test.android.gonet.storagelib.entity.child.SpokenLanguageRealmEntity
import mx.test.android.gonet.storagelib.realmConfig.RealmCore

@SuppressLint("CheckResult")
class MoviesRawStorage(val context: Context) : IDao<MovieRawModel> {
    override fun getRx(id: Int): Observable<MovieRawModel> {
        return Observable.create { emitter ->
            val realm = RealmCore.getRxInstance(context = context)

            realm.executeTransaction { rlm ->
                val realmEntity =
                    rlm.where<MovieRawRealmEntity>().equalTo("page", id).findFirst()

                realmEntity?.let { list ->
                    emitter.onNext(MovieRawStorageConverter.entityToModel(list))
                } ?: emitter.onError(Throwable("GWHomeCarouselsStorage: Element is null"))
            }
            realm.close()
        }
    }

    override fun saveRx(model: MovieRawModel?): Observable<Boolean> {
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
                                rlm.createObject<MovieRawRealmEntity>(primaryKeyValue = movieModel.id)
                                    .apply {
                                        adult = movieModel.adult
                                        backdrop_path = movieModel.backdropPath
                                        budget = movieModel.budget
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
                                        homepage = movieModel.homepage
                                        imdb_id = movieModel.imdbId
                                        original_language = movieModel.originalLanguage
                                        original_title = movieModel.originalTitle
                                        overview = movieModel.overview
                                        popularity = movieModel.popularity
                                        poster_path = movieModel.posterPath
                                        val prodCompaniesRealmList: RealmList<ProductionCompanyRealmEntity> = RealmList()
                                        movieModel.productionCompanies.forEach {
                                            prodCompaniesRealmList.add(
                                                rlm.createObject(ProductionCompanyStorageConverter.modelToEntity(it))
                                            )
                                        }
                                        production_companies = prodCompaniesRealmList
                                        val prodCountriesRealmList: RealmList<ProductionCountryRealmEntity> = RealmList()
                                        movieModel.productionCompanies.forEach {
                                            prodCountriesRealmList.add(
                                                rlm.createObject(ProductionCompanyStorageConverter.modelToEntity(it))
                                            )
                                        }
                                        production_countries = prodCountriesRealmList
                                        release_date = movieModel.releaseDate
                                        revenue = movieModel.revenue
                                        runtime = movieModel.runtime
                                        val spokenLanguagesRealmList: RealmList<SpokenLanguageRealmEntity> = RealmList()
                                        movieModel.spokenLanguages.forEach {
                                            spokenLanguagesRealmList.add(
                                                rlm.createObject(SpokenLanguagesStorageConverter.modelToEntity(it))
                                            )
                                        }
                                        spoken_languages = spokenLanguagesRealmList
                                        status = movieModel.status
                                        tagline = movieModel.tagline
                                        title = movieModel.title
                                        video = movieModel.video
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

    override fun updateRx(model: MovieRawModel): Observable<Boolean> {
        return Observable.create { emitter ->
            val realm = RealmCore.getRxInstance(context)
            realm.executeTransaction { rlm ->
                rlm.copyToRealmOrUpdate(MovieRawStorageConverter.modelToEntity(model))
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
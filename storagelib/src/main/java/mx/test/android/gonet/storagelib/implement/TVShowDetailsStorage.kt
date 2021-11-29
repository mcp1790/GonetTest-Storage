package mx.test.android.gonet.storagelib.implement

import android.annotation.SuppressLint
import android.content.Context
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.realm.RealmList
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import mx.test.android.gonet.domainlib.models.TvShowDetailModel
import mx.test.android.gonet.storagelib.converter.*
import mx.test.android.gonet.storagelib.entity.ListTvShowsRealmEntity
import mx.test.android.gonet.storagelib.entity.TvShowDetailRealmEntity
import mx.test.android.gonet.storagelib.entity.child.*
import mx.test.android.gonet.storagelib.realmConfig.RealmCore

@SuppressLint("CheckResult")
class TVShowDetailsStorage(val context: Context) : IDao<TvShowDetailModel> {
    override fun getRx(id: Int): Observable<TvShowDetailModel> {
        return Observable.create { emitter ->
            val realm = RealmCore.getRxInstance(context = context)

            realm.executeTransaction { rlm ->
                val realmEntity =
                    rlm.where<TvShowDetailRealmEntity>().equalTo("id", id).findFirst()

                realmEntity?.let { list ->
                    emitter.onNext(TvShowDetailStorageConverter.entityToModel(list))
                } ?: emitter.onError(Throwable("GWHomeCarouselsStorage: Element is null"))
            }
            realm.close()
        }
    }

    override fun saveRx(model: TvShowDetailModel?): Observable<Boolean> {
        return Observable.create { emitter ->

            model?.let { tvModel ->
                this.getRx(tvModel.id)
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
                                rlm.createObject<TvShowDetailRealmEntity>(primaryKeyValue = tvModel.id)
                                    .apply {
                                        backdrop_path = tvModel.backdropPath
                                        val createdByRealmList: RealmList<CreatedByRealmEntity> = RealmList()
                                        tvModel.createdBy.forEach {
                                            createdByRealmList.add(
                                                rlm.createObject(CreatedByStorageConverter.modelToEntity(it))
                                            )
                                        }
                                        created_by = createdByRealmList
                                        val episodeRuntRealmList: RealmList<Int> = RealmList()
                                        tvModel.episodeRunTime.forEach {
                                            episodeRuntRealmList.add(
                                                rlm.createObject(it)
                                            )
                                        }
                                        episode_run_time = episodeRuntRealmList
                                        first_air_date = tvModel.firstAirDate
                                        val genreRealmList: RealmList<GenreRealmEntity> = RealmList()
                                        tvModel.genres.forEach {
                                            genreRealmList.add(
                                                rlm.createObject(GenreStorageConverter.modelToEntity(it))
                                            )
                                        }
                                        genres = genreRealmList
                                        homepage = tvModel.homepage
                                        in_production = tvModel.inProduction
                                        val languagesRealmList: RealmList<String> = RealmList()
                                        tvModel.languages.forEach {
                                            languagesRealmList.add(
                                                rlm.createObject(it)
                                            )
                                        }
                                        languages = languagesRealmList
                                        lastA_air_date = tvModel.lastAirDate
                                        last_episode_to_air = rlm.copyToRealm(LastEpisodeOnAirStorageConverter.modelToEntity(tvModel.lastEpisodeToAir))
                                        name = tvModel.name
                                        val networkRealmList: RealmList<NetworkRealmEntity> = RealmList()
                                        tvModel.networks.forEach {
                                            networkRealmList.add(
                                                rlm.createObject(NetworkStorageConverter.modelToEntity(it))
                                            )
                                        }
                                        networks = networkRealmList
                                        number_of_episodes = tvModel.numberOfEpisodes
                                        number_of_seasons = tvModel.numberOfSeasons
                                        val countryRealmList: RealmList<String> = RealmList()
                                        tvModel.originCountry.forEach {
                                            countryRealmList.add(
                                                rlm.createObject(it)
                                            )
                                        }
                                        origin_country = countryRealmList
                                        original_language = tvModel.originalLanguage
                                        original_name = tvModel.originalName
                                        overview = tvModel.overview
                                        popularity = tvModel.popularity
                                        poster_path = tvModel.posterPath
                                        val prodCompaniesRealmList: RealmList<ProductionCompanyRealmEntity> = RealmList()
                                        tvModel.productionCompanies.forEach {
                                            prodCompaniesRealmList.add(
                                                rlm.createObject(ProductionCompanyStorageConverter.modelToEntity(it))
                                            )
                                        }
                                        production_companies = prodCompaniesRealmList
                                        val prodCountriesRealmList: RealmList<ProductionCountryRealmEntity> = RealmList()
                                        tvModel.productionCompanies.forEach {
                                            prodCountriesRealmList.add(
                                                rlm.createObject(ProductionCompanyStorageConverter.modelToEntity(it))
                                            )
                                        }
                                        production_countries = prodCountriesRealmList
                                        val seasonsRealmList: RealmList<SeasonRealmEntity> = RealmList()
                                        tvModel.seasons.forEach {
                                            seasonsRealmList.add(
                                                rlm.createObject(SeasonStorageConverter.modelToEntity(it))
                                            )
                                        }
                                        seasons = seasonsRealmList
                                        val spokenLanguagesRealmList: RealmList<SpokenLanguageRealmEntity> = RealmList()
                                        tvModel.spokenLanguages.forEach {
                                            spokenLanguagesRealmList.add(
                                                rlm.createObject(SpokenLanguagesStorageConverter.modelToEntity(it))
                                            )
                                        }
                                        spoken_languages = spokenLanguagesRealmList
                                        status = tvModel.status
                                        tagline = tvModel.tagline
                                        type = tvModel.type
                                        vote_average = tvModel.voteAverage
                                        vote_count = tvModel.voteCount
                                    }
                            }

                            realm.close()
                            emitter.onNext(true)
                        }
                    )
            }
        }
    }

    override fun updateRx(model: TvShowDetailModel): Observable<Boolean> {
        return Observable.create { emitter ->
            val realm = RealmCore.getRxInstance(context)
            realm.executeTransaction { rlm ->
                rlm.copyToRealmOrUpdate(TvShowDetailStorageConverter.modelToEntity(model))
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
                    rlm.where<ListTvShowsRealmEntity>().equalTo("id", id).findFirst()
                realmEntity?.deleteFromRealm()
            }

            realm.close()
            emitter.onNext(true)
        }
    }
}